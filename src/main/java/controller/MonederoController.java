package controller;

import Views.MonederoEstudiantil;
import Views.Op_Usuario;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MonederoController {
    private final MonederoEstudiantil view;
    private final DecimalFormat formato = new DecimalFormat("$#,##0.00");
    private final Map<String, Estudiante> baseDatos;
    private double saldo = 0.0;
    private final String usuarioActual;
    private final boolean esAdmin;

    public MonederoController(MonederoEstudiantil view, String usuario, boolean esAdmin) {
        this.view = view;
        this.usuarioActual = usuario;
        this.esAdmin = esAdmin;
        this.baseDatos = new HashMap<>();
        cargarBaseDatos();
        configurarListeners();
        cargarSaldoInicial();
    }

    private void configurarListeners() {
        view.setRecargarListener(this::procesarRecarga);
        view.setRegresarListener(this::regresarAUsuario);
    }

    private void cargarSaldoInicial() {
        Estudiante estudiante = baseDatos.get(usuarioActual);
        if (estudiante != null) {
            saldo = estudiante.saldo;
            view.actualizarSaldo(formato.format(saldo));
            view.mostrarHistorial(estudiante.historial != null ? estudiante.historial : "");
    } else {
        // Inicializar un nuevo estudiante si no existe
        estudiante = new Estudiante(usuarioActual, "Estudiante", "", "", 0.0, "");
        baseDatos.put(usuarioActual, estudiante);
        guardarBaseDatos();
     }
    }

    public void procesarRecarga(java.awt.event.ActionEvent e) {
        if (!validarCampos()) return;

        String telefono = view.getTelefono();
        String banco = view.getBanco();
        double monto = view.getMonto();

        Estudiante estudiante = baseDatos.computeIfAbsent(usuarioActual, 
            k -> new Estudiante(usuarioActual, "Estudiante", telefono, banco, 0.0, ""));

        estudiante.saldo += monto;
        saldo = estudiante.saldo;

        String transaccion = String.format(
            "%n[Transacción %s]%n" +
            "Fecha/Hora: %s%n" +
            "Tipo: Recarga%n" +
            "Monto: %s%n" +
            "Banco: %s%n" +
            "Teléfono asociado: %s%n%n",
    LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")),
    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
    formato.format(monto),
    banco,
    telefono);
    
    // Asegurar que siempre empiece con historial limpio
    if (estudiante.historial == null) estudiante.historial = "";
    estudiante.historial += transaccion;

    view.actualizarSaldo(formato.format(saldo));
    // Muestra todo el historial
    view.mostrarHistorial(estudiante.historial);
    view.limpiarCampos();
    
    guardarBaseDatos();
    }

    private boolean validarCampos() {
        if (!validarTelefono()) return false;
        if (!validarBanco()) return false;
        return validarMonto();
    }

    private boolean validarTelefono() {
        String telefono = view.getTelefono();
        if (telefono == null || telefono.trim().isEmpty() || !telefono.matches("\\d{11}")) {
            view.mostrarError("El teléfono debe tener 11 dígitos");
            return false;
        }
        return true;
    }

    private boolean validarBanco() {
        if ("Seleccionar banco".equals(view.getBanco())) {
            view.mostrarError("Seleccione un banco");
            return false;
        }
        return true;
    }

    private boolean validarMonto() {
        try {
            double monto = view.getMonto();
            if (monto <= 0) {
                view.mostrarError("El monto debe ser mayor a cero");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            view.mostrarError("Monto inválido");
            return false;
        }
    }

    private void cargarBaseDatos() {
    String nombreArchivo = "DataBase/monedero_" + usuarioActual + ".txt";
    new File("DataBase").mkdirs();
    
    try {
        String content = new String(Files.readAllBytes(Paths.get(nombreArchivo)));
        String[] sections = content.split("--- HISTORIAL ---");
        
        if(sections.length > 1) {
            // Procesar sección de datos
            String dataSection = sections[0];
            Map<String, String> dataMap = new HashMap<>();
            Arrays.stream(dataSection.split("\n"))
                .filter(line -> line.contains(":"))
                .forEach(line -> {
                    String[] parts = line.split(":", 2);
                    dataMap.put(parts[0].trim(), parts[1].trim());
                });
            
            // Procesar historial
            String historial = sections[1].replace("|TRANSACCION_FINAL|", "\n")
                                       .replace("-----------------", "")
                                       .trim();
            
            Estudiante e = new Estudiante(
                dataMap.get("Usuario"),
                dataMap.get("Nombre"),
                dataMap.get("Teléfono"),
                dataMap.get("Banco preferido"),
                Double.parseDouble(dataMap.get("Saldo actual").replace("$", "")),
                historial
            );
            baseDatos.put(e.cedula, e);
        }
    } catch (IOException e) {
        System.err.println("Error al cargar datos: " + e.getMessage());
        baseDatos.put(usuarioActual, new Estudiante(usuarioActual, "Estudiante", "", "", 0.0, ""));
    }
}

    private void guardarBaseDatos() {
        String nombreArchivo = "DataBase/monedero_" + usuarioActual + ".txt";
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            Estudiante estudiante = baseDatos.get(usuarioActual);
            if (estudiante != null) {
                pw.println(estudiante);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
            view.mostrarError("Error al guardar los datos");
        }
    }

   public void regresarAUsuario(java.awt.event.ActionEvent e) {
    view.cerrarVentana();
    SwingUtilities.invokeLater(() -> {
        Op_Usuario opUsuario = new Op_Usuario(usuarioActual, this.esAdmin);
        new OpUsuarioController(opUsuario, usuarioActual, this.esAdmin); // Conectamos el controlador
        opUsuario.setVisible(true);
    });
}

    static class Estudiante {
        final String cedula;
        final String nombre;
        final String telefono;
        final String banco;
        double saldo;
        String historial;

        public Estudiante(String cedula, String nombre, String telefono, 
                         String banco, double saldo, String historial) {
            this.cedula = cedula;
            this.nombre = nombre;
            this.telefono = telefono;
            this.banco = banco;
            this.saldo = saldo;
            this.historial = (historial != null) 
                 ? historial.replace("||n||", "\n ").replace("#", " ").trim()
                 : " " ;
        }

        @Override
        public String toString() {
    // Separar el historial con un delimitador claro
    String histParaArchivo = historial.replace("\n", " ");
    return String.format(
        "Usuario: %s\n" +
        "Nombre: %s\n" +
        "Teléfono: %s\n" +
        "Banco preferido: %s\n" +
        "Saldo actual: %s\n" +
        "--- HISTORIAL ---\n%s\n" +
        "-----------------",
        cedula, nombre, telefono, banco, 
        String.valueOf(saldo), histParaArchivo);
    }
  }
}
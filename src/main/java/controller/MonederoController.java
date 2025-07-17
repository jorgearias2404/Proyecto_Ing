package controller;

import Views.MonederoEstudiantil;
import Views.Op_Usuario;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MonederoController {
    private final MonederoEstudiantil view;
    private final DecimalFormat formato = new DecimalFormat("$#,##0.00");
    private final Map<String, Estudiante> baseDatos;
    private double saldo = 0.0;

    // Colores
    private static final Color COLOR_EXITO = new Color(46, 204, 113);
    private static final Color COLOR_ERROR = new Color(231, 76, 60);

    public MonederoController(MonederoEstudiantil view) {
        this.view = view;
        this.baseDatos = new HashMap<>();
        cargarBaseDatos();
        setupListeners();
    }

    private void setupListeners() {
        view.setRecargarListener(this::procesarRecarga);
        view.setRegresarListener(this::regresarAUsuario);
    }

    private void procesarRecarga(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        String cedula = view.getCedula();
        String telefono = view.getTelefono();
        String banco = view.getBanco();
        double monto = view.getMonto();

        Estudiante estudiante = baseDatos.computeIfAbsent(cedula, 
            k -> new Estudiante(cedula, "Nombre no registrado", telefono, banco, 0.0, ""));

        estudiante.saldo += monto;
        saldo = estudiante.saldo;

        String transaccion = String.format("[%s] Recarga: %s - Banco: %s%n",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")),
            formato.format(monto),
            banco
        );
        estudiante.historial += transaccion;

        actualizarVista(transaccion);
        guardarBaseDatos();
    }

    private void actualizarVista(String transaccion) {
        view.actualizarSaldo(formato.format(saldo));
        view.agregarTransaccion(transaccion);
        view.limpiarCampos();
        view.mostrarMensaje("¡Recarga exitosa!", COLOR_EXITO);
    }

    private boolean validarCampos() {
        if (!validarCedula()) return false;
        if (!validarTelefono()) return false;
        if (!validarBanco()) return false;
        return validarMonto();
    }

    private boolean validarCedula() {
        String cedula = view.getCedula();
        if (cedula == null || cedula.trim().isEmpty() || !cedula.matches("\\d{7,8}")) {
            view.mostrarError("La cédula debe tener 7 u 8 dígitos");
            return false;
        }
        return true;
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
        File archivo = new File("monedero.txt");
        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.lines().forEach(linea -> {
                String[] partes = linea.split(",", 6);
                if (partes.length == 6) {
                    try {
                        Estudiante e = new Estudiante(
                            partes[0], partes[1], partes[2], partes[3],
                            Double.parseDouble(partes[4]), partes[5]
                        );
                        baseDatos.put(e.cedula, e);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parseando línea: " + linea);
                    }
                }
            });
        } catch (IOException e) {
            System.err.println("Error al cargar base de datos: " + e.getMessage());
        }
    }

    private void guardarBaseDatos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("monedero.txt"))) {
            baseDatos.values().forEach(pw::println);
        } catch (IOException e) {
            System.err.println("Error al guardar base de datos: " + e.getMessage());
            JOptionPane.showMessageDialog(view, 
                "Error al guardar los datos", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void regresarAUsuario(java.awt.event.ActionEvent e) {
        view.cerrarVentana();
        SwingUtilities.invokeLater(() -> {
            Op_Usuario opUsuario = new Op_Usuario();
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
            this.historial = historial;
        }

        @Override
        public String toString() {
            return String.join(",", cedula, nombre, telefono, banco, 
                             String.valueOf(saldo), historial);
        }
    }
}
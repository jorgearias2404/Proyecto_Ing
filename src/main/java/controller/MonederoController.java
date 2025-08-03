package controller;

import Views.MonederoEstudiantil;
import Views.Op_Usuario;
import javax.swing.*;
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

    String banco = view.getBanco();
    double monto = view.getMonto();
    
    Estudiante estudiante = baseDatos.computeIfAbsent(usuarioActual, 
        k -> new Estudiante(usuarioActual, "Estudiante", "", "", 0.0, ""));
    
    estudiante.saldo += monto;
    saldo = estudiante.saldo;
    
    // Crear línea de transacción en formato exacto
    String transaccion = String.format("%s,%.1f,[%s],Recarga: $%.1f,Banco: %s",
        usuarioActual,
        saldo,
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")),
        monto,
        banco
    );
    
    // Agregar al historial (nueva línea)
    estudiante.historial = (estudiante.historial != null ? estudiante.historial + "\n" : "") + transaccion;
    
    // Actualizar vista
    view.actualizarSaldo(String.format("$%.1f", saldo));
    view.agregarTransaccion(transaccion); // Enviamos la línea completa ya formateada
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
    
    try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
        StringBuilder historial = new StringBuilder();
        String linea;
        double ultimoSaldo = 0;
        
        while ((linea = br.readLine()) != null) {
            if (!linea.trim().isEmpty()) {
                historial.append(linea).append("\n");
                // Extraer el último saldo
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    ultimoSaldo = Double.parseDouble(partes[1]);
                }
            }
        }
        
        // Crear objeto Estudiante con el historial completo
        Estudiante e = new Estudiante(
            usuarioActual, 
            "Estudiante", 
            "", 
            "", 
            ultimoSaldo, 
            historial.toString()
        );
        baseDatos.put(e.cedula, e);
        
    } catch (IOException e) {
        System.err.println("Error al cargar datos: " + e.getMessage());
        baseDatos.put(usuarioActual, new Estudiante(usuarioActual, "Estudiante", "", "", 0.0, ""));
    }
}

    private void guardarBaseDatos() {
    String nombreArchivo = "DataBase/monedero_" + usuarioActual + ".txt";
    
    try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
        Estudiante estudiante = baseDatos.get(usuarioActual);
        if (estudiante != null && estudiante.historial != null) {
            pw.print(estudiante.historial);
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
        if (historial == null || historial.isEmpty()) {
            return String.join(",",
                cedula,
                String.valueOf(saldo),
                "[]",
                "Recarga: $0.0",
                "Banco: "
            );
        }
        
        StringBuilder sb = new StringBuilder();
        String[] transacciones = historial.split("\n");
        
        // Cada transacción genera una línea completa
        for (String trans : transacciones) {
            if (!trans.trim().isEmpty()) {
                String[] partes = parseTransaccion(trans);
                if (partes != null) {
                    sb.append(String.join(",",
                        cedula,
                        String.valueOf(saldo),
                        "[" + partes[0] + "]",
                        "Recarga: $" + partes[2],
                        "Banco: " + partes[3]
                    )).append("\n");
                }
            }
        }
        
        return sb.toString().trim(); // Eliminar el último \n
    }
    
    private String[] parseTransaccion(String transaccion) {
        try {
            String fecha = transaccion.substring(
                transaccion.indexOf("[") + 1, 
                transaccion.indexOf("]"));
            
            String montoStr = transaccion.substring(
                transaccion.indexOf("$") + 1, 
                transaccion.indexOf(" - Banco:"));
            
            String banco = transaccion.substring(
                transaccion.indexOf("Banco: ") + 7)
                .replace("#", "").trim();
            
            return new String[]{fecha, "Recarga", montoStr, banco};
        } catch (Exception e) {
            System.err.println("Error parseando transacción: " + transaccion);
            return null;
        }
    }
  }
}
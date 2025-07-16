package Views;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MonederoEstudiantil extends JFrame {

    // colores de la aplicacion
     Color COLOR_FONDO = new Color(7, 64, 91);
     Color COLOR_TEXTO = new Color(240, 240, 240);
     Color COLOR_BOTON = new Color(151, 188, 199);
     Color COLOR_EXITO = new Color(46, 204, 113);
     Color COLOR_ERROR = new Color(231, 76, 60);
     Color COLOR_PANEL = new Color(44, 62, 80);
     Color COLOR_CAMPOS = new Color(200, 220, 230);
     Color COLOR_ETIQUETAS = new Color(180, 220, 240);

    // componentes
    private JTextField campoCedula,campoTelefono, campoMonto;
    private JComboBox<String> comboBanco;
    private JLabel labelSaldo, labelMensaje;
    private JTextArea areaHistorial;
    private double saldo = 0.0;
    private DecimalFormat formato = new DecimalFormat("$#,##0.00");
    private Map<String, Estudiante> baseDatos = new HashMap<>();
 
    public MonederoEstudiantil() {
        configurarVentana();
        crearComponentes();
        configurarLayout();
        cargarBaseDatos();
    }

    private void configurarVentana() {
        setTitle("Monedero Estudiantil");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
    }

    private void crearComponentes() {
        // campos de textos
        campoCedula = crearCampoTexto("Ej: 28345678");
        campoTelefono = crearCampoTexto("Ej: 04121234567");
        campoMonto = crearCampoTexto("Ej: 100.00");

        // selector de banco
        String[] bancos = {"Seleccionar banco", "Banco de Venezuela", "Banesco", "Mercantil", "BBVA Provincial"};
        comboBanco = new JComboBox<>(bancos);
        comboBanco.setBackground(COLOR_CAMPOS);
        comboBanco.setForeground(Color.BLACK);
        comboBanco.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBanco.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BOTON, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        // etiquetas
        labelSaldo = new JLabel("Saldo: " + formato.format(saldo));
        labelSaldo.setFont(new Font("Arial", Font.BOLD, 16));
        labelSaldo.setForeground(COLOR_TEXTO);

        labelMensaje = new JLabel("Complete los datos para recargar");
        labelMensaje.setFont(new Font("Arial", Font.ITALIC, 12));
        labelMensaje.setForeground(COLOR_TEXTO);

        // area de historial
        areaHistorial = new JTextArea(8, 20);
        areaHistorial.setBackground(COLOR_PANEL);
        areaHistorial.setForeground(COLOR_TEXTO);
        areaHistorial.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaHistorial.setEditable(false);
        areaHistorial.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField(12);
        campo.setBackground(COLOR_CAMPOS);
        campo.setForeground(Color.BLACK);
        campo.setFont(new Font("Arial", Font.PLAIN, 12));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BOTON, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        campo.setToolTipText(placeholder);
        return campo;
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        // panel superior título y saldo
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelSuperior.setBackground(COLOR_FONDO);
        
        JLabel titulo = new JLabel(" MONEDERO ESTUDIANTIL ");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(COLOR_TEXTO);
        panelSuperior.add(titulo);
        panelSuperior.add(labelSaldo);

        // panel central formulario
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_PANEL);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // etiquetas y campos
        Font fontEtiquetas = new Font("Arial", Font.BOLD, 12);

        gbc.gridx = 0; gbc.gridy = 0;
        panelCentral.add(crearEtiqueta("Cédula:", fontEtiquetas, COLOR_ETIQUETAS), gbc);
        gbc.gridx = 1;
        panelCentral.add(campoCedula, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelCentral.add(crearEtiqueta("Banco:", fontEtiquetas, COLOR_ETIQUETAS), gbc);
        gbc.gridx = 1;
        panelCentral.add(comboBanco, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelCentral.add(crearEtiqueta("Teléfono:", fontEtiquetas, COLOR_ETIQUETAS), gbc);
        gbc.gridx = 1;
        panelCentral.add(campoTelefono, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelCentral.add(crearEtiqueta("Monto (Bs):", fontEtiquetas, COLOR_ETIQUETAS), gbc);
        gbc.gridx = 1;
        panelCentral.add(campoMonto, gbc);

        // boton de recarga
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton botonRecargar = new JButton("Recargar Saldo");
        botonRecargar.setBackground(COLOR_BOTON);
        botonRecargar.setForeground(COLOR_FONDO);
        botonRecargar.setFont(new Font("Arial", Font.BOLD, 12));
        botonRecargar.addActionListener(e -> procesarRecarga());
        panelCentral.add(botonRecargar, gbc);
          
        // boton de regresar
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.setBackground(COLOR_BOTON);
        botonRegresar.setForeground(COLOR_FONDO);
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 12));
        botonRegresar.addActionListener(e -> regresarUsuario());
        panelCentral.add(botonRegresar, gbc);

        // panel del historial
        JScrollPane scrollHistorial = new JScrollPane(areaHistorial);
        scrollHistorial.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BOTON, 1), 
            "Historial de Transacciones",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            COLOR_TEXTO
        ));

        // organizacion final
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.WEST);
        add(scrollHistorial, BorderLayout.CENTER);
        add(labelMensaje, BorderLayout.SOUTH);
    }

    private JLabel crearEtiqueta(String texto, Font font, Color color) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(font);
        etiqueta.setForeground(color);
        return etiqueta;
    }

    private void procesarRecarga() {
    if (!validarCampos()) return;

    String cedula = campoCedula.getText().trim();
    String telefono = campoTelefono.getText().trim();
    String banco = (String) comboBanco.getSelectedItem();
    double monto = Double.parseDouble(campoMonto.getText().trim());

    // verificar si el estudiante ya existe o crear uno nuevo
    Estudiante estudiante = baseDatos.get(cedula);
    if (estudiante == null) {
        estudiante = new Estudiante(cedula, "Nombre no registrado", telefono, banco, 0.0, "");
        baseDatos.put(cedula, estudiante);
    }

    // actualizar saldo
    estudiante.saldo += monto;
    saldo = estudiante.saldo; // actualiza el saldo mostrado
    
    // registrar en historial
    String transaccion = String.format("[%s] Recarga: %s - Banco: %s\n",
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")),
        formato.format(monto),
        banco
    );
    estudiante.historial += transaccion;
    areaHistorial.append(transaccion);

    // actualizar interfaz
    labelSaldo.setText("Saldo: " + formato.format(saldo));
    limpiarCampos();
    labelMensaje.setText("¡Recarga exitosa!");
    labelMensaje.setForeground(COLOR_EXITO);
    
    guardarBaseDatos();
}

     private boolean validarCampos() {

    // validar cedula 
    String cedula = campoCedula.getText().trim();
    if (cedula.isEmpty()) {
        mostrarError("Ingrese la cédula");
        campoCedula.requestFocus();
        return false;
    }
    if (!cedula.matches("\\d+")) {
        mostrarError("La cédula solo puede contener números");
        campoCedula.requestFocus();
        return false;
    }
    if (cedula.length() < 7 || cedula.length() > 8) {
        mostrarError("La cédula debe tener 7 u 8 dígitos");
        campoCedula.requestFocus();
        return false;
    }

    // validar telefono 
    String telefono = campoTelefono.getText().trim();
    if (telefono.isEmpty()) {
        mostrarError("Ingrese el teléfono");
        campoTelefono.requestFocus();
        return false;
    }
    if (!telefono.matches("\\d+")) {
        mostrarError("El teléfono solo puede contener números");
        campoTelefono.requestFocus();
        return false;
    }
    if (telefono.length() != 11) {
        mostrarError("El teléfono debe tener 11 dígitos (ej: 04121234567)");
        campoTelefono.requestFocus();
        return false;
    }

    // validar banco 
    if (comboBanco.getSelectedIndex() == 0) {
        mostrarError("Seleccione un banco");
        comboBanco.requestFocus();
        return false;
    }

    // validar monto 
    try {
        double monto = Double.parseDouble(campoMonto.getText().trim());
        if (monto <= 0) {
            mostrarError("El monto debe ser mayor a cero");
            campoMonto.requestFocus();
            return false;
        }
    } catch (NumberFormatException e) {
        mostrarError("Ingrese un monto válido (ej: 100.50)");
        campoMonto.requestFocus();
        return false;
    }

    return true;
}

private void mostrarError(String mensaje) {
    labelMensaje.setText("Error: " + mensaje);
    labelMensaje.setForeground(COLOR_ERROR);
}
    private void limpiarCampos() {
        campoCedula.setText("");
        comboBanco.setSelectedIndex(0);
        campoTelefono.setText("");
        campoMonto.setText("");
    }

private void cargarBaseDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader("monedero"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",", 6);
                if (partes.length == 6) {
                    Estudiante e = new Estudiante(partes[0], partes[1], partes[2], partes[3],
                            Double.parseDouble(partes[4]), partes[5]);
                    baseDatos.put(e.cedula, e);
                }
            }
        } catch (IOException e) {
            System.out.println(" ");
        }
    }
  
    private void guardarBaseDatos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("monedero.txt"))) {
            for (Estudiante e : baseDatos.values()) {
                pw.println(e.toString());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar base de datos.");
        }
    }

    class Estudiante {
        String cedula, nombre, telefono, banco, historial;
        double saldo;

        Estudiante(String cedula, String nombre, String telefono, String banco, double saldo, String historial) {
            this.cedula = cedula;
            this.nombre = nombre;
            this.telefono = telefono;
            this.banco = banco;
            this.saldo = saldo;
            this.historial = historial;
        }

        @Override
        public String toString() {
            return String.join(",", cedula, nombre, telefono, banco, String.valueOf(saldo), historial);
        }
    }

    // metedo para regresar a la ventana anterior
   private void regresarUsuario() {
    this.dispose(); // cierra la ventana actual
    SwingUtilities.invokeLater(() -> {
        Op_Usuario opUsuario = new Op_Usuario();
        opUsuario.setVisible(true);
    });
 }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MonederoEstudiantil app = new MonederoEstudiantil();
            app.setVisible(true);
        });
    }
}
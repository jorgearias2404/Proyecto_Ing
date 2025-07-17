package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MonederoEstudiantil extends JFrame {
    // Componentes UI
    private JTextField campoCedula, campoTelefono, campoMonto;
    private JComboBox<String> comboBanco;
    private JLabel labelSaldo, labelMensaje;
    private JTextArea areaHistorial;
    
    // Listeners
    private ActionListener recargarListener;
    private ActionListener regresarListener;
    
    // Colores
    private final Color COLOR_FONDO = new Color(7, 64, 91);
    private final Color COLOR_TEXTO = new Color(240, 240, 240);
    private final Color COLOR_BOTON = new Color(151, 188, 199);
    private final Color COLOR_PANEL = new Color(44, 62, 80);
    private final Color COLOR_CAMPOS = new Color(200, 220, 230);
    private final Color COLOR_ETIQUETAS = new Color(180, 220, 240);
    private final Color COLOR_ERROR = new Color(231, 76, 60);

    public MonederoEstudiantil() {
        configurarVentana();
        crearComponentes();
        configurarLayout();
    }

    private void configurarVentana() {
        setTitle("Monedero Estudiantil");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
    }

    private void crearComponentes() {
        // Campos de texto
        campoCedula = crearCampoTexto("Ej: 28345678");
        campoTelefono = crearCampoTexto("Ej: 04121234567");
        campoMonto = crearCampoTexto("Ej: 100.00");

        // Selector de banco
        String[] bancos = {"Seleccionar banco", "Banco de Venezuela", "Banesco", "Mercantil", "BBVA Provincial"};
        comboBanco = new JComboBox<>(bancos);
        estiloCombo(comboBanco);

        // Etiquetas
        labelSaldo = new JLabel("Saldo: $0.00");
        labelSaldo.setFont(new Font("Arial", Font.BOLD, 16));
        labelSaldo.setForeground(COLOR_TEXTO);

        labelMensaje = new JLabel("Complete los datos para recargar");
        labelMensaje.setFont(new Font("Arial", Font.ITALIC, 12));
        labelMensaje.setForeground(COLOR_TEXTO);

        // Área de historial
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

    private void estiloCombo(JComboBox<String> combo) {
        combo.setBackground(COLOR_CAMPOS);
        combo.setForeground(Color.BLACK);
        combo.setFont(new Font("Arial", Font.PLAIN, 12));
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BOTON, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior (título y saldo)
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelSuperior.setBackground(COLOR_FONDO);
        
        JLabel titulo = new JLabel(" MONEDERO ESTUDIANTIL ");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(COLOR_TEXTO);
        panelSuperior.add(titulo);
        panelSuperior.add(labelSaldo);

        // Panel central (formulario)
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_PANEL);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Añadir componentes al panel central
        agregarComponente(panelCentral, gbc, 0, 0, "Cédula:", campoCedula);
        agregarComponente(panelCentral, gbc, 0, 1, "Banco:", comboBanco);
        agregarComponente(panelCentral, gbc, 0, 2, "Teléfono:", campoTelefono);
        agregarComponente(panelCentral, gbc, 0, 3, "Monto (Bs):", campoMonto);

        // Botón Recargar
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton botonRecargar = new JButton("Recargar Saldo");
        estiloBoton(botonRecargar);
        botonRecargar.addActionListener(e -> {
            if (recargarListener != null) recargarListener.actionPerformed(e);
        });
        panelCentral.add(botonRecargar, gbc);
          
        // Botón Regresar
        gbc.gridy = 5;
        JButton botonRegresar = new JButton("Regresar");
        estiloBoton(botonRegresar);
        botonRegresar.addActionListener(e -> {
            if (regresarListener != null) regresarListener.actionPerformed(e);
        });
        panelCentral.add(botonRegresar, gbc);

        // Panel del historial
        JScrollPane scrollHistorial = new JScrollPane(areaHistorial);
        scrollHistorial.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BOTON, 1), 
            "Historial de Transacciones",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            COLOR_TEXTO
        ));

        // Organización final
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.WEST);
        add(scrollHistorial, BorderLayout.CENTER);
        add(labelMensaje, BorderLayout.SOUTH);
    }

    private void agregarComponente(JPanel panel, GridBagConstraints gbc, int x, int y, String texto, JComponent componente) {
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(crearEtiqueta(texto), gbc);
        gbc.gridx = 1;
        panel.add(componente, gbc);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 12));
        etiqueta.setForeground(COLOR_ETIQUETAS);
        return etiqueta;
    }

    private void estiloBoton(JButton boton) {
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(COLOR_FONDO);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setFocusPainted(false);
    }

    // Métodos para el controlador
    public void setRecargarListener(ActionListener listener) {
        this.recargarListener = listener;
    }
    
    public void setRegresarListener(ActionListener listener) {
        this.regresarListener = listener;
    }
    
    public String getCedula() {
        return campoCedula.getText().trim();
    }
    
    public String getTelefono() {
        return campoTelefono.getText().trim();
    }
    
    public String getBanco() {
        return (String) comboBanco.getSelectedItem();
    }
    
    public double getMonto() {
        try {
            return Double.parseDouble(campoMonto.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public void actualizarSaldo(String saldoFormateado) {
        labelSaldo.setText("Saldo: " + saldoFormateado);
    }
    
    public void agregarTransaccion(String transaccion) {
        areaHistorial.append(transaccion);
    }
    
    public void limpiarCampos() {
        campoCedula.setText("");
        comboBanco.setSelectedIndex(0);
        campoTelefono.setText("");
        campoMonto.setText("");
    }
    
    public void mostrarMensaje(String mensaje, Color color) {
        labelMensaje.setText(mensaje);
        labelMensaje.setForeground(color);
    }
    
    public void mostrarError(String mensaje) {
        mostrarMensaje("Error: " + mensaje, COLOR_ERROR);
    }
    
    public void cerrarVentana() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MonederoEstudiantil view = new MonederoEstudiantil();
            new controller.MonederoController(view);
            view.setVisible(true);
        });
    }
}
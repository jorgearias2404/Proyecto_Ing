package Views;

import javax.swing.*;
import controller.MonederoController;
import java.awt.*;
import java.awt.event.ActionListener;

public class MonederoEstudiantil extends JFrame {
    private JTextField campoTelefono;
    private JComboBox<String> comboBanco;
    private JTextField campoMonto;
    private JTextArea areaHistorial;
    private JLabel labelSaldo;
    private ActionListener recargarListener;
    private ActionListener regresarListener;

    // Colores
    private final Color COLOR_FONDO = new Color(7, 64, 91);
    private final Color COLOR_TEXTO = new Color(240, 240, 240);
    private final Color COLOR_BOTON = new Color(151, 188, 199);
    private final Color COLOR_PANEL = new Color(44, 62, 80);
    private final Color COLOR_CAMPOS = new Color(200, 220, 230);
    private final Color COLOR_ETIQUETAS = new Color(180, 220, 240);
   

    public MonederoEstudiantil(String usuario) {
        setTitle("Monedero Estudiantil - " + usuario);
        configurarVentana();
        crearComponentes();
        configurarLayout();
    }

    private void configurarVentana() {
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
    }

    private void crearComponentes() {
        campoTelefono = crearCampoTexto("Ej: 04121234567");
        campoMonto = crearCampoTexto("Ej: 100.00");

        String[] bancos = {"Seleccionar banco", "Banco Nacional", "Banco Provincial", "Otro Banco"};
        comboBanco = new JComboBox<>(bancos);
        estiloCombo(comboBanco);

        labelSaldo = new JLabel("Saldo: $0.00");
        labelSaldo.setFont(new Font("Arial", Font.BOLD, 16));
        labelSaldo.setForeground(COLOR_TEXTO);

        areaHistorial = new JTextArea(8, 20);
        areaHistorial.setEditable(false);
        areaHistorial.setBackground(COLOR_PANEL);
        areaHistorial.setForeground(COLOR_TEXTO);
    }

    private JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField(15);
        campo.setBackground(COLOR_CAMPOS);
        campo.setForeground(Color.BLACK);
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
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BOTON, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(COLOR_FONDO);
        panelSuperior.add(labelSaldo);

        // Panel central
        JPanel panelCentral = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCentral.setBackground(COLOR_PANEL);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelCentral.add(crearEtiqueta("Teléfono:"));
        panelCentral.add(campoTelefono);
        panelCentral.add(crearEtiqueta("Banco:"));
        panelCentral.add(comboBanco);
        panelCentral.add(crearEtiqueta("Monto:"));
        panelCentral.add(campoMonto);

        JButton btnRecargar = new JButton("Recargar");
        estiloBoton(btnRecargar);
        btnRecargar.addActionListener(e -> {
            if (recargarListener != null) recargarListener.actionPerformed(e);
        });
        panelCentral.add(btnRecargar);

        JButton btnRegresar = new JButton("Regresar");
        estiloBoton(btnRegresar);
        btnRegresar.addActionListener(e -> {
            if (regresarListener != null) regresarListener.actionPerformed(e);
        });
        panelCentral.add(btnRegresar);

        // Panel historial
        JScrollPane scrollHistorial = new JScrollPane(areaHistorial);
        scrollHistorial.setBorder(BorderFactory.createTitledBorder("Historial"));

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(scrollHistorial, BorderLayout.SOUTH);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(COLOR_ETIQUETAS);
        return etiqueta;
    }

    private void estiloBoton(JButton boton) {
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(COLOR_FONDO);
        boton.setFocusPainted(false);
    }

    // Métodos para el controlador
    public void setRecargarListener(ActionListener listener) {
        this.recargarListener = listener;
    }
    
    public void setRegresarListener(ActionListener listener) {
        this.regresarListener = listener;
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
         if (transaccion != null && !transaccion.isEmpty()) {
        if (!transaccion.endsWith("\n ")) {
            transaccion += "\n ";
        }
        areaHistorial.append(transaccion);
        areaHistorial.setCaretPosition(areaHistorial.getDocument().getLength());
     }
    }
    
    public void mostrarHistorial(String historial) {
    // Limpiar y mostrar todo el historial
    areaHistorial.setText("");
    if (historial != null && !historial.trim().isEmpty()) {
        // Reemplazar cualquier # residual
        areaHistorial.setText(historial.replace("#", ""));
      }
   }
    
    public void limpiarCampos() {
        campoTelefono.setText("");
        comboBanco.setSelectedIndex(0);
        campoMonto.setText("");
    }
    
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void cerrarVentana() {
        dispose();
    }
    
public void setController(MonederoController controller) {
    this.recargarListener = e -> controller.procesarRecarga(e);  
   this.regresarListener = e -> {
        controller.regresarAUsuario(e);
        this.cerrarVentana();
    };
}
}
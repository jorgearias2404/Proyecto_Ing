package Views;
import java.awt.Color;  
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class CalculadoraCCB extends JFrame {

    private JTextField txtCostosFijos, txtCostosVariables, txtBandejas, txtMerma;
    private JLabel lblResultado;

    public CalculadoraCCB() {
        initComponents();
    }

    private void initComponents(){
        // Configuración de la ventana
        setTitle("Calculadora CCB");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        Color COLOR_FONDO = new Color(7, 64, 91);
        Color COLOR_TEXTO = new Color(240, 240, 240);
        Color COLOR_BOTON = new Color(151, 188, 199);
        Color COLOR_ETIQUETAS = new Color(180, 220, 240);
        getContentPane().setBackground(COLOR_FONDO);


        // Componentes del formulario
        JLabel lblCostosFijos = new JLabel("Costos Fijos:");
        lblCostosFijos.setBounds(30, 30, 120, 20);
        add(lblCostosFijos);
        lblCostosFijos.setForeground(COLOR_TEXTO);

        txtCostosFijos = new JTextField();
        txtCostosFijos.setBounds(160, 30, 200, 25);
        add(txtCostosFijos);
        txtCostosFijos.setBackground(COLOR_ETIQUETAS);

        JLabel lblCostosVariables = new JLabel("Costos Variables:");
        lblCostosVariables.setBounds(30, 70, 120, 20);
        add(lblCostosVariables);
        lblCostosVariables.setForeground(COLOR_TEXTO);

        txtCostosVariables = new JTextField();
        txtCostosVariables.setBounds(160, 70, 200, 25);
        add(txtCostosVariables);
        txtCostosVariables.setBackground(COLOR_ETIQUETAS);

        JLabel lblBandejas = new JLabel("Número Bandejas:");
        lblBandejas.setBounds(30, 110, 120, 20);
        add(lblBandejas);
        lblBandejas.setForeground(COLOR_TEXTO);

        txtBandejas = new JTextField();
        txtBandejas.setBounds(160, 110, 200, 25);
        add(txtBandejas);
        txtBandejas.setBackground(COLOR_ETIQUETAS);

        JLabel lblMerma = new JLabel("Merma (%):");
        lblMerma.setBounds(30, 150, 120, 20);
        add(lblMerma);
        lblMerma.setForeground(COLOR_TEXTO);

        txtMerma = new JTextField();
        txtMerma.setBounds(160, 150, 200, 25);
        add(txtMerma);
        txtMerma.setBackground(COLOR_ETIQUETAS);

        // Botón de cálculo
        JButton btnCalcular = new JButton("Calcular CCB");
        btnCalcular.setBounds(120, 190, 150, 30);
        add(btnCalcular);
        btnCalcular.setBackground(COLOR_BOTON);
        btnCalcular.setForeground(COLOR_FONDO);

        // Etiqueta para mostrar el resultado
        lblResultado = new JLabel("CCB: ");
        lblResultado.setBounds(30, 230, 300, 30);
        add(lblResultado);
        lblResultado.setForeground(COLOR_TEXTO);

        // Evento del botón
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularCCB();
            }
        });
    }

    private void calcularCCB() {
        try {
            // Obtener valores de los campos
            double costosFijos = Double.parseDouble(txtCostosFijos.getText());
            double costosVariables = Double.parseDouble(txtCostosVariables.getText());
            double bandejas = Double.parseDouble(txtBandejas.getText());
            double merma = Double.parseDouble(txtMerma.getText()) / 100; // Convertir % a decimal

            // Calcular CCB según la fórmula
            double ccb = ((costosFijos + costosVariables) / bandejas) * (1 + merma);

            // Formatear el resultado a 2 decimales
            DecimalFormat df = new DecimalFormat("#.##");
            lblResultado.setText("CCB: " + df.format(ccb));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "¡Error! Ingresa valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraCCB ventana = new CalculadoraCCB();
            ventana.setVisible(true);
        });
    }
}
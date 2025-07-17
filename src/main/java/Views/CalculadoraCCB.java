package Views;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        // Ventana
        setTitle("Calculadora CCB");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        Color COLOR_FONDO = new Color(7, 64, 91);
        Color COLOR_TEXTO = new Color(240, 240, 240);
        Color COLOR_BOTON = new Color(151, 188, 199);
        Color COLOR_ETIQUETAS = new Color(180, 220, 240);
        getContentPane().setBackground(COLOR_FONDO);


        // Formulario
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

        // Boton
        JButton btnCalcular = new JButton("Calcular CCB");
        btnCalcular.setBounds(120, 190, 150, 30);
        add(btnCalcular);
        btnCalcular.setBackground(COLOR_BOTON);
        btnCalcular.setForeground(COLOR_FONDO);

        // Resultado
        lblResultado = new JLabel("CCB: ");
        lblResultado.setBounds(30, 230, 300, 30);
        add(lblResultado);
        lblResultado.setForeground(COLOR_TEXTO);

        // Boton
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularCCB();
            }
        });
    }

    private void calcularCCB() {
        try {
            // Obtener Valores
            double costosFijos = Double.parseDouble(txtCostosFijos.getText());
            double costosVariables = Double.parseDouble(txtCostosVariables.getText());
            double bandejas = Double.parseDouble(txtBandejas.getText());
            double merma = Double.parseDouble(txtMerma.getText()) / 100; // Convertir % a decimal

            // Calcular CCB 
            double ccb = ((costosFijos + costosVariables) / bandejas) * (1 + merma);

            // Acomodar resultado a 2 decimales
            DecimalFormat df = new DecimalFormat("#.##");
            lblResultado.setText("CCB: " + df.format(ccb));

            guardarEnArchivo(costosFijos, costosVariables, bandejas, merma, ccb);

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

    private void guardarEnArchivo(double costosFijos, double costosVariables, double bandejas, double merma, double ccb) {
        // Ruta de DataBase
        String directorio = "src\\DataBase";

        // Ruta del .txt
        String rutaArchivo = directorio + "/datos_ccb.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            // Fecha y hora actual
            String fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        
            // Escribir los datos 
            writer.write("\n--- Registro [" + fechaHora + "] ---");
            writer.write("\nCostos Fijos: " + costosFijos);
            writer.write("\nCostos Variables: " + costosVariables);
            writer.write("\nBandejas: " + bandejas);
            writer.write("\nMerma (%): " + (merma * 100));  // Mostrar como porcentaje
            writer.write("\nCCB calculado: " + new DecimalFormat("#.##").format(ccb));
            writer.write("\n----------------------------\n");
        
            JOptionPane.showMessageDialog(this, "Datos guardados en: " + rutaArchivo);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}
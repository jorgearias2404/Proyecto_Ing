package Views;

import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

public class CargaCCB extends JFrame {
    private JTextField txtCostosFijos, txtCostosVariables, txtBandejas, txtMerma;
    private JLabel lblResultado;
    private Op_Admin opAdmin; // Referencia a la ventana de administrador

    public CargaCCB(Op_Admin opAdmin) {
        this.opAdmin = opAdmin;
        initComponents();
    }

    private void initComponents() {
        // Configuración de la ventana
        setTitle("Calculadora CCB");
        setSize(400, 325);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        Color COLOR_FONDO = new Color(7, 64, 91);
        Color COLOR_TEXTO = new Color(240, 240, 240);
        Color COLOR_BOTON = new Color(151, 188, 199);
        Color COLOR_ETIQUETAS = new Color(180, 220, 240);
        getContentPane().setBackground(COLOR_FONDO);

        // Componentes del formulario
        JLabel lblCostosFijos = new JLabel("Costos Fijos:");
        lblCostosFijos.setBounds(30, 30, 120, 20);
        lblCostosFijos.setForeground(COLOR_TEXTO);
        add(lblCostosFijos);

        txtCostosFijos = new JTextField();
        txtCostosFijos.setBounds(160, 30, 200, 25);
        txtCostosFijos.setBackground(COLOR_ETIQUETAS);
        add(txtCostosFijos);

        JLabel lblCostosVariables = new JLabel("Costos Variables:");
        lblCostosVariables.setBounds(30, 70, 120, 20);
        lblCostosVariables.setForeground(COLOR_TEXTO);
        add(lblCostosVariables);

        txtCostosVariables = new JTextField();
        txtCostosVariables.setBounds(160, 70, 200, 25);
        txtCostosVariables.setBackground(COLOR_ETIQUETAS);
        add(txtCostosVariables);

        JLabel lblBandejas = new JLabel("Número Bandejas:");
        lblBandejas.setBounds(30, 110, 120, 20);
        lblBandejas.setForeground(COLOR_TEXTO);
        add(lblBandejas);

        txtBandejas = new JTextField();
        txtBandejas.setBounds(160, 110, 200, 25);
        txtBandejas.setBackground(COLOR_ETIQUETAS);
        add(txtBandejas);

        JLabel lblMerma = new JLabel("Merma (%):");
        lblMerma.setBounds(30, 150, 120, 20);
        lblMerma.setForeground(COLOR_TEXTO);
        add(lblMerma);

        txtMerma = new JTextField();
        txtMerma.setBounds(160, 150, 200, 25);
        txtMerma.setBackground(COLOR_ETIQUETAS);
        add(txtMerma);

        // Botones
        JButton btnCalcular = new JButton("Calcular CCB");
        btnCalcular.setBounds(120, 190, 150, 30);
        btnCalcular.setBackground(COLOR_BOTON);
        btnCalcular.setForeground(COLOR_FONDO);
        add(btnCalcular);

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(120, 230, 150, 30);
        btnRegresar.setBackground(COLOR_BOTON);
        btnRegresar.setForeground(COLOR_FONDO);
        add(btnRegresar);

        // Etiqueta para el resultado
        lblResultado = new JLabel("CCB: ");
        lblResultado.setBounds(30, 250, 300, 30);
        lblResultado.setForeground(COLOR_TEXTO);
        add(lblResultado);

        // Eventos
        btnRegresar.addActionListener(e -> {
            this.dispose();
            opAdmin.setVisible(true);
        });

        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String costosFijos = txtCostosFijos.getText();
                    String costosVariables = txtCostosVariables.getText();
                    String bandejas = txtBandejas.getText();
                    String merma = txtMerma.getText();

                    double ccb = controller.CalculadoraCCB.calcularCCB(costosFijos, costosVariables, bandejas, merma);
                
                    if (ccb == 0) {
                        lblResultado.setText("Número inválido");
                        return;
                    }

                    DecimalFormat df = new DecimalFormat("#.##");
                    String ccbFormateado = df.format(ccb);
                    lblResultado.setText("CCB: " + ccbFormateado);

                    guardarEnArchivo(costosFijos, costosVariables, bandejas, merma, ccbFormateado);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void guardarEnArchivo(String costosFijos, String costosVariables, String bandejas, String merma, String ccb) {
        try {
            String rutaBase = "Database";
            Files.createDirectories(Paths.get(rutaBase));
            String nombreArchivo = rutaBase + "/formulario_" + getWeekRange() + ".txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
                writer.write("= DATOS DEL FORMULARIO =\n");
                writer.write("Costos Fijos: " + costosFijos + "\n");
                writer.write("Costos Variables: " + costosVariables + "\n");
                writer.write("Bandejas: " + bandejas + "\n");
                writer.write("Merma (%): " + merma + "\n");
                writer.write("\n= RESULTADO CCB =\n" + ccb + "\n");
            }

            JOptionPane.showMessageDialog(null, "Archivo guardado en: " + nombreArchivo, "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static String getWeekRange() {
        LocalDate hoy = LocalDate.now();
        LocalDate domingo = hoy.with(DayOfWeek.SUNDAY);
        LocalDate sabado = domingo.plusDays(6);
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("ddMMyyyy");
        return domingo.format(formateador) + "_" + sabado.format(formateador);
    }
}
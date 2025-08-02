package Views;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;  
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JOptionPane;

public class CargaCCB extends JFrame {

    private JTextField txtCostosFijos, txtCostosVariables, txtBandejas, txtMerma;
    private JLabel lblResultado;
    private Op_Usuario opUsuario; // Referencia a la ventana de opciones de usuario

    public CargaCCB(Op_Usuario opUsuario) {
        this.opUsuario = opUsuario;
        initComponents();
    }

    private void initComponents(){
        // Ventana
        setTitle("Calculadora CCB");
        setSize(400, 325);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        // Boton regresar para volver a Op_Usuario
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(120, 230, 150, 30);
        add(btnRegresar);
        btnRegresar.setBackground(COLOR_BOTON);
        btnRegresar.setForeground(COLOR_FONDO);

        // Etiqueta para mostrar el resultado
        lblResultado = new JLabel("CCB: ");
        lblResultado.setBounds(30, 250, 300, 30);
        add(lblResultado);
        lblResultado.setForeground(COLOR_TEXTO);
        // Evento del boton regresar
        btnRegresar.addActionListener(e -> {
            this.dispose(); 
            opUsuario.setVisible(true); 
        });

        // Evento del botón
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener valores del formulario
                    String costosFijos = txtCostosFijos.getText();
                    String costosVariables = txtCostosVariables.getText();
                    String bandejas = txtBandejas.getText();
                    String merma = txtMerma.getText();

                    // Calcular CCB
                    double ccb = controller.CalculadoraCCB.calcularCCB(costosFijos, costosVariables, bandejas, merma);
                
                    if (ccb == 0) {
                        lblResultado.setText("Número inválido");
                        return;
                    }

                    // Formatear resultado
                    DecimalFormat df = new DecimalFormat("#.##");
                    String ccbFormateado = df.format(ccb);
                    lblResultado.setText("CCB: " + ccbFormateado);

                    // guardar archivo
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
            // Crea la carpeta "Database" si no existe
            Files.createDirectories(Paths.get(rutaBase));

            // Genera nombre del archivo con fecha/hora
            String nombreArchivo = rutaBase + "/formulario_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";

            // Escribe los datos en el archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
                writer.write("=== DATOS DEL FORMULARIO ===\n");
                writer.write("Costos Fijos: " + costosFijos + "\n");
                writer.write("Costos Variables: " + costosVariables + "\n");
                writer.write("Bandejas: " + bandejas + "\n");
                writer.write("Merma (%): " + merma + "\n");
                writer.write("\n=== RESULTADO CCB ===\n" + ccb + "\n");
            }

            // Muestra confirmación 
            JOptionPane.showMessageDialog(null, "Archivo guardado en: " + nombreArchivo, "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        Op_Usuario opUsuario = new Op_Usuario("UsuarioDemo", false); // Simulación de usuario actual
        CargaCCB ventana = new CargaCCB(opUsuario); // Pasa la referencia de Op_Usuario
        ventana.setVisible(true);
    });
  }
}
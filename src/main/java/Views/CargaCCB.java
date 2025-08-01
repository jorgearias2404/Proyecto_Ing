package Views;
import java.awt.Color;  
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class CargaCCB extends JFrame {

    private JTextField txtCostosFijos, txtCostosVariables, txtBandejas, txtMerma;
    private JLabel lblResultado;
    private Op_Usuario opUsuario; // Referencia a la ventana de opciones de usuario

    public CargaCCB(Op_Usuario opUsuario) {
        this.opUsuario = opUsuario;
        initComponents();
    }

    private void initComponents(){
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

        // Boton regresar para volver a Op_Usuario
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(120, 230, 150, 30);
        add(btnRegresar);
        btnRegresar.setBackground(COLOR_BOTON);
        btnRegresar.setForeground(COLOR_FONDO);

        // Etiqueta para mostrar el resultado
        lblResultado = new JLabel("CCB: ");
        lblResultado.setBounds(30, 230, 300, 30);
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
                double ccb = controller.CalculadoraCCB.calcularCCB(txtCostosFijos.getText(), txtCostosVariables.getText(), txtBandejas.getText(), txtMerma.getText());
                if(ccb == 0) {
                    lblResultado.setText("Numero invalido");
                    return;
                }


                // Formatear el resultado a 2 decimales
                DecimalFormat df = new DecimalFormat("#.##");
                lblResultado.setText("CCB: " + df.format(ccb));

            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        Op_Usuario opUsuario = new Op_Usuario("UsuarioDemo", false); // Simulación de usuario actual
        CargaCCB ventana = new CargaCCB(opUsuario); // Pasa la referencia de Op_Usuario
        ventana.setVisible(true);
    });
  }
}
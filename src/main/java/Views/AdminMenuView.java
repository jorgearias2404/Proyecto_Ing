package Views;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AdminMenuView extends JFrame {
    private JButton btnGuardar;
    private JButton btnAtras;
    private JComboBox<String> comboDias;
    private JTextField txtDesayuno;
    private JTextField txtAlmuerzo;
    private JTextField txtPostre;
    private JTextArea areaMenuExistente;
    private JScrollPane scrollPane;

    public AdminMenuView(String usuario) {
        setTitle("Administrar Menús - " + usuario);
        initComponents();
    }

    private void initComponents() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(7, 64, 91));

        JLabel titulo = new JLabel("Administración de Menús Semanales", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Panel principal dividido en formulario y vista de menú
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        // Panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBackground(new Color(151, 188, 199));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos del formulario
        panelFormulario.add(new JLabel("Día:"));
        comboDias = new JComboBox<>(new String[]{"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"});
        panelFormulario.add(comboDias);
        
        panelFormulario.add(new JLabel("Desayuno:"));
        txtDesayuno = new JTextField();
        panelFormulario.add(txtDesayuno);
        
        panelFormulario.add(new JLabel("Almuerzo:"));
        txtAlmuerzo = new JTextField();
        panelFormulario.add(txtAlmuerzo);
        
        panelFormulario.add(new JLabel("Postre:"));
        txtPostre = new JTextField();
        panelFormulario.add(txtPostre);

        // Panel de visualización del menú existente
        JPanel panelMenuExistente = new JPanel(new BorderLayout());
        panelMenuExistente.setBackground(new Color(151, 188, 199));
        panelMenuExistente.setBorder(BorderFactory.createTitledBorder("Menú Existente"));
        
        areaMenuExistente = new JTextArea();
        areaMenuExistente.setEditable(false);
        areaMenuExistente.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scrollPane = new JScrollPane(areaMenuExistente);
        panelMenuExistente.add(scrollPane, BorderLayout.CENTER);

        splitPane.setLeftComponent(panelFormulario);
        splitPane.setRightComponent(panelMenuExistente);
        panelPrincipal.add(splitPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(7, 64, 91));
        btnGuardar = new JButton("Guardar Cambios");
        btnAtras = new JButton("Volver");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnAtras);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
        setLocationRelativeTo(null);
    }

    public void mostrarMenuExistente(String contenido) {
        areaMenuExistente.setText(contenido);
        areaMenuExistente.setCaretPosition(0); // Mover al inicio
    }

    public void setGuardarListener(java.awt.event.ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void setAtrasListener(java.awt.event.ActionListener listener) {
        btnAtras.addActionListener(listener);
    }

    public Map<String, String> getDatosMenu() {
        Map<String, String> datos = new HashMap<>();
        datos.put("dia", comboDias.getSelectedItem().toString());
        datos.put("desayuno", txtDesayuno.getText());
        datos.put("almuerzo", txtAlmuerzo.getText());
        datos.put("postre", txtPostre.getText());
        return datos;
    }
}
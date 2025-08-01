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

    public AdminMenuView(String usuario) {
        setTitle("Administrar Menús - " + usuario);
        initComponents();
    }

    private void initComponents() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(7, 64, 91));

        JLabel titulo = new JLabel("Administración de Menús Semanales", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Aquí iría el formulario para editar los menús
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBackground(new Color(151, 188, 199));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Ejemplo de campos para editar menús
        panelFormulario.add(new JLabel("Día:"));
        comboDias = new JComboBox<>(new String[]{"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"});
        panelFormulario.add(comboDias);
        txtDesayuno = new JTextField();
        txtAlmuerzo = new JTextField();
        txtPostre = new JTextField();

        panelFormulario.add(new JLabel("Desayuno:"));
        panelFormulario.add(txtDesayuno);
        panelFormulario.add(new JLabel("Almuerzo:"));
        panelFormulario.add(txtAlmuerzo);
        panelFormulario.add(new JLabel("Postre:"));
        panelFormulario.add(txtPostre);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(7, 64, 91));
        btnGuardar = new JButton("Guardar Cambios");
        btnAtras = new JButton("Volver");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnAtras);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
        setLocationRelativeTo(null);
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
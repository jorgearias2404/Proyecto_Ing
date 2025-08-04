package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import controller.MenuComedorController;

public class MenuComedorUniversitario extends JFrame {

    private ActionListener atrasListener;
    private JPanel panelPrincipal;
    private MenuComedorController controller;

    public MenuComedorUniversitario(String usuario) {
        setTitle("Menú del Comedor - " + usuario);
        initComponents();
    }

    public void setController(MenuComedorController controller) {
        this.controller = controller;

    }

    private void initComponents() {
    setTitle("Menú del Comedor");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Color colorFondo = controller != null ? controller.getColorFondo() : new Color(7, 64, 91);
    panelPrincipal = new JPanel();
    panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    panelPrincipal.setBackground(colorFondo);

    JLabel titulo = new JLabel("Menú Semanal del Comedor", JLabel.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 22));
    titulo.setForeground(Color.WHITE);
    panelPrincipal.add(titulo);
    panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

    String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
    String[][] desayunos = {
            {"Café con leche", "Pan integral", "Fruta"},
            {"Té con galletas", "Yogur", "Plátano"},
            {"Chocolate caliente", "Tostadas", "Manzana"},
            {"Jugo de naranja", "Croissant", "Pera"},
            {"Café americano", "Pan con mermelada", "Uvas"}
    };
    String[][] almuerzos = {
            {"Arroz con pollo", "Ensalada verde", "Postre"},
            {"Pasta a la boloñesa", "Pan al ajo", "Gelatina"},
            {"Lentejas con arroz", "Plátano frito", "Flan"},
            {"Pescado al horno", "Puré de papa", "Fruta"},
            {"Pizza vegetariana", "Ensalada César", "Helado"}
    };

    for (int i = 0; i < dias.length; i++) {
        JPanel panelDia = new JPanel(new BorderLayout(10, 10));
        panelDia.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelDia.setBackground(new Color(151, 188, 199));

        JLabel labelDia = new JLabel(dias[i], JLabel.CENTER);
        labelDia.setFont(new Font("Arial", Font.BOLD, 16));
        panelDia.add(labelDia, BorderLayout.NORTH);

        JPanel panelComidas = new JPanel(new GridLayout(1, 2, 15, 0));

        // Panel Desayuno
        JPanel panelDesayuno = new JPanel();
        panelDesayuno.setLayout(new BoxLayout(panelDesayuno, BoxLayout.Y_AXIS));
        panelDesayuno.setBackground(new Color(151, 188, 199));
        panelDesayuno.setBorder(BorderFactory.createTitledBorder("Desayuno"));

        for (String comida : desayunos[i]) {
            JLabel label = new JLabel("• " + comida);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            panelDesayuno.add(label);
        }

        // Panel Almuerzo
        JPanel panelAlmuerzo = new JPanel();
        panelAlmuerzo.setLayout(new BoxLayout(panelAlmuerzo, BoxLayout.Y_AXIS));
        panelAlmuerzo.setBackground(new Color(151, 188, 199));
        panelAlmuerzo.setBorder(BorderFactory.createTitledBorder("Almuerzo"));

        for (String comida : almuerzos[i]) {
            JLabel label = new JLabel("• " + comida);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            panelAlmuerzo.add(label);
        }

        panelComidas.add(panelDesayuno);
        panelComidas.add(panelAlmuerzo);
        panelDia.add(panelComidas, BorderLayout.CENTER);
        panelPrincipal.add(panelDia);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    // Botón Atrás 
    JButton btnAtras = new JButton("Atrás");
    btnAtras.addActionListener(e -> {
        if (atrasListener != null) {
            atrasListener.actionPerformed(e);
        }
    });

    JPanel panelBotones = new JPanel();
    panelBotones.setBackground(colorFondo);
    panelBotones.add(btnAtras);

    panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
    panelPrincipal.add(panelBotones);

    add(new JScrollPane(panelPrincipal));
    setLocationRelativeTo(null);
}

 
    public void setAtrasListener(ActionListener listener) {
        this.atrasListener = listener;
    }

    public void cerrarVentana() {
        this.dispose();
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String usuario = "UsuarioDemo";
            boolean esAdmin = false;
            MenuComedorUniversitario view = new MenuComedorUniversitario(usuario);
            MenuComedorController controller = new MenuComedorController(view, usuario, esAdmin);
            view.setVisible(true);
        });
    }
}
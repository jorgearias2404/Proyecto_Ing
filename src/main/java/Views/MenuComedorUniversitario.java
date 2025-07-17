
 package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import controller.MenuComedorController;

public class MenuComedorUniversitario extends JFrame {
    private ActionListener guardarListener;
    private ActionListener atrasListener;
    private JPanel panelPrincipal;
    private MenuComedorController controller;

    public MenuComedorUniversitario() {
        initComponents();
    }

    public void setController(MenuComedorController controller) {
        this.controller = controller;
    }

    private void initComponents() {
        setTitle("Menú del Comedor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color colorFondo = new Color(7, 64, 91);
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
            final int diaIndex = i;
            JPanel panelDia = new JPanel(new BorderLayout(10, 10));
            panelDia.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 2),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            panelDia.setBackground(new Color(151, 188, 199));

            JLabel labelDia = new JLabel(dias[diaIndex], JLabel.CENTER);
            labelDia.setFont(new Font("Arial", Font.BOLD, 16));
            panelDia.add(labelDia, BorderLayout.NORTH);

            JPanel panelComidas = new JPanel(new GridLayout(1, 2, 15, 0));

            // Panel Desayuno
            JPanel panelDesayuno = new JPanel();
            panelDesayuno.setLayout(new BoxLayout(panelDesayuno, BoxLayout.Y_AXIS));
            panelDesayuno.setBackground(new Color(151, 188, 199));
            panelDesayuno.setBorder(BorderFactory.createTitledBorder("Desayuno"));

            for (String comida : desayunos[diaIndex]) {
                JCheckBox cb = new JCheckBox(comida);
                cb.setBackground(new Color(151, 188, 199));
                cb.addItemListener(e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        controller.agregarSeleccion(dias[diaIndex] + " - Desayuno: " + comida);
                    } else {
                        controller.removerSeleccion(dias[diaIndex] + " - Desayuno: " + comida);
                    }
                });
                panelDesayuno.add(cb);
            }

            // Panel Almuerzo
            JPanel panelAlmuerzo = new JPanel();
            panelAlmuerzo.setLayout(new BoxLayout(panelAlmuerzo, BoxLayout.Y_AXIS));
            panelAlmuerzo.setBackground(new Color(151, 188, 199));
            panelAlmuerzo.setBorder(BorderFactory.createTitledBorder("Almuerzo"));

            for (String comida : almuerzos[diaIndex]) {
                JCheckBox cb = new JCheckBox(comida);
                cb.setBackground(new Color(151, 188, 199));
                cb.addItemListener(e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        controller.agregarSeleccion(dias[diaIndex] + " - Almuerzo: " + comida);
                    } else {
                        controller.removerSeleccion(dias[diaIndex] + " - Almuerzo: " + comida);
                    }
                });
                panelAlmuerzo.add(cb);
            }

            panelComidas.add(panelDesayuno);
            panelComidas.add(panelAlmuerzo);
            panelDia.add(panelComidas, BorderLayout.CENTER);
            panelPrincipal.add(panelDia);
            panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            if (guardarListener != null) {
                guardarListener.actionPerformed(e);
            }
        });

        JButton btnAtras = new JButton("Atrás");
        btnAtras.addActionListener(e -> {
            if (atrasListener != null) {
                atrasListener.actionPerformed(e);
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(colorFondo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnAtras);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPrincipal.add(panelBotones);

        add(new JScrollPane(panelPrincipal));
        setLocationRelativeTo(null);
    }

    public void setGuardarListener(ActionListener listener) {
        this.guardarListener = listener;
    }

    public void setAtrasListener(ActionListener listener) {
        this.atrasListener = listener;
    }

    public void cerrarVentana() {
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuComedorUniversitario view = new MenuComedorUniversitario();
            MenuComedorController controller = new MenuComedorController(view);
            view.setController(controller); 
            view.setVisible(true);
        });
    }
}
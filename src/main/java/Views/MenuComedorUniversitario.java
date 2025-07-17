package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuComedorUniversitario extends JFrame {
    private ActionListener guardarListener;
    private ActionListener atrasListener;
    private JPanel panelPrincipal;
    
    public MenuComedorUniversitario() {
        initComponents();
    }

    private void initComponents() {
   
        setTitle("Menú del Comedor");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


         Color colorFondo = new Color(7, 64, 91);
        // Panel principal
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(colorFondo);

        // Titulo
        JLabel titulo = new JLabel("Menú Semanal del Comedor", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Datos del menú (estos podrían moverse al controlador)
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

        // Imágenes
        String[] imagenesDesayuno = {
                "images/desayuno.png",
                "images/desayuno2.png",
                "images/desayuno3.png",
                "images/desayuno4.png",
                "images/desayuno5.png"
        };
        String[] imagenesAlmuerzo = {
                "images/almuerzo1.png",
                "images/almuerzo2.png",
                "images/almuerzo3.png",
                "images/almuerzo4.png",
                "images/almuerzo5.png"
        };

        // Panel días
        for (int i = 0; i < dias.length; i++) {
            JPanel panelDia = new JPanel(new BorderLayout(10, 10));
            panelDia.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 2),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            // Título del día
            JLabel labelDia = new JLabel(dias[i], JLabel.CENTER);
            labelDia.setFont(new Font("Arial", Font.BOLD, 16));
            panelDia.add(labelDia, BorderLayout.NORTH);

            // Panel de comidas
            JPanel panelComidas = new JPanel(new GridLayout(1, 2, 15, 0));

            // Panel Desayuno
            JPanel panelDesayuno = crearPanelComida(
                    "Desayuno (7:00-10:00AM)", desayunos[i], imagenesDesayuno[i], dias[i] + " - Desayuno");
            panelComidas.add(panelDesayuno);

            // Panel Almuerzo
            JPanel panelAlmuerzo = crearPanelComida(
                    "Almuerzo (12:00-2:00PM)", almuerzos[i], imagenesAlmuerzo[i], dias[i] + " - Almuerzo");
            panelComidas.add(panelAlmuerzo);

            panelDia.add(panelComidas, BorderLayout.CENTER);
            panelPrincipal.add(panelDia);
            panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        // Botón Guardar
        JButton botonGuardar = new JButton("Guardar Selecciones");
        estiloBoton(botonGuardar);
        botonGuardar.addActionListener(e -> {
            if (guardarListener != null) guardarListener.actionPerformed(e);
        });
        panelPrincipal.add(botonGuardar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botón Atrás
        JButton botonAtras = new JButton("Atrás");
        estiloBoton(botonAtras);
        botonAtras.addActionListener(e -> {
            if (atrasListener != null) atrasListener.actionPerformed(e);
        });
        panelPrincipal.add(botonAtras);

        add(new JScrollPane(panelPrincipal));
    }

    private JPanel crearPanelComida(String tipo, String[] comidas, String rutaImagen, String diaComida) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel labelTipo = new JLabel(tipo, JLabel.CENTER);
        labelTipo.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(labelTipo);

        // Imagen
        try {
            ImageIcon icono = new ImageIcon(rutaImagen);
            JLabel imagen = new JLabel(new ImageIcon(icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
            imagen.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(imagen);
        } catch (Exception e) {
            JLabel error = new JLabel("[Imagen no disponible]");
            error.setForeground(Color.RED);
            panel.add(error);
        }

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Opciones de comida
        for (String comida : comidas) {
            JCheckBox checkBox = new JCheckBox(comida);
            checkBox.setFont(new Font("Arial", Font.PLAIN, 12));
            panel.add(checkBox);
        }

        return panel;
    }

    // Métodos para el controlador
    public void setGuardarListener(ActionListener listener) {
        this.guardarListener = listener;
    }
    
    public void setAtrasListener(ActionListener listener) {
        this.atrasListener = listener;
    }
    
    public void cerrarVentana() {
        dispose();
    }
    
    private void estiloBoton(JButton boton) {
        boton.setBackground(new Color(151, 188, 199));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuComedorUniversitario view = new MenuComedorUniversitario();
            new controller.MenuComedorController(view);
            view.setVisible(true);
        });
    }
}


import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuComedorUniversitario {
    // colores aplicacion y config
    private static  Color COLOR_FONDO = new Color(7, 64, 91);
    private static  Color COLOR_TEXTO = new Color(240, 240, 240);
    private static  Color COLOR_INPUT = new Color(151, 188, 199);
    private static  Color COLOR_BORDE = new Color(100, 100, 100);
    private static  Color COLOR_BOTON = new Color(151, 188, 199);
    private static  Color COLOR_BOTON_TEXTO = Color.WHITE;
    private static  int ANCHO_IMAGEN = 120;  
    private static  int ALTO_IMAGEN = 120;  

    private static List<String> selecciones = new ArrayList<>();

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Menú del Comedor");
        ventana.setSize(900, 700);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.getContentPane().setBackground(COLOR_FONDO);

        // panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // titulo
        JLabel titulo = new JLabel("Menú Semanal del Comedor", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(COLOR_TEXTO);
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // datos del menu
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

        // imagenes
        String[] imagenesDesayuno = {
                "desayuno.png",
                "desayuno2.png",
                "desayuno3.png",
                "desayuno4.png",
                "desayuno5.png"
        };
        String[] imagenesAlmuerzo = {
                "almuerzo1.png",
                "almuerzo2.png",
                "almuerzo3.png",
                "almuerzo4.png",
                "almuerzo5.png"
        };

        // panel dias
        for (int i = 0; i < dias.length; i++) {
            JPanel panelDia = new JPanel(new BorderLayout(10, 10));
            panelDia.setBackground(COLOR_INPUT);
            panelDia.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_BORDE, 2),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            // titulo del dia
            JLabel labelDia = new JLabel(dias[i], JLabel.CENTER);
            labelDia.setFont(new Font("Arial", Font.BOLD, 16));
            labelDia.setForeground(COLOR_TEXTO);
            panelDia.add(labelDia, BorderLayout.NORTH);

            // panel de comidas 
            JPanel panelComidas = new JPanel(new GridLayout(1, 2, 15, 0));
            panelComidas.setBackground(COLOR_INPUT);

            // panel Desayuno 
            JPanel panelDesayuno = crearPanelComida(
                    "Desayuno (7:00-10:00AM)", desayunos[i], imagenesDesayuno[i], dias[i] + " - Desayuno" 
            );
            panelComidas.add(panelDesayuno);

            // panel Almuerzo 
            JPanel panelAlmuerzo = crearPanelComida(
                    "Almuerzo (12:00-2:00PM)", almuerzos[i], imagenesAlmuerzo[i], dias[i] + " - Almuerzo"
            );
            panelComidas.add(panelAlmuerzo);

            panelDia.add(panelComidas, BorderLayout.CENTER);
            panelPrincipal.add(panelDia);
            panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        
        // Botón para guardar selecciones
        JButton botonGuardar = new JButton("Guardar Selecciones");
        estiloBoton(botonGuardar, COLOR_BOTON, COLOR_BOTON_TEXTO);
        botonGuardar.addActionListener(e -> guardarSelecciones());
        panelPrincipal.add(botonGuardar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10))); 

      // Boton atras
      JButton botonAtras = new JButton("Atrás");
      estiloBoton(botonAtras, COLOR_BOTON, COLOR_BOTON_TEXTO);
      botonAtras.addActionListener(e -> abrirOpUsuario(ventana)); // me regreso a la ventana anterior 
      panelPrincipal.add(botonAtras);

       // agregar scroll y mostrar
       ventana.add(new JScrollPane(panelPrincipal));
       ventana.setVisible(true);
    }  
        
    private static void estiloBoton(JButton boton, Color fondo, Color texto) {
       boton.setBackground(fondo);
       boton.setForeground(texto);
       boton.setFocusPainted(false);
       boton.setFont(new Font("Arial", Font.BOLD, 14));
       boton.setAlignmentX(Component.CENTER_ALIGNMENT);
}

    // metodo para crear un panel de comida 
    private static JPanel crearPanelComida(String tipo, String[] comidas, String rutaImagen, String diaComida) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_INPUT);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // titulo desyuno y almuerzo
        JLabel labelTipo = new JLabel(tipo, JLabel.CENTER);
        labelTipo.setFont(new Font("Arial", Font.BOLD, 14));
        labelTipo.setForeground(COLOR_TEXTO);
        panel.add(labelTipo);

      
        try {
            ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
            Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(
                    ANCHO_IMAGEN, ALTO_IMAGEN, Image.SCALE_SMOOTH  
            );
            JLabel imagen = new JLabel(new ImageIcon(imagenRedimensionada));
            imagen.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(imagen);
        } catch (Exception e) {
            // si falla muestra placeholder
            JLabel errorImagen = new JLabel("[Imagen no disponible]");
            errorImagen.setForeground(Color.RED);
            panel.add(errorImagen);
        }

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        //check para cada opcion de comida
        for (String comida : comidas) {
            JCheckBox checkBox = new JCheckBox(comida);
            checkBox.setFont(new Font("Arial", Font.PLAIN, 12));
            checkBox.setBackground(COLOR_INPUT);
            checkBox.setForeground(COLOR_TEXTO);
            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    selecciones.add(diaComida + ": " + comida);
                } else {
                    selecciones.remove(diaComida + ": " + comida);
                }
            });
            panel.add(checkBox);
        }

        return panel;
    }

    // metodo abrirOpUsuario
    private static void abrirOpUsuario(JFrame ventanaActual) {
    ventanaActual.dispose();
    SwingUtilities.invokeLater(() -> Op_Usuario.main(new String[]{}));
}

    // metodo para guardar selecciones 
    private static void guardarSelecciones() {
        try (FileWriter writer = new FileWriter("selecciones_menu.txt")) {
            for (String seleccion : selecciones) {
                writer.write(seleccion + "\n");
            }
            JOptionPane.showMessageDialog(null, "¡Selecciones guardadas!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
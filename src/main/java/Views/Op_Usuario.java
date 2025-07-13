package Views;

import javax.swing.*;
import java.awt.*;

public class Op_Usuario extends JFrame {
    
    public Op_Usuario() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Opciones Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setResizable(false);

        // Color principal oscuro
        Color colorFondo = new Color(7, 64, 91);
        Color colorTexto = new Color(240, 240, 240);
        Color colorBoton = new Color(151, 188, 199);
        Color colorBotonTexto = new Color(255, 255, 255);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setBackground(colorFondo);

        // Título
        JLabel titulo = new JLabel("Opciones de Usuario");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(colorTexto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(titulo);

        // Espacio
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botón 1
        JButton boton1 = new JButton("Opción 1");
        boton1.setMaximumSize(new Dimension(250, 40));
        boton1.setBackground(colorBoton);
        boton1.setForeground(colorBotonTexto);
        boton1.setFont(new Font("Arial", Font.BOLD, 14));
        boton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(boton1);

        // Espacio
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botón 2
        JButton boton2 = new JButton("Opción 2");
        boton2.setMaximumSize(new Dimension(250, 40));
        boton2.setBackground(colorBoton);
        boton2.setForeground(colorBotonTexto);
        boton2.setFont(new Font("Arial", Font.BOLD, 14));
        boton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(boton2);

        // Añadir panel principal a la ventana
        add(panelPrincipal);

        // Centrar ventana
        setLocationRelativeTo(null);
    }
}
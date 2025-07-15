

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Op_Usuario extends JFrame {
    
    public Op_Usuario() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Opciones Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setResizable(false);

        // Color principal oscuro
        Color colorFondo = new Color(7, 64, 91);
        Color colorTexto = new Color(240, 240, 240);
        Color colorBoton = new Color(151, 188, 199);
        Color colorBotonTexto = new Color(0, 0, 0);
        Color colorTitulo = new Color(255, 255, 255);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setBackground(colorFondo);

        // Nombre del usuario (en la parte superior)
        JLabel nombreUsuario = new JLabel("Nombre_Persona");
        nombreUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        nombreUsuario.setForeground(colorTexto);
        nombreUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(nombreUsuario);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Título "SELECCIONA ALGUNA OPCION"
        JLabel titulo = new JLabel("SELECCIONA ALGUNA OPCION");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(colorTitulo);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(titulo);

        // Espacio
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botón REALIZAR RESERVA
        JButton botonReserva = new JButton("REALIZAR RESERVA");
        estiloBoton(botonReserva, colorBoton, colorBotonTexto);
        botonReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirMenuComedor();
            }
        });
        panelPrincipal.add(botonReserva);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botón VER HISTORIAL DE CONSUMO
        JButton botonHistorial = new JButton("VER HISTORIAL DE CONSUMO");
        estiloBoton(botonHistorial, colorBoton, colorBotonTexto);
        panelPrincipal.add(botonHistorial);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botón RECARGAR MONEDERO
        JButton botonRecargar = new JButton("RECARGAR MONEDERO");
        estiloBoton(botonRecargar, colorBoton, colorBotonTexto);
        panelPrincipal.add(botonRecargar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
         botonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirMonedero();
            }
        });

        // Botón RESERVAR TURNO DE COMIDA
        JButton botonComida = new JButton("RESERVAR TURNO DE COMIDA");
        estiloBoton(botonComida, colorBoton, colorBotonTexto);
        panelPrincipal.add(botonComida);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botón CERRAR
        JButton botonAtras = new JButton("CERRAR");
        estiloBoton(botonAtras, colorBoton, colorBotonTexto);
        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
            }
        });
        panelPrincipal.add(botonAtras);

        // Añadir panel principal a la ventana
        add(panelPrincipal);

        // Centrar ventana
        setLocationRelativeTo(null);
    }

    private void estiloBoton(JButton boton, Color fondo, Color texto) {
        boton.setMaximumSize(new Dimension(300, 50));
        boton.setBackground(fondo);
        boton.setForeground(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
    }

    private void abrirMenuComedor() {
        // Cierra la ventana actual
        this.dispose();
        
        // Abre el menú del comedor usando su método main
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuComedorUniversitario.main(new String[]{});
            }
        });
    }

    private void abrirMonedero() {
        // Cierra la ventana actual
        this.dispose();
        
        // Abre el menú del comedor usando su método main
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MonederoEstudiantil.main(new String[]{});
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Op_Usuario().setVisible(true);
            }
        });
    }
}
package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Op_Admin extends JFrame {
    private JButton botonReserva;
    private JButton botonHistorial;
    private JButton botonRecargar;
    private JButton botonSalir;
    private JButton botonAdminMenu;
    
    public Op_Admin(String usuario, boolean esAdmin) {
        initComponents(usuario, esAdmin);
    }

    private void initComponents(String usuario, boolean esAdmin) {
        setTitle("Panel de Administración - " + usuario);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setResizable(false);

        Color colorFondo = new Color(7, 64, 91); // Azul oscuro para admin
        Color colorTexto = new Color(240, 240, 240);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setBackground(colorFondo);

        JLabel nombreUsuario = new JLabel("ADMIN: " + usuario);
        nombreUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        nombreUsuario.setForeground(colorTexto);
        nombreUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(nombreUsuario);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel titulo = new JLabel("PANEL DE ADMINISTRACIÓN");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(colorTexto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        botonReserva = crearBoton("MENÚ SEMANAL");
        panelPrincipal.add(botonReserva);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        botonHistorial = crearBoton("CALCULADORA CCB");
        panelPrincipal.add(botonHistorial);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        botonRecargar = crearBoton("RECARGAR MONEDERO");
        panelPrincipal.add(botonRecargar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        botonAdminMenu = crearBoton("ADMINISTRAR MENÚS");
        botonAdminMenu.setVisible(true); // Siempre visible para admin
        panelPrincipal.add(botonAdminMenu);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        botonSalir = crearBoton("CERRAR SESIÓN");
        panelPrincipal.add(botonSalir);

        add(panelPrincipal);
        setLocationRelativeTo(null);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setMaximumSize(new Dimension(300, 50));
        boton.setBackground(new Color(151, 188, 199));
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return boton;
    }

    public void addReservaListener(ActionListener listener) {
        botonReserva.addActionListener(listener);
    }

    public void addHistorialListener(ActionListener listener) {
        botonHistorial.addActionListener(listener);
    }

    public void addRecargarListener(ActionListener listener) {
        botonRecargar.addActionListener(listener);
    }

    public void addSalirListener(ActionListener listener) {
        botonSalir.addActionListener(listener);
    }

    public void addAdminMenuListener(ActionListener listener) {
        botonAdminMenu.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void cerrarVentana() {
        this.dispose();
    }
}
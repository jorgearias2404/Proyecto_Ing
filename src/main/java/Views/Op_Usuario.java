package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Op_Usuario extends JFrame {
    private JButton botonReserva;
    private JButton botonHistorial;
    private JButton botonRecargar;
    private JButton botonSalir;
    
    public Op_Usuario(String usuario) {
        initComponents(usuario);
    }

    private void initComponents(String usuario) {
        setTitle("Opciones Usuario - " + usuario);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setResizable(false);

        Color colorFondo = new Color(7, 64, 91);
        Color colorTexto = new Color(240, 240, 240);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setBackground(colorFondo);

        JLabel nombreUsuario = new JLabel(usuario);
        nombreUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        nombreUsuario.setForeground(colorTexto);
        nombreUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(nombreUsuario);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel titulo = new JLabel("SELECCIONA UNA OPCIÓN");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(colorTexto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        botonReserva = crearBoton("REALIZAR RESERVA");
        panelPrincipal.add(botonReserva);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        botonHistorial = crearBoton("CALCULADORA CCB");
        panelPrincipal.add(botonHistorial);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        botonRecargar = crearBoton("RECARGAR MONEDERO");
        panelPrincipal.add(botonRecargar);
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

    // metodos para el controlador
    public void addReservaListener(ActionListener listener) {
        botonReserva.addActionListener(listener);
    }

    // Metodo que se modificado en Op_Usuario
     public void addHistorialListener(ActionListener listener) {
         botonHistorial.addActionListener(e -> {
        this.setVisible(false); // Oculta la ventana actual
        CalculadoraCCB calculadora = new CalculadoraCCB(this); // Crea una nueva instancia de CalculadoraCCB
        calculadora.setVisible(true);
    });
}
    public void addRecargarListener(ActionListener listener) {
        botonRecargar.addActionListener(listener);
    }

    public void addSalirListener(ActionListener listener) {
        botonSalir.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void cerrarVentana() {
        this.dispose();
    }
}
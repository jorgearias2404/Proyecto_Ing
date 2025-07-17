package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.MenuComedorController;
import controller.MonederoController;
import Views.MenuComedorUniversitario;
import Views.MonederoEstudiantil;

public class Op_Usuario extends JFrame {
    private String usuarioActual;
    
    public Op_Usuario(String usuario) {
        this.usuarioActual = usuario;
        initComponents();
    }

    private void initComponents() {
        setTitle("Opciones Usuario - " + usuarioActual);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setResizable(false);

        Color colorFondo = new Color(7, 64, 91);
        Color colorTexto = new Color(240, 240, 240);
        Color colorBoton = new Color(151, 188, 199);
        Color colorBotonTexto = new Color(0, 0, 0);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setBackground(colorFondo);

        JLabel nombreUsuario = new JLabel(usuarioActual);
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

        JButton botonReserva = crearBoton("REALIZAR RESERVA", e -> abrirMenuComedor());
        panelPrincipal.add(botonReserva);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton botonHistorial = crearBoton("VER HISTORIAL", e -> abrirHistorial());
        panelPrincipal.add(botonHistorial);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton botonRecargar = crearBoton("RECARGAR MONEDERO", e -> abrirMonedero());
        panelPrincipal.add(botonRecargar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton botonSalir = crearBoton("CERRAR SESIÓN", e -> dispose());
        panelPrincipal.add(botonSalir);

        add(panelPrincipal);
        setLocationRelativeTo(null);
    }

    private JButton crearBoton(String texto, ActionListener listener) {
        JButton boton = new JButton(texto);
        boton.setMaximumSize(new Dimension(300, 50));
        boton.setBackground(new Color(151, 188, 199));
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener(listener);
        return boton;
    }

    private void abrirMenuComedor() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            MenuComedorUniversitario view = new MenuComedorUniversitario();
            MenuComedorController controller = new MenuComedorController(view, usuarioActual);
            view.setController(controller);
            view.setVisible(true);
        });
    }
   private void abrirMonedero() {
    this.dispose();
    SwingUtilities.invokeLater(() -> {
        MonederoEstudiantil view = new MonederoEstudiantil();
        MonederoController controller = new MonederoController(view, usuarioActual);
        view.setController(controller); // Ahora este método existe
        view.setVisible(true);
    });
}
   

    private void abrirHistorial() {
        JOptionPane.showMessageDialog(this, 
            "Historial de: " + usuarioActual, 
            "Historial", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}
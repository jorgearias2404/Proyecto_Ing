package Views;

import Services.AuthService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;

public class Modulo1 extends JFrame {
    
    private JTextField campoUsuario;
    private JPasswordField campoContraseña;
    
    public Modulo1() {
        initComponents();
    }
    private Border crearBordeRedondeado(int radio, Color color) {
    return BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(color, 1),  // Borde exterior (1px de grosor)
        BorderFactory.createEmptyBorder(5, 15, 5, 15)  // Margen interno (top, left, bottom, right)
    );
}
    
    private void initComponents() {
        // Configuración básica de la ventana
        setTitle("Inicio de Sesión Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setResizable(false);
        
        
        // Color principal oscuro
        Color colorFondo = new Color(7, 64, 91);
        Color colorTexto = new Color(240, 240, 240);
        Color colorInput = new Color(151, 188, 199);
        Color colorBordeInput = new Color(100, 100, 100);
        Color colorBoton = new Color(151, 188, 199);
        Color colorBotonTexto = new Color(255, 255, 255);
        
        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setBackground(colorFondo);
        
        // Panel superior con botón Atrás
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(colorFondo);
        
 
        
        // Panel central para el formulario
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(colorFondo);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Título
        JLabel titulo = new JLabel("Inicio de sesion");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(colorTexto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(titulo);
        
        // Espacio
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Etiqueta "correo - usuario"
        JLabel etiquetaCorreo = new JLabel("CORREO - USUARIO");
        etiquetaCorreo.setFont(new Font("Arial", Font.PLAIN, 12));
        etiquetaCorreo.setForeground(colorTexto);
        etiquetaCorreo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(etiquetaCorreo);
        
        // Campo usuario
        campoUsuario = new JTextField();
        campoUsuario.setMaximumSize(new Dimension(250, 35));
        campoUsuario.setBackground(colorInput);
        campoUsuario.setForeground(colorTexto);
        campoUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBordeInput),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(campoUsuario);
        
        // Espacio
        panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Etiqueta "contraseña"
        JLabel etiquetaContraseña = new JLabel("CONTRASEÑA");
        etiquetaContraseña.setFont(new Font("Arial", Font.PLAIN, 12));
        etiquetaContraseña.setForeground(colorTexto);
        etiquetaContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(etiquetaContraseña);
        
        // Campo contraseña
        campoContraseña = new JPasswordField();
        campoContraseña.setMaximumSize(new Dimension(250, 35));
        campoContraseña.setBackground(colorInput);
        campoContraseña.setForeground(colorTexto);
        campoContraseña.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBordeInput),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campoContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(campoContraseña);
        
        // Espacio
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Botón INGRESAR
        JButton botonIngresar = new JButton("INGRESAR");
        botonIngresar.setMaximumSize(new Dimension(250, 40));
        botonIngresar.setBackground(colorBoton);
        botonIngresar.setForeground(colorBotonTexto);
        botonIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        botonIngresar.setBorderPainted(false);
        botonIngresar.setFocusPainted(false);
        botonIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonIngresar.addActionListener(e -> validarLogin());
        panelCentral.add(botonIngresar);
        
        // Espacio
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Botón registrarse
        JButton botonRegistrarse = new JButton("registrarse");
        botonRegistrarse.setForeground(colorTexto);
        botonRegistrarse.setBackground(colorFondo);
        botonRegistrarse.setBorderPainted(false);
        botonRegistrarse.setFocusPainted(false);
        botonRegistrarse.setContentAreaFilled(false);
        botonRegistrarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRegistrarse.addActionListener(e -> mostrarRegistro());
        panelCentral.add(botonRegistrarse);
        
        // Añadir todos los paneles al panel principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        // Añadir panel principal a la ventana
        add(panelPrincipal);
        
        // Centrar ventana
        setLocationRelativeTo(null);
    }
    
    private void validarLogin() {
        String username = campoUsuario.getText();
        String password = new String(campoContraseña.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor complete todos los campos", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Primero intenta como usuario normal
        boolean loginExitoso = AuthService.validarLogin(username, password, false);
        String rol = "Usuario";
        
        // Si falla, intenta como admin
        if (!loginExitoso) {
            loginExitoso = AuthService.validarLogin(username, password, true);
            rol = "Administrador";
        }

        if (loginExitoso) {
            String nombre = AuthService.obtenerNombre(username, rol.equals("Administrador"));
            JOptionPane.showMessageDialog(this,
                "Bienvenido " + nombre + " (" + rol + ")",
                "Login exitoso",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Credenciales incorrectas",
                "Error de autenticación",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarRegistro() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de registro en desarrollo", 
            "Registro", JOptionPane.INFORMATION_MESSAGE);
    }
}
package Views;

import Services.AuthService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class Login extends JFrame {
    
    private JTextField campoUsuario;
    private JPasswordField campoContraseña;
    
    public Login() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Inicio de Sesión Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400); // Aumenté ligeramente el tamaño para mejor visualización
        setResizable(false);

        // Colores
        Color colorFondo = new Color(7, 64, 91);
        Color colorTexto = new Color(240, 240, 240);
        Color colorInput = new Color(151, 188, 199);
        Color colorBordeInput = new Color(100, 100, 100);
        Color colorBoton = new Color(151, 188, 199);
        Color colorBotonTexto = new Color(0, 0, 0);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setBackground(colorFondo);

        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(colorFondo);

        // Título
        JLabel titulo = new JLabel("Inicio de sesión");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(colorTexto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(titulo);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 30)));

        // Campo usuario
        campoUsuario = new JTextField();
        campoUsuario.setMaximumSize(new Dimension(300, 35));
        campoUsuario.setBackground(colorInput);
        campoUsuario.setForeground(Color.BLACK);
        campoUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBordeInput),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        JLabel lblUsuario = new JLabel("CORREO - USUARIO");
        lblUsuario.setForeground(colorTexto);
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(lblUsuario);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 5)));
        panelCentral.add(campoUsuario);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo contraseña
        campoContraseña = new JPasswordField();
        campoContraseña.setMaximumSize(new Dimension(300, 35));
        campoContraseña.setBackground(colorInput);
        campoContraseña.setForeground(Color.BLACK);
        campoContraseña.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBordeInput),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        JLabel lblContraseña = new JLabel("CONTRASEÑA");
        lblContraseña.setForeground(colorTexto);
        lblContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(lblContraseña);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 5)));
        panelCentral.add(campoContraseña);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botón Ingresar
        JButton btnIngresar = new JButton("INGRESAR");
        btnIngresar.setMaximumSize(new Dimension(300, 40));
        btnIngresar.setBackground(colorBoton);
        btnIngresar.setForeground(colorBotonTexto);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.addActionListener(this::validarLogin);
        panelCentral.add(btnIngresar);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botón Registrarse - CORREGIDO
        JButton btnRegistrarse = new JButton("REGISTRARSE");
        btnRegistrarse.setMaximumSize(new Dimension(300, 40));
        btnRegistrarse.setBackground(colorFondo);
        btnRegistrarse.setForeground(colorTexto);
        btnRegistrarse.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarse.setBorder(BorderFactory.createLineBorder(colorTexto));
        btnRegistrarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrarse.setContentAreaFilled(false);
        btnRegistrarse.setFocusPainted(false);
        btnRegistrarse.addActionListener(this::mostrarRegistro);
        panelCentral.add(btnRegistrarse);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER); 
        add(panelPrincipal);
        setLocationRelativeTo(null);
    }

    private void validarLogin(ActionEvent e) {
        String username = campoUsuario.getText();
        String password = new String(campoContraseña.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean loginExitoso = AuthService.validarLogin(username, password, false);
        String rol = "Usuario";
        
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
            this.dispose();
            new Op_Usuario().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Credenciales incorrectas", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarRegistro(ActionEvent e) {
        this.dispose();
        // Asegúrate de tener la clase Registration en tu proyecto
        new Registration().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
} 





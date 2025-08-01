package Views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoContraseña;
    private ActionListener loginListener;
    private ActionListener registerListener;
    
    public Login() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Inicio de Sesión Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setResizable(false);

        Color colorFondo = new Color(7, 64, 91);
        Color colorTexto = new Color(240, 240, 240);
        Color colorInput = new Color(151, 188, 199);
        Color colorBordeInput = new Color(100, 100, 100);
        Color colorBoton = new Color(151, 188, 199);
        Color colorBotonTexto = new Color(0, 0, 0);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setBackground(colorFondo);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(colorFondo);

        JLabel titulo = new JLabel("Inicio de sesión");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(colorTexto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(titulo);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 30)));

        campoUsuario = new JTextField();
        campoUsuario.setMaximumSize(new Dimension(300, 35));
        campoUsuario.setBackground(colorInput);
        campoUsuario.setForeground(Color.BLACK);
        campoUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBordeInput),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        JLabel lblUsuario = new JLabel("USUARIO");
        lblUsuario.setForeground(colorTexto);
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(lblUsuario);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 5)));
        panelCentral.add(campoUsuario);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

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

        JButton btnIngresar = new JButton("INGRESAR");
        btnIngresar.setMaximumSize(new Dimension(300, 40));
        btnIngresar.setBackground(colorBoton);
        btnIngresar.setForeground(colorBotonTexto);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.addActionListener(e -> {
            if (loginListener != null) {
                loginListener.actionPerformed(e);
            }
        });
        panelCentral.add(btnIngresar);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btnRegistrarse = new JButton("REGISTRARSE");
        btnRegistrarse.setMaximumSize(new Dimension(300, 40));
        btnRegistrarse.setBackground(colorFondo);
        btnRegistrarse.setForeground(colorTexto);
        btnRegistrarse.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarse.setBorder(BorderFactory.createLineBorder(colorTexto));
        btnRegistrarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrarse.setContentAreaFilled(false);
        btnRegistrarse.setFocusPainted(false);
        btnRegistrarse.addActionListener(e -> {
            if (registerListener != null) {
                registerListener.actionPerformed(e);
            }
        });
        panelCentral.add(btnRegistrarse);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER); 
        add(panelPrincipal);
        setLocationRelativeTo(null);
    }

    // Métodos para el controlador
    public void setLoginListener(ActionListener listener) {
        this.loginListener = listener;
    }
    
    public void setRegisterListener(ActionListener listener) {
        this.registerListener = listener;
    }
    
    public String getUsername() {
        return campoUsuario.getText().trim();
    }
    
    public String getPassword() {
        return new String(campoContraseña.getPassword());
    }
    
    public void close() {
        this.dispose();
    }
    
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login view = new Login();
            new controller.LoginController(view); // Conectamos el controlador
            view.setVisible(true);
        });
    }
}
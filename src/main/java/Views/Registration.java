package Views;

import javax.swing.*;

//import java.awt.event.*;
import java.awt.*;

public class Registration extends JFrame {
    private JPanel registrationPanel;
    private JTextField textBoxEmail, textBoxUser, textBoxID;
    private JPasswordField textBoxPassword;
    private JComboBox<String> roleSelectionMenu;
    private JButton registerButton;

    public static void main(String[] args){
        Registration register = new Registration();
        register.setVisible(true);
    }

    public Registration() {

        //Configuracion basica de la ventana
        setSize(500, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Registro de nuevo usuario");
        setLocationRelativeTo(null);

        // Colores principales
        Color bgColor = new Color(7, 64, 91);
        Color textColor = new Color(240, 240, 240);
        Color inputColor = new Color(151, 188, 199);
        Color inputBorderColor = new Color(100, 100, 100);
        Color consTextColor = new Color(168, 168, 168);
        Color buttonColor = new Color(151, 188, 199);
        Color textButtonColor = new Color(255, 255, 255);


        addMainPanel(textColor, bgColor);
        addEmail(textColor, inputColor, inputBorderColor);
        addPassword(textColor, inputColor, inputBorderColor, consTextColor);
        addUser(textColor, inputColor, inputBorderColor, consTextColor);
        addID(textColor, inputColor, inputBorderColor, consTextColor);
        addRoleMenu(textColor, inputColor, inputBorderColor);
        addRegisterButton(buttonColor, textButtonColor);
    } 

    private void addMainPanel(Color textColor, Color bgColor) {
        registrationPanel = new JPanel();
        registrationPanel.setLayout(null);
        registrationPanel.setBackground(bgColor);
        this.getContentPane().add(registrationPanel);
        JLabel subtitle = new JLabel("Registro en COMEST");
        subtitle.setBounds(15,10,470,30);
        subtitle.setHorizontalAlignment(SwingConstants.LEFT);
        subtitle.setFont(new Font("Arial", Font.BOLD, 20));
        subtitle.setForeground(textColor);
        registrationPanel.add(subtitle);
    }

    private void addEmail(Color textColor, Color inputColor, Color inputBorderColor) {
        JLabel emaiLabel = new JLabel("Email*");
        emaiLabel.setBounds(15, 70, 310, 20);
        emaiLabel.setHorizontalAlignment(SwingConstants.LEFT);
        emaiLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emaiLabel.setForeground(textColor);
        registrationPanel.add(emaiLabel);

        textBoxEmail = new JTextField();
        textBoxEmail.setBounds(15, 95, 455, 35);
        //textBoxEmail.setMaximumSize(new Dimension(250, 35));
        textBoxEmail.setBackground(inputColor);
        textBoxEmail.setForeground(textColor);
        textBoxEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(inputBorderColor),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        textBoxEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        //registrationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        registrationPanel.add(textBoxEmail);
    }

    private void addPassword(Color textColor, Color inputColor, Color inputBorderColor, Color consTextColor) {
        JLabel passwordLabel = new JLabel("Contraseña*");
        passwordLabel.setBounds(15, 150, 310, 20);
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(textColor);
        registrationPanel.add(passwordLabel);

        textBoxPassword = new JPasswordField();
        textBoxPassword.setBounds(15, 175, 455, 35);
        //textBoxEmail.setMaximumSize(new Dimension(250, 35));
        textBoxPassword.setBackground(inputColor);
        textBoxPassword.setForeground(textColor);
        textBoxPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(inputBorderColor),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        textBoxPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        //registrationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        registrationPanel.add(textBoxPassword);

        JLabel passwordConsLabel = new JLabel();
        passwordConsLabel.setText("<html>" + "La contraseña debe tener al menos 15 caracteres O al menos 8 caracteres incluyendo un número y una letra minúscula."  + "</html>");
        passwordConsLabel.setBounds(15, 210, 455, 30);
        passwordConsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        passwordConsLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        passwordConsLabel.setForeground(consTextColor);
        registrationPanel.add(passwordConsLabel);
    }

    private void addUser(Color textColor, Color inputColor, Color inputBorderColor, Color consTextColor) {
        JLabel userLabel = new JLabel("Nombre de Usuario*");
        userLabel.setBounds(15, 260, 220, 20);
        userLabel.setHorizontalAlignment(SwingConstants.LEFT);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(textColor);
        registrationPanel.add(userLabel);

        textBoxUser = new JTextField();
        textBoxUser.setBounds(15, 285, 220, 35);
        //textBoxEmail.setMaximumSize(new Dimension(250, 35));
        textBoxUser.setBackground(inputColor);
        textBoxUser.setForeground(textColor);
        textBoxUser.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(inputBorderColor),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        textBoxUser.setFont(new Font("Arial", Font.PLAIN, 14));
        //registrationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        registrationPanel.add(textBoxUser);

        JLabel userConsLabel = new JLabel();
        userConsLabel.setText("<html>" + "El nombre de usuario sólo puede contener caracteres alfanuméricos."  + "</html>");
        userConsLabel.setBounds(15, 315, 220, 40);
        userConsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        userConsLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        userConsLabel.setForeground(consTextColor);
        registrationPanel.add(userConsLabel);
    }

    private void addID(Color textColor, Color inputColor, Color inputBorderColor, Color consTextColor) {
        JLabel idLabel = new JLabel("Cedula*");
        idLabel.setBounds(245, 260, 225, 20);
        idLabel.setHorizontalAlignment(SwingConstants.LEFT);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        idLabel.setForeground(textColor);
        registrationPanel.add(idLabel);

        textBoxID = new JTextField();
        textBoxID.setBounds(245, 285, 225, 35);
        //textBoxEmail.setMaximumSize(new Dimension(250, 35));
        textBoxID.setBackground(inputColor);
        textBoxID.setForeground(textColor);
        textBoxID.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(inputBorderColor),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        textBoxID.setFont(new Font("Arial", Font.PLAIN, 14));
        //registrationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        registrationPanel.add(textBoxID);

        JLabel idConsLabel = new JLabel();
        idConsLabel.setText("<html>" + "Ingrese la cedula sin puntos de separación. Ej. 20313234"  + "</html>");
        idConsLabel.setBounds(245, 315, 220, 40);
        idConsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        idConsLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        idConsLabel.setForeground(consTextColor);
        registrationPanel.add(idConsLabel);
    }

    private void addRoleMenu(Color textColor, Color inputColor, Color inputBorderColor) {
        String[] options = {"Rol", "Estudiante", "Profesor", "Empleado", "Administrador"};
        roleSelectionMenu = new JComboBox<>(options);
        roleSelectionMenu.setBounds(15, 370, 455, 40);
        roleSelectionMenu.setFont(new Font("Arial", Font.BOLD, 14));
        roleSelectionMenu.setBackground(inputColor);
        roleSelectionMenu.setForeground(textColor);
        registrationPanel.add(roleSelectionMenu);
    }

    private void addRegisterButton(Color buttonColor, Color textButtonColor) {
        registerButton = new JButton("REGISTARSE >");
        registerButton.setBounds(15, 450, 455, 50);
        registerButton.setBackground(buttonColor);
        registerButton.setForeground(textButtonColor);
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registrationPanel.add(registerButton);
    }
}
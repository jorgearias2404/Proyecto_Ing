package controller;

import Services.AuthService;
import Views.Login;
import Views.Op_Usuario;
import Views.Op_Admin;
import Views.Registration;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginController {
    private final Login loginView;
    private final AuthService authService;
    private static final String ADMIN_SECRET_KEY = "claveAdmin123";
    
    public LoginController(Login loginView) {
        this.loginView = loginView;
        this.authService = new AuthService();
        setupListeners();
    }
    
    private void setupListeners() {
        loginView.setLoginListener(this::handleLogin);
        loginView.setRegisterListener(this::handleRegister);
    }
    
    public void handleLogin(ActionEvent e) {
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        
        if (username.isEmpty() || password.isEmpty()) {
            loginView.showError("Complete todos los campos");
            return;
        }

        // Verificar si es administrador basado en el AuthService
        boolean isAdmin = authService.validarLogin(username, password, true);
        boolean isUser = authService.validarLogin(username, password, false);
        
        // Determinar el tipo de usuario correcto
        if (isAdmin) {
            // Es administrador, proceder con el login
        } else if (isUser) {
            // Es usuario regular, proceder con el login
            isAdmin = false;
        } else {
            // Credenciales incorrectas
            loginView.showError("Credenciales incorrectas");
            return;
        }

        // Usar las credenciales ya verificadas
        boolean loginExitoso = isAdmin || isUser;
        
        if (loginExitoso) {
            handleSuccessfulLogin(username, isAdmin);
        } else {
            loginView.showError("Credenciales incorrectas");
        }
    }
    
    private void handleSuccessfulLogin(String username, boolean isAdmin) {
        String nombre = authService.obtenerNombre(username, isAdmin);
        String rol = isAdmin ? "Administrador" : "Usuario";
        
        loginView.showMessage("Bienvenido " + nombre + " (" + rol + ")", "Login exitoso");
        loginView.close();
        
        SwingUtilities.invokeLater(() -> {
            if (isAdmin) {
                Op_Admin opAdmin = new Op_Admin(username, true);
                new OpAdminController(opAdmin, username, true);
                opAdmin.setVisible(true);
            } else {
                Op_Usuario opUsuario = new Op_Usuario(username, false);
                new OpUsuarioController(opUsuario, username, false);
                opUsuario.setVisible(true);
            }
        });
    }
    
    public void handleRegister(ActionEvent e) {
        loginView.close();
        SwingUtilities.invokeLater(() -> {
            Registration registration = new Registration();
            new RegistrationController(registration);
            registration.setVisible(true);
        });
    }
}
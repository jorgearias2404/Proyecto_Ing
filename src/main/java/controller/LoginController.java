
package controller;

import Services.AuthService;
import Views.Login;
import Views.Op_Usuario;
import Views.Registration;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginController {
    private final Login loginView;
    private final AuthService authService;
    
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

        boolean isAdmin = false;
        boolean loginExitoso = authService.validarLogin(username, password, isAdmin);
        
        if (!loginExitoso) {
            isAdmin = true;
            loginExitoso = authService.validarLogin(username, password, isAdmin);
        }

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
            Op_Usuario opUsuario = new Op_Usuario(username);
            new OpUsuarioController(opUsuario, username); // conectamos el controlador
            opUsuario.setVisible(true);
        });
    }
    
    public void handleRegister(ActionEvent e) {
        loginView.close();
        SwingUtilities.invokeLater(() -> {
            Registration registration = new Registration();
            registration.setVisible(true);
        });
    }
}


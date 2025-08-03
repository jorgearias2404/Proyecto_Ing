package controller;

import Views.Op_Usuario;
import Views.MenuComedorUniversitario;
import Views.MonederoEstudiantil;
import Views.Login;
import Views.AdminMenuView;
import javax.swing.*;

public class OpUsuarioController {
    private final Op_Usuario view;
    private final String usuarioActual;
    private final boolean esAdmin;

    public OpUsuarioController(Op_Usuario view, String usuario, boolean esAdmin) {
        if (view == null) {
            throw new IllegalArgumentException("La vista no puede ser nula");
        }
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario no puede estar vacío");
        }

        this.view = view;
        this.usuarioActual = usuario;
        this.esAdmin = esAdmin;
        configurarListeners();
    }

    private void configurarListeners() {
        try {
            view.addReservaListener(e -> abrirMenuComedor());
            view.addRecargarListener(e -> abrirMonedero());
            view.addSalirListener(e -> salir());

            if (esAdmin) {
                view.addAdminMenuListener(e -> abrirAdminMenu());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al configurar listeners: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirMenuComedor() {
        try {
            view.cerrarVentana();
            SwingUtilities.invokeLater(() -> {
                MenuComedorUniversitario menuView = new MenuComedorUniversitario(usuarioActual);
                new MenuComedorController(menuView, usuarioActual, esAdmin);
                menuView.setVisible(true);
            });
        } catch (Exception e) {
            manejarError("Error al abrir menú comedor", e);
        }
    }

    private void abrirMonedero() {
        try {
            view.cerrarVentana();
            SwingUtilities.invokeLater(() -> {
                MonederoEstudiantil monederoView = new MonederoEstudiantil(usuarioActual);
                new MonederoController(monederoView, usuarioActual, esAdmin);
                monederoView.setVisible(true);
            });
        } catch (Exception e) {
            manejarError("Error al abrir monedero estudiantil", e);
        }
    }

    private void salir() {
        try {
            view.cerrarVentana();
            SwingUtilities.invokeLater(() -> {
                Login loginView = new Login();
                new LoginController(loginView);
                loginView.setVisible(true);
            });
        } catch (Exception e) {
            manejarError("Error al cerrar sesión", e);
        }
    }

private void abrirAdminMenu() {
    try {
        if (!esAdmin) {
            throw new IllegalAccessException("Acceso denegado: se requiere privilegios de administrador");
        }
        
        view.cerrarVentana();
        SwingUtilities.invokeLater(() -> {
            AdminMenuView adminView = new AdminMenuView(usuarioActual);
            new AdminMenuController(adminView, usuarioActual, true); // Pasar true explícitamente para admin
            adminView.setVisible(true);
        });
    } catch (Exception e) {
        manejarError("Error al abrir menú de administrador", e);
    }
}

    private void manejarError(String mensaje, Exception e) {
        System.err.println(mensaje + ": " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, 
            mensaje + ": " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
            
        // Volver al login en caso de error crítico
        salir();
    }
}
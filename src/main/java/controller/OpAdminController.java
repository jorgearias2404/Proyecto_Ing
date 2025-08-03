package controller;

import Views.Op_Admin;
import Views.MenuComedorUniversitario;
import Views.MonederoEstudiantil;
import Views.Login;
import Views.AdminMenuView;
import javax.swing.*;
import Views.CargaCCB;

public class OpAdminController {
    private final Op_Admin view;
    private final String usuarioActual;
    private final boolean esAdmin;

    public OpAdminController(Op_Admin view, String usuario, boolean esAdmin) {
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
            view.addHistorialListener(e -> mostrarHistorial());
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
            new MenuComedorController(menuView, usuarioActual, esAdmin); // Asegurar que esAdmin se pasa
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
            new MonederoController(monederoView, usuarioActual, esAdmin); // Asegurar que esAdmin se pasa
            monederoView.setVisible(true);
        });
    } catch (Exception e) {
        manejarError("Error al abrir monedero estudiantil", e);
    }
}

private void mostrarHistorial() {
    try {
        view.setVisible(false); // Oculta Op_Admin sin cerrarlo
        SwingUtilities.invokeLater(() -> {
            CargaCCB cargaCCB = new CargaCCB(view); // Pasa la referencia de Op_Admin
            cargaCCB.setVisible(true); // Muestra la ventana CCB
        });
    } catch (Exception e) {
        manejarError("Error al abrir calculadora CCB", e);
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
            JOptionPane.showMessageDialog(null, 
                "Error al cerrar sesión: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
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
    }
}
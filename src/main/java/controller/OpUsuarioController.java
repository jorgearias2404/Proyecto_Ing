
package controller;

import Views.Op_Usuario;
import Views.MenuComedorUniversitario;
import Views.MonederoEstudiantil;
import Views.Login;
import Views.AdminMenuView;
import javax.swing.*;

public class OpUsuarioController {
    private Op_Usuario view;
    private String usuarioActual;
    private boolean esAdmin;

    public OpUsuarioController(Op_Usuario view, String usuario, boolean esAdmin) {
        this.view = view;
        this.usuarioActual = usuario;
        this.esAdmin = esAdmin;
        configurarListeners();
    }

    private void configurarListeners() {
        view.addReservaListener(e -> abrirMenuComedor());
        view.addHistorialListener(e -> mostrarHistorial());
        view.addRecargarListener(e -> abrirMonedero());
        view.addSalirListener(e -> salir());

        if (esAdmin) {
            view.addAdminMenuListener(e -> abrirAdminMenu());
        }
    }

    private void abrirMenuComedor() {
        view.cerrarVentana();
        SwingUtilities.invokeLater(() -> {
            MenuComedorUniversitario menuView = new MenuComedorUniversitario(usuarioActual);
            MenuComedorController menuController = new MenuComedorController(menuView, usuarioActual, esAdmin);
            menuView.setVisible(true);
        });
    }

    private void abrirMonedero() {
        view.cerrarVentana();
        SwingUtilities.invokeLater(() -> {
            MonederoEstudiantil monederoView = new MonederoEstudiantil(usuarioActual);
            MonederoController monederoController = new MonederoController(monederoView, usuarioActual, esAdmin);
            monederoView.setVisible(true);
        });
    }

    private void mostrarHistorial() {
        view.mostrarMensaje("Historial de: " + usuarioActual, "Historial");
    }

    private void salir() {
        view.cerrarVentana();
        SwingUtilities.invokeLater(() -> {
            Login loginView = new Login();
            new LoginController(loginView);
            loginView.setVisible(true);
        });
    }

    private void abrirAdminMenu() {
        view.cerrarVentana();
        SwingUtilities.invokeLater(() -> {
            AdminMenuView adminView = new AdminMenuView(usuarioActual);
            AdminMenuController adminController = new AdminMenuController(adminView, usuarioActual);
            adminView.setVisible(true);
        });
    }

}

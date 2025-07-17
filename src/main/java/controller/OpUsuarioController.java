/*package controller;

import Views.Op_Usuario;
import Views.MenuComedorUniversitario;
import Views.MonederoEstudiantil;
import javax.swing.SwingUtilities;

public class OpUsuarioController {
    private Op_Usuario view;
    private String usuarioActual;

    public OpUsuarioController(Op_Usuario view, String usuario) {
        this.view = view;
        this.usuarioActual = usuario;
        configurarListeners();
    }

    private void configurarListeners() {
        view.addReservaListener(e -> abrirMenuComedor());
        view.addHistorialListener(e -> mostrarHistorial());
        view.addRecargarListener(e -> abrirMonedero());
        view.addSalirListener(e -> salir());
    }

private void abrirMenuComedor() {
    view.cerrarVentana();
    SwingUtilities.invokeLater(() -> {
        MenuComedorUniversitario menuView = new MenuComedorUniversitario(usuarioActual);
        new MenuComedorController(menuView, usuarioActual);
        menuView.setVisible(true);
    });
}

private void abrirMonedero() {
    view.cerrarVentana();
    SwingUtilities.invokeLater(() -> {
        MonederoEstudiantil monederoView = new MonederoEstudiantil(usuarioActual);
        new MonederoController(monederoView, usuarioActual);
        monederoView.setVisible(true);
    });
}

    private void mostrarHistorial() {
        view.mostrarMensaje("Historial de: " + usuarioActual, "Historial");
    }

    private void salir() {
        view.cerrarVentana();
        // Aquí podrías agregar lógica para volver al login
    }
}*/

package controller;

import Views.Op_Usuario;
import Views.MenuComedorUniversitario;
import Views.MonederoEstudiantil;
import Views.Login;
import javax.swing.*;

public class OpUsuarioController {
    private Op_Usuario view;
    private String usuarioActual;

    public OpUsuarioController(Op_Usuario view, String usuario) {
        this.view = view;
        this.usuarioActual = usuario;
        configurarListeners();
    }

    private void configurarListeners() {
        view.addReservaListener(e -> abrirMenuComedor());
        view.addHistorialListener(e -> mostrarHistorial());
        view.addRecargarListener(e -> abrirMonedero());
        view.addSalirListener(e -> salir());
    }

    private void abrirMenuComedor() {
        view.cerrarVentana();
        SwingUtilities.invokeLater(() -> {
            MenuComedorUniversitario menuView = new MenuComedorUniversitario(usuarioActual);
            MenuComedorController menuController = new MenuComedorController(menuView, usuarioActual);
            menuView.setVisible(true);
        });
    }

    private void abrirMonedero() {
        view.cerrarVentana();
        SwingUtilities.invokeLater(() -> {
            MonederoEstudiantil monederoView = new MonederoEstudiantil(usuarioActual);
            MonederoController monederoController = new MonederoController(monederoView, usuarioActual);
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
}
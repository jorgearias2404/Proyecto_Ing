package controller;

import Views.MenuComedorUniversitario;
import Views.Op_Admin;
import Views.Op_Usuario;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuComedorController {
    private final MenuComedorUniversitario view;
    private final String usuarioActual;
    private final boolean esAdmin;
    private final List<String> selecciones;
  

    public MenuComedorController(MenuComedorUniversitario view, String usuario, boolean esAdmin) {
        if (view == null) {
            throw new IllegalArgumentException("La vista no puede ser nula");
        }
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario no puede estar vacío");
        }

        this.view = view;
        this.usuarioActual = usuario;
        this.esAdmin = esAdmin;
        this.selecciones = new ArrayList<>();
        configurarListeners();
        view.setController(this);
    }

 

    private void configurarListeners() {
        view.setGuardarListener(this::manejarReserva);
        view.setAtrasListener(this::manejarRegreso);
    }

    public void agregarSeleccion(String seleccion) {
        selecciones.add(seleccion);
    }

    public void removerSeleccion(String seleccion) {
        selecciones.remove(seleccion);
    }

    private void manejarReserva(ActionEvent e) {
        try {
            if (selecciones.isEmpty()) {
                view.mostrarError("Debe seleccionar al menos un menú");
                return;
            }

            StringBuilder mensaje = new StringBuilder("Reserva exitosa para:\n");
            for (String seleccion : selecciones) {
                mensaje.append("- ").append(seleccion).append("\n");
            }
            mensaje.append("Usuario: ").append(usuarioActual);
            
            JOptionPane.showMessageDialog(view, mensaje.toString(), "Reserva Exitosa", JOptionPane.INFORMATION_MESSAGE);
            selecciones.clear();
        } catch (Exception ex) {
            manejarError("Error al procesar reserva", ex);
        }
    }

    private void manejarRegreso(ActionEvent e) {
        try {
            view.cerrarVentana();
            SwingUtilities.invokeLater(() -> {
                if (esAdmin) {
                    Op_Admin opAdmin = new Op_Admin(usuarioActual, true);
                    new OpAdminController(opAdmin, usuarioActual, true);
                    opAdmin.setVisible(true);
                } else {
                    Op_Usuario opUsuario = new Op_Usuario(usuarioActual, false);
                    new OpUsuarioController(opUsuario, usuarioActual, false);
                    opUsuario.setVisible(true);
                }
            });
        } catch (Exception ex) {
            manejarError("Error al regresar", ex);
        }
    }

    private void manejarError(String mensaje, Exception e) {
        System.err.println(mensaje + ": " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(view, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
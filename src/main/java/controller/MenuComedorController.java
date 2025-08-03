package controller;

import Views.MenuComedorUniversitario;
import Views.Op_Admin;
import Views.Op_Usuario;
import javax.swing.*;
//import Services.AuthService;
import java.awt.*;

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

 

    private void initController() {
        view.setAtrasListener(e -> volverAOpUsuario());
    }
    
 private void volverAOpUsuario() {
    view.cerrarVentana();
    SwingUtilities.invokeLater(() -> {
        Op_Usuario opUsuario = new Op_Usuario(usuarioActual, esAdmin);
        new OpUsuarioController(opUsuario, usuarioActual, esAdmin); // Conectamos el controlador
        opUsuario.setVisible(true);
    });
}

    // Métodos getter para colores 
    public Color getColorFondo() { return COLOR_FONDO; }
    public Color getColorTexto() { return COLOR_TEXTO; }
    public Color getColorInput() { return COLOR_INPUT; }
    public Color getColorBorde() { return COLOR_BORDE; }
    public Color getColorBoton() { return COLOR_BOTON; }
    public int getAnchoImagen() { return ANCHO_IMAGEN; }
    public int getAltoImagen() { return ALTO_IMAGEN; }
}
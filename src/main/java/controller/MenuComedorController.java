package controller;

import Views.MenuComedorUniversitario;
import Views.Op_Usuario;
import javax.swing.*;
//import Services.AuthService;
import java.awt.*;

public class MenuComedorController {
    private final MenuComedorUniversitario view;
    private final String usuarioActual;
    private final boolean esAdmin;
    
    private static final Color COLOR_FONDO = new Color(7, 64, 91);
    private static final Color COLOR_TEXTO = new Color(240, 240, 240);
    private static final Color COLOR_INPUT = new Color(151, 188, 199);
    private static final Color COLOR_BORDE = new Color(100, 100, 100);
    private static final Color COLOR_BOTON = new Color(151, 188, 199);
    private static final int ANCHO_IMAGEN = 120;
    private static final int ALTO_IMAGEN = 120;

    public MenuComedorController(MenuComedorUniversitario view, String usuario, boolean esAdmin) {
        this.view = view;
        this.usuarioActual = usuario;
        this.esAdmin = esAdmin;
        initController();
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
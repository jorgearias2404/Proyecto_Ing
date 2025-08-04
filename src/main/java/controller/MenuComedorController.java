package controller;

import Views.MenuComedorUniversitario;
import Views.Op_Admin;
import Views.Op_Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuComedorController {
    private final MenuComedorUniversitario view;
    private final String usuarioActual;
    private final boolean esAdmin;
    private final List<String> selecciones;
    
    // Constantes para colores y dimensiones
    private static final Color COLOR_FONDO = new Color(7, 64, 91);
    private static final Color COLOR_TEXTO = new Color(240, 240, 240);
    private static final Color COLOR_INPUT = new Color(151, 188, 199);
    private static final Color COLOR_BORDE = new Color(100, 100, 100);
    private static final Color COLOR_BOTON = new Color(151, 188, 199);
    private static final int ANCHO_IMAGEN = 100;
    private static final int ALTO_IMAGEN = 100;

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
        view.setAtrasListener(e -> volverAVistaAnterior());
    }
    
    private void volverAVistaAnterior() {
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
    }

    // Métodos para manejar selecciones
    public void agregarSeleccion(String seleccion) {
        selecciones.add(seleccion);
    }
    
    public void removerSeleccion(String seleccion) {
        selecciones.remove(seleccion);
    }
    
    public List<String> getSelecciones() {
        return new ArrayList<>(selecciones);
    }

    // Métodos getter para colores y dimensiones
    public Color getColorFondo() { 
        return COLOR_FONDO; 
    }
    
    public Color getColorTexto() { 
        return COLOR_TEXTO; 
    }
    
    public Color getColorInput() { 
        return COLOR_INPUT; 
    }
    
    public Color getColorBorde() { 
        return COLOR_BORDE; 
    }
    
    public Color getColorBoton() { 
        return COLOR_BOTON; 
    }
    
    public int getAnchoImagen() { 
        return ANCHO_IMAGEN; 
    }
    
    public int getAltoImagen() { 
        return ALTO_IMAGEN; 
    }
}
package controller;

import Views.MenuComedorUniversitario;
import Views.Op_Usuario;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuComedorController {
    private final MenuComedorUniversitario view;
    private final List<String> selecciones;
    
    // Colores y configuraciones
    private static final Color COLOR_FONDO = new Color(7, 64, 91);
    private static final Color COLOR_TEXTO = new Color(240, 240, 240);
    private static final Color COLOR_INPUT = new Color(151, 188, 199);
    private static final Color COLOR_BORDE = new Color(100, 100, 100);
    private static final Color COLOR_BOTON = new Color(151, 188, 199);
    private static final int ANCHO_IMAGEN = 120;
    private static final int ALTO_IMAGEN = 120;

    public MenuComedorController(MenuComedorUniversitario view) {
        this.view = view;
        this.selecciones = new ArrayList<>();
        initController();
    }

    private void initController() {
        view.setGuardarListener(e -> guardarSelecciones());
        view.setAtrasListener(e -> volverAOpUsuario());
    }

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

    public void agregarSeleccion(String seleccion) {
        System.out.println("Agregando selección: " + seleccion); 
        if (!selecciones.contains(seleccion)) {
            selecciones.add(seleccion);
            System.out.println("Selecciones actuales: " + selecciones);
        }
    }

     public void removerSeleccion(String seleccion) {
        System.out.println("Removiendo selección: " + seleccion); 
        selecciones.remove(seleccion);
        System.out.println("Selecciones actuales: " + selecciones); 
    }

     private void guardarSelecciones() {
        System.out.println("Intentando guardar. Total selecciones: " + selecciones.size()); 
        
        if (selecciones.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "No hay selecciones para guardar.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (FileWriter writer = new FileWriter("selecciones_menu.txt")) {
            for (String seleccion : selecciones) {
                writer.write(seleccion + "\n");
            }
            JOptionPane.showMessageDialog(view, 
                "¡Se guardaron " + selecciones.size() + " selecciones!", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, 
                "Error al guardar: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverAOpUsuario() {
        view.cerrarVentana();
        SwingUtilities.invokeLater(() -> Op_Usuario.main(new String[]{}));
    }
}
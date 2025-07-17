package controller;

import Views.MenuComedorUniversitario;
import Views.Op_Usuario;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MenuComedorController {
    private final MenuComedorUniversitario view;
    private final List<String> selecciones;
    private final String usuarioActual;
    
    private static final Color COLOR_FONDO = new Color(7, 64, 91);
    private static final Color COLOR_TEXTO = new Color(240, 240, 240);
    private static final Color COLOR_INPUT = new Color(151, 188, 199);
    private static final Color COLOR_BORDE = new Color(100, 100, 100);
    private static final Color COLOR_BOTON = new Color(151, 188, 199);
    private static final int ANCHO_IMAGEN = 120;
    private static final int ALTO_IMAGEN = 120;

    public MenuComedorController(MenuComedorUniversitario view, String usuario) {
        this.view = view;
        this.selecciones = new ArrayList<>();
        this.usuarioActual = usuario;
        initController();
    }

    private void initController() {
        view.setGuardarListener(e -> guardarSelecciones());
        view.setAtrasListener(e -> volverAOpUsuario());
    }

    public void agregarSeleccion(String seleccion) {
        if (!selecciones.contains(seleccion)) {
            selecciones.add(seleccion);
        }
    }

    public void removerSeleccion(String seleccion) {
        selecciones.remove(seleccion);
    }

   private void guardarSelecciones() {
    if (selecciones.isEmpty()) {
        JOptionPane.showMessageDialog(view, 
            "No hay selecciones para guardar.", 
            "Advertencia", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    String directorio = "DataBase/selecciones";
    try {
        Files.createDirectories(Paths.get(directorio)); // Crea el directorio si no existe
    } catch (IOException e) {
        JOptionPane.showMessageDialog(view, 
            "Error al crear directorio: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    String nombreArchivo = directorio + "/" + usuarioActual + ".txt"; // Nombre personalizado: usuario.txt

    try (FileWriter writer = new FileWriter(nombreArchivo, true)) { // Modo append=true para añadir al final
        for (String seleccion : selecciones) {
            writer.write(seleccion + "\n"); // Añade cada selección en una nueva línea
        }
        JOptionPane.showMessageDialog(view, 
            "¡Se guardaron " + selecciones.size() + " selecciones para " + usuarioActual + "!", 
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
    SwingUtilities.invokeLater(() -> {
        Op_Usuario opUsuario = new Op_Usuario(usuarioActual);
        new OpUsuarioController(opUsuario, usuarioActual); // Conectamos el controlador
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
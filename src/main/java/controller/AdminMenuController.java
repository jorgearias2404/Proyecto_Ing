
package controller;

import Views.AdminMenuView;
import Views.Op_Usuario;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class AdminMenuController {
    private AdminMenuView view;
    private String usuarioActual;
    private static final String ARCHIVO_MENU = "DataBase/menus/menu_semanal.txt";

    public AdminMenuController(AdminMenuView view, String usuario) {
        this.view = view;
        this.usuarioActual = usuario;
        initController();
    }

    private void initController() {
        view.setGuardarListener(e -> guardarMenu());
        view.setAtrasListener(e -> volverAOpUsuario());
    }

    private void guardarMenu() {
        Map<String, String> datos = view.getDatosMenu();
        
        try {
            Files.createDirectories(Paths.get("DataBase/menus"));
            
            try (FileWriter writer = new FileWriter(ARCHIVO_MENU, true)) { // Modo append
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                
                writer.write("\n--- Actualización ---\n");
                writer.write("Fecha: " + timestamp + "\n");
                writer.write("Admin: " + usuarioActual + "\n");
                writer.write("Día: " + datos.get("dia") + "\n");
                writer.write("Desayuno: " + datos.get("desayuno") + "\n");
                writer.write("Almuerzo: " + datos.get("almuerzo") + "\n");
                writer.write("Postre: " + datos.get("postre") + "\n");
                writer.write("-------------------\n");
                
                JOptionPane.showMessageDialog(view, 
                    "Menú actualizado correctamente para " + datos.get("dia"), 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, 
                "Error al guardar: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverAOpUsuario() {
        view.dispose();
        SwingUtilities.invokeLater(() -> {
            Op_Usuario opUsuario = new Op_Usuario(usuarioActual, true);
            new OpUsuarioController(opUsuario, usuarioActual, true);
            opUsuario.setVisible(true);
        });
    }
}
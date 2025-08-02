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
    private final AdminMenuView view;
    private final String usuarioActual;
    private static final String ARCHIVO_MENU = "Database/menus/menu_semanal.txt";

    public AdminMenuController(AdminMenuView view, String usuario) {
        if (view == null) {
            throw new IllegalArgumentException("La vista no puede ser nula");
        }
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario no puede estar vacío");
        }

        this.view = view;
        this.usuarioActual = usuario;
        initController();
    }

    private void initController() {
        view.setGuardarListener(e -> guardarMenu());
        view.setAtrasListener(e -> volverAOpUsuario());
        cargarMenuExistente();
    }

    private void cargarMenuExistente() {
        try {
            if (Files.exists(Paths.get(ARCHIVO_MENU))) {
                String contenido = new String(Files.readAllBytes(Paths.get(ARCHIVO_MENU)));
                view.mostrarMenuExistente(contenido);
            } else {
                view.mostrarMenuExistente("No existe un menú previo.\nPuede crear uno nuevo.");
            }
        } catch (IOException e) {
            mostrarError("Error al cargar menú existente", e);
        }
    }

    private void guardarMenu() {
        try {
            Map<String, String> datos = validarDatosMenu();
            guardarMenuEnArchivo(datos);
            cargarMenuExistente(); // Actualizar vista con los nuevos datos
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            mostrarError("Error al guardar menú", e);
        }
    }

    private Map<String, String> validarDatosMenu() {
        Map<String, String> datos = view.getDatosMenu();
        
        if (datos == null) {
            throw new IllegalArgumentException("No se pudieron obtener los datos del menú");
        }
        
        if (datos.get("dia") == null || datos.get("desayuno") == null || datos.get("almuerzo") == null) {
            throw new IllegalArgumentException("Error: Datos del menú no válidos");
        }

        if (datos.get("dia").isEmpty() || datos.get("desayuno").isEmpty() || datos.get("almuerzo").isEmpty()) {
            throw new IllegalArgumentException("Debe completar al menos día, desayuno y almuerzo");
        }

        return datos;
    }

    private void guardarMenuEnArchivo(Map<String, String> datos) throws IOException {
        Files.createDirectories(Paths.get(ARCHIVO_MENU).getParent());
        
        try (FileWriter writer = new FileWriter(ARCHIVO_MENU, true)) {
            String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            writer.write("\n--- Actualización ---\n");
            writer.write("Fecha: " + timestamp + "\n");
            writer.write("Admin: " + usuarioActual + "\n");
            writer.write("Día: " + datos.get("dia") + "\n");
            writer.write("Desayuno: " + datos.get("desayuno") + "\n");
            writer.write("Almuerzo: " + datos.get("almuerzo") + "\n");
            
            if (datos.get("postre") != null && !datos.get("postre").isEmpty()) {
                writer.write("Postre: " + datos.get("postre") + "\n");
            }
            
            writer.write("-------------------\n");
            
            JOptionPane.showMessageDialog(view, 
                "Menú actualizado correctamente para " + datos.get("dia"), 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
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

    private void mostrarError(String mensaje, Exception e) {
        System.err.println(mensaje + ": " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, 
            mensaje + ": " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
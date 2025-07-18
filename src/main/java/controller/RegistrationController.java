package controller;

import Views.Registration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
//import java.nio.file.Path;
import java.nio.file.Paths;

public class RegistrationController {
    private final Registration view;
    private static final String USERS_FILE = getAbsoluteDatabasePath();

    public RegistrationController(Registration view) {
        this.view = view;
        view.setRegisterListener(new RegisterListener());
        System.out.println("RUTA DEFINITIVA DEL ARCHIVO: " + USERS_FILE);
    }

    private static String getAbsoluteDatabasePath() {
        // 1. Primero intentamos encontrar la ruta base del proyecto
        String projectPath = System.getProperty("user.dir");
        System.out.println("Directorio actual: " + projectPath);

        // 2. Posibles ubicaciones del archivo
        String[] possiblePaths = {
            projectPath + "/Database/selecciones/usuarios_administradores.txt",
            projectPath + "/src/Database/selecciones/usuarios_administradores.txt",
            projectPath + "/target/classes/Database/selecciones/usuarios_administradores.txt",
        };

        // 3. Buscar el archivo en las ubicaciones posibles
        for (String path : possiblePaths) {
            if (Files.exists(Paths.get(path))) {
                System.out.println("Archivo encontrado en: " + path);
                return path;
            }
            System.out.println("No encontrado en: " + path);
        }

        // 4. Si no se encuentra, crear la estructura de directorios
        String defaultPath = projectPath + "/Database/selecciones/usuarios_administradores.txt";
        try {
            Files.createDirectories(Paths.get(defaultPath).getParent());
            System.out.println("Estructura de directorios creada en: " + defaultPath);
            return defaultPath;
        } catch (IOException e) {
            System.err.println("Error al crear directorios: " + e.getMessage());
            return defaultPath; // Retornamos la ruta igualmente
        }
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                
                ValidarFormularioRegistro val = new ValidarFormularioRegistro();
                if(!val.validateForm(view.getEmail(), view.getPassword(), view.getUsername(), view.getID(), view.getRole())) {
                    view.showError(val.errorMessage);
                    return;
                }

                String userData = String.format("%s|%s|%s|%s (%s)|%s",
                    view.getRole().equals("Administrador") ? "admin" : "user",
                    view.getUsername(),
                    view.getPassword(),
                    view.getUsername(),
                    view.getID(),
                    view.getEmail());

                saveUserData(userData);
                
                view.showMessage("¡Registro exitoso!", "Éxito");
                view.close();
                
            } catch (Exception ex) {
                handleRegistrationError(ex);
            }
        }

        private void saveUserData(String data) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(USERS_FILE, true))) {
                
                if (new File(USERS_FILE).length() > 0) {
                    writer.newLine();
                }
                writer.write(data);
                System.out.println("Datos guardados correctamente en: " + USERS_FILE);
            }
        }

        private void handleRegistrationError(Exception ex) {
            System.err.println("ERROR EN REGISTRO: " + ex.getMessage());
            view.showError("Error al registrar: " + ex.getMessage());
            ex.printStackTrace();
            
            // Mostrar información adicional para debug
            System.out.println("Ruta actual del archivo: " + USERS_FILE);
            System.out.println("El archivo existe: " + new File(USERS_FILE).exists());
        }

    }
}
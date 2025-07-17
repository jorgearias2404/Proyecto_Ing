package controller;

import Views.Registration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RegistrationController {
    private Registration view;
    private static final String USERS_FILE = "src/Database/usuarios_administradores.txt"; // Nombre del archivo de usuarios

    public RegistrationController(Registration view) {
        this.view = view;
        
        // Configurar el listener para el botón de registro
        view.setRegisterListener(new RegisterListener());
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Validar los datos del formulario
            if (!validateForm()) {
                return;
            }

            // Obtener los datos del formulario
            String email = view.getEmail();
            String password = view.getPassword();
            String username = view.getUsername();
            String id = view.getID();
            String role = view.getRole();

            // Determinar el tipo de usuario (user o admin)
            String userType = (role.equals("Administrador")) ? "admin" : "user";

            // Crear la línea en el formato correcto
            String userLine = String.format("%s|%s|%s|%s (%s)", 
                userType, username, password, username, id);

            try {
                // Guardar en el archivo
                saveUserToFile(userLine);
                
                // Mostrar mensaje de éxito
                view.showMessage("Usuario registrado exitosamente!", "Éxito");
                view.close();
            } catch (IOException ex) {
                view.showError("Error al guardar el usuario: " + ex.getMessage());
            }
        }

        private void saveUserToFile(String userLine) throws IOException {
            // Usar try-with-resources para asegurar que el archivo se cierre correctamente
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
                
                 // Si el archivo no está vacío, agregar un salto de línea antes del nuevo registro
                    if (Files.size(Paths.get(USERS_FILE)) > 0) {
                        writer.newLine();
                    }
                writer.write(userLine);
            }
        }

        private boolean validateForm() {
            // Validar email
            if (view.getEmail().isEmpty()) {
                view.showError("El email es requerido");
                return false;
            }

            // Validar contraseña
            String password = view.getPassword();
            if (password.isEmpty()) {
                view.showError("La contraseña es requerida");
                return false;
            }
            if (!isValidPassword(password)) {
                view.showError("La contraseña no cumple con los requisitos");
                return false;
            }

            // Validar nombre de usuario
            String username = view.getUsername();
            if (username.isEmpty()) {
                view.showError("El nombre de usuario es requerido");
                return false;
            }
            if (!username.matches("^[a-zA-Z0-9]+$")) {
                view.showError("El nombre de usuario solo puede contener caracteres alfanuméricos");
                return false;
            }

            // Validar cédula
            String id = view.getID();
            if (id.isEmpty()) {
                view.showError("La cédula es requerida");
                return false;
            }
            if (!id.matches("^[0-9]+$")) {
                view.showError("La cédula solo puede contener números");
                return false;
            }

            // Validar rol
            if (view.getRole().equals("Rol")) {
                view.showError("Debe seleccionar un rol");
                return false;
            }

            return true;
        }

        private boolean isValidPassword(String password) {
            // Validación 1: Al menos 15 caracteres
            if (password.length() >= 15) {
                return true;
            }
            
            // Validación 2: Al menos 8 caracteres con un número y una minúscula
            if (password.length() >= 8 && 
                password.matches(".*[0-9].*") && 
                password.matches(".*[a-z].*")) {
                return true;
            }
            
            return false;
        }
    }
}
package controller;

import Views.Registration;
import Views.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class RegistrationController {
    private final Registration view;
    private static final String USERS_FILE = getAbsoluteDatabasePath();

    public RegistrationController(Registration view) {
        this.view = view;
        view.setRegisterListener(new RegisterListener());
        System.out.println("RUTA DEFINITIVA DEL ARCHIVO: " + USERS_FILE);
    }

    private static String getAbsoluteDatabasePath() {
        String projectPath = System.getProperty("user.dir");
        System.out.println("Directorio actual: " + projectPath);

        String[] possiblePaths = {
            projectPath + "/Database/selecciones/usuarios_administradores.txt",
            projectPath + "/src/Database/selecciones/usuarios_administradores.txt",
            projectPath + "/target/classes/Database/selecciones/usuarios_administradores.txt",
        };

        for (String path : possiblePaths) {
            if (Files.exists(Paths.get(path))) {
                System.out.println("Archivo encontrado en: " + path);
                return path;
            }
            System.out.println("No encontrado en: " + path);
        }

        String defaultPath = projectPath + "/Database/selecciones/usuarios_administradores.txt";
        try {
            Files.createDirectories(Paths.get(defaultPath).getParent());
            System.out.println("Estructura de directorios creada en: " + defaultPath);
            return defaultPath;
        } catch (IOException e) {
            System.err.println("Error al crear directorios: " + e.getMessage());
            return defaultPath;
        }
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Validación de rol administrador
                if (view.getRole().equals("Administrador")) {
                    String adminPassword = view.showAdminPasswordDialog();
                    if (adminPassword == null) {
                        view.showError("Debe ingresar la contraseña de administrador");
                        return;
                    }
                    
                    ValidarFormularioRegistro validator = new ValidarFormularioRegistro();
                    if (!validator.validateAdminPassword(adminPassword)) {
                        view.showError("Contraseña de administrador incorrecta");
                        return;
                    }
                }
                
                // Validación del formulario
                ValidarFormularioRegistro val = new ValidarFormularioRegistro();
                if(!val.validateForm(view.getEmail(), view.getPassword(), 
                   view.getUsername(), view.getID(), view.getRole())) {
                    view.showError(val.errorMessage);
                    return;
                }

                // Normalización de datos
                String email = view.getEmail().trim().toLowerCase();
                String id = view.getID().trim();
                String username = view.getUsername().trim();
                String password = encryptPassword(view.getPassword()); // Contraseña encriptada
                String role = view.getRole().equals("Administrador") ? "admin" : "user";

                // Verificación de duplicados
                String duplicateError = checkForDuplicates(email, id, username);
                if (duplicateError != null) {
                    view.showError(duplicateError);
                    return;
                }

                // Creación del registro
                String userData = String.format("%s|%s|%s|%s (%s)|%s",
                    role,
                    username,
                    password, // Guarda la contraseña encriptada
                    username,
                    id,
                    email);

                saveUserData(userData);
                
                view.showMessage("¡Registro exitoso!", "Éxito");
                view.close();
                
                // Volver al login
                SwingUtilities.invokeLater(() -> {
                    Login login = new Login();
                    new LoginController(login);
                    login.setVisible(true);
                });
                
            } catch (Exception ex) {
                handleRegistrationError(ex);
            }
        }

        private String encryptPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashedBytes = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte b : hashedBytes) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                System.err.println("Error al encriptar contraseña: " + e.getMessage());
                throw new RuntimeException("Error en el sistema de seguridad");
            }
        }

        private String checkForDuplicates(String email, String id, String username) throws IOException {
            File file = new File(USERS_FILE);
            if (!file.exists()) return null;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6) {
                        // Verificar email
                        String existingEmail = parts[5].trim().toLowerCase();
                        if (existingEmail.equals(email)) {
                            return "El correo electrónico ya está registrado";
                        }
                        
                        // Verificar cédula
                        String namePart = parts[3].trim();
                        int start = namePart.indexOf("(");
                        int end = namePart.indexOf(")");
                        if (start != -1 && end != -1) {
                            String existingId = namePart.substring(start + 1, end).trim();
                            if (existingId.equals(id)) {
                                return "La cédula ya está registrada";
                            }
                        }
                        
                        // Verificar nombre de usuario
                        String existingUsername = parts[1].trim().toLowerCase();
                        if (existingUsername.equals(username.toLowerCase())) {
                            return "El nombre de usuario ya está en uso";
                        }
                    }
                }
            }
            return null;
        }

        private void saveUserData(String data) throws IOException {
            File file = new File(USERS_FILE);
            boolean fileExists = file.exists() && file.length() > 0;
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                if (fileExists) {
                    writer.newLine();
                }
                writer.write(data);
                System.out.println("Datos guardados correctamente en: " + USERS_FILE);
            }
        }

        private void handleRegistrationError(Exception ex) {
            String errorMsg = "Error al registrar: " + ex.getMessage();
            System.err.println("ERROR EN REGISTRO: " + errorMsg);
            ex.printStackTrace();
            
            // Mostrar mensaje de error más amigable
            if (ex instanceof IOException) {
                errorMsg = "Error al acceder a la base de datos. Intente nuevamente.";
            } else if (ex instanceof SecurityException) {
                errorMsg = "Problema de permisos. Contacte al administrador.";
            }
            
            JOptionPane.showMessageDialog(view, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Ruta actual del archivo: " + USERS_FILE);
            System.out.println("El archivo existe: " + new File(USERS_FILE).exists());
        }
    }
}
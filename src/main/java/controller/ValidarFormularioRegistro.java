package controller;

public class ValidarFormularioRegistro {
    public String errorMessage = "";
    
    public boolean validateForm(String email, String password, String username, String id, String role) {
        // Validar campos vacíos
        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || id.isEmpty() || role.equals("Rol")) {
            errorMessage = "Todos los campos marcados con * son obligatorios";
            return false;
        }

        // Validar formato de email
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessage = "El formato del email no es válido";
            return false;
        }

        // Validar contraseña
        if (!isValidPassword(password)) {
            errorMessage = "La contraseña debe tener al menos 15 caracteres O al menos 8 caracteres incluyendo un número y una letra minúscula";
            return false;
        }

        // Validar nombre de usuario
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            errorMessage = "El nombre de usuario solo puede contener caracteres alfanuméricos";
            return false;
        }

        // Validar cédula (solo números)
        if (!id.matches("^\\d+$")) {
            errorMessage = "La cédula solo puede contener números";
            return false;
        }

        // Validación especial para administradores
        if (role.equals("Administrador") && !username.startsWith("admin_")) {
            errorMessage = "Los administradores deben usar el prefijo 'admin_' en su nombre de usuario";
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        // Opción 1: 15+ caracteres
        if (password.length() >= 15) {
            return true;
        }
        
        // Opción 2: 8+ caracteres con al menos un número y una minúscula
        return password.length() >= 8 && 
               password.matches(".*\\d.*") && 
               password.matches(".*[a-z].*");
    }
}
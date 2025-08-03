package controller;

public class ValidarFormularioRegistro {
    public String errorMessage = "";
    private static final String ADMIN_PASSWORD = "admin12345";  // Contraseña de validación para rol Admin

    public boolean validateForm(String email, String password, String username, String id, String role) {
        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || id.isEmpty() || role.equals("Rol")) {
            errorMessage = "Todos los campos marcados con * son obligatorios";
            return false;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            errorMessage = "El formato del email no es válido";
            return false;
        }

        // Validación de contraseña personal (aplica para todos, incluidos admins)
        if (!isValidPassword(password)) {
            errorMessage = "La contraseña debe tener al menos 15 caracteres O al menos 8 caracteres incluyendo un número y una letra minúscula";
            return false;
        }

        if (!username.matches("^[a-zA-Z0-9]+$")) {
            errorMessage = "El nombre de usuario solo puede contener caracteres alfanuméricos";
            return false;
        }

        if (!id.matches("^\\d+$")) {
            errorMessage = "La cédula solo puede contener números";
            return false;
        }

        return true;
    }

    // Valida la contraseña de administrador (solo para verificación de rol)
    public boolean validateAdminPassword(String inputPassword) {
        return inputPassword.equals(ADMIN_PASSWORD);
    }

    // Valida la contraseña personal (reglas para todos los usuarios)
    private boolean isValidPassword(String password) {
        if (password.length() >= 15) {
            return true;
        }
        return password.length() >= 8 && 
               password.matches(".*\\d.*") && 
               password.matches(".*[a-z].*");
    }
}
package controller;

public class ValidarFormularioRegistro {
    public String errorMessage = "";
    private static final String ADMIN_PASSWORD = "admin12345";

    public boolean validateForm(String email, String password, String username, String id, String role) {
        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || id.isEmpty() || role.equals("Rol")) {
            errorMessage = "Todos los campos marcados con * son obligatorios";
            return false;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            errorMessage = "El formato del email no es válido";
            return false;
        }

        if (!isValidPassword(password, role)) {
            if (role.equals("Administrador")) {
                errorMessage = "Para rol Administrador, la contraseña debe ser: " + ADMIN_PASSWORD;
            } else {
                errorMessage = "La contraseña debe tener al menos 15 caracteres O al menos 8 caracteres incluyendo un número y una letra minúscula";
            }
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

    private boolean isValidPassword(String password, String role) {
        if (role.equals("Administrador")) {
            return password.equals(ADMIN_PASSWORD);
        }
        
        if (password.length() >= 15) {
            return true;
        }
        
        return password.length() >= 8 && 
               password.matches(".*\\d.*") && 
               password.matches(".*[a-z].*");
    }
}
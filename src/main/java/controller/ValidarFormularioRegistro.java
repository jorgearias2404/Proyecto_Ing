package controller;

public class ValidarFormularioRegistro {
    public String errorMessage;

    public ValidarFormularioRegistro() {
        this.errorMessage = "exito";
    }

    public boolean validateForm(String email, String password, String username, String id, String role) {
        // Validar email
            if (email.isEmpty()) {
                errorMessage = "El email es requerido";
                return false;
            }
            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                errorMessage = "El nombre de usuario solo puede contener caracteres alfanuméricos";
                return false;
            }

            // Validar contraseña
            if (password.isEmpty()) {
                errorMessage = "La contraseña es requerida";
                return false;
            }
            if (!isValidPassword(password)) {
                errorMessage = "La contraseña no cumple con los requisitos";
                return false;
            }

            // Validar nombre de usuario
            if (username.isEmpty()) {
                errorMessage = "El nombre de usuario es requerido";
                return false;
            }
            if (!username.matches("^[a-zA-Z0-9]+$")) {
                errorMessage = "El nombre de usuario solo puede contener caracteres alfanuméricos";
                return false;
            }

            // Validar cédula
            if (id.isEmpty()) {
                errorMessage = "La cédula es requerida";
                return false;
            }
            if (!id.matches("^[0-9]+$")) {
                errorMessage = "La cédula solo puede contener números";
                return false;
            }

            // Validar rol
        if (role.equals("Rol")) {
            errorMessage = "Debe seleccionar un rol";
            return false;
        }

        return true;
    }

    private static boolean isValidPassword(String password) {
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

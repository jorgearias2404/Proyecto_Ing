package Services;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    // Credenciales hardcodeadas con estructura correcta
    private static final Map<String, Map<String, String>> USUARIOS = new HashMap<>();
    private static final Map<String, Map<String, String>> ADMINISTRADORES = new HashMap<>();

    static {
        // Datos de usuarios normales
        Map<String, String> usuario1 = new HashMap<>();
        usuario1.put("password", "123");
        usuario1.put("nombre", "Usuario Test");
        USUARIOS.put("test", usuario1);

        Map<String, String> usuario2 = new HashMap<>();
        usuario2.put("password", "clave123");
        usuario2.put("nombre", "Usuario Normal");
        USUARIOS.put("user", usuario2);

        // Datos de administradores
        Map<String, String> admin1 = new HashMap<>();
        admin1.put("password", "456");
        admin1.put("nombre", "Admin Principal");
        ADMINISTRADORES.put("admin", admin1);

        Map<String, String> admin2 = new HashMap<>();
        admin2.put("password", "admin123");
        admin2.put("nombre", "Admin Secundario");
        ADMINISTRADORES.put("admin1", admin2);
    }

    public static boolean validarLogin(String username, String password, boolean esAdmin) {
        Map<String, String> credenciales = esAdmin ? ADMINISTRADORES.get(username) : USUARIOS.get(username);
        return credenciales != null && credenciales.get("password").equals(password);
    }

    public static String obtenerNombre(String username, boolean esAdmin) {
        Map<String, String> datos = esAdmin ? ADMINISTRADORES.get(username) : USUARIOS.get(username);
        return datos != null ? datos.get("nombre") : "Invitado";
    }
}
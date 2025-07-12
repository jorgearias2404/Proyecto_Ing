package Services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static final String ARCHIVO_USUARIOS = "DATABASE/usuarios_administradores.txt";
    private static Map<String, Map<String, String>> usuarios = new HashMap<>();
    private static Map<String, Map<String, String>> administradores = new HashMap<>();

    static {
        cargarDatos();
    }

    private static void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            String seccionActual = null;
            Map<String, String> datosActual = null;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("[") && linea.endsWith("]")) {
                    seccionActual = linea.substring(1, linea.length() - 1);
                    if ("USUARIO".equals(seccionActual)) {
                        datosActual = new HashMap<>();
                    } else if ("ADMIN".equals(seccionActual)) {
                        datosActual = new HashMap<>();
                    }
                } else if (datosActual != null && linea.contains("=")) {
                    String[] partes = linea.split("=", 2);
                    datosActual.put(partes[0].trim(), partes[1].trim());
                    if ("USUARIO".equals(seccionActual)) {
                        usuarios.put(datosActual.get("username"), datosActual);
                    } else if ("ADMIN".equals(seccionActual)) {
                        administradores.put(datosActual.get("username"), datosActual);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validarLogin(String username, String password, boolean esAdmin) {
        Map<String, String> userData = esAdmin ? administradores.get(username) : usuarios.get(username);
        return userData != null && userData.get("password").equals(password);
    }

    public static String obtenerNombre(String username, boolean esAdmin) {
        Map<String, String> userData = esAdmin ? administradores.get(username) : usuarios.get(username);
        return userData != null ? userData.get("nombre") : null;
    }
}
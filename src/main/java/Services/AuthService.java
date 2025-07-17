package Services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    // Cambia esta ruta según donde esté tu archivo
    private static final String USUARIOS_FILE = "src/Database/usuarios_administradores.txt";
    private static final Map<String, Map<String, String>> USUARIOS = new HashMap<>();
    private static final Map<String, Map<String, String>> ADMINISTRADORES = new HashMap<>();

    static {
        System.out.println("Buscando archivo en: " + new File(USUARIOS_FILE).getAbsolutePath());
        cargarCredenciales();
    }

    private static void cargarCredenciales() {
        try {
            File archivo = new File(USUARIOS_FILE);
            if (!archivo.exists()) {
                throw new Exception("Archivo no encontrado en: " + archivo.getAbsolutePath());
            }

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    linea = linea.trim();
                    if (linea.isEmpty() || linea.startsWith("#")) {
                        continue;
                    }

                    // Eliminar números de línea si existen
                    linea = linea.replaceAll("^\\d+\\.?\\s*", "").trim();
                    
                    String[] partes = linea.split("\\s*\\|\\s*"); // Split que ignora espacios alrededor del |
                    if (partes.length == 4) {
                        String tipo = partes[0].trim().toLowerCase();
                        String username = partes[1].trim();
                        String password = partes[2].trim();
                        String nombre = partes[3].trim();

                        Map<String, String> datos = new HashMap<>();
                        datos.put("password", password);
                        datos.put("nombre", nombre);

                        if (tipo.equals("admin")) {
                            ADMINISTRADORES.put(username, datos);
                            System.out.println("Admin registrado: " + username);
                        } else {
                            USUARIOS.put(username, datos);
                            System.out.println("Usuario registrado: " + username);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR CRÍTICO: " + e.getMessage());
            cargarCredencialesPorDefecto();
        }
    }

    private static void cargarCredencialesPorDefecto() {
        // Usuario por defecto
        Map<String, String> usuario = new HashMap<>();
        usuario.put("password", "123");
        usuario.put("nombre", "Usuario Default");
        USUARIOS.put("default", usuario);

        // Admin por defecto
        Map<String, String> admin = new HashMap<>();
        admin.put("password", "admin");
        admin.put("nombre", "Admin Default");
        ADMINISTRADORES.put("admin", admin);
    }

    public static boolean validarLogin(String username, String password, boolean esAdmin) {
        Map<String, String> credenciales = esAdmin ? ADMINISTRADORES.get(username) : USUARIOS.get(username);
        if (credenciales == null) {
            System.out.println("Usuario no encontrado: " + username);
            return false;
        }
        boolean coincide = credenciales.get("password").equals(password);
        if (!coincide) {
            System.out.println("Contraseña incorrecta para: " + username);
        }
        return coincide;
    }

    public static String obtenerNombre(String username, boolean esAdmin) {
        Map<String, String> datos = esAdmin ? ADMINISTRADORES.get(username) : USUARIOS.get(username);
        return datos != null ? datos.get("nombre") : "Invitado";
    }
}
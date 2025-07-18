package Services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static final String USUARIOS_FILE = findDatabaseFile("selecciones/usuarios_administradores.txt");
    private static final Map<String, Map<String, String>> USUARIOS = new HashMap<>();
    private static final Map<String, Map<String, String>> ADMINISTRADORES = new HashMap<>();

    static {
        System.out.println("[AuthService] Archivo de usuarios: " + USUARIOS_FILE);
        cargarCredenciales();
    }

    private static String findDatabaseFile(String relativePath) {
        // Possible locations to check (relative to different execution contexts)
        String[] possiblePaths = {
            "Database/" + relativePath,
            "../Database/" + relativePath,
            "src/Database/" + relativePath,
            "../src/Database/" + relativePath,
            "target/classes/Database/" + relativePath,
            System.getProperty("user.dir") + "/Database/" + relativePath
        };

        for (String path : possiblePaths) {
            System.out.println("[AuthService] Checking path: " + path);
            if (Files.exists(Paths.get(path))) {
                System.out.println("[AuthService] Found at: " + path);
                return path;
            }
        }

        // Fallback to default location
        System.err.println("[AuthService] No se encontró el archivo, usando ubicación por defecto");
        return "Database/" + relativePath;
    }

    private static void cargarCredenciales() {
        try {
            File archivo = new File(USUARIOS_FILE);
            System.out.println("[AuthService] Ruta absoluta: " + archivo.getAbsolutePath());

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

                    // Parsear la línea
                    String[] partes = linea.split("\\s*\\|\\s*");
                    if (partes.length >= 4) {
                        String tipo = partes[0].trim().toLowerCase();
                        String username = partes[1].trim();
                        String password = partes[2].trim();
                        String nombre = partes[3].trim();

                        Map<String, String> datos = new HashMap<>();
                        datos.put("password", password);
                        datos.put("nombre", nombre);

                        if (tipo.equals("admin")) {
                            ADMINISTRADORES.put(username, datos);
                            System.out.println("[AuthService] Admin cargado: " + username);
                        } else {
                            USUARIOS.put(username, datos);
                            System.out.println("[AuthService] Usuario cargado: " + username);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[AuthService] ERROR: " + e.getMessage());
            cargarCredencialesPorDefecto();
        }
    }

    private static void cargarCredencialesPorDefecto() {
        System.out.println("[AuthService] Cargando credenciales por defecto");
        
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

    public boolean validarLogin(String username, String password, boolean esAdmin) {
        Map<String, String> credenciales = esAdmin ? 
            ADMINISTRADORES.get(username) : USUARIOS.get(username);
            
        if (credenciales == null) {
            System.out.println("[AuthService] Usuario no encontrado: " + username);
            return false;
        }
        
        boolean coincide = credenciales.get("password").equals(password);
        System.out.printf("[AuthService] Validación %s para %s: %b%n",
            (esAdmin ? "admin" : "usuario"), username, coincide);
            
        return coincide;
    }

    public String obtenerNombre(String username, boolean esAdmin) {
        Map<String, String> datos = esAdmin ? 
            ADMINISTRADORES.get(username) : USUARIOS.get(username);
            
        return datos != null ? datos.get("nombre") : "Invitado";
    }

    // Método para debug
    public static void printFileLocation() {
        System.out.println("[AuthService] Ubicación actual del archivo: " + USUARIOS_FILE);
        System.out.println("[AuthService] Ruta absoluta: " + 
            new File(USUARIOS_FILE).getAbsolutePath());
    }
}
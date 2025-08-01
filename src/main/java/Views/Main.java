package Views;

import javax.swing.SwingUtilities;
import java.io.File;
import controller.LoginController;

public class Main {
    public static void main(String[] args) {
        // Establecer el working directory correcto
        setWorkingDirectory();
        
        SwingUtilities.invokeLater(() -> {
            Login loginView = new Login();
            new LoginController(loginView);
            loginView.setVisible(true);
        });
    }

    private static void setWorkingDirectory() {
        try {
            File file = new File(".");
            System.out.println("Working directory: " + file.getCanonicalPath());
            
            // Si estamos en el directorio incorrecto, intentar cambiarlo
            if (!new File("Database").exists()) {
                File newDir = new File("src");
                if (newDir.exists()) {
                    System.setProperty("user.dir", newDir.getCanonicalPath());
                    System.out.println("Changed working dir to: " + newDir.getCanonicalPath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package Views;
import javax.swing.SwingUtilities;

import controller.LoginController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login ventana = new Login();
            new LoginController(ventana);
            ventana.setVisible(true);
        });
    }
}
package Views;
import javax.swing.SwingUtilities;

import controller.LoginController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginView = new Login();
            new LoginController(loginView);
            loginView.setVisible(true);
        });
    }
}
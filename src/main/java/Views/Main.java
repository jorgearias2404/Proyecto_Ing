package Views;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Modulo1 ventana = new Modulo1();
            ventana.setVisible(true);
        });
    }
}
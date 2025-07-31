package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Views.VistaImagen;

public class ControladorImagen implements ActionListener {
    VistaImagen ventanaImagen;
    String imagenesPath;

    public ControladorImagen() {
        ventanaImagen = new VistaImagen();
        ventanaImagen.setVisible(true);
        this.ventanaImagen.setControlador((ActionListener) this);
      
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "CARGAR":
                String filePath = ventanaImagen.showFileChooser();
                if (filePath != null) {
                    //ventanaRecon.updatePathLabel(filePath);
                    // Aquí puedes pasar la ruta al modelo para procesamiento
                    System.out.println("Ruta seleccionada: " + filePath);
                    if(hacerComparacion(filePath)) {

                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean hacerComparacion(String filePath) {
        return true;
    }
}

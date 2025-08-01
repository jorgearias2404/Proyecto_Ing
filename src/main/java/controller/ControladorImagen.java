package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Services.ComparadorImagen;
import Services.ReadDatabase;
import Views.VistaImagen;

public class ControladorImagen implements ActionListener {
    VistaImagen ventanaImagen;
    String imagenesPath;
    ReadDatabase lector;
    ComparadorImagen comparador;

    public ControladorImagen() {
        ventanaImagen = new VistaImagen();
        ventanaImagen.setVisible(true);
        this.ventanaImagen.setControlador((ActionListener) this);
        lector = new ReadDatabase("secretaria.txt");
      
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
                        System.err.println("usuario reconocido");
                    } else {
                        System.err.println("usuario NO reconocido");
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean hacerComparacion(String filePath) {
        String contenido = lector.leerArchivo();
        String info[]  = contenido.split("#");
        for(int i = 2; i < info.length; i += 3) {
            try {
                if(ComparadorImagen.areJpgsIdentical(filePath, "Database/imagenes/" + info[i])) {
                    return true;
                }
            } catch (IOException e) {

            }
           

            
        }
        return false;
    }

    public static void main(String args[]) {
        ControladorImagen c = new ControladorImagen();
    }
}

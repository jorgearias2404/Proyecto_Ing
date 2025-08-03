package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import Services.MonederoData;
import Services.ComparadorImagen;
import Services.ReadDatabase;
import Views.VistaImagen;

public class ControladorImagen implements ActionListener {
    VistaImagen ventanaImagen;
    String imagenesPath;
    ReadDatabase lector;
    ComparadorImagen comparador;
    String usuarioEncontrado;
    MonederoData monedero;

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
                        System.err.println("usuario " + usuarioEncontrado + " reconocido");
                        monedero = new MonederoData(this.usuarioEncontrado);
                        double saldo = monedero.getSaldo();
                        System.err.println("saldo: " + saldo);
                        //TODO: cobrar CCB
                        double ccb = 1;
                        saldo -= ccb;
                        monedero.actualizarSaldo(saldo);
                    } else {
                        ventanaImagen.showError("Usuario NO Reconocido");
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
                    this.usuarioEncontrado = info[i-2];
                    return true;
                }
            } catch (IOException e) {
                System.err.println("no se pudo abrir algun archivo de imagen");
            }
           
        }
        return false;
    }

    

    public static void main(String args[]) {
        ControladorImagen c = new ControladorImagen();
    }
}

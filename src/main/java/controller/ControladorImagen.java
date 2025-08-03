package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import Services.MonederoData;
import Services.ComparadorImagen;
import Services.ReadDatabase;
import Views.CargaCCB;
import Views.VistaImagen;

public class ControladorImagen implements ActionListener {
    VistaImagen ventanaImagen;
    String imagenesPath;
    ReadDatabase lector;
    ComparadorImagen comparador;
    String usuarioEncontrado, tipoUsuario;
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
                CargaCCB.getWeekRange();
                String filePath = ventanaImagen.showFileChooser();
                if (filePath != null) {
                    //ventanaRecon.updatePathLabel(filePath);
                    // Aquí puedes pasar la ruta al modelo para procesamiento
                    System.out.println("Ruta seleccionada: " + filePath);
                    if(hacerComparacion(filePath)) {
                        System.err.println("usuario " + usuarioEncontrado + " reconocido");
                        monedero = new MonederoData(this.usuarioEncontrado);
                        String nombreArchivo = "DataBase/monedero_" + this.usuarioEncontrado + ".txt";
                        File file = new File(nombreArchivo);

                        if (file.exists()) {
                            double saldo = monedero.getSaldo();
                            System.err.println("saldo: " + saldo);
                            double ccb = CalculadoraCCB.getCCB();
                            if(saldo - ccb >= 0) {
                                saldo -= ccb;
                                monedero.actualizarSaldo(saldo, ccb);
                                JOptionPane.showMessageDialog(null, "Reconocimiento y cobro exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                ventanaImagen.showError("Saldo insuficiente, Recargue");
                            }
                            
                        } else {
                            ventanaImagen.showError("Saldo insuficiente, Recargue");
                        }
                        
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
        for(int i = 3; i < info.length; i += 4) {
            try {
                if(ComparadorImagen.areJpgsIdentical(filePath, "Database/imagenes/" + info[i])) {
                    this.usuarioEncontrado = info[i-3];
                    this.tipoUsuario = info[i-1];
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

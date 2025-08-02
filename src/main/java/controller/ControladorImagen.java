package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Services.ComparadorImagen;
import Services.ReadDatabase;
import Views.VistaImagen;

public class ControladorImagen implements ActionListener {
    VistaImagen ventanaImagen;
    String imagenesPath;
    ReadDatabase lector;
    ComparadorImagen comparador;
    String usuarioEncontrado;

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
                        System.err.println("saldo: " + cargarSaldo());
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

    public double cargarSaldo() {
        String nombreArchivo = "DataBase/monedero_" + this.usuarioEncontrado + ".txt";
        new File("DataBase").mkdirs();
        
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",", 6);
                if (partes.length == 6) {
                    return Double.parseDouble(partes[4]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }

        return 0.00;
    }

    public static void main(String args[]) {
        ControladorImagen c = new ControladorImagen();
    }
}

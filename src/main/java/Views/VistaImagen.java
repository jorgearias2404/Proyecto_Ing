package Views;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

public class VistaImagen extends JFrame {
    JPanel panel;
    JButton botonCargarImagen;

    public VistaImagen() {
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Reconocimiento Facial COMEST");
        setLocationRelativeTo(null);
        agregarPanel(); 
        agregarDescripcion();
        agregarCantidad();
        agregarCosto();
        agregarBotonCargarImagen();
    }

    public String showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Image File");
        
        // Filtrar solo archivos de imagen
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(java.io.File f) {
                if (f.isDirectory()) return true;
                final String name = f.getName().toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".jpeg") || 
                       name.endsWith(".png") || name.endsWith(".gif");
            }
            public String getDescription() {
                return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }


    private void agregarDescripcion() {

        JLabel etiquetaDesc = new JLabel("Descripción: ");
        etiquetaDesc.setBounds(15,35,75,20);
        etiquetaDesc.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(etiquetaDesc);

    }

    private void agregarCantidad(){

        // AGREGA LA ETIQUETA DE CANTIDAD
        JLabel etiquetaCant = new JLabel();
        etiquetaCant.setText("Cantidad:");
        etiquetaCant.setBounds(15,70,75,20); 
        etiquetaCant.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(etiquetaCant);

    }

     private void agregarCosto(){
        // AGREGA LA ETIQUETA DE COSTO UNITARIO
        JLabel etiquetaCostoUni = new JLabel();
        etiquetaCostoUni.setText("Costo Unitario (Bs.):");
        etiquetaCostoUni.setBounds(220,70,145,20);
        etiquetaCostoUni.setHorizontalAlignment(SwingConstants.CENTER); 

        panel.add(etiquetaCostoUni);

    }

    private void agregarBotonCargarImagen() {
        botonCargarImagen = new JButton("Tomar Imagen");
        botonCargarImagen.setBounds(150, 275, 200, 50);
        botonCargarImagen.setActionCommand("CARGAR");
        panel.add(botonCargarImagen);
    }

    private void agregarPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        JLabel subtitulo = new JLabel("Reconocimiento Facial");
        subtitulo.setBounds(10,10,150,20);
        subtitulo.setHorizontalAlignment(SwingConstants.LEFT);
        subtitulo.setForeground(Color.BLACK);
        panel.add(subtitulo);
        
    }

    public void setControlador(ActionListener controlador) {
        botonCargarImagen.addActionListener(controlador);
    }
}

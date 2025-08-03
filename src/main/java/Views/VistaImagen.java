package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaImagen extends JFrame {
    JPanel panel;
    JButton botonCargarImagen;

    public VistaImagen() {
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Reconocimiento Facial COMEST");
        setLocationRelativeTo(null);
        agregarPanel(); 
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

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void agregarBotonCargarImagen() {
        Color colorBoton = new Color(151, 188, 199);
        Color colorBotonTexto = new Color(0, 0, 0);
        
        botonCargarImagen = new JButton("Tomar Imagen");
        botonCargarImagen.setBackground(colorBoton);
        botonCargarImagen.setForeground(colorBotonTexto);
        botonCargarImagen.setBounds(50, 25, 200, 50);
        botonCargarImagen.setActionCommand("CARGAR");
        botonCargarImagen.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(botonCargarImagen);
    }

    private void agregarPanel() {
        Color colorFondo = new Color(7, 64, 91);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(colorFondo);
        this.getContentPane().add(panel);
        /*JLabel subtitulo = new JLabel("Reconocimiento Facial");
        subtitulo.setBounds(10,10,150,20);
        subtitulo.setHorizontalAlignment(SwingConstants.LEFT);
        subtitulo.setForeground(Color.BLACK);
        panel.add(subtitulo);*/
        
    }

    public void setControlador(ActionListener controlador) {
        botonCargarImagen.addActionListener(controlador);
    }
}

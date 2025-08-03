package Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonederoData {
    String usuarioActual;

    public MonederoData(String usuario) {
        usuarioActual = usuario;
    }

    public double getSaldo() {
        String nombreArchivo = "DataBase/monedero_" + usuarioActual + ".txt";
        new File("DataBase").mkdirs();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] contenido = linea.split(",", 6);
                if (contenido.length == 6) {
                    return Double.parseDouble(contenido[4]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }

        return 0.00;
    }

    public void actualizarSaldo(double nuevoSaldo, double ccb) {
        String[] nuevoContenido = actualizarSaldo_(nuevoSaldo);
        System.err.println(ccb);
        actualizarArchivo(nuevoContenido, ccb);
    }

    private void actualizarArchivo(String[] contenido, double ccb) {
        String nombreArchivo = "DataBase/monedero_" + usuarioActual + ".txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for(int i = 0; i < contenido.length - 1; i++) {
                writer.write(contenido[i] + ",");
            }
            String[] recargas = contenido[contenido.length-1].split("#");
            for(String i : recargas) {
                System.err.println(i);
            }
            System.err.println(recargas.length);
            for(int i = 0; i < recargas.length - 1; i++) {
                writer.write(recargas[i] + "#");
                writer.newLine();
            }
            
            String fecha = "[" + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()) + "]";
            writer.write("\\n " + fecha + " Visita al comedor: $-" + ccb + "  #");
            writer.newLine();
            writer.write("\\n");
            
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    private String[] actualizarSaldo_(double nuevoSaldo) {
        String nombreArchivo = "DataBase/monedero_" + usuarioActual + ".txt";
        new File("DataBase").mkdirs();
        String[] contenido = {};
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            String cont = "";
            while ((linea = br.readLine()) != null) {
                cont += linea; 
            }
            contenido = cont.split(",", 6);
            System.err.println(cont);
            if (contenido.length == 6) {
                contenido[4] = Double.toString(nuevoSaldo);
            }
            return contenido;
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
        return contenido;
    }

}

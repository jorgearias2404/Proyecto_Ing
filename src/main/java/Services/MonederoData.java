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
            double saldo = 0;
            while ((linea = br.readLine()) != null) {
                String[] contenido = linea.split(",");
                saldo = Double.parseDouble(contenido[1]);
            }
            return saldo;
            
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }

        return 0.00;
    }

    public void actualizarSaldo(double nuevoSaldo, double ccb) {
        String[] nuevoContenido = actualizarSaldo_();
        System.err.println(ccb);
        actualizarArchivo(nuevoContenido, ccb, nuevoSaldo);
    }

    private void actualizarArchivo(String[] contenido, double ccb, double nuevoSaldo) {
        String nombreArchivo = "DataBase/monedero_" + usuarioActual + ".txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for(int i = 0; i < contenido.length; i++) {
                if((i+1) % 5 == 0) {
                    System.err.println(contenido[i]);
                    writer.write(contenido[i]);
                    writer.newLine();
                    continue;
                }
                writer.write(contenido[i] + ",");
            }

            //sergiogo,25.0,[03/08/25 18:43],Recarga: $5.0,Banco: Banco Provincial
            
            String fecha = "[" + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()) + "]";
            writer.write(usuarioActual + "," + nuevoSaldo + "," + fecha + "," +"Consumo: $-" + ccb + "," + "Almuerzo");
            writer.newLine();
            
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    private String[] actualizarSaldo_() {
        String nombreArchivo = "DataBase/monedero_" + usuarioActual + ".txt";
        new File("DataBase").mkdirs();
        String[] contenido = {};
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            String cont = "";
            while ((linea = br.readLine()) != null) {
                cont += linea + ","; 
            }
            System.err.println(cont);
            contenido = cont.split(",");
            return contenido;
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
        return contenido;
    }

}

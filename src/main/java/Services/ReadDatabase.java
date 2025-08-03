package Services;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ReadDatabase {
    String nombreArchivo;
    public ReadDatabase(String archivo) {
        nombreArchivo = archivo;
    }

    public String leerArchivo() {
        String contenido = "";

        try (Scanner scanner = new Scanner(new File("Database/" + nombreArchivo))) {
            while(scanner.hasNextLine()) {
                contenido = contenido + scanner.nextLine();
            }
        } catch(IOException e) {
            //TODO
        }
        System.err.println(contenido);
        return contenido;
    }
}

package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import Views.CargaCCB;

public class CalculadoraCCB {
    public static double calcularCCB(String costosFijosText, String costosVariablesText, String bandejasText,
            String mermaText) {
        // Validar formato numérico
        if (!costosFijosText.matches("^[+]?([0-9]+([.][0-9]*)?|[.][0-9]+)$") ||
                !costosVariablesText.matches("^[+]?([0-9]+([.][0-9]*)?|[.][0-9]+)$") ||
                !bandejasText.matches("^[+]?([0-9]+([.][0-9]*)?|[.][0-9]+)$") ||
                !mermaText.matches("^[+]?([0-9]+([.][0-9]*)?|[.][0-9]+)$")) {
            return 0;
        }

        double costosFijos = Double.parseDouble(costosFijosText);
        double costosVariables = Double.parseDouble(costosVariablesText);
        double bandejas = Double.parseDouble(bandejasText);
        double merma = Double.parseDouble(mermaText) / 100; // Convertir % a decimal

        if (merma < 0 || merma > 100) {
            return 0;
        }

        return ((costosFijos + costosVariables) / bandejas) * (1 + merma);
    }

    public static double getCCB() {
        String nombreArchivo = "Database/formulario_" + CargaCCB.getWeekRange() + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().equals("= RESULTADO CCB =")) {
                    String valor = br.readLine();
                    if (valor != null) {
                        return Double.parseDouble(valor.trim());
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar CCB: " + e.getMessage());
        }
        return 0.00;
    }
}
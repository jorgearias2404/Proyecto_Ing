package controller;

public class CalculadoraCCB {
    public static double calcularCCB(String costosFijosText, String costosVariablesText, String bandejasText, String mermaText) {

        // Obtener valores de los campos
        if(!costosFijosText.matches("^[+]?([0-9]+([.][0-9]*)?|[.][0-9]+)$") || !costosVariablesText.matches("^[+]?([0-9]+([.][0-9]*)?|[.][0-9]+)$") || !bandejasText.matches("^[+]?([0-9]+([.][0-9]*)?|[.][0-9]+)$") || !mermaText.matches("^[+]?([0-9]+([.][0-9]*)?|[.][0-9]+)$")) {
            return 0;
        }
        double costosFijos = Double.parseDouble(costosFijosText);
        double costosVariables = Double.parseDouble(costosVariablesText);
        double bandejas = Double.parseDouble(bandejasText);
        double merma = Double.parseDouble(mermaText) / 100; // Convertir % a decimal

        if(0 > merma || merma > 100) {
            return 0;
        }

        return ((costosFijos + costosVariables) / bandejas) * (1 + merma);
    }
}

package br.ufjf.dcc.io;

public class Utils {

    // Função auxiliar para tratar dados vindos dos CSVs
    public static double parseDoubleGeral(String valor) {
        if (valor == null || valor.trim().isEmpty() || valor.equals("-")) return 0.0;
        try {
            if (valor.contains(",")) {
                return Double.parseDouble(valor.replace(".", "").replace(",", "."));
            }
            return Double.parseDouble(valor);
        } catch (Exception e) {
            return 0.0;
        }
    }
}

package br.ufjf.dcc.model;

public class Acao extends Ativo {
    public Acao(String nome, String ticker, double precoAtual, boolean qualificado) {
        super(nome, ticker, precoAtual, qualificado);
    }

    public String getTipoRenda() {
        return "Variável";
    }

    public String getTipoAcao() {
        if (getTicker()!= null && !getTicker().isEmpty()) {
            int indiceUltimoCaractere = getTicker().length() - 1;
            if (getTicker().charAt(indiceUltimoCaractere) == '3') {
                return "Ordinária";
            } else if (getTicker().charAt(indiceUltimoCaractere) == '4' || getTicker().charAt(indiceUltimoCaractere) == '5' || getTicker().charAt(indiceUltimoCaractere) == '6') {
                return "Preferencial";
            } else if (getTicker().charAt(indiceUltimoCaractere) == '1') {
                return "Unit";
            }
        }
        return "Tipo de ação inexistente";
    }
}

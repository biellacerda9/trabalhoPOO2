package br.ufjf.dcc.model;

public class Criptomoeda extends Ativo{
    private String algoritmoConsenso;
    double qtdMaxCirculacao;
    double fatorConversaoDolar;

    public Criptomoeda(String nome, String ticker, double precoAtual, boolean qualificado, String algoritmoConsenso, double qtdMaxCirculacao) {
        super(nome, ticker, precoAtual, qualificado);
        this.algoritmoConsenso = algoritmoConsenso;
        this.qtdMaxCirculacao = qtdMaxCirculacao;
        this.fatorConversaoDolar = 5.39;
    }

    public String getAlgoritmoConsenso() {
        return algoritmoConsenso;
    }

    public void setAlgoritmoConsenso(String algoritmoConsenso) {
        this.algoritmoConsenso = algoritmoConsenso;
    }

    public double getQtdMaxCirculacao() {
        return qtdMaxCirculacao;
    }

    public void setQtdMaxCirculacao(double qtdMaxCirculacao) {
        this.qtdMaxCirculacao = qtdMaxCirculacao;
    }

    public String getTipoRenda() {
        return "Variável";
    }

    public double converterMoedaParaReal() {
        return this.getPrecoAtual() * this.fatorConversaoDolar;
    }
}

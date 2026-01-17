package br.ufjf.dcc.model.ativos;

public class Criptomoeda extends Ativo implements AtivoInternacional {
    private String algoritmoConsenso;
    private double qtdMaxCirculacao;
    private double fatorConversao;

    public Criptomoeda(String nome, String ticker, double precoAtual, boolean qualificado, String algoritmoConsenso, double qtdMaxCirculacao, double  fatorConversao) {
        super(nome, ticker, precoAtual, qualificado);
        this.algoritmoConsenso = algoritmoConsenso;
        this.qtdMaxCirculacao = qtdMaxCirculacao;
        this.fatorConversao = fatorConversao;
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
        return this.getPrecoAtual() * this.fatorConversao;
    }

    @Override
    public boolean ehNacional() {
        return false;
    }

    public double getFatorConversao() {
        return this.fatorConversao;
    }

    public void setFatorConversao(double fatorConversao) {
        this.fatorConversao = fatorConversao;
    }
}

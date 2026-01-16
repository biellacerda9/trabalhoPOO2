package br.ufjf.dcc;

public class Stocks extends Ativo{
    private String bolsaNegociacao;
    private String setorEmpresa;
    private double fatorConversaoDolar;
    public Stocks(String nome, String ticker, double precoAtual, boolean qualificado, String bolsaNegociacao, String setorEmpresa) {
        super(nome, ticker, precoAtual, qualificado);
        this.bolsaNegociacao = bolsaNegociacao;
        this.setorEmpresa = setorEmpresa;
        this.fatorConversaoDolar = 5.39;
    }

    public String getBolsaNegociacao() {
        return bolsaNegociacao;
    }

    public void setBolsaNegociacao(String bolsaNegociacao) {
        this.bolsaNegociacao = bolsaNegociacao;
    }

    public String getSetorEmpresa() {
        return setorEmpresa;
    }

    public void setSetorEmpresa(String setorEmpresa) {
        this.setorEmpresa = setorEmpresa;
    }
    public String getTipoRenda() {
        return "Variável";
    }

    public double converterMoedaParaReal () {
        return this.getPrecoAtual() * this.fatorConversaoDolar;
    }
}

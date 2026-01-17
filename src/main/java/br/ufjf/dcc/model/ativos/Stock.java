package br.ufjf.dcc.model.ativos;

public class Stock extends Ativo implements AtivoInternacional {
    private String bolsaNegociacao;
    private String setorEmpresa;
    private double fatorConversao;
    public Stock(String nome, String ticker, double precoAtual, boolean qualificado, String bolsaNegociacao, String setorEmpresa, double fatorConversao) {
        super(nome, ticker, precoAtual, qualificado);
        this.bolsaNegociacao = bolsaNegociacao;
        this.setorEmpresa = setorEmpresa;
        this.fatorConversao = fatorConversao;
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
        return this.getPrecoAtual() * this.fatorConversao;
    }

    @Override
    public boolean ehNacional() {
        return false;
    }

    @Override
    public double getFatorConversao() {
        return this.fatorConversao;
    }

    public void setFatorConversao(double fatorConversao) {
        this.fatorConversao = fatorConversao;
    }
}

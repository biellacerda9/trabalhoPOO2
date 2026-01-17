package br.ufjf.dcc.model.ativos;

public class FII extends Ativo {
    private String segmento;
    private double valorDividendo;
    private double taxaAdm;

    public FII (String nome, String ticker, double precoAtual, boolean qualificado, String segmento, double valorDividendo, double taxaAdm) {
        super(nome, ticker, precoAtual, qualificado);
        this.segmento = segmento;
        this.valorDividendo = valorDividendo;
        this.taxaAdm = taxaAdm;
    }

    public String getTipoRenda() {
        return "Variável";
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getTaxaAdm() {
        return String.format("%.2f%%", this.taxaAdm);
    }

    public void setTaxaAdm(double taxaAdm) {
        this.taxaAdm = taxaAdm;
    }

    public double getValorDividendo() {
        return valorDividendo;
    }

    public void setValorDividendo(double valorDividendo) {
        this.valorDividendo = valorDividendo;
    }

    public boolean  ehNacional() {
        return true;
    }

}

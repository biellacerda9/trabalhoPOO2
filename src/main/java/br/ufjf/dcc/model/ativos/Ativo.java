package br.ufjf.dcc.model.ativos;

public abstract class Ativo {
    private String nome;
    private String ticker;
    private double precoAtual;
    private boolean qualificado;

    public Ativo(String nome, String ticker, double precoAtual, boolean qualificado) {
        this.nome = nome;
        this.ticker = ticker;
        this.precoAtual = precoAtual;
        this.qualificado = qualificado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(double precoAtual) {
        this.precoAtual = precoAtual;
    }

    public boolean isQualificado() {
        return qualificado;
    }

    public void setQualificado(boolean qualificado) {
        this.qualificado = qualificado;
    }

    public abstract String getTipoRenda();
    public abstract boolean ehNacional();
}

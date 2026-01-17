package br.ufjf.dcc.model;

public class Tesouro extends Ativo{
    private String tipoRendimento;
    private String dataVencimento; //no futuro podemos trocar para Date ou LocalDate
    public Tesouro(String nome, String ticker, double precoAtual, boolean qualificado, String tipoRendimento, String dataVencimento) {
        super(nome, ticker, precoAtual, qualificado);
        this.tipoRendimento = tipoRendimento;
        this.dataVencimento = dataVencimento;
    }

    public String getTipoRenda() {
        return "Fixa";
    }

    public String getTipoRendimento() {
        return tipoRendimento;
    }

    public void setTipoRendimento(String tipoRendimento) {
        this.tipoRendimento = tipoRendimento;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}

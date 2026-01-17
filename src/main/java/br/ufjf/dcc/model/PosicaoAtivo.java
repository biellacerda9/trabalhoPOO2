package br.ufjf.dcc.model;

public class PosicaoAtivo {
    private Ativo ativo;
    private double quantidade;
    private double precoMedioCompra;

    public PosicaoAtivo(Ativo ativo, double quantidade, double precoPago) {
        this.ativo = ativo;
        this.quantidade = quantidade;
        this.precoMedioCompra = precoPago;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoMedioCompra() {
        return precoMedioCompra;
    }

    public void setPrecoMedioCompra(double precoMedio) {
        this.precoMedioCompra = precoMedio;
    }

    //Metodo para calcular quanto essa posição vale HOJE
    public double getValorAtual() {
        return this.quantidade * this.ativo.getPrecoAtual();
    }

    //Metodo para calcular quanto foi gasto no total
    public double getValorGasto() {
        return this.quantidade * this.precoMedioCompra;
    }

    //Atualiza o precoMedio
    public void atualizar(double novaQuantidade, double novoPrecoCompra) {
        double valorTotalAtual = this.quantidade * this.precoMedioCompra;
        double valorNovaCompra = novaQuantidade * novoPrecoCompra;

        double novaQuantidadeTotal = this.quantidade + novaQuantidade;

        this.precoMedioCompra = (valorTotalAtual + valorNovaCompra) / novaQuantidadeTotal;
        this.quantidade = novaQuantidadeTotal;
    }

}
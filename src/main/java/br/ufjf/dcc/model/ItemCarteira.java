package br.ufjf.dcc.model;

import br.ufjf.dcc.model.ativos.Ativo;
import br.ufjf.dcc.model.ativos.AtivoInternacional;

public class ItemCarteira {
    private Ativo ativo;
    private double quantidade;
    private double precoMedioCompra;

    public ItemCarteira(Ativo ativo, double quantidade, double precoPago) {
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

//    //Metodo para calcular quanto essa posição vale HOJE
//    public double getValorAtual() {
//        return this.quantidade * this.ativo.getPrecoAtual();
//    }

    //Metodo para calcular quanto foi gasto no total
//    public double getValorGasto() {
//        return this.quantidade * this.precoMedioCompra;
//    }

    //Atualiza o precoMedio
    public void atualizar(double novaQuantidade, double novoPrecoCompra) {
        double valorTotalAtual = this.quantidade * this.precoMedioCompra;
        double valorNovaCompra = novaQuantidade * novoPrecoCompra;

        double novaQuantidadeTotal = this.quantidade + novaQuantidade;

        this.precoMedioCompra = (valorTotalAtual + valorNovaCompra) / novaQuantidadeTotal;
        this.quantidade = novaQuantidadeTotal;
    }

    //calcula o valor gasto total, ja convertendo caso for internacional
    public double getValorGastoEmReal() {
        double valorBase = this.quantidade * this.precoMedioCompra;
        if (this.ativo instanceof AtivoInternacional) {
            AtivoInternacional a = (AtivoInternacional) this.ativo;
            return valorBase * a.getFatorConversao();
        }
        return valorBase;
    }


    //calcula o valor de mercado ATUAL em real
    public double getValorAtualEmReal() {
        if  (this.ativo instanceof AtivoInternacional) {
            AtivoInternacional a = (AtivoInternacional) this.ativo;
            return this.quantidade * a.converterMoedaParaReal();
        }
        return this.quantidade * this.ativo.getPrecoAtual();
    }

    //caso de vendas
    public void subtrairQuantidade (double qtd) {
        this.quantidade -= qtd;
    }


    @Override
    public String toString() {
        return "Ativo: " + ativo.getTicker() +
                " | Quantidade: " + quantidade +
                " | Preço médio: R$ " + precoMedioCompra +
                " | Valor atual: R$ " + getValorAtualEmReal();
    }
}
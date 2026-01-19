package br.ufjf.dcc.model;

import br.ufjf.dcc.model.ativos.Ativo;

import java.time.LocalDate;

public class Movimentacao {
    private static int controlarId = 1; //para ser id unico
    private int id;
    private String tipo; //compra ou venda
    private String instituicao;
    private Ativo ativo;
    private double quantidade;
    private LocalDate data;
    private double preco;

    public Movimentacao (String tipo, String instituicao, Ativo ativo, double quantidade, double preco) {
        this.id = controlarId++;
        this.tipo = tipo;
        this.instituicao = instituicao;
        this.ativo = ativo;
        this.quantidade = quantidade;
        this.data = LocalDate.now();
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }


    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}

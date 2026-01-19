package br.ufjf.dcc.model;

import br.ufjf.dcc.model.ativos.Ativo;

public abstract class Investidor {
    private String nome;
    private String identificador;
    private String telefone;
    private String dataNascimento;
    private Endereco endereco;
    private double patrimonio;
    private Carteira carteira;

    public Investidor(String nome, String identificador, String telefone, String dataNascimento, Endereco endereco,double patrimonio, Carteira carteira) {
        this.nome = nome;
        this.identificador = identificador;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.patrimonio = patrimonio;
        this.carteira = carteira;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public double getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(double patrimonio) {
        this.patrimonio = patrimonio;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public abstract boolean podeInvestir(Ativo ativo);

    public String cadastrarInvestimento (Ativo ativo, double quantidade, double preco, String tipo, String instituicao) {
        if (!podeInvestir(ativo)) return "FALHA! Seu perfil não pode investir neste ativo.";

        boolean sucesso = false;
        if (tipo.equalsIgnoreCase("COMPRA")) {
            this.carteira.adicionarAtivos(ativo, quantidade, preco);
            sucesso = true;
        }else if (tipo.equalsIgnoreCase("VENDA")) {
            sucesso = this.carteira.removerAtivo(ativo.getTicker(), quantidade);
        }
        if (sucesso) {
            Movimentacao movimentacao = new Movimentacao(tipo, instituicao, ativo, quantidade, preco);
            return "Movimentação concluída com sucesso!";
        }
        return "ERRO: Saldo insuficiente!";
    }
}

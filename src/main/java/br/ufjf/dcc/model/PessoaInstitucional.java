package br.ufjf.dcc.model;

import br.ufjf.dcc.model.ativos.Ativo;

public class PessoaInstitucional extends Investidor{
    private String razaoSocial;

    public PessoaInstitucional (String nome, String identificador, String telefeone, String dataNascimento, Endereco endereco, double patrimonio, Carteira carteira, String razaoSocial) {
        super (nome, identificador, telefeone, dataNascimento, endereco, patrimonio, carteira);
        this.razaoSocial = razaoSocial;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Override
    public boolean podeInvestir(Ativo ativo) {
        return true;
    }
}

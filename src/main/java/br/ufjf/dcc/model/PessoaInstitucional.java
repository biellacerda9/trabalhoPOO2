package br.ufjf.dcc.model;

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
}

package br.ufjf.dcc.model;

import br.ufjf.dcc.model.ativos.Ativo;
import br.ufjf.dcc.model.ativos.Criptomoeda;
import br.ufjf.dcc.model.ativos.Stock;

public class PessoaFisica extends Investidor{
    private PerfilInvestimento perfil;

    public PessoaFisica (String nome, String identificador, String telefeone, String dataNascimento, Endereco endereco, double patrimonio, Carteira carteira, PerfilInvestimento perfil) {
        super (nome, identificador, telefeone, dataNascimento, endereco, patrimonio, carteira);
        this.perfil = perfil;
    }

    public PerfilInvestimento getPerfilInvestimento() {
        return perfil;
    }
    public void setPerfilInvestimento(PerfilInvestimento perfil) {
        this.perfil = perfil;
    }

    public boolean podeInvestir (Ativo ativo) {
        if (ativo instanceof Criptomoeda && this.perfil != PerfilInvestimento.ARROJADO) return false;
        if (ativo instanceof Stock && this.perfil != PerfilInvestimento.MODERADO && this.perfil != PerfilInvestimento.ARROJADO) {
            return false;
        }
        if (ativo.isQualificado() && this.getPatrimonio() < 1000000) return false;

        return true;
    }

}

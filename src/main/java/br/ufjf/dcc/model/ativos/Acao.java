package br.ufjf.dcc.model.ativos;

import java.util.Scanner;

public class Acao extends Ativo {
    public Acao(String nome, String ticker, double precoAtual, boolean qualificado) {
        super(nome, ticker, precoAtual, qualificado);
    }


    public String getTipoAcao() {
        String t = getTicker();
        if (t.endsWith("3")) return "Ordinária";
        if (t.endsWith("4") || t.endsWith("5") || t.endsWith("6")) return "Preferencial";
        if (t.endsWith("11")) return "Unit";
        return "Tipo de ação inexistente";
    }
    public String getTipoRenda() {return "Variável";}
    public boolean ehNacional() {return true;}

    @Override
    public String getMenuEspecifico() {
        return "";
    }

    @Override
    public void editarCamposEspecificos(String escolha, Scanner scanner) {
        //
    }
    @Override
    public boolean isOpcaoEspecificaValida(String opcao) {
        return false;
    }
}

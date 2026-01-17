package br.ufjf.dcc.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carteira {
    private Map<String, PosicaoAtivo> posicoes = new HashMap<>();

    public Carteira (Map<String, PosicaoAtivo> posicoes){
        if (posicoes != null) {
            this.posicoes.putAll(posicoes);
        }
    }

    public void adicionarAtivos(Ativo ativo, double quantidade, double precoMedio){

        if(this.posicoes.containsKey(ativo.getTicker())){
            PosicaoAtivo posicaoExistente = this.posicoes.get(ativo.getTicker());
            posicaoExistente.atualizar(quantidade, precoMedio);
        } else {
            PosicaoAtivo novaPosicao = new PosicaoAtivo(ativo, quantidade, precoMedio);
            this.posicoes.put(ativo.getTicker(), novaPosicao);
        }
    }
}

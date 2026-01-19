package br.ufjf.dcc.model;


import br.ufjf.dcc.model.ativos.Ativo;

import java.util.HashMap;
import java.util.Map;

public class Carteira {
    private Map<String, ItemCarteira> itens = new HashMap<>();

    public Carteira (Map<String, ItemCarteira> itens){
        if (itens != null) {
            this.itens.putAll(itens);
        }
    }

    public void adicionarAtivos(Ativo ativo, double quantidade, double precoMedio){

        if(this.itens.containsKey(ativo.getTicker())){
            ItemCarteira posicaoExistente = this.itens.get(ativo.getTicker());
            posicaoExistente.atualizar(quantidade, precoMedio);
        } else {
            ItemCarteira novaPosicao = new ItemCarteira(ativo, quantidade, precoMedio);
            this.itens.put(ativo.getTicker(), novaPosicao);
        }
    }

    public void removerAtivo(String ticker, double quantidade){

        if(!this.itens.containsKey(ticker)){
            println("Erro: Você não possui este ativo " + ticker);
            return;
        }

        ItemCarteira item = this.itens.get(ticker);

        if(quantidade > item.getQuantidade()){
            println("Erro: Saldo insuficiente. Você tem apenas: " + item.getQuantidade());
            return;
        } else {
            item.subtrairQuantidade(quantidade);
            println( quantidade + " itens removidos com sucesso!");
        }
    }

    public static void println(String msg) {
        System.out.println(msg);
    }

    public static void print(String msg) {
        System.out.print(msg);
    }
}

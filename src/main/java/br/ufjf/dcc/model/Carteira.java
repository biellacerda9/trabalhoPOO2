package br.ufjf.dcc.model;


import br.ufjf.dcc.model.ativos.Ativo;
import br.ufjf.dcc.model.ativos.Tesouro;

import java.util.HashMap;
import java.util.Map;

public class Carteira {
    private Map<String, ItemCarteira> itens = new HashMap<>();

    public Carteira (Map<String, ItemCarteira> itens){
        if (itens != null) {
            this.itens.putAll(itens);
        }
    }


    public Map<String, ItemCarteira> getItens() {
        return itens;
    }


    public void exibirAtivos() {
        if (itens.isEmpty()) {
            System.out.println("Carteira vazia.");
            return;
        }

        System.out.println("Ativos na carteira:");
        for (ItemCarteira item : itens.values()) {
            System.out.println(item);
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

    public boolean removerAtivo(String ticker, double quantidade){

        if(!this.itens.containsKey(ticker)){
            println("Erro: Você não possui este ativo " + ticker);
            return false;
        }

        ItemCarteira item = this.itens.get(ticker);

        if(quantidade > item.getQuantidade()){
            println("Erro: Saldo insuficiente. Você tem apenas: " + item.getQuantidade());
            return false;
        } else {
            item.subtrairQuantidade(quantidade);

            if (item.getQuantidade() == 0) {
                this.itens.remove(ticker);
                println("Ativo removido da carteira.");
            } else {
                println(quantidade + " itens removidos com sucesso!");
            }
        }
        return true;
    }

    public double getValorTotalGasto () {
        double total = 0;

        for(ItemCarteira item : this.itens.values()){
            total += item.getValorGastoEmReal();
        }
        return total;
    }

    public double getValorTotalAtual () {
        double total = 0;

        for(ItemCarteira item : this.itens.values()){
            total += item.getValorAtualEmReal();
        }

        return total;
    }

    //Pegar o valor total da carteira, somar todos os ativos que tem renda fixa e apos dividir
    //pela pelo valor total da carteira. Mesmo processo pra renda variavel

    public double getPercentualRendaFixa() {
        double totalCarteira = getValorTotalAtual();
        if (totalCarteira == 0) return 0;

        double valorTotalFixo = 0;
        for (ItemCarteira a: itens.values()){
            if(a.getAtivo().getTipoRenda().equals("Fixa")){
                valorTotalFixo += a.getValorAtualEmReal();
            }
        }

        return  (valorTotalFixo / totalCarteira) * 100;
    }

    public double getPercentualRendaVariavel() {
        double totalCarteira = getValorTotalAtual();
        if (totalCarteira == 0) return 0;

        double valorTotalVariavel = 0;
        for (ItemCarteira a: itens.values()){
            if(a.getAtivo().getTipoRenda().equals("Variável")){
                valorTotalVariavel += a.getValorAtualEmReal();
            }
        }

        return (valorTotalVariavel / totalCarteira) * 100;
    }

    public double getPercentualNacional() {
        double totalCarteira = getValorTotalAtual();
        if (totalCarteira == 0) return 0;

        double totalProdutosNacionais = 0;
        for (ItemCarteira a: itens.values()){
            if(a.getAtivo().ehNacional()){
                totalProdutosNacionais += a.getValorAtualEmReal();
            }
        }

        return  (totalProdutosNacionais / totalCarteira) * 100;
    }

    public double getPercentualInteracional() {
        double totalCarteira = getValorTotalAtual();
        if (totalCarteira == 0) return 0;

        double totalProdutosInternacionais = 0;
        for (ItemCarteira a: itens.values()){
            if(!a.getAtivo().ehNacional()){
                totalProdutosInternacionais += a.getValorAtualEmReal();
            }
        }

        return (totalProdutosInternacionais / totalCarteira) * 100;
    }

    public static void println(String msg) {
        System.out.println(msg);
    }

    public static void print(String msg) {
        System.out.print(msg);
    }
}

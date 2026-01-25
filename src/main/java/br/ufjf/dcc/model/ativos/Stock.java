package br.ufjf.dcc.model.ativos;

import java.util.Scanner;

public class Stock extends Ativo implements AtivoInternacional {
    private String bolsaNegociacao;
    private String setorEmpresa;
    private double fatorConversao;
    public Stock(String nome, String ticker, double precoAtual, boolean qualificado, String bolsaNegociacao, String setorEmpresa, double fatorConversao) {
        super(nome, ticker, precoAtual, qualificado);
        this.bolsaNegociacao = bolsaNegociacao;
        this.setorEmpresa = setorEmpresa;
        this.fatorConversao = fatorConversao;
    }

    public String getBolsaNegociacao() {
        return bolsaNegociacao;
    }

    public void setBolsaNegociacao(String bolsaNegociacao) {
        this.bolsaNegociacao = bolsaNegociacao;
    }

    public String getSetorEmpresa() {
        return setorEmpresa;
    }

    public void setSetorEmpresa(String setorEmpresa) {
        this.setorEmpresa = setorEmpresa;
    }
    public String getTipoRenda() {
        return "Variável";
    }

    public double converterMoedaParaReal () {
        return this.getPrecoAtual() * this.fatorConversao;
    }

    @Override
    public boolean ehNacional() {
        return false;
    }

    @Override
    public double getFatorConversao() {
        return this.fatorConversao;
    }

    public void setFatorConversao(double fatorConversao) {
        this.fatorConversao = fatorConversao;
    }

    @Override
    public String getMenuEspecifico() {
        return "5. Bolsa de Negociação, 6. Setor, 7. Fator Conversão";
    }

    @Override
    public void editarCamposEspecificos(String escolha, Scanner scanner) {
        if (escolha.equals("5")) {
            System.out.print("Nova Bolsa: ");
            this.setBolsaNegociacao(scanner.nextLine());
            System.out.println("Nova bolsa de negociação alterada para " + this.getBolsaNegociacao());
        } else if (escolha.equals("6")) {
            System.out.print("Novo Setor: ");
            this.setSetorEmpresa(scanner.nextLine());
            System.out.println("Novo setor da empresa alterado para " + this.getSetorEmpresa());
        } else if (escolha.equals("7")) {
            double fator = 0;
            while (true) {
                try {
                    System.out.println("Fator de conversão: ");
                    fator = Double.parseDouble(scanner.nextLine().replace(",", "."));
                    if (fator >= 0) break;
                    System.out.println("ERRO: O fator de conversão não pode ser negativo.");
                }catch (NumberFormatException e) {
                    System.out.println("Erro: Digite um valor numérico válido!");
                }
            }
            this.setFatorConversao(fator);
            System.out.println("Novo fator de conversão alterado para " + this.getFatorConversao());
        }
    }
    @Override
    public boolean isOpcaoEspecificaValida(String opcao) {
        return opcao.equals("5") || opcao.equals("6") || opcao.equals("7");
    }
}

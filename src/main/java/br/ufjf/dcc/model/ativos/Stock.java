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
        } else if (escolha.equals("6")) {
            System.out.print("Novo Setor: ");
            this.setSetorEmpresa(scanner.nextLine());
        } else if (escolha.equals("7")) {
            System.out.print("Novo Fator de Conversão (Câmbio): ");
            this.setFatorConversao(Double.parseDouble(scanner.nextLine().replace(",", ".")));
        }
    }
}

package br.ufjf.dcc.model.ativos;

import java.util.Scanner;

public class Criptomoeda extends Ativo implements AtivoInternacional {
    private String algoritmoConsenso;
    private double qtdMaxCirculacao;
    private double fatorConversao;

    public Criptomoeda(String nome, String ticker, double precoAtual, boolean qualificado, String algoritmoConsenso, double qtdMaxCirculacao, double  fatorConversao) {
        super(nome, ticker, precoAtual, qualificado);
        this.algoritmoConsenso = algoritmoConsenso;
        this.qtdMaxCirculacao = qtdMaxCirculacao;
        this.fatorConversao = fatorConversao;
    }

    public String getAlgoritmoConsenso() {
        return algoritmoConsenso;
    }

    public void setAlgoritmoConsenso(String algoritmoConsenso) {
        this.algoritmoConsenso = algoritmoConsenso;
    }

    public double getQtdMaxCirculacao() {
        return qtdMaxCirculacao;
    }

    public void setQtdMaxCirculacao(double qtdMaxCirculacao) {
        this.qtdMaxCirculacao = qtdMaxCirculacao;
    }

    public String getTipoRenda() {
        return "Variável";
    }

    public double converterMoedaParaReal() {
        return this.getPrecoAtual() * this.fatorConversao;
    }

    @Override
    public boolean ehNacional() {
        return false;
    }

    public double getFatorConversao() {
        return this.fatorConversao;
    }

    public void setFatorConversao(double fatorConversao) {
        this.fatorConversao = fatorConversao;
    }

    @Override
    public String getMenuEspecifico() {
        return "5. Algoritmo Consenso, 6. Qtd Máx Circulação, 7. Fator Conversão";
    }

    @Override
    public void editarCamposEspecificos(String escolha, Scanner scanner) {
        if (escolha.equals("5")) {
            System.out.print("Novo Algoritmo: ");
            this.setAlgoritmoConsenso(scanner.nextLine());
        } else if (escolha.equals("6")) {
            System.out.print("Nova Qtd Máxima: ");
            this.setQtdMaxCirculacao(Double.parseDouble(scanner.nextLine().replace(",", ".")));
        } else if (escolha.equals("7")) {
            System.out.print("Novo Fator de Conversão (Câmbio): ");
            this.setFatorConversao(Double.parseDouble(scanner.nextLine().replace(",", ".")));
        }
    }
}

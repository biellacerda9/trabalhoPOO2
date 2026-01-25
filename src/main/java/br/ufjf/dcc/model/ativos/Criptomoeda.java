package br.ufjf.dcc.model.ativos;

import br.ufjf.dcc.io.Utils;

import java.util.InputMismatchException;
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
            double qtd = 0;
            while (true) {
                try {
                    System.out.println("Nova Qtd Máxima: ");
                    qtd = Double.parseDouble(scanner.nextLine());
                    if (qtd >= 0) break;
                    System.out.println("ERRO: Quantidade não pode ser negativa.");
                } catch (NumberFormatException ex) {
                    System.out.println("ERRO: Digite apenas números.");
                }
            }
            this.setQtdMaxCirculacao(qtd);
            System.out.println("Nova quantidade alterada para " + this.getQtdMaxCirculacao());
        } else if (escolha.equals("7")) {
            double fator = 0;
            while (true) {
                try {
                    System.out.println("Fator de conversão: ");
                    fator = Double.parseDouble(scanner.nextLine());
                    if (fator >= 0) break;
                    System.out.println("ERRO: O fator de conversão não pode ser negativo.");
                }catch (NumberFormatException e) {
                    System.out.println("Erro: Digite um valor numérico válido!");
                }
            }
            this.setFatorConversao(fator);
            System.out.println("Novo fator de conversão alterada para " + this.getFatorConversao());
        }
    }
    @Override
    public boolean isOpcaoEspecificaValida(String opcao) {
        return opcao.equals("5") || opcao.equals("6") || opcao.equals("7");
    }
}

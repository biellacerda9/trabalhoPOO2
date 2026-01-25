package br.ufjf.dcc.model.ativos;

import java.util.Scanner;

public class FII extends Ativo {
    private String segmento;
    private double valorDividendo;
    private double taxaAdm;

    public FII (String nome, String ticker, double precoAtual, boolean qualificado, String segmento, double valorDividendo, double taxaAdm) {
        super(nome, ticker, precoAtual, qualificado);
        this.segmento = segmento;
        this.valorDividendo = valorDividendo;
        this.taxaAdm = taxaAdm;
    }

    public String getTipoRenda() {
        return "Variável";
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getTaxaAdm() {
        return String.format("%.2f%%", this.taxaAdm);
    }

    public void setTaxaAdm(double taxaAdm) {
        this.taxaAdm = taxaAdm;
    }

    public double getValorDividendo() {
        return valorDividendo;
    }

    public void setValorDividendo(double valorDividendo) {
        this.valorDividendo = valorDividendo;
    }

    public boolean ehNacional() {
        return true;
    }

    @Override
    public String getMenuEspecifico() {
        return "5. Segmento, 6. Valor Dividendo, 7. Taxa Adm";
    }

    @Override
    public void editarCamposEspecificos(String escolha, Scanner scanner) {
        if (escolha.equals("5")) {
            System.out.print("Digite o novo segmento: ");
            this.setSegmento(scanner.nextLine());
            System.out.println("Novo segmento alterado para " + this.getSegmento());
        } else if (escolha.equals("6")) {
            double entrada = 0;
            while(true) {
                try {
                    System.out.print("Digite o novo valor do dividendo: ");
                    entrada = Double.parseDouble(scanner.nextLine().replace(",", "."));
                    if (entrada >= 0) break;
                    System.out.println("ERRO: O valor do dividendo não pode ser nulo ou negativo");
                } catch (Exception e) {
                    System.out.println("ERRO: Digite apenas números.");
                }
            }
                this.setValorDividendo(entrada);
                System.out.println("Novo valor do dividendo alterado para " + this.getValorDividendo());
        } else if (escolha.equals("7")) {
            double entrada = 0;
            while (true) {
                try {
                    System.out.print("Digite o novo taxa de administração: ");
                    entrada = Double.parseDouble(scanner.nextLine().replace(",", "."));
                    if (entrada >= 0) break;
                    System.out.println("ERRO: O valor da taxa de administração não pode ser nula ou negativa");
                } catch (Exception e) {
                    System.out.println("ERRO: Digite apenas números.");
                }
            }
            this.setTaxaAdm(entrada);
            System.out.println("Nova taxa de administração alterado para " + this.getTaxaAdm());
        }
    }
    @Override
    public boolean isOpcaoEspecificaValida(String opcao) {
        return opcao.equals("5") || opcao.equals("6") || opcao.equals("7");
    }
}

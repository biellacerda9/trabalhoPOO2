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
        } else if (escolha.equals("6")) {
            System.out.print("Digite o novo valor do dividendo: ");
            String entrada = scanner.nextLine().replace(",", ".");
            this.setValorDividendo(Double.parseDouble(entrada));
        } else if (escolha.equals("7")) {
            System.out.print("Digite a nova taxa administrativa: ");
            String entrada = scanner.nextLine().replace(",", ".");
            this.setTaxaAdm(Double.parseDouble(entrada));
        }
    }

}

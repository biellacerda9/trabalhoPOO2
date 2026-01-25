package br.ufjf.dcc.model.ativos;

import java.util.Scanner;

public class Tesouro extends Ativo {
    private String tipoRendimento;
    private String dataVencimento; //no futuro podemos trocar para Date ou LocalDate
    public Tesouro(String nome, String ticker, double precoAtual, boolean qualificado, String tipoRendimento, String dataVencimento) {
        super(nome, ticker, precoAtual, qualificado);
        this.tipoRendimento = tipoRendimento;
        this.dataVencimento = dataVencimento;
    }

    public String getTipoRenda() {
        return "Fixa";
    }

    public String getTipoRendimento() {
        return tipoRendimento;
    }

    public void setTipoRendimento(String tipoRendimento) {
        this.tipoRendimento = tipoRendimento;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public boolean ehNacional() {
        return true;
    }

    @Override
    public String getMenuEspecifico() {
        return "5. Tipo Rendimento, 6. Data Vencimento";
    }

    @Override
    public void editarCamposEspecificos(String escolha, Scanner scanner) {
        if (escolha.equals("5")) {
            System.out.print("Novo Tipo de Rendimento: ");
            this.setTipoRendimento(scanner.nextLine());
            System.out.println("Novo tipo de rendimento alterado para " + this.getTipoRendimento());
        } else if (escolha.equals("6")) {
            System.out.print("Nova Data de Vencimento: ");
            this.setDataVencimento(scanner.nextLine());
            System.out.println("Nova data de vencimento alterado para " + this.getDataVencimento());
        }
    }
    @Override
    public boolean isOpcaoEspecificaValida(String opcao) {
        return opcao.equals("5") || opcao.equals("6");
    }
}

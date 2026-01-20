package br.ufjf.dcc.model.ativos;

import java.util.Scanner;

public abstract class Ativo {
    private String nome;
    private String ticker;
    private double precoAtual;
    private boolean qualificado;

    public Ativo(String nome, String ticker, double precoAtual, boolean qualificado) {
        this.nome = nome;
        this.ticker = ticker;
        this.precoAtual = precoAtual;
        this.qualificado = qualificado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(double precoAtual) {
        this.precoAtual = precoAtual;
    }

    public boolean isQualificado() {
        return qualificado;
    }

    public void setQualificado(boolean qualificado) {
        this.qualificado = qualificado;
    }

    public abstract String getTipoRenda();
    public abstract boolean ehNacional();

    public void editarCamposComuns(String escolha, Scanner scanner) {
        if (escolha.equals("1")) {
            System.out.print("Digite o novo nome: ");
            this.setNome(scanner.nextLine());
        } else if (escolha.equals("2")) {
            System.out.print("Digite o novo ticker: ");
            this.setTicker(scanner.nextLine());
        } else if (escolha.equals("3")) {
            System.out.print("Digite o novo preço: ");
            this.setPrecoAtual(Double.parseDouble(scanner.nextLine().replace(",", ".")));
        } else if (escolha.equals("4")) {
            System.out.print("Digite a nova qualificação (SIM/NÃO): ");
            String novaQualificacao = scanner.nextLine();
            if (novaQualificacao.equalsIgnoreCase("SIM")) {
                this.setQualificado(true);
            } else if (novaQualificacao.equalsIgnoreCase("NÃO")) {
                this.setQualificado(false);
            }
        }
    }

    public abstract String getMenuEspecifico();
    public abstract void editarCamposEspecificos(String escolha, Scanner scanner);
}

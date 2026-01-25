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
            String ticker = "";
            while (true) {
                System.out.println("Ticker: ");
                ticker = scanner.nextLine().trim().toUpperCase();
                if (!ticker.isEmpty() && !ticker.equals("")) break;
                System.out.println("ERRO: O identificador não pode ser nulo.");
            }
            this.setTicker(ticker);
        } else if (escolha.equals("3")) {
            double novoPreco = 0;
            while (true) {
                System.out.println("Preço: ");
                String entradaPreco = scanner.nextLine().replace(",", ".");

                try {
                    novoPreco = Double.parseDouble(entradaPreco);

                    if (novoPreco > 0) break;
                    else System.out.println("ERRO! O preço deve ser maior que ZERO.");
                } catch (NumberFormatException e) {
                    System.out.println("ERRO: O valor não pode conter letras.");
                    System.out.println("Use apenas números e ponto ou vírgula para decimais.");
                }
            }
            this.setPrecoAtual(novoPreco);

        } else if (escolha.equals("4")) {
            boolean qualificado = false;
            while (true) {
                System.out.println("Qualificado? (S/N)");
                String entrada = scanner.nextLine().trim().toUpperCase();

                if (entrada.equals("S")) {
                    qualificado = true;
                    break;
                } else if (entrada.equals("N")) {
                    qualificado = false;
                    break;
                } else System.out.println("ERRO: Entrada inválida! Digite apenas 'S' para SIM ou 'N' para NÃO.");
            }
            this.setQualificado(qualificado);
        }
    }

    public boolean isOpcaoEspecificaValida(String opcao) {
        return false;
    }

    public abstract String getMenuEspecifico();
    public abstract void editarCamposEspecificos(String escolha, Scanner scanner);
}

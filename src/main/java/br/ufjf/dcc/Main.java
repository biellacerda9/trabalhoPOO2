package br.ufjf.dcc;

import java.util.Scanner;

public class Main {
    static void main() {
        exibirMenuPrincipal();
    }

    public static void exibirMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        println("Esse é o menu principal!");

        while(true){
            println("1. Menu de Ativos");
            println("2. Menu de Investidores");
            println("3. Encerrar");
            print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                exibeMenuAtivos();
            } else if (escolha.equals("2")) {
                exibeMenuDeInvestidores();
            } else if (escolha.equals("3")) {
                break;
            } else {
                println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void exibeMenuAtivos() {
        Scanner scanner = new Scanner(System.in);
        println("Esse é o menu de ativos!");

        while(true){
            println("1. Cadastrar ativo");
            println("2. Cadastrar ativo em lote");
            println("3. Editar ativo");
            println("4. Excluir ativo");
            println("5. Exibir relatório de ativos");
            println("6. Voltar");
            print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                println("Entrou no 1.");
            } else if (escolha.equals("2")) {
                println("Entrou no 2.");
            } else if (escolha.equals("3")) {
                println("Entrou no 3.");
            } else if (escolha.equals("4")) {
                println("Entrou no 4.");
            } else if (escolha.equals("5")) {
                println("Entrou no 5.");
            } else if (escolha.equals("6")) {
                break;
            } else {
                println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void exibeMenuDeInvestidores() {
        Scanner scanner = new Scanner(System.in);
        println("Esse é o menu de investidores!");

        while(true){
            println("1. Cadastrar investidor");
            println("2. Cadastrar investidor em lote");
            println("3. Exibir todos investidores");
            println("4. Excluir investidores");
            println("5. Selecionar investidor");
            println("6. Voltar");
            print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                println("Entrou no 1.");
            } else if (escolha.equals("2")) {
                println("Entrou no 2.");
            } else if (escolha.equals("3")) {
                println("Entrou no 3.");
            } else if (escolha.equals("4")) {
                println("Entrou no 4.");
            } else if (escolha.equals("5")) {
                println("Entrou no 5.");
            } else if (escolha.equals("6")) {
                break;
            } else {
                println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void println(String msg) {
        System.out.println(msg);
    }

    public static void print(String msg) {
        System.out.print(msg);
    }
}

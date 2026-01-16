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
            println("2. Menu de Investidor");
            println("3. Encerrar");
            print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                println("Entrou no 1.");
            } else if (escolha.equals("2")) {
                println("Entrou no 2.");
            } else if (escolha.equals("3")) {
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

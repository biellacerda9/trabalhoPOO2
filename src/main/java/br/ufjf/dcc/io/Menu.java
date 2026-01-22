package br.ufjf.dcc.io;

import java.util.Scanner;
import static br.ufjf.dcc.app.AtivoApp.*;
import static br.ufjf.dcc.app.InvestidorApp.*;

public class Menu {

    public static void exibirMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Esse é o menu principal!");

        while(true){
            System.out.println("1. Menu de Ativos");
            System.out.println("2. Menu de Investidores");
            System.out.println("3. Encerrar");
            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                exibeMenuAtivos();
            } else if (escolha.equals("2")) {
                exibeMenuDeInvestidores();
            } else if (escolha.equals("3")) {
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }


    public static void exibeMenuAtivos() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Esse é o menu de ativos!");

        while(true){
            System.out.println("1. Cadastrar ativo");
            System.out.println("2. Cadastrar ativo em lote");
            System.out.println("3. Editar ativo");
            System.out.println("4. Excluir ativo");
            System.out.println("5. Exibir relatório de ativos");
            System.out.println("6. Voltar");
            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                cadastrarAtivo();
            } else if (escolha.equals("2")) {
                cadastrarAtivoEmLote();
            } else if (escolha.equals("3")) {
                editarAtivo();
            } else if (escolha.equals("4")) {
                excluirAtivo();
            } else if (escolha.equals("5")) {
                exibirRelatorioAtivos();
            } else if (escolha.equals("6")) {
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }


    public static void exibeMenuDeInvestidores() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Esse é o menu de investidores!");

        while(true){
            System.out.println("1. Cadastrar investidor");
            System.out.println("2. Cadastrar investidor em lote");
            System.out.println("3. Exibir todos investidores");
            System.out.println("4. Excluir investidores");
            System.out.println("5. Selecionar investidor");
            System.out.println("6. Voltar");
            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                System.out.println("Entrou no 1.");
                cadastrarInvestidor();
            } else if (escolha.equals("2")) {
                System.out.println("Entrou no 2.");

            } else if (escolha.equals("3")) {
                System.out.println("Entrou no 3.");
            } else if (escolha.equals("4")) {
                System.out.println("Entrou no 4.");
            } else if (escolha.equals("5")) {
                System.out.println("Entrou no 5.");
            } else if (escolha.equals("6")) {
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }


    public static void exibeMenuDoInvestidor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Esse é o menu do investidor!");

        while(true){
            System.out.println("1. Editar informações");
            System.out.println("2. Excluir investidor");
            System.out.println("3. Exibir ativos");
            System.out.println("4. Exibir total gasto");
            System.out.println("5. Exibir total atual");
            System.out.println("6. Exibir porcentagem de produtos RF e RV");
            System.out.println("7. Exibir porcentagem de produtos NAC e INT");
            System.out.println("8. Salvar relatório");
            System.out.println("9. Adicionar movimentação (compra)");
            System.out.println("10. Adicionar movimentação (venda)");
            System.out.println("11. Adicionar lote de movimentações");
            System.out.println("12. Voltar");
            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                System.out.println("Entrou no 1.");
            } else if (escolha.equals("2")) {
                System.out.println("Entrou no 2.");
            } else if (escolha.equals("3")) {
                System.out.println("Entrou no 3.");
            } else if (escolha.equals("4")) {
                System.out.println("Entrou no 4.");
            } else if (escolha.equals("5")) {
                System.out.println("Entrou no 5.");
            } else if (escolha.equals("6")) {
                System.out.println("Entrou no 6.");
            } else if (escolha.equals("7")) {
                System.out.println("Entrou no 7.");
            } else if (escolha.equals("8")) {
                System.out.println("Entrou no 8.");
            } else if (escolha.equals("9")) {
                System.out.println("Entrou no 9.");
            } else if (escolha.equals("10")) {
                System.out.println("Entrou no 10.");
            } else if (escolha.equals("11")) {
                System.out.println("Entrou no 11.");
            } else if (escolha.equals("12")) {
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}

package br.ufjf.dcc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static void exibeMenuDoInvestidor() {
        Scanner scanner = new Scanner(System.in);
        println("Esse é o menu do investidor!");

        while(true){
            println("1. Editar informações");
            println("2. Excluir investidor");
            println("3. Exibir ativos");
            println("4. Exibir total gasto");
            println("5. Exibir total atual");
            println("6. Exibir porcentagem de produtos RF e RV");
            println("7. Exibir porcentagem de produtos NAC e INT");
            println("8. Salvar relatório");
            println("9. Adicionar movimentação (compra)");
            println("10. Adicionar movimentação (venda)");
            println("11. Adicionar lote de movimentações");
            println("12. Voltar");
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
                println("Entrou no 6.");
            } else if (escolha.equals("7")) {
                println("Entrou no 7.");
            } else if (escolha.equals("8")) {
                println("Entrou no 8.");
            } else if (escolha.equals("9")) {
                println("Entrou no 9.");
            } else if (escolha.equals("10")) {
                println("Entrou no 10.");
            } else if (escolha.equals("11")) {
                println("Entrou no 11.");
            } else if (escolha.equals("12")) {
                break;
            } else {
                println("Opção inválida. Tente novamente.");
            }
        }
    }

    // ToDo: Passar esse trecho (e suas importações) para uma classe própria depois
    public static List<String[]> lerCSV(String nomeArquivo) {
        Path caminho = Paths.get(nomeArquivo);
        try (Stream<String> linhas = Files.lines(caminho)) { // linhas armazena o fluxo de strings, onde cada string é uma linha do arquivo
            return linhas
                    .skip(1) // Pula o cabeçalho
                    .map(linha -> linha.split(",")) // Quebra a linha do registro em um array de strings, separados por virgulas
                    .collect(Collectors.toList()); // Pega todos os arrays e coloca dentro de uma lista
        } catch (IOException e) { // Se o arquivo não for encontrado na raiz
            System.err.println("ERRO: Não foi possível ler o arquivo: " + nomeArquivo);
            return Collections.emptyList();
        }
    }

    public static void println(String msg) {
        System.out.println(msg);
    }

    public static void print(String msg) {
        System.out.print(msg);
    }
}

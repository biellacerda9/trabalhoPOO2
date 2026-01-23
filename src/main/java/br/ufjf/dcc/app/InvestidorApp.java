package br.ufjf.dcc.app;

import br.ufjf.dcc.io.CSVReader;
import br.ufjf.dcc.io.Utils;
import br.ufjf.dcc.model.*;
import br.ufjf.dcc.model.ativos.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static br.ufjf.dcc.data.BancoAtivos.bancoAtivos;
import static br.ufjf.dcc.data.BancoInvestidor.bancoInvestidor;

public class InvestidorApp {

    public static void cadastrarInvestidor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- CADASTRO DE INVESTIDOR ---");
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.println("Data de nascimento: ");
        String dataNascimento = scanner.nextLine();

        double patrimonio = 0;
        while (true) {
            try {
                System.out.println("Patrimônio TOTAL (em real): ");
                patrimonio = scanner.nextDouble();
                if (patrimonio >= 0) {
                    scanner.nextLine();
                    break;
                }
                System.out.println("ERRO: O patrimônio não deve ser negativo.");
            }catch (InputMismatchException e){
                System.out.println("ERRO: Digite uma entrada válida.");
                scanner.nextLine();
            }
        }

        System.out.println("--- ENDEREÇO ---");

        System.out.println("CEP: ");
        String cep = scanner.nextLine();

        System.out.println("Estado");
        String estado = scanner.nextLine();

        System.out.println("Cidade");
        String cidade = scanner.nextLine();

        System.out.println("Bairro");
        String bairro = scanner.nextLine();

        System.out.println("Rua: ");
        String rua = scanner.nextLine();

        System.out.println("Número: ");
        int numero;
        while(true) {
            if(scanner.hasNextInt()) {
                numero = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Valor inválido. Digite novamente!");
                scanner.next();
            }
        }

        Endereco endereco = new Endereco(rua,numero,bairro,cidade,estado,cep);

        System.out.println("1- Pessoa Física | 2- Pessoa Institucional:");
        String escolha = scanner.nextLine();
        br.ufjf.dcc.model.Investidor novoInv = null;
        Carteira carteira = new Carteira(null);

        if (escolha.equals("1")) {
            System.out.println("CPF: ");
            String cpf = scanner.nextLine();

            System.out.println("Perfil de investimento : [1] Conservador | [2] Moderado | [3] Arrojado ");
            String perfilString =  scanner.nextLine();
            PerfilInvestimento perfil;

            if (perfilString.equals("1")) {
                perfil = PerfilInvestimento.CONSERVADOR;
            } else if  (perfilString.equals("2")) {
                perfil = PerfilInvestimento.MODERADO;
            } else if (perfilString.equals("3")) {
                perfil = PerfilInvestimento.ARROJADO;
            } else {
                System.out.println("Valor Invalido. Cancelando cadastro!");
                return;
            }

            novoInv = new PessoaFisica(nome, cpf, telefone, dataNascimento, endereco, patrimonio, carteira, perfil);

            if(novoInv != null){
                bancoInvestidor.put(nome, novoInv);
            }
        } else  if (escolha.equals("2")) {
            System.out.println("CNPJ: ");
            String cnpj = scanner.nextLine();
            System.out.println("Razão Social: ");
            String razaoSocial = scanner.nextLine();
            novoInv = new PessoaInstitucional(nome, cnpj, telefone, dataNascimento, endereco, patrimonio, carteira, razaoSocial);

            if(novoInv != null){
                bancoInvestidor.put(novoInv.getIdentificador(), novoInv);
                System.out.println("\nInvestidor cadastrado com sucesso!\n");
            }
        }
    }

//estagio ainda inicial, precisa de ajustes e finalizar implementações
    public static void cadastrarInvestidorEmLote () {
        Scanner scanner = new Scanner(System.in);

        String caminho = "";
        while (true) {
            try {
                System.out.println("Digite o caminho do arquivo (ex: investidores.csv): ");
                caminho = scanner.nextLine();
                if (caminho.endsWith(".csv")) break;
                System.out.println("ERRO: O caminho do arquivo precisa terminar com '.csv'.");
            }catch (IllegalArgumentException e) {
                System.out.println("ERRO: Digite um caminho válido para o arquivo.");
            }
        }

        System.out.println("Digite o tipo de investidores presente neste arquivo: ");
        System.out.println("1- Pessoa Física, 2- Pessoa Institucional");
        String tipoDoArquivo = scanner.nextLine();

        List<String[]> linhas = CSVReader.lerCSV(caminho);
        //ordem -> nome, cpf, telefone, data, cep, estado, cidade, bairro, rua, numero, patrimonio, carteira, perfil

        for  (String[] col : linhas) {
                //criando endereço antes
                String cep = col[4];
                String estado = col[5];
                String cidade = col[6];
                String bairro = col[7];
                String rua = col[8];
                int numero = Integer.parseInt(col[9]);
                Endereco endereco = new Endereco(rua,numero,bairro,cidade,estado,cep);

                //dados gerais
                String nome = col[0];
                String id = col[1];
                String telefone = col[2];
                String data = col[3];
                double patrimonio = Utils.parseDoubleGeral(col[10]);
                Investidor novoInv = null;
                Carteira carteira = new Carteira(null);

                if (tipoDoArquivo.equalsIgnoreCase("1")) {
                    PerfilInvestimento perfil = PerfilInvestimento.valueOf(col[12].toUpperCase().trim());
                    novoInv = new PessoaFisica(nome, id, telefone, data, endereco, patrimonio, carteira, perfil);
                } else if (tipoDoArquivo.equalsIgnoreCase("2")) {
                    String razaoSocial = col[12].trim();
                    novoInv = new PessoaInstitucional(nome,id,telefone, data, endereco, patrimonio, carteira, razaoSocial);
                }

                if (novoInv != null) {
                    bancoInvestidor.put(novoInv.getIdentificador(), novoInv);
                    System.out.println("\nLote de investidores cadastrados com sucesso!\n");
                }
        }
    }

    public static void exibirInvestidores () {
        int paginaAtual= 0;
        int itensPorPagina = 50;

        while(true) {
            System.out.println("\n------- PÁGINA " + (paginaAtual + 1) + " -------");
            int contadorGeral = 0;
            int contadorExibidos = 0;
            int pulaPagina = paginaAtual * itensPorPagina;

            boolean temMaisPagina = false;

            for (br.ufjf.dcc.model.Investidor a: bancoInvestidor.values()) {
                if(contadorGeral < pulaPagina){
                    contadorGeral++;
                    continue;
                }

                if(contadorExibidos < itensPorPagina){
                    System.out.println(a.getNome() + " | " + a.getIdentificador());
                    contadorExibidos++;
                    contadorGeral++;
                } else {
                    temMaisPagina = true;
                    break;
                }
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("\n-----");
            if(paginaAtual > 0 ){
                System.out.print(" [A] Anterior |");
            }
            if (temMaisPagina){
                System.out.print(" [P] Proximo |");
            }
            System.out.print(" [S] Sair ");
            System.out.print("-----\n");
            String opcao = scanner.nextLine().toUpperCase();

            if(opcao.equals("P")){
                if(temMaisPagina){
                    paginaAtual++;
                } else {
                    System.out.println("Você está na ultima página!");
                }
            } else if (opcao.equals("A")){
                if (paginaAtual > 0) {
                    paginaAtual--;
                } else {
                    System.out.println("Você está na primeira página");
                }
            } else if (opcao.equals("S")){
                break;
            } else {
                System.out.println("Valor inválido. Digite novamente!");
                return;
            }
        }
    }
}

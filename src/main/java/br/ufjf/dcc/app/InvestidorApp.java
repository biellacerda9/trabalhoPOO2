package br.ufjf.dcc.app;

import br.ufjf.dcc.io.CSVReader;
import br.ufjf.dcc.io.Utils;
import br.ufjf.dcc.model.*;
import br.ufjf.dcc.model.ativos.*;

import java.io.File;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static br.ufjf.dcc.data.BancoAtivos.bancoAtivos;
import static br.ufjf.dcc.data.BancoInvestidor.bancoInvestidor;

public class InvestidorApp {

    public static void cadastrarInvestidor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- CADASTRO DE INVESTIDOR ---");

        String nome = "";
        while (true) {
            System.out.println("Nome: ");
            nome = scanner.nextLine();
            if (nome.matches(".*\\d.*")) System.out.println("ERRO: O nome não pode conter números.");
            else if (nome.trim().isEmpty()) System.out.println("ERRO: O nome não pode estar vazio.");
            else break;
        }

        String telefone = "";
        while (true) {
            try {
                System.out.println("Telefone (COM DDD): ");
                telefone = scanner.nextLine();
                String telLimpo = telefone.replaceAll("\\D", "");
                telefone = telLimpo;
                //11 com 9 na frente e 10 sem o 9 (jeito antigo)
                if (telLimpo.length() == 11 || telLimpo.length() == 10) break;
                System.out.println("ERRO: Telefone com números faltando ou em excesso. Tente novamente.");
            }catch (InputMismatchException e){
                System.out.println("ERRO: Número de telefone incorreto.");
            }
        }

        int dia, mes, ano;
        while (true) {
            try {
                System.out.println("Dia: ");
                dia = Integer.parseInt(scanner.nextLine());
                if (dia >= 1 && dia <= 31) break;
                System.out.println("ERRO: Dia inválido.");
            }catch (Exception e){
                System.out.println("ERRO: Digite apenas números.");
            }
        }

        while (true) {
            try {
                System.out.println("Mês: ");
                mes = Integer.parseInt(scanner.nextLine());
                if (mes >= 1 && mes <= 12) break;
                System.out.println("ERRO: Mês inválido.");
            }catch (Exception e){
                System.out.println("ERRO: Digite apenas números.");
            }
        }

        while (true) {
            try {
                System.out.println("Ano: ");
                ano = Integer.parseInt(scanner.nextLine());
                if (ano > 1900 && ano <= 2026) break;
                System.out.println("ERRO: Ano inválido.");
            }catch (Exception e){
                System.out.println("ERRO: Digite apenas números.");
            }
        }

        String dataNascimento = String.format("%02d/%02d/%04d", dia, mes, ano);

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

        String cep = "";
        while (true) {
            try {
                System.out.println("CEP: ");
                cep = scanner.nextLine();
                String cepLimpo = cep.replaceAll("\\D", "");
                cep = cepLimpo;
                if (cepLimpo.length() == 8) break;
                System.out.println("ERRO: CEP com números em excesso ou faltando.");
            }catch (Exception e) {
                System.out.println("ERRO: Digite uma opção válida.");
            }
        }

        String estado = "";
        while (true) {
            System.out.println("Estado: ");
            estado = scanner.nextLine();

            if (estado.matches(".*\\d.*")) System.out.println("ERRO: O nome do estado não pode conter números.");
            else if (estado.trim().isEmpty()) System.out.println("ERRO: O nome do estado não pode estar vazio.");
            else break;
        }

        String cidade = "";
        while (true) {
            System.out.println("Cidade: ");
            cidade = scanner.nextLine();
            if (cidade.matches(".*\\d.*")) System.out.println("ERRO: O nome do cidade não pode conter números.");
            else if (cidade.trim().isEmpty()) System.out.println("ERRO: O nome do cidade não pode estar vazio.");
            else break;
        }

        System.out.println("Bairro: ");
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

        String escolha = "";
        while (true) {
            try {
                System.out.println("1- Pessoa Física | 2- Pessoa Institucional:");
                escolha = scanner.nextLine();
                if (escolha.equals("1") || escolha.equals("2")) break;
                System.out.println("ERRO: Digite 1 para Pessoa Física e 2 para Institucional.");
            }catch (Exception e) {
                System.out.println("ERRO: Digite um número válido.");
            }
        }

        br.ufjf.dcc.model.Investidor novoInv = null;
        Carteira carteira = new Carteira(null);

        if (escolha.equals("1")) {
            String cpf = "";
            while (true) {
                try {
                    System.out.println("CPF: ");
                    cpf = scanner.nextLine();
                    String cpfLimpo = cpf.replaceAll("\\D", "");
                    cpf =  cpfLimpo;
                    if (cpf.length() == 11) break;
                    System.out.println("ERRO: Digitos em excesso ou faltando. Tente novamente.");
                }catch (Exception e) {
                    System.out.println("ERRO: CPF inválido.");
                }
            }

            String perfilString = "";
            while (true) {
                System.out.println("Perfil de investimento : [1] Conservador | [2] Moderado | [3] Arrojado ");
                perfilString =  scanner.nextLine();
                if (perfilString.equals("1") || perfilString.equals("2") || perfilString.equals("3")) break;
                System.out.println("ERRO: Valor inválido. Digite novamente.");
            }
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
                System.out.println("\nInvestidor cadastrado com sucesso!\n");
                bancoInvestidor.put(cpf, novoInv);
            }
        } else  if (escolha.equals("2")) {
            String cnpj = "";
            while (true) {
                try {
                    System.out.println("CNPJ: ");
                    cnpj= scanner.nextLine();
                    String cnpjLimpo = cnpj.replaceAll("\\D", "");
                    cnpj = cnpjLimpo;
                    if (cnpj.length() == 14) break;
                    System.out.println("ERRO: Digitos em excesso ou faltando. Tente novamente.");
                }catch (Exception e) {
                    System.out.println("ERRO: CPF inválido.");
                }
            }
            System.out.println("Razão Social: ");
            String razaoSocial = scanner.nextLine();
            novoInv = new PessoaInstitucional(nome, cnpj, telefone, dataNascimento, endereco, patrimonio, carteira, razaoSocial);

            if(novoInv != null){
                bancoInvestidor.put(cnpj, novoInv);
                System.out.println("\nInvestidor cadastrado com sucesso!\n");
            }
        }
    }

    public static void cadastrarInvestidorEmLote () {
        Scanner scanner = new Scanner(System.in);

        String caminho = "";
        while (true) {
            try {
                System.out.println("Digite o caminho do arquivo (ex: investidores.csv): ");
                caminho = scanner.nextLine();

                File file = new File(caminho);
                if (!file.exists() || !file.isFile()) {
                    System.out.println("ERRO: Arquivo não encontrado.");
                    continue;
                }
                else if (caminho.endsWith(".csv")) break;
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

    //tem que refatorar tudo isso aqui, pq o jeito de excluir é diferente

//    public static void excluirInvestidor () {
//        Scanner scanner = new Scanner(System.in);
//        //exibirInvestidores();
//        System.out.println("Digite o CPF/CNPJ do investidor que deseja excluir: ");
//        String investidorExcluir = scanner.nextLine().replaceAll("\\D", "");
//
//        br.ufjf.dcc.model.Investidor investidor = bancoInvestidor.get(investidorExcluir);
//
//        if (investidor != null) {
//            while (true) {
//                System.out.print("Tem certeza que deseja excluir " + investidor.getNome() + " | " + investidor.getIdentificador() + "? "  + "(S/N) ");
//                String escolha = scanner.nextLine().trim().toUpperCase();
//                if (escolha.equals("S")) {
//                    bancoInvestidor.remove(investidorExcluir);
//                    System.out.println("Excluido com sucesso!");
//                    return;
//                } else if (escolha.equals("N")) {
//                    System.out.println("Exclusão cancelada. \nSaindo da exclusão...");
//                    return;
//                }else System.out.println("ERRO: Entrada inválida! Digite 'S' para sim e 'N' para não");
//            }
//        } else System.out.println("ERRO: Investidor com id " + investidor.getIdentificador() + " não encontrado no sistema.");
//    }
}

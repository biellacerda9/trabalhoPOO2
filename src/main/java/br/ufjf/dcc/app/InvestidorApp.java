package br.ufjf.dcc.app;

import br.ufjf.dcc.io.CSVReader;
import br.ufjf.dcc.io.Utils;
import br.ufjf.dcc.model.*;
import br.ufjf.dcc.model.ativos.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


    public static void excluirInvestidores () {
        Scanner scanner = new Scanner(System.in);
        imprimirResumo();

        while (true) {
            System.out.print("Digite o CPF/CNPJ dos investidores que deseja excluir separados por vŕigula (ou 'S' para sair): ");
            String escolha = scanner.nextLine();

            if (escolha.equalsIgnoreCase("S")) {
                System.out.println("Saindo do processo de exclusão...");
                break;
            }

            String[] listaParaExcluir = escolha.split(",");

            for (String item : listaParaExcluir) {
                String cpfInvestidor = item.trim().replaceAll("\\D", "");

                if (cpfInvestidor.isEmpty()) {
                    continue;
                }

                if (bancoInvestidor.containsKey(cpfInvestidor)) {
                    bancoInvestidor.remove(cpfInvestidor);
                    System.out.println("Sucesso: Investidor [" + cpfInvestidor + "] e sua carteira foram removidos.");
                } else {
                    System.err.println("Erro: Investidor com documento [" + cpfInvestidor + "] não encontrado.");
                }
                System.out.println("\n>>> Processo concluído.");
            }
        }
    }


    public static Investidor selecionarInvestidor() {
        Scanner scanner = new Scanner(System.in);
        imprimirResumo();

        while (true) {
            System.out.print("Digite o CPF/CNPJ do investidor que deseja selecionar (ou 'S' para sair): ");
            String escolha = scanner.nextLine().trim();

            if (escolha.equalsIgnoreCase("S")) return null;

            String cpfInvestidorSelecionado = escolha.replaceAll("\\D", "");
            br.ufjf.dcc.model.Investidor investidor = bancoInvestidor.get(cpfInvestidorSelecionado);

            if (investidor != null) {
                return investidor;
            }
            System.err.println("Erro: Investidor com CPF/CNPJ " + cpfInvestidorSelecionado + " não encontrado no sistema.");
        }
    }


    // Função auxiliar para printar os investidores
    public static void imprimirResumo() {
        System.out.println("\n--- Lista de Investidores Registrados ---");
        for (Investidor a : bancoInvestidor.values()) {
            System.out.println(a.getNome() + " | CPF: " + a.getIdentificador());
        }
        System.out.println("------------------------------------------");
    }

    // Funções para o terceiro menu (Menu do Investidor)
    public static void excluirInvestidor(Investidor investidor) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Tem certeza que deseja excluir esse investidor? (S/N): ");
            String escolha = scanner.nextLine().trim();

            if (escolha.equalsIgnoreCase("S")) {

                if (bancoInvestidor.containsKey(investidor.getIdentificador())) {
                    bancoInvestidor.remove(investidor.getIdentificador());
                    System.out.println("Sucesso: Investidor e sua carteira foram removidos.");
                } else {
                    System.err.println("Erro: Investidor não encontrado.");
                }

                return;
            }

            if (escolha.equalsIgnoreCase("N")) {
                System.out.println("Operação cancelada.");
                return;
            }

            System.err.println("Entrada inválida. Digite apenas S ou N.");
        }
    }


    public static void salvarRelatorio(Investidor investidor) {
        Carteira carteira = investidor.getCarteira();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String data = LocalDateTime.now().format(formatter);

        String nomeArquivo = "relatorios/relatorio_" + investidor.getIdentificador() + "_" + data + ".json";

        try (FileWriter writer = new FileWriter(nomeArquivo)) {

            writer.write("{\n");

            // --- Investidor ---
            writer.write("  \"investidor\": {\n");
            writer.write("    \"nome\": \"" + investidor.getNome() + "\",\n");
            writer.write("    \"identificador\": \"" + investidor.getIdentificador() + "\",\n");
            writer.write("    \"telefone\": \"" + investidor.getTelefone() + "\",\n");
            writer.write("    \"dataNascimento\": \"" + investidor.getDataNascimento() + "\",\n");
            writer.write("    \"patrimonio\": " + investidor.getPatrimonio() + "\n");
            writer.write("  },\n");

            // --- Carteira ---
            writer.write("  \"carteira\": {\n");
            writer.write("    \"totalGasto\": " + carteira.getValorTotalGasto() + ",\n");
            writer.write("    \"totalAtual\": " + carteira.getValorTotalAtual() + ",\n");
            writer.write("    \"percentualRendaFixa\": " + carteira.getPercentualRendaFixa() + ",\n");
            writer.write("    \"percentualRendaVariavel\": " + carteira.getPercentualRendaVariavel() + ",\n");
            writer.write("    \"percentualNacional\": " + carteira.getPercentualNacional() + ",\n");
            writer.write("    \"percentualInternacional\": " + carteira.getPercentualInteracional() + ",\n");

            // --- Ativos ---
            writer.write("    \"ativos\": [\n");

            int contador = 0;
            int tamanho = carteira.getItens().size();

            for (ItemCarteira item : carteira.getItens().values()) {
                writer.write("      {\n");
                writer.write("        \"ticker\": \"" + item.getAtivo().getTicker() + "\",\n");
                writer.write("        \"quantidade\": " + item.getQuantidade() + ",\n");
                writer.write("        \"valorAtual\": " + item.getValorAtualEmReal() + "\n");
                writer.write("      }");

                contador++;
                if (contador < tamanho) writer.write(",");
                writer.write("\n");
            }

            writer.write("    ]\n");
            writer.write("  }\n");
            writer.write("}\n");

            System.out.println("Relatório salvo com sucesso em: " + nomeArquivo);

        } catch (IOException e) {
            System.err.println("Erro ao salvar o relatório: " + e.getMessage());
        }
    }

    public static void editarInvestidor(Investidor investidor) {
        Scanner scanner = new Scanner(System.in);
        String escolha = "";
        while (true) {
            System.out.println("--- EDITAR " + investidor.getNome().toUpperCase() + " ---");
            System.out.println("1-Nome, 2-Telefone, 3-Data de Nascimento, 4-Endereço, 5-Patrimônio, 6-Voltar");
            System.out.println("Escolha o campo para alterar:");
            escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                String novoNome = "";
                while (true) {
                    System.out.print("Novo Nome: ");
                    novoNome = scanner.nextLine();
                    if (novoNome.matches(".*\\d.*")) System.out.println("ERRO: O nome não pode conter números.");
                    else if (novoNome.trim().isEmpty()) System.out.println("ERRO: O nome não pode estar vazio.");
                    else {
                        investidor.setNome(novoNome);
                        System.out.println("Nome atualizado!");
                        break;
                    }
                }
            } else if (escolha.equals("2")) {
                while (true) {
                    System.out.print("Novo Telefone: ");
                    String tel = scanner.nextLine().replaceAll("\\D", "");
                    if (tel.length() == 11 || tel.length() == 10) {
                        investidor.setTelefone(tel);
                        System.out.println("Telefone atualizado!");
                        break;
                    }
                    System.out.println("ERRO: Formato inválido.");
                }
            } else if (escolha.equals("3")) {
                System.out.println("Nova Data de Nascimento: ");
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

                String novaData = String.format("%02d/%02d/%04d", dia, mes, ano);
                investidor.setDataNascimento(novaData);
                System.out.println("Data de nascimento atualizada!");

            } else if (escolha.equals("4")) {
                System.out.println("Novo endereço: ");
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
                Endereco novoEndereco = new Endereco(rua, numero, bairro, cidade, estado, cep);
                investidor.setEndereco(novoEndereco);
                System.out.println("Endereço atualizado!");

            } else if (escolha.equals("5")) {
                while (true) {
                    try {
                        System.out.print("Novo Patrimônio TOTAL: ");
                        double patri = Double.parseDouble(scanner.nextLine().replace(",", "."));
                        if (patri >= 0) {
                            investidor.setPatrimonio(patri);
                            System.out.println("Patrimônio atualizado!");
                            break;
                        }
                        System.out.println("ERRO: O patrimônio não pode ser negativo.");
                    } catch (Exception e) {
                        System.out.println("ERRO: Digite um valor válido.");
                    }
                }
            } else if (escolha.equals("6")) {
                System.out.println("Saindo da edição...");
                break;
            } else System.out.println("Opção inválida!");
        }
    }

    public static void movimentarCompra(Investidor investidor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSaldo atual: " + investidor.getPatrimonio());

        System.out.println("\nDigite o TICKER (ID) do ativo que deseja comprar: ");
        String ticker = scanner.nextLine().toUpperCase().trim();
        if (!bancoAtivos.containsKey(ticker)) {
            System.out.println("ERRO: Esse ticker não foi encontrado no sistema.");
        }

        Ativo ativo = bancoAtivos.get(ticker);

        System.out.println("\nAtivo selecionado: " + ativo.getNome() + " | " + ativo.getPrecoAtual());

        double quantidade = 0;
        System.out.println("Digite a quantidade desejada: ");
        quantidade = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Instituição responsável pela movimentação:");
        String instituicao = scanner.nextLine();

        String resultado = investidor.cadastrarInvestimento(ativo, quantidade, ativo.getPrecoAtual(), "COMPRA",  instituicao);
        System.out.println("\nResultado: " + resultado);
    }

    public static void movimentarVenda (Investidor investidor) {
        Scanner scanner = new Scanner(System.in);

        investidor.getCarteira().exibirAtivos();

        if (investidor.getCarteira().getItens().isEmpty()) {
            System.out.println("ERRO: Carteira não possui nenhum ativo!");
            return;
        }

        Carteira carteira = investidor.getCarteira();
        String ticker;

        // ===== LOOP PARA ESCOLHER ATIVO =====
        while (true) {
            System.out.println("\nDigite o TICKER (ID) do ativo que deseja vender (ou 'S' para sair):");
            ticker = scanner.nextLine().toUpperCase().trim();

            if (ticker.equals("S")) {
                System.out.println("Operação de venda cancelada.");
                return;
            }
            if (!bancoAtivos.containsKey(ticker)) {
                System.out.println("ERRO: Esse ticker não foi encontrado no sistema.");
                continue;
            }
            if (!carteira.getItens().containsKey(ticker)) {
                System.out.println("ERRO: Você não possui esse ativo na carteira.");
                continue;
            }
            break; // ticker válido
        }

        ItemCarteira item = carteira.getItens().get(ticker);
        Ativo ativo = item.getAtivo();

        System.out.println("\nAtivo selecionado: " + ativo.getNome() + " | " + ativo.getPrecoAtual());
        double quantidadeDisponivel = item.getQuantidade();
        System.out.println("Você possui " + quantidadeDisponivel + " unidades de " + ticker);

        String desejo = "";
        double quantidadeVender = 0;

        while (true) {
            System.out.println("Escolha uma opção: ");
            System.out.print("1.Vender tudo, 2.Vender quantidade personalizada ");
            desejo = scanner.nextLine();

            if (desejo.equals("1")) {
                quantidadeVender = quantidadeDisponivel;
                break;
            }
            else if (desejo.equals("2")) {
                System.out.println("Quanto deseja vender: ");

                try {
                    quantidadeVender = Utils.parseDoubleGeral(scanner.nextLine());

                    if (quantidadeVender > quantidadeDisponivel) {
                        System.out.println("ERRO: Você possui apenas " + quantidadeDisponivel + " unidades.");
                        continue;
                    }
                    if (quantidadeVender <= 0) {
                        System.out.println("ERRO: A quantidade desejada deve ser maior que 0.");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("ERRO: Entrada inválida!");
                }
            }
            else {
                System.out.println("ERRO: Opção inválida! Tente novamente!");
            }
        }

        System.out.println("Instituição responsável pela movimentação:");
        String instituicao = scanner.nextLine();

        String resultado = investidor.cadastrarInvestimento(
                ativo,
                quantidadeVender,
                ativo.getPrecoAtual(),
                "VENDA",
                instituicao
        );
        System.out.println("\nResultado: " + resultado);
    }
}

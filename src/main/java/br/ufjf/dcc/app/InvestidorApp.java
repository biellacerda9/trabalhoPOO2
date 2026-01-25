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
            System.out.println("Nome (ou 'S' para sair): ");
            nome = scanner.nextLine();

            if (nome.equalsIgnoreCase("S")) {
                System.out.println("Cadastro cancelado.\n");
                return;
            }
            if (nome.matches(".*\\d.*"))
                System.out.println("ERRO: O nome não pode conter números.");
            else if (nome.trim().isEmpty())
                System.out.println("ERRO: O nome não pode estar vazio.");
            else
                break;
        }
        String telefone = "";
        while (true) {
            System.out.println("Telefone (COM DDD) ou 'S' para sair: ");
            telefone = scanner.nextLine();

            if (telefone.equalsIgnoreCase("S")) {
                System.out.println("Cadastro cancelado.\n");
                return;
            }
            String telLimpo = telefone.replaceAll("\\D", "");
            if (telLimpo.length() == 11) {
                telefone = telLimpo;
                break;
            }
            System.out.println("ERRO: Telefone inválido.");
        }
        int dia, mes, ano;
        while (true) {
            System.out.println("Dia (ou 'S' para sair): ");
            String entrada = scanner.nextLine();

            if (entrada.equalsIgnoreCase("S")) {
                System.out.println("Cadastro cancelado.\n");
                return;
            }
            try {
                dia = Integer.parseInt(entrada);
                if (dia >= 1 && dia <= 31) break;
                System.out.println("ERRO: Dia inválido.");
            } catch (Exception e) {
                System.out.println("ERRO: Digite apenas números.");
            }
        }
        while (true) {
            System.out.println("Mês (ou 'S' para sair): ");
            String entrada = scanner.nextLine();

            if (entrada.equalsIgnoreCase("S")) {
                System.out.println("Cadastro cancelado.\n");
                return;
            }
            try {
                mes = Integer.parseInt(entrada);
                if (mes >= 1 && mes <= 12) break;
                System.out.println("ERRO: Mês inválido.");
            } catch (Exception e) {
                System.out.println("ERRO: Digite apenas números.");
            }
        }
        while (true) {
            System.out.println("Ano (ou 'S' para sair): ");
            String entrada = scanner.nextLine();

            if (entrada.equalsIgnoreCase("S")) {
                System.out.println("Cadastro cancelado.\n");
                return;
            }
            try {
                ano = Integer.parseInt(entrada);
                if (ano > 1900 && ano <= 2026) break;
                System.out.println("ERRO: Ano inválido.");
            } catch (Exception e) {
                System.out.println("ERRO: Digite apenas números.");
            }
        }
        String dataNascimento = String.format("%02d/%02d/%04d", dia, mes, ano);

        double patrimonio;
        while (true) {
            System.out.println("Patrimônio TOTAL (ou -1 para sair): ");
            try {
                patrimonio = scanner.nextDouble();
                scanner.nextLine();

                if (patrimonio == -1) {
                    System.out.println("Cadastro cancelado.\n");
                    return;
                }
                if (patrimonio >= 0) break;
                System.out.println("ERRO: O patrimônio não pode ser negativo.");
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Digite um valor válido.");
                scanner.nextLine();
            }
        }
        System.out.println("--- ENDEREÇO ---");

        String cep;
        while (true) {
            System.out.println("CEP (ou 'S' para sair): ");
            cep = scanner.nextLine();

            if (cep.equalsIgnoreCase("S")) {
                System.out.println("Cadastro cancelado.\n");
                return;
            }
            String cepLimpo = cep.replaceAll("\\D", "");
            if (cepLimpo.length() == 8) {
                cep = cepLimpo;
                break;
            }
            System.out.println("ERRO: CEP inválido.");
        }

        System.out.println("Estado: ");
        String estado = scanner.nextLine();

        System.out.println("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.println("Bairro: ");
        String bairro = scanner.nextLine();

        System.out.println("Rua: ");
        String rua = scanner.nextLine();

        System.out.println("Número: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep);

        String escolha;
        while (true) {
            System.out.println("1- Pessoa Física | 2- Pessoa Institucional | S- Sair");
            escolha = scanner.nextLine();

            if (escolha.equalsIgnoreCase("S")) {
                System.out.println("Cadastro cancelado.\n");
                return;
            }
            if (escolha.equals("1") || escolha.equals("2")) break;
            System.out.println("ERRO: Opção inválida.");
        }

        Investidor novoInv;
        Carteira carteira = new Carteira(null);

        if (escolha.equals("1")) {
            String cpf;
            while (true) {
                System.out.println("CPF (ou 'S' para sair): ");
                cpf = scanner.nextLine();

                if (cpf.equalsIgnoreCase("S")) {
                    System.out.println("Cadastro cancelado.\n");
                    return;
                }
                cpf = cpf.replaceAll("\\D", "");
                if (cpf.length() == 11) break;
                System.out.println("ERRO: CPF inválido.");
            }
            System.out.println("Perfil: [1] Conservador | [2] Moderado | [3] Arrojado");
            String perfilString = scanner.nextLine();

            PerfilInvestimento perfil =
                    perfilString.equals("1") ? PerfilInvestimento.CONSERVADOR :
                            perfilString.equals("2") ? PerfilInvestimento.MODERADO :
                                    perfilString.equals("3") ? PerfilInvestimento.ARROJADO : null;
            if (perfil == null) {
                System.out.println("Cadastro cancelado.\n");
                return;
            }
            novoInv = new PessoaFisica(nome, cpf, telefone, dataNascimento, endereco, patrimonio, carteira, perfil);
            bancoInvestidor.put(cpf, novoInv);

        } else {
            String cnpj;
            while (true) {
                System.out.println("CNPJ (ou 'S' para sair): ");
                cnpj = scanner.nextLine();

                if (cnpj.equalsIgnoreCase("S")) {
                    System.out.println("Cadastro cancelado.\n");
                    return;
                }
                cnpj = cnpj.replaceAll("\\D", "");
                if (cnpj.length() == 14) break;
                System.out.println("ERRO: CNPJ inválido.");
            }
            System.out.println("Razão Social: ");
            String razaoSocial = scanner.nextLine();

            novoInv = new PessoaInstitucional(nome, cnpj, telefone, dataNascimento, endereco, patrimonio, carteira, razaoSocial);
            bancoInvestidor.put(cnpj, novoInv);
        }
        System.out.println("\nInvestidor cadastrado com sucesso!\n");
    }


    public static void cadastrarInvestidorEmLote () {
        Scanner scanner = new Scanner(System.in);

        String caminho = "";
        while (true) {
            try {
                System.out.println("Digite o caminho do arquivo (ex: investidores.csv) ou 'S' para sair: ");
                caminho = scanner.nextLine();

                if (caminho.equalsIgnoreCase("S")) {
                    System.out.println("Cadastro em lote cancelado.\n");
                    return;
                }
                File file = new File(caminho);
                if (!file.exists() || !file.isFile()) {
                    System.out.println("ERRO: Arquivo não encontrado.");
                    continue;
                }
                if (!caminho.endsWith(".csv")) {
                    System.out.println("ERRO: O caminho do arquivo precisa terminar com '.csv'.");
                    continue;
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("ERRO: Digite um caminho válido para o arquivo.");
            }
        }

        List<String[]> linhas = CSVReader.lerCSV(caminho);

        // ordem - nome, cpf/cnpj, telefone, data, cep, estado, cidade, bairro, rua, numero, patrimonio, carteira, perfil/razao
        int falhas = 0;
        int tam = 0;

        for (String[] col : linhas) {
            try {
                String cep = col[4];
                String estado = col[5];
                String cidade = col[6];
                String bairro = col[7];
                String rua = col[8];
                int numero = Integer.parseInt(col[9]);

                Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep);

                String nome = col[0];
                String id = col[1];
                String telefone = col[2];
                String data = col[3];
                double patrimonio = Utils.parseDoubleGeral(col[10]);

                Investidor novoInv = null;
                Carteira carteira = new Carteira(null);

                tam = id.length();

                if (id.length() == 11) {
                    PerfilInvestimento perfil =
                            PerfilInvestimento.valueOf(col[12].toUpperCase().trim());
                    novoInv = new PessoaFisica(
                            nome, id, telefone, data, endereco, patrimonio, carteira, perfil
                    );
                } else if (id.length() == 14) {
                    String razaoSocial = col[12].trim();
                    novoInv = new PessoaInstitucional(
                            nome, id, telefone, data, endereco, patrimonio, carteira, razaoSocial
                    );
                } else {
                    falhas++;
                }
                if (novoInv != null) {
                    bancoInvestidor.put(novoInv.getIdentificador(), novoInv);
                }
            } catch (Exception e) {
                falhas++;
            }
        }
        if (falhas > 0)
            System.out.println(falhas + " falhas devido a erro nos dados. Verifique o arquivo CSV.");
        if (tam == 11)
            System.out.println("Lote de pessoas físicas cadastrado!");
        else if (tam == 14)
            System.out.println("Lote de pessoas institucionais cadastrado!");
    }


    public static void exibirInvestidores () {
        if (bancoInvestidor.isEmpty()) {
            System.out.println("\nNenhum investidor cadastrado.\n");
            return;
        }
        int paginaAtual = 0;
        int itensPorPagina = 50;

        while (true) {
            System.out.println("\n------- PÁGINA " + (paginaAtual + 1) + " -------");
            int contadorGeral = 0;
            int contadorExibidos = 0;
            int pulaPagina = paginaAtual * itensPorPagina;

            boolean temMaisPagina = false;

            for (Investidor a : bancoInvestidor.values()) {
                if (contadorGeral < pulaPagina) {
                    contadorGeral++;
                    continue;
                }
                if (contadorExibidos < itensPorPagina) {
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
            if (paginaAtual > 0) {
                System.out.print(" [A] Anterior |");
            }
            if (temMaisPagina) {
                System.out.print(" [P] Próximo |");
            }
            System.out.print(" [S] Sair ");
            System.out.print("-----\n");

            String opcao = scanner.nextLine().toUpperCase();

            if (opcao.equals("P")) {
                if (temMaisPagina) {
                    paginaAtual++;
                } else {
                    System.out.println("Você está na última página!");
                }
            } else if (opcao.equals("A")) {
                if (paginaAtual > 0) {
                    paginaAtual--;
                } else {
                    System.out.println("Você está na primeira página");
                }
            } else if (opcao.equals("S")) {
                break;
            } else {
                System.out.println("Valor inválido. Digite novamente!");
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
                        System.out.println("Nome atualizado para " + investidor.getNome());
                        break;
                    }
                }
            } else if (escolha.equals("2")) {
                while (true) {
                    System.out.print("Novo Telefone: ");
                    String tel = scanner.nextLine().replaceAll("\\D", "");
                    if (tel.length() == 11 || tel.length() == 10) {
                        investidor.setTelefone(tel);
                        System.out.println("Telefone atualizado para " + investidor.getTelefone());
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
                System.out.println("Data de nascimento atualizada para  " + investidor.getDataNascimento());

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
                System.out.println("Endereço atualizado para " + investidor.getEndereco().getCEP() + " | " + investidor.getEndereco().getCidade() + " | " + investidor.getEndereco().getEstado());

            } else if (escolha.equals("5")) {
                while (true) {
                    try {
                        System.out.print("Novo Patrimônio TOTAL: ");
                        double patri = Double.parseDouble(scanner.nextLine().replace(",", "."));
                        if (patri > 0) {
                            investidor.setPatrimonio(patri);
                            System.out.println("Patrimônio atualizado!");
                            break;
                        }
                        System.out.println("ERRO: O patrimônio não pode ser negativo nem nulo.");
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

        String ticker = "";
        while (true) {
            try {
                System.out.println("\nDigite o TICKER (ID) do ativo que deseja comprar: ");
                ticker = scanner.nextLine().toUpperCase().trim();
                if (bancoAtivos.containsKey(ticker)) break;
                System.out.println("ERRO: Esse ticker não foi encontrado no sistema.");
            } catch (Exception e) {
                System.out.println("ERRO: Digite um ticker válido.");
            }
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


    public static void cadastrarMovimentacaoEmLote(Investidor investidor) {
        Scanner scanner = new Scanner(System.in);

        String caminho;
        while (true) {
            try {
                System.out.println("Digite o caminho do arquivo (ex: movimentacoes.csv): ");
                caminho = scanner.nextLine();

                File file = new File(caminho);
                if (!file.exists() || !file.isFile()) {
                    System.out.println("ERRO: Arquivo não encontrado.");
                    continue;
                }
                if (!caminho.endsWith(".csv")) {
                    System.out.println("ERRO: O arquivo precisa ser .csv.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("ERRO: Caminho inválido.");
            }
        }
        List<String[]> linhas = CSVReader.lerCSV(caminho);

        for (String[] col : linhas) {
            try {
                String tipo = col[0].trim().toUpperCase(); // COMPRA ou VENDA
                String ticker = col[1].trim().toUpperCase();
                double quantidade = Utils.parseDoubleGeral(col[2]);
                double preco = Utils.parseDoubleGeral(col[3]);
                String instituicao = col[4].trim();

                if (!bancoAtivos.containsKey(ticker)) {
                    System.out.println("ERRO: Ativo " + ticker + " não existe. Linha ignorada.");
                    continue;
                }
                if (quantidade <= 0 || preco <= 0) {
                    System.out.println("ERRO: Quantidade ou preço inválido para " + ticker + ". Linha ignorada.");
                    continue;
                }
                Ativo ativo = bancoAtivos.get(ticker);

                String resultado = investidor.cadastrarInvestimento(
                        ativo,
                        quantidade,
                        preco,
                        tipo,
                        instituicao
                );
                System.out.println("[" + tipo + "] " + ticker + " → " + resultado);
            } catch (Exception e) {
                System.out.println("ERRO: Linha inválida no arquivo. Linha ignorada.");
            }
        }
        System.out.println("\nLote de movimentações processado com sucesso!\n");
    }
}

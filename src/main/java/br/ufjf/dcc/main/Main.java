package br.ufjf.dcc.main;

import br.ufjf.dcc.model.ativos.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    //"banco de dados"
    private static Map<String, Ativo> bancoAtivos = new HashMap<>();

    static void main() {
        exibirMenuPrincipal();
    }


    //funcao pra colocar os arquivos iniciais tambem no "banco"
    public static void carregarArquivosIniciais () {
        List<String[]> acao = lerCSV("acao.csv");
        for (String[] col : acao) {
            try {
                Acao a = new Acao(col[0], col[1], Double.parseDouble(col[2]), Boolean.parseBoolean(col[3]));
                bancoAtivos.put(a.getTicker().toUpperCase(), a);
            } catch (Exception e) {
                println("Erro ao carregar arquivo iniciais: " + e.getMessage());
            }
        }

        List<String[]> fii = lerCSV("fii.csv");
        for (String[] col : fii) {
            try {
                FII a = new FII(col[0], col [1], Double.parseDouble(col[2]), Boolean.parseBoolean(col[3]), col[4], Double.parseDouble(col[5]), Double.parseDouble(col[6]));
                bancoAtivos.put(a.getTicker().toUpperCase(), a);
            } catch (Exception e) {
                println("Erro ao carregar arquivo iniciais: " + e.getMessage());
            }
        }

        List<String[]> tesouro = lerCSV("tesouro.csv");
        for (String[] col : tesouro) {
            try {
                Tesouro a = new Tesouro(col[0], col [1], Double.parseDouble(col[2]), Boolean.parseBoolean(col[3]), col[4], col[5]);
                bancoAtivos.put(a.getTicker().toUpperCase(), a);
            } catch (Exception e) {
                println("Erro ao carregar arquivo iniciais: " + e.getMessage());
            }
        }

        List<String[]> stock = lerCSV("stock.csv");
        for (String[] col : stock) {
            try {
                Stock a = new Stock(col[0], col [1], Double.parseDouble(col[2]), Boolean.parseBoolean(col[3]),  col[4], col[5], Double.parseDouble(col[6]));
                bancoAtivos.put(a.getTicker().toUpperCase(), a);
            } catch (Exception e) {
                println("Erro ao carregar arquivo iniciais: " + e.getMessage());
            }
        }

        List<String[]> cripto = lerCSV("criptoativo.csv");
        for (String[] col : cripto) {
            try {
                Criptomoeda a = new Criptomoeda(col[0], col [1], Double.parseDouble(col[2]), Boolean.parseBoolean(col[3]), col[4], Double.parseDouble(col[5]), Double.parseDouble(col[6]));
                bancoAtivos.put(a.getTicker().toUpperCase(), a);
            } catch (Exception e) {
                println("Erro ao carregar arquivo iniciais: " + e.getMessage());
            }
        }

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
                cadastrarAtivo();
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

    public static void cadastrarAtivo() {
        Scanner scanner = new Scanner(System.in);
        println("Escolha o tipo de ativo: 1-Ação, 2-FII, 3-Stock, 4-Cripto, 5-Tesouro");
        String escolha = scanner.nextLine();
        println("Ticker: ");
        String ticker = scanner.nextLine();
        println("Nome: ");
        String nome = scanner.nextLine();
        println("Preço: ");
        double preco = scanner.nextDouble();
        println("Qualificado? (S/N)");
        boolean qualificado = scanner.nextLine().equalsIgnoreCase("S");

        if (ticker == null || ticker.isEmpty() || preco <= 0) {
            println("Erro ao cadastrar ativo. Tente novamente.");
            return;
        }

        Ativo novoAtivo = null;

        if (escolha.equals("1")) {
            novoAtivo = new Acao(nome, ticker, preco, qualificado);
        }
        else if (escolha.equals("2")) {
            println("Segmento: ");
            String segmento = scanner.nextLine();
            println("Valor do último dividendo: ");
            double valorDividendo = scanner.nextDouble();
            println("Taxa de administração: ");
            double taxaAdm = scanner.nextDouble();
            novoAtivo = new FII(nome, ticker, preco, qualificado,segmento, valorDividendo, taxaAdm);
        } else if (escolha.equals("3")) {
            println("Bolsa de Negociação: ");
            String bolsa = scanner.nextLine();
            println("Setor da empresa: ");
            String setor = scanner.nextLine();
            println("Fator de conversão: ");
            double  fator = scanner.nextDouble();

            novoAtivo = new Stock(nome, ticker, preco, qualificado,bolsa, setor, fator);
        } else if (escolha.equals("4")) {
            println("Algoritmo de consenso: ");
            String algoritmo = scanner.nextLine();
            println("Quantidade máxima de circulação: ");
            double quantidade = scanner.nextDouble();
            println("Fator de Conversão:");
            double fator = scanner.nextDouble();
            novoAtivo = new Criptomoeda(nome, ticker, preco, qualificado, algoritmo, quantidade, fator);
        } else  if (escolha.equals("5")) {
            println("Tipo de rendimento: ");

            String tipo = scanner.nextLine();
            println("Data de vencimento");
            String dataVencimento = scanner.nextLine();
            novoAtivo = new Tesouro(nome, ticker, preco, qualificado, tipo, dataVencimento);
        }
        else {
            println("Tipo de ativo inválido.");
            return;
        }
    }

    public static void cadastrarAtivoEmLote () {
        Scanner scanner = new Scanner(System.in);

        println("Digite o caminho do arquivo (ex: acoes.csv): ");
        String caminho = scanner.nextLine();

        println("Digite o tipo de ativos presente neste arquivo: ");
        println("1-Ação, 2-FII, 3-Stock, 4-Cripto, 5-Tesouro)");
        String tipoDoArquivo = scanner.nextLine();

        List<String[]> linhas = lerCSV(caminho);

        for  (String[] col : linhas) {
            Ativo novoAtivo = null;
            if (tipoDoArquivo.equals("1")){
                novoAtivo = new Acao(col[0], col [1], Double.parseDouble(col[2]), Boolean.parseBoolean(col[3]));
            } else if (tipoDoArquivo.equals("2")) {
                novoAtivo = new FII(col[0], col [1], Double.parseDouble(col[2]), Boolean.parseBoolean(col[3]), col[4], Double.parseDouble(col[5]), Double.parseDouble(col[6]));
            } else if (tipoDoArquivo.equals("3")) {
                novoAtivo = new Stock(col[0], col [1], Double.parseDouble(col[2]), Boolean.parseBoolean(col[3]),  col[4], col[5], Double.parseDouble(col[6]));
            } else if (tipoDoArquivo.equals("4")) {
                novoAtivo = new Criptomoeda(col[0], col [1], Double.parseDouble(col[2]), Boolean.parseBoolean(col[3]), col[4], Double.parseDouble(col[5]), Double.parseDouble(col[6]));
            } else if (tipoDoArquivo.equals("5")) {
                novoAtivo = new Tesouro(col[0], col [1], Double.parseDouble(col[2]), Boolean.parseBoolean(col[3]), col[4], col[5]);
            }

            if (novoAtivo != null) {
                bancoAtivos.put(novoAtivo.getTicker().toUpperCase() , novoAtivo);
            }
        }
    }

    public static void exibirAtivosDoBanco () {
        println("LISTA DE ATIVOS NO SISTEMA:");
        for (Ativo a :  bancoAtivos.values()) {
            println(a.getTicker() + " | " + a.getNome() + " | Preço Atual: R$ " + a.getPrecoAtual());
        }
    }

    public static void editarAtivo () {
        Scanner scanner = new Scanner(System.in);
        println("Qual tipo de ativo deseja editar? ");
        println("1-Ação, 2-FII, 3-Stock, 4-Cripto, 5-Tesouro");
        String tipoDoArquivo = scanner.nextLine();

        if (tipoDoArquivo.equals("1")) {
            println("Digite o ticker (ID) do arquivo que deseja editar: ");
            String tickerEditar = scanner.nextLine();

            while (true) {
                for (Ativo a :  bancoAtivos.values()) {
                    if (a.getTicker().equals(tickerEditar.toUpperCase())) {
                        println("O que deseja editar?");
                        println("1- Nome, 2- Ticker, 3- Preço, 4- Qualificação, 5-SAIR");
                        String escolha = scanner.nextLine();

                        if (escolha.equals("1")) {
                            println("Digite o novo nome: ");
                            String novoNome =  scanner.nextLine();
                            a.setNome(novoNome);
                        } else if  (escolha.equals("2")) {
                            println("Digite o novo ticker: ");
                            String novoTicker = scanner.nextLine();
                            a.setTicker(novoTicker);
                        } else if   (escolha.equals("3")) {
                            println("Digite o novo preço: ");
                        } else if (escolha.equals("4")) {
                            println("Digite a nova qualificação (SIM/NÃO)");
                            String novaQualificacao = scanner.nextLine();
                            if (novaQualificacao.equalsIgnoreCase("SIM")) {
                                a.setQualificado(true);
                            } else if (novaQualificacao.equalsIgnoreCase("NÃO")) {
                                a.setQualificado(false);
                            } else {
                                println("Valor inválido, tente novamente!");
                                //logica para perguntar de novo a resposta
                            }
                        } else if   (escolha.equals("5")) {
                            break;
                        }
                    }
                }
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

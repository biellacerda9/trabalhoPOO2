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
        carregarArquivosIniciais();
        exibirMenuPrincipal();
    }

    // Função auxiliar para tratar dados vindos dos CSVs
    private static double parseDoubleGeral(String valor) {
        if (valor == null || valor.trim().isEmpty() || valor.equals("-")) return 0.0;
        try {
            if (valor.contains(",")) {
                return Double.parseDouble(valor.replace(".", "").replace(",", "."));
            }
            return Double.parseDouble(valor);
        } catch (Exception e) {
            return 0.0;
        }
    }


    //funcao pra colocar os arquivos iniciais tambem no "banco"
    public static void carregarArquivosIniciais() {
        println("Carregando arquivos iniciais...");

        List<String[]> acaoLinhas = lerCSV("acao.csv");
        for (String[] col : acaoLinhas) {
            try {
                boolean qualificado = col[3].equals("1");
                double preco = parseDoubleGeral(col[2]);
                Acao a = new Acao(col[1], col[0], preco, qualificado);
                bancoAtivos.put(a.getTicker().toUpperCase(), a);
            } catch (Exception e) {  }
        }

        List<String[]> fiiLinhas = lerCSV("fii.csv");
        for (String[] col : fiiLinhas) {
            try {
                double preco = parseDoubleGeral(col[3]);
                double div = parseDoubleGeral(col[4]);
                double taxa = parseDoubleGeral(col[5]);
                FII f = new FII(col[1], col[0], preco, false, col[2], div, taxa);
                bancoAtivos.put(f.getTicker().toUpperCase(), f);
            } catch (Exception e) { }
        }

        List<String[]> tesouroLinhas = lerCSV("tesouro.csv");
        for (String[] col : tesouroLinhas) {
            try {
                double preco = parseDoubleGeral(col[2]);
                Tesouro t = new Tesouro(col[1], col[0], preco, false, col[3], col[4]);
                bancoAtivos.put(t.getTicker().toUpperCase(), t);
            } catch (Exception e) { }
        }

        List<String[]> stockLinhas = lerCSV("stock.csv");
        for (String[] col : stockLinhas) {
            try {
                double preco = parseDoubleGeral(col[2]);
                Stock s = new Stock(col[1], col[0], preco, false, col[3], col[4], 5.50);
                bancoAtivos.put(s.getTicker().toUpperCase(), s);
            } catch (Exception e) { }
        }

        List<String[]> criptoLinhas = lerCSV("criptoativo.csv");
        for (String[] col : criptoLinhas) {
            try {
                double preco = parseDoubleGeral(col[2]);
                double qtdMax = col.length > 4 ? parseDoubleGeral(col[4]) : 0;
                Criptomoeda c = new Criptomoeda(col[1], col[0], preco, false, col[3], qtdMax, 5.50);
                bancoAtivos.put(c.getTicker().toUpperCase(), c);
            } catch (Exception e) { }
        }

        println("Carga finalizada! Total de ativos no banco: " + bancoAtivos.size());
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
                cadastrarAtivo();
            } else if (escolha.equals("2")) {
                println("Entrou no 2.");
            } else if (escolha.equals("3")) {
                editarAtivo();
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
        println("Escolha o tipo de ativo: 1. Ação, 2. FII, 3. Stock, 4. Cripto, 5. Tesouro");
        String escolha = scanner.nextLine();

        println("Ticker: ");
        String ticker = scanner.nextLine();
        println("Nome: ");
        String nome = scanner.nextLine();

        double preco = 0;
        println("Preço: ");
        try {
            String entradaPreco = scanner.nextLine().replace(",", ".");
            preco = Double.parseDouble(entradaPreco);
        } catch (NumberFormatException e) {
            println("ERRO: Preço inválido! O cadastro foi cancelado.");
            println("Use apenas números e ponto ou vírgula para decimais.");
            return;
        }

        println("Qualificado? (S/N)");
        boolean qualificado = scanner.nextLine().equalsIgnoreCase("S");

        if (ticker == null || ticker.isEmpty() || preco <= 0) {
            println("Erro ao cadastrar ativo. Tente novamente.");
            return;
        }

        Ativo novoAtivo = null;

        try {
            if (escolha.equals("1")) {
                novoAtivo = new Acao(nome, ticker, preco, qualificado);
            } else if (escolha.equals("2")) {
                println("Segmento: ");
                String segmento = scanner.nextLine();
                println("Valor do último dividendo: ");
                double valorDividendo = Double.parseDouble(scanner.nextLine().replace(",", "."));
                println("Taxa de administração: ");
                double taxaAdm = Double.parseDouble(scanner.nextLine().replace(",", "."));
                novoAtivo = new FII(nome, ticker, preco, qualificado, segmento, valorDividendo, taxaAdm);
            } else if (escolha.equals("3")) {
                println("Bolsa de Negociação: ");
                String bolsa = scanner.nextLine();
                println("Setor da empresa: ");
                String setor = scanner.nextLine();
                println("Fator de conversão: ");
                double fator = Double.parseDouble(scanner.nextLine().replace(",", "."));
                novoAtivo = new Stock(nome, ticker, preco, qualificado, bolsa, setor, fator);
            } else if (escolha.equals("4")) {
                println("Algoritmo de consenso: ");
                String algoritmo = scanner.nextLine();
                println("Quantidade máxima de circulação: ");
                double quantidade = Double.parseDouble(scanner.nextLine().replace(",", "."));
                println("Fator de Conversão:");
                double fator = Double.parseDouble(scanner.nextLine().replace(",", "."));
                novoAtivo = new Criptomoeda(nome, ticker, preco, qualificado, algoritmo, quantidade, fator);
            } else if (escolha.equals("5")) {
                println("Tipo de rendimento: ");
                String tipo = scanner.nextLine();
                println("Data de vencimento: ");
                String dataVencimento = scanner.nextLine();
                novoAtivo = new Tesouro(nome, ticker, preco, qualificado, tipo, dataVencimento);
            } else {
                println("Tipo de ativo inválido.");
                return;
            }
        } catch (NumberFormatException e) {
            println("ERRO: Um dos valores numéricos específicos é inválido. Cadastro cancelado.");
            return;
        }

        // Salva no banco de dados
        if (novoAtivo != null) {
            bancoAtivos.put(ticker, novoAtivo);
            println("Sucesso: Ativo " + ticker + " cadastrado!");
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
        exibirAtivosDoBanco();
        println("Digite o ticker (ID) do ativo que deseja editar: ");
        String tickerEditar = scanner.nextLine().trim().toUpperCase();

        Ativo ativo = bancoAtivos.get(tickerEditar);

        if (ativo != null) {
            String escolha = "";
            while (!escolha.equals("0")) {
                println("\nEditando: " + ativo.getNome());
                println("1. Nome, 2. Ticker, 3. Preço, 4. Qualificação");

                String menuExtra = ativo.getMenuEspecifico();
                if (!menuExtra.isEmpty()) {
                    println(menuExtra);
                }
                println("0. Sair");

                escolha = scanner.nextLine();

                if (escolha.equals("1") || escolha.equals("2") || escolha.equals("3") || escolha.equals("4")) {
                    ativo.editarCamposComuns(escolha, scanner);
                } else if (!escolha.equals("0")) {
                    ativo.editarCamposEspecificos(escolha, scanner);
                }
            }
        } else {
            println("Ativo não encontrado!");
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
                    .map(linha -> linha.split(";")) // Quebra a linha do registro em um array de strings, separados por virgulas
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

package br.ufjf.dcc.app;

import br.ufjf.dcc.io.CSVReader;
import br.ufjf.dcc.io.Utils;
import br.ufjf.dcc.model.ativos.*;
import java.util.List;
import java.util.Scanner;
import static br.ufjf.dcc.data.BancoAtivos.bancoAtivos;

public class AtivoApp {

    //funcao pra colocar os arquivos iniciais tambem no "banco"
    public static void carregarArquivosIniciais() {
        System.out.println("Carregando arquivos iniciais...");

        List<String[]> acaoLinhas = CSVReader.lerCSV("acao.csv");
        for (String[] col : acaoLinhas) {
            try {
                boolean qualificado = col[3].equals("1");
                double preco = Utils.parseDoubleGeral(col[2]);
                Acao a = new Acao(col[1], col[0], preco, qualificado);
                bancoAtivos.put(a.getTicker().toUpperCase(), a);
            } catch (Exception e) {  }
        }

        List<String[]> fiiLinhas = CSVReader.lerCSV("fii.csv");
        for (String[] col : fiiLinhas) {
            try {
                double preco = Utils.parseDoubleGeral(col[3]);
                double div = Utils.parseDoubleGeral(col[4]);
                double taxa = Utils.parseDoubleGeral(col[5]);
                FII f = new FII(col[1], col[0], preco, false, col[2], div, taxa);
                bancoAtivos.put(f.getTicker().toUpperCase(), f);
            } catch (Exception e) { }
        }

        List<String[]> tesouroLinhas = CSVReader.lerCSV("tesouro.csv");
        for (String[] col : tesouroLinhas) {
            try {
                double preco = Utils.parseDoubleGeral(col[2]);
                Tesouro t = new Tesouro(col[1], col[0], preco, false, col[3], col[4]);
                bancoAtivos.put(t.getTicker().toUpperCase(), t);
            } catch (Exception e) { }
        }

        List<String[]> stockLinhas = CSVReader.lerCSV("stock.csv");
        for (String[] col : stockLinhas) {
            try {
                double preco = Utils.parseDoubleGeral(col[2]);
                Stock s = new Stock(col[1], col[0], preco, false, col[3], col[4], 5.50);
                bancoAtivos.put(s.getTicker().toUpperCase(), s);
            } catch (Exception e) { }
        }

        List<String[]> criptoLinhas = CSVReader.lerCSV("criptoativo.csv");
        for (String[] col : criptoLinhas) {
            try {
                double preco = Utils.parseDoubleGeral(col[2]);
                double qtdMax = col.length > 4 ? Utils.parseDoubleGeral(col[4]) : 0;
                Criptomoeda c = new Criptomoeda(col[1], col[0], preco, false, col[3], qtdMax, 5.50);
                bancoAtivos.put(c.getTicker().toUpperCase(), c);
            } catch (Exception e) { }
        }
        System.out.println("Carga finalizada! Total de ativos no banco: " + bancoAtivos.size());
    }


    public static void cadastrarAtivo() {
        Scanner scanner = new Scanner(System.in);
        String escolha = "";

        while (true) {
            System.out.println("Escolha o tipo de ativo: 1. Ação, 2. FII, 3. Stock, 4. Cripto, 5. Tesouro");
            escolha = scanner.nextLine();

            if (escolha.equals("1") || escolha.equals("2") || escolha.equals("3") ||
                    escolha.equals("4") || escolha.equals("5")) {
                break;
            } else {
                System.out.println("Opção INVÁLIDA! Por favor, escolbha um número de 1 a 5.");
            }
        }

        System.out.println("Ticker: ");
        String ticker = scanner.nextLine().toUpperCase();
        System.out.println("Nome: ");
        String nome = scanner.nextLine();

        double preco = 0;
        while (true) {
            System.out.println("Preço: ");
            String entradaPreco = scanner.nextLine().replace(",", ".");

            try {
                preco = Double.parseDouble(entradaPreco);

                if (preco > 0) break;
                else System.out.println("ERRO! O preço deve ser maior que ZERO.");
            } catch (NumberFormatException e) {
                System.out.println("ERRO: O valor não pode conter letras.");
                System.out.println("Use apenas números e ponto ou vírgula para decimais.");
            }
        }

        boolean qualificado = false;
        while (true) {
            System.out.println("Qualificado? (S/N)");
            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.equals("S")) {
                qualificado = true;
                break;
            } else if (entrada.equals("N")) {
                qualificado = false;
                break;
            } else System.out.println("ERRO: Entrada inválida! Digite apenas 'S' para SIM ou 'N' para NÃO.");
        }

        if (ticker == null || ticker.isEmpty() || preco <= 0) {
            System.out.println("Erro ao cadastrar ativo. Tente novamente.");
            return;
        }

        br.ufjf.dcc.model.ativos.Ativo novoAtivo = null;

            if (escolha.equals("1")) {
                novoAtivo = new Acao(nome, ticker, preco, qualificado);
            } else if (escolha.equals("2")) {
                System.out.println("Segmento: ");
                String segmento = scanner.nextLine();

                double valorDividendo = 0;
                while (true) {
                    try {
                        System.out.println("Valor do último dividendo: ");
                        valorDividendo = Double.parseDouble(scanner.nextLine().replace(",", "."));
                        if (valorDividendo >= 0) break;
                        System.out.println("ERRO: O dividendo não pode ser negativo.");
                    } catch (NumberFormatException e) {
                        System.out.println("ERRO: Digite um valor numérico válido!");
                    }
                }

                double taxaAdm = 0;
                while(true) {
                    try {
                        System.out.println("Taxa de administração: ");
                        taxaAdm = Double.parseDouble(scanner.nextLine().replace(",", "."));
                        if (taxaAdm >= 0) break;
                        System.out.println("ERRO: A taxa de administração não pode ser negativa.");
                    }catch (NumberFormatException e) {
                        System.out.println("Erro: Digite um valor numérico válido!");
                    }
                }
                novoAtivo = new FII(nome, ticker, preco, qualificado, segmento, valorDividendo, taxaAdm);
            } else if (escolha.equals("3")) {
                System.out.println("Bolsa de Negociação: ");
                String bolsa = scanner.nextLine();
                System.out.println("Setor da empresa: ");
                String setor = scanner.nextLine();

                double fator = 0;
                while (true) {
                    try {
                        System.out.println("Fator de conversão: ");
                        fator = Double.parseDouble(scanner.nextLine().replace(",", "."));
                        if (fator >= 0) break;
                        System.out.println("ERRO: O fator de conversão não pode ser negativo.");
                    }catch (NumberFormatException e) {
                        System.out.println("Erro: Digite um valor numérico válido!");
                    }
                }
                novoAtivo = new Stock(nome, ticker, preco, qualificado, bolsa, setor, fator);
            } else if (escolha.equals("4")) {
                System.out.println("Algoritmo de consenso: ");
                String algoritmo = scanner.nextLine();

                double quantidade = 0;
                while (true) {
                    try {
                        System.out.println("Quantidade máxima de circulação: ");
                        quantidade = Double.parseDouble(scanner.nextLine().replace(",", "."));
                        if (quantidade >=0) break;
                        System.out.println("ERRO: A quantidade não pode ser negativa.");
                    }catch (NumberFormatException e) {
                        System.out.println("Erro: Digite um valor numérico válido!");
                    }
                }
                double fator = 0;
                while (true) {
                    try {
                        System.out.println("Fator de conversão: ");
                        fator = Double.parseDouble(scanner.nextLine().replace(",", "."));
                        if (fator >= 0) break;
                        System.out.println("ERRO: O fator de conversão não pode ser negativo.");
                    }catch (NumberFormatException e) {
                        System.out.println("Erro: Digite um valor numérico válido!");
                    }
                }
                novoAtivo = new Criptomoeda(nome, ticker, preco, qualificado, algoritmo, quantidade, fator);
            } else if (escolha.equals("5")) {
                System.out.println("Tipo de rendimento: ");
                String tipo = scanner.nextLine();
                System.out.println("Data de vencimento: ");
                String dataVencimento = scanner.nextLine();
                novoAtivo = new Tesouro(nome, ticker, preco, qualificado, tipo, dataVencimento);
            } else {
                System.out.println("Tipo de ativo inválido.");
                return;
            }

        // Salva no banco de dados
        if (novoAtivo != null) {
            bancoAtivos.put(ticker, novoAtivo);
            System.out.println("Sucesso: Ativo " + ticker + " cadastrado!");
        }
    }


    public static void cadastrarAtivoEmLote () {
        Scanner scanner = new Scanner(System.in);

        String caminho = "";
        while (true) {
            try {
                System.out.println("Digite o caminho do arquivo (ex: acoes.csv): ");
                caminho = scanner.nextLine();
                if (caminho.endsWith(".csv")) break;
                System.out.println("ERRO: O caminho do arquivo precisa terminar com '.csv'.");
            }catch (IllegalArgumentException e) {
                System.out.println("ERRO: Digite um caminho válido para o arquivo.");
            }
        }

        System.out.println("Digite o tipo de ativos presente neste arquivo: ");
        System.out.println("1-Ação, 2-FII, 3-Stock, 4-Cripto, 5-Tesouro)");
        String tipoDoArquivo = scanner.nextLine();

        List<String[]> linhas = CSVReader.lerCSV(caminho);

        for  (String[] col : linhas) {
            br.ufjf.dcc.model.ativos.Ativo novoAtivo = null;
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
        int paginaAtual= 0;
        int itensPorPagina = 50;

        while(true) {
            System.out.println("\n------- PÁGINA " + (paginaAtual + 1) + " -------");
            int contadorGeral = 0;
            int contadorExibidos = 0;
            int pulaPagina = paginaAtual * itensPorPagina;

            boolean temMaisPagina = false;

            for (br.ufjf.dcc.model.ativos.Ativo a : bancoAtivos.values()) {
                if(contadorGeral < pulaPagina){
                    contadorGeral++;
                    continue;
                }

                if(contadorExibidos < itensPorPagina){
                    System.out.println(a.getTicker() + " | " + a.getNome() + " | Preço Atual: R$ " + a.getPrecoAtual());
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


    public static void editarAtivo () {
        Scanner scanner = new Scanner(System.in);
        exibirAtivosDoBanco();
        System.out.println("Digite o ticker (ID) do ativo que deseja editar: ");
        String tickerEditar = scanner.nextLine().trim().toUpperCase();

        br.ufjf.dcc.model.ativos.Ativo ativo = bancoAtivos.get(tickerEditar);

        if (ativo != null) {
            String escolha = "";
            while (!escolha.equals("0")) {
                System.out.println("\n--- MODO DE EDIÇÃO ---");
                System.out.println("DADOS: " + ativo.getNome() + " | " + ativo.getPrecoAtual() + " | "  + ativo.getTicker() + " | "  + ativo.isQualificado());
                System.out.println("1. Nome, 2. Ticker, 3. Preço, 4. Qualificação");

                String menuExtra = ativo.getMenuEspecifico();
                if (!menuExtra.isEmpty()) {
                    System.out.println(menuExtra);
                }
                System.out.println("0. Sair");

                escolha = scanner.nextLine();

                if (escolha.equals("1") || escolha.equals("2") || escolha.equals("3") || escolha.equals("4")) {
                    ativo.editarCamposComuns(escolha, scanner);
                } else if (!escolha.equals("0")) {
                    ativo.editarCamposEspecificos(escolha, scanner);
                }
            }
        } else {
            System.out.println("Ativo não encontrado!");
        }
    }


    public static void excluirAtivo () {
        Scanner scanner = new Scanner(System.in);
        exibirAtivosDoBanco();
        System.out.println("Digite o ticker (ID) do ativo que deseja excluir: ");
        String tickerExcluir =  scanner.nextLine().trim().toUpperCase();

        br.ufjf.dcc.model.ativos.Ativo ativo = bancoAtivos.get(tickerExcluir);

        String escolha = "";
        if (ativo != null) {
            while (true) {
                try {
                    System.out.print("Tem certeza que deseja excluir " + ativo.getTicker() + " | " + ativo.getNome() + "? "  + "(S/N) ");
                    escolha = scanner.nextLine().trim().toUpperCase();
                    if (escolha.equals("S")) {
                        bancoAtivos.remove(tickerExcluir);
                        System.out.println("Excluido com sucesso!");
                        return;
                    } else if (escolha.equals("N")) {
                        System.out.println("Exclusão cancelada. \nSaindo da exclusão...");
                        return;
                    }
                    System.out.println("ERRO: Entrada inválida! Digite 'S' para sim e 'N' para não");
                }catch (Exception e) {
                    System.out.println("ERRO: Digite um valor válido.");
                }
            }
        }
    }


    public static void exibirRelatorioAtivos () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- RELATÓRIO DE ATIVOS ---");
        while (true) {
            System.out.println("1. Exibir todos os ativos");
            System.out.println("2. Exibir apenas AÇÕES");
            System.out.println("3. Exibir apenas FIIs");
            System.out.println("4. Exibir apenas CRIPTOATIVOS");
            System.out.println("5. Exibir apenas STOCKS");
            System.out.println("6. Exibir apenas TESOURO");
            System.out.println("0. Voltar");
            System.out.println("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                System.out.println("Exibindo todos os ativos: ");
                exibirAtivosDoBanco();
            } else if  (escolha.equals("2")) {
                System.out.println("Exibindo apenas as AÇÕES: ");

                for (br.ufjf.dcc.model.ativos.Ativo a : bancoAtivos.values()) {
                    if (a instanceof Acao) {
                        System.out.println(a.getTicker() + " | " + a.getNome() + " | Preço Atual: R$ " + a.getPrecoAtual());
                    }
                }
            } else if (escolha.equals("3")) {
                System.out.println("Exibindo apenas FIIs: ");
                for (br.ufjf.dcc.model.ativos.Ativo a : bancoAtivos.values()) {
                    if (a instanceof FII) {
                        System.out.println(a.getTicker() + " | " + a.getNome() + " | Preço Atual: R$ " + a.getPrecoAtual());
                    }
                }
            } else if  (escolha.equals("4")) {
                System.out.println("Exibindo apenas CRIPTOATIVOS: ");
                for (br.ufjf.dcc.model.ativos.Ativo a : bancoAtivos.values()) {
                    if (a instanceof Criptomoeda) {
                        System.out.println(a.getTicker() + " | " + a.getNome() + " | Preço Atual: R$ " + a.getPrecoAtual());
                    }
                }
            } else if  (escolha.equals("5")) {
                System.out.println("Exibindo apenas STOCKS: ");
                for (br.ufjf.dcc.model.ativos.Ativo a : bancoAtivos.values()) {
                    if (a instanceof Stock) {
                        System.out.println(a.getTicker() + " | " + a.getNome() + " | Preço Atual: R$ " + a.getPrecoAtual());
                    }
                }
            } else if (escolha.equals("6")) {
                System.out.println("Exibindo apenas TESOURO: ");
                for (br.ufjf.dcc.model.ativos.Ativo a : bancoAtivos.values()) {
                    if (a instanceof Tesouro) {
                        System.out.println(a.getTicker() + " | " + a.getNome() + " | Preço Atual: R$ " + a.getPrecoAtual());
                    }
                }
            } else if   (escolha.equals("0")) {
                System.out.println("Voltando...");
                break;
            }
        }
    }
}

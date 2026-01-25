package br.ufjf.dcc.app;

import br.ufjf.dcc.io.CSVReader;
import br.ufjf.dcc.io.Utils;
import br.ufjf.dcc.model.ativos.*;

import java.io.File;
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
            System.out.println("Escolha o tipo de ativo: 1. Ação, 2. FII, 3. Stock, 4. Cripto, 5. Tesouro, V. Voltar");
            escolha = scanner.nextLine();

            if (escolha.equalsIgnoreCase("V")) {
                System.out.println("Voltando...");
                return;
            }
            if (escolha.equals("1") || escolha.equals("2") || escolha.equals("3") ||
                    escolha.equals("4") || escolha.equals("5")) {
                break;
            } else {
                System.out.println("Opção INVÁLIDA!");
            }
        }
        String ticker;
        while (true) {
            System.out.println("Ticker (ou 'V' para voltar): ");
            ticker = scanner.nextLine().trim().toUpperCase();

            if (ticker.equalsIgnoreCase("V")) {
                System.out.println("Voltando...");
                return;
            }
            if (!ticker.isEmpty()) break;
            System.out.println("ERRO: O identificador não pode ser vazio.");
        }
        System.out.println("Nome (ou 'V' para voltar): ");
        String nome = scanner.nextLine();
        if (nome.equalsIgnoreCase("V")) return;

        double preco;
        while (true) {
            System.out.println("Preço (ou 'V' para voltar): ");
            String entradaPreco = scanner.nextLine();

            if (entradaPreco.equalsIgnoreCase("V")) {
                System.out.println("Voltando...");
                return;
            }
            try {
                preco = Double.parseDouble(entradaPreco.replace(",", "."));
                if (preco > 0) break;
                System.out.println("ERRO: O preço deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Valor inválido.");
            }
        }
        boolean qualificado;
        while (true) {
            System.out.println("Qualificado? (S/N ou V para voltar)");
            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.equals("V")) {
                System.out.println("Voltando...");
                return;
            }
            if (entrada.equals("S")) {
                qualificado = true;
                break;
            } else if (entrada.equals("N")) {
                qualificado = false;
                break;
            } else {
                System.out.println("ERRO: Digite S, N ou V.");
            }
        }
        Ativo novoAtivo = null;

        if (escolha.equals("1")) {
            novoAtivo = new Acao(nome, ticker, preco, qualificado);
        } else if (escolha.equals("2")) {
            System.out.println("Segmento (ou 'V' para voltar): ");
            String segmento = scanner.nextLine();
            if (segmento.equalsIgnoreCase("V")) return;

            double valorDividendo;
            while (true) {
                System.out.println("Valor do último dividendo (ou 'V' para voltar): ");
                String entrada = scanner.nextLine();
                if (entrada.equalsIgnoreCase("V")) return;

                try {
                    valorDividendo = Double.parseDouble(entrada.replace(",", "."));
                    if (valorDividendo >= 0) break;
                    System.out.println("ERRO: Valor inválido.");
                } catch (NumberFormatException e) {
                    System.out.println("ERRO: Digite um número válido.");
                }
            }
            double taxaAdm;
            while (true) {
                System.out.println("Taxa de administração (ou 'V' para voltar): ");
                String entrada = scanner.nextLine();
                if (entrada.equalsIgnoreCase("V")) return;

                try {
                    taxaAdm = Double.parseDouble(entrada.replace(",", "."));
                    if (taxaAdm >= 0) break;
                    System.out.println("ERRO: Valor inválido.");
                } catch (NumberFormatException e) {
                    System.out.println("ERRO: Digite um número válido.");
                }
            }
            novoAtivo = new FII(nome, ticker, preco, qualificado, segmento, valorDividendo, taxaAdm);

        } else if (escolha.equals("3")) {
            System.out.println("Bolsa de negociação (ou 'V' para voltar): ");
            String bolsa = scanner.nextLine();
            if (bolsa.equalsIgnoreCase("V")) return;

            System.out.println("Setor da empresa (ou 'V' para voltar): ");
            String setor = scanner.nextLine();
            if (setor.equalsIgnoreCase("V")) return;

            double fator;
            while (true) {
                System.out.println("Fator de conversão (ou 'V' para voltar): ");
                String entrada = scanner.nextLine();
                if (entrada.equalsIgnoreCase("V")) return;

                try {
                    fator = Double.parseDouble(entrada.replace(",", "."));
                    if (fator >= 0) break;
                    System.out.println("ERRO: Valor inválido.");
                } catch (NumberFormatException e) {
                    System.out.println("ERRO: Digite um número válido.");
                }
            }
            novoAtivo = new Stock(nome, ticker, preco, qualificado, bolsa, setor, fator);

        } else if (escolha.equals("4")) {
            System.out.println("Algoritmo de consenso (ou 'V' para voltar): ");
            String algoritmo = scanner.nextLine();
            if (algoritmo.equalsIgnoreCase("V")) return;

            double quantidade;
            while (true) {
                System.out.println("Quantidade máxima de circulação (ou 'V' para voltar): ");
                String entrada = scanner.nextLine();
                if (entrada.equalsIgnoreCase("V")) return;

                try {
                    quantidade = Double.parseDouble(entrada.replace(",", "."));
                    if (quantidade >= 0) break;
                    System.out.println("ERRO: Valor inválido.");
                } catch (NumberFormatException e) {
                    System.out.println("ERRO: Digite um número válido.");
                }
            }
            double fator;
            while (true) {
                System.out.println("Fator de conversão (ou 'V' para voltar): ");
                String entrada = scanner.nextLine();
                if (entrada.equalsIgnoreCase("V")) return;

                try {
                    fator = Double.parseDouble(entrada.replace(",", "."));
                    if (fator >= 0) break;
                    System.out.println("ERRO: Valor inválido.");
                } catch (NumberFormatException e) {
                    System.out.println("ERRO: Digite um número válido.");
                }
            }
            novoAtivo = new Criptomoeda(nome, ticker, preco, qualificado, algoritmo, quantidade, fator);

        } else if (escolha.equals("5")) {
            System.out.println("Tipo de rendimento (ou 'V' para voltar): ");
            String tipo = scanner.nextLine();
            if (tipo.equalsIgnoreCase("V")) return;

            System.out.println("Data de vencimento (ou 'V' para voltar): ");
            String dataVencimento = scanner.nextLine();
            if (dataVencimento.equalsIgnoreCase("V")) return;

            novoAtivo = new Tesouro(nome, ticker, preco, qualificado, tipo, dataVencimento);
        }
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
                System.out.println("Digite o caminho do arquivo (ex: acoes.csv) ou 'V' para voltar: ");
                caminho = scanner.nextLine();

                if (caminho.equalsIgnoreCase("V")) {
                    System.out.println("Voltando...\n");
                    return;
                }
                File file = new File(caminho);
                if (!file.exists() || !file.isFile()) {
                    System.out.println("ERRO: Arquivo não encontrado.");
                    continue;
                }
                if (caminho.endsWith(".csv")) break;
                System.out.println("ERRO: O caminho do arquivo precisa terminar com '.csv'.");
            } catch (IllegalArgumentException e) {
                System.out.println("ERRO: Digite um caminho válido para o arquivo.");
            }
        }
        String tipoDoArquivo;
        while (true) {
            System.out.println("Digite o tipo de ativos presente neste arquivo (ou 'V' para voltar): ");
            System.out.println("1-Ação, 2-FII, 3-Stock, 4-Cripto, 5-Tesouro");
            tipoDoArquivo = scanner.nextLine();

            if (tipoDoArquivo.equalsIgnoreCase("V")) {
                System.out.println("Voltando...\n");
                return;
            }
            if (tipoDoArquivo.matches("[1-5]")) break;
            System.out.println("ERRO: Opção inválida.");
        }
        List<String[]> linhas = CSVReader.lerCSV(caminho);

        if (linhas.isEmpty()) {
            System.out.println("ERRO: O arquivo está vazio.");
            return;
        }
        String[] primeiraLinha = linhas.get(0);
        boolean valido = false;

        if (tipoDoArquivo.equals("1") && primeiraLinha.length >= 4) valido = true;
        else if (tipoDoArquivo.equals("2") && primeiraLinha.length >= 7) valido = true;
        else if (tipoDoArquivo.equals("3") && primeiraLinha.length >= 7) valido = true;
        else if (tipoDoArquivo.equals("4") && primeiraLinha.length >= 7) valido = true;
        else if (tipoDoArquivo.equals("5") && primeiraLinha.length >= 6) valido = true;

        if (!valido) {
            System.out.println("ERRO: O tipo de ativo selecionado não corresponde com o tipo presente no arquivo.");
            return;
        }
        for (String[] col : linhas) {
            try {
                Ativo novoAtivo = null;

                if (tipoDoArquivo.equals("1")) {
                    novoAtivo = new Acao(col[0], col[1],
                            Double.parseDouble(col[2]),
                            Boolean.parseBoolean(col[3]));
                } else if (tipoDoArquivo.equals("2")) {
                    novoAtivo = new FII(col[0], col[1],
                            Double.parseDouble(col[2]),
                            Boolean.parseBoolean(col[3]),
                            col[4],
                            Double.parseDouble(col[5]),
                            Double.parseDouble(col[6]));
                } else if (tipoDoArquivo.equals("3")) {
                    novoAtivo = new Stock(col[0], col[1],
                            Double.parseDouble(col[2]),
                            Boolean.parseBoolean(col[3]),
                            col[4], col[5],
                            Double.parseDouble(col[6]));
                } else if (tipoDoArquivo.equals("4")) {
                    novoAtivo = new Criptomoeda(col[0], col[1],
                            Double.parseDouble(col[2]),
                            Boolean.parseBoolean(col[3]),
                            col[4],
                            Double.parseDouble(col[5]),
                            Double.parseDouble(col[6]));
                } else if (tipoDoArquivo.equals("5")) {
                    novoAtivo = new Tesouro(col[0], col[1],
                            Double.parseDouble(col[2]),
                            Boolean.parseBoolean(col[3]),
                            col[4], col[5]);
                }
                if (novoAtivo != null) {
                    bancoAtivos.put(novoAtivo.getTicker().toUpperCase(), novoAtivo);
                }
            } catch (Exception e) {
                // ignora linha inválida e continua
            }
        }
        System.out.println("Cadastro em lote concluído com sucesso.");
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
                continue;
            }
        }
    }


    public static void editarAtivo () {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n=== Exibindo todos os ativos com visualização interativa ===");
        exibirAtivosDoBanco();

        System.out.println("Digite o ticker (ID) do ativo que deseja editar ou 'S' para sair: ");
        String tickerEditar = scanner.nextLine().trim().toUpperCase();

        if (tickerEditar.equals("S")) {
            System.out.println("Voltando...\n");
            return;
        }
        br.ufjf.dcc.model.ativos.Ativo ativo = bancoAtivos.get(tickerEditar);

        if (ativo != null) {
            String escolha = "";

            while (!escolha.equalsIgnoreCase("S")) {
                System.out.println("\n--- MODO DE EDIÇÃO ---");
                System.out.println(
                        "DADOS: " + ativo.getNome() + " | " +
                                ativo.getPrecoAtual() + " | " +
                                ativo.getTicker() + " | " +
                                ativo.isQualificado()
                );
                System.out.println("1. Nome, 2. Ticker, 3. Preço, 4. Qualificação");

                String menuExtra = ativo.getMenuEspecifico();
                if (!menuExtra.isEmpty()) {
                    System.out.println(menuExtra);
                }
                System.out.println("S. Sair");

                escolha = scanner.nextLine();
                if (escolha.equals("1") || escolha.equals("2") || escolha.equals("3") || escolha.equals("4")) {
                    ativo.editarCamposComuns(escolha, scanner);

                } else if (ativo.isOpcaoEspecificaValida(escolha)) {
                    ativo.editarCamposEspecificos(escolha, scanner);

                } else if (!escolha.equalsIgnoreCase("S")) {
                    System.out.println("Opção inválida!");
                }
            }
        } else {
            System.out.println("Ativo não encontrado!");
        }
    }


    public static void excluirAtivo () {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\n=== Exibindo todos os ativos com visualização interativa ===");
        exibirAtivosDoBanco();

        System.out.println("Digite o ticker (ID) do ativo que deseja excluir ou 'S' para sair: ");
        String tickerExcluir = scanner.nextLine().trim().toUpperCase();

        if (tickerExcluir.equals("S")) {
            System.out.println("Voltando...\n");
            return;
        }
        br.ufjf.dcc.model.ativos.Ativo ativo = bancoAtivos.get(tickerExcluir);

        if (ativo == null) {
            System.out.println("Ativo não encontrado!");
            return;
        }
        while (true) {
            System.out.print(
                    "Tem certeza que deseja excluir " +
                            ativo.getTicker() + " | " + ativo.getNome() + "? (S/N) "
            );
            String escolha = scanner.nextLine().trim().toUpperCase();

            if (escolha.equals("S")) {
                bancoAtivos.remove(tickerExcluir);
                System.out.println("Excluído com sucesso!");
                return;
            } else if (escolha.equals("N")) {
                System.out.println("Exclusão cancelada.\nVoltando...");
                return;
            } else {
                System.out.println("ERRO: Entrada inválida! Digite 'S' para SIM ou 'N' para NÃO.");
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
            } else System.out.println("Digite uma opção válida");
        }
    }
}

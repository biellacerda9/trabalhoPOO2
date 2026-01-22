package br.ufjf.dcc.app;

import br.ufjf.dcc.io.CSVReader;
import br.ufjf.dcc.model.Endereco;
import br.ufjf.dcc.model.PerfilInvestimento;
import br.ufjf.dcc.model.ativos.*;

import java.util.List;
import java.util.Scanner;

import static br.ufjf.dcc.data.BancoAtivos.bancoAtivos;

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
        System.out.println("Patrimônio TOTAL (em real): ");
        double patrimonio = scanner.nextDouble();
        scanner.nextLine();

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
        int numero = scanner.nextInt();
        scanner.nextLine();

        Endereco endereco = new Endereco(rua,numero,bairro,cidade,estado,cep);

        System.out.println("1- Pessoa Física | 2- Pessoa Institucional:");
        String escolha = scanner.nextLine();
        br.ufjf.dcc.model.Investidor novoInv = null;

        if (escolha.equals("1")) {
            System.out.println("CPF: ");
            String cpf = scanner.nextLine();
            System.out.println("Perfil de investimento (Conservador, Moderado ou Arrojado): ");
            String perfilString =  scanner.nextLine();
            PerfilInvestimento perfil;

            if (perfilString.equalsIgnoreCase("conservador")) {
                perfil = PerfilInvestimento.CONSERVADOR;
            } else if  (perfilString.equalsIgnoreCase("moderado")) {
                perfil = PerfilInvestimento.MODERADO;
            } else {
                perfil = PerfilInvestimento.ARROJADO;
            }
            // arrumar aqui depois de arrumar a carteira
            // novoInv = new PessoaFisica(nome, cpf, telefone, dataNascimento, endereco, patrimonio, carteira, perfil);
        } else  if (escolha.equals("2")) {
            System.out.println("CNPJ: ");
            String cnpj = scanner.nextLine();
            // arrumar aqui depois de arrumar a carteira
            //novoInv = new PessoaInstitucional();
        }
    }

//estagio ainda inicial, precisa de ajustes e finalizar implementações
    public static void cadastrarInvestidorEmLote () {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o caminho do arquivo (ex: investidores.csv): ");
        String caminho = scanner.nextLine();

        System.out.println("Digite o tipo de investidores presente neste arquivo: ");
        System.out.println("1- Pessoa Física, 2- Pessoa Institucional");
        String tipoDoArquivo = scanner.nextLine();

        List<String[]> linhas = CSVReader.lerCSV(caminho);

        for  (String[] col : linhas) {
            br.ufjf.dcc.model.Investidor novoInvestidor = null;

            br.ufjf.dcc.model.Carteira carteira = new br.ufjf.dcc.model.Carteira(null);

            if (tipoDoArquivo.equals("1")) {

                String textoPerfil = col[11];
                PerfilInvestimento perfil;

                if (textoPerfil.equalsIgnoreCase("conservador")) {
                    perfil = PerfilInvestimento.CONSERVADOR;
                } else if (textoPerfil.equalsIgnoreCase("moderado")) {
                    perfil = PerfilInvestimento.MODERADO;
                } else {
                    perfil = PerfilInvestimento.ARROJADO;
                }
            }
        }
    }
}

package br.ufjf.dcc.app;

import br.ufjf.dcc.model.Endereco;
import br.ufjf.dcc.model.PerfilInvestimento;
import java.util.Scanner;

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
}

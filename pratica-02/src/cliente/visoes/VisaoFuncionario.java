package cliente.visoes;

import servidor.DigitalBank;
import utils.Conta;
import utils.TipoConta;
import utils.TipoInvestimento;

import java.rmi.RemoteException;
import java.util.Scanner;

public class VisaoFuncionario {
    static Scanner teclado = new Scanner(System.in);
    static String token;

    public void iniciar(DigitalBank stubCliente, String tokenRecebido) throws RemoteException {
        token = tokenRecebido;

        while (true) {
            System.out.println(" ---------------------------------------------");
            System.out.println("|  [0]  - SAIR DESSA CONTA                    |");
            System.out.println("|  [1]  - CRIAR UMA CONTA                     |");
            System.out.println("|  [2]  - ALTERAR UMA CONTA                   |");
            System.out.println("|  [3]  - BUSCAR UMA CONTA                    |");
            System.out.println("|  [4]  - LISTAR CONTAS                       |");
            System.out.println("|  [5]  - REMOVER UMA CONTA                   |");
            System.out.println("|  [6]  - SACAR UM VALOR DA SUA CONTA         |");
            System.out.println("|  [7]  - DEPOSITAR UM VALOR NA SUA CONTA     |");
            System.out.println("|  [8]  - VER O SALDO DA SUA CONTA            |");
            System.out.println("|  [9]  - TRANSFERÊNCIA DE VALOR              |");
            System.out.println("|  [10] - INVESTIMENTO NA POUPANÇA            |");
            System.out.println("|  [11] - INVESTIMENTO NA RENDA FIXA          |");
            System.out.println(" ---------------------------------------------");
            System.out.print("  DIGITE O NÚMERO DA AÇÃO QUE VOCÊ DESEJA: ");

            switch (teclado.nextInt()) {
                case 0:
                    teclado = new Scanner(System.in);
                    System.out.println();
                    return;
                case 1:
                    teclado = new Scanner(System.in);
                    criarConta(stubCliente);
                    break;
                case 2:
                    teclado = new Scanner(System.in);
                    alterarConta(stubCliente);
                    break;
                case 3:
                    teclado = new Scanner(System.in);
                    buscarConta(stubCliente);
                    break;
                case 4:
                    teclado = new Scanner(System.in);
                    listarContas(stubCliente);
                    break;
                case 5:
                    teclado = new Scanner(System.in);
                    removerConta(stubCliente);
                    break;
                case 6:
                    teclado = new Scanner(System.in);
                    sacar(stubCliente);
                    break;
                case 7:
                    teclado = new Scanner(System.in);
                    depositar(stubCliente);
                    break;
                case 8:
                    teclado = new Scanner(System.in);
                    saldo(stubCliente);
                    break;
                case 9:
                    teclado = new Scanner(System.in);
                    transferencia(stubCliente);
                    break;
                case 10:
                    teclado = new Scanner(System.in);
                    investimentoPoupanca(stubCliente);
                    break;
                case 11:
                    teclado = new Scanner(System.in);
                    investimentoRendaFixa(stubCliente);
                    break;
                default:
                    System.out.println("Comando inválido!");
            }
        }
    }

    public void criarConta(DigitalBank stubCliente) throws RemoteException {
        Conta conta = new Conta();

        System.out.println("CRIAÇÃO DE NOVA CONTA:");
        System.out.print("Digite o tipo de conta (F)uncionário ou (U)suário: ");
        String tipoConta = teclado.nextLine();
        if (tipoConta.equals("f") || tipoConta.equals("F")) conta.setTipoConta(TipoConta.FUNCIONARIO);
        else if (tipoConta.equals("u") || tipoConta.equals("U")) conta.setTipoConta(TipoConta.USUARIO);
        else {
            System.out.println("Tipo de conta inválida!");
            return;
        }
        System.out.print("Digite um login: ");
        conta.setLogin(teclado.nextLine());
        System.out.print("Digite uma senha: ");
        conta.setSenha(teclado.nextLine());
        System.out.print("Digite um cpf: ");
        conta.setCpf(teclado.nextLine());
        System.out.print("Digite o nome: ");
        conta.setNome(teclado.nextLine());
        System.out.print("Digite o endereço: ");
        conta.setEndereco(teclado.nextLine());
        System.out.print("Digite a data de nascimento: ");
        conta.setNascimento(teclado.nextLine());
        System.out.print("Digite o telefone: ");
        conta.setTelefone(teclado.nextLine());

        if (stubCliente.criarConta(conta, token) == 1) {
            System.out.println("Conta Criada!");
        } else {
            System.out.println("Erro ao Criar Conta!");
        }

        atrasoRelogio();
    }

    public void alterarConta(DigitalBank stubCliente) throws RemoteException {

        System.out.println("ALTERAÇÃO DE UMA CONTA:");
        System.out.print("Digite o número da conta que deseja alterar: ");
        String numeroConta = teclado.nextLine();

        Conta conta = stubCliente.buscarConta(numeroConta);
        System.out.print("Digite o novo cpf: ");
        conta.setCpf(teclado.nextLine());
        System.out.print("Digite o novo nome: ");
        conta.setNome(teclado.nextLine());
        System.out.print("Digite o novo endereço: ");
        conta.setEndereco(teclado.nextLine());
        System.out.print("Digite a nova data de nascimento: ");
        conta.setNascimento(teclado.nextLine());
        System.out.print("Digite o novo telefone: ");
        conta.setTelefone(teclado.nextLine());

        if (stubCliente.alterarConta(numeroConta, conta, token) == 1) {
            System.out.println("Conta Alterada!");
        } else {
            System.out.println("Erro ao Alterar Conta!");
        }

        atrasoRelogio();
    }

    public void buscarConta(DigitalBank stubCliente) throws RemoteException {

        System.out.println("BUSCA DE CONTA:");
        System.out.print("Digite o número da conta que deseja buscar: ");
        String numeroConta = teclado.nextLine();

        Conta conta = stubCliente.buscarConta(numeroConta);

        if (conta != null) {
            System.out.println("Login: " + conta.getLogin());
            System.out.print("Acesso da conta: ");
            if (conta.getTipoConta().equals(TipoConta.FUNCIONARIO)) System.out.println("Funcionário");
            else if (conta.getTipoConta().equals(TipoConta.USUARIO)) System.out.println("Usuário");
            System.out.print("Tipo da conta: ");
            if (conta.getTipoInvestimento().equals(TipoInvestimento.POUPANCA)) System.out.println("Poupança");
            else if (conta.getTipoInvestimento().equals(TipoInvestimento.RENDA_FIXA)) System.out.println("Renda Fixa");
            System.out.println("Número da conta: " + conta.getNumero());
            System.out.println("Saldo da conta: " + conta.getValor());
            System.out.println("CPF: " + conta.getCpf());
            System.out.println("Dono da conta: " + conta.getNome());
            System.out.println("Endereço : " + conta.getEndereco());
            System.out.println("Data de nascimento: " + conta.getNascimento());
            System.out.println("Telefone: " + conta.getTelefone());
            System.out.println();
        } else {
            System.out.println("Conta não encontrada!");
        }
        atrasoRelogio();
    }

    public void listarContas(DigitalBank stubCliente) throws RemoteException {

        System.out.println("LISTA DE CONTAS EXISTENTES:");

        for (Conta conta : stubCliente.listarContas(token)) {
            System.out.println("Número da conta: " + conta.getNumero());
            System.out.println("Dono da conta: " + conta.getNome());
            System.out.println();
        }

        atrasoRelogio();
    }

    public void removerConta(DigitalBank stubCliente) throws RemoteException {

        System.out.println("REMOÇÃO DE CONTA:");
        System.out.print("Digite o número da conta que deseja remover: ");
        String numeroConta = teclado.nextLine();

        if (stubCliente.removerConta(numeroConta) == 1) {
            System.out.println("Conta Removida!");
        } else {
            System.out.println("Conta não encontrada!");
        }

        atrasoRelogio();
    }

    public void sacar(DigitalBank stubCliente) throws RemoteException {

        System.out.println("SAQUE:");
        System.out.print("Digite o valor vai sacar: ");
        double valor = teclado.nextDouble();

        valor = stubCliente.sacar(valor, token);

        if (valor == -1) {
            System.out.println("Erro ao sacar!");
        } else {
            System.out.println("Saque Realizado!\nAgora você tem: " + valor);
        }

        atrasoRelogio();
    }

    public void depositar(DigitalBank stubCliente) throws RemoteException {

        System.out.println("DEPOSITO:");
        System.out.print("Digite o valor vai depositar: ");
        double valor = teclado.nextDouble();

        valor = stubCliente.depositar(valor, token);
        if (valor > 0) {
            System.out.println("Depósito Realizado!\nAgora você tem: " + valor);
        } else {
            System.out.println("Conta não encontrada!");
        }

        atrasoRelogio();
    }

    public void saldo(DigitalBank stubCliente) throws RemoteException {

        System.out.println("SALDO:");
        System.out.println("Seu saldo é: " + stubCliente.saldo(token));

        atrasoRelogio();
    }

    public void transferencia(DigitalBank stubCliente) throws RemoteException {

        System.out.println("TRANSFERÊNCIA:");
        System.out.print("Digite o número da conta que vai receber o valor: ");
        String numero = teclado.nextLine();
        System.out.print("Digite o valor vai transferir: ");
        double valor = teclado.nextDouble();

        if (stubCliente.transferir(numero, valor, token) == 1) {
            System.out.println("Transferência Realizada!");
        } else {
            System.out.println("Erro na Transferência!");
        }

        atrasoRelogio();
    }

    public void investimentoPoupanca(DigitalBank stubCliente) throws RemoteException {

        System.out.println("INVESTIMENTO NA POUPANÇA:");
        System.out.println(stubCliente.investirPoupanca(token));

        atrasoRelogio();
    }

    public void investimentoRendaFixa(DigitalBank stubCliente) throws RemoteException {

        System.out.println("INVESTIMENTO NA RENDA FIXA:");
        System.out.println(stubCliente.investirRendaFixa(token));

        atrasoRelogio();
    }

    private void atrasoRelogio() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

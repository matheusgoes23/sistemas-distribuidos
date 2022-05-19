package cliente.visoes;

import servidor.DigitalBank;
import utils.Conta;
import utils.TipoConta;
import utils.TipoInvestimento;

import java.rmi.RemoteException;
import java.util.Scanner;

public class VisaoUsuario {
    static Scanner teclado = new Scanner(System.in);
    static String token;

    public void iniciar(DigitalBank stubCliente, String tokenRecebido) throws RemoteException {
        token = tokenRecebido;

        while (true) {
            System.out.println(" --------------------------------------------");
            System.out.println("|  [0] - SAIR DESSA TELA                     |");
            System.out.println("|  [1] - ALTERAR SUA CONTA                   |");
            System.out.println("|  [2] - LISTAR SUA CONTA                    |");
            System.out.println("|  [3] - REMOVER SUA CONTA                   |");
            System.out.println("|  [4] - SACAR UM VALOR DA SUA CONTA         |");
            System.out.println("|  [5] - DEPOSITAR UM VALOR NA SUA CONTA     |");
            System.out.println("|  [6] - VER O SALDO DA SUA CONTA            |");
            System.out.println("|  [7] - TRANSFERÊNCIA DE VALOR              |");
            System.out.println("|  [8] - INVESTIMENTO NA POUPANÇA            |");
            System.out.println("|  [9] - INVESTIMENTO NA RENDA FIXA          |");
            System.out.println(" --------------------------------------------");
            System.out.print("  DIGITE O NÚMERO DA AÇÃO QUE VOCÊ DESEJA: ");

            switch (teclado.nextInt()) {
                case 0:
                    return;
                case 1:
                    teclado = new Scanner(System.in);
                    alterarMinhaConta(stubCliente);
                    break;
                case 2:
                    teclado = new Scanner(System.in);
                    listarMinhaConta(stubCliente);
                    break;
                case 3:
                    teclado = new Scanner(System.in);
                    removerMinhaConta(stubCliente);
                    break;
                case 4:
                    teclado = new Scanner(System.in);
                    sacar(stubCliente);
                    break;
                case 5:
                    teclado = new Scanner(System.in);
                    depositar(stubCliente);
                    break;
                case 6:
                    teclado = new Scanner(System.in);
                    saldo(stubCliente);
                    break;
                case 7:
                    teclado = new Scanner(System.in);
                    transferencia(stubCliente);
                    break;
                case 8:
                    teclado = new Scanner(System.in);
                    investimentoPoupanca(stubCliente);
                    break;
                case 9:
                    teclado = new Scanner(System.in);
                    investimentoRendaFixa(stubCliente);
                    break;
                default:
                    System.out.println("Comando inválido!");
            }
        }
    }

    public void alterarMinhaConta(DigitalBank stubCliente) throws RemoteException {

        System.out.println("ALTERAÇÃO DE DADOS DA CONTA:");
        Conta conta = stubCliente.buscarMinhaConta(token);

        System.out.print("Digite o novo login: ");
        conta.setLogin(teclado.nextLine());
        System.out.print("Digite a nova senha: ");
        conta.setSenha(teclado.nextLine());
        System.out.print("Digite o novo endereço: ");
        conta.setEndereco(teclado.nextLine());
        System.out.print("Digite a nova data de nascimento: ");
        conta.setNascimento(teclado.nextLine());
        System.out.print("Digite o novo telefone: ");
        conta.setTelefone(teclado.nextLine());

        if (stubCliente.alterarConta(conta.getNumero(), conta, token) == 1) {
            System.out.println("Conta Alterada!");
        } else {
            System.out.println("Erro ao Alterar Conta!");
        }

        atrasoRelogio();
    }

    public void listarMinhaConta(DigitalBank stubCliente) throws RemoteException {

        System.out.println("MEUS DADOS:");
        Conta conta = stubCliente.buscarMinhaConta(token);

        if (conta != null) {
            System.out.println("Login: " + conta.getLogin());
            System.out.println("Senha: " + conta.getSenha());
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

    public void removerMinhaConta(DigitalBank stubCliente) throws RemoteException {

        System.out.println("REMOÇÃO DE CONTA:");
        System.out.print("Deseja solicitar a remoção de sua conta? (S/N): ");
        String resposta = teclado.nextLine();

        if (resposta.equals("s") || resposta.equals("S")) {
            Conta conta = stubCliente.buscarMinhaConta(token);

            if (stubCliente.removerConta(conta.getNumero(), token) == 1) {
                System.out.println("Conta Removida!");
            } else {
                System.out.println("Conta não encontrada!");
            }

            atrasoRelogio();
        }
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

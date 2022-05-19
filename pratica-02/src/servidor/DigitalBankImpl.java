package servidor;

import servidor.servicos.ServicosFuncionario;
import utils.*;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DigitalBankImpl implements DigitalBank {

    static ServicosFuncionario servicosFuncionario = new ServicosFuncionario();

    @Override
    public String autenticar(String login, String senha) throws RemoteException {

        Conta conta = Dados.obterContaPeloLogin(login);

        if (conta != null && conta.getSenha().equals(senha)) {
            return Token.gerarToken(conta);
        }

        return null;
    }

    @Override
    public int obterTipoConta(String token) throws RemoteException {
        TipoConta tipoConta = Token.obterTipoConta(token);

        if (tipoConta != null) {
            if (tipoConta.equals(TipoConta.FUNCIONARIO)) {
                return 0;
            } else if (tipoConta.equals(TipoConta.USUARIO)) {
                return 1;
            }
        }

        return -1;
    }

    @Override
    public int criarConta(Conta conta, String token) {
        if (Objects.equals(Token.obterTipoConta(token), TipoConta.FUNCIONARIO)) {
            servicosFuncionario.criarConta(conta);
            System.out.println("Conta Criada!");
            return 1;
        } else {
            System.out.println("Acesso Negado!");
            return 0;
        }
    }

    @Override
    public int alterarConta(String numero, Conta conta, String token) {

        if (conta != null) {
            if (conta.getTipoConta().equals(TipoConta.USUARIO)) {
                List<Conta> contas = new ArrayList<>();
                if (conta.getNumero().equals(numero)) {
                    for (Conta contaBusca : servicosFuncionario.listarContas()) {
                        if (contaBusca.getNumero().equals(Token.obterNumeroConta(token)))
                            contas.add(contaBusca);
                    }
                } else {
                    contas = listarContas(token);
                }

                for (int i = 0; i < contas.size(); i++) {
                    if (contas.get(i).getNumero().equals(conta.getNumero())) {
                        contas.get(i).setLogin(conta.getLogin());
                        contas.get(i).setSenha(conta.getSenha());
                        contas.get(i).setCpf(conta.getCpf());
                        contas.get(i).setNome(conta.getNome());
                        contas.get(i).setEndereco(conta.getEndereco());
                        contas.get(i).setNascimento(conta.getNascimento());
                        contas.get(i).setTelefone(conta.getTelefone());

                        Dados.alterarContas(contas);
                        return 1;
                    }
                }
            }
        }
        System.out.println("Acesso Negado!");
        return 0;
    }

    @Override
    public Conta buscarConta(String numero, String token) {
        for (Conta conta : Dados.obterContas()) {
            if (conta.getNumero().equals(numero)) return conta;
        }
        return null;
    }

    @Override
    public Conta buscarMinhaConta(String token) throws RemoteException {
        for (Conta conta : servicosFuncionario.listarContas()) {
            if (conta.getNumero().equals(Token.obterNumeroConta(token)))
                return conta;
        }
        return null;
    }

    @Override
    public List<Conta> listarContas(String token) {
        if (Objects.equals(Token.obterTipoConta(token), TipoConta.FUNCIONARIO)) {
            return servicosFuncionario.listarContas();
        } else {
            System.out.println("Acesso Negado!");
            return null;
        }
    }

    @Override
    public int removerConta(String numero, String token) {
        Conta conta = buscarConta(numero, token);

        if (conta != null)
            if (conta.getTipoConta().equals(TipoConta.USUARIO)) {
                List<Conta> contas = Dados.obterContas();
                contas.remove(conta);
                Dados.alterarContas(contas);
                return 1;
            }
        System.out.println("Acesso Negado!");
        return 0;

    }

    @Override
    public double sacar(double valor, String token) {
        if (Objects.equals(Token.obterTipoConta(token), TipoConta.FUNCIONARIO)) {
            return servicosFuncionario.sacar(Token.obterNumeroConta(token), valor);
        } else if (Objects.equals(Token.obterTipoConta(token), TipoConta.USUARIO)) {
            return servicosFuncionario.sacar(Token.obterNumeroConta(token), valor);
        } else {
            return -1;
        }
    }

    @Override
    public double depositar(double valor, String token) {
        if (Objects.equals(Token.obterTipoConta(token), TipoConta.FUNCIONARIO)) {
            return servicosFuncionario.depositar(Token.obterNumeroConta(token), valor);
        } else if (Objects.equals(Token.obterTipoConta(token), TipoConta.USUARIO)) {
            return servicosFuncionario.depositar(Token.obterNumeroConta(token), valor);
        } else {
            return 0;
        }
    }

    @Override
    public double saldo(String token) {
        return buscarConta(Token.obterNumeroConta(token), token).getValor();
    }

    @Override
    public int transferir(String numeroReceber, double valor, String token) {
        List<Conta> contas = Dados.obterContas();
        Conta contaEnviar = buscarConta(Token.obterNumeroConta(token), token);
        Conta contaReceber = buscarConta(numeroReceber, token);

        if (contaEnviar == null || contaReceber == null)
            return 0;

        contaEnviar.setValor(contaEnviar.getValor() - valor);
        contaReceber.setValor(contaReceber.getValor() + valor);

        int sucesso = 0;
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getNumero().equals(contaEnviar.getNumero())) {
                contas.get(i).setValor(contaEnviar.getValor());
                sucesso++;
            }
            if (contas.get(i).getNumero().equals(contaReceber.getNumero())) {
                contas.get(i).setValor(contaReceber.getValor());
                sucesso++;
            }
        }
        if (sucesso == 2) {
            Dados.alterarContas(contas);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String investirPoupanca(String token) throws RemoteException {
        Conta conta;
        if (Objects.equals(Token.obterTipoConta(token), TipoConta.FUNCIONARIO)) {
            conta = buscarConta(Token.obterNumeroConta(token), token);
        } else if (Objects.equals(Token.obterTipoConta(token), TipoConta.USUARIO)) {
            conta = buscarMinhaConta(token);
        } else {
            return null;
        }

        if (conta.getTipoInvestimento().equals(TipoInvestimento.RENDA_FIXA)) {
            List<Conta> contas = Dados.obterContas();
            for (int i = 0; i < contas.size(); i++) {
                if (contas.get(i).getNumero().equals(conta.getNumero())) {
                    contas.get(i).setTipoInvestimento(TipoInvestimento.POUPANCA);
                }
            }
            Dados.alterarContas(contas);
        }

        return "Valor aplicado: " + conta.getValor()
                + "\nEm 3 meses renderá: "
                + new DecimalFormat("###.00").format(rendimentoPoupanca(conta.getValor(), 3))
                + "\nEm 6 meses renderá: "
                + new DecimalFormat("###.00").format(rendimentoPoupanca(conta.getValor(), 6))
                + "\nEm 12 meses renderá: "
                + new DecimalFormat("###.00").format(rendimentoPoupanca(conta.getValor(), 12));
    }

    @Override
    public String investirRendaFixa(String token) throws RemoteException {
        Conta conta;
        if (Objects.equals(Token.obterTipoConta(token), TipoConta.FUNCIONARIO)) {
            conta = buscarConta(Token.obterNumeroConta(token), token);
        } else if (Objects.equals(Token.obterTipoConta(token), TipoConta.USUARIO)) {
            conta = buscarMinhaConta(token);
        } else {
            return null;
        }

        if (conta.getTipoInvestimento().equals(TipoInvestimento.POUPANCA)) {
            List<Conta> contas = Dados.obterContas();
            for (int i = 0; i < contas.size(); i++) {
                if (contas.get(i).getNumero().equals(conta.getNumero())) {
                    contas.get(i).setTipoInvestimento(TipoInvestimento.RENDA_FIXA);
                }
            }
            Dados.alterarContas(contas);
        }

        return "Valor aplicado: " + conta.getValor()
                + "\nEm 3 meses renderá: "
                + new DecimalFormat("###.00").format(rendimentoRendaFixa(conta.getValor(), 3))
                + "\nEm 6 meses renderá: "
                + new DecimalFormat("###.00").format(rendimentoRendaFixa(conta.getValor(), 6))
                + "\nEm 12 meses renderá: "
                + new DecimalFormat("###.00").format(rendimentoRendaFixa(conta.getValor(), 12));
    }

    static double rendimentoPoupanca(double valor, int meses) {
        for (int i = 0; i < meses; i++) {
            valor = valor + (valor * 0.005);
        }
        return valor;
    }

    static double rendimentoRendaFixa(double valor, int meses) {
        for (int i = 0; i < meses; i++) {
            valor = valor + (valor * 0.015);
        }
        return valor;
    }
}

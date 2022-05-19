package utils;

import java.util.ArrayList;
import java.util.List;

public abstract class Dados {
    private static List<Conta> contas = new ArrayList<>();

    public static List<Conta> obterContas() {
        return contas;
    }

    public static void alterarContas(List<Conta> contas) {
        Dados.contas = contas;
    }

    public static Conta obterContaPeloLogin(String login) {

        for (Conta conta : contas) {
            if (conta.getLogin().equals(login)) {
                return conta;
            }
        }
        return null;
    }

    public static void gerarFuncionarioInicial() {
        Conta conta = new Conta();
        conta.setLogin("admin");
        conta.setSenha("123");
        conta.setNome("admin");
        conta.setValor(100.0);
        conta.setTipoConta(TipoConta.FUNCIONARIO);
        contas.add(conta);

        System.out.println("Funcionário inicial [ login: admin, senha: 123 ]");
    }
}

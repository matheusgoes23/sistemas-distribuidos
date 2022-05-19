package servidor.servicos;

import utils.Conta;
import utils.Dados;

import java.util.List;

public class ServicosFuncionario {

    public void criarConta(Conta conta) {
        List<Conta> contas = Dados.obterContas();
        contas.add(conta);
        Dados.alterarContas(contas);
    }

    public List<Conta> listarContas() {
        return Dados.obterContas();
    }

    public double sacar(String numero, double valor) {
        List<Conta> contas = Dados.obterContas();

        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getNumero().equals(numero)) {

                if (contas.get(i).getValor() < valor) return 0;

                contas.get(i).setValor(contas.get(i).getValor() - valor);
                Dados.alterarContas(contas);
                return contas.get(i).getValor();
            }
        }
        return 0;
    }

    public double depositar(String numero, double valor) {
        List<Conta> contas = Dados.obterContas();

        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getNumero().equals(numero)) {
                contas.get(i).setValor(contas.get(i).getValor() + valor);
                Dados.alterarContas(contas);
                return contas.get(i).getValor();
            }
        }
        return 0;
    }
}

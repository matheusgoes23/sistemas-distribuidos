package questao2.tcp;

import questao2.Operacao;

public class TipoOperacao {
    private static Operacao operacao;

    public static Operacao getOperacao() {
        return operacao;
    }

    public static void setOperacao(Operacao operacao) {
        TipoOperacao.operacao = operacao;
    }
}

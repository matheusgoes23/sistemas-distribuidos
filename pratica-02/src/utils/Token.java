package utils;

public abstract class Token {
    public static String gerarToken(Conta conta) {
        return conta.getNumero() + "." + conta.getTipoConta().name();
    }

    public static TipoConta obterTipoConta(String token) {
        if (token.contains(TipoConta.FUNCIONARIO.name())) {
            return TipoConta.FUNCIONARIO;
        }
        if (token.contains(TipoConta.USUARIO.name())) {
            return TipoConta.USUARIO;
        }
        return null;
    }

    public static String obterNumeroConta(String token) {
        String numero = "";

        for (int i = 0; i < token.length(); i++) {
            if (token.charAt(i) == '.')
                return numero;

            numero += token.charAt(i);
        }

        return numero;
    }
}

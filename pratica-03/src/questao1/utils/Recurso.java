package questao1.utils;

public abstract class Recurso {
    private static boolean disponivel = true;

    public static boolean estaDisponivel() {
        return disponivel;
    }

    public static void tornarDisponivel() {
        disponivel = true;
    }

    public static void tornarIndisponivel() {
        disponivel = false;
    }
}

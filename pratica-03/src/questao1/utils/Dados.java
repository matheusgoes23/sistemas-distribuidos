package questao1.utils;

import questao1.processo.Processo;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public abstract class Dados {
    private static List<Processo> processos = Arrays.asList(
            new Processo(1, 5001),
            new Processo(2, 5002),
            new Processo(3, 5003),
            new Processo(4, 5004));

    private static Queue<Processo> fila = new ArrayDeque<>();

    public static Processo obterProcesso(int id) {
        for (Processo processo : processos)
            if (processo.getId() == id)
                return processo;

        return null;
    }

    public static boolean filaVazia(){
        return fila.isEmpty();
    }

    public static Processo addFila(Processo processo){
        fila.add(processo);
        return fila.peek();
    }

    public static Processo tirarFila(){
        return fila.poll();
    }
}

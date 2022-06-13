package questao1;

import questao1.coordenador.Coordenador;
import questao1.processo.Processo;
import questao1.utils.Dados;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Coordenador coordenador = new Coordenador();

        Processo P1 = new Processo(Dados.obterProcesso(1));
        Processo P2 = new Processo(Dados.obterProcesso(2));
        Processo P3 = new Processo(Dados.obterProcesso(3));
        Processo P4 = new Processo(Dados.obterProcesso(4));

        Thread threadCoordenador = new Thread(coordenador);
        threadCoordenador.start();

        List<Thread> processos = new ArrayList<>();
        processos.add(new Thread(P1));
        processos.add(new Thread(P2));
        processos.add(new Thread(P3));
        processos.add(new Thread(P4));

        for (var processo : processos) {
            processo.start();
        }
    }
}

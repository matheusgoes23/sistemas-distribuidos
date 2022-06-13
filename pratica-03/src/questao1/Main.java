package questao1;

import questao1.coordenador.Coordenador;
import questao1.processo.Processo;
import questao1.utils.Dados;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Inicia processo coordenador como servidor
        Coordenador coordenador = new Coordenador();
        //Inicia os 4 processos como cliente
        Processo P1 = new Processo(Dados.obterProcesso(1));
        Processo P2 = new Processo(Dados.obterProcesso(2));
        Processo P3 = new Processo(Dados.obterProcesso(3));
        //O coordenador guarda os pedidos com seus ids e portas
        //Cada processo espera uma resposta OK
        //Se Respota de cada processo for OK acessa o recurso

        Thread threadCoordenador = new Thread(coordenador);
        threadCoordenador.start();

        List<Thread> processos = new ArrayList<>();
        processos.add(new Thread(P1));
        processos.add(new Thread(P2));
        processos.add(new Thread(P3));

        //Cada processo faz um pedido
        for (var processo : processos) {
            processo.start();
        }
    }
}

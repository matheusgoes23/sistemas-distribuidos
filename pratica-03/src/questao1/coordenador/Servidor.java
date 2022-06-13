package questao1.coordenador;

import questao1.utils.Dados;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {
    private int porta;

    public Servidor(int porta) {
        this.porta = porta;
    }

    public int getPorta() {
        return porta;
    }

    @Override
    public void run() {
        try {

            Envio envio = new Envio();
            Thread threadEnvio = new Thread(envio);
            threadEnvio.start();

            ServerSocket serverSocket = new ServerSocket(this.getPorta());
            System.out.println("Coordenador iniciou");

            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream fluxoEntrada = new DataInputStream(socket.getInputStream());

                int processoQueRequisitou = fluxoEntrada.readInt();
                System.out.println("Coordenador recebeu pedido do processo " + processoQueRequisitou);
                Dados.addFila(Dados.obterProcesso(processoQueRequisitou));

                fluxoEntrada.close();
                socket.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

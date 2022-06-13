package questao1.processo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {
    private int id;
    private int porta;

    public Servidor(int id, int porta) {
        this.id = id;
        this.porta = porta;
    }

    public int getId() {
        return id;
    }

    public int getPorta() {
        return porta;
    }

    @Override
    public void run() {
        try {

            ServerSocket serverSocket = new ServerSocket(this.getPorta());
            Socket socket = serverSocket.accept();
            DataOutputStream fluxoSaida = new DataOutputStream(socket.getOutputStream());

            fluxoSaida.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

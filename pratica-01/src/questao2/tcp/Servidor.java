package questao2.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {
    private int id;
    private int porta;
    private Integer mensagem;
    private int quantidade;

    public Servidor(int id, int porta) {
        this.id = id;
        this.porta = porta;
        this.quantidade = 1;
    }

    public Servidor(int id, int porta, int quantidade) {
        this.id = id;
        this.porta = porta;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public int getPorta() {
        return porta;
    }

    public Integer getMensagem() {
        return mensagem;
    }

    public void setMensagem(Integer mensagem) {
        this.mensagem = mensagem;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.getPorta());
            Socket socket = serverSocket.accept();
            DataInputStream fluxoEntrada = new DataInputStream(socket.getInputStream());

            this.setMensagem(fluxoEntrada.readInt());
            System.out.println("P" + this.getId() + " Recebeu: [ " + this.getMensagem() + " ]");

            fluxoEntrada.close();
            socket.close();
            serverSocket.close();

            System.out.println("P" + this.getId() + " enviou: [ " + this.getMensagem() + " ]");
            for (int i = 0; i < this.getQuantidade(); i++) {
                ServerSocket serverSocket2 = new ServerSocket(this.getPorta());
                Socket socket2 = serverSocket2.accept();

                DataOutputStream fluxoSaida = new DataOutputStream(socket2.getOutputStream());

                fluxoSaida.writeInt(this.getMensagem());

                fluxoSaida.close();
                socket2.close();
                serverSocket2.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

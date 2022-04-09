package questao1.tcp;

import questao1.Sentido;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {
    private int id;
    private int porta;
    private Integer mensagem;
    private Sentido sentido;

    public Servidor(int id, int porta, Sentido sentido) {
        this.id = id;
        this.porta = porta;
        this.sentido = sentido;
    }

    public int getId() {
        return id;
    }

    public int getPorta() {
        return porta;
    }

    public Sentido getSentido() {
        return sentido;
    }

    public Integer getMensagem() {
        return mensagem;
    }

    public void setMensagem(Integer mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public void run() {
        try {

            ServerSocket serverSocket = new ServerSocket(this.getPorta());
            System.out.println("Iniciado Servidor P" + this.getId());
            Socket socket = serverSocket.accept();
            DataOutputStream fluxoSaida = new DataOutputStream(socket.getOutputStream());

            if (this.getSentido().equals(Sentido.HORARIO)) {
                Thread.sleep(this.getId() * 1000L);
            } else {
                switch (this.getId()) {
                    case 1:
                        Thread.sleep(1000L);
                        break;
                    case 2:
                        Thread.sleep(4000L);
                        break;
                    case 3:
                        Thread.sleep(3000L);
                        break;
                    case 4:
                        Thread.sleep(2000L);
                        break;
                }
            }

            this.setMensagem(Dados.getMensagem(this.getId()));

            if (this.getMensagem() >= 0) {
                System.out.println("Mensagem enviada pelo P" + this.getId() + ": [ " + this.getMensagem() + " + " + this.getId() + " ]");
                fluxoSaida.writeInt(this.getMensagem() + this.getId());
            }

            fluxoSaida.close();
            socket.close();
            serverSocket.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

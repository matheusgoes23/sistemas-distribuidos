package questao1.processo;

import questao1.utils.Recurso;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente implements Runnable {
    private int id;
    private int porta;
    private String mensagem;
    private int coordenadorPorta;

    public Cliente(int id, int porta, int coordenadorPorta) {
        this.id = id;
        this.porta = porta;
        this.coordenadorPorta = coordenadorPorta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(this.getId() * 100L);
            System.out.println("Iniciou cliente do Processo " + this.getId());
            Socket socket = new Socket("localhost", coordenadorPorta);
            DataOutputStream fluxoSaida = new DataOutputStream(socket.getOutputStream());

            System.out.println("Processo " + this.getId() + " enviou pedido");
            fluxoSaida.writeInt(this.getId());

            ServerSocket serverSocket = new ServerSocket(this.getPorta());
            Socket socketRecebimento = serverSocket.accept();
            System.out.println("Processo " + this.getId() + " esperando permissão");
            DataInputStream fluxoEntrada = new DataInputStream(socketRecebimento.getInputStream());

            this.setMensagem(fluxoEntrada.readUTF());

            System.out.println("Processo " + this.getId() + " recebeu " + this.getMensagem());

            Recurso.tornarIndisponivel();
            Thread.sleep(3000L);
            Recurso.tornarDisponivel();

            fluxoSaida.close();
            socket.close();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
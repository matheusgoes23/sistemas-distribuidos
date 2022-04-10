package questao1.tcp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente implements Runnable {
    private int id;
    private Integer mensagem;
    private Processo antecessor;

    public Cliente(int id, Processo antecessor) {
        this.id = id;
        this.antecessor = antecessor;
    }

    public int getId() {
        return id;
    }

    public Integer getMensagem() {
        return mensagem;
    }

    public void setMensagem(Integer mensagem) {
        this.mensagem = mensagem;
    }

    public Processo getAntecessor() {
        return antecessor;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            Socket socket = new Socket("localhost", this.getAntecessor().getPorta());

            DataInputStream fluxoEntrada = new DataInputStream(socket.getInputStream());
            this.setMensagem(fluxoEntrada.readInt());
            Dados.setMensagem(this.getId(), this.getMensagem());

            System.out.println("Mensagem recebida pelo P" + this.getId() + ": [ " + this.getMensagem() + " ]");

            fluxoEntrada.close();
            socket.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}

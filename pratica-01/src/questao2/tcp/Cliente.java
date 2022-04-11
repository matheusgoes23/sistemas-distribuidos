package questao2.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cliente implements Runnable {
    private int id;
    private int portaCentral;
    private Integer mensagem;
    private boolean condicao;

    public Cliente(int id, int portaCentral, Integer mensagem, boolean condicao) {
        this.id = id;
        this.portaCentral = portaCentral;
        this.mensagem = mensagem;
        this.condicao = condicao;
    }

    public Cliente(int id, int portaCentral, boolean condicao) {
        this.id = id;
        this.portaCentral = portaCentral;
        this.condicao = condicao;
    }

    public int getId() {
        return id;
    }

    public int getPortaCentral() {
        return portaCentral;
    }

    public Integer getMensagem() {
        return mensagem;
    }

    public void setMensagem(Integer mensagem) {
        this.mensagem = mensagem;
    }

    public boolean getCondicao() {
        return condicao;
    }

    @Override
    public void run() {
        try {
            if (this.getCondicao()) {
                Thread.sleep(1000L * this.getId());
            } else {
                Thread.sleep(4000L / this.getId());
            }

            Socket socket = new Socket("localhost", this.getPortaCentral());

            if (this.getMensagem() != null) {
                DataOutputStream fluxoSaida = new DataOutputStream(socket.getOutputStream());

                System.out.println("P" + this.getId() + " enviou: [ " + this.getMensagem() + " ]");
                fluxoSaida.writeInt(this.getMensagem());

                fluxoSaida.close();
            } else {
                DataInputStream fluxoEntrada = new DataInputStream(socket.getInputStream());
                this.setMensagem(fluxoEntrada.readInt());
                System.out.println("P" + this.getId() + " recebeu: [ " + this.getMensagem() + " ]");

                fluxoEntrada.close();
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

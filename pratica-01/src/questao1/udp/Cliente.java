package questao1.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

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
            byte[] mensagemRecebimento = new byte[1024];

            DatagramSocket datagramSocket = new DatagramSocket(this.getAntecessor().getPorta());
            DatagramPacket datagramPacket = new DatagramPacket(mensagemRecebimento, mensagemRecebimento.length);

            datagramSocket.receive(datagramPacket);

            this.setMensagem(Integer.parseInt(new String(datagramPacket.getData()).trim()));
            Dados.setMensagem(this.getId(), this.getMensagem());
            System.out.println("Mensagem recebida pelo P" + this.getId() + ": [ " + this.getMensagem() + " ]");

            datagramSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

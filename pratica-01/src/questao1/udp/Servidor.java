package questao1.udp;

import questao1.Sentido;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
            byte[] mensagemEnvio = String.valueOf(Dados.getMensagem(this.getId()) + this.getId()).getBytes();

            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramPacket datagramPacket = new DatagramPacket(mensagemEnvio, mensagemEnvio.length, InetAddress.getByName("localhost"), this.getPorta());

            System.out.println("Mensagem enviada pelo P" + this.getId() + ": [ " + this.getMensagem() + " + " + this.getId() + " ]");
            datagramSocket.send(datagramPacket);

            datagramSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package questao1.udp;

import questao1.Sentido;

public class Processo implements Runnable {
    private int id;
    private int porta;
    private Processo antecessor;
    private Processo sucessor;
    private Integer mensagem;
    private Sentido sentido;

    public Processo(int id, int porta) {
        this.id = id;
        this.porta = porta;
    }

    public Integer getId() {
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

    public Processo getAntecessor() {
        return antecessor;
    }

    public Processo getSucessor() {
        return sucessor;
    }

    public Sentido getSentido() {
        return sentido;
    }

    public void conexoes(Processo antecessor, Processo sucessor, Sentido sentido) {
        this.antecessor = antecessor;
        this.sucessor = sucessor;
        this.sentido = sentido;
    }

    @Override
    public void run() {

        Servidor servidor;
        Cliente cliente;

        servidor = new Servidor(this.getId(), this.getPorta(), this.getSentido());

        if (this.getSentido().equals(Sentido.HORARIO)) {
            cliente = new Cliente(this.getId(), this.getAntecessor()); //Com sentido HORARIO
        } else {
            cliente = new Cliente(this.getId(), this.getSucessor()); //Com sentido ANTI_HORARIO
        }

        Thread threadServidor = new Thread(servidor);
        threadServidor.start();

        Thread threadCliente = new Thread(cliente);
        threadCliente.start();

    }
}

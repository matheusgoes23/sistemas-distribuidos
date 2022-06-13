package questao1.processo;

public class Processo implements Runnable {
    private static final int coordenadorPorta = 5000;
    private int id;
    private int porta;

    public Processo(int id, int porta) {
        this.id = id;
        this.porta = porta;
    }

    public Processo(Processo processo) {
        this.id = processo.getId();
        this.porta = processo.getPorta();
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

    @Override
    public void run() {
//        Servidor servidor;
//        servidor = new Servidor(this.getId(), this.getPorta());

        Cliente cliente = new Cliente(this.getId(), this.getPorta(), coordenadorPorta);

//        Thread threadServidor = new Thread(servidor);
//        threadServidor.start();

        Thread threadCliente = new Thread(cliente);
        threadCliente.start();
    }
}

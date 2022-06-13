package questao1.coordenador;

public class Coordenador implements Runnable {
    private static final int porta = 5000;

    @Override
    public void run() {

        Servidor servidor = new Servidor(porta);

        Thread threadServidor = new Thread(servidor);
        threadServidor.start();
    }
}

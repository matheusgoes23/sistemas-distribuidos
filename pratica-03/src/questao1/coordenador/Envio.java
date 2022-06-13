package questao1.coordenador;

import questao1.processo.Processo;
import questao1.utils.Dados;
import questao1.utils.Recurso;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Envio implements Runnable {

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10L);
                if (Recurso.estaDisponivel() && !Dados.filaVazia()) {
                    Processo processoComPermissao = Dados.tirarFila();

                    Socket socketEnvio = new Socket("localhost", processoComPermissao.getPorta());
                    DataOutputStream fluxoSaida = new DataOutputStream(socketEnvio.getOutputStream());
                    System.out.println("Coordenador enviou para o Processo " + processoComPermissao.getId());
                    fluxoSaida.writeUTF("OK");

                    fluxoSaida.close();
                    socketEnvio.close();
                }
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

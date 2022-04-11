package questao2.tcp;

import questao2.Operacao;

import java.util.HashMap;
import java.util.Map;

public class Processo implements Runnable {
    private final int portaCentral = 5001;
    Map<Integer, Processo> processos = new HashMap<>();
    private int id;
    private int porta;
    private Integer mensagem;
    private Operacao operacao = TipoOperacao.getOperacao();
    private Processo processo;

    public Processo(int id, int porta) {
        this.id = id;
        this.porta = porta;
    }

    public int getId() {
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

    public Operacao getOperacao() {
        return operacao;
    }

    public Map<Integer, Processo> getProcessos() {
        return processos;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void enviarMensagem(Processo processo, Integer mensagem) {
        this.processo = processo;
        this.mensagem = mensagem;
        this.getProcessos().remove(this.getId());
    }

    public int getPortaCentral() {
        return portaCentral;
    }

    public void conexoes(Processo P2, Processo P3, Processo P4) {
        this.processos.put(P2.getId(), P2);
        this.processos.put(P3.getId(), P3);
        this.processos.put(P4.getId(), P4);
    }

    @Override
    public void run() {

        if (this.getPortaCentral() == this.getPorta() && this.getOperacao().equals(Operacao.UNICAST)) {
            Servidor servidor = new Servidor(this.getId(), this.getPorta());
            Thread threadServidor = new Thread(servidor);
            threadServidor.start();
        } else if (this.getPortaCentral() == this.getPorta() && this.getOperacao().equals(Operacao.BROADCAST)) {
            Servidor servidor = new Servidor(this.getId(), this.getPorta(), this.getProcessos().size());
            Thread threadServidor = new Thread(servidor);
            threadServidor.start();
        } else if (this.getProcesso() != null && this.getOperacao().equals(Operacao.UNICAST)) {
            boolean condicao = this.getId() < this.getProcesso().getId();

            Cliente cliente = new Cliente(this.getId(), this.getPortaCentral(), this.getMensagem(), condicao);
            Thread threadCliente = new Thread(cliente);
            threadCliente.start();

            Cliente clienteRecebimento = new Cliente(this.getProcesso().getId(), this.getPortaCentral(), condicao);
            Thread threadClienteRecebimento = new Thread(clienteRecebimento);
            threadClienteRecebimento.start();
        } else if (this.getOperacao() != null && this.getOperacao().equals(Operacao.BROADCAST)) {
            Cliente cliente = new Cliente(this.getId(), this.getPortaCentral(), this.getMensagem(), true);
            Thread threadCliente = new Thread(cliente);
            threadCliente.start();
        }
    }
}

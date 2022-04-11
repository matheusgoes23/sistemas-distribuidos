package questao2;

import questao2.tcp.MainTCP;

public class Questao2 {
    public static void main(String[] args) {

        //  Cliente/Servidor com TCP em Unicast
//        MainTCP.start(Operacao.UNICAST);

        //  Cliente/Servidor com TCP em Broadcast
        MainTCP.start(Operacao.BROADCAST);

    }
}

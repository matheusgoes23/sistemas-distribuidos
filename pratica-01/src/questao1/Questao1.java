package questao1;

import questao1.tcp.MainTCP;
import questao1.udp.MainUDP;

public class Questao1 {
    public static void main(String[] args) {

        //  Cliente/Servidor com TCP em sentido horário
        MainTCP.start(Sentido.HORARIO);

        //  Cliente/Servidor com TCP em sentido anti-horario
//        MainTCP.start(Sentido.ANTI_HORARIO);

        //  Cliente/Servidor com UDP em sentido anti-horario
//        MainUDP.start(Sentido.HORARIO);

        //  Cliente/Servidor com UDP em sentido anti-horario
//        MainUDP.start(Sentido.ANTI_HORARIO);
    }
}

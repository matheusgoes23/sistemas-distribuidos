package questao1;

import questao1.tcp.MainTCP;

public class Questao1 {
    public static void main(String[] args) {

        //  Cliente/Servidor com TCP em sentido horário
        MainTCP.start(Sentido.HORARIO);

        //  Cliente/Servidor com TCP em sentido anti-horario
//        MainTCP.start(Sentido.ANTI_HORARIO);
    }
}

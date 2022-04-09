package questao1.tcp;

import questao1.Sentido;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainTCP {
    public static void start(Sentido sentido) {

        Processo P1 = new Processo(1, 5001);
        Processo P2 = new Processo(2, 5002);
        Processo P3 = new Processo(3, 5003);
        Processo P4 = new Processo(4, 5004);

        P1.conexoes(P4, P2, sentido);
        P2.conexoes(P1, P3, sentido);
        P3.conexoes(P2, P4, sentido);
        P4.conexoes(P3, P1, sentido);

        P1.setMensagem(lerTeclado());

        Dados.addDados(P1);
        Dados.addDados(P2);
        Dados.addDados(P3);
        Dados.addDados(P4);

        List<Thread> processos = new ArrayList<>();
        processos.add(new Thread(P1));
        processos.add(new Thread(P2));
        processos.add(new Thread(P3));
        processos.add(new Thread(P4));

        for (var processo : processos) {
            processo.start();
        }
    }

    public static Integer lerTeclado() {
        Scanner in = new Scanner(System.in);

        System.out.print("Digite um valor entre 0 a 9: ");

        var mensagem = in.nextLine();

        boolean condicao = true;
        while (condicao) {
            switch (mensagem) {
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    condicao = false;
                    break;
                default:
                    System.out.println("Não é possível operar com esse tipo de dado. Tente outra vez!");
                    mensagem = in.nextLine();
            }
        }

        return Integer.parseInt(mensagem);
    }
}
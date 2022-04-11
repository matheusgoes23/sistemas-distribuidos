package questao2.tcp;

import questao2.Operacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainTCP {
    static Scanner in = new Scanner(System.in);

    public static void start(Operacao operacao) {
        TipoOperacao.setOperacao(operacao);

        Processo P1 = new Processo(1, 5001);
        Processo P2 = new Processo(2, 5002);
        Processo P3 = new Processo(3, 5003);
        Processo P4 = new Processo(4, 5004);

        P1.conexoes(P2, P3, P4);

        System.out.println("Escolha qual processo deve enviar a mensagem:" + "\nP2: Digite 2" + "\nP3: Digite 3" + "\nP4: Digite 4");

        int processoEnvio = in.nextInt();

        boolean condicao = true;
        while (condicao) {
            switch (processoEnvio) {
                case 2:
                    if (operacao.equals(Operacao.UNICAST)) {
                        P2.enviarMensagem(escolhaRecebimento(P3, P4), receberMensagem());
                    } else {
                        P2.setMensagem(receberMensagem());
                    }
                    condicao = false;
                    break;
                case 3:
                    if (operacao.equals(Operacao.UNICAST)) {
                        P3.enviarMensagem(escolhaRecebimento(P2, P4), receberMensagem());
                    } else {
                        P3.setMensagem(receberMensagem());
                    }
                    condicao = false;
                    break;
                case 4:
                    if (operacao.equals(Operacao.UNICAST)) {
                        P4.enviarMensagem(escolhaRecebimento(P2, P3), receberMensagem());
                    } else {
                        P4.setMensagem(receberMensagem());
                    }
                    condicao = false;
                    break;
                default:
                    System.out.println("Não existe esse processo. Tente outra vez!");
                    processoEnvio = in.nextInt();
            }
        }

        List<Thread> processos = new ArrayList<>();
        processos.add(new Thread(P1));
        processos.add(new Thread(P2));
        processos.add(new Thread(P3));
        processos.add(new Thread(P4));

        for (var processo : processos) {
            processo.start();
        }
    }

    public static Processo escolhaRecebimento(Processo primeiro, Processo segundo) {
        int P1 = primeiro.getId();
        int P2 = segundo.getId();

        System.out.println("Escolha qual processo deve receber a mensagem:" + "\nP" + primeiro.getId() + ": Digite " + primeiro.getId() + "\nP" + segundo.getId() + ": Digite " + segundo.getId());

        int processoRecebimento = in.nextInt();

        boolean condicao = true;
        while (condicao) {
            if (processoRecebimento == primeiro.getId()) {
                return primeiro;
            } else if (processoRecebimento == segundo.getId()) {
                return segundo;
            } else {
                System.out.println("Não existe esse processo. Tente outra vez!");
                processoRecebimento = in.nextInt();
            }
        }
        return null;
    }

    public static Integer receberMensagem() {
        System.out.print("Digite um valor entre 0 a 9: ");

        var messagem = in.nextLine();

        boolean condicao = true;
        while (condicao) {
            switch (messagem) {
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
                    messagem = in.nextLine();
            }
        }

        return Integer.parseInt(messagem);
    }
}

package cliente;

import cliente.visoes.VisaoFuncionario;
import cliente.visoes.VisaoUsuario;
import servidor.DigitalBank;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

    static Scanner teclado = new Scanner(System.in);

    static String token = null;

    public static void main(String[] args) {

        try {
            Registry registro = LocateRegistry.getRegistry("localhost", 20001);

            DigitalBank stubCliente = (DigitalBank) registro.lookup("DigitalBank");

            token = fazerLogin(stubCliente);

            int tipoConta = stubCliente.obterTipoConta(token);

            if (tipoConta == 0) {
                VisaoFuncionario visaoFuncionario = new VisaoFuncionario();
                visaoFuncionario.iniciar(stubCliente, token);
            } else if (tipoConta == 1) {
                VisaoUsuario visaoUsuario = new VisaoUsuario();
                visaoUsuario.iniciar(stubCliente, token);
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static String fazerLogin(DigitalBank stubCliente) throws RemoteException {
        while (token == null) {

            System.out.print("Digite seu login: ");
            String login = teclado.nextLine();
            System.out.print("Digite sua senha: ");
            String senha = teclado.nextLine();

            token = stubCliente.autenticar(login, senha);

            if (token == null) {
                System.err.println("Login ou senha inválidos! Tente novamente.");
                System.out.println();
            }
        }

        return token;
    }
}

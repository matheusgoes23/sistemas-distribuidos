package servidor;

import utils.Dados;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Servidor {

    public static void main(String[] args) {


        try {
            DigitalBankImpl refObjRemoto = new DigitalBankImpl();

            DigitalBank skeleton = (DigitalBank) UnicastRemoteObject.exportObject(refObjRemoto, 0);

            LocateRegistry.createRegistry(20001);

            Registry registro = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress(), 20001);

            registro.bind("DigitalBank", skeleton);

            System.err.println("Servidor pronto");

            Dados.gerarFuncionarioInicial();

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}

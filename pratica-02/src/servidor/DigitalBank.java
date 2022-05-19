package servidor;

import utils.Conta;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DigitalBank extends Remote {
    String autenticar(String login, String senha) throws RemoteException;

    int obterTipoConta(String token) throws RemoteException;

    int criarConta(Conta conta, String token) throws RemoteException;

    int alterarConta(String numero, Conta conta, String token) throws RemoteException;

    Conta buscarConta(String numero, String token) throws RemoteException;

    Conta buscarMinhaConta(String token) throws RemoteException;

    List<Conta> listarContas(String token) throws RemoteException;

    int removerConta(String numero, String token) throws RemoteException;

    double sacar(double valor, String token) throws RemoteException;

    double depositar(double valor, String token) throws RemoteException;

    double saldo(String token) throws RemoteException;

    int transferir(String numeroReceber, double valor, String token) throws RemoteException;

    String investirPoupanca(String token) throws RemoteException;

    String investirRendaFixa(String token) throws RemoteException;
}
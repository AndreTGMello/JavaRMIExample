package main;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RepositoryServer {

	public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
		String nomeArmazem = args[0];
		PartRepositoryReal partRepository = null;
		Scanner sc = new Scanner(System.in);
		
		try{
			partRepository = new PartRepositoryReal(nomeArmazem);
            Naming.rebind(nomeArmazem,partRepository);
            System.out.println("\nO servidor esta ativo!"
            		+ "\nPara encerrar o servidor digite shutdown.");
		}catch(RemoteException re){
            System.out.println("\nErro Remoto: "+re.toString());
        }
        catch(Exception e){
            System.out.println("\nErro Local: "+e.toString());
        }
		while(true){
			String cmd = sc.next();
			if(cmd.equals("shutdown")){
				Naming.unbind(partRepository.getNomeArmazem());
		        UnicastRemoteObject.unexportObject(partRepository, true);
		        System.out.println("\nArmazem fechado.");
		        break;
			}
			else System.out.println("\nComando invalido.");
		}
		return;
	}

}

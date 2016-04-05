package main;

import java.rmi.*;

public class RepositoryServer {

	public static void main(String[] args) {
		String nomeArmazem = args[0];
		PartRepositoryReal partRepository;
		
		try{
			partRepository = new PartRepositoryReal(nomeArmazem);
            Naming.rebind(nomeArmazem,partRepository);
            System.out.println("\nO servidor esta ativo!"
            		+ "\nPara encerrar o servidor acione Ctr+C.");
		}catch(RemoteException re){
            System.out.println("\nErro Remoto: "+re.toString());
        }
        catch(Exception e){
            System.out.println("\nErro Local: "+e.toString());
        }
	}

}

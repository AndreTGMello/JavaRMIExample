package main;

import java.rmi.*;

public class RepositoryServer {

	public static void main(String[] args) {
		String nomeArmazem = args[0];
		PartRepositoryReal partRepository;
		
		try{
			partRepository = new PartRepositoryReal(nomeArmazem);
            Naming.rebind(nomeArmazem,partRepository);
            System.out.println("ObjetoServidor esta ativo!");
		}catch(RemoteException re){
            System.out.println("Erro Remoto: "+re.toString());
        }
        catch(Exception e){
            System.out.println("Erro Local: "+e.toString());
        }
	}

}

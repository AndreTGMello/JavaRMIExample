package main;

import javax.rmi.PortableRemoteObject;
import java.rmi.*;
import javax.swing.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class PartRepositoryReal extends UnicastRemoteObject implements PartRepository {

	ArrayList<Part> partsArmazenadas;
	String nomeArmazem;
	
	public PartRepositoryReal(String nomeArmazem) throws RemoteException{
		this.partsArmazenadas = new ArrayList<>();
		this.nomeArmazem = nomeArmazem;
	}

	@Override
	public ArrayList<Part> getListaPart() throws RemoteException{
		return partsArmazenadas;
	}

	@Override
	public Part getPart(int cod) throws RemoteException{
		Part p = null;

		try{
			p = partsArmazenadas.get(cod);
		}
		catch(Exception e){
			System.out.println("Nao foi possivel encontrar o item desejado."
					+"\nErro: "+e.toString());
		}
		
		return p;
	}

	@Override
	public void addPart(Part p) throws RemoteException{
		partsArmazenadas.add(p);
	}

	@Override
	public String getNomeArmazem() throws RemoteException{
		return nomeArmazem;
	}

}

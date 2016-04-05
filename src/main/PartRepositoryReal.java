package main;

import javax.rmi.PortableRemoteObject;
import java.rmi.*;
import javax.swing.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PartRepositoryReal extends UnicastRemoteObject implements PartRepository {

	private static AtomicInteger GERADOR_COD = new AtomicInteger(0);
	ArrayList<Part> partsArmazenadas;
	String nomeArmazem;
	
	public PartRepositoryReal(String nomeArmazem) throws RemoteException{
		this.partsArmazenadas = new ArrayList<>();
		this.nomeArmazem = nomeArmazem;
		System.out.println("Repositorio criado com nome de armazem: " + this.nomeArmazem);
	}

	@Override
	public ArrayList<Part> getListaPart() throws RemoteException{
		System.out.println("\nEnviando lista de pecas armazenadas.");
		return this.partsArmazenadas;
	}

	@Override
	public Part getPart(int cod) throws RemoteException{
		Part p = null;

		System.out.println("\nSolicitacao de busca por pecas.");
		try{
			for (Part part : partsArmazenadas) {
				if(part.getCod()==cod){
					p = part;
					System.out.println("Peca encontrada. Cod: " + p.getCod());
				}
			}
		}
		catch(Exception e){
			System.out.println("Nao foi possivel encontrar o item desejado."
					+"\nErro: "+e.toString());
		}
		
		return p;
	}

	@Override
	public boolean addPart(Part p) throws RemoteException{
		if(!partsArmazenadas.contains(p)){
			partsArmazenadas.add(p);
			System.out.println("\nAdicionada peca de codigo: " + p.getCod() 
					+ " a lista de pecas.");
			return true;
		}else{
			System.out.println("Peca inserida ja existe.");
			return false;
		}
	}

	@Override
	public String getNomeArmazem() throws RemoteException{
		System.out.println("\nNome do armazem acessado. Retornando: " 
				+ this.nomeArmazem);
		return this.nomeArmazem;
	}

	@Override
	public int getCodNovaPeca() throws RemoteException{
		int cod = GERADOR_COD.getAndIncrement();
		System.out.println("\nCodigo gerado para nova peca: " + cod);
		return cod;
	}

	@Override
	public boolean removePart(int cod) throws RemoteException {
		Part p = getPart(cod);
		if(p!=null){
			partsArmazenadas.remove(p);
			return true;
		}else
			return false;
	}

}

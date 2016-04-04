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
	public ArrayList<Part> getListaPart() {
		return partsArmazenadas;
	}

	@Override
	public Part getPart(int cod) {
		return partsArmazenadas.get(cod);
	}

	@Override
	public void addPart(Part p) {
		partsArmazenadas.add(p);
	}

	@Override
	public String getNomeArmazem() {
		return nomeArmazem;
	}

}

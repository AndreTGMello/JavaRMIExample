package main;

import java.rmi.*;
import java.util.ArrayList; 

public interface PartRepository extends Remote{
	ArrayList<Part> getListaPart() throws RemoteException;
	Part getPart(int cod) throws RemoteException;
	void addPart(Part p) throws RemoteException;
	String getNomeArmazem() throws RemoteException;
	}

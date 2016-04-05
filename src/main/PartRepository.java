package main;

import java.rmi.*;
import java.util.ArrayList;

public interface PartRepository extends Remote{
	ArrayList<Part> getListaPart() throws RemoteException;
	Part getPart(int cod) throws RemoteException;
	boolean addPart(Part p) throws RemoteException;
	boolean removePart(int cod) throws RemoteException;
	String getNomeArmazem() throws RemoteException;
	int getCodNovaPeca() throws RemoteException;
	}

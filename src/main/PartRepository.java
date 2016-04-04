package main;

import java.rmi.Remote; 
import java.rmi.RemoteException;
import java.util.ArrayList; 

public interface PartRepository extends Remote{
	ArrayList<Part> getListaPart();
	Part getPart(int cod);
	void addPart(Part p);
	String getNomeArmazem();
	}

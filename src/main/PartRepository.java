package main;

import java.rmi.Remote; 
import java.rmi.RemoteException; 

public interface PartRepository extends Remote{
	void getListaPart();
	Part getPart(int cod);
	void addPart(Part p);
	}

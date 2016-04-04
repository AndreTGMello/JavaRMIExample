package main;

import java.rmi.Remote; 
import java.rmi.RemoteException; 

public interface PartRepository extends Remote{
	public static void getListaPart(){	
	}
	public static Part getPart(int cod){
		return null;	
	}
	public static void addPart(Part p){	
	}
}

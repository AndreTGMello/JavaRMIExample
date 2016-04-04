package main;

import java.util.ArrayList;

public class PartReal implements Part {
	private int cod;
	private String nome;
	private String desc;
	private String localArmazenado;
	private ArrayList <Part> subComponentes = new ArrayList<>();
	
	public PartReal(int cod, String desc, String localArmazenado, ArrayList<Part> subComponentes) {
		this.cod = cod;
		this.desc = desc;
		this.localArmazenado = localArmazenado;
		this.subComponentes = subComponentes;
	}
	
	public int getCod(){
		return cod;
	}
	public String getNome(){
		return nome;
	}
	public String getDesc(){
		return desc;
	}
	public String getLocalArmazenado(){
		return localArmazenado;
	}
	public ArrayList<Part> getSubComp(){
		return subComponentes;
	}
	public int getQtdSubComp(){
		return getSubComp().size();
	}
	public String primitivaOuAgregada(){
		if(getQtdSubComp()==0){
			return "Primitiva";
		}else return "Agregada";
	}
}

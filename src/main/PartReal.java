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
	
	@Override
	public int getCod(){
		return cod;
	}
	
	@Override
	public String getNome(){
		return nome;
	}
	
	@Override
	public String getDesc(){
		return desc;
	}
	
	@Override
	public String getLocalArmazenado(){
		return localArmazenado;
	}
	
	@Override
	public ArrayList<Part> getSubComp(){
		return subComponentes;
	}
	
	@Override
	public int getQtdSubComp(){
		return getSubComp().size();
	}
	
	@Override
	public String primitivaOuAgregada(){
		if(getQtdSubComp()==0){
			return "Primitiva";
		}else return "Agregada";
	}
}

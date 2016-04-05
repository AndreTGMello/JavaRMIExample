package main;

import java.util.ArrayList;
import java.util.Map;

public class PartReal implements Part {
	private int cod;
	private String nome;
	private String desc;
	private String localArmazenado;
	private Map<Part, Integer> subComponentes;
	
	public PartReal(int cod, String nome ,String desc, String localArmazenado, Map<Part, Integer> subComponentes) {
		this.cod = cod;
		this.nome = nome;
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
	public Map<Part, Integer> getSubComp(){
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

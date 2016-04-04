package main;

import java.util.ArrayList;

public interface Part {
	int getCod();
	String getNome();
	String getDesc();
	String getLocalArmazenado();
	ArrayList<Part> getSubComp();
	int getQtdSubComp();
	String primitivaOuAgregada();
}

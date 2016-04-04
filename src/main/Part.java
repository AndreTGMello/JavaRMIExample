package main;

import java.util.ArrayList;
import java.util.Map;

public interface Part {
	int getCod();
	String getNome();
	String getDesc();
	String getLocalArmazenado();
	Map<Part, Integer> getSubComp();
	int getQtdSubComp();
	String primitivaOuAgregada();
}

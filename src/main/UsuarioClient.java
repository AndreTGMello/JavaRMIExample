package main;

import java.io.File;
import java.rmi.*;
import java.util.Scanner;

import javax.swing.*;

public class UsuarioClient {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String repositorioCorrente;
		Part pecaCorrente;
		
		while(scan.hasNext()){
			String comando = scan.next();
			if(comando.equals("bind")){
				System.out.println("Insira o nome do armazem a ser acessado:");
				String nomeArmazem = scan.next();
				
			}
		}

	}

}

package main;

import java.io.File;
import java.rmi.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class UsuarioClient {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String nomeArmazem;
		PartRepository repositorioCorrente = null;
		Part pecaCorrente = null;
		ArrayList<Part> subPecasCorrente = null;
		
		
		while(!scan.next().equals("quit")){
			String comando = scan.next();
			if(comando.equals("bind")){
				System.out.println("Insira o nome do armazem a ser acessado:");
				nomeArmazem = scan.next();
				
				try{
					repositorioCorrente = (PartRepository) Naming.lookup(nomeArmazem);
				}catch(RemoteException re){
		            System.out.println("Erro Remoto: "+re.toString());
		        }
		        catch(Exception e){
		            System.out.println("Erro Local: "+e.toString());
		        }
			}
			else if(comando.equals("listp")){
				ArrayList<Part> listaPart = repositorioCorrente.getListaPart();
				for (Part part : listaPart) {
					System.out.print(part+" | ");
				}
				System.out.println();
			}
			else if(comando.equals("getp")){
				System.out.println("Insira o codigo da peca a ser buscada:");
				int cod = scan.nextInt();
				if(repositorioCorrente.getPart(cod)!=null){
					pecaCorrente = repositorioCorrente.getPart(cod);
				}
				else System.out.println("Peca nao encontrada, tente novamente.");
			}
			else if(comando.equals("showp")){
				System.out.println("Codigo da peca: "
						+ pecaCorrente.getCod()
						+ "\nNome da peca: "
						+ pecaCorrente.getNome()
						+ "\nDescricao da peca: "
						+ pecaCorrente.getDesc()
						+ "\nLocal de armazenamento: "
						+ pecaCorrente.getLocalArmazenado()
						+ "Tipo: "
						+ pecaCorrente.primitivaOuAgregada()
						+ "Quantidade de subcomponentes: "
						+ pecaCorrente.getQtdSubComp());
				if(pecaCorrente.getQtdSubComp()>0){
					System.out.println("Subcomponentes: ");
					for (Part part : pecaCorrente.getSubComp()) {
						System.out.print(part+" | ");
					}
					System.out.println();
				}
			}
			else if(comando.equals("clearlist")){
				subPecasCorrente = new ArrayList<>();
			}
			else if(comando.equals("addsubpart")){
				System.out.println("Numero de pecas que deseja adicionar: ");
				int qtd = scan.nextInt();
				subPecasCorrente.add(pecaCorrente);
			}
			else if(comando.equals("addp")){
				
			}
		}

	}

}

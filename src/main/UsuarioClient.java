package main;

import java.io.File;
import java.rmi.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Objects;

import javax.swing.*;

public class UsuarioClient {

	public static void main(String[] args) throws RemoteException {
		Scanner scan = new Scanner(System.in);
		String nomeArmazem = "";
		PartRepository repositorioCorrente = null;
		Part pecaCorrente = null;
		Map<Part, Integer> subPecasCorrente = new HashMap<>();
		
		
		while(true){
			System.out.println();
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
				System.out.println("Armazem " + nomeArmazem + " acessado.");
			}
			else if(comando.equals("listp")){
				
				ArrayList<Part> listaPart = repositorioCorrente.getListaPart();
				for (Part part : listaPart) {
					System.out.println("Codigo: " + part.getCod() 
					+ ". Nome: " + part.getNome() + ".");
				}
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
						+ "\nTipo: "
						+ pecaCorrente.primitivaOuAgregada()
						+ "\nQuantidade de subcomponentes: "
						+ pecaCorrente.getQtdSubComp());
				if(pecaCorrente.getQtdSubComp()>0){
					System.out.println("Subcomponentes: ");
					Map<Part, Integer> subComp = pecaCorrente.getSubComp();
					for (Map.Entry<Part, Integer> entry : subComp.entrySet())
					{
					    System.out.println("Subcomponente" + entry.getValue() 
					    + ". Quantidade: " + entry.getValue());
					}
				}
				
			}
			else if(comando.equals("clearlist")){
				
				subPecasCorrente = new HashMap<>();

			}
			else if(comando.equals("addsubpart")){
			
				System.out.println("Numero de pecas que deseja adicionar: ");
				int qtd = scan.nextInt();
				if(subPecasCorrente.containsValue(pecaCorrente)){
					subPecasCorrente.replace(pecaCorrente, 
							subPecasCorrente.get(pecaCorrente), 
							subPecasCorrente.get(pecaCorrente)+qtd);
				}
				else{
					subPecasCorrente.put(pecaCorrente, qtd);
				}
				
			}
			else if(comando.equals("addp")){
				Part p = criaPeca(subPecasCorrente, nomeArmazem);
				repositorioCorrente.addPart(p);
			}
			else if(comando.equals("quit")){
				break;
			}
			else{
				System.out.println("Comando invalido.");
			}
		}

	}

	private static Part criaPeca(Map<Part, Integer> subComponentes, String localArmazenado) {
		int cod = 0;
		String nome = "";
		String desc = "";
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Digite o codigo da peca: ");
		cod = scan.nextInt();
		
		System.out.println("Digite o nome da peca: ");
		nome = scan.next();
		
		System.out.println("Digite a descricao da peca: ");
		desc = scan.next();
		
		PartReal p = new PartReal(cod, nome, desc, localArmazenado, subComponentes);
		return p;
	}

}

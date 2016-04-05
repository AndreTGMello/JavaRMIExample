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
		Scanner scan = new Scanner(System.in).useDelimiter("\\n");
		String nomeArmazem = "";
		PartRepository repositorioCorrente = null;
		Part pecaCorrente = null;
		Map<Part, Integer> subPecasCorrente = new HashMap<>();
		
		
		while(true){
			System.out.println();
			String comando = scan.next();
			if(comando.equals("bind")){
				System.out.println("\nInsira o nome do armazem a ser acessado:");
				nomeArmazem = scan.nextLine();
				
				try{
					repositorioCorrente = (PartRepository) Naming.lookup(nomeArmazem);
				}catch(RemoteException re){
		            System.out.println("Erro Remoto: " + re.toString() + "."
		            					+ "\nArmazem de nome '" + nomeArmazem 
		            					+ "' nao foi encontrado.");
		            nomeArmazem = "Erro. Armazem nao encontrado.";
		        }
		        catch(Exception e){
		            System.out.println("Erro Local: "+e.toString());
		        }
				System.out.println("Conectado ao armazem: "+nomeArmazem);
			}
			else if(comando.equals("listp")){
				
				ArrayList<Part> listaPart = repositorioCorrente.getListaPart();
				for (Part part : listaPart) {
					System.out.println("Codigo: " + part.getCod() 
					+ ". Nome: " + part.getNome() + ".");
				}
			}
			else if(comando.equals("getp")){
				
				System.out.println("\nInsira o codigo da peca a ser buscada:");
				int cod = scan.nextInt();
				if(repositorioCorrente.getPart(cod)!=null){
					pecaCorrente = repositorioCorrente.getPart(cod);
					System.out.println("Peca cod " + pecaCorrente.getCod() 
					+ " obtida com sucesso.");
				}
				else System.out.println("Peca nao encontrada, tente novamente.");
				
			}
			else if(comando.equals("showp")){
				
				System.out.println("\nCodigo da peca: "
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
					System.out.println("Lista de subcomponentes: ");
					Map<Part, Integer> subComp = pecaCorrente.getSubComp();
					for (Map.Entry<Part, Integer> entry : subComp.entrySet())
					{
					    System.out.println("Subcomponente codigo: " 
					    			+ entry.getKey().getCod() 
					    			+ ". Quantidade: " + entry.getValue());
					}
				}
				
			}
			else if(comando.equals("clearlist")){
				
				subPecasCorrente = new HashMap<>();
				System.out.println("\nLista limpa com sucesso.");

			}
			else if(comando.equals("addsubpart")){
			
				System.out.println("\nNumero de pecas do tipo cod "
						+ pecaCorrente.getCod() + " que deseja adicionar: ");
				int qtd = scan.nextInt();
				if(subPecasCorrente.containsKey(pecaCorrente)){
					subPecasCorrente.replace(pecaCorrente, 
							subPecasCorrente.get(pecaCorrente), 
							subPecasCorrente.get(pecaCorrente)+qtd);
				}
				else{
					subPecasCorrente.put(pecaCorrente, qtd);
				}
				System.out.println("\n"+qtd+ " pecas cod "
						+ pecaCorrente.getCod() + " adicionadas corretamente.");
				
			}
			else if(comando.equals("addp")){
				
				Part p = criaPeca(subPecasCorrente, nomeArmazem);
				repositorioCorrente.addPart(p);
				System.out.println("\nPeca cod " + p.getCod() 
				+ " inserida corretamente no armazem " + nomeArmazem + ".");
				
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
		
		System.out.println("\nDigite o codigo da peca: ");
		cod = scan.nextInt();
		scan.nextLine();
		
		System.out.println("\nDigite o nome da peca: ");
		nome = scan.nextLine();
		
		System.out.println("\nDigite a descricao da peca: ");
		desc = scan.nextLine();
		
		PartReal p = new PartReal(cod, nome, desc, localArmazenado, subComponentes);
		return p;
	}

}

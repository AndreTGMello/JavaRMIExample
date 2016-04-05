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
		PartRepository repositorioCorrente = null;
		Part pecaCorrente = null;
		Map<Part, Integer> subPecasCorrente = new HashMap<>();
		
		while(true){
			System.out.println();
			String comando = scan.next();
			if(comando.equals("bind")){
				System.out.println("\nInsira o nome do armazem a ser acessado:");
				String nomeArmazem = scan.next();
				
				try{
					repositorioCorrente = (PartRepository) Naming.lookup(nomeArmazem);
				}catch(RemoteException re){
				    System.out.println("\nErro Remoto: "+re.toString());
			    }
		        catch(Exception e){
		        	System.out.println("\nErro: " + e.toString());
		        	nomeArmazem = "Erro. Armazem nao encontrado. Tente novamente.";
		        }
				System.out.println("\nConectado ao armazem:\n"+nomeArmazem);
			}
			else if(comando.equals("listp")){
				
				ArrayList<Part> listaPart = repositorioCorrente.getListaPart();
				if(listaPart.size()==0){
					System.out.println("\nArmazem " 
							+ repositorioCorrente.getNomeArmazem() 
							+ " nao contem nenhuma peca.");
				}else{
					System.out.println("\nArmazem " 
							+ repositorioCorrente.getNomeArmazem() 
							+ " contem as seguintes pecas: ");
					for (Part part : listaPart) {
						System.out.println("Codigo: " + part.getCod() 
								+ ". Nome: " + part.getNome() + ".");
					}	
				}
			}
			else if(comando.equals("getp")){
				
				System.out.println("\nInsira o codigo da peca a ser buscada:");
				int cod = scan.nextInt();
				if(repositorioCorrente.getPart(cod)!=null){
					pecaCorrente = repositorioCorrente.getPart(cod);
					System.out.println("\nPeca cod " + pecaCorrente.getCod() 
							+ " obtida com sucesso.");
				}
				else System.out.println("\nPeca nao encontrada, tente novamente.");
				
			}
			else if(comando.equals("showp")){
				
				System.out.println("\nInformacoes sobre a peca corrente:"
						+ "\nCodigo da peca: "
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
					    System.out.println("Codigo: " 
					    			+ entry.getKey().getCod()
					    			+ ". Local de armazenamento: "
					    			+ entry.getKey().getLocalArmazenado()
					    			+ ". Quantidade: " + entry.getValue());
					}
				}
				
			}
			else if(comando.equals("showr")){
				
				System.out.println("\nInformacoes sobre o armazem corrente:"
						+ "\nNome do armazem: " + repositorioCorrente.getNomeArmazem()
						+ "\nQUantidade de pecas do armazem: " + repositorioCorrente.getListaPart().size());
				
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
				System.out.println("\n"+qtd+ " pecas de cod "
						+ pecaCorrente.getCod() + " adicionadas corretamente"
								+ " a lista de subpecas corrente.");
				
			}
			else if(comando.equals("addp")){
				
				Part p = criaPeca(subPecasCorrente, repositorioCorrente.getNomeArmazem());
				repositorioCorrente.addPart(p);
				System.out.println("\nPeca cod " + p.getCod() 
				+ " inserida corretamente no armazem " + repositorioCorrente.getNomeArmazem() + ".");
				
			}
			else if(comando.equals("quit")){
				
				break;
			
			}else if(comando.equals("help")){
				System.out.println("\nLista de comandos: "
						+ "\nhelp"
						+ "\nbind"
						+ "\nshowr"
						+ "\nshowp"
						+ "\nlistp"
						+ "\ngetp"
						+ "\nclearlist"
						+ "\naddsubpart"
						+ "\naddpt"
						+ "\nquit"
						+ "\n"
						+ "\nPara executar um comando basta digita-lo, sem a necessidade"
						+ " de fornecer parametros."
						+ "\nO programa solicitara as entradas necessarias para a correta"
						+ " execucao do comando, caso necessario.");
			}
			else{
				
				System.out.println("\nComando invalido.");
				
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

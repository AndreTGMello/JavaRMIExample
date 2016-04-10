package main;

import java.io.File;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
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
				if(listaPart==null || listaPart.size()==0){
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
				try{
					int cod = scan.nextInt();
					if(repositorioCorrente.getPart(cod)!=null){
						pecaCorrente = repositorioCorrente.getPart(cod);
						System.out.println("\nPeca cod " + pecaCorrente.getCod() 
								+ " obtida com sucesso.");
					}
					else System.out.println("\nPeca nao encontrada, tente novamente.");	
				}
				catch(Exception e){
					System.out.println("Erro: " + e.toString());
				}
				
				
			}
			else if(comando.equals("showp")){
				
				try {
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
				} catch (Exception e) {
					System.out.println("Erro: " + e.toString());
				}
				
			}
			else if(comando.equals("showr")){
				try {
					System.out.println("\nInformacoes sobre o armazem corrente:"
							+ "\nNome do armazem: " + repositorioCorrente.getNomeArmazem()
							+ "\nQuantidade de pecas do armazem: " + repositorioCorrente.getListaPart().size());
					
				} catch (Exception e) {
					System.out.println("Erro: " + e.toString());
				}
				
			}
			else if(comando.equals("clearlist")){
				
				subPecasCorrente = new HashMap<>();
				System.out.println("\nLista limpa com sucesso.");

			}
			else if(comando.equals("addsubpart")){
			
				System.out.println("\nNumero de pecas do tipo cod "
						+ pecaCorrente.getCod() + " que deseja adicionar: ");
				try {
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
				} catch (Exception e) {
					System.out.println("Erro: " + e.toString());
				}
				
			}
			else if(comando.equals("showsubpart")){
				if(!subPecasCorrente.isEmpty()){
					for (Map.Entry<Part, Integer> entry : subPecasCorrente.entrySet()) { 
						System.out.println("\nCod peca: " + entry.getKey().getCod()
								+ ", Local de armazenamento: " + entry.getKey().getLocalArmazenado()
								+ ", Qtde: " + entry.getValue()); 
					}
				}else System.out.println("\nLista de subpecas corrente esta vazia.");
			}
			else if(comando.equals("addp")){
				
				try {
					Part p = criaPeca(repositorioCorrente, subPecasCorrente);
					boolean insere = repositorioCorrente.addPart(p);
					if(insere){
						System.out.println("\nPeca cod " + p.getCod() 
						+ " inserida corretamente no armazem " + repositorioCorrente.getNomeArmazem() + ".");	
					}
					else{
						System.out.println("\nPeca ja existe no repositorio.");
					}
						
				} catch (Exception e) {
					System.out.println("Erro: " + e.toString());
				}
				
			}
			else if(comando.equals("removep")){
				
				System.out.println("\nInforme o codigo da peca a ser removida do"
						+ " armazem " + repositorioCorrente.getNomeArmazem() + ".");
				
				try {
					int cod = scan.nextInt();
					scan.nextLine();
				
					boolean remove = repositorioCorrente.removePart(cod);
					if(remove){
						System.out.println("\nPeca cod " + cod 
						+ " removida corretamente no armazem " + repositorioCorrente.getNomeArmazem() + ".");	
					}
					else{
						System.out.println("\nPeca nao existente no repositorio.");
					}
						
				} catch (Exception e) {
					System.out.println("Erro: " + e.toString());
				}
			}
			else if(comando.equals("quit")){
				
				break;
			
			}else if(comando.equals("help")){
				System.out.println("\nLista de comandos: "
						+ "\nhelp - Exibe essa tela de ajuda."
						+ "\nbind - Se conecta a um armazem."
						+ "\nshowr - Exibe informacoes basicas do armazem conectado."
						+ "\nshowp - Exibe informacoes basicas da peca corrente."
						+ "\nlistp - Exibe lista de pecas do armazem conectado."
						+ "\ngetp - Busca e retorna peca presente no armazem conectado."
						+ "\nclearlist - Limpa lista de subcomponentes corrente."
						+ "\naddsubpart - Adiciona peca corrente a lista de subcomponentes corrente"
						+ "\nshowsubpart - Exibe lista de subpecas corrente."
						+ "\naddp - Adiciona peca ao armazem conectado."
						+ "\nremovep - Remove peca do armazem corrente."
						+ "\nquit - Finaliza este programa."
						+ "\n"
						+ "\nPara executar um comando basta digita-lo, sem a necessidade"
						+ " de fornecer parametros."
						+ "\nO programa solicitara as devidas entradas para a correta"
						+ " execucao do comando, quando necessario.");
				
			}
			else{
				
				System.out.println("\nComando invalido.");
				
			}
		}

	}

	private static Part criaPeca(PartRepository r, Map<Part, Integer> subComponentes) throws RemoteException {
		int cod = 0;
		String nome = "";
		String desc = "";
		String localArmazenado = "";
		Scanner scan = new Scanner(System.in);
		PartReal p = null;
		try {
			cod = r.getCodNovaPeca();
			
			System.out.println("\nDigite o nome da peca: ");
			nome = scan.nextLine();
			
			System.out.println("\nDigite a descricao da peca: ");
			desc = scan.nextLine();
			
			localArmazenado = r.getNomeArmazem();
			
			p = new PartReal(cod, nome, desc, localArmazenado, subComponentes);
		} catch (Exception e) {
			p = null;
			System.out.println("Erro: " + e.toString());
		} finally {
			return p;
		}
		
	}

}

package controller;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;


import dao.*;
import model.*;

public class ValidacaoEstrutural {
	public static final String APPROVED = "APPROVED";
	public static final String REJECTED = "Campo obrigatório";
	//public static final String REJECTED_SIZE = "Campo com tamanho inválido";
	public static final String REJECTED_NOT_EXISTS = "Não existe no layout";
	
	private DaoTabela daoTabela;
	private DaoCampos daoCampos;
	private String nomeTabela;
	private Tabela tabela;
	private Hashtable<String, Campos> campos;
	
	public ValidacaoEstrutural(String nomeTabela){
		this.nomeTabela = nomeTabela.replaceAll("(?i).xml", "");
		daoTabela = new DaoTabela();
		tabela = daoTabela.buscar(this.nomeTabela.toUpperCase());
		daoCampos = new DaoCampos();
		campos = daoCampos.buscarTodos(tabela.getId_tabela());
		
	}
	
	public String valida(String columnName, String value) {
		try{	
			
			if(campos.get(columnName).getObrigatorio().compareTo("SIM") == 0 && (value == null || value == "" || value.isEmpty())){
				return REJECTED;// é obrigatório mas está em branco
				
			}
		}catch (NullPointerException npe) {
			return REJECTED_NOT_EXISTS; // nao existe no layout
		}
				
		return APPROVED;
	}
	public String validaVinculo(String columnName, String value){
		
		return APPROVED;
	}
	public Hashtable<String, String> buscaVinculados(){
		Hashtable<String, String> temp = new Hashtable<String, String>();
		Iterator<Map.Entry<String, Campos>> it = campos.entrySet().iterator();

		while (it.hasNext()) {
		  Map.Entry<String, Campos> entry = it.next();

		  // insere no HT os campos que são vinculados a outras tabelas
		  if (!campos.get(entry.getKey()).getTabelaOrigem().equals("")) {
			  temp.put(campos.get(entry.getKey()).getNome(), campos.get(entry.getKey()).getTabelaOrigem());		 
		  }
		}
		return temp;
	}


}

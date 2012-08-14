package controller;

import java.util.Hashtable;


import dao.*;
import model.*;

public class ValidacaoEstrutural {
	public static final String APPROVED = "APPROVED";
	public static final String REJECTED = "Campo obrigatório";
	public static final String REJECTED_SIZE = "Campo com tamanho inválido";
	public static final String REJECTED_NOT_EXISTS = "Não existe no layout";
	
	private DaoTabela daoTabela;
	private DaoCampos daoCampos;
	private String nomeTabela;
	private Tabela tabela;
	private Hashtable<String, Campos> campos;
	
	public ValidacaoEstrutural(String nomeTabela){
		this.nomeTabela = nomeTabela.replaceAll("(?i).xml", "");
		System.out.println(this.nomeTabela);
		daoTabela = new DaoTabela();
		tabela = daoTabela.buscar(this.nomeTabela.toUpperCase());
		daoCampos = new DaoCampos();
		campos = daoCampos.buscarTodos(tabela.getId_tabela());
		
	}
	
	public String valida(String columnName, String value) {
		try{	
			
			if(campos.get(columnName + tabela.getId_tabela()).isObrigatorio().compareTo("SIM") == 0 && (value == null || value == "" || value.isEmpty())){
				return REJECTED;
				
			}
			if(campos.get(columnName + tabela.getId_tabela()).isTamanho_fixo() && value.length() != campos.get(columnName + tabela.getId_tabela()).getTamanho()){
				return REJECTED_SIZE;
			}
		}catch (NullPointerException npe) {
			return REJECTED_NOT_EXISTS;
		}
				
		return APPROVED;
	}
	

}

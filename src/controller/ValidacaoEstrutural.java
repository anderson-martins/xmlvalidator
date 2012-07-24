package controller;

import dao.*;
import model.*;

public class ValidacaoEstrutural {
	public static final boolean APPROVED = true;
	public static final boolean REJECTED = false;
	
	private DaoTabela daoTabela;
	private DaoCampos daoCampos;
	private String nomeTabela;
	private Tabela tabela;
	
	public ValidacaoEstrutural(String nomeTabela){
		this.nomeTabela = nomeTabela.replace(".xml", "");
		daoTabela = new DaoTabela();
		tabela = daoTabela.buscar(this.nomeTabela);
		daoCampos = new DaoCampos();
		
		
	}
	
	public boolean valida(String columnName, String value){
		try{
			
			if(daoCampos.buscar(columnName, tabela.getId_tabela()).isObrigatorio().compareTo("SIM") == 0 && (value == null || value == "")){
				return REJECTED;
				
			}
				
		}catch (NullPointerException npe) {
			System.err.println(npe.getMessage());
		}
		return APPROVED;
	}

}

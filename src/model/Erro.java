/* Este model não está na base de dados, somente usado para geração do relatório*/
package model;

public class Erro {
	private String codErro;
	private String erro;
	private String tabela;
	private String coluna;
	
	public String getColuna() {
		return coluna;
	}
	public void setColuna(String coluna) {
		this.coluna = coluna;
	}
	public String getTabela() {
		return tabela;
	}
	public void setTabela(String tabela) {
		this.tabela = tabela;
	}
	public Erro(){}
	public Erro(String codErro, String erro, String tabela, String coluna){
		this.codErro = codErro;
		this.erro = erro;
		this.tabela = tabela;
		this.coluna = coluna;
	}
	public String getCodErro() {
		return codErro;
	}
	public void setCodErro(String codErro) {
		this.codErro = codErro;
	}
	public String getErro() {
		return erro;
	}
	public void setErro(String erro) {
		this.erro = erro;
	}
	

}

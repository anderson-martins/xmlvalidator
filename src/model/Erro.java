/* Este model n�o est� na base de dados, pois � usado para gera��o do relat�rio*/
package model;

public class Erro {
	private String codErro;
	private String erro;
	public Erro(){}
	public Erro(String codErro, String erro){
		this.codErro = codErro;
		this.erro = erro;
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

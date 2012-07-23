package model;

public class Campos {
	private int id_campo;
	private String nome;
	private int tamanho;
	private boolean tamanho_fixo;
	private boolean obrigatorio;
	private String tabelaOrigem;
	private boolean chave;
	private String tipo;
	private String comentario;
	private int id_tabela;
	
	public int getId_campo() {
		return id_campo;
	}
	public void setId_campo(int id_campo) {
		this.id_campo = id_campo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getTamanho() {
		return tamanho;
	}
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	public boolean isTamanho_fixo() {
		return tamanho_fixo;
	}
	public void setTamanho_fixo(boolean tamanho_fixo) {
		this.tamanho_fixo = tamanho_fixo;
	}
	public boolean isObrigatorio() {
		return obrigatorio;
	}
	public void setObrigatorio(boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}
	public String getTabelaOrigem() {
		return tabelaOrigem;
	}
	public void setTabelaOrigem(String tabelaOrigem) {
		this.tabelaOrigem = tabelaOrigem;
	}
	public boolean isChave() {
		return chave;
	}
	public void setChave(boolean chave) {
		this.chave = chave;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public int getId_tabela() {
		return id_tabela;
	}
	public void setId_tabela(int id_tabela) {
		this.id_tabela = id_tabela;
	}
	

}

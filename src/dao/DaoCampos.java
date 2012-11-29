package dao;

import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.util.Hashtable;
import view.helper;
  
import javax.swing.JOptionPane;  
  
import model.Campos;  
import dao.banco.ConFactory;  
  
public class DaoCampos {  
   
  
   private Connection con;  
   private Statement comando;  
  
   public void apagar(String id) {  
      conectar();  
      try {  
         comando  
               .executeUpdate("DELETE FROM campos WHERE id_campo = '" + id  
                     + "';");  
      } catch (SQLException e) {  
         helper.imprimeErro("Erro ao apagar campo", e.getMessage());  
      } finally {  
         fechar();  
      }  
   }  
  
   public Hashtable<String, Campos> buscarTodos(int idTabela) {
	   conectar();  
	   Hashtable<String, Campos> resultados = new Hashtable<String, Campos>();  
	   ResultSet rs;  
	   try {
		   rs = comando.executeQuery("SELECT * FROM campos where id_tabela = "+idTabela);  
		   while (rs.next()) {  
			   Campos temp = new Campos();           
	           temp.setId_campo(rs.getInt("id_campo"));
	           temp.setId_tabela(rs.getInt("id_tabela"));  
	           temp.setNome(rs.getString("nome"));  
	           temp.setChave(rs.getString("chave"));  
	           temp.setObrigatorio(rs.getString("obrigatorio"));
	           temp.setTabelaOrigem(rs.getString("tabela_origem"));
	           temp.setTipo(rs.getString("tipo"));
	           resultados.put(temp.getNome().toUpperCase(),temp);  
           }  
         return resultados;  
      } catch (SQLException e) {  
         helper.imprimeErro("Erro ao buscar campos", e.getMessage());  
         return null;  
      }  
   }  
  
   public void atualizar(Campos campos) {  
      conectar();  
      String com = "UPDATE campos SET nome = '" + campos.getNome()  
            + ", obrigatorio =" + campos.getObrigatorio()
            + ", id_tabela =" + campos.getId_tabela()
            + ", tabela_origem ='" + campos.getTabelaOrigem()
            + "', chave =" + campos.getChave()
            + ", tipo ='" + campos.getTipo()
            + "' WHERE  id_campo = '" + campos.getId_campo() + "';";  
      System.out.println("Atualizado id: "+campos.getId_campo());  
      try {  
         comando.executeUpdate(com);  
      } catch (SQLException e) {  
         e.printStackTrace();  
      } finally {  
         fechar();  
      }  
   }  
  
   public Campos buscar(String nome, int id_tabela) {  
      conectar();  
     
      ResultSet rs;  
      try {  
    	 
         rs = comando.executeQuery("SELECT * FROM campos WHERE nome = '"  
               + nome + "' AND id_tabela = "+id_tabela+";");          
         if(rs.first()){
        	Campos temp = new Campos();  
        	temp.setId_campo(rs.getInt("id_campo"));
            temp.setId_tabela(rs.getInt("id_tabela"));  
            temp.setNome(rs.getString("nome"));  
            temp.setChave(rs.getString("chave"));  
            temp.setObrigatorio(rs.getString("obrigatorio"));
            temp.setTabelaOrigem(rs.getString("tabela_origem"));
            temp.setTipo(rs.getString("tipo"));
            fechar();
            return temp;  
         }
         return null;
      } catch (SQLException e) {  
         helper.imprimeErro("Erro ao buscar campo", e.getMessage());  
         return null;  
      }  
  
   }  
  
   public void insere(Campos campos){  
      conectar();  
      try {  
         comando.executeUpdate("INSERT INTO campos VALUES("  
               + "null, '"+ campos.getNome() + "', "
        		+ campos.getObrigatorio()+ ", "
        		+ campos.getId_tabela()+ ", '"
        		+ campos.getTabelaOrigem()+ "', "
        		+ campos.getChave()+ ", '"
        		+ campos.getTipo()+ "', '"
        		+ ")");  
         System.out.println("campo "+campos.getId_campo()+" inserido!");  
      } catch (SQLException e) {  
         helper.imprimeErro("Erro ao inserir campo", e.getMessage());  
      } finally {  
         fechar();  
      }  
   }  
  
   private void conectar() {  
     con = ConFactory.getConexao();
     try{
    	 comando = con.createStatement();  
     }catch (SQLException e) {
		System.err.println(e.getMessage());
	}
     //System.out.println("Conectado!");  
       
   }  
  
   private void fechar() {  
      try {  
         comando.close();  
         con.close();  
         //System.out.println("Conex�o Fechada");  
      } catch (SQLException e) {  
         helper.imprimeErro("Erro ao fechar conex�o", e.getMessage());  
      }  
   }  
  
}  
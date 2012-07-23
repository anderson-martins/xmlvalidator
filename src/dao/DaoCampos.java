package dao;

import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.util.Vector;  
  
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
         imprimeErro("Erro ao apagar campo", e.getMessage());  
      } finally {  
         fechar();  
      }  
   }  
  
   public Vector<Campos> buscarTodos() {  
      conectar();  
      Vector<Campos> resultados = new Vector<Campos>();  
      ResultSet rs;  
      try {  
         rs = comando.executeQuery("SELECT * FROM campos");  
         while (rs.next()) {  
            Campos temp = new Campos();  
            
            temp.setId_campo(rs.getInt("id_campo"));
            temp.setId_tabela(rs.getInt("id_tabela"));  
            temp.setNome(rs.getString("nome"));  
            temp.setChave(rs.getBoolean("chave"));  
            temp.setComentario(rs.getString("comentario"));
            temp.setObrigatorio(rs.getBoolean("obrigatorio"));
            temp.setTabelaOrigem(rs.getString("tabela_origem"));
            temp.setTamanho(rs.getInt("tamanho"));
            temp.setTamanho_fixo(rs.getBoolean("tamanho_fixo"));
            temp.setTipo(rs.getString("tipo"));
            
     
            resultados.add(temp);  
         }  
         return resultados;  
      } catch (SQLException e) {  
         imprimeErro("Erro ao buscar campos", e.getMessage());  
         return null;  
      }  
   }  
  
   public void atualizar(Campos campos) {  
      conectar();  
      String com = "UPDATE campos SET nome = '" + campos.getNome()  
            + "', tamanho =" + campos.getTamanho()  
            + "', tamanho_fixo =" + campos.isTamanho_fixo()
            + "', obrigatorio =" + campos.isObrigatorio()
            + "', id_tabela =" + campos.getId_tabela()
            + "', tabela_origem =" + campos.getTabelaOrigem()
            + "', chave =" + campos.isChave()
            + "', tipo =" + campos.getTipo()
            + "', comentario =" + campos.getComentario()
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
  
   public Vector<Campos> buscar(String nome, String id_tabela) {  
      conectar();  
      Vector<Campos> resultados = new Vector<Campos>();  
      ResultSet rs;  
      try {  
         rs = comando.executeQuery("SELECT * FROM campos WHERE nome = '"  
               + nome + "' AND id_tabela = '"+id_tabela+"';");  
         while (rs.next()) {  
            Campos temp = new Campos();  

            temp.setId_campo(rs.getInt("id_campo"));
            temp.setId_tabela(rs.getInt("id_tabela"));  
            temp.setNome(rs.getString("nome"));  
            temp.setChave(rs.getBoolean("chave"));  
            temp.setComentario(rs.getString("comentario"));
            temp.setObrigatorio(rs.getBoolean("obrigatorio"));
            temp.setTabelaOrigem(rs.getString("tabela_origem"));
            temp.setTamanho(rs.getInt("tamanho"));
            temp.setTamanho_fixo(rs.getBoolean("tamanho_fixo"));
            temp.setTipo(rs.getString("tipo"));
            
         }  
         return resultados;  
      } catch (SQLException e) {  
         imprimeErro("Erro ao buscar tabela", e.getMessage());  
         return null;  
      }  
  
   }  
  
   public void insere(Campos campos){  
      conectar();  
      try {  
         comando.executeUpdate("INSERT INTO campos VALUES("  
               + "null, '"+ campos.getNome() + "', '"
        		+ campos.getTamanho()+ "', "
        		+ campos.isTamanho_fixo()+ ", "
        		+ campos.isObrigatorio()+ ", "
        		+ campos.getId_tabela()+ ", '"
        		+ campos.getTabelaOrigem()+ "', "
        		+ campos.isChave()+ ", '"
        		+ campos.getTipo()+ "', '"
        		+ campos.getComentario()+ "', "
        		+ ")");  
         System.out.println("campo "+campos.getId_campo()+" inserido!");  
      } catch (SQLException e) {  
         imprimeErro("Erro ao inserir campo", e.getMessage());  
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
     System.out.println("Conectado!");  
       
   }  
  
   private void fechar() {  
      try {  
         comando.close();  
         con.close();  
         System.out.println("Conexão Fechada");  
      } catch (SQLException e) {  
         imprimeErro("Erro ao fechar conexão", e.getMessage());  
      }  
   }  
  
   private void imprimeErro(String msg, String msgErro) {  
      JOptionPane.showMessageDialog(null, msg, "Erro no banco de dados", 0);  
      System.err.println(msg);  
      System.out.println(msgErro);  
      System.exit(0);  
   }  
}  
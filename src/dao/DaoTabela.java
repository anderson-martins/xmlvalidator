package dao;

import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.util.Vector;  
  
import javax.swing.JOptionPane;  
  
import model.Tabela;  
import dao.banco.ConFactory;  
  
public class DaoTabela {  
   
  
   private Connection con;  
   private Statement comando;  
  
   public void apagar(String id) {  
      conectar();  
      try {  
         comando  
               .executeUpdate("DELETE FROM tabela WHERE id_tabela = '" + id  
                     + "';");  
      } catch (SQLException e) {  
         imprimeErro("Erro ao apagar tabela", e.getMessage());  
      } finally {  
         fechar();  
      }  
   }  
  
   public Vector<Tabela> buscarTodos() {  
      conectar();  
      Vector<Tabela> resultados = new Vector<Tabela>();  
      ResultSet rs;  
      try {  
         rs = comando.executeQuery("SELECT * FROM tabela");  
         while (rs.next()) {  
            Tabela temp = new Tabela();  
            
            temp.setId_tabela(rs.getInt("id_tabela"));  
            temp.setNome(rs.getString("nome"));  
            temp.setLayout(rs.getString("layout"));  
     
            resultados.add(temp);  
         }  
         return resultados;  
      } catch (SQLException e) {  
         imprimeErro("Erro ao buscar tabelas", e.getMessage());  
         return null;  
      }  
   }  
  
   public void atualizar(Tabela tabela) {  
      conectar();  
      String com = "UPDATE tabela SET nome = '" + tabela.getNome()  
            + "', layout =" + tabela.getLayout()  
            + "' WHERE  id_tabela = '" + tabela.getId_tabela() + "';";  
      System.out.println("Atualizado id: "+tabela.getId_tabela());  
      try {  
         comando.executeUpdate(com);  
      } catch (SQLException e) {  
         e.printStackTrace();  
      } finally {  
         fechar();  
      }  
   }  
  
   public Vector<Tabela> buscar(String nome, String layout) {  
      conectar();  
      Vector<Tabela> resultados = new Vector<Tabela>();  
      ResultSet rs;  
      try {  
         rs = comando.executeQuery("SELECT * FROM tabela WHERE nome = '"  
               + nome + "' AND layout = '"+layout+"';");  
         while (rs.next()) {  
            Tabela temp = new Tabela();  
            // pega todos os atributos da tabela 
            temp.setId_tabela(rs.getInt("id_tabela"));  
            temp.setNome(rs.getString("nome"));  
            temp.setLayout(rs.getString("layout"));   
            resultados.add(temp);  
         }  
         return resultados;  
      } catch (SQLException e) {  
         imprimeErro("Erro ao buscar tabela", e.getMessage());  
         return null;  
      }  
  
   }  
  
   public void insere(Tabela tabela){  
      conectar();  
      try {  
         comando.executeUpdate("INSERT INTO tabela VALUES("  
               + "null, '"+ tabela.getNome() + "', '" + tabela.getLayout()+ "')");  
         System.out.println("Inserida!");  
      } catch (SQLException e) {  
         imprimeErro("Erro ao inserir Tabela", e.getMessage());  
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
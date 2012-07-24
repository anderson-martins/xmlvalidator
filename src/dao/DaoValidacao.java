package dao;

import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.util.Vector;  
  
import javax.swing.JOptionPane;  
  
import model.Validacao;  
import dao.banco.ConFactory;  
  
public class DaoValidacao {  
   
  
   private Connection con;  
   private Statement comando;  
  
   public void apagar(String id) {  
      conectar();  
      try {  
         comando  
               .executeUpdate("DELETE FROM validacao WHERE id_validacao = '" + id  
                     + "';");  
      } catch (SQLException e) {  
         imprimeErro("Erro ao apagar validacao", e.getMessage());  
      } finally {  
         fechar();  
      }  
   }  
  
   public Vector<Validacao> buscarTodos() {  
      conectar();  
      Vector<Validacao> resultados = new Vector<Validacao>();  
      ResultSet rs;  
      try {  
         rs = comando.executeQuery("SELECT * FROM validacao");  
         while (rs.next()) {  
            Validacao temp = new Validacao();  
            
            temp.setId_validacao(rs.getInt("id_validacao"));
            temp.setTipoValidacao(rs.getString("tipo_validacao"));  
            temp.setValorFixo(rs.getString("valor_fixo"));  
            temp.setTabelaCondicao(rs.getString("tabela_condicao"));  
            temp.setCampoCondicao(rs.getString("campo_condicao"));
            temp.setOperacaoCondicao(rs.getString("operacao_condicao"));
            temp.setValorCondicao(rs.getString("valor_condicao"));
            temp.setId_campo(rs.getInt("id_campos"));
            
            resultados.add(temp);  
         }  
         return resultados;  
      } catch (SQLException e) {  
         imprimeErro("Erro ao buscar validacao", e.getMessage());  
         return null;  
      }  
   }  
  
   public void atualizar(Validacao validacao) {  
      conectar();  
      String com = "UPDATE validacao SET tipo_validacao = '" + validacao.getTipoValidacao()
            + "', valor_fixo ='" + validacao.getValorFixo()
            + "', tabela_condicao ='" + validacao.getTabelaCondicao()
            + "', campo_condicao ='" + validacao.getCampoCondicao()
            + "', operacao_condicao ='" + validacao.getOperacaoCondicao()
            + "', valor_condicao ='" + validacao.getValorCondicao()
            + "', id_campos =" + validacao.getId_campo()
            + " WHERE  id_validacao = " + validacao.getId_validacao() + ";";  
      System.out.println("Atualizado id: "+validacao.getId_validacao());  
      try {  
         comando.executeUpdate(com);  
      } catch (SQLException e) {  
         e.printStackTrace();  
      } finally {  
         fechar();  
      }  
   }  
  
   public Vector<Validacao> buscar(int idCampos) {  
      conectar();  
      Vector<Validacao> resultados = new Vector<Validacao>();  
      ResultSet rs;  
      try {  
         rs = comando.executeQuery("SELECT * FROM validacao WHERE id_campos = "  
               + idCampos +";");  
         while (rs.next()) {  
            Validacao temp = new Validacao();  

            temp.setId_validacao(rs.getInt("id_validacao"));
            temp.setTipoValidacao(rs.getString("tipo_validacao"));  
            temp.setValorFixo(rs.getString("valor_fixo"));  
            temp.setTabelaCondicao(rs.getString("tabela_condicao"));  
            temp.setCampoCondicao(rs.getString("campo_condicao"));
            temp.setOperacaoCondicao(rs.getString("operacao_condicao"));
            temp.setValorCondicao(rs.getString("valor_condicao"));
            temp.setId_campo(rs.getInt("id_campos"));
            
            resultados.add(temp);
         }  
         return resultados;  
      } catch (SQLException e) {  
         imprimeErro("Erro ao buscar validacao", e.getMessage());  
         return null;  
      }  
  
   }  
  
   public void insere(Validacao validacao){  
      conectar();  
      try {  
         comando.executeUpdate("INSERT INTO validacao VALUES("  
               + "null, '"+ validacao.getTipoValidacao() + "', '"
        		+ validacao.getValorFixo()+ "', '"
        		+ validacao.getTabelaCondicao()+ "', '"
        		+ validacao.getCampoCondicao()+ "', '"
        		+ validacao.getOperacaoCondicao()+ "', '"
        		+ validacao.getValorCondicao()+ "', "
        		+ validacao.getId_campo()+ ""
        		+ ")");  
         System.out.println("validacao "+validacao.getId_validacao()+" inserida!");  
      } catch (SQLException e) {  
         imprimeErro("Erro ao inserir validacao", e.getMessage());  
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
      //System.exit(0);  
   }  
}  
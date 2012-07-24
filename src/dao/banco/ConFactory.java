package dao.banco;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConFactory {
	private static final String STR_DRIVER = "com.mysql.jdbc.Driver";  
    private static final String DATABASE = "xmlvalidator";  
    private static final String IP = "localhost";   
    private static final String STR_CON = "jdbc:mysql://" + IP + ":3306/" + DATABASE;  
    private static final String USER = "root";  
    private static final String PASSWORD = "1";  
    private static Connection con = null; 
    
public static Connection getConexao() {  
        
        try {  
            Class.forName(STR_DRIVER); 
            System.out.println("Obtendo conexao..."); 
            con = DriverManager.getConnection(STR_CON, USER, PASSWORD);  
            System.out.println("Conectado!");  
        }catch(ClassNotFoundException c){
        	System.out.println("Classe não encontrada " + c.getMessage());
        }
        catch (SQLException e) {  
            System.out.println("Erro ao conectar ao banco de dados "+e.getMessage());
              
        }  
        return con;
    }  
  
    public static void fechaConexao(){
    	try{
    		con.close();    		
    	}catch(SQLException e){
    		System.out.println("Erro ao tentar fecha conexão.");
    	}
    	
    }
  
    public static void closeAll(Connection con, Statement stmt, ResultSet rs) {  
        try {  
        	
        	stmt.close();
        	rs.close();
        	con.close();
        }catch (Exception e) {
			System.out.println("Erro ao fechar conexao.");
		}
    }
   /* public static void main(String[] args) {
		getConexao();
		try{
			Statement statement = con.createStatement();
			ResultSet pessoa = statement.executeQuery("select nome from tabela");
			while(pessoa.next()){
				System.out.println("Nome: "+ pessoa.getString("nome"));
			}
		}catch (SQLException sqle) {
			// TODO: handle exception
			System.out.println("Erro: "+ sqle.getMessage());
		}
    	fechaConexao();
	}*/

}

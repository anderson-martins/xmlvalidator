package view;

import javax.swing.JOptionPane;

public class helper {
	
	  public static void imprimeErro(String msg, String msgErro) {  
	      JOptionPane.showMessageDialog(null, msg, "Erro no banco de dados", 0);  
	      System.err.println(msg);  
	      System.out.println(msgErro);  
	      //System.exit(0);  
	   }  

}

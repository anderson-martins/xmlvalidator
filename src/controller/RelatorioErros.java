package controller;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import view.TabelaXML;

import model.Erro;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class RelatorioErros {
	//private Vector<Erro> vectorErros;
	public static void relatorio(LinkedList<TabelaXML> listTabela){
		Vector<Erro> vectorErros = new Vector<Erro>();
		//percorre listTabelas
		for(int k=0;k < listTabela.size();k++){
			//percorre todas as celulas da tabela e valida uma a uma
			for(int i=0; i < listTabela.get(k).getColumnCount(); i++){
				for(int j=0; j < listTabela.get(k).getRowCount(); j++){
					if(listTabela.get(k).getStatus(j, i) != ValidacaoEstrutural.APPROVED){
						vectorErros.add(new Erro(Integer.toString(j+1),
								listTabela.get(k).getStatus(j, i),
								listTabela.get(k).getArquivo().getName(), 
								listTabela.get(k).getColumnName(i)));
					}
				}
			}
			for(String lostField : listTabela.get(k).getLostFields()){
				vectorErros.add(new Erro("#", 
						"O campo "+lostField+" não existe no arquivo XML (Layout fora do padrão)",
						listTabela.get(k).getArquivo().getName(),
						"#"));
			}
		}
		
		
        try {
        	JasperReport report = JasperCompileManager.compileReport("reports/errors.jrxml");
            
        	JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(vectorErros)); // usa como datasourde uma coleção de objetos carregados na memória
            JasperViewer.viewReport(print, false);
          
        } catch (JRException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void relatorioVinculo(LinkedList<TabelaXML> listTabela){
		/* TODO : Varrer todos as tabelas XML no diretorio de validação (c:/aplic) e verificar os campos vinculados de cada tabela criando
		uma nova tabela  e buscar se existe o vinculo.
		*/
		List<File> listArquivos = listDirectoryAppend(new File("c:/aplic"), new LinkedList<File>());
		TabelaXML tempTabela;
		
		for(int k = 0; k < listTabela.size();k++){
			Iterator<Map.Entry<String, String>> it;
			try{
				it = listTabela.get(k).getCamposVinculados().entrySet().iterator();
			}catch (NullPointerException npe) {
				continue;// não existe campos vinculados pula para próxima iteração
			}
			while (it.hasNext()) {
				  Map.Entry<String, String> entry = it.next();
				  //System.out.println("tabela: "+listTabela.get(k).getArquivo().getName()+" Chave: "+entry.getKey()+" valor: "+entry.getValue());		
				  for(int a=0; a< listArquivos.size();a++){
					  
					  if(listArquivos.get(a).getName().replaceAll("(?i).xml", "").equalsIgnoreCase(entry.getValue())){
						  System.out.println("tabela vinculada: "+entry.getValue());
						  try{
							  tempTabela = new TabelaXML(listArquivos.get(a));
						  }catch (Exception e) {
							System.err.println("Erro na criação da tabela xml para o relatorio de vinculos");
						}
					  }
				  }
				  
				  
				  
				  for(int i=0; i < listTabela.get(k).getColumnCount(); i++){
					  if(listTabela.get(k).getColumnName(i).equalsIgnoreCase(entry.getKey())){
						  for(int j=0; j < listTabela.get(k).getRowCount(); j++){
								
							}						  
					  }
					  
					}
				  
			}
		}
	}
	/*
	* Retorna Lista com arquivos xml na pasta "dir" 
	*/
	public static List<File> listDirectoryAppend(File dir, LinkedList<File> lista) {
        if (dir.isDirectory()) {
            String[] filhos = dir.list();
            for (int i = 0; i < filhos.length; i++) {
                File nome = new File(dir, filhos[i]);
                if (nome.isFile() && nome.getName().toLowerCase().endsWith(".xml")) {
                	//System.out.println(nome.getAbsolutePath());
                	lista.add(nome);
                } else if (nome.isDirectory()) {
                	
                    listDirectoryAppend(nome, lista);
                }
                //System.out.println(nome.getAbsolutePath());
                
            }
        } 
        return lista;
    }

}

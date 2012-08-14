package controller;

import java.util.ArrayList;
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
	public static void relatorio(ArrayList<TabelaXML> listTabela){
		Vector<Erro> vectorErros = new Vector<Erro>();
		
		for(int k=0;k < listTabela.size();k++){
			for(int i=0; i < listTabela.get(k).getColumnCount(); i++){
				for(int j=0; j < listTabela.get(k).getRowCount(); j++){
					if(listTabela.get(k).getStatus(j, i) != ValidacaoEstrutural.APPROVED){
						vectorErros.add(new Erro(listTabela.get(k).getStatus(j, i),listTabela.get(k).getStatus(j, i)));
					}
				}
			}
		}
		
		
        try {
        	JasperReport report = JasperCompileManager.compileReport("reports/errors.jrxml");
            
        	JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(vectorErros));
            JasperViewer.viewReport(print, false);
          
        } catch (JRException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}

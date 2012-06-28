import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class TabelaXML extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1684081279215472578L;
	NodeList colunas;
	NodeList linhas;
	Element columnSection;
	Element rowSection;
	JTable table;
	JScrollPane scrollpane;
	File arquivoTabela;
	Document tabela;
	
	public TabelaXML(String documentoXml) throws IOException, ParserConfigurationException, org.xml.sax.SAXException{
		this.arquivoTabela = new File(documentoXml);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		
		DocumentBuilder parser = factory.newDocumentBuilder();
		
		//tabela = parser.parse(documentoXml); //encoding="ISO-8859-1"
		InputStream is = new BufferedInputStream(new FileInputStream(arquivoTabela));
		tabela = parser.parse(new InputSource(new InputStreamReader(is, "ISO-8859-1"))); //gambiarra suprema para ler em um charset diferente da utf-8 que tava dando pau em algumas XML 
		
		colunas = tabela.getElementsByTagName("FIELD");
		linhas = tabela.getElementsByTagName("ROW");
		
		table = new JTable();
		table.setModel(this);
		table.setAutoCreateRowSorter(true);
		scrollpane = new JScrollPane(table);
		
		
				
	}
	
	
	public void setPanelSize(int width, int height){
		scrollpane.setPreferredSize(new Dimension(width,height));
	}
	public JScrollPane getPanel(){
		return this.scrollpane;
	}
	
	//metodos necessários para montagem da tabela, usados internamente quando usado o metodo JTabel.seTModel()
	@Override
	public int getColumnCount() {
		return colunas.getLength();
	}

	@Override
	public int getRowCount() {
		return linhas.getLength();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		rowSection = (Element) linhas.item(rowIndex);
		return rowSection.getAttribute(getColumnName(columnIndex));
	}
	@Override
	public String getColumnName(int column){
		columnSection = (Element) colunas.item(column);
		return columnSection.getAttribute("attrname");
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex){
		return true; // permiti editar celulas
	}
	
	
	 /* -------------------- -----MODO DE USAR ----------------------------
	  * public static void main(String argv[]) throws Exception {
         TabelaXML t = new TabelaXML("PARTICIPANTE_PROC_LICIT_CERTID.xml");
         JTable table = new JTable();
         table.setModel(t);
         JScrollPane scrollpane = new JScrollPane(table);
         JPanel panel = new JPanel();
         panel.add(scrollpane);
         JFrame frame = new JFrame();
         frame.add(panel, "Center");
         frame.pack();
         frame.setVisible(true);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }*/

}

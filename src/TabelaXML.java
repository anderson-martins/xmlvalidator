import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

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
	public TabelaXML(String documentoXml) throws IOException, ParserConfigurationException, org.xml.sax.SAXException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		
		DocumentBuilder parser = factory.newDocumentBuilder();
		
		Document tabela = parser.parse(documentoXml);
		
		colunas = tabela.getElementsByTagName("FIELD");
		linhas = tabela.getElementsByTagName("ROW");
				
	}

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
		return true;
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

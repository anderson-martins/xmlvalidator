import java.awt.Color;
import java.awt.Component;
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

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class TabelaXML extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1684081279215472578L;
	public static final boolean APPROVED = true;
	public static final boolean REJECTED = false;
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
		
		
		InputStream is = new BufferedInputStream(new FileInputStream(arquivoTabela));
		tabela = parser.parse(new InputSource(new InputStreamReader(is, "ISO-8859-1"))); //gambiarra suprema para ler em um charset diferente da utf-8 que tava dando pau em algumas XML 
		
		colunas = tabela.getElementsByTagName("FIELD");
		linhas = tabela.getElementsByTagName("ROW");
		
		table = new JTable();
		table.setModel(this);
		table.setAutoCreateRowSorter(true);
		scrollpane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setLarguraMaximaColuna();
		table.getColumnModel().getColumn(1).setCellRenderer(new StatusColumnCellRenderer());
			
	}
	
	
	public void setPanelSize(int width, int height){
		scrollpane.setPreferredSize(new Dimension(width,height));
	}
	public JScrollPane getPanel(){
		return this.scrollpane;
	}
	public File getArquivo(){
		return this.arquivoTabela;
	}
	public Document getDocument(){
		return this.tabela;
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
	@Override
	public void setValueAt(Object valor, int row, int column){
		rowSection = (Element) linhas.item(row);	
		rowSection.setAttribute(getColumnName(column),(String) valor);
		fireTableCellUpdated(row, column); //avisa o listener
	}
	
	public void setLarguraMaximaColuna(){
        //Ajusta largura das colunas
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
        //DefaultTableCellRenderer dtcr;
        for (int c = 0; c < table.getColumnCount(); c++) {
            int width = 0;

            // Obtém a largura do cabeçalho da coluna
            TableCellRenderer renderer = colModel.getColumn(c).getHeaderRenderer();
            if (renderer == null) {
                renderer = table.getTableHeader().getDefaultRenderer();
            }
            Component comp = renderer.getTableCellRendererComponent(table, colModel.getColumn(c).getHeaderValue(), false, false, 0, 0);
            width = comp.getPreferredSize().width;

            // Obtém a largura máxima da coluna de dados
            for (int r = 0; r < table.getRowCount(); r++) {
                renderer = table.getCellRenderer(r, c);
                comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, c), false, false, r, c);
                width = Math.max(width, comp.getPreferredSize().width);
                
                if (r == 1) {
                    //Alinha conteúdo da célula
                     //dtcr = new DefaultTableCellRenderer();
                     //colModel.getColumn(c).setCellRenderer(ViewDataFormatAlign((table.getModel()).getValueAt(r, c), dtcr));
                }
            }

            width += 2 * 2;

            // Configura a largura
            colModel.getColumn(c).setPreferredWidth(width);
        }

	}
	// TODO: implementar validacao de campos
	public boolean getStatus(int row, int column){
		if (getValueAt(row, column).toString().length() != 2)
			return REJECTED;
		return APPROVED;
	}
	
	/*public void celulaVermelha(int row, int column){
		TableCellRenderer tcr = table.getCellRenderer(row, column);
		Component cell = tcr.getTableCellRendererComponent(table, table.getValueAt(row, column), false, false, row, column);
		cell.setBackground(Color.RED);
		
		
	}*/
	
	@SuppressWarnings("serial")
	public class StatusColumnCellRenderer extends DefaultTableCellRenderer {
		  @Override
		  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

		    //Cells are by default rendered as a JLabel.
		    JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		    //Get the status for the current row.
		    TabelaXML tableModel = (TabelaXML) table.getModel();
		    if(isSelected && tableModel.getStatus(row, col) == TabelaXML.APPROVED){
		    	l.setBackground(new Color(0,100,160));
		    }
		    else if (tableModel.getStatus(row, col) == TabelaXML.REJECTED) {
		      l.setBackground(Color.RED);
		    } else {
		    	if(row%2 == 0){
		    		l.setBackground(new Color(242,242,242));
		    	} else{
		    		l.setBackground(Color.WHITE);
		    	}
		      
		    }

		  //Return the JLabel which renders the cell.
		  return l;

		}
	
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

import java.io.IOException;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class TabelaXML {
	public TabelaXML(String documentoXml) throws IOException, ParserConfigurationException, org.xml.sax.SAXException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		
		DocumentBuilder parser = factory.newDocumentBuilder();
		
		Document tabela = parser.parse(documentoXml);
		
	}

}

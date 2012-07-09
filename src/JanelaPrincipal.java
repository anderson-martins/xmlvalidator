import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static java.awt.event.InputEvent.*;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;





@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame {
	private static ResourceBundle resources;
	
	//gui components
	private Container content = getContentPane();
	private JMenuBar menuBar = new JMenuBar();
	private JToolBar toolBar = new JToolBar();
	private JPanel propriedades = new JPanel();
	private URL url;
	private JTabbedPane abas;
	private TabelaXML tabela;
	final private int PROPORCAO_TELA = 7;
	JPanel painelAbas;
	protected boolean alterado;
	protected Hashtable<String, TabelaXML> hTabelas;
	
	public  void createWindow(){
						
		GraphicsEnvironment localGE = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle center = localGE.getMaximumWindowBounds(); // tamanho maximo
		
		hTabelas = new Hashtable<String, TabelaXML>();
		url = null;
		setBounds(center);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setJMenuBar(menuBar);
		
		//Resources
		try {
			resources = ResourceBundle.getBundle("resources.JanelaPrincipal",Locale.getDefault());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		setTitle("Validador XML");
		
		//************************* Menu **********************************
		//arquivo
		JMenu menuArquivo = new JMenu("Arquivo");
		JMenuItem menuArquivoNovo = new JMenuItem("Novo");
		
		JMenuItem menuArquivoAbrir = new JMenuItem("Abrir");
		menuArquivoAbrir.addActionListener(new OpenAction());
		
		JMenuItem menuArquivoSalvar = new JMenuItem("Salvar");
		menuArquivoSalvar.addActionListener(new SaveAction());
		
		JMenuItem menuArquivoSair = new JMenuItem("Sair");
		menuArquivoSair.addActionListener(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
				
			}
		});
		
		menuArquivo.add(menuArquivoNovo);
		menuArquivo.add(menuArquivoAbrir);
		menuArquivo.add(menuArquivoSalvar);
		menuArquivo.addSeparator();
		menuArquivo.add(menuArquivoSair);
		menuArquivo.setMnemonic('A');
		menuArquivoSalvar.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_DOWN_MASK));
		menuBar.add(menuArquivo);
		
		//editar
		JMenu menuEditar = new JMenu("Editar");
		JMenuItem menuEditarCopiar = new JMenuItem("Copiar");
		JMenuItem menuEditarColar = new JMenuItem("Colar");
		JMenuItem menuEditarLocalizar = new JMenuItem("Localizar");
		menuEditar.add(menuEditarCopiar);
		menuEditar.add(menuEditarColar);
		menuEditar.add(menuEditarLocalizar);
		menuEditar.setMnemonic('E');
		menuBar.add(menuEditar);
		
		//validar
		
		JMenu menuValidar = new JMenu("Validar");
		url = getResource("validarImage");
		JMenuItem menuValidarValidar = new  JMenuItem("Validar");
		if (url != null) {
			menuValidarValidar.setHorizontalTextPosition(JButton.RIGHT);
			menuValidarValidar.setIcon(new ImageIcon(url));
		}
		JMenuItem menuValidarEstrutura = new JMenuItem("Estrutura do arquivo");
		JMenuItem menuValidarVinculos = new JMenuItem("Vinculos");
		menuValidar.add(menuValidarValidar);
		menuValidar.add(menuValidarEstrutura);
		menuValidar.add(menuValidarVinculos);
		menuValidar.setMnemonic('V');
		menuBar.add(menuValidar);
		
		//relatorios
		JMenu menuRelatorios = new JMenu("Relatorios");
		menuRelatorios.setMnemonic('R');
		menuBar.add(menuRelatorios);
		
		//********************************* fim menu ************************************
				
		//janela
		JButton btValida = new JButton(new ImageIcon(url));
		toolBar.add(btValida);
		
		//status
		JLabel statusBar = new JLabel("Validação de arquivos XML");
		statusBar.setBorder(new EmptyBorder(4,4,4,4));
		statusBar.setHorizontalAlignment(JLabel.LEADING);

		// JPanel propriedades
		propriedades.setMinimumSize(new Dimension(content.getWidth()/PROPORCAO_TELA,content.getHeight()/PROPORCAO_TELA));
		JLabel lbPropriedades = new JLabel("Arquivos");
		lbPropriedades.setHorizontalAlignment(JLabel.LEADING);
		propriedades.add(lbPropriedades,BorderLayout.NORTH);
				
		// areadas talbelas		
		abas = new JTabbedPane(JTabbedPane.TOP);
		abas.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		
		
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, propriedades, abas);
		
		// add
		content.setLayout(new BorderLayout());
		content.add(toolBar,BorderLayout.NORTH);
		content.add(statusBar, BorderLayout.SOUTH);
		content.add(splitPane, BorderLayout.CENTER);
		
		// altera LookAndFeel
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); //tenta setar o L&F
			javax.swing.SwingUtilities.updateComponentTreeUI(this); 
		}catch(Exception e){
			try{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); //L&F Default
				javax.swing.SwingUtilities.updateComponentTreeUI(this);
			}catch (Exception ex) {
				System.err.println("Look And Feel nao encontrado!");
			}
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new JanelaPrincipal().createWindow();
			}
		});
		/*UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		for(UIManager.LookAndFeelInfo look : looks){
			System.out.println(look.getClassName());
		}*/
		

	}
	public static boolean usingNimbus() {
		return UIManager.getLookAndFeel().getName().equals("Nimbus");	
	}
	 protected URL getResource(String key) {
		String name = getResourceString(key);
		if (name != null) {
		    URL url = getClass().getResource(name);
		    return url;
		}
		return null;
	}
	 
	 protected String getResourceString(String nm) {
		String str;
		try {
		    str = resources.getString(nm);
		} catch (MissingResourceException mre) {
		    str = null;
		}
		return str;
	}
	 //implementado pela oracle ButtonTabComponent
	 protected void initTabComponent(int i) {
	        abas.setTabComponentAt(i,
	                 new ButtonTabComponent(abas));	    
	 }


	class OpenAction extends AbstractAction {
		ExtensionFilter extensionFilter = new ExtensionFilter("xml", "Arquivos XML (Tabela APLIC)");
		OpenAction() {
		    super("abrir");
		}

	        public void actionPerformed(ActionEvent e) {
		    
	            JFileChooser chooser = new JFileChooser("c:/aplic"); //se nao abrir nesse diretorio abrirá no default
	            chooser.addChoosableFileFilter(extensionFilter);
	            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	            chooser.setFileFilter(extensionFilter);
	            chooser.rescanCurrentDirectory();
	            
	            int ret = chooser.showOpenDialog(getContentPane());

	            if (ret != JFileChooser.APPROVE_OPTION) {
			return;
		    }

	            File f = chooser.getSelectedFile();
		    if (f.isFile() && f.canRead() && f.getName().toLowerCase().endsWith("xml")) {
		    	
		    	try {
					tabela = new TabelaXML(f.getAbsolutePath());
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
		    	painelAbas = new JPanel();
				painelAbas.setMinimumSize(new Dimension((content.getWidth()/PROPORCAO_TELA )*4,content.getHeight()/PROPORCAO_TELA));
				painelAbas.setLayout(new BorderLayout());
				
				abas.add(f.getName(), painelAbas);
				
				painelAbas.add(tabela.getPanel());
		    	
				alterado = false;
				
				initTabComponent(abas.getTabCount()-1); //insere botao fechar na aba
				abas.setSelectedIndex(abas.getTabCount()-1); // seleciona a aba criada
		    	tabela.addTableModelListener(new EditTabela());
		    			    			    	
		    	hTabelas.put(f.getName(), tabela);
			
		    } else {
	                JOptionPane.showMessageDialog(getContentPane(),
	                        "Não foi possível abrir a tabela XML ou não é um documento válido: " + f,
	                        "Erro ao abrir arquivo",
	                        JOptionPane.ERROR_MESSAGE);
		    
		    }
	        }
	    }
	class EditTabela implements TableModelListener{
		

		
		public void tableChanged(TableModelEvent ev) {
			if (!alterado){
				abas.setTitleAt(abas.getSelectedIndex(), "*"+abas.getTitleAt(abas.getSelectedIndex()));
				alterado = true;
			}
			
		}

		
	}
	class SaveAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			
			if(alterado){
				abas.setTitleAt(abas.getSelectedIndex(), abas.getTitleAt(abas.getSelectedIndex()).replace("*", ""));
				alterado = false;
			}
			TabelaXML t = hTabelas.get(abas.getTitleAt(abas.getSelectedIndex()));
			
			try{
				DOMSource source = new DOMSource(t.getDocument());
				StreamResult result = new StreamResult(new FileOutputStream(t.getArquivo()));
				TransformerFactory transFactory = TransformerFactory.newInstance();
				Transformer transformer = transFactory.newTransformer();
				transformer.transform(source, result);			
			}catch (Exception ex) {
				JOptionPane.showMessageDialog(getContentPane(),
                        "Não foi possível salvar a tabela XML: "+t.getArquivo(),
                        "Erro ao abrir arquivo",
                        JOptionPane.ERROR_MESSAGE);
			
			}
			
		}
	}

}
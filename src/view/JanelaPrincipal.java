package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.LinkedList;
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
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import view.TabelaXML;
import view.Preferencias;

import controller.MacAddress;
import controller.RelatorioErros;

@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame {
	private static ResourceBundle resources; 
	
	//gui components
	private Container content = getContentPane();
	private JMenuBar menuBar = new JMenuBar();
	private JToolBar toolBar = new JToolBar();
	private JScrollPane propriedades;
	private URL url;
	private JTabbedPane abas;
	private TabelaXML tabela;
	final private int PROPORCAO_TELA = 7;
	JPanel painelAbas;
	protected boolean alterado;
	protected Hashtable<String, TabelaXML> hTabelas;
	JTree fileTree;
	FileSystemModel fileSystemModel;
	protected JButton btRelatorioErros;
	protected JButton btRelatorioVinculacaoErros;
	
	
	public  void createWindow(){
		try{
			//System.out.println(MacAddress.getMacAddsess());				
		}catch (Exception e) {
			System.out.println("Nï¿½o foi possï¿½vel ler o MAC ADDRESS: " + e.getMessage());
		}
		
		hTabelas = new Hashtable<String, TabelaXML>();
		url = null;
		setExtendedState(MAXIMIZED_BOTH);// maximiza a janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setJMenuBar(menuBar);
		
		
		//Resources
		try {
			resources = ResourceBundle.getBundle("view.resources.JanelaPrincipal",Locale.getDefault());
		} catch (Exception e) {
			System.err.println("erro - Resource "+e.getMessage());
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
		
		JMenu menuArquivoLayout = new JMenu("Layout");
		JMenuItem menuArquivoLayoutTabela = new JMenuItem("Tabela");
		JMenuItem menuArquivoLayoutCampos = new JMenuItem("Campos");
		JMenuItem menuArquivoLayoutValidacao = new JMenuItem("Validaï¿½ï¿½o");
		menuArquivoLayout.add(menuArquivoLayoutTabela);
		menuArquivoLayout.add(menuArquivoLayoutCampos);
		menuArquivoLayout.add(menuArquivoLayoutValidacao);
		
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
		menuArquivo.add(menuArquivoLayout);
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
		JMenuItem menuEditarPreferencias = new JMenuItem("PreferÃªncias");
		
		// actions for Edit Menu::
		menuEditarPreferencias.addActionListener(new OpenPreferencias());
		
		// add itens
		
		menuEditar.add(menuEditarCopiar);
		menuEditar.add(menuEditarColar);
		menuEditar.add(menuEditarLocalizar);
		menuEditar.addSeparator();
		menuEditar.add(menuEditarPreferencias);
		
		menuEditar.setMnemonic('E');
		menuBar.add(menuEditar);
		
		
		
		//validar
		
		JMenu menuValidar = new JMenu("Validar");
		url = getResource("validarImage");
		JMenuItem menuValidarValidar = new  JMenuItem("Validar");
		menuValidarValidar.addActionListener(new ValidateAction());
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
		
		//********************************* toolbar ************************************
			
		JButton newFile = new JButton(new ImageIcon(getResource("newImage")));
		JButton openFile = new JButton(new ImageIcon(getResource("openImage")));
		openFile.addActionListener(new OpenAction());
		JButton saveFile = new JButton(new ImageIcon(getResource("saveImage")));
		saveFile.addActionListener(new SaveAction());
		JButton btValida = new JButton(new ImageIcon(getResource("validarImage")));
		btValida.addActionListener(new ValidateAction());
		JButton btStop = new JButton(new ImageIcon(getResource("stopImage")));
		btStop.addActionListener(new StopValidateAction());
		btRelatorioErros = new JButton(new ImageIcon(getResource("relatorioErros")));
		btRelatorioErros.addActionListener(new RelatorioAction());
		btRelatorioVinculacaoErros = new JButton(new ImageIcon(getResource("relatorioVinculos")));
		btRelatorioVinculacaoErros.addActionListener(new RelatorioAction());
		
		toolBar.add(newFile);
		toolBar.add(openFile);
		toolBar.add(saveFile);
		toolBar.addSeparator();// -----
		toolBar.add(btValida);
		toolBar.add(btStop);
		toolBar.addSeparator();// -----
		toolBar.add(btRelatorioErros);
		toolBar.add(btRelatorioVinculacaoErros);
		
		
		//status
		JLabel statusBar = new JLabel("Validaï¿½ï¿½o de arquivos XML");
		statusBar.setBorder(new EmptyBorder(4,4,4,4));
		statusBar.setHorizontalAlignment(JLabel.LEADING);

		// JPanel propriedades
		
		fileSystemModel = new FileSystemModel(new File("c:\\aplic"));
		fileTree = new JTree(fileSystemModel);
		fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		fileTree.addMouseListener(new MouseAdapter() {
		
		      public void mouseClicked(MouseEvent event) {
		        File file = (File) fileTree.getLastSelectedPathComponent();
		        if (file != null){
			        if (file.getName().toLowerCase().endsWith("xml")){
			        	if( event.getClickCount() == 2)
			        	abreAbaXML(file);
			        }
		        }
		        	
		      }
		    });
		fileTree.setAlignmentX(LEFT_ALIGNMENT);
		fileTree.setBackground(new Color(215,215,225));
		//fileTree.setCellRenderer(new TreeRenderer()); 
		
		/* Key listener ao teclar enter o filetree abre a tabela XML */
		fileTree.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					File file = (File) fileTree.getLastSelectedPathComponent();
			        if (file.getName().toLowerCase().endsWith("xml"))
			        	abreAbaXML(file);
				}
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				
				
			}
		});

		propriedades = new JScrollPane(fileTree,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);			
		propriedades.setMinimumSize(new Dimension(content.getWidth()/PROPORCAO_TELA,content.getHeight()/PROPORCAO_TELA));
		
		// area das talbelas		
		abas = new JTabbedPane(JTabbedPane.TOP);
		abas.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);		
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, propriedades, abas);
		splitPane.setOneTouchExpandable(true);
		
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
		//cria uma Thread para abrir janelas, isso impede um dead lock
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new JanelaPrincipal().createWindow();
			}
		});
		/*UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		for(UIManager.LookAndFeelInfo look : looks){
			System.out.println(look.getClassName());
		}*/
		//RelatorioErros.listDirectoryAppend(new File("c:/aplic"), new LinkedList<File>());
		

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
		    
	            JFileChooser chooser = new JFileChooser("c:/aplic"); //se nao abrir nesse diretorio abrirï¿½ no default
	            chooser.addChoosableFileFilter(extensionFilter);
	            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	            chooser.setFileFilter(extensionFilter);
	            chooser.rescanCurrentDirectory();
	            
	            int ret = chooser.showOpenDialog(getContentPane());

	            if (ret != JFileChooser.APPROVE_OPTION) {
	            	return;
	            }

	            File f = chooser.getSelectedFile();
	            abreAbaXML(f);
	        }
	    }
	
	
	public void abreAbaXML(File f){
		if (f.isFile() && f.canRead() && f.getName().toLowerCase().endsWith("xml")) {
	    	if(hTabelas.get(f.getName()) != null){ 
	    		for(int i=0; i < abas.getTabCount(); i++){
	    			if(abas.getTitleAt(i).equals(f.getName())){
	    				abas.setSelectedIndex(i); // se jï¿½ existe na hTabelas seleciona o indice
	    				return;
	    			}
	    		}
	    	}
	    		
	    	try {
				tabela = new TabelaXML(f.getAbsolutePath());
			} catch (Exception ex) {
				System.err.println("Erro - abrir arquivo " +ex.getMessage());
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
                        "Nï¿½o foi possï¿½vel abrir a tabela XML ou nï¿½o ï¿½ um documento vï¿½lido: " + f,
                        "Erro ao abrir arquivo",
                        JOptionPane.ERROR_MESSAGE);
	    
	    }
	}
	
	class EditTabela implements TableModelListener{
		

		@Override
		public void tableChanged(TableModelEvent ev) {
			// o metodo getLastRow retorna 2147483647 qndo indica possível mudança em toda tabela equivale a capacidade do int 2³¹
			if(ev.getLastRow() != 2147483647 && !abas.getTitleAt(abas.getSelectedIndex()).contains("*"))				
				abas.setTitleAt(abas.getSelectedIndex(), "*"+abas.getTitleAt(abas.getSelectedIndex()));		
		}
				
	}
	class SaveAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			
				abas.setTitleAt(abas.getSelectedIndex(), abas.getTitleAt(abas.getSelectedIndex()).replaceAll("\\*?", "")); //substitui todos os * por nada
				alterado = false;
				
			TabelaXML t = hTabelas.get(abas.getTitleAt(abas.getSelectedIndex()).replaceAll("\\*?", ""));
			
			try{
				DOMSource source = new DOMSource(t.getDocument());
				StreamResult result = new StreamResult(new FileOutputStream(t.getArquivo()));
				TransformerFactory transFactory = TransformerFactory.newInstance();
				Transformer transformer = transFactory.newTransformer();
				transformer.transform(source, result);			
			}catch (Exception ex) {
				JOptionPane.showMessageDialog(getContentPane(),
                        "Nï¿½o foi possï¿½vel salvar a tabela XML: "+t.getArquivo(),
                        "Erro ao abrir arquivo",
                        JOptionPane.ERROR_MESSAGE);
			
			}
			
		}
	}
	class ValidateAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){	
			try{
				hTabelas.get(abas.getTitleAt(abas.getSelectedIndex()).replaceAll("\\*?", "")).fireTableValidation();
			}catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("não existe aba selecionada");
			}
			
		}
	}
	class StopValidateAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			try{
				hTabelas.get(abas.getTitleAt(abas.getSelectedIndex()).replaceAll("\\*?", "")).stopTableValidation();
			}catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("não existe aba selecionada");
			}
		}
	}
	class RelatorioAction extends AbstractAction{
		
		public void actionPerformed(ActionEvent e){	
			content.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			LinkedList<TabelaXML> listTabelaXML = new LinkedList<TabelaXML>();
			File diretorio = new File("c:/aplic");
			File arquivos[] = diretorio.listFiles();
			for(int i=0;i < arquivos.length;i++){
				if(arquivos[i].getName().toLowerCase().endsWith(".xml")){
					File arquivo = arquivos[i];
					try{
						listTabelaXML.add(new TabelaXML(arquivo.getAbsolutePath()));
					}catch (Exception ex) {
						System.out.println("erro ao gerar relatï¿½rio");
					}
				}
			}
			if(e.getSource() == btRelatorioErros)
				RelatorioErros.relatorio(listTabelaXML);
			else if(e.getSource() == btRelatorioVinculacaoErros)
				RelatorioErros.relatorioVinculo(listTabelaXML);		
			content.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	class OpenPreferencias extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			
			Preferencias.Open();
			
		}
	}
	
}
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
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
	
	public  void createWindow(){
						
		GraphicsEnvironment localGE = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle center = localGE.getMaximumWindowBounds(); // tamanho maximo
		
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
		JMenuItem menuArquivoSair = new JMenuItem("Sair");
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
		JLabel statusBar = new JLabel("Valida��o de arquivos XML");
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new JanelaPrincipal().createWindow();
			}
		});
		UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		for(UIManager.LookAndFeelInfo look : looks){
			System.out.println(look.getClassName());
		}
		

	}
	class OpenAction extends AbstractAction {
		ExtensionFilter extensionFilter = new ExtensionFilter("xml", "Arquivos XML (Tabela APLIC)");
		OpenAction() {
		    super("abrir");
		}

	        public void actionPerformed(ActionEvent e) {
		    
	            JFileChooser chooser = new JFileChooser("c:/aplic");
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
		    	
				initTabComponent(abas.getTabCount()-1); //insere botao fechar na aba
				abas.setSelectedIndex(abas.getTabCount()-1); // seleciona a aba criada
		    	
			
		    } else {
	                JOptionPane.showMessageDialog(getContentPane(),
	                        "N�o foi poss�vel abrir a tabela XML ou n�o � um documento v�lido: " + f,
	                        "Erro ao abrir arquivo",
	                        JOptionPane.ERROR_MESSAGE);
		    }
		}
	    }

}
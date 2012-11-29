package view;

import java.awt.*;  
import javax.swing.*;
import javax.swing.tree.*;

public class Preferencias extends JPanel {
	
	public static void Open()
	{
		
		JDialog frame = new JDialog();
		frame.setMinimumSize(new Dimension(600, 460));
		frame.setLocationRelativeTo(null);
		frame.setTitle("Preferências");
		frame.setModal(true);
		
		// panel ------------------------------------------------------------------------------------------------------
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		

		// menus ------------------------------------------------------------------------------------------------------
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Root");
		DefaultMutableTreeNode mnopt1 = new DefaultMutableTreeNode("Geral");
		DefaultMutableTreeNode mnopt2 = new DefaultMutableTreeNode("Layouts");
		
		top.add(mnopt1);
		top.add(mnopt2);
		
		JTree tree = new JTree(top);
		    
		tree.setRootVisible(false);
		
		// treeView ---------------------------------------------------------------------------------------------------  
		JScrollPane treeView = new JScrollPane(tree);
		treeView.setPreferredSize(new Dimension(160, 460));
		treeView.setAlignmentX(Component.LEFT_ALIGNMENT);
		treeView.setBackground(new Color(241,241,241));
		    
		
		panel.add(treeView);
		
		frame.add(panel);
				
		// por último
		frame.pack();
		frame.setVisible(true);
		
	}


}

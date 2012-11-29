package view;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

class TreeRenderer extends DefaultTreeCellRenderer {
   

    public TreeRenderer() {
        super();
       
    }

    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {

        super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);
        if (leaf ) {
            //setIcon();
        	 System.out.println(value);
        } else {
           
        }
        

        return this;
    }
}
package views.paneles;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import views.tablas.ModeloTabla;

public class PanelTabla<T> extends JPanel {
	 private ModeloTabla<T> modelo;
	 public PanelTabla(String bordeId, ModeloTabla<T> modelo){
		 this.setLayout(new GridLayout(1,1));
		 this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), bordeId));
		 
		 this.modelo = modelo;
		 JTable tabla = new JTable(this.modelo) {
			 public boolean isCellEditable(int rowIndex, int colIndex) {
				 return false;
			 }
		 };
		 this.add(new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	 }
	}

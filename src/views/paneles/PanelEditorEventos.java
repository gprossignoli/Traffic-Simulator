package views.paneles;

import views.VentanaPrincipal;

public class PanelEditorEventos extends PanelAreaTexto{
	public PanelEditorEventos(String titulo, String texto, boolean editable, VentanaPrincipal mainWindow) {
			 super(titulo,editable);
			 this.setTexto(texto);
			 
			 
			 
			/*
			 // OPCIONAL
			 PopUpMenu popUp = new PopUpMenu(mainWindow);
			 this.areatexto.add(popUp);
			 this.areatexto.addMouseListener(new MouseListener() {
			// ...
			 @Override
			 public void mousePressed(MouseEvent e) {
			 if (e.isPopupTrigger() && areatexto.isEnabled())
			 popUp.show(e.getComponent(), e.getX(), e.getY());
			 }
			 @Override
			 public void mouseReleased(MouseEvent e) {...}
			 });*/
	}
}

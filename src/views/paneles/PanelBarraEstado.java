package views.paneles;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.Controlador;
import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.ObservadorSimuladorTrafico;



public class PanelBarraEstado extends JPanel implements ObservadorSimuladorTrafico {
	private JLabel infoEjecucion;
	public PanelBarraEstado(String mensaje, Controlador controlador) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.infoEjecucion = new JLabel(mensaje);
		this.add(this.infoEjecucion);
		this.setBorder(BorderFactory.createBevelBorder(1));
		controlador.addObserver(this);
	}
	public void setMensaje(String mensaje) {this.infoEjecucion.setText(mensaje);} // la ventana principal se

	
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.infoEjecucion.setText("Paso: " + (tiempo + 1) + " del Simulador");
	}
	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.infoEjecucion.setText("Evento a�adido al simulador");
	}
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos){
		this.infoEjecucion.setText("Simulacion reiniciada durante el paso " +  tiempo);
	}
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		this.infoEjecucion.setText(e.getMessage());
	}
}
package views.paneles;

import java.util.List;

import control.Controlador;
import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.ObservadorSimuladorTrafico;

//OBSERVADOR DEL MODELO
public class PanelInformes extends PanelAreaTexto implements ObservadorSimuladorTrafico {
	public PanelInformes(String titulo, boolean editable, Controlador ctrl) {
		super(titulo, editable);
		ctrl.addObserver(this); // se añade como observador
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.areatexto.append(mapa.generateReport(tiempo));
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos)	{}
}
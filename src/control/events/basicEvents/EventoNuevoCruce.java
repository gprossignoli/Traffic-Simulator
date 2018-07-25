package control.events.basicEvents;

import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.cruces.Cruce;
import model.cruces.CruceGenerico;

public class EventoNuevoCruce extends Evento {
	protected static final String nombreEvento = "Nuevo Cruce";
	 protected String id;
	 public EventoNuevoCruce(int time, String id) {
		 super(time);
		 this.id = id;
	 }
	 @Override
	 public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		 CruceGenerico<?> nuevo = new Cruce(id);
		try { mapa.addCruce(id, nuevo);}
		catch (ErrorDeSimulacion e) {throw e;}
	 // crea el cruce y se lo a�ade al mapa
	 }
	@Override
	public String getNombreEvento() {
		return nombreEvento;
	}

}
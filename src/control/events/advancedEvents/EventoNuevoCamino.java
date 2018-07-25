package control.events.advancedEvents;

import control.events.basicEvents.EventoNuevaCarretera;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.carreteras.Camino;
import model.carreteras.Carretera;
import model.cruces.CruceGenerico;


public class EventoNuevoCamino extends EventoNuevaCarretera{

	public EventoNuevoCamino(int tiempo, String id, String origen, String destino,
		int velocidadMaxima, int longitud) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);

	}
	@Override
	 public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		try {super.ejecuta(mapa);}
		catch (ErrorDeSimulacion e) {throw e; }
	 }
	 
	protected Carretera creaCarretera(CruceGenerico<?> cOrigen, CruceGenerico<?> cDestino){
		return new Camino(id, longitud, velocidadMaxima, cOrigen, cDestino);
	}
	
}


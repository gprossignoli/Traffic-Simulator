package control.events.advancedEvents;

import control.events.basicEvents.EventoNuevaCarretera;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.carreteras.Autopista;
import model.carreteras.Carretera;
import model.cruces.CruceGenerico;


public class EventoNuevaAutopista extends EventoNuevaCarretera{
	//protected String type;
	protected Integer carriles;
	
	public EventoNuevaAutopista(int tiempo, String id, String origen, String destino, int velocidadMaxima,
		int longitud,int lanes) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);

		//this.type = type;
		this.carriles = lanes;
	}
	
	@Override
	 public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		try {super.ejecuta(mapa);}
		catch (ErrorDeSimulacion e) {throw e;}
	 }
	
	protected Carretera creaCarretera(CruceGenerico<?> cOrigen, CruceGenerico<?> cDestino){
		return new Autopista(id, longitud, velocidadMaxima, cOrigen, cDestino, carriles);
	}
	
}

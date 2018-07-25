package control.events.advancedEvents;

import control.events.basicEvents.EventoNuevoCruce;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.cruces.CruceCongestionado;
import model.cruces.CruceGenerico;

public class EventoNuevoCruceCongestionado extends EventoNuevoCruce{
	
	public EventoNuevoCruceCongestionado(int time, String id) {
		super(time, id);
		// TODO Auto-generated constructor stub
	}

	 @Override
	 public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		 CruceGenerico<?> nuevo = new CruceCongestionado(id);
		try { mapa.addCruce(id, nuevo);}
		catch (ErrorDeSimulacion e) {throw e;}
	 // crea el cruce y se lo a�ade al mapa
	 }
	 
	protected CruceCongestionado creaCruce(){
		return new CruceCongestionado(this.id);
	}

}

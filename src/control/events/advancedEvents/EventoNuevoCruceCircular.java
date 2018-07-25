package control.events.advancedEvents;

import control.events.basicEvents.EventoNuevoCruce;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.cruces.CruceCircular;
import model.cruces.CruceGenerico;

public class EventoNuevoCruceCircular extends EventoNuevoCruce {

	protected int maxValorIntervalo;
	protected int minValorIntervalo;
	
	public EventoNuevoCruceCircular(int time, String id,int maxValor, int minValor) {
		super(time, id);
		maxValorIntervalo = maxValor;
		minValorIntervalo = minValor;
	}

	 @Override
	 public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		 CruceGenerico<?> nuevo = new CruceCircular(id,maxValorIntervalo,minValorIntervalo);
		try { mapa.addCruce(id, nuevo);}
		catch (ErrorDeSimulacion e) {throw e;}
	 // crea el cruce y se lo a�ade al mapa
	 }
	 
	protected CruceCircular creaCruce(){
		return new CruceCircular(id,maxValorIntervalo,minValorIntervalo);
	}

}

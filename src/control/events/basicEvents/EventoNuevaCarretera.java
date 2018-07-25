
package control.events.basicEvents;

import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.carreteras.Carretera;
import model.cruces.CruceGenerico;

public class EventoNuevaCarretera extends Evento {
	 protected static final String nombreEvento = "Nueva Carretera";
	 protected String id;
	 protected Integer velocidadMaxima;
	 protected Integer longitud;
	 protected String cruceOrigenId;
	 protected String cruceDestinoId;
	 public EventoNuevaCarretera(int tiempo, String id, String origen,String destino, int velocidadMaxima, int longitud) {
		 super(tiempo);
		 this.id = id;
		 this.velocidadMaxima = velocidadMaxima;
		 this.longitud = longitud;
		 this.cruceOrigenId = origen;
		 this.cruceDestinoId = destino;
	 }
	 @Override
	 public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		CruceGenerico<?> cOrigen =  mapa.getCruce(cruceOrigenId);
		CruceGenerico<?> cDestino = mapa.getCruce(cruceDestinoId);
		if(cOrigen.getId().equals(cDestino.getId()))
			throw new ErrorDeSimulacion("Cruce origen y destino es el mismo");
		//Carretera nueva = new Carretera(id,longitud,velocidadMaxima,cOrigen, cDestino);
		Carretera nueva = creaCarretera(cOrigen, cDestino);
		try{mapa.addCarretera(id, cOrigen, nueva, cDestino);}
		catch(ErrorDeSimulacion e) {throw e;}
	 // obten cruce origen y cruce destino utilizando el mapa
	 // crea la carretera
	 // a�ade al mapa la carretera
	 }
	protected Carretera creaCarretera(CruceGenerico<?> cOrigen, CruceGenerico<?> cDestino) {
		return new Carretera(this.id, this.longitud, this.velocidadMaxima, cOrigen, cDestino);
	}
	
	public String getNombreEvento(){
		return nombreEvento;
	}

}

	


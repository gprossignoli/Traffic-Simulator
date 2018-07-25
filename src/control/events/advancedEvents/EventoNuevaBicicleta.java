package control.events.advancedEvents;

import java.util.List;

import control.events.basicEvents.EventoNuevoVehiculo;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;
import model.vehiculos.Bicicleta;
import util.ParserCarreteras;


public class EventoNuevaBicicleta extends EventoNuevoVehiculo {

	public EventoNuevaBicicleta(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		super(tiempo, id, velocidadMaxima, itinerario);
		// TODO Auto-generated constructor stub
	}

	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(itinerario, mapa);
		if (iti == null || iti.size() < 2) throw new ErrorDeSimulacion("No existe itinerario");
		else {
			Vehiculo b = new Bicicleta(id, velocidadMaxima, iti);
			try {
				mapa.addVehiculo(id, b);
			} catch (ErrorDeSimulacion e) {
				throw e;
			}
		}
	}
}
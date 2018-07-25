package control.events.advancedEvents;

import java.util.List;

import control.events.basicEvents.EventoNuevoVehiculo;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.cruces.CruceGenerico;
import model.vehiculos.Coche;
import model.vehiculos.Vehiculo;
import util.ParserCarreteras;

public class EventoNuevoCoche extends EventoNuevoVehiculo{
	
	protected int resistance;
	protected double fault_probability;
	protected int max_fault_duration;
	protected long seed;

	
	public EventoNuevoCoche(int tiempo, String id, int velocidadMaxima, String[] itinerario, int resistenciaKm
		, double probabilidadAveria, int maxFault, long semilla) {
		super(tiempo, id, velocidadMaxima, itinerario);
		resistance = resistenciaKm;
		fault_probability = probabilidadAveria;
		max_fault_duration = maxFault;
		seed = semilla;

		
		// TODO Auto-generated constructor stub
	}

	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(itinerario, mapa);
		if(iti == null || iti.size() < 2) throw new ErrorDeSimulacion("No existe itinerario");
		else {
			Vehiculo c = new Coche(id, velocidadMaxima,iti,resistance,max_fault_duration, fault_probability, seed);
			try {mapa.addVehiculo(id, c);}
			catch (ErrorDeSimulacion e) {throw e;}
		}
	}

}

package control.events.basicEvents;

import java.util.List;

import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;
import util.ParserCarreteras;

public class EventoNuevoVehiculo extends Evento {
	protected static final String nombreEvento = "Nuevo Vehiculo";
	 protected String id;
	 protected Integer velocidadMaxima;
	 protected String[] itinerario;
	
	 public EventoNuevoVehiculo(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		 super(tiempo);
		 this.id = id;
		 this.velocidadMaxima = velocidadMaxima;
		 this.itinerario = itinerario;
	 }

	 public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion{
	 List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
	 if(iti == null || iti.size() < 2){
		 throw new ErrorDeSimulacion("No existe itinerario");
	 }
	 
	 else{
		 Vehiculo e = new Vehiculo(id, velocidadMaxima, iti);
		 try {mapa.addVehiculo(this.id, e);}
		 catch (ErrorDeSimulacion ex) {throw ex;}
	 }
	 // si iti es null o tiene menos de dos cruces lanzar excepci�n
	 // en otro caso crear el veh�culo y a�adirlo al mapa.
	 }
	@Override
	public String getNombreEvento() {
		return nombreEvento;
	}

	}
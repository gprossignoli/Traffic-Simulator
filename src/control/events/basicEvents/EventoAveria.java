package control.events.basicEvents;

import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;

public class EventoAveria extends Evento{
	protected static final String nombreEvento = "Nueva Averia";
	protected Integer duration;
	protected String[] vehicles;
	
	 public EventoAveria(int tiempo, String[] vehicles, int duration) {
		 super(tiempo);
		 this.duration = duration;
		 this.vehicles = vehicles;
	 }

	 public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion{
		 try {
			 for(int i = 0;i < vehicles.length;i++)
				 mapa.getVehiculo(vehicles[i]).setTiempoAveria(duration);
		 }
		 catch (ErrorDeSimulacion e) {throw e;}
	 }
	 public String getNombreEvento(){
			return nombreEvento;
		}
}

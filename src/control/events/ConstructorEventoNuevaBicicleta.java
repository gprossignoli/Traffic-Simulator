package control.events;

import control.events.advancedEvents.EventoNuevaBicicleta;
import ini.IniSection;

public class ConstructorEventoNuevaBicicleta extends ConstructorEventos{
	public ConstructorEventoNuevaBicicleta() {
		 this.etiqueta = "new_vehicle";
		 this.claves = new String[] { "time", "id", "itinerary", "max_speed","type"};
		 //this.valoresPorDefecto = new String[] { "", "", };
		}
		 
	 public Evento parser(IniSection section) {

		 if(!section.getTag().equals(etiqueta) || !section.getValue("type").equals("bike")) { return null; }
		 else return new EventoNuevaBicicleta (
				 // extrae el valor del campo �time� en la secci�n
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 // extrae el valor del campo �id� de la secci�n
				 ConstructorEventos.identificadorValido(section, "id"),
				 ConstructorEventos.parseaInt(section, "max_speed"),
				 ConstructorEventos.parseaArray(section, "itinerary")
				 );
	 }
		
	 public String toString() { return "New Vehicle"; }

}

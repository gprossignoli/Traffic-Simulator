package control.events;

import control.events.advancedEvents.EventoNuevoCoche;
import ini.IniSection;

public class ConstructorEventoNuevoCoche extends ConstructorEventos{

	public ConstructorEventoNuevoCoche() {
		 this.etiqueta = "new_vehicle";
		 this.claves = new String[] { "time", "id", "itinerary", "max_speed","type","resistance",
		 "fault_probability","max_fault_duration","seed"};
		 //this.valoresPorDefecto = new String[] { "", "", };
		}
		 
		 public Evento parser(IniSection section) {
			 
		 if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("car")) return null;
		 else return new EventoNuevoCoche(
				 // extrae el valor del campo �time� en la secci�n
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 // extrae el valor del campo �id� de la secci�n
				 ConstructorEventos.identificadorValido(section, "id"),
				 ConstructorEventos.parseaInt(section, "max_speed"),
				 ConstructorEventos.parseaArray(section, "itinerary"),
				 ConstructorEventos.parseaInt(section, "resistance"),
				 ConstructorEventos.parseaDoubleNoNegativo(section, "fault_probability", 0),
				 ConstructorEventos.parseaInt(section, "max_fault_duration"),
				 ConstructorEventos.parseaInt(section, "seed")
				 );
		 
		 }
		 
		 public String toString() { return "New Vehicle"; }

}

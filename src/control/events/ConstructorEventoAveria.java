package control.events;

import control.events.basicEvents.EventoAveria;
import ini.IniSection;

public class ConstructorEventoAveria extends ConstructorEventos{
	public ConstructorEventoAveria() {
		 this.etiqueta = "make_vehicle_faulty";
		 this.claves = new String[] { "time", "vehicles", "duration" };
		 //this.valoresPorDefecto = new String[] { "", "", };
		}
		 
		 public Evento parser(IniSection section) {
		 if (!section.getTag().equals(this.etiqueta) ||
		 section.getValue("type") != null) return null;
		 else
		 return new EventoAveria(
				 // extrae el valor del campo �time� en la secci�n
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 ConstructorEventos.parseaArray(section, "vehicles"),
				 ConstructorEventos.parseaInt(section, "duration")
				 );
			 
		 }
		 
		 public String toString() { return "New Junction"; }
}

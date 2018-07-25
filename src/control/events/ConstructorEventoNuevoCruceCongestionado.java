package control.events;

import control.events.advancedEvents.EventoNuevoCruceCongestionado;
import ini.IniSection;

public class ConstructorEventoNuevoCruceCongestionado extends ConstructorEventos{
	public ConstructorEventoNuevoCruceCongestionado() {
		 this.etiqueta = "new_junction";
		 this.claves = new String[] { "time", "id", "type" };
		 //this.valoresPorDefecto = new String[] { "", "", };
		}
		 
		 public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("mc")) return null;

		 else
		 return new EventoNuevoCruceCongestionado(
				 // extrae el valor del campo �time� en la secci�n
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 // extrae el valor del campo �id� de la secci�n
				 ConstructorEventos.identificadorValido(section, "id"));
		 }
		 
		 public String toString() { return "New Junction"; }
}

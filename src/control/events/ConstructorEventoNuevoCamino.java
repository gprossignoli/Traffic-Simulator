package control.events;

import control.events.advancedEvents.EventoNuevoCamino;
import ini.IniSection;

public class ConstructorEventoNuevoCamino extends ConstructorEventos{
	
	public ConstructorEventoNuevoCamino(){
		 this.etiqueta = "new_road";
		 this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "lenght", "type"};
		 //this.valoresPorDefecto = new String[] { "", "", };
		}
		 
		 public Evento parser(IniSection section) {
		 if (!section.getTag().equals(this.etiqueta) ||
		 !section.getValue("type").equals("dirt")) return null;
		 else
		 return new EventoNuevoCamino(
				 // extrae el valor del campo �time� en la secci�n
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 // extrae el valor del campo �id� de la secci�n
				 ConstructorEventos.identificadorValido(section, "id"),
				 ConstructorEventos.identificadorValido(section, "src"),
				 ConstructorEventos.identificadorValido(section, "dest"),
				 ConstructorEventos.parseaInt(section, "max_speed"),
				 ConstructorEventos.parseaInt(section, "length")
				 );
		 }
		 
		 public String toString() { return "New Road"; }
}

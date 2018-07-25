package control.events;

import control.events.basicEvents.EventoNuevaCarretera;
import ini.IniSection;

public class ConstructorEventoNuevaCarretera extends ConstructorEventos{
	public ConstructorEventoNuevaCarretera(){
			 this.etiqueta = "new_road";
			 this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "lenght" };
			 //this.valoresPorDefecto = new String[] { "", "", };
			}
			 
			 public Evento parser(IniSection section) {
			 if (!section.getTag().equals(this.etiqueta) ||
			 section.getValue("type") != null) return null;
			 else
			 return new EventoNuevaCarretera(
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

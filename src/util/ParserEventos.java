package util;

import control.events.ConstructorEventoAveria;
import control.events.ConstructorEventoNuevaAutopista;
import control.events.ConstructorEventoNuevaBicicleta;
import control.events.ConstructorEventoNuevaCarretera;
import control.events.ConstructorEventoNuevoCamino;
import control.events.ConstructorEventoNuevoCoche;
import control.events.ConstructorEventoNuevoCruce;
import control.events.ConstructorEventoNuevoCruceCircular;
import control.events.ConstructorEventoNuevoCruceCongestionado;
import control.events.ConstructorEventoNuevoVehiculo;
import control.events.ConstructorEventos;
import control.events.Evento;
import ini.IniSection;

public class ParserEventos {
	 private static ConstructorEventos[] constructorEventos = {
	 new ConstructorEventoNuevoCruce(),
	 new ConstructorEventoNuevoCruceCongestionado(),
	 new ConstructorEventoNuevoCruceCircular(),
	 new ConstructorEventoNuevaCarretera(),
	 new ConstructorEventoNuevaAutopista(),
	 new ConstructorEventoNuevoCamino(),
	 new ConstructorEventoNuevoVehiculo(),
	 new ConstructorEventoNuevoCoche(),
	 new ConstructorEventoNuevaBicicleta(),
	 new ConstructorEventoAveria()
	 };
	// bucle de prueba y error
	 public static Evento parseaEvento(IniSection sec) {
		 int i = 0;
		 boolean seguir = true;
		 Evento e = null;
		 while (i < ParserEventos.constructorEventos.length && seguir ) {
			 // ConstructorEventos contiene el m�todo parse(sec)
			 e = ParserEventos.constructorEventos[i].parser(sec);
			 if (e!=null) seguir = false;
			 else i++;
		 }
		 return e;
	 }
	 
}
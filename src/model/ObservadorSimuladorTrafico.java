package model;

import java.util.List;

import control.events.Evento;
import exceptions.ErrorDeSimulacion;

public interface ObservadorSimuladorTrafico{
	 // notifica errores
	 public void errorSimulador(int tiempo, MapaCarreteras map,
                                List<Evento> eventos, ErrorDeSimulacion e);
	 // notifica el avance de los objetos de simulación
	 public void avanza(int tiempo, MapaCarreteras mapa,
                        List<Evento> eventos);
	 // notifica que se ha generado un nuevo evento
	 public void addEvento(int tiempo, MapaCarreteras mapa,
                           List<Evento> eventos);
	 // notifica que la simulación se ha reiniciado
	 public void reinicia(int tiempo, MapaCarreteras mapa,
                          List<Evento> eventos);
}

package model;

import java.util.List;

import control.events.Evento;
import exceptions.ErrorDeSimulacion;

public interface ObservadorSimuladorTrafico{

     void errorSimulador(int tiempo,
                         MapaCarreteras map,
                         List<Evento> eventos, ErrorDeSimulacion e);

	 void avanza(int tiempo,
                 MapaCarreteras mapa,
                 List<Evento> eventos);

	 void addEvento(int tiempo,
                    MapaCarreteras mapa,
                    List<Evento> eventos);

	 void reinicia(int tiempo,
                   MapaCarreteras mapa,
                   List<Evento> eventos);
}

package util;

import java.util.ArrayList;
import java.util.List;

import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.cruces.CruceGenerico;

public class ParserCarreteras {
	
	public static List<CruceGenerico<?>> parseaListaCruces(String[] itinerario,
                                                           MapaCarreteras mapa) throws ErrorDeSimulacion {
		
		List<CruceGenerico<?>> lista = new ArrayList<CruceGenerico<?>>();	
	
		for(int i = 0;i < itinerario.length;++i) {
			lista.add(mapa.getCruce(itinerario[i]));
		}
		return lista;
	}

}

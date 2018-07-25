package model.cruces;

import ini.IniSection;
import model.carreteras.Carretera;
import model.carreteras.CarreteraEntranteConIntervalo;

public class CruceCongestionado extends CruceGenerico<CarreteraEntranteConIntervalo> {
	
	// no tiene atributos
	
	 public CruceCongestionado(String id) {
		super(id);
	}

	 @Override
	 protected void actualizaSemaforos() {
		 CarreteraEntranteConIntervalo ri = null;
		 int posicion = 0;
		 if(this.indiceSemaforoVerde == -1) {
			posicion = this.getCarreteraConMasVehiculos(null);
			ri = this.carreterasEntrantes.get(0);
			ri.ponSemaforo(true);
			ri.setIntervaloDeTiempo(Math.max(ri.getNvehiculosEnCola(), 1));
			++this.indiceSemaforoVerde;
		 }
		 else {
			 ri = this.carreterasEntrantes.get(this.indiceSemaforoVerde);
			 if(ri.tiempoConsumido()) {
				 ri.ponSemaforo(false);
				 ri.setUnidadesDeTiempoUsadas();
				 //ri = new CarreteraEntranteConIntervalo(ri.getRoad(),0);
				 ri.setIntervaloDeTiempo(0);
				 
				 posicion = this.getCarreteraConMasVehiculos(ri.getRoad().getId());
				 CarreteraEntranteConIntervalo rj = this.carreterasEntrantes.get(posicion);
				 indiceSemaforoVerde = posicion;
				 rj.ponSemaforo(true);
				 int tempo = Math.max(rj.getNvehiculosEnCola()/2, 1);
			//	 rj = new CarreteraEntranteConIntervalo(rj.getRoad(),tempo);
				 rj.setIntervaloDeTiempo(tempo);
			 }
		 }
	// - Si no hay carretera con semáforo en verde (indiceSemaforoVerde == -1) entonces se
	// selecciona la carretera que tenga más vehículos en su cola de vehículos.
	// - Si hay carretera entrante "ri" con su semáforo en verde, (indiceSemaforoVerde != - 1)
	// entonces:
	// 1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
	// 1.1. Se pone el semáforo de "ri" a rojo.
	// 1.2. Se inicializan los atributos de "ri".
	// 1.3. Se busca la posición "max" que ocupa la primera carretera entrante
	// distinta de "ri" con el mayor número de vehículos en su cola.
	// 1.4. "indiceSemaforoVerde" se pone a "max".
	// 1.5. Se pone el semáforo de la carretera entrante en la posición "max" ("rj")
	// a verde y se inicializan los atributos de "rj", entre ellos el
	// "intervaloTiempo" a Math.max(rj.numVehiculosEnCola()/2,1).
	 }

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntranteConIntervalo(carretera, 0);
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		is.setValue("type", "mc");
	}

	protected int getCarreteraConMasVehiculos(String keynoValida) {
		int pos = 0;
		int mayor = -1;
		CarreteraEntranteConIntervalo c = null;
		for(int i = 0; i < this.carreterasEntrantes.size();++i) {
			c = this.carreterasEntrantes.get(i);
			if(mayor < c.getNvehiculosEnCola() && !c.getRoad().getId().equals(keynoValida)){
				pos = i;
			}
		}
		return pos;
	}
	
}

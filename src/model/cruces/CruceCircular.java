package model.cruces;

import ini.IniSection;
import model.carreteras.Carretera;
import model.carreteras.CarreteraEntranteConIntervalo;

public class CruceCircular extends CruceGenerico<CarreteraEntranteConIntervalo> {
	protected int minValorIntervalo;
	protected int maxValorIntervalo;

	public CruceCircular(String id,int maxValor,int minValor) {
		super(id);
		maxValorIntervalo = maxValor;
		minValorIntervalo = minValor;
	}

	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntranteConIntervalo(carretera, maxValorIntervalo);
	}


	protected void actualizaSemaforos() {
		 CarreteraEntranteConIntervalo ri;
		 final boolean red = false;
		 final boolean green = true;

		 if(this.indiceSemaforoVerde == -1){
			 ri = this.carreterasEntrantes.get(0);
			 ri.ponSemaforo(green);
			 ++this.indiceSemaforoVerde;
		 }

		 else{
			 ri = this.carreterasEntrantes.get(this.indiceSemaforoVerde);
			 if(ri.tiempoConsumido()){
				ri.ponSemaforo(red);
				final int intervalo = ri.getIntervaloDeTiempo();

				if(ri.usoCompleto()) {
				ri.setIntervaloDeTiempo(Math.min(intervalo + 1, maxValorIntervalo));
				}

				else if (!ri.usada()) {
				ri.setIntervaloDeTiempo(Math.max(intervalo - 1, minValorIntervalo));
				}

				ri.setUnidadesDeTiempoUsadas(); 
				super.actualizaSemaforos();
			 }
		 }
	 }

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		 is.setValue("type", "rr");
	}
}

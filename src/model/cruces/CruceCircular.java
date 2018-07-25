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
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntranteConIntervalo(carretera, maxValorIntervalo);
	}

	 @Override
	 protected void actualizaSemaforos() {
		 CarreteraEntranteConIntervalo ri;
		 if(this.indiceSemaforoVerde == -1){
			 ri = this.carreterasEntrantes.get(0);
			 ri.ponSemaforo(true);
			 ++this.indiceSemaforoVerde;
			 
		 }
		 else{
			 ri = this.carreterasEntrantes.get(this.indiceSemaforoVerde);
			 if(ri.tiempoConsumido()){
				ri.ponSemaforo(false);
				int intervalo = ri.getIntervaloDeTiempo();
				if(ri.usoCompleto()) {
				ri.setIntervaloDeTiempo(Math.min(intervalo + 1, maxValorIntervalo));
				}
				else if (!ri.usada()) {
				ri.setIntervaloDeTiempo(Math.max(intervalo - 1, minValorIntervalo));
				}	
				ri.setUnidadesDeTiempoUsadas(); 
				++indiceSemaforoVerde;
				 indiceSemaforoVerde = indiceSemaforoVerde % carreterasEntrantes.size();
				 carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
				 
			 }
			
		 }
	 }
	 
		 /* - Si no hay carretera con sem�foro en verde (indiceSemaforoVerde == -1) entonces se
	 selecciona la primera carretera entrante (la de la posici�n 0) y se pone su
	 sem�foro en verde.
	 - Si hay carretera entrante "ri" con su sem�foro en verde, (indiceSemaforoVerde !=
	 -1) entonces:
	 1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
	 1.1. Se pone el sem�foro de "ri" a rojo.
	 1.2. Si ha sido usada en todos los pasos (�ri.usoCompleto()�), se fija
	 el intervalo de tiempo a ... Sino, si no ha sido usada
	 (�!ri.usada()�) se fija el intervalo de tiempo a ...
	 1.3. Se coge como nueva carretera con sem�foro a verde la inmediatamente
	 Posterior a �ri�.*/
	 

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		 is.setValue("type", "rr");
	}

}

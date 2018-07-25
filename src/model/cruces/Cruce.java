package model.cruces;


import model.carreteras.Carretera;
import model.carreteras.CarreteraEntrante;


	public class Cruce extends CruceGenerico<CarreteraEntrante> {
	 
	 public Cruce(String id) {
		 super(id);
	 }

	 protected void actualizaSemaforos(){
		 if(indiceSemaforoVerde != -1)
		 	carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(false);
		 ++indiceSemaforoVerde;
		 indiceSemaforoVerde = indiceSemaforoVerde % carreterasEntrantes.size();
		 carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
	  // pone el sem�foro de la carretera actual a �rojo�, y busca la siguiente
	  // carretera entrante para ponerlo a �verde�
		 /*indiceSemaforoVerde = carreterasEntrantes.size() - 1;
		 ++indiceSemaforoVerde;
		 indiceSemaforoVerde = indiceSemaforoVerde % carreterasEntrantes.size();
		 carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);*/
	 } 

	@Override
	protected CarreteraEntrante creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntrante(carretera);
	}
}

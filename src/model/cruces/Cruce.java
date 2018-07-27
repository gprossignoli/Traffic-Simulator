package model.cruces;


import model.carreteras.Carretera;
import model.carreteras.CarreteraEntrante;


	public class Cruce extends CruceGenerico<CarreteraEntrante> {
	 
	 public Cruce(String id)  {
		 super(id);
	 }

	 protected void actualizaSemaforos(){
         final boolean red = false;
         final boolean green = true;

	     if(indiceSemaforoVerde != -1) {
	        carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(red);
         }
         super.actualizaSemaforos();
	 }

	@Override
	protected CarreteraEntrante creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntrante(carretera);
	}
}

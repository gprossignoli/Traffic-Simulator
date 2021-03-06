package control.events;

import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;

public abstract class Evento {
	 protected Integer tiempo;
	 public Evento(int tiempo) {
		 this.tiempo = tiempo;
	 }
	 public int getTiempo() { 
		 return this.tiempo;
	 }
	 
	 // cada clase que hereda implementa su m�todo ejecuta, que crear�
	 // el correspondiente objeto de la simulaci�n.

	 public abstract void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion;
	 public abstract String getNombreEvento();
}
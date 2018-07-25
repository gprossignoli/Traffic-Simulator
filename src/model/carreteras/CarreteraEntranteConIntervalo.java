package model.carreteras;

import exceptions.ErrorDeSimulacion;
import model.vehiculos.Vehiculo;

public class CarreteraEntranteConIntervalo extends CarreteraEntrante{
	protected int intervaloDeTiempo; // Tiempo que ha de transcurrir para poner el semÃ¡foro de la carretera en rojo
	protected int unidadesDeTiempoUsadas; // Se incrementa cada vez que avanza un vehÃ­culo
	protected boolean usoCompleto; // Controla que en cada paso con el semÃ¡foro en verde, ha pasado un vehÃ­culo
	protected boolean usadaPorUnVehiculo;	// Controla que al menos ha pasado un vehÃ­culo mientras el semÃ¡foro estaba en verde
	
	public CarreteraEntranteConIntervalo(Carretera carretera, int intervalTiempo) {
		 super(carretera);
		 intervaloDeTiempo = intervalTiempo;
		 unidadesDeTiempoUsadas = 0;
		 usoCompleto = false;
		 usadaPorUnVehiculo = false;
		 }
	
	public void avanzaPrimerVehiculo() throws ErrorDeSimulacion {
		++unidadesDeTiempoUsadas;
		if(this.colaVehiculos.isEmpty())
			usoCompleto = false;
		else {
			Vehiculo v = this.colaVehiculos.get(0);
			v.moverASiguienteCarretera();
			v.salirCruce();
			this.colaVehiculos.remove(0);
			usadaPorUnVehiculo = true; 
		}
		 // Incrementa unidadesDeTiempoUsadas
		 // Actualiza usoCompleto:
		 // - Si â€œcolaVehiculosâ€� es vacÃ­a, entonces â€œusoCompleto=falseâ€�
		 // - En otro caso saca el primer vehÃ­culo â€œvâ€� de la â€œcolaVehiculosâ€�,
		 // y le mueve a la siguiente carretera (â€œv.moverASiguienteCarretera()â€�)
		 // Pone â€œusadaPorUnVehiculoâ€� a true.
		 }
	public boolean tiempoConsumido() {
		return unidadesDeTiempoUsadas >= intervaloDeTiempo;
		 // comprueba si se ha agotado el intervalo de tiempo.
		 // â€œunidadesDeTiempoUsadas >= â€œintervaloDeTiempoâ€�
		 }
	protected int tiempoRestante() {
		return intervaloDeTiempo - unidadesDeTiempoUsadas;
	}
	 public String toString() {
		 String colorSem;
		 if(semaforo)
			 colorSem = "green";
		 else 
			 colorSem = "red";
		 /*String aux = colaVehiculos.toString();
		 if(aux.length() > 2)
			 aux = "[" + aux.substring(2, aux.length()-3) + "]";*/
		 String aux = "[";
		 Vehiculo tmp;
		 if(colaVehiculos.size()>0) {
		 for(int i = 0; i < colaVehiculos.size()-1;++i) {
			 tmp = colaVehiculos.get(i);
			 aux += tmp.getId() + ",";
		 }
		 tmp = colaVehiculos.get(colaVehiculos.size()-1);
		 aux += tmp.getId();
		 }
		 aux += "]";
		 int time = tiempoRestante();
		 if(colorSem.equals("green")) {
		 return "(" + carretera.getId() + "," + colorSem + ":" + time + "," + aux + ")";
		 }
		 else {
			 return "(" + carretera.getId() + "," + colorSem + "," + aux + ")";
		 }
	 }
	public boolean usoCompleto() {return usoCompleto;} // mÃ©todo get
	public boolean usada() {return usadaPorUnVehiculo;} // mÃ©todo get
	public int getIntervaloDeTiempo() {return intervaloDeTiempo;}
	public void setIntervaloDeTiempo(int nuevoValor){intervaloDeTiempo = nuevoValor;}
	public void setUnidadesDeTiempoUsadas() {unidadesDeTiempoUsadas = 0; this.usoCompleto = true; usadaPorUnVehiculo = false;}	
}

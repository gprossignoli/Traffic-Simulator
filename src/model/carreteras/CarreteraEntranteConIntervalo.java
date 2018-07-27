package model.carreteras;

import exceptions.ErrorDeSimulacion;
import model.vehiculos.Vehiculo;

public class CarreteraEntranteConIntervalo extends CarreteraEntrante{
	protected int intervaloDeTiempo; // Tiempo que ha de transcurrir para poner el semaforo de la carretera en rojo
	protected int unidadesDeTiempoUsadas; // Se incrementa cada vez que avanza un vehiculo
	protected boolean usoCompleto; // Controla que en cada paso con el semaforo en verde, ha pasado un vehiculo
	protected boolean usadaPorUnVehiculo;	// Controla que al menos ha pasado un vehiculo mientras el semaforo estaba en verde
	
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
			final int firstVehicle = 0;
			Vehiculo v = this.colaVehiculos.get(firstVehicle);
			v.moverASiguienteCarretera();
			v.salirCruce();
			this.colaVehiculos.remove(firstVehicle);
			usadaPorUnVehiculo = true; 
		}
	}

	public boolean tiempoConsumido() {
		return unidadesDeTiempoUsadas >= intervaloDeTiempo;
	}

	protected int tiempoRestante() {
		return intervaloDeTiempo - unidadesDeTiempoUsadas;
	}

	public String toString() {
		String colorSem = "green";
		if(!semaforo)
			colorSem = "red";

		String vehicleID = super.makeVehiclesIDList();

		if(colorSem.equals("red")) {
			return "(" + carretera.getId() + "," + colorSem + "," + vehicleID + ")";
		}

		final int time = tiempoRestante();
		return "(" + carretera.getId() + "," + colorSem + ":" + time + "," + vehicleID + ")";
	}

	public boolean usoCompleto() {
		return usoCompleto;
	}

	public boolean usada() {
		return usadaPorUnVehiculo;
	}

	public int getIntervaloDeTiempo() {
		return intervaloDeTiempo;
	}

	public void setIntervaloDeTiempo(int nuevoValor){
		intervaloDeTiempo = nuevoValor;
	}

	public void setUnidadesDeTiempoUsadas() {
		unidadesDeTiempoUsadas = 0;
		this.usoCompleto = true;
		usadaPorUnVehiculo = false;
	}
}

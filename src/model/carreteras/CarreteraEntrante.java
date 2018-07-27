package model.carreteras;

import java.util.ArrayList;
import java.util.List;

import exceptions.ErrorDeSimulacion;
import model.vehiculos.Vehiculo;


public class CarreteraEntrante {
	 protected Carretera carretera;
	 protected List<Vehiculo> colaVehiculos;
	 protected boolean semaforo; // true=verde, false=rojo
	 
	 public CarreteraEntrante(Carretera carretera) {
		 this.carretera = carretera;
		 this.semaforo = false;
		 this.colaVehiculos = new ArrayList<Vehiculo>();
	 }

	 public void ponSemaforo(boolean color) {
		 if(color)
			 semaforo = true;
		 else
			 semaforo = false;
	 }

	 public void avanzaPrimerVehiculo() throws ErrorDeSimulacion {
		try {
			if(colaVehiculos.size()>0) {
				final int firstVehicle = 0;
				Vehiculo v = colaVehiculos.get(firstVehicle);
				v.moverASiguienteCarretera();
				v.salirCruce();
				colaVehiculos.remove(firstVehicle);
			}
		}
		catch(ErrorDeSimulacion e) {throw e;}
	 }

	 @Override
	 public String toString() {
		 
		 String colorSem = "green";

		 if(!semaforo)
			 colorSem = "red";

		 String vehicleID = makeVehiclesIDList();
		 return "(" + carretera.getId() + "," + colorSem + "," + vehicleID + ")";
	 }

	 protected String makeVehiclesIDList(){
		 String vehicleID = "[";

		 if(colaVehiculos.size()>0) {
			 Vehiculo vInQueue;
			 for(int i = 0; i < colaVehiculos.size()-1;++i) {
				 vInQueue = colaVehiculos.get(i);
				 vehicleID += vInQueue.getId() + ",";
			 }
			 vInQueue = colaVehiculos.get(colaVehiculos.size()-1);
			 vehicleID += vInQueue.getId();
		 }

		 vehicleID += "]";
		 return vehicleID;
	 }
	 public void addNewVehicle(Vehiculo vehiculo) {
		 colaVehiculos.add(vehiculo);
	 }

	 public Carretera getRoad() {
		 return carretera;
	 }

	 public int getNvehiculosEnCola() {
		 return colaVehiculos.size();
	 }

	 public boolean getSemaforo() {
		 return semaforo;
	 }
	}

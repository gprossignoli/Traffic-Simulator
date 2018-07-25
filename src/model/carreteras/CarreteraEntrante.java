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
		 semaforo = false;
		 colaVehiculos = new ArrayList<Vehiculo>();
	 // inicia los atributos.
	 // el sem�foro a rojo
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
				Vehiculo v = colaVehiculos.get(0);
				v.moverASiguienteCarretera();
				v.salirCruce();
				colaVehiculos.remove(0);
			}
		}
		catch(ErrorDeSimulacion e) {throw e;}
	 // coge el primer vehiculo de la cola, lo elimina,
	 // y le manda que se mueva a su siguiente carretera.
	 }
	 @Override
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
		 return "(" + carretera.getId() + "," + colorSem + "," + aux + ")";
		 
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

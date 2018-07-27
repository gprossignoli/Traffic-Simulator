package model.vehiculos;

import java.util.List;

import exceptions.ErrorDeSimulacion;
import ini.IniSection;
import model.ObjetoSimulacion;
import model.carreteras.Carretera;
import model.cruces.CruceGenerico;

public class Vehiculo extends ObjetoSimulacion{
	 protected Carretera carretera;
	 protected int velocidadMaxima;
	 protected int velocidadActual;
	 protected int kilometraje; // distancia recorrida
	 protected int localizacion;
	 protected int tiempoAveria;
	 protected List<CruceGenerico<?>> itinerario; // itinerario a recorrer (minimo 2)
	 protected boolean estaEnCruce;
	 protected boolean haLlegado; //el vehiculo ha llegado a destino
	 protected int nCrucesSuperados;

	 public Vehiculo(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws ErrorDeSimulacion { 
		 super(id);
		 if(iti.size() < 2) {throw new ErrorDeSimulacion("El itinerario debe tener al menos dos cruces");}
		 if(velocidadMaxima < 0) {throw new ErrorDeSimulacion("La velocidad maxima es demasiado baja");}
		 this.velocidadMaxima = velocidadMaxima;
		 this.itinerario = iti;
		 this.carretera = null;
		 this.nCrucesSuperados = 0;
	}
	
	public int getLocalizacion() {
		if(haLlegado)
			return -1;

		return localizacion;
	 }
	 public int getTiempoDeAveria() {
		 return this.tiempoAveria;
	 }

	 public void setVelocidadActual(int velocidad) {
         velocidadActual = velocidad;

	     if(velocidad < 0)
			 velocidadActual = 0;

	     if(velocidad > velocidadMaxima)
			 velocidadActual = velocidadMaxima;

	 }
	 public void setTiempoAveria(Integer duracionAveria) {
		 if(carretera != null) {
			 tiempoAveria = duracionAveria;

			 if(tiempoAveria > 0)
				 velocidadActual = 0;
		 }
	 }
	 
	 protected void completaDetallesSeccion(IniSection is) {
	  is.setValue("speed", velocidadActual);
	  is.setValue("kilometrage", kilometraje);
	  is.setValue("faulty", tiempoAveria);
	  is.setValue("location", this.haLlegado ? "arrived" :
                "(" + this.carretera.getId() + "," + localizacion + ")");
	 }

	 public void avanza() {
		 if(isAveriado())
			 this.tiempoAveria--;

		 if(localizacion < carretera.getLength()){
			 localizacion += velocidadActual;
			 kilometraje += velocidadActual;

			 if(localizacion >= carretera.getLength()) {
				 final int reduccion = localizacion - carretera.getLength();
				 localizacion = carretera.getLength();
				 velocidadActual = 0;
				 kilometraje -= reduccion;
				 carretera.entraVehiculoAlCruce(this);
				 estaEnCruce = true;
				 nCrucesSuperados++;
				 }
		 }
	 }

	 public void moverASiguienteCarretera() throws ErrorDeSimulacion { //REVISAR
		 if(carretera != null) {
		 carretera.saleVehiculo(this);
		 }

		 if(nCrucesSuperados == itinerario.size()-1) {
			 haLlegado = true;
			 carretera = null;
			 velocidadActual = 0;
			 localizacion = 0;
			 estaEnCruce = true;
		 }

		 else {
			CruceGenerico<?> next;
			next = itinerario.get(nCrucesSuperados + 1);
			carretera = itinerario.get(nCrucesSuperados).carreteraHaciaCruce(next);  

			if (carretera == null) throw new ErrorDeSimulacion("No existe siguiente carretera");

			localizacion = 0;
			carretera.entraVehiculo(this);
		 }
	 }
		
	 protected String getNombreSeccion() { return "vehicle_report";}

	 public String toString() { return "(" + id + "," + localizacion +")";}

	 public boolean isAveriado() { return this.tiempoAveria > 0; }
		
	 public boolean isInCruce() { return this.estaEnCruce; }
		
	 public void salirCruce() { this.estaEnCruce = false; }
		
	 public Carretera getCarreteraActual() {return this.carretera;}

	 public int getVelocidadActual() {return this.velocidadActual;}

	 public int getKilometraje() {return this.kilometraje;}

	 public int getFaulty() {return this.tiempoAveria;}

	 public List<CruceGenerico<?>> getItinerario(){return this.itinerario;}
}

package model.vehiculos;

import java.util.List;

import exceptions.ErrorDeSimulacion;
import ini.IniSection;
import model.ObjetoSimulacion;
import model.carreteras.Carretera;
import model.cruces.CruceGenerico;

public class Vehiculo extends ObjetoSimulacion{


	 protected Carretera carretera; // carretera en la que est� el veh�culo
	 protected int velocidadMaxima; // velocidad m�xima
	 protected int velocidadActual; // velocidad actual
	 protected int kilometraje; // distancia recorrida
	 protected int localizacion; // localizaci�n en la carretera
	 protected int tiempoAveria; // tiempo que estar� averiado
	 protected List<CruceGenerico<?>> itinerario; // itinerario a recorrer (m�nimo 2)
	 protected boolean estaEnCruce; //el vehiculo esta en cruce
	 protected boolean haLlegado; //el vehiculo ha llegado a destino
	 protected int nCrucesSuperados; //contador que indica cuantos cruces ha superado el vehiculo
	 public Vehiculo(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws ErrorDeSimulacion { 
		 super(id);
		 if(iti.size() < 2) {throw new ErrorDeSimulacion("El itinerario debe tener al menos dos cruces");}
		 if(velocidadMaxima < 0) {throw new ErrorDeSimulacion("La velocidad maxima es demasiado baja");}
		 this.velocidadMaxima = velocidadMaxima;
		 this.itinerario = iti;
		 this.carretera = null;
		 this.nCrucesSuperados = 0;
	 // comprobar que la velocidadMaxima es mayor o igual que 0, y
	 // que el itinerario tiene al menos dos cruces.
	 // En caso de no cumplirse lo anterior, lanzar una excepci�n.
	 // inicializar los atributos teniendo en cuenta los par�metros.
	 // al crear un veh�culo su �carretera� ser� inicalmene �null�.
	}
	
	public int getLocalizacion() {
		if(!haLlegado)
			return localizacion;
		else return -1;
	 }
	 public int getTiempoDeAveria() {
		 return this.tiempoAveria;
	 }
	 public void setVelocidadActual(int velocidad) {
		 if(velocidad < 0)
			 velocidadActual = 0;
		 else if(velocidad > velocidadMaxima)
			 velocidadActual = velocidadMaxima;
		 else
			 velocidadActual = velocidad;
	  // Si �velocidad� es negativa, entonces la �velocidadActual� es 0.
	  // Si �velocidad� excede a �velocidadMaxima�, entonces la
	  // �velocidadActual� es �velocidadMaxima�
	  // En otro caso, �velocidadActual� es �velocidad�
	 }
	 public void setTiempoAveria(Integer duracionAveria) {
		 if(carretera != null) {
			 tiempoAveria = duracionAveria;
			 if(tiempoAveria > 0)
				 velocidadActual = 0;
		 }
	  // Comprobar que �carretera� no es null.
	  // Se fija el tiempo de aver�a de acuerdo con el enunciado.
	  // Si el tiempo de aver�a es finalmente positivo, entonces
	  // la �velocidadActual� se pone a 0
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
		 else if(localizacion<carretera.getLength()){
			 localizacion += velocidadActual;
			 kilometraje += velocidadActual;
			 if(localizacion>=carretera.getLength()) {
				 int reduccion = localizacion - carretera.getLength();
				 localizacion = carretera.getLength();
				 velocidadActual = 0;
				 kilometraje -= reduccion;
				 carretera.entraVehiculoAlCruce(this);
				 estaEnCruce = true;
				 nCrucesSuperados++;
				 }
		 }
		
		 
	  // si el coche est� averiado, decrementar tiempoAveria
	  // si el coche est� esperando en un cruce, no se hace nada.
	  /* en otro caso:
	  1. Actualizar su �localizacion�
	  2. Actualizar su �kilometraje�
	  3. Si el coche ha llegado a un cruce (localizacion >= carretera.getLength())
	  3.1. Poner la localizaci�n igual a la longitud de la carretera.
	  3.2. Corregir el kilometraje.
	  3.3. Indicar a la carretera que el veh�culo entra al cruce.
	  3.4. Marcar que �ste veh�culo est� en un cruce (this.estEnCruce = true)*/
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
			next = itinerario.get(nCrucesSuperados+1);
			carretera = itinerario.get(nCrucesSuperados).carreteraHaciaCruce(next);  
			if (carretera == null) throw new ErrorDeSimulacion("No existe siguiente carretera");
			else {
				localizacion = 0;
				carretera.entraVehiculo(this);
				}
			}
		 // Si la carretera no es null, sacar el veh�culo de la carretera.
		 /* Si hemos llegado al �ltimo cruce del itinerario, entonces:
		 1. Se marca que el veh�culo ha llegado (this.haLlegado = true).
		 2. Se pone su carretera a null.
		 3. Se pone su �velocidadActual� y �localizacion� a 0.
		 // y se marca que el veh�culo est� en un cruce (this.estaEnCruce = true).
		 // En otro caso:
		 1. Se calcula la siguiente carretera a la que tiene que ir.
		 2. Si dicha carretera no existe, se lanza excepci�n.
		 3. En otro caso, se introduce el veh�culo en la carretera.
		 4. Se inicializa su localizaci�n.*/
}
		
		protected String getNombreSeccion() {
			return "vehicle_report";
		}
		public String toString() {
			return "(" + id + "," + localizacion +")";
		}
		public boolean isAveriado() {
			if(this.tiempoAveria > 0)
				return true;
			else return false;
		}
		
		public boolean isInCruce() {
			return this.estaEnCruce;
		}
		
		public void salirCruce() {
			this.estaEnCruce = false;
		}
		
		public Carretera getCarreteraActual() {return this.carretera;}
		public int getVelocidadActual() {return this.velocidadActual;}
		public int getKilometraje() {return this.kilometraje;}
		public int getFaulty() {return this.tiempoAveria;}
		public List<CruceGenerico<?>> getItinerario(){return this.itinerario;}
}

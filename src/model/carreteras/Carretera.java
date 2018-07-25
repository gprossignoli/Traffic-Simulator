package model.carreteras;

import java.util.Comparator;
import java.util.List;

import ini.IniSection;
import model.ObjetoSimulacion;
import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;
import util.SortedArrayList;

public class Carretera extends ObjetoSimulacion {
	 protected int longitud; // longitud de la carretera
	 protected int velocidadMaxima; // velocidad m�xima
	 protected CruceGenerico<?> cruceOrigen; // cruce del que parte la carretera
	 protected CruceGenerico<?> cruceDestino; // cruce al que llega la carretera
	 protected List<Vehiculo> vehiculos; // lista ordenada de veh�culos en la
	// carretera (ordenada por localizaci�n)
	 protected Comparator<Vehiculo> comparadorVehiculo; // orden entre veh�culos
	 
	 public Carretera(String id, int length, int maxSpeed, CruceGenerico<?> cOrigen, CruceGenerico<?> cDestino) {
		 super(id);
		 longitud = length;
		 velocidadMaxima = maxSpeed;
		 cruceOrigen = cOrigen;
		 cruceDestino = cDestino;
		 comparadorVehiculo = new Comparator<Vehiculo>(){
			@Override
			public int compare(Vehiculo arg0, Vehiculo arg1) {
				if(arg0.getLocalizacion() > arg1.getLocalizacion())
					return -1;
				else if(arg0.getLocalizacion()==arg1.getLocalizacion())
					return 0;
				else
					return 1;
			}
		 };
		 vehiculos = new SortedArrayList<Vehiculo>(comparadorVehiculo);
	// se inicializan los atributos de acuerdo con los par�metros.
	 // se fija el orden entre los veh�culos: (inicia comparadorVehiculo)
	 // - la localizaci�n 0 es la menor
	 }
	
	@Override
	 public void avanza() {
		 int velocidadBase;
		 int obstaculos = 0;
		 int factorReduccion;
		 
		 for(Vehiculo v : vehiculos) {
			 if(v.isAveriado())
				 obstaculos++;
			else if(v.isInCruce())
				 v.setVelocidadActual(0);
			 else{
				 velocidadBase = calculaVelocidadBase();
			 	factorReduccion = calculaFactorReduccion(obstaculos);
			 	v.setVelocidadActual(velocidadBase/factorReduccion);
			 }
			 v.avanza();
		 }
		 vehiculos.sort(comparadorVehiculo);
	  // calcular velocidad base de la carretera
	  // inicializar obst�culos a 0
	  // Para cada veh�culo de la lista �vehiculos�:
	 // 1. Si el veh�culo est� averiado se incrementa el n�mero de obstaculos.
	  // 2. Se fija la velocidad actual del veh�culo
	  // 3. Se pide al veh�culo que avance.
	  // ordenar la lista de veh�culos
	 }
	 public void entraVehiculo(Vehiculo vehiculo) {
		 if(!vehiculos.contains(vehiculo)) {
			 vehiculos.add(vehiculo);
			 vehiculos.sort(comparadorVehiculo);
		 }
		 // Si el veh�culo no existe en la carretera, se a�ade a la lista de veh�culos y
		 // se ordena la lista.
		 // Si existe no se hace nada.
		}
		public void saleVehiculo(Vehiculo vehiculo) {
			vehiculos.remove(vehiculo);
		// elimina el veh�culo de la lista de veh�culos
		}
		public void entraVehiculoAlCruce(Vehiculo v) {
			cruceDestino.entraVehiculoAlCruce(id, v);
		// a�ade el veh�culo al �cruceDestino� de la carretera�
		}
		protected int calculaVelocidadBase() {
			 int max;
			 if(vehiculos.size() > 1)
				 max = vehiculos.size();
			 else 
				 max = 1;
			 int div = (velocidadMaxima / max) + 1;
			 int velocidadBase;
			 if(velocidadMaxima < div)
				 velocidadBase = velocidadMaxima;
			 else
				 velocidadBase = div;
			 return velocidadBase;
		}
		protected int calculaFactorReduccion(int obstaculos) {
			if(obstaculos == 0)
				return 1;
			else
				return 2;
		}
		@Override
		protected String getNombreSeccion() {
			return "road_report";
		}
		@Override
		protected void completaDetallesSeccion(IniSection is) {
		
			if(vehiculos.size() == 0) {
				is.setValue("state", "");
			}
			else {
				String valores = "";
			for(int i = 0; i < vehiculos.size();i++) {
			//is.setValue("state", vehiculos.get(i).toString());
				
				valores += vehiculos.get(i).toString();
				valores += ',';
				if(i == vehiculos.size() - 1){
					valores  = valores.substring(0, valores.length() - 1);
				}
			}
			is.setValue("state", valores);
			}
		 // crea �vehicles = (v1,10),(v2,10) �
		}

		public int getLength() {
			return this.longitud;
		}
		
		public CruceGenerico<?> getSource(){
			return this.cruceOrigen;
		}
		
		public CruceGenerico<?> getTarget(){
			return this.cruceDestino;
		}
		
		public int getMaxSpeed() {
			return this.velocidadMaxima;
		}
		
		public List<Vehiculo> getVehicles(){
			return this.vehiculos;
		}
}
	 
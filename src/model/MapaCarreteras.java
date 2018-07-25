package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.ErrorDeSimulacion;
import model.carreteras.Carretera;
import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;

public class MapaCarreteras {
 private List<Carretera> carreteras;
 private List<CruceGenerico<?>> cruces;
 private List<Vehiculo> vehiculos;
 // estructuras para agilizar la bÃºsqueda (id,valor)
 private Map<String, Carretera> mapaDeCarreteras;
 private Map<String, CruceGenerico<?>> mapaDeCruces;
 private Map<String, Vehiculo> mapaDeVehiculos;
 
 public MapaCarreteras() {
	 this.carreteras = new ArrayList<Carretera>(); 
	 this.cruces = new ArrayList<CruceGenerico<?>>();
	 this.vehiculos = new ArrayList<Vehiculo>();
	 this.mapaDeCarreteras = new HashMap<String,Carretera>();
	 this.mapaDeCruces = new HashMap<String,CruceGenerico<?>>();
	 this.mapaDeVehiculos = new HashMap<String,Vehiculo>();
	 // inicializa los atributos a sus constructoras por defecto.
	 // Para carreteras, cruces y vehÃ­culos puede usarse ArrayList.
	 // Para los mapas puede usarse HashMap
	}
	public void addCruce(String idCruce, CruceGenerico<?> cruce) throws ErrorDeSimulacion{
		if(!(this.mapaDeCruces.containsKey(idCruce))) {
			this.cruces.add(cruce);
			this.mapaDeCruces.put(idCruce, cruce);
		}
		else
			throw new ErrorDeSimulacion("El cruce ya existe en el mapa");
			
	 // comprueba que â€œidCruceâ€� no existe en el mapa.
	 // Si no existe, lo aÃ±ade a â€œcrucesâ€� y a â€œmapaDeCrucesâ€�.
	 // Si existe lanza una excepciÃ³n.
	}
	public void addVehiculo(String idVehiculo,Vehiculo vehiculo) throws ErrorDeSimulacion {
		if(!this.mapaDeVehiculos.containsKey(idVehiculo)) {
			this.vehiculos.add(vehiculo);
			this.mapaDeVehiculos.put(idVehiculo, vehiculo);
			vehiculos.get(vehiculos.size()-1).moverASiguienteCarretera();
		}
		else 
			throw new ErrorDeSimulacion("El vehiculo ya existe en el mapa");
		 // comprueba que â€œidVehiculoâ€� no existe en el mapa.
		 // Si no existe, lo aÃ±ade a â€œvehiculosâ€� y a â€œmapaDeVehiculosâ€�,
		 // y posteriormente solicita al vehiculo que se mueva a la siguiente
		 // carretera de su itinerario (moverASiguienteCarretera).
		 // Si existe lanza una excepciÃ³n.
		}
	public void addCarretera(String idCarretera, CruceGenerico<?> origen, Carretera carretera, CruceGenerico<?> destino) throws ErrorDeSimulacion {
		if(!this.mapaDeCarreteras.containsKey(idCarretera)) {
			this.carreteras.add(carretera);
			this.mapaDeCarreteras.put(idCarretera, carretera);
			origen.addCarreteraSalienteAlCruce(destino, carretera);
			destino.addCarreteraEntranteAlCruce(idCarretera, carretera);
		}
		else 
			throw new ErrorDeSimulacion("La carretera ya existe en el mapa");
			 // comprueba que â€œidCarreteraâ€� no existe en el mapa.
			 // Si no existe, lo aÃ±ade a â€œcarreterasâ€� y a â€œmapaDeCarreterasâ€�,
			 // y posteriormente actualiza los cruces origen y destino como sigue:
			 // - AÃ±ade al cruce origen la carretera, como â€œcarretera salienteâ€�
			 // - AÃ±ade al crude destino la carretera, como â€œcarretera entranteâ€�
			 // Si existe lanza una excepciÃ³n.
			}
	public String generateReport(int time) {
		 String report = "";
		 for(CruceGenerico<?> c : cruces)
			report += c.generaInforme(time);
		 for(Carretera r : carreteras)
			 report += r.generaInforme(time);
		 if(vehiculos.size() == 0) report = report.substring(0, report.length() - 2);
		 for(Vehiculo v : vehiculos) {
			 report += v.generaInforme(time);
			 if(v != vehiculos.get(vehiculos.size() - 1))
				 report += "\n";
		 }
		 return report;
		 // genera informe para cruces
		 // genera informe para carreteras
		 // genera informe para vehiculos
		}
		public void actualizar() throws ErrorDeSimulacion {
			for(Carretera r: this.carreteras)
				r.avanza();
			for(CruceGenerico<?> c: this.cruces) 
				c.avanza();
		 // llama al mÃ©todo avanza de cada cruce
		 // llama al mÃ©todo avanza de cada carretera
		}
		public CruceGenerico<?> getCruce(String id) throws ErrorDeSimulacion {
			if(this.mapaDeCruces.containsKey(id))
				return this.mapaDeCruces.get(id);
			else
				throw new ErrorDeSimulacion("El cruce solicitado no existe en el mapa");
		 // devuelve el cruce con ese â€œidâ€� utilizando el mapaDeCruces.
		 // sino existe el cruce lanza excepciÃ³n.
		}
		public Vehiculo getVehiculo(String id) throws ErrorDeSimulacion {
			if(this.mapaDeVehiculos.containsKey(id))
				return this.mapaDeVehiculos.get(id);
			else
				throw new ErrorDeSimulacion("El vehiculo solicitado no existe en el mapa");
		 // devuelve el vehÃ­culo con ese â€œidâ€� utilizando el mapaDeVehiculos.
		 // sino existe el vehÃ­culo lanza excepciÃ³n.
		}
		public Carretera getCarretera(String id) throws ErrorDeSimulacion{
			if(this.mapaDeCarreteras.containsKey(id))
				return this.mapaDeCarreteras.get(id);
			else
				throw new ErrorDeSimulacion("La carretera solicitada no existe en el mapa");
		 // devuelve la carretera con ese â€œidâ€� utilizando el mapaDeCarreteras.
		 // sino existe la carretra lanza excepciÃ³n.
		}
		
		public List<Carretera> getCarreteras(){return this.carreteras;}
		public List<CruceGenerico<?>> getCruces(){return this.cruces;}
		public List<Vehiculo> getVehiculos(){return this.vehiculos;}
}

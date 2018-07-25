package model.cruces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.ErrorDeSimulacion;
import ini.IniSection;
import model.ObjetoSimulacion;
import model.carreteras.Carretera;
import model.carreteras.CarreteraEntrante;
import model.vehiculos.Vehiculo;

abstract public class CruceGenerico<T extends CarreteraEntrante> extends ObjetoSimulacion {
	protected int indiceSemaforoVerde;
	 protected List<T> carreterasEntrantes;
	 protected Map<String,T> mapaCarreterasEntrantes;
	 protected Map<CruceGenerico<?>, Carretera> carreterasSalientes;
	 
	 public CruceGenerico(String id) {
		 super(id);
		 carreterasEntrantes = new ArrayList<T>();
		 mapaCarreterasEntrantes = new HashMap<String,T>();
		 carreterasSalientes = new HashMap<CruceGenerico<?>, Carretera>();
		 indiceSemaforoVerde = -1;
	 }
	 
	 public Carretera carreteraHaciaCruce(CruceGenerico<?> cruce) {
		 return this.carreterasSalientes.get(cruce);
	 }
	 
	 public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {
		 T ri = creaCarreteraEntrante(carretera);
		 carreterasEntrantes.add(ri);
		 mapaCarreterasEntrantes.put(idCarretera, ri);
		 }
	 abstract protected T creaCarreteraEntrante(Carretera carretera);
	 
	 public void addCarreteraSalienteAlCruce(CruceGenerico<?> destino, Carretera carr) {
		 carreterasSalientes.put(destino, carr);
	 }
	 
	 public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo){
		 mapaCarreterasEntrantes.get(idCarretera).addNewVehicle(vehiculo);
	 }
	 
	 public void avanza() throws ErrorDeSimulacion{
		 try {
			 if(!carreterasEntrantes.isEmpty()) {
				 if(indiceSemaforoVerde != -1){
					 carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
				 }
				 actualizaSemaforos();
				 
			 }
		 }
		 catch (ErrorDeSimulacion e) {throw e;}
	 }
	 
	abstract protected void actualizaSemaforos();
	 
	 @Override
	 protected void completaDetallesSeccion(IniSection is) {
		 if(carreterasEntrantes.size() < 1)
			 is.setValue("queues","\n");
		 else {
			 String cola = "";
		 for(int i = 0; i < carreterasEntrantes.size(); i++) {
			 cola += carreterasEntrantes.get(i).toString();
			 if(i < carreterasEntrantes.size() - 1)
				 cola += ",";
		 }
		 
		 is.setValue("queues", cola);
			 //is.setValue("queues", carreterasEntrantes.get(i).toString());
	  // genera la secci�n queues = (r2,green,[]),...
		 }
	 }
	 
	 public String  getNombreSeccion(){
		 return "junction_report";
	 }
	 
	 public CarreteraEntrante getCarreteraEntranteVerde() {
		 if(indiceSemaforoVerde != -1)
			 return this.carreterasEntrantes.get(indiceSemaforoVerde);
		 else return null;
	 }
	 
	 public List<String> getCarreterasEntrantesRojo(){

		 List<String> reportes = new ArrayList<String>();
		 for(int i = 0; i < this.carreterasEntrantes.size(); ++i) {
			 if(i != this.indiceSemaforoVerde) {
				 reportes.add(this.carreterasEntrantes.get(i).toString());
			 }
		 }
		 return reportes;
	 }
	 
}

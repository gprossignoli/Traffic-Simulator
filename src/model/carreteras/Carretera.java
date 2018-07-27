package model.carreteras;

import java.util.Comparator;
import java.util.List;

import ini.IniSection;
import model.ObjetoSimulacion;
import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;
import util.SortedArrayList;

public class Carretera extends ObjetoSimulacion {
	 protected int longitud;
	 protected int velocidadMaxima;
	 protected CruceGenerico<?> cruceOrigen;
	 protected CruceGenerico<?> cruceDestino;
	 protected List<Vehiculo> vehiculos; // lista ordenada por localizacion, empezando por 0
	 protected Comparator<Vehiculo> comparadorVehiculo;
	 
	 public Carretera(String id, int length, int maxSpeed, CruceGenerico<?> cOrigen, CruceGenerico<?> cDestino) {
		 super(id);
		 longitud = length;
		 velocidadMaxima = maxSpeed;
		 cruceOrigen = cOrigen;
		 cruceDestino = cDestino;
		 comparadorVehiculo = new Comparator<Vehiculo>(){
			@Override
			public int compare(Vehiculo v1, Vehiculo v2) {
				if(v1.getLocalizacion() > v2.getLocalizacion())
					return -1;
				else if(v1.getLocalizacion()== v2.getLocalizacion())
					return 0;
				else
					return 1;
			}
		 };
		 vehiculos = new SortedArrayList<Vehiculo>(comparadorVehiculo);
	 }
	
	@Override
	 public void avanza() {
		 int obstaculos = 0;
		 
		 for(Vehiculo v : vehiculos) {
		 	if(v.isAveriado())
				 obstaculos++;
			else if(v.isInCruce())
				 v.setVelocidadActual(0);
			else{
				int velocidadBase = calculaVelocidadBase();
			 	int factorReduccion = calculaFactorReduccion(obstaculos);
			 	v.setVelocidadActual(velocidadBase/factorReduccion);
			 }
			 v.avanza();
		 }
		 vehiculos.sort(comparadorVehiculo);
	 }

	 public void entraVehiculo(Vehiculo vehiculo) {
		 if(!vehiculos.contains(vehiculo)) {
			 vehiculos.add(vehiculo);
			 vehiculos.sort(comparadorVehiculo);
		 }
	 }
	 public void saleVehiculo(Vehiculo vehiculo) {
	     vehiculos.remove(vehiculo);

	 }

	 public void entraVehiculoAlCruce(Vehiculo v) {
	     cruceDestino.entraVehiculoAlCruce(id, v);
	 }

	 protected int calculaVelocidadBase() {
	     int max = 1;
	     if(vehiculos.size() > 1)
	         max = vehiculos.size();

	     final int div = (velocidadMaxima / max) + 1;

	     int velocidadBase = div;
	     if(velocidadMaxima < div)
	         velocidadBase = velocidadMaxima;

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
	 
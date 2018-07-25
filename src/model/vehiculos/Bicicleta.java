package model.vehiculos;

import java.util.List;

import exceptions.ErrorDeSimulacion;
import ini.IniSection;
import model.cruces.CruceGenerico;

public class Bicicleta extends Vehiculo {
	public Bicicleta(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws ErrorDeSimulacion {
		super(id,velocidadMaxima,iti);
	}
	public void setTiempoAveria(Integer duracionAveria) {
		if(this.velocidadActual >= this.velocidadMaxima / 2) {
			super.setTiempoAveria(duracionAveria);
		}
	 }
	 protected void completaDetallesSeccion(IniSection is) {
		  super.completaDetallesSeccion(is);
		  is.setValue("type", "bike");
	 }
}

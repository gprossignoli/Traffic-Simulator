package model;

import exceptions.ErrorDeSimulacion;
import ini.IniSection;

public abstract class ObjetoSimulacion {
	 protected String id;
	 public ObjetoSimulacion(String id) {
		 this.id = id;
	 }
	 public String getId() {
		 return this.id;
	 }
	 
	 public abstract void avanza() throws ErrorDeSimulacion;
	 public String generaInforme(int tiempo) {
		 IniSection is = new IniSection(this.getNombreSeccion());
		 is.setValue("id", this.id);
		 is.setValue("time", tiempo);
		 this.completaDetallesSeccion(is);
		 return is.toString();

	}
		// los m�todos getNombreSeccion y completaDetallesSeccion
		// tendr�n que implementarlos cada subclase de ObjetoSimulacion
	protected abstract void completaDetallesSeccion(IniSection is);
	protected abstract String getNombreSeccion();	

}	
		// los m�todos getNombreSeccion y completaDetallesSeccion
		// tendr�n que implementarlos cada subclase de ObjetoSimulacion


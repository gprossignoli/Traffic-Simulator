package model.carreteras;

import ini.IniSection;
import model.cruces.CruceGenerico;

public class Camino extends Carretera{

	public Camino(String id, int length, int maxSpeed, CruceGenerico<?> cOrigen, CruceGenerico<?> cDestino) {
		super(id, length, maxSpeed, cOrigen, cDestino);
	}

	protected int calculaVelocidadBase(){
		return this.velocidadMaxima;
	}
	
	protected int calculaFactorReduccion(int obstacles){
		return obstacles + 1;
	}
	
	protected void completaDetallesSeccion(IniSection is) {			
		super.completaDetallesSeccion(is);
		is.setValue("type", "dirt");
	}

}
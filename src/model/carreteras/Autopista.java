package model.carreteras;

import ini.IniSection;
import model.cruces.CruceGenerico;

public class Autopista extends Carretera{
	private int numCarriles;
	
	public Autopista(String id, Integer length, Integer maxSpeed, CruceGenerico<?> cOrigen, CruceGenerico<?> cDestino, int nCarriles){
		super(id, length, maxSpeed, cOrigen, cDestino);
		this.numCarriles = nCarriles;
	}
	
	protected int calculaVelocidadBase(){
		int max = 1;
		if(vehiculos.size() > 1)
			max = vehiculos.size();

		final int div = (velocidadMaxima * numCarriles / max) + 1;
		int velocidadBase = div;

		if(velocidadMaxima < div)
			 velocidadBase = velocidadMaxima;

		return velocidadBase;
	}
	
	protected int calculaFactorReduccion(int obstaculos){
		if(numCarriles > obstaculos) return 1;
		else return 2;
	}

	protected void completaDetallesSeccion(IniSection iniSect) {
		super.completaDetallesSeccion(iniSect);
		iniSect.setValue("type", "lanes");
		// crea vehicles = (v1,10),(v2,10)
	}

}

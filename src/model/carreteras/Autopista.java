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
		int max;
		 if(vehiculos.size() > 1)
			 max = vehiculos.size();
		 else 
			 max = 1;
		 int div = (velocidadMaxima * numCarriles / max) + 1;
		 int velocidadBase;
		 if(velocidadMaxima < div)
			 velocidadBase = velocidadMaxima;
		 else
			 velocidadBase = div;
		 return velocidadBase;
	}
	
	protected int calculaFactorReduccion(int obstaculos){
		if(numCarriles > obstaculos) return 1;
		else return 2;
	}

	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		is.setValue("type", "lanes");
		// crea �vehicles = (v1,10),(v2,10) �
	}

	
}

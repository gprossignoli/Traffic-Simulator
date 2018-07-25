package model.vehiculos;

import java.util.List;
import java.util.Random;

import exceptions.ErrorDeSimulacion;
import ini.IniSection;
import model.cruces.CruceGenerico;

public class Coche extends Vehiculo {
	protected int kmUltimaAveria;
	protected int resistenciaKm;
	protected int duracionMaximaAveria;
	protected double probabilidadDeAveria;
	protected Random numAleatorio;
	public Coche(String id, int velocidadMaxima, List<CruceGenerico<?>> iti,int resistance, int max_fault_duration, 
		double fault_probability, long seed) throws ErrorDeSimulacion {
		super(id, velocidadMaxima, iti);
		kmUltimaAveria = 0;
		resistenciaKm = resistance;
		duracionMaximaAveria = max_fault_duration;
		probabilidadDeAveria = fault_probability;
		numAleatorio = new Random(seed);
	}

	public void avanza() {
		
		if(this.tiempoAveria > 0) {
			this.kmUltimaAveria = this.kilometraje;
		}
		
		else if((kilometraje - this.kmUltimaAveria) > this.resistenciaKm && numAleatorio.nextDouble() <= this.probabilidadDeAveria) {
			super.setTiempoAveria(numAleatorio.nextInt(duracionMaximaAveria) + 1);
		}
		super.avanza();
		// - Si el coche estÃ¡ averiado poner â€œkmUltimaAveriaâ€� a â€œkilometrajeâ€�.
	 // - Sino el coche no estÃ¡ averiado y ha recorrido â€œresistenciakmâ€�, y ademÃ¡s
	 // â€œnumAleatorioâ€�.nextDouble() < â€œprobabilidadDeAveriaâ€�, entonces
	 // incrementar â€œtiempoAveriaâ€� con â€œnumAleatorio.nextInt(â€œduracionMaximaAveriaâ€�)+1
	 // - Llamar a super.avanza();
	}
	 protected void completaDetallesSeccion(IniSection is) {
		  super.completaDetallesSeccion(is);
		  is.setValue("type","car");
	}
}
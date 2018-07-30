package model;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import util.SortedArrayList;

public class SimuladorTrafico implements Observador<ObservadorSimuladorTrafico>{
	private MapaCarreteras mapa;
	private List<Evento> eventos;
	private int contadorTiempo;
	private List<ObservadorSimuladorTrafico> observadores;

	public SimuladorTrafico() {
			 this.mapa = new MapaCarreteras();
			 this.contadorTiempo = 0;
			 Comparator<Evento> cmp = new Comparator<Evento>() {
				@Override
				public int compare(Evento e0, Evento e1) {
                    if(e0.getTiempo() < e1.getTiempo())
						return -1;

					if(e0.getTiempo() == e1.getTiempo())
						return 0;

					return 1;
				}
			 };
			 this.eventos = new SortedArrayList<>(cmp); // estructura ordenada por tiempo
			 this.observadores = new ArrayList<>();
			 }
	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws ErrorDeSimulacion {
			 int limiteTiempo = this.contadorTiempo + pasosSimulacion - 1;
			 String out = "";

			 while (this.contadorTiempo <= limiteTiempo) {
				 out += "# ******* step " + contadorTiempo + " *******\n\n";

				 int i = this.contadorTiempo;
				 while(i < eventos.size()) {
					Evento event = eventos.get(i);
					 if(event.getTiempo() == this.contadorTiempo)
					    event.ejecuta(mapa);
					 ++i;
				 }

				 try {
					 this.mapa.actualizar();
					 this.notificaAvanza();
				 }
				 catch(ErrorDeSimulacion e) {
					 this.notificaError(e);
				 }

				 ++contadorTiempo;	
				 out += this.mapa.generateReport(contadorTiempo) + "\n"; 
	
			 }

			 try(PrintStream salida = new PrintStream(ficheroSalida) ){
				 salida.print(out);
			 }
	}

	public String generaInformes() {
		return this.mapa.generateReport(contadorTiempo);
	}

	public void insertaEvento(Evento e) throws ErrorDeSimulacion {
	    if(e == null){
            ErrorDeSimulacion err = new ErrorDeSimulacion("Evento anterior al paso de simulacion");
            this.notificaError(err);
            throw err;
        }

        if(e.getTiempo() < this.contadorTiempo){
            ErrorDeSimulacion err = new ErrorDeSimulacion("Evento anterior al paso de simulacion");
            this.notificaError(err);
            throw err;
        }

        eventos.add(e);
        this.notificaNuevoEvento();
	}
	
	public void reinicia() {
	    this.mapa = new MapaCarreteras();
	    this.contadorTiempo = 0;
	    this.eventos.clear();
	    this.notificaReinicia();
	}
	
	public int getContadorTiempo(){
		return this.contadorTiempo;
	}
	
	private void notificaNuevoEvento() {
		 for (ObservadorSimuladorTrafico o : this.observadores) {
			 o.addEvento(this.contadorTiempo,this.mapa,this.eventos);
		 }
	}
	
	private void notificaError(ErrorDeSimulacion e) {
		for (ObservadorSimuladorTrafico o : this.observadores) {
			 o.errorSimulador(contadorTiempo, mapa, eventos, e);
		 }
	}
	
	private void notificaAvanza() {
		for(ObservadorSimuladorTrafico o : this.observadores) {
			o.avanza(contadorTiempo, mapa, eventos);
		}
	}
	
	private void notificaReinicia() {
		for(ObservadorSimuladorTrafico o : this.observadores)
			o.reinicia(contadorTiempo, mapa, eventos);
	}
	
	@Override
	public void addObservador(ObservadorSimuladorTrafico o) {
		if (o != null && !this.observadores.contains(o)) {
		this.observadores.add(o);
		}
	}
	@Override
	public void removeObservador(ObservadorSimuladorTrafico o) {
		if (o != null && this.observadores.contains(o)) {
		this.observadores.remove(o);
		}
	}
}			 	
		



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
					else if(e0.getTiempo() == e1.getTiempo())
						return 0;
					else
						return 1;
				}
			 };
			 this.eventos = new SortedArrayList<>(cmp); // estructura ordenada por �tiempo�
			 this.observadores = new ArrayList<>();
			 }
	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws ErrorDeSimulacion {
			 int limiteTiempo = this.contadorTiempo + pasosSimulacion - 1;
			 String out = "";
			 while (this.contadorTiempo <= limiteTiempo) {
				 out += "# ******* step " + contadorTiempo + " *******\n\n";
				 int i = this.contadorTiempo;
				 Evento event;
				 
				 while(i < eventos.size()) {
					 event = eventos.get(i);
					 if(eventos.get(i).getTiempo() == this.contadorTiempo)
					 event.ejecuta(mapa);
					 ++i;
				 } 
				 try {
					 this.mapa.actualizar();
					 this.notificaAvanza();
				 }catch(ErrorDeSimulacion e) {
					 this.notificaError(e);
				 }
				 ++contadorTiempo;	
				 out += this.mapa.generateReport(contadorTiempo) + "\n"; 
	
			 // ejecutar todos los eventos correspondienes a �this.contadorTiempo�
			 // actualizar �mapa�
			 // escribir el informe en �ficheroSalida�, controlando que no sea null.
			 	}
			 try(PrintStream salida = new PrintStream(ficheroSalida) ){
				 salida.print(out);
			 }
			
	}
	public String generaInformes() {
		return this.mapa.generateReport(contadorTiempo);
	}
	public void insertaEvento(Evento e) throws ErrorDeSimulacion {
		if(e != null) {
			if(e.getTiempo() >= this.contadorTiempo) { 
				eventos.add(e);
				this.notificaNuevoEvento();
			}
			else {
				ErrorDeSimulacion err = new ErrorDeSimulacion("Evento anterior al paso de simulacion");
				this.notificaError(err);
				throw err;
			}
		}
		else {
			ErrorDeSimulacion err = new ErrorDeSimulacion("El evento no contiene informacion");
			this.notificaError(err);
			throw err;
		}
			 // inserta un evento en �eventos�, controlando que el tiempo de
			 // ejecuci�n del evento sea menor que �contadorTiempo�
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
		



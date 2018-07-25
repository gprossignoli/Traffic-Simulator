
package control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import ini.Ini;
import ini.IniSection;
import model.ObservadorSimuladorTrafico;
import model.SimuladorTrafico;
import util.ParserEventos;

public class Controlador {
	 private SimuladorTrafico simulador;
	 private OutputStream ficheroSalida;
	 private InputStream ficheroEntrada;
	 private int pasosSimulacion;
	 
	 public Controlador(SimuladorTrafico sim, Integer limiteTiempo, InputStream is, OutputStream os) {
		 this.simulador = sim;
		 this.pasosSimulacion = limiteTiempo;
		 this.ficheroEntrada = is;
		 this.ficheroSalida = os;
		
	 }
	public void ejecuta() {
		try{
			this.cargaEventos(this.ficheroEntrada);
			this.simulador.ejecuta(pasosSimulacion,this.ficheroSalida);
		}
		catch(ErrorDeSimulacion e){
			e.printStackTrace();
		}
	}
	public void ejecuta(int pasos)throws ErrorDeSimulacion {
		this.simulador.ejecuta(pasos,this.ficheroSalida);
	}
	
	 public void cargaEventos(InputStream inStream) throws ErrorDeSimulacion {
		 Ini ini;
		 try {
			 // lee el fichero y carga su atributo iniSections
			 ini = new Ini(inStream);
		 }
		 catch (IOException e) {
			 throw new ErrorDeSimulacion("Error en la lectura de eventos: " + e);
		 }
		 // recorremos todas los elementos de iniSections para generar el evento
		 // correspondiente
		 for (IniSection sec : ini.getSections()) {
		 // parseamos la secci�n para ver a que evento corresponde
			 Evento e = ParserEventos.parseaEvento(sec);
			 if (e != null) this.simulador.insertaEvento(e);
			 else
				 throw new ErrorDeSimulacion("Evento desconocido: " + sec.getTag());
		 }
	}
	
	 public void reinicia() {
		 this.simulador.reinicia();
	 }
	 
	 public String generaInformes() {
		 return this.simulador.generaInformes();
	 }
	  
	 public Integer getPasosSimulacion(){
		 return this.simulador.getContadorTiempo();
	 }
	 
	 public void setFicheroEntrada(InputStream inStream) {
		 ficheroEntrada = inStream;
	 }
	 public void addObserver(ObservadorSimuladorTrafico a) {
		 this.simulador.addObservador(a);
	 }
	
	 public void removeObserver(ObservadorSimuladorTrafico a) {
		 this.simulador.removeObservador(a);
	 }
	 
	 
}
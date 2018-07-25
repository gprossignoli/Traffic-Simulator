package views.tablas;

import java.util.List;
import javax.swing.SwingUtilities;

import control.Controlador;
import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.cruces.CruceGenerico;

public class ModeloTablaCruces extends ModeloTabla<CruceGenerico<?>> {
	 public ModeloTablaCruces(String[] columnIdEventos, Controlador ctrl) { 
		 super(columnIdEventos, ctrl);
	 }
	 @Override // necesario para que se visualicen los datos
	 public Object getValueAt(int indiceFil, int indiceCol) {
		 Object s = null;
		 switch (indiceCol) {
		 	case 0: s = this.lista.get(indiceFil).getId(); break;
		 	case 1: 
		 		if(this.lista.get(indiceFil).getCarreteraEntranteVerde() != null)
		 			s = this.lista.get(indiceFil).getCarreteraEntranteVerde().toString(); 
		 		else s = "[]"; 
		 		break;
		 	case 2: s = this.generarEnRojo(indiceFil); break;
		 	default: assert(false);
		 }
		 return s;
	 }
	 //u
	 private String generarEnRojo(int indiceFil) {
		 int size = this.lista.get(indiceFil).getCarreterasEntrantesRojo().size();
		 String s = "[";
		 if(size != 0) {
		 for(int i = 0; i < size; ++i) {
	 			s += this.lista.get(indiceFil).getCarreterasEntrantesRojo().get(i);
	 			if(i < this.lista.get(indiceFil).getCarreterasEntrantesRojo().size() - 1)
	 				s += ",";
	 		}
		 }
		 else s = "[]";
		 return s;
	 }
	 
	 @Override
	 public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		 SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				 lista = mapa.getCruces(); 
				 fireTableDataChanged();
			}
		});
	 }
	 
	 @Override
	 public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		 SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 lista = mapa.getCruces(); 
				 fireTableDataChanged();
			}
		});	 }
	 
	 
	 @Override
	 public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		 SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				 lista = mapa.getCruces(); 
				 fireTableDataChanged();
			}
		});
		 
	 }
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {}
}
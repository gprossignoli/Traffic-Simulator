package views.tablas;

import java.util.List;
import javax.swing.SwingUtilities;

import control.Controlador;
import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.carreteras.Carretera;
import model.vehiculos.Vehiculo;

public class ModeloTablaCarreteras extends ModeloTabla<Carretera> {
	 public ModeloTablaCarreteras(String[] columnIdEventos, Controlador ctrl) { 
		 super(columnIdEventos, ctrl);
	 }
	 @Override // necesario para que se visualicen los datos
	 public Object getValueAt(int indiceFil, int indiceCol) {
		 Object s = null;
		 switch (indiceCol) {
		 	case 0: s = this.lista.get(indiceFil).getId(); break;
		 	case 1: s = this.lista.get(indiceFil).getSource().getId(); break;
		 	case 2: s = this.lista.get(indiceFil).getTarget().getId(); break;
		 	case 3: s = this.lista.get(indiceFil).getLength(); break;
		 	case 4: s = this.lista.get(indiceFil).getMaxSpeed(); break;
		 	case 5: s = vehiculosToString(indiceFil); break;
		 	default: assert(false);
		 }
		 return s;
	 }
	 
	 private String vehiculosToString(int indiceFil) {
		 String s = "[";
	 		List<Vehiculo> v = this.lista.get(indiceFil).getVehicles();
	 		for(int i = 0; i < v.size(); ++i) {
	 			s += v.get(i).toString();
	 			if(i < v.size() - 1)
	 				s += ",";
	 		}
	 		s += "]";
	 		return s;
	 }
	 
	 @Override
	 public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		 SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				lista = mapa.getCarreteras(); 
				fireTableDataChanged();
			}
		 });
	 }
	 
	 @Override
	 public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		 SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					lista = mapa.getCarreteras(); 
					fireTableDataChanged();
				}
			 });
	 }
	 
	 
	 @Override
	 public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		 SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					lista = mapa.getCarreteras(); 
					fireTableDataChanged();
				}
			 });
	 }
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {}
}
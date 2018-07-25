package views.tablas;

import java.util.List;
import javax.swing.SwingUtilities;

import control.Controlador;
import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;

public class ModeloTablaVehiculos extends ModeloTabla<Vehiculo> {
	public ModeloTablaVehiculos(String [] columnIdVehiculos, Controlador ctrl) {
		super(columnIdVehiculos, ctrl);
	}
	public Object getValueAt(int indiceFil, int indiceCol) {
		 Object s = null;
		 switch (indiceCol) {
		 	case 0: s = this.lista.get(indiceFil).getId(); break;
		 	case 1: 
		 		 if(this.lista.get(indiceFil).getCarreteraActual() != null)
		 			s = this.lista.get(indiceFil).getCarreteraActual().getId();
		 		 else s = ""; 
		 		 break;
		 	case 2: 
		 		if(this.lista.get(indiceFil).getLocalizacion()!=-1)
		 			s = this.lista.get(indiceFil).getLocalizacion();
		 		else s = "arrived"; 
		 		break;
		 	case 3: s = this.lista.get(indiceFil).getVelocidadActual(); break;
		 	case 4: s = this.lista.get(indiceFil).getKilometraje(); break;
		 	case 5: s = this.lista.get(indiceFil).getTiempoDeAveria(); break;
		 	case 6: s = vehiculos(indiceFil); break;
		 	default: assert(false);
		 }
		 return s;
	 }
	
	private String vehiculos(int indiceFil) {
		String s = "[";
		
		List<CruceGenerico<?>> v = this.lista.get(indiceFil).getItinerario();
		for(int i = 0; i < v.size(); ++i) {
			s += v.get(i).getId();
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
				lista = mapa.getVehiculos(); 
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
					lista = mapa.getVehiculos(); 
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
					lista = mapa.getVehiculos(); 
					fireTableDataChanged();
				}
			 });
	 }
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {}
}


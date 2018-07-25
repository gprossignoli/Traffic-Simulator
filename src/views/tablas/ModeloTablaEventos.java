package views.tablas;

import java.util.List;

import control.Controlador;
import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;

public class ModeloTablaEventos extends ModeloTabla<Evento> {
	 public ModeloTablaEventos(String[] columnIdEventos, Controlador ctrl) { 
		 super(columnIdEventos, ctrl);
	 }
	 @Override // necesario para que se visualicen los datos
	 public Object getValueAt(int indiceFil, int indiceCol) {
		 Object s = null;
		 switch (indiceCol) {
		 	case 0: s = indiceFil; break;
		 	case 1: s = this.lista.get(indiceFil).getTiempo(); break;
		 	case 2: s = this.lista.get(indiceFil).getNombreEvento(); break;
		 	default: assert(false);
		 }
		 return s;
	 }
	 
	 @Override
	 public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {

	 }
	 
	 @Override
	 public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		 this.lista = eventos;
		 this.fireTableDataChanged();
	 }
	 
	 
	 @Override
	 public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		 this.lista = eventos; 
		 this.fireTableDataChanged();
	 }
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {}
}
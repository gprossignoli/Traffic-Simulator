package views.componentes;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import control.Controlador;
import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.ObservadorSimuladorTrafico;
import util.Utils;
import views.VentanaPrincipal;

public class BarraHerramientas extends JToolBar implements ObservadorSimuladorTrafico {
	private JSpinner steps;
	private JSpinner delay;
	private JTextField time;
	public BarraHerramientas(VentanaPrincipal mainWindow, Controlador controlador) throws Exception{
		super();
		controlador.addObserver(this);
		//Cargar Eventos
		JButton botonCargar = new JButton();
		botonCargar.setToolTipText("Carga un fichero de eventos");
		botonCargar.setIcon(new ImageIcon(Utils.loadImage("resources/icons/open.png")));
		botonCargar.addActionListener(new ActionListener() {
		@Override
		 public void actionPerformed(ActionEvent e) {
			mainWindow.cargaFichero();
			
		 }
		});
		this.add(botonCargar);
		
		//Guardar fichero eventos
		JButton  botonGuardarEventos = new JButton();
		botonGuardarEventos.setToolTipText("Guardar eventos");
		botonGuardarEventos.setIcon(new ImageIcon(Utils.loadImage("resources/icons/save.png")));
		botonGuardarEventos.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mainWindow.salvarFicheroEventos(null);
			}
		});
		this.add(botonGuardarEventos);
		
		//limpiar informes
		JButton botonLimpiarEventos = new JButton();
		botonLimpiarEventos.setToolTipText("Limpiar area de eventos");
		botonLimpiarEventos.setIcon(new ImageIcon(Utils.loadImage("resources/icons/clear.png")));
		botonLimpiarEventos.addActionListener(new ActionListener() {
		@Override
		 public void actionPerformed(ActionEvent e) {
			mainWindow.setPanelEditorEventos("");
		 }
		});
		this.add(botonLimpiarEventos);
		
		//insertar eventos
		JButton botonNuevoEvento = new JButton();
		botonNuevoEvento.setToolTipText("Insertar nuevos eventos");
		botonNuevoEvento.setIcon(new ImageIcon(Utils.loadImage("resources/icons/events.png")));
		botonNuevoEvento.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			mainWindow.salvarFicheroEventos(mainWindow.getFicheroActualAsFile());
			
			try {
				controlador.reinicia();
				controlador.cargaEventos(mainWindow.getFicheroActualAsInputStream());
			} catch (ErrorDeSimulacion e1) {
				// TODO Auto-generated catch block
				mainWindow.muestraDialogoError("Error al cargar los eventos");
			}
			}
		});
		this.add(botonNuevoEvento);
		
		//ejecuta simulacion
		JButton botonEjecuta = new JButton();
		botonEjecuta.setToolTipText("Ejecuta la simulacion");
		botonEjecuta.setIcon(new ImageIcon(Utils.loadImage("resources/icons/play.png")));
		botonEjecuta.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){ 
				mainWindow.executeSimulation();
			}
		});
		this.add(botonEjecuta);
		
		//parar ejecucion
		JButton botonStop = new JButton();
		botonStop.setToolTipText("Detiene la simulacion");
		botonStop.setIcon(new ImageIcon(Utils.loadImage("resources/icons/stop.png")));
		botonStop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mainWindow.stopSimulation();
			}
		});
		this.add(botonStop);
		
		//reiniciar simulacion
		JButton botonReinicia = new JButton();
		botonReinicia.setToolTipText("Reinicia la simulacion");
		botonReinicia.setIcon(new ImageIcon(Utils.loadImage("resources/icons/reset.png")));
		botonReinicia.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				controlador.reinicia();
				try{controlador.cargaEventos(mainWindow.getFicheroActualAsInputStream());}
				catch (ErrorDeSimulacion e1) {mainWindow.muestraDialogoError(e1.getMessage());}
				mainWindow.setPanelInformes("");
			}
		});
		this.add(botonReinicia);
		
		//Delay
		this.add(new JLabel(" Delay: "));
		this.delay = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
		this.delay.setToolTipText("delay a ejecutar: 1-5 segundos");
		this.delay.setMaximumSize(new Dimension(70, 70));
		this.delay.setMinimumSize(new Dimension(70, 70));
		this.delay.setValue(1);
		this.add(delay);
		
		//Spinner
		this.add(new JLabel(" Pasos: "));
		this.steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		this.steps.setToolTipText("pasos a ejecutar: 1-1000");
		this.steps.setMaximumSize(new Dimension(70, 70));
		this.steps.setMinimumSize(new Dimension(70, 70));
		this.steps.setValue(1);
		this.add(steps);
		//Tiempo
		this.add(new JLabel(" Tiempo: "));
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(70, 70));
		this.time.setMinimumSize(new Dimension(70, 70));
		this.time.setEditable(false);
		this.add(this.time);
		
		//generar informes
		JButton botonGeneraInformes = new JButton();
		botonGeneraInformes.setToolTipText("Genera los informes de la simulacion");
		botonGeneraInformes.setIcon(new ImageIcon(Utils.loadImage("resources/icons/report.png")));
		botonGeneraInformes.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mainWindow.generaInformes();
			}
		});
		this.add(botonGeneraInformes);
		
		//limpiar informes
		JButton botonLimpiaInformes = new JButton();
		botonLimpiaInformes.setToolTipText("Limpia los informes");
		botonLimpiaInformes.setIcon(new ImageIcon(Utils.loadImage("resources/icons/delete_report.png")));
		botonLimpiaInformes.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mainWindow.setPanelInformes("");
			}
		});
		this.add(botonLimpiaInformes);
		
		//guardar informes
		JButton botonGuardaInformes = new JButton();
		botonGuardaInformes.setToolTipText("Guarda los informes de la simulacion");
		botonGuardaInformes.setIcon(new ImageIcon(Utils.loadImage("resources/icons/save_report.png")));
		botonGuardaInformes.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mainWindow.salvaFicheroInformes();			
			}
		});
		this.add(botonGuardaInformes);
		
		//salir
		JButton botonSalir = new JButton();
		botonSalir.setToolTipText("Cerrar la ventana");
		botonSalir.setIcon(new ImageIcon(Utils.loadImage("resources/icons/exit.png")));
		botonSalir.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mainWindow.quit();			
			}
		});
		this.add(botonSalir);
	}	
		
	
	//metodos de clase 
	
	//este metodo es totalmente dependiente de la posicion del boton stop, si el boton cambia de posicion se debe actualizar el if del bucle
	public void desactivaBotones(Boolean b) { //si b == false entonces se desactiva
		for(int i = 0; i < this.getComponentCount();++i) {
			if(i != 5) { //se evita desactivar Stop pero esto es totalmente dependiente de la colocacion de Stop.
				this.getComponentAtIndex(i).setEnabled(b);
			}
		}
	}
	
	public JSpinner getSteps() {
		return steps;
	}
	
	public JSpinner getDelay() {
		return delay;
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {}
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		 this.time.setText(""+(tiempo+1));
	}
	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {}
	
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		 this.steps.setValue(1);
		 this.time.setText("0");
		 this.delay.setValue(1);
	}
	
	
	
}
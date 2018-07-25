package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import control.Controlador;
import control.events.Evento;
import exceptions.ErrorDeSimulacion;
import model.MapaCarreteras;
import model.ObservadorSimuladorTrafico;
import model.carreteras.Carretera;
import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;
import views.componentes.BarraHerramientas;
import views.componentes.BarraMenu;
import views.componentes.ComponenteMapa;
import views.paneles.PanelAreaTexto;
import views.paneles.PanelBarraEstado;
import views.paneles.PanelEditorEventos;
import views.paneles.PanelInformes;
import views.paneles.PanelTabla;
import views.tablas.ModeloTablaCarreteras;
import views.tablas.ModeloTablaCruces;
import views.tablas.ModeloTablaEventos;
import views.tablas.ModeloTablaVehiculos;




public class VentanaPrincipal extends JFrame implements ObservadorSimuladorTrafico {
	
	public static Border bordePorDefecto = BorderFactory.createLineBorder(Color.black, 2);
			// SUPERIOR PANEL
			static private final String[] columnIdEventos = { "#", "Tiempo", "Tipo" };
			private PanelAreaTexto panelEditorEventos;
			private PanelAreaTexto panelInformes;
			private PanelTabla<Evento> panelColaEventos;
			// MENU AND TOOL BAR
			private JFileChooser fc;
			private BarraHerramientas toolbar;
			// GRAPHIC PANEL
			private ComponenteMapa componenteMapa;
			// STATUS BAR (INFO AT THE BOTTOM OF THE WINDOW)
			private PanelBarraEstado panelBarraEstado;
			// INFERIOR PANEL
			static private final String[] columnIdVehiculo = { "ID", "Carretera",
			"Localizacion", "Vel.", "Km", "Tiempo. Ave.", "Itinerario" };
			static private final String[] columnIdCarretera = { "ID", "Origen",
			 "Destino", "Longitud", "Vel. Max", "Vehiculos" };
			static private final String[] columnIdCruce = { "ID", "Verde", "Rojo" };
			private PanelTabla<Vehiculo> panelVehiculos;
			private PanelTabla<Carretera> panelCarreteras;
			private PanelTabla<CruceGenerico<?>> panelCruces;
			// REPORT DIALOG
			//private DialogoInformes dialogoInformes; // opcional
			// MODEL PART - VIEW CONTROLLER MODEL
			private File ficheroActual;
			private Controlador controlador;
			private WindowListener addWindowListener;
			private Thread hiloSimulacion;
			
			public VentanaPrincipal(String ficheroEntrada,Controlador ctrl) throws Exception {
					 super("Simulador de Trafico");
					 this.controlador = ctrl;
					 this.ficheroActual = ficheroEntrada != null ? new File(ficheroEntrada) : null;
					 this.initGUI();
					 this.cargaFicheroInicial();	
						
					 // añadimos la ventana principal como observadora
					 ctrl.addObserver(this);
			}
			
			private void initGUI() throws Exception {
			
				
				 this.addWindowListener = new WindowListener() {

					@Override
					public void windowOpened(WindowEvent e) {}

					@Override
					public void windowClosing(WindowEvent e) {
						quit();
					}

					@Override
					public void windowClosed(WindowEvent e) {}

					@Override
					public void windowIconified(WindowEvent e) {}

					@Override
					public void windowDeiconified(WindowEvent e) {}

					@Override
					public void windowActivated(WindowEvent e) {}

					@Override
					public void windowDeactivated(WindowEvent e) {}
				 };
				 this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				 this.addWindowListener(addWindowListener);
				
				 JPanel panelPrincipal = this.creaPanelPrincipal();
				 this.setContentPane(panelPrincipal);
				 // BARRA DE ESTADO INFERIOR
				 // (contiene una JLabel para mostrar el estado delsimulador)
				 this.addBarraEstado(panelPrincipal);
				 // PANEL QUE CONTIENE EL RESTO DE COMPONENTES
				 // (Lo dividimos en dos paneles (superior e inferior)
				 JPanel panelCentral = this.createPanelCentral();
				 panelPrincipal.add(panelCentral,BorderLayout.CENTER);
				// PANEL SUPERIOR
				 this.createPanelSuperior(panelCentral);
				 // MENU
				 BarraMenu menubar = new BarraMenu(this,this.controlador);
				 this.setJMenuBar(menubar);
				// PANEL INFERIOR
				 this.createPanelInferior(panelCentral);
				 // BARRA DE HERRAMIENTAS
				 this.addToolBar(panelPrincipal);
				 // FILE CHOOSER
				 this.fc = new JFileChooser();
				 // REPORT DIALOG (OPCIONAL)
				// this.dialogoInformes = new DialogoInformes(this,this.controlador);
				 this.pack();
				 this.setVisible(true);
				}

			private JPanel creaPanelPrincipal() {
				JPanel mainPanel = new JPanel();
				mainPanel.setLayout(new BorderLayout());
				return mainPanel;
				
			}
			private JPanel createPanelCentral() {
				JPanel mainPanel = new JPanel();
				mainPanel.setLayout(new GridLayout(2,1));
				return mainPanel;
			}

			private void createPanelSuperior(JPanel panelCentral) throws Exception {
				JPanel supPanel = new JPanel();
				//BoxLayout supPanel = new BoxLayout(getContentPane(),BoxLayout.LINE_AXIS);
				supPanel.setLayout(new BoxLayout(supPanel,BoxLayout.LINE_AXIS));
				String texto = "";
				String titulo = "";
				try {
				 texto = this.leeFichero(this.ficheroActual);
				 titulo = "Evento: " + ficheroActual.getName();
				} catch (FileNotFoundException e) {
				 this.ficheroActual = null;
				 this.muestraDialogoError("Error durante la lectura del fichero: " + e.getMessage());
				}
				this.panelEditorEventos = new PanelEditorEventos(titulo,texto,true,this);
				supPanel.add(this.panelEditorEventos);
				this.panelColaEventos = new PanelTabla<Evento>("Cola Eventos: ",
					new ModeloTablaEventos(VentanaPrincipal.columnIdEventos, this.controlador));
				supPanel.add(panelColaEventos);
				this.panelInformes = new PanelInformes("Informes: ",false, this.controlador);
				supPanel.add(panelInformes);
				panelCentral.add(supPanel);
				
			}
			
			private void createPanelInferior(JPanel panelCentral){
				JPanel panelInferior = new JPanel();
				panelInferior.setLayout(new BoxLayout(panelInferior,BoxLayout.LINE_AXIS));
				JPanel left = new JPanel();
				left.setLayout(new GridLayout(3,1));
				this.panelVehiculos = new PanelTabla<Vehiculo>("Vehiculos",
					new ModeloTablaVehiculos(VentanaPrincipal.columnIdVehiculo,
						 this.controlador));
				left.add(panelVehiculos);
				this.panelCarreteras = new PanelTabla<Carretera>("Carretras",
					new ModeloTablaCarreteras(VentanaPrincipal.columnIdCarretera,
						 this.controlador));
				left.add(panelCarreteras);
				this.panelCruces = new PanelTabla<CruceGenerico<?>>("Cruces",
					new ModeloTablaCruces(VentanaPrincipal.columnIdCruce,
						 this.controlador));
				left.add(panelCruces);
				panelInferior.add(left);
				this.componenteMapa = new ComponenteMapa(this.controlador);
				panelInferior.add(componenteMapa);
				// a�adir un ScroolPane al panel inferior donde se coloca la
				// componente.
				panelInferior.add(new JScrollPane(componenteMapa,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
				panelCentral.add(panelInferior);
				
			}
			
			private void addBarraEstado(JPanel panelPrincipal) {
				this.panelBarraEstado = new PanelBarraEstado("Bienvenido al simulador!", this.controlador);
				// se a�ade al panel principal (el que contiene al panel
				// superior y al inferior)
				panelPrincipal.add(this.panelBarraEstado,BorderLayout.PAGE_END);
			}
			
			private void addToolBar(JPanel panelPrincipal) throws Exception {
				// TODO Auto-generated method stub
				this.toolbar = new BarraHerramientas(this, this.controlador);
				panelPrincipal.add(this.toolbar, BorderLayout.PAGE_START);
			}
			
			//metodos relacionados con uso de ficheros
			
			public void cargaFichero(){
				 int returnVal = this.fc.showOpenDialog(null);
				 if (returnVal == JFileChooser.APPROVE_OPTION) {
				 File fichero = this.fc.getSelectedFile();
				 String s = null;
				try {
					s = leeFichero(fichero);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					this.muestraDialogoError("error al leer el fichero: " + fichero);
				}
				 this.controlador.reinicia();
				 this.ficheroActual = fichero;
				 this.panelEditorEventos.setTexto(s);
				 this.panelEditorEventos.setBorde(this.ficheroActual.getName());
				 this.panelBarraEstado.setMensaje("Fichero " + fichero.getName() +
				 " de eventos cargado en el editor");
				 try {
						controlador.cargaEventos(getFicheroActualAsInputStream());
					} catch (ErrorDeSimulacion e1) {
						// TODO Auto-generated catch block
						muestraDialogoError("Error al cargar los eventos");
					}
				 }
				 
				}
			
			public void cargaFicheroInicial(){
				 try { controlador.cargaEventos(getFicheroActualAsInputStream()); }
				 catch (ErrorDeSimulacion e1) { muestraDialogoError("Error al cargar los eventos"); }
				}

			private String leeFichero(File fichero) throws FileNotFoundException, IOException{
				String s = "";				
				try (FileInputStream fich = new FileInputStream(fichero)){
				int content;
				while((content = fich.read()) != -1){
					s += (char) content;
				}}
				return s;
			}
		
			public void salvaFicheroInformes() {
				 int returnVal = this.fc.showOpenDialog(null);
				 if (returnVal == JFileChooser.APPROVE_OPTION) {
				 File fichero = this.fc.getSelectedFile();
				 try {
					 guardarFicheroInformes(fichero);
					 this.panelBarraEstado.setMensaje("Fichero" + fichero.getName() + " guardado correctamente");
				 } catch(FileNotFoundException e) {
					 this.muestraDialogoError("Error durante el guardado: " +
							 e.getMessage());
				 	} catch(IOException e) {
				 		 this.muestraDialogoError("Error durante el guardado: " +
								 e.getMessage());
				 	}
				 }
			}
			
			private void guardarFicheroInformes(File fichero) throws IOException {
				String s = this.panelInformes.getTexto();
				try (FileOutputStream fich = new FileOutputStream(fichero)){
					for(int i = 0; i < s.length(); ++i) {
						fich.write(s.charAt(i));
					}
				} catch (FileNotFoundException e) {
					throw e;
				}
					
			}
			
			public void salvarFicheroEventos(File fichero)  {
				int returnVal;
				if(fichero == null) {
				 returnVal = this.fc.showOpenDialog(null);
				 if (returnVal == JFileChooser.APPROVE_OPTION) {
					 fichero = this.fc.getSelectedFile();
					 }
				} 
				 try {
					 guardarFicheroEventos(fichero);
					 this.panelBarraEstado.setMensaje("Fichero" + fichero.getName() + " guardado correctamente");
				 	} 
				 catch(FileNotFoundException e) {
					 this.muestraDialogoError("Error durante el guardado: " +
							 e.getMessage());
				 	} 
				 catch(IOException e) {
				 		 this.muestraDialogoError("Error durante el guardado: " +
								 e.getMessage());
				 	}
			}
			
			private void guardarFicheroEventos(File fichero) throws IOException {
				String s = this.panelEditorEventos.getTexto();
				try (FileOutputStream fich = new FileOutputStream(fichero)){
					for(int i = 0; i < s.length(); ++i) {
						fich.write(s.charAt(i));
					}
				} catch (FileNotFoundException e) {
					throw e;
				}
					
			}
			
			//metodos de ventana principal
			
			public void quit() {
				int n = JOptionPane.showOptionDialog(this, "Estas seguro de que quieres salir?", "Salir",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);
						if (n == 0) System.exit(0);
			}
	
			public void muestraDialogoError(String string) {
				Component frame = null;
				JOptionPane.showMessageDialog(frame, string, "File Error" ,JOptionPane.ERROR_MESSAGE);
			}
			
			//metodos de simulacion
			
			public void executeSimulation() {
				if(this.hiloSimulacion == null) {
					this.iniciaHebra();
					this.hiloSimulacion.start();
				}
			}
			
			public void stopSimulation() {
				this.hiloSimulacion.interrupt();
				this.hiloSimulacion = null;
				this.toolbar.desactivaBotones(true); //reactiva los botones
			}
			
			private boolean execute(int pasos,int delay) {
			 int i = -1;
			 this.toolbar.desactivaBotones(false);
			 while(++i < pasos && !Thread.interrupted()) {
							  try {
								controlador.ejecuta(1);
							  } catch (ErrorDeSimulacion e1) {
									// TODO Auto-generated catch block
									muestraDialogoError(e1.getMessage());
							  } 
				try{Thread.sleep(delay);}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Thread.currentThread().interrupt();	
					return false;
				}
			 }
			 this.toolbar.desactivaBotones(true);
			 return true;
			}
			
			void iniciaHebra() {
				 hiloSimulacion = new Thread() { 
					 public void run() {
						int pasos = getSteps();
						int delay = getDelay();
						controlador.setFicheroEntrada(getFicheroActualAsInputStream());
						execute(pasos,delay);
						
					 }
				 };
			}
			
			//metodos de informes
			
			public void generaInformes() {
				this.setPanelInformes(this.controlador.generaInformes());
			}
	
			//colocar getter y setter
			
			public void setPanelInformes(String s) {
				this.panelInformes.setTexto(s);
			}
			public void setMensaje(String string) {
				// TODO Auto-generated method stub
				this.panelBarraEstado.setMensaje(string);
			}

			public void setPanelEditorEventos(String s) {
				// TODO Auto-generated method stub
				this.panelEditorEventos.setTexto(s);
			}
			
			public int getSteps() {
				return (int) this.toolbar.getSteps().getValue();
			}
			
			public int getDelay() {
				int tmp = (int) this.toolbar.getDelay().getValue();
				return tmp * 1000;
			}
			public InputStream getFicheroActualAsInputStream() {
				// TODO Auto-generated method stub
				InputStream in = null;
				try {
					in = new FileInputStream(this.ficheroActual);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					this.muestraDialogoError("fichero no encontrado");
					//tal vez llamar a FileChooser
				}
				return in;
			}
			public File getFicheroActualAsFile() {
				return ficheroActual;
			}
			
			//metodos de observador
			
			@Override
			public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
				// TODO Auto-generated method stub
				//setMensaje(e.getMessage());
				this.muestraDialogoError(e.getMessage());
			}

			@Override
			public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
				// TODO Auto-generated method stub
				//this.panelInformes.setTexto(mapa.generateReport(tiempo));
			
			}

			@Override
			public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
				// TODO Auto-generated method stub
			}

			

			

			
}

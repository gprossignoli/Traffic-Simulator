package views.componentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import control.Controlador;
import exceptions.ErrorDeSimulacion;
import views.VentanaPrincipal;

public class BarraMenu extends JMenuBar {


	 public BarraMenu(VentanaPrincipal mainWindow, Controlador controlador) throws ErrorDeSimulacion {
	 super();
	 // MANEJO DE FICHEROS
	 JMenu menuFicheros = new JMenu("Ficheros");
	 this.add(menuFicheros);
	 this.creaMenuFicheros(menuFicheros,mainWindow);
	 // SIMULADOR
	 JMenu menuSimulador = new JMenu("Simulador");
	 this.add(menuSimulador);
	 try {
	 this.creaMenuSimulador(menuSimulador,controlador,mainWindow);
	 }catch (ErrorDeSimulacion e) { throw e;}
	 // INFORMES
	 JMenu menuReport = new JMenu("Informes");
	 this.add(menuReport);
	 this.creaMenuInformes(menuReport,mainWindow);
	 }

	 
	 private void creaMenuInformes(JMenu menuReport, VentanaPrincipal mainWindow) {
		 JMenuItem generar = new JMenuItem("Mostrar Informes de la simulacion");
			generar.setMnemonic(KeyEvent.VK_L);
			generar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
					ActionEvent.ALT_MASK));
			generar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainWindow.generaInformes();
				}
			 });
			
			JMenuItem limpiar = new JMenuItem("Limpiar informes");
			limpiar.setMnemonic(KeyEvent.VK_L);
			limpiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					ActionEvent.ALT_MASK));
			limpiar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainWindow.setPanelInformes("");
				}
			 });
			menuReport.add(generar);
			menuReport.add(limpiar);
	}


	 private void creaMenuSimulador(JMenu menuSimulador, Controlador controlador, VentanaPrincipal mainWindow) throws ErrorDeSimulacion {
			 
		 JMenuItem ejecuta = new JMenuItem("Ejecuta");
		 ejecuta.addActionListener(new ActionListener() {
		 @Override
		
		 	public void actionPerformed(ActionEvent e) {
			 	mainWindow.executeSimulation();
		 	}
		 });
		 
		 JMenuItem stop = new JMenuItem("Stop");
		 stop.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 mainWindow.stopSimulation();
			 }
		 });
		 
		 JMenuItem reinicia = new JMenuItem("Reinicia");
		 reinicia.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 controlador.reinicia();
					try{controlador.cargaEventos(mainWindow.getFicheroActualAsInputStream());}
					catch (ErrorDeSimulacion e1) {mainWindow.muestraDialogoError(e1.getMessage());}
					mainWindow.setPanelInformes("");
			 }
		 });
		 menuSimulador.add(ejecuta);
		 menuSimulador.add(stop);
		 menuSimulador.add(reinicia);
	}


	private void creaMenuFicheros(JMenu menu,VentanaPrincipal mainWindow) {
		 JMenuItem cargar = new JMenuItem("Carga Eventos");
		cargar.setMnemonic(KeyEvent.VK_L);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.ALT_MASK));
		cargar.addActionListener(new ActionListener() {
			@Override	
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		 });
		
		JMenuItem salvarInforme = new JMenuItem("Guardar Informes de la simulacion");
		salvarInforme.setMnemonic(KeyEvent.VK_L);
		salvarInforme.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.ALT_MASK));
		salvarInforme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvaFicheroInformes();
			}
		 });
		
		JMenuItem salvarEventos = new JMenuItem("Guardar eventos de la simulacion");
		salvarEventos.setMnemonic(KeyEvent.VK_L);
		salvarEventos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));
		salvarEventos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvarFicheroEventos(null);
			}
		 });
		
		JMenuItem salirSimulacion = new JMenuItem("Salir de la simulacion");
		salirSimulacion.setMnemonic(KeyEvent.VK_L);
		salirSimulacion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.ALT_MASK));
		salirSimulacion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			mainWindow.quit();	
			}
		 });
		
		
		menu.add(cargar);
		menu.add(salvarEventos);
		menu.addSeparator();
		menu.add(salvarInforme);
		menu.addSeparator();
		menu.add(salirSimulacion);
		}

}
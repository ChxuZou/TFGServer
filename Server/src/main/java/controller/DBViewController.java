package controller;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import dao.Gestion;
import dao.Partida;
import view.DBView;

public class DBViewController {
	private Gestion dbController;
	private DBView view;

	public DBViewController() {
		dbController = new Gestion();
		view = new DBView(this);
	}

	public void init() {
		SwingUtilities.invokeLater(() -> {
			JFrame.setDefaultLookAndFeelDecorated(true);
			view.setVisible(true);
			
			UIManager.put("OptionPane.yesButtonText", "Cerrar");
			UIManager.put("OptionPane.noButtonText", "Cancelar");
			
			view.addWindowListener(new java.awt.event.WindowAdapter() {
	            @Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	                if (JOptionPane.showConfirmDialog(view, 
	                    "¿Estás seguro que quieres cerrar esta ventana? \nEl servidor seguirá ejecutándose en segundo plano", "Cerrar ventana", 
	                    JOptionPane.YES_NO_OPTION,
	                    JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){
	                    view.dispose();
	                }
	            }
	        });
		});
		
	}

	public void showAllData() {
		List<Partida> games = dbController.buscarTodos();
		view.updateTable(games);
	}

	public void showByID(int id) {
		List<Partida> games = dbController.buscarID(id);
		view.updateTable(games);
	}

	public void showByName(String name) {
		List<Partida> games = dbController.buscarNombre(name);
		view.updateTable(games);
	}

}

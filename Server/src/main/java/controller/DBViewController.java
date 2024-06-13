package controller;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
			view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			view.setVisible(true);
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

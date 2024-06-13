import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import dao.Gestion;
import dao.Partida;

public class DBViewController {
	private Gestion dbController;
	private JFrame view;

	public DBViewController() {
		dbController = new Gestion();
		view = new JFrame();
	}

	public void init() {
		SwingUtilities.invokeLater(() -> {
			view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			view.setSize(800, 600);

			view.setVisible(true);
		});
	}

	public void showAllData() {
		List<Partida> games = dbController.buscarTodos();

		// view.updateTable(games);
	}

	public void showByID(int id) {
		List<Partida> games = dbController.buscarID(id);

		// view.updateTable(games);
	}

	public void showByName(String name) {
		List<Partida> games = dbController.buscarNombre(name);

		// view.updateTable(games);
	}

}

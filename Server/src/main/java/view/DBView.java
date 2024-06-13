package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.DBViewController;
import dao.Partida;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DBView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField findByIdTextField;
	private JTextField findByNameTextField;
	private DBViewController controller;

	public DBView(DBViewController controller) {
		this.controller = controller;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 93, 542, 267);
		contentPane.add(scrollPane);

		List<Partida> partidas = new ArrayList<>();

		table = new JTable();
		updateTable(partidas);
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 542, 80);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton searchBtn = new JButton("Buscar");

		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (findByIdTextField.getText().isEmpty() && findByNameTextField.getText().isEmpty()) {
					System.out.println("Buscando por todo");
					controller.showAllData();
					return;
				}

				if (!findByIdTextField.getText().isEmpty()) {
					System.out.println("Buscando por id");
					Integer id = Integer.parseInt(findByIdTextField.getText());
					controller.showByID(id);
					return;
				}

				if (!findByNameTextField.getText().isEmpty()) {
					System.out.println("Buscando por nombre");
					String nombre = findByNameTextField.getText();
					controller.showByName(nombre);
					return;
				}

			}
		});
		searchBtn.setBounds(424, 28, 89, 23);
		panel.add(searchBtn);

		JPanel findByIdPanel = new JPanel();
		findByIdPanel.setBorder(
				new TitledBorder(null, "Buscar por ID ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		findByIdPanel.setBounds(10, 11, 186, 54);
		panel.add(findByIdPanel);
		findByIdPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		findByIdTextField = new NumericTextField();
		findByIdPanel.add(findByIdTextField);
		findByIdTextField.setColumns(10);
		findByIdTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (findByIdTextField.getText().isEmpty()) {
					findByNameTextField.setEditable(true);
					findByNameTextField.setEnabled(true);
				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				findByNameTextField.setEditable(false);
				findByNameTextField.setEnabled(false);

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// Do nothing
			}
		});

		JPanel findByNamePanel = new JPanel();
		findByNamePanel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Buscar por jugador", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		findByNamePanel.setBounds(206, 11, 186, 54);
		panel.add(findByNamePanel);

		findByNameTextField = new JTextField();
		findByNamePanel.add(findByNameTextField);
		findByNameTextField.setColumns(10);

		findByNameTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (findByNameTextField.getText().isEmpty()) {
					findByIdTextField.setEditable(true);
					findByIdTextField.setEnabled(true);
				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				findByIdTextField.setEditable(false);
				findByIdTextField.setEnabled(false);

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// Do nothing
			}
		});

	}

	public void updateTable(List<Partida> games) {
		String[] columnNames = { "ID", "Jugador 1", "Jugador 2", "Resultado", "Tablero", "Hora" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// Todas las celdas no son editables
				return false;
			}
		};

		for (Partida game : games) {
			int id = game.getId();
			String jugador1 = game.getJugador1();
			String jugador2 = game.getJugador2();
			String resultado = game.getResultado();
			String tablero = formatTablero(game.getTablero());
			String hora = game.getHora();

			Object[] row = { id, jugador1, jugador2, resultado, tablero, hora };
			model.addRow(row);
		}

		table.setModel(model);

		table.setDefaultRenderer(Object.class, new TableCellRenderer() {
			private JTextArea textArea = new JTextArea();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				textArea.setText((String) value.toString());
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				JPanel panel = new JPanel(new BorderLayout());
				panel.add(textArea, BorderLayout.CENTER);
				panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
				panel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
				return panel;
			}
		});

		// Ajustar la altura de las filas después del renderizado
		for (int row = 0; row < table.getRowCount(); row++) {
			int rowHeight = table.getRowHeight();

			for (int column = 0; column < table.getColumnCount(); column++) {
				Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
				rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			}

			table.setRowHeight(row, rowHeight);
		}
	}

	private String formatTablero(String tablero) {
		String[] rows = tablero.split("\\|");
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < rows.length; i++) {
			if (i > 0) {
				sb.append("- - - - - \n");
			}
			for (int j = 0; j < rows[i].length(); j++) {
				sb.append(rows[i].charAt(j));
				if (j < rows[i].length() - 1) {
					sb.append(" | ");
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}

}

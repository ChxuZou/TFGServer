package view;

import java.awt.BorderLayout;
import java.awt.Component;
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
	private final String[] columnNames = { "ID", "Jugador 1", "Jugador 2", "Resultado", "Tablero", "Hora" };
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private DBViewController controller;
	
	private JScrollPane tableScrollPane;
	private JTable table;
	private DefaultTableModel model;
	
	private JPanel searchPanel;
	private JPanel idPanel;
	private JTextField idTextField;
	private JPanel namePanel;
	private JTextField nameTextField;
	private JButton searchBtn;

	public DBView(DBViewController controller) {
		this.controller = controller;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 578, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.model = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// Todas las celdas no son editables
				return false;
			}
		};

		initTableComponent();
		initSearchComponent();

	}

	private void initSearchComponent() {
		searchPanel = new JPanel();
		searchPanel.setBounds(10, 11, 542, 80);
		contentPane.add(searchPanel);
		searchPanel.setLayout(null);

		initSearchBtn();
		initIdTextField();
		initNameTextField();
	}

	private void initNameTextField() {
		namePanel = new JPanel();
		namePanel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Buscar por jugador", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		namePanel.setBounds(206, 11, 186, 54);
		searchPanel.add(namePanel);

		nameTextField = new JTextField();
		namePanel.add(nameTextField);
		nameTextField.setColumns(10);

		nameTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (nameTextField.getText().isEmpty()) {
					idTextField.setEditable(true);
					idTextField.setEnabled(true);
				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				idTextField.setEditable(false);
				idTextField.setEnabled(false);

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// Do nothing
			}
		});
	}

	private void initIdTextField() {
		idPanel = new JPanel();
		idPanel.setBorder(
				new TitledBorder(null, "Buscar por ID ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		idPanel.setBounds(10, 11, 186, 54);
		searchPanel.add(idPanel);
		idPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		idTextField = new NumericTextField();
		idPanel.add(idTextField);
		idTextField.setColumns(10);
		idTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (idTextField.getText().isEmpty()) {
					nameTextField.setEditable(true);
					nameTextField.setEnabled(true);
				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				nameTextField.setEditable(false);
				nameTextField.setEnabled(false);

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// Do nothing
			}
		});
	}

	private void initSearchBtn() {
		searchBtn = new JButton("Buscar");

		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idTextField.getText().isEmpty() && nameTextField.getText().isEmpty()) {
					System.out.println("Buscando por todo");
					controller.showAllData();
					return;
				}

				if (!idTextField.getText().isEmpty()) {
					System.out.println("Buscando por id");
					Integer id = Integer.parseInt(idTextField.getText());
					controller.showByID(id);
					return;
				}

				if (!nameTextField.getText().isEmpty()) {
					System.out.println("Buscando por nombre");
					String nombre = nameTextField.getText();
					controller.showByName(nombre);
					return;
				}

			}
		});
		
		searchBtn.setBounds(424, 28, 89, 23);
		searchPanel.add(searchBtn);
	}

	private void initTableComponent() {
		tableScrollPane = new JScrollPane();
		tableScrollPane.setBounds(10, 93, 542, 267);
		contentPane.add(tableScrollPane);

		table = new JTable();
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
		updateTable(null);
		
		tableScrollPane.setViewportView(table);
	}

	public void updateTable(List<Partida> games) {
		//Limpia los datos existentes
		this.model.setRowCount(0);
		
		if(games != null) {
			
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
		
		table.setModel(model);


	}

	private String formatTablero(String tablero) {
		String[] rows = tablero.split("\\|");
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < rows.length; i++) {
			if (i > 0) {
				sb.append("- - - - - \n");
			}
			for (int j = 0; j < rows[i].length(); j++) {
				char cell = (rows[i].charAt(j) != '-') ? rows[i].charAt(j) : ' ';
				sb.append(cell);
				if (j < rows[i].length() - 1) {
					sb.append(" | ");
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}

}

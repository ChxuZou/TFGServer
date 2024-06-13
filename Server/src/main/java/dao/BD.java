package dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BD {
	
	private static Connection conn = null;

	/**
	 * Establece una conexión con la base de datos
	 */
	private BD() {
		try {
			Properties prop = new Properties();
			prop.load(new FileReader("database.properties"));

			String driver = prop.getProperty("driver");
			String dsn = prop.getProperty("dsn");
			String user = prop.getProperty("user", "");
			String pass = prop.getProperty("pass", "");
			Class.forName(driver);
			conn = DriverManager.getConnection(dsn, user, pass);

			createTable();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve una conexión a la base de datos
	 * 
	 * @return Conexión a la base de datos
	 */
	public static Connection getConnection() {
		if (conn == null) {
			new BD();
		}
		return conn;
	}

	/**
	 * Cierra la conexión
	 */
	public static void close() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Crea el esquema de la base de datos
	 * 
	 * @throws SQLException
	 */
	private void createTable() {
		String sqls = """
					CREATE TABLE IF NOT EXISTS partida (
						id INTEGER PRIMARY KEY,
						jugador1 TEXT UNIQUE NOT NULL,
						jugador2 TEXT DEFAULT NULL,
						resultado TEXT NOT NULL,
						tablero TEXT NOT NULL,
						hora TEXT NOT NULL
					)
				""";
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sqls);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

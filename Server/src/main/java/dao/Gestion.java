package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Gestion {

	private Connection conn = null;

	public Gestion() {
		conn = BD.getConnection();
	}

	public List<Partida> imprimir() {
		List<Partida> partidas = new ArrayList<>();
		String sql = """
				SELECT p.id AS id,
				p.jugador1 AS jugador1,
				p.jugador2 AS jugador2,
				p.tablero AS tablero,
				p.resultado AS resultado,
				p.hora AS hora
				FROM partida p
				""";
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				partidas.add(lectura(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partidas;

	}

	public boolean insertar(Partida p) {
		String sql = """
				INSERT INTO partida (jugador1, jugador2, resultado, tablero, hora)
				VALUES (?, ?, ?, ?, ?)
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, p.getJugador1());
			ps.setString(2, p.getJugador2());
			ps.setString(3, p.getResultado());
			ps.setString(4, p.getTablero());
			ps.setString(5, p.getHora());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Partida> buscarID(int id) {
		List<Partida> partidas = new ArrayList<>();
		String sql = """
				SELECT p.id AS id,
				p.jugador1 AS jugador1,
				p.jugador2 AS jugador2,
				p.tablero AS tablero,
				p.resultado AS resultado,
				p.hora AS hora
				FROM partida p
				WHERE p.id = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				partidas.add(lectura(rs));
			}
		} catch (SQLException e) {
		}
		return partidas;
	}

	public List<Partida> buscarNombre(String nombre) {
		List<Partida> partidas = new ArrayList<>();
		String sql = """
				SELECT p.id AS id,
				p.jugador1 AS jugador1,
				p.jugador2 AS jugador2,
				p.tablero AS tablero,
				p.resultado AS resultado,
				p.hora AS hora
				FROM partida p
				WHERE p.jugador1 LIKE ? OR p.jugador2 LIKE ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.setString(2, nombre);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				partidas.add(lectura(rs));
			}
		} catch (SQLException e) {
		}
		return partidas;
	}
	
	public List<Partida> buscarTodos() {
		List<Partida> partidas = new ArrayList<>();
		String sql = """
				SELECT p.id AS id,
				p.jugador1 AS jugador1,
				p.jugador2 AS jugador2,
				p.tablero AS tablero,
				p.resultado AS resultado,
				p.hora AS hora
				FROM partida p
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				partidas.add(lectura(rs));
			}
		} catch (SQLException e) {
		}
		return partidas;
	}


	private Partida lectura(ResultSet rs) {
		try {
			Integer id = rs.getInt("id");
			String jugador1 = rs.getString("jugador1");
			String jugador2 = rs.getString("jugador2");
			String hora = rs.getString("hora");
			String tablero = rs.getString("tablero");
			String resultado = rs.getString("resultado");
			return new Partida(id, jugador1, jugador2, resultado, tablero, hora);
		} catch (SQLException e) {
		}
		return null;
	}

}

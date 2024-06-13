package dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

public class Partida {

	@Getter
	@Setter
	private String resultado;
	@Getter
	@Setter
	private String jugador1;
	@Getter
	@Setter
	private String jugador2;
	@Getter
	@Setter
	private int id;
	@Getter
	@Setter
	private String tablero;
	@Getter
	private String hora;

	public Partida(int id, String jugador1, String jugador2, String resultado, String tablero, String hora) {
		this.resultado = resultado;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.id = id;
		this.tablero = tablero;
		this.hora = hora;
	}

	public Partida(String jugador1, String jugador2, String resultado, String tablero) {
		this.resultado = resultado;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.tablero = tablero;
		setHora();
	}

	public Partida(int id, String jugador1, String jugador2, String resultado, String tablero) {
		this.resultado = resultado;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.id = id;
		this.tablero = tablero;
		setHora();
	}

	public void setHora() {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		this.hora = ldt.format(dtf);
	}

}

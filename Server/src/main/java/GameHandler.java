import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import dao.Gestion;
import dao.Partida;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class GameHandler extends Thread {
	private Socket player1;
	private Socket player2;
	private Board board;
	private HashMap<CellState, Socket> playerFiguras;
	private HashMap<Socket, String> playerNames;
	private Gestion bdManager;

	public GameHandler(Socket player1, Socket player2) {
		this.player1 = player1;
		this.player2 = player2;
		board = new Board();
		playerFiguras = new HashMap<CellState, Socket>();
		playerNames = new HashMap<Socket, String>();
		this.bdManager = new Gestion();
	}

	private CellState assignRandomCellState() {
		return Math.random() < 0.5 ? CellState.CIRCLE : CellState.CROSS;
	}

	private CellState assignOtherCellState(CellState cellState) {
		return cellState.equals(CellState.CIRCLE) ? CellState.CROSS : CellState.CIRCLE;
	}

	@Override
	public void run() {

		// Asignación de una pieza random
		CellState pieza1 = assignRandomCellState();
		CellState pieza2 = assignOtherCellState(pieza1);

		playerFiguras.put(pieza1, player1);
		playerFiguras.put(pieza2, player2);

		try {
			// Entrada y salida de datos
			BufferedReader input1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
			PrintWriter output1 = new PrintWriter(player1.getOutputStream(), true);
			BufferedReader input2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
			PrintWriter output2 = new PrintWriter(player2.getOutputStream(), true);

			// Recogida de nombres de los jugadores
			playerNames.put(player1, input1.readLine());
			playerNames.put(player2, input2.readLine());

			System.out.println("Nombres de jugadores");
			System.out.println("Jugador 1 " + playerNames.get(player1));
			System.out.println("Jugador 2 " + playerNames.get(player2));

			// Se asigna una pieza random para ver quien empieza
			CellState whoPlays = assignRandomCellState();

			boolean turn = player1.equals(playerFiguras.get(whoPlays));

			String baseMessage = playerNames.get(player1) + " " + playerNames.get(player2) + " ";
			String messageToPlayer1 = baseMessage + pieza1.toString() + " " + turn;
			String messageToPlayer2 = baseMessage + pieza2.toString() + " " + !turn;

			output1.println(messageToPlayer1);
			output2.println(messageToPlayer2);

			// TODO Meter logica con listenner para comprobar la conexión de ambos jugadores
			// Si alguno se desconecta, pierde directamente
			// Se puede usar un try catch () y comprobar cual de los dos se ha desconectado
			// Ejem: this.player1.isClosed();

			BufferedReader currentPlayerInput;
			PrintWriter otherPlayerOutPut;
			String[] message;
			Movement movement;
			String playerName;

			do {
				currentPlayerInput = new BufferedReader(
						new InputStreamReader(playerFiguras.get(whoPlays).getInputStream()));
				otherPlayerOutPut = new PrintWriter(playerFiguras.get(assignOtherCellState(whoPlays)).getOutputStream(),
						true);

				// Formato de coordenadas 0 0
				message = currentPlayerInput.readLine().split(" ");
				playerName = playerNames.get(playerFiguras.get(whoPlays));

				movement = new Movement(whoPlays, Integer.parseInt(message[0]), Integer.parseInt(message[1]));

				board.makeMove(movement.getRow(), movement.getCol(), movement.getFigure());

				otherPlayerOutPut.println(movement.toString());

				whoPlays = assignOtherCellState(whoPlays);
			} while (!board.hasEnded());

			if (board.checkWin()) {
				// Enviar Mensaje de que se ha terminado por ganador
				System.out.println("Hay ganador:" + playerName);
				output1.println(movement.toString() + " " + playerName);
				output2.println(movement.toString() + " " + playerName);

				Partida game = new Partida(playerNames.get(player1), playerNames.get(player2), "win:" + playerName,
						board.toString());
				bdManager.insertar(game);

			} else {
				output1.println(movement.toString() + " " + null);
				output2.println(movement.toString() + " " + null);

				Partida game = new Partida(playerNames.get(player1), playerNames.get(player2), "draw",
						board.toString());
				bdManager.insertar(game);
			}

			input1.close();
			input2.close();

			output1.close();
			output2.close();

			// Cierre de los sockets
			player1.close();
			player2.close();
		} catch (IOException e) {
			System.out.println("Uno de los jugadores se ha desconectado durante la partida");
		}
	}

}

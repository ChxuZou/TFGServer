package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server extends Thread {

	private ServerSocket serverSocket;
	private BlockingQueue<Socket> waitingPlayers;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		waitingPlayers = new LinkedBlockingQueue<>();

	}

	@Override
	public void run() {
		System.out.println("Conectando servidor en puerto " + serverSocket.getLocalPort());

		while (true) {
			try {
				Socket playerSocket = serverSocket.accept();
				waitingPlayers.put(playerSocket);
				System.out.println(playerSocket.getPort()+ " en cola");

				if (waitingPlayers.size() >= 2) {
					System.out.println("Iniciando emparejamiento");
					new GameHandler(waitingPlayers.take(), waitingPlayers.take()).start();
				}

			} catch (IOException | InterruptedException e) {

			}
		}

	}

}

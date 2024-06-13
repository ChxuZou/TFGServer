import java.io.IOException;

import controller.DBViewController;

public class Main {
	private static final int PORT = 8080;
	public static void main(String[] args) {

		try {
			Server server = new Server(PORT);
			server.start();
			
			DBViewController controller = new DBViewController();
			controller.init();
		} catch (IOException e) {
			System.out.println("No se pudo iniciar el servidor en el puerto "+ PORT);
		}

	}

}

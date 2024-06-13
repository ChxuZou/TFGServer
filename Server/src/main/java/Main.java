import java.io.IOException;

import controller.DBViewController;

public class Main {
	private static final int PORT = 8080;
	public static void main(String[] args) {

		try {
			DBViewController controller = new DBViewController();
			Server server = new Server(PORT);
			
			controller.init();
			server.start();
			

			server.join();
		} catch (IOException e) {
			System.out.println("No se pudo iniciar el servidor en el puerto "+ PORT);
		} catch(InterruptedException e) {
			System.out.println("Se ha cerrado el server antes de tiempo");
		}

	}

}

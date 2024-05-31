import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import java.net.Socket;
import java.io.IOException;

public class Cliente {

	private Socket socket;
	private CodedOutputStream out;
	private CodedInputStream in;

	public Cliente(String host, int port) throws IOException {
		socket = new Socket(host, port);
		out = CodedOutputStream.newInstance(socket.getOutputStream());
		in = CodedInputStream.newInstance(socket.getInputStream());
	}

	public void send(String message) throws IOException {
		out.writeStringNoTag(message);
		out.flush();
	}

	public String receive() throws IOException {
		return in.readString();
	}

	public static void main(String[] args) {
		try {
			Cliente client = new Cliente("localhost", 4000);
			client.send("Hola, soy el cliente");
			String response = client.receive();
			System.out.println("Respuesta del servidor: " + response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

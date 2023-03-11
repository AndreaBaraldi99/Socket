package es2;
import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;



public class SmartReceiverClientSocket {

	public static void main(String[] args) {
		Socket socket; // my socket
		InetAddress serverAddress; // the server address
		int serverPort; // the server port
		DataInputStream in; // the source of stream of bytes
		PrintWriter out;
		String messageString = ""; // the text to be displayed
		String[] movieList = {"Il padrino", "Forrest Gump", "Schindler's List", 
    							"Pulp Fiction", "Interstellar", "La vita è bella"};
		byte[] byteReceived = new byte[1000];

		try { // connect to the server
			serverPort = 53535;
			serverAddress = InetAddress.getByName("localhost");
			socket = new Socket(serverAddress, serverPort);
			System.out.println("Connected to: " + socket.getInetAddress());
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new DataInputStream(socket.getInputStream());

			out.write(1);
			out.write("\n");
			out.write(4);
			out.flush();
			System.out.println("Message sent");

			int response = in.read();
			in.read();

			if (response == 1) {
				int bytesRead = 0;
				bytesRead = in.read(byteReceived);
				messageString += new String(byteReceived, 0, bytesRead);				
			} else {
				System.out.println("Richiesta malformata");
				System.exit(0);
			}
			System.out.println("Response: " + messageString);		
			
			out.write(2);
			out.flush();
			out.write(4);
			out.flush();
			String chosenMovie = movieList[(int)(Math.random()*6)];
			out.write(chosenMovie);
			out.flush();
			int seats = (int)(Math.random()*200)+1;
			Thread.sleep(2000);
			out.write(seats);
			out.flush();
			System.out.println("Request sent. Movie: " + chosenMovie + ", seats: " + seats);
			int requestResponse = in.read();
			System.out.println("Response: " + requestResponse);
			System.out.println("Closing connection");

			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}


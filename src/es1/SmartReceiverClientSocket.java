package es1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;



public class SmartReceiverClientSocket {

	public static void main(String[] args) {
		Socket socket; // my socket
		InetAddress serverAddress; // the server address
		int serverPort; // the server port
		BufferedReader in; // the source of stream of bytes
		PrintWriter out;
		String messageString = ""; // the text to be displayed

		try { // connect to the server
			serverPort = 53535;
			serverAddress = InetAddress.getByName("localhost");
			socket = new Socket(serverAddress, serverPort);
			System.out.println("Connected to: " + socket.getInetAddress());
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out.write(1);
			out.write("\n");
			out.write(4);
			out.flush();
			System.out.println("Message sent");

			int response = in.read();
			in.read();

			if(response == 1){
				while(true){
					int resp = in.read();
					if(resp != -1){
						messageString += (char)resp;
					}
					else
						break;
				}
			}
			else{
				System.out.println("Malformed request");
				System.exit(0);
			}
			System.out.println("Response: " + messageString);		
			
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

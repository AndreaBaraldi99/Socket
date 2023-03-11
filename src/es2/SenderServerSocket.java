package es2;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SenderServerSocket {

	public static void main(String[] args) {
		try {
			Socket clientSocket;
			ServerSocket listenSocket;
			DataInputStream in;
			PrintWriter out;
			String[] movieList = {"Il padrino", "Forrest Gump", "Schindler's List", 
    									"Pulp Fiction", "Interstellar", "La vita è bella"};
			Sala[] dailyMovies;
			listenSocket = new ServerSocket(53535);
			System.out.println("Running Server: " + "Host=" + listenSocket.getInetAddress() + " Port="
					+ listenSocket.getLocalPort());

			while (true) {
				clientSocket = listenSocket.accept();
				System.out.println("Connected to client at port: " + clientSocket.getPort());
				//inizialize components
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new DataInputStream(clientSocket.getInputStream());
				dailyMovies = new Sala[movieList.length];
				for (int i = 0; i < movieList.length; i++){
					dailyMovies[i] = new Sala(movieList[i]);
				}
				ArrayList<Sala[]> weeklyMovies = new ArrayList<Sala[]>(7);
				for(int i = 0; i < 7; i++){
					weeklyMovies.add(i, dailyMovies);
				}
				System.out.println("Ready to receive input");
				int command = in.read();
				in.read();
				int day = in.read();			
				System.out.println("Received: " + command + " as command and " + day + " as day" );

				if(command == 1){
					switch(day){
						case 1:							
						case 2:							
						case 3:							
						case 4:							
						case 5:							
						case 6:							
						case 7:
							out.write(1);
							out.write("\n");
							for(int i = 0; i < weeklyMovies.get(day-1).length; i++){
								out.write(weeklyMovies.get(day-1)[i].getNomeFilm() + " ");
							}
							break;							
						default:
							out.write(0);
							out.write("\n");
							out.write("Error in setting the day");
							break;
					}				
				}
				else{
					out.write(0);
					out.write("\n");
					out.write("Command error");
				}
				out.flush();
				Thread.sleep(1000);
				
				int newCommand = in.read();
				int chosenDay = in.read();
				byte byteReceived[] = new byte[1024];
				String chosenMovie = "";
				int bytesRead = 0;
				bytesRead = in.read(byteReceived);
				chosenMovie += new String(byteReceived, 0, bytesRead);
				System.out.println("Received: " + chosenMovie);
				int seats = in.read();
				if(newCommand == 2){
					if(chosenDay > 0 && chosenDay < 8){
						for (Sala sala : dailyMovies) {
							if(sala.getNomeFilm().equals(chosenMovie)){
								if(sala.prenotaPosti(seats) != -1){
									out.write(1);
									System.out.println("Posti prenotati. Posti rimasti: " + sala.getPosti());
								}
							}
						}

					}
				}
				else{
					System.out.println("Errore nella richiesta inviata");
					out.write(0);
				}
				out.flush();
				in.close();
				out.close();			
				clientSocket.close();
				listenSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}

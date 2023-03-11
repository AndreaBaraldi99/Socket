package es1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SenderServerSocket {
	public static void main(String[] args) {
		try {
			Socket clientSocket;
			ServerSocket listenSocket;
			BufferedReader in;
			PrintWriter out;
			WeeklyMovie[] movies = new WeeklyMovie[7];
			for (int i = 0; i<movies.length; i++) {
				movies[i] = new WeeklyMovie();
			} 

			listenSocket = new ServerSocket(53535);
			System.out.println("Running Server: " + "Host=" + listenSocket.getInetAddress() + " Port="
					+ listenSocket.getLocalPort());

			while (true) {
				clientSocket = listenSocket.accept();
				System.out.println("Connected to client at port: " + clientSocket.getPort());
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				System.out.println("Ready to receive input");
				int command = in.read();
				in.read();
				int day = in.read();			
				System.out.println("Received: " + command + " as command and " + day + " as day" );

				if(command == 1){
					switch(day){
						case 1:
							out.write(1);
							out.write("\n");
							out.write(movies[0].getMovieList());
							break;
						case 2:
							out.write(1);
							out.write("\n");
							out.write(movies[1].getMovieList());
							break;
						case 3:
							out.write(1);
							out.write("\n");
							out.write(movies[2].getMovieList());
							break;
						case 4:
							out.write(1);
							out.write("\n");
							out.write(movies[3].getMovieList());
							break;
						case 5:
							out.write(1);
							out.write("\n");
							out.write(movies[4].getMovieList());
							break;
						case 6:
							out.write(1);
							out.write("\n");
							out.write(movies[5].getMovieList());
							break;
						case 7:
							out.write(1);
							out.write("\n");
							out.write(movies[6].getMovieList());
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
				in.close();
				out.close();			
				clientSocket.close();
				listenSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

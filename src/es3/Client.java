package es3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    
    public static void main(String[] args) {

        Socket clientSocket;
        BufferedReader in;
        PrintStream out;
        Scanner scanner;

        try{
            clientSocket = new Socket("localhost", Server.port);
            out = new PrintStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            scanner = new Scanner(System.in);

            while(true){
                String response = in.readLine();
                if(response == null || response.equals("-1") || response.equals("EOF")){
                    response = in.readLine();
                }
                System.out.println(response);
                
                while(true){
                    response = in.readLine();
                    if(response == null || response.equals("-1") || response.equals("EOF"))
                        break;
                    System.out.println(response);
                }
                
                String command = scanner.nextLine();
                if(command.equals("close connection"))
                    break;
                out.println(command);
                out.flush();
                while(true){
                    response = in.readLine();
                    if(response == null || response.equals("-1")){
                        System.out.println(response);
                        break;
                    }
                    else if(response.equals("EOF")){
                        break;
                    }
                    System.out.println(response);
                }
            }
            clientSocket.close();
            out.close();
            in.close();
            scanner.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

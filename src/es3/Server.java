package es3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {

    public static int port = 53535;
    
    public static void main(String[] args) {
        try{
            ServerSocket listenSocket = new ServerSocket(port);
            Socket clientSocket;
            BufferedReader in;
            PrintStream out;
            HashSet<Author> authors = new HashSet<Author>();
            String[] commands = {"getAllAuthors", "getAuthorId", "addAuthor", "addBook", "getAllBooks", "getBookOf", "close connection"};

            System.out.println("Running Server: " + "Host=" + listenSocket.getInetAddress() + " Port="
					+ listenSocket.getLocalPort());
            
            while(true){
                clientSocket = listenSocket.accept();
                System.out.println("Connected to client at port: " + clientSocket.getPort());
                //inizialize components
                out = new PrintStream(clientSocket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while(true){
                    out.println("Choose a command:");
                    out.flush();
                    for(int i = 0; i < commands.length; i++){
                        out.println(commands[i]);
                        out.flush();
                    }
                    out.println("EOF");
                    out.flush();
                    Thread.sleep(2000);
                    System.out.println("Ready to receive input");
                    String command = in.readLine();

                    if(command.equals(commands[6])){ //close connection
                        out.println("Closing connection");
                        out.flush();
                        Thread.sleep(1000);
                        clientSocket.close();
                        break;
                    }
                    else if(command.equals(commands[0])){ //getAllAuthors
                        out.println("All authors:");
                        out.flush();
                        for (Author author : authors) {
                            out.println(author.getName());
                            out.flush();
                        }
                    }
                    else if(command.contains(commands[1])){ //getAuthorId
                        boolean found = false;
                        for (Author author : authors) {
                            if(command.contains(author.getName())){
                                found = true;
                                out.println("Author id: " + author.getId());
                                out.flush();
                                break;
                            }
                        }
                        if(!found){
                            out.println(-1);
                            out.flush();
                        }
                    }
                    else if(command.contains(commands[2])){ //addAuthor
                        out.println("Insert author name:");
                        out.flush();
                        String authorName = command.substring(command.indexOf(" ")+1);
                        Author author = new Author(authorName);
                        boolean result = authors.add(author);
                        if(result)
                            out.println(author.getId());
                        else
                            out.println(-1);
                        out.flush();
                    }
                    else if(command.contains(commands[3])){ //addBook
                        String[] commandString = command.split("\\W+");
                        String authorName = commandString[1] + " " + commandString[2];
                        String bookTitle = commandString[3];
                        boolean found = false;
                        for (Author author : authors) {
                            if(author.getName().equals(authorName)){
                                found = author.addBook(bookTitle);
                                break;
                            }
                        }
                        out.println(found);
                        out.flush();
                    }
                    else if(command.equals(commands[4])){ //getAllBooks
                        out.println("All books:");
                        out.flush();
                        for (Author author : authors) {
                            for (Book book : author.getBooks()) {
                                out.println(author.getName() + ": " + book.getTitle());
                                out.flush();
                            }
                        }
                    }
                    else if(command.contains(commands[5])){ //getBookOf
                        String[] commandString = command.split("\\W+");
                        String authorName = commandString[1] + " " + commandString[2];
                        for (Author author : authors) {
                            if(author.getName().equals(authorName)){
                                for (Book book : author.getBooks()) {
                                    out.println(book.getTitle());
                                    out.flush();
                                }
                                break;
                            }
                        }
                    }
                    else{
                        out.println("Command not found");
                        out.flush();
                    }
                    out.println("EOF");
                    out.flush();
                    Thread.sleep(1000);
                }
                out.close();
                in.close();
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

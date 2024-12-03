// Added prompt for server IP address and defaults to localhost -moeikrey (A.S.) 12/1/24
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String host; 
        int port = 8080; 

        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter the server IP address (or press Enter for localhost): ");
            host = console.readLine();
            if (host.isEmpty()) {
                host = "localhost"; // default to localhost if no input is provided
            }
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
            return; // Exit if there's an error reading input
        }

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server!");

            // Start a thread to listen for server messages
            new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from the server.");
                }
            }).start();

            // Send commands to the server
            String userInput;
            while ((userInput = console.readLine()) != null) {
                out.println(userInput);
            }

        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
        }
    }
}

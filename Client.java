// Added prompt for server IP address and defaults to localhost -moeikrey (A.S.) 12/1/24
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for the server's IP address and port
        System.out.print("Enter the server IP address (or press Enter for localhost): ");
        String host = scanner.nextLine().trim();
        if (host.isEmpty()) {
            host = "127.0.0.1"; // Default to localhost
        }

        System.out.print("Enter the server port (default: 8080): ");
        String portInput = scanner.nextLine().trim();
        int port = portInput.isEmpty() ? 8080 : Integer.parseInt(portInput);

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to the server!");

            // Read the server's welcome message and menu
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);

                // Check if the server expects user input
                if (serverMessage.toLowerCase().contains("enter your choice")) {
                    String choice = scanner.nextLine();
                    out.println(choice);

                    // If GUI is chosen, handle GUI logic here
                    if ("2".equals(choice.trim())) {
                        System.out.println("Launching GUI...");
                        new LibraryGUI(new BookManager(), new LoanManager(), new registration());
                        break; // Exit console loop as GUI takes over
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
        }
    }
}

import java.net.*;
import java.util.HashMap;


class PhoneServer {
    private static int portNumber = 2014;
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static Rolodex rolodex = new Rolodex();

    public static void main(String[] args) throws Exception {
        // creates a ServerSocket bound to declared port number

        serverSocket = new ServerSocket(portNumber);
        System.out.println("Server is listening on port " + portNumber + "...");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    System.out.println("ServerSocket is closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        while (true) {

            // accept incoming connections from clients
            clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // create a new thread for the client
            Thread clientThread = new ClientHandler(clientSocket, rolodex);
            clientThread.start();
        }
    }
}


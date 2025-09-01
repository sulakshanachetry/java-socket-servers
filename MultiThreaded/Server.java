
import java.io.*;
import java.net.*;

public class Server {

    // returns a Runnable that can handle one client
    public Runnable getRunnable(Socket clientSocket) {
        return new Runnable() {
            @Override
            public void run() {
                try (
                    Socket socket = clientSocket; // auto-close at end
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String clientMessage = in.readLine();
                    System.out.println("Received from client: " + clientMessage);

                    out.println("Hello, client! You are connected to the server on thread "
                            + Thread.currentThread().getName());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // create a thread using the runnable from getRunnable()
                Thread clientThread = new Thread(server.getRunnable(clientSocket));
                clientThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

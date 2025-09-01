
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    // returns a Runnable that handles one client
    public Runnable getRunnable(Socket clientSocket) {
        return new Runnable() {
            @Override
            public void run() {
                try (
                        Socket socket = clientSocket; PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String clientMessage = in.readLine();
                    System.out.println("Received: " + clientMessage);

                    out.println("Hello client! Handled by " + Thread.currentThread().getName());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 10;
        Server server = new Server();

        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Thread Pool Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // submit task to thread pool instead of creating new thread
                executor.submit(server.getRunnable(clientSocket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

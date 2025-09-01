
import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() throws IOException, UnknownHostException {
        ServerSocket ss = new ServerSocket(8010);
        while (true) {
            System.out.println("Waiting for client connection...");
            Socket s = ss.accept();
            System.out.println("Client connected: " + s.getInetAddress());
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String clientMessage = in.readLine();
            System.out.println("Received from client: " + clientMessage);
            out.println("Hello, client from Server!");
        }
    }
}


import java.io.*;
import java.net.*;

public class Client {

    public Runnable getRunnable(int clientId)
            throws IOException, UnknownHostException {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("localhost", 8010);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println("Hello, Server. I'm the Client " + clientId + "!");
                    System.out.println(in.readLine());
                    out.close();
                    in.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();
        for (int i = 0; i < 20; i++) {
            try {
                Thread thread = new Thread(client.getRunnable(i));
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

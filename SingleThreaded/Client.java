
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException, UnknownHostException {
        Socket socket = new Socket("localhost", 8010);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println("Hello, Server. I'm the Client!");
        System.out.println(in.readLine());
        out.close();
        in.close();
        socket.close();
    }

}

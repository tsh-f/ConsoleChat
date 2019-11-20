import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8080);
            while (true) {
                new SampleServer(server.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server started");
    }

    public SampleServer(Socket socket) {
        this.socket = socket;
    }
}



}

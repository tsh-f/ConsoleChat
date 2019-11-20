package Chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

class Server {
    private LinkedList<SampleServer> serverList = new LinkedList<>();
    private Socket socket;

    Server(int port) {
        createServer(port);
    }

    public static void main(String[] args) {
        new Server(8080);
    }

    private void createServer(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server started");
            while (true) {
                socket = server.accept();
                new SampleServer(socket, serverList).start();
                System.out.println(socket.getLocalAddress().getHostName() + "connected!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
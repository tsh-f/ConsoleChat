package Chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

class Server {
    private LinkedList<SampleServer> serverList;
    private ServerSocket server;
    private Socket socket;

    Server(int port) {
        createServer(port);
    }

    public static void main(String[] args) {
        System.out.print("Введите порт сервера: ");
        new Server(new Scanner(System.in).nextInt());
    }

    private void createServer(int port) {
        try {
            server = new ServerSocket(port);
            serverList = new LinkedList<>();
            while (true) {
                socket = server.accept();
                new SampleServer(socket).start();
                System.out.println(socket.getInetAddress().getHostName() + " connected!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class SampleServer extends Thread {
        private Socket socket;
        private BufferedReader in;
        private BufferedWriter out;

        SampleServer(Socket socket) {
            this.socket = socket;
            serverList.add(this);
            createSampleServer();
        }

        private void createSampleServer() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String tmp;
            try {
                String name = in.readLine();
                while (true) {
                    tmp = in.readLine();
                    if (tmp.equals("стоп")) {
                        break;
                    }

                    for (SampleServer ss : serverList) {
                        ss.out.write(name + ": " + tmp + "\n");
                        ss.out.flush();
                    }
                }
            } catch (IOException e) {
                System.out.println("Connection reset " + socket.getInetAddress().getHostName());
            } finally {
                try {
                    serverList.remove(this);
                    this.socket.close();
                    this.in.close();
                    this.out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
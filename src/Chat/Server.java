package Chat;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

class Server {
    private List<SampleServer> serverList = new LinkedList<>();
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
                SampleServer ss = new SampleServer(socket);
                ss.start();
                serverList.add(ss);
                System.out.println(socket.getLocalAddress().getHostName() + "connected!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SampleServer extends Thread {
        private Socket socket;
        private BufferedReader in;
        private BufferedWriter out;
        private String name;

        private SampleServer(Socket socket) {
            this.socket = socket;
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
                while (true) {
                    tmp = in.readLine();
                    System.out.println(tmp);
                    for (SampleServer ss : serverList) {
                        send(tmp.substring(0, tmp.length() - 4));
                    }
                    if (tmp.equals("стоп")) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void send(String tmp) {
            try {
                out.write(name + ": " + tmp + "\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
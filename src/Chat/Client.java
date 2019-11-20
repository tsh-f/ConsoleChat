package Chat;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader reader;
    private String name;

    Client() {
        createClient();
    }

    public static void main(String[] args) {
        new Client();
    }

    private void createClient() {
        try {
            socket = new Socket("localhost", 8080);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(System.in));
            ReadMessage rm = new ReadMessage();
            SendMessage sm = new SendMessage();
            rm.start();
            sm.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ReadMessage extends Thread {
        String tmp;

        @Override
        public void run() {
            while (true) {
                try {
                    tmp = in.readLine();
                    if (tmp.equals("стоп")) {
                        break;
                    }
                    System.out.println(tmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class SendMessage extends Thread {
        String tmp;

        @Override
        public void run() {
            try {
                name = reader.readLine();
                out.write(name + "\n");
                while (true) {
                    tmp = reader.readLine();
                    if (tmp.equals("стоп")) {
                        out.write("стоп\n");
                        break;
                    }
                    out.write(tmp + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

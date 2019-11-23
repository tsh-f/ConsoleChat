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
            UIClient ui = new UIClient();
            System.out.print("Введите хост и порт сервера: ");
            reader = new BufferedReader(new InputStreamReader(System.in));
//            socket = new Socket(reader.readLine(), Integer.parseInt(reader.readLine()));
            socket = new Socket("localhost", 8080);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
                    System.out.println(tmp);
                } catch (IOException e) {
                    System.out.println("Соединение потеряно");
                    try {
                        sleep(10000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    class SendMessage extends Thread {
        String tmp;

        @Override
        public void run() {
            try {
                System.out.print("Введите имя: ");
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
            }finally {
                System.exit(0);
            }
        }
    }
}

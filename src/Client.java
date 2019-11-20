import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String name;

    Client() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите IP для подключения к серверу");
            socket = new Socket(scanner.nextLine(), 8090);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Введите свой ник");
            name = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

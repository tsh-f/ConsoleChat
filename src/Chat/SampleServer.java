package Chat;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

class SampleServer extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private LinkedList<SampleServer> serverList;

    SampleServer(Socket socket, LinkedList<SampleServer> serverList) {
        this.socket = socket;
        this.serverList = serverList;
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
                    ss.send(tmp, name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void send(String tmp, String name) {
        try {
            out.write(name + ": " + tmp + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

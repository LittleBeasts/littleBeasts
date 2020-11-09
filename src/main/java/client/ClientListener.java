package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientListener extends Thread {

    private BufferedReader in;
    private Socket socket;
    private List<String> messageBuffer;

    ClientListener(Socket socket) throws IOException {
        this.socket = socket;
        messageBuffer = new ArrayList<>();
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (in.ready()) {
                    String resp = in.readLine();
                    messageBuffer.add(resp);
                    System.out.println(resp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getMessageBuffer() {
        List<String> tmp = new ArrayList<>(this.messageBuffer);
        this.messageBuffer.clear();
        return tmp;
    }

    public boolean messagesBuffered() {
        return this.messageBuffer.size() > 0;
    }

}

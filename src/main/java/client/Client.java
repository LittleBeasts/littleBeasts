package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ClientListener clientListener;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String msg) throws IOException {
        out.println(msg);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public Client() throws IOException {
        startConnection("127.0.0.1", 9999);
        clientListener = new ClientListener(this.clientSocket);
    }

  //  public static void main(String[] args) throws IOException {
  //      // Client client = new Client();
  //      //  client.startConnection("127.0.0.1", 9999);
  //      //Scanner scanner = new Scanner(new BufferedInputStream(System.in));
  //      //client.clientListener = new ClientListener(client.clientSocket);
  //      //while (true) {
  //      //    System.out.println("Waiting for input...");
  //      //    client.sendMessage(scanner.nextLine());
  //      //}
//
  //  }


    public ClientListener getClientListener() {
        return clientListener;
    }
}





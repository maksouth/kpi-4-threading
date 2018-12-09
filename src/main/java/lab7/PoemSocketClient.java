package lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PoemSocketClient {
    private Socket clientSocket;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String getPoem() throws IOException {
        return in.readLine();
    }

    public void stopConnection() throws IOException {
        in.close();
        clientSocket.close();
    }
}
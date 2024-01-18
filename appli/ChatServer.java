import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();

    public ChatServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startServer() {
        System.out.println("Server is running on port " + serverSocket.getLocalPort());

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);

                ClientHandler clientHandler = new ClientHandler(socket, this);
                clients.add(clientHandler);

                new Thread(clientHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public static void main(String[] args) {
        int port = 5555;
        new ChatServer(port);
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private ChatServer server;
    private DataInputStream input;
    private DataOutputStream output;

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = input.readUTF();
                System.out.println("Received message: " + message);

                // Broadcast the message to all connected clients
                server.broadcast(message, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

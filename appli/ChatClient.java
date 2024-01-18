import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class ChatClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private Consumer<String> messageCallback;


    public ChatClient(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            startListening();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setMessageCallback(Consumer<String> callback) {
        this.messageCallback = callback;
    }

    private void startListening() {
        new Thread(() -> {
            try {
                while (true) {
                    String message = input.readUTF();
                    // Notify the callback with the received message
                    if (messageCallback != null) {
                        messageCallback.accept(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String serverAddress = "localhost"; // Change to the server address
        int serverPort = 5555; // Change to the server port
        ChatClient client = new ChatClient(serverAddress, serverPort);
        // Example: client.sendMessage("Hello, server!");
    }
}

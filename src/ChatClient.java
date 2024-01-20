import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * La classe ChatClient représente un client de chat qui se connecte à un serveur
 * via un socket et permet l'envoi et la réception de messages.
 */
public class ChatClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private Consumer<String> messageCallback;

    /**
     * Constructeur de la classe ChatClient.
     *
     * @param serverAddress L'adresse IP du serveur.
     * @param serverPort    Le port du serveur.
     */
    public ChatClient(String serverAddress, int serverPort) {
        try {
            // Établir une connexion au serveur
            socket = new Socket(serverAddress, serverPort);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            // Commencer l'écoute des messages du serveur en arrière-plan
            startListening();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Définit le callback pour recevoir les messages du serveur.
     *
     * @param callback La fonction de callback pour les messages reçus.
     */
    public void setMessageCallback(Consumer<String> callback) {
        this.messageCallback = callback;
    }

    /**
     * Initialise l'écoute des messages du serveur en arrière-plan.
     */
    private void startListening() {
        new Thread(() -> {
            try {
                // Écouter en permanence les messages du serveur
                while (true) {
                    String message = input.readUTF();
                    
                    // Notifier le callback avec le message reçu
                    if (messageCallback != null) {
                        messageCallback.accept(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Envoie un message au serveur.
     *
     * @param message Le message à envoyer.
     */
    public void sendMessage(String message) {
        try {
            // Envoyer le message au serveur
            output.writeUTF(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode principale pour tester le client de chat.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Modifier avec l'adresse du serveur
        int serverPort = 5555; // Modifier avec le port du serveur
        new ChatClient(serverAddress, serverPort);
    }
}

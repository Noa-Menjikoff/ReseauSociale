import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HomePage extends BorderPane {
    private AppliReseau appli;
    private TextArea messageArea;
    private ChatClient chatClient; 
    private ConnexionMySQL connexion;
    private int userId;

    public HomePage(ConnexionMySQL connexion, AppliReseau appli) {
        this.userId = 1;
        this.connexion = connexion;
        this.appli = appli;
        messageArea = new TextArea();
        chatClient = new ChatClient("192.168.28.82", 5555);
        chatClient.setMessageCallback(this::updateMessageArea);

        // Left side with buttons
        GridPane leftGrid = new GridPane();
        leftGrid.setPadding(new Insets(60));


        Button buttonFollowed = new Button("Accédez à ces follows");
        Button buttonMessage = new Button("Accédez à mes discussions");
        Button button3 = new Button("3");
        Button button4 = new Button("4");
        Button button5 = new Button("5");

        leftGrid.add(buttonFollowed, 0, 0);
        leftGrid.add(buttonMessage, 0, 1);
        leftGrid.add(button3, 0, 2);
        leftGrid.add(button4, 0, 3);
        leftGrid.add(button5, 0, 4);

        setLeft(leftGrid);

        // Center for content (initially empty)
        GridPane centerGrid = new GridPane();
        setCenter(messageArea);

        // Event handling
        buttonFollowed.setOnAction(e -> handleButtonAction("Button Followed"));
        buttonMessage.setOnAction(e -> handleButtonAction("Button Message"));
        button3.setOnAction(e -> handleButtonAction("Button 3"));
        button4.setOnAction(e -> handleButtonAction("Button 4"));
        button5.setOnAction(e -> handleButtonAction("Button 5"));
    }

    private void displayAllMessagesFromDatabase(ConnexionMySQL connexion) {
        try {
            // Assurez-vous d'adapter cette requête selon votre schéma de base de données
            String query = "SELECT messages.id_utilisateur, messages.contenu, messages.date_heure, utilisateurs.nom_utilisateur " +
                           "FROM messages " +
                           "JOIN utilisateurs ON messages.id_utilisateur = utilisateurs.id";

            try (Connection dbConnection = connexion.getMySQLConnection();
                 PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Récupérer les informations du message depuis le résultat
                    int idUtilisateur = resultSet.getInt("id_utilisateur");
                    String nomUtilisateur = resultSet.getString("nom_utilisateur");
                    String contenu = resultSet.getString("contenu");
                    String dateHeure = resultSet.getString("date_heure");

                    // Formater le message et l'ajouter à la zone de texte
                    String formattedMessage = String.format("%s (%s): %s", nomUtilisateur, dateHeure, contenu);
                    updateMessageArea(formattedMessage);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToDatabase(ConnexionMySQL connexion, String message, int userId) {
        
    }
    

    private void handleButtonAction(String buttonLabel) {
        // Replace the content in the center based on the clicked button
        GridPane newContent = new GridPane();
        newContent.setPadding(new Insets(10));
        newContent.add(new Button("New Content for " + buttonLabel), 0, 0);
        if (buttonLabel == "Button Message"){
            displayAllMessagesFromDatabase(connexion);
            setCenter(chat(userId));
        }
    }

    private void sendMessage(String message) {
        // Send the message to the server using the ChatClient
        chatClient.sendMessage(message);
        // Update the UI to display the sent message
        updateMessageArea("You: " + message);
    }
    private void updateMessageArea(String message) {
        // Update the UI to display the received message
        messageArea.appendText(message + "\n");
    }

    private BorderPane chat(int userId) {
        BorderPane borderPane = new BorderPane();
    
        // Text area for displaying messages
        messageArea.setEditable(false);
    
        // Text area for input
        TextArea inputArea = new TextArea();
        inputArea.setPromptText("Type your message here...");
    
        // Send button
        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            String message = inputArea.getText().trim();
            if (!message.isEmpty()) {
                // Send the message to the server using the ChatClient
                sendMessageToDatabase(connexion, message, userId); 
                sendMessage(message);
                // Update the UI to display the sent message
                updateMessageArea("You: " + message);
                // Clear the input area
                inputArea.clear();
            }
        });
    
        // HBox for input and send button
        HBox inputBox = new HBox(inputArea, sendButton);
    
        // VBox to hold the message area and input box
        VBox chatBox = new VBox(messageArea, inputBox);
    
        // Set the VBox to the center of the BorderPane
        borderPane.setCenter(chatBox);
    
        return borderPane;
    }
    

}

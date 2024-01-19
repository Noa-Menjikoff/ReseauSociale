import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.geometry.Insets;

import java.beans.Statement;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HomePage extends BorderPane {
    private Utilisateur utilisateur;
    private AppliReseau appli;

    private TextArea messageArea;
    private ChatClient chatClient; 
    private ConnexionMySQL connexion;
    private int userId;
    private java.sql.Statement st;
    private MessageBD messageBD;

  
    public HomePage(ConnexionMySQL connexion, AppliReseau appli, Utilisateur utilisateur, String ip) {
        this.appli = appli;
        this.utilisateur = utilisateur;
        this.connexion = connexion;
        this.executer();
        this.messageBD = new MessageBD(connexion);
        this.userId = utilisateur.getIdUtilisateur();
        this.connexion = connexion;
        this.appli = appli;
        messageArea = new TextArea();
        chatClient = new ChatClient(ip, 5555);
        chatClient.setMessageCallback(this::updateMessageArea);
    }

    private void executer(){
        GridPane leftGrid = new GridPane();
        leftGrid.setPadding(new Insets(60));
        Button buttonFollowed = new Button("Accédez à mes follows");

        Button buttonMessage = new Button("Accédez à mes discussions");
        Button button3 = new Button("Cherchez des contacts");
        Button button4 = new Button("Afficher mon historique");
        Button button5 = new Button("5");
        leftGrid.add(buttonFollowed, 0, 0);
        leftGrid.add(buttonMessage, 0, 1);
        leftGrid.add(button3, 0, 2);
        leftGrid.add(button4, 0, 3);
        leftGrid.add(button5, 0, 4);
        setLeft(leftGrid);


        GridPane centerGrid = new GridPane();
        setCenter(messageArea);


        // GridPane centerGrid = new GridPane();
        // setCenter(centerGrid);

        buttonFollowed.setOnAction(e -> handleButtonAction("Button Followed"));
        buttonMessage.setOnAction(e -> handleButtonAction("Button Message"));
        button3.setOnAction(e -> handleButtonAction("Button Contact"));
        button4.setOnAction(e -> handleButtonAction("Button Historique de message"));
        button5.setOnAction(e -> handleButtonAction("Button 5"));
    }


    private void displayAllMessagesFromDatabase(ConnexionMySQL connexion) {
        try {
            String query = "SELECT messages.id_utilisateur, messages.contenu, messages.date_heure, utilisateurs.nom_utilisateur " +
                           "FROM messages " +
                           "JOIN utilisateurs ON messages.id_utilisateur = utilisateurs.id";

            try (Connection dbConnection = connexion.getMySQLConnection();
                 PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int idUtilisateur = resultSet.getInt("id_utilisateur");
                    String nomUtilisateur = resultSet.getString("nom_utilisateur");
                    String contenu = resultSet.getString("contenu");
                    String dateHeure = resultSet.getString("date_heure");

                    String formattedMessage = String.format("%s (%s): %s", nomUtilisateur, dateHeure, contenu);
                    updateMessageArea(formattedMessage);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToDatabase(ConnexionMySQL connexion, String message, int userId) throws SQLException{
        connexion.connecter("servinfo-maria", "DBmenjikoff", "menjikoff", "menjikoff");
        this.messageBD.insererUtilisateur(userId, message);
    }
    



    private void sendMessage(String message) {
        chatClient.sendMessage(message);
        updateMessageArea("You: " + message);
    }
    private void updateMessageArea(String message) {
        messageArea.appendText(message + "\n");
    }

    public void handleButtonAction(String buttonLabel) {
        GridPane newContent = new GridPane();
        newContent.setPadding(new Insets(10));
        if (buttonLabel=="Button Followed"){
            setCenter(pageFollow(this.connexion, utilisateur));
        }
        else if (buttonLabel=="Button Contact"){
            setCenter(pageToFollow(this.connexion, utilisateur));
        }
        else if (buttonLabel=="Button Historique de message"){
            setCenter(pageHistoriqueMessage(connexion, utilisateur));
        }
        else if (buttonLabel == "Button Message"){
            displayAllMessagesFromDatabase(connexion);
            setCenter(chat(userId));
        }
    }

    public GridPane pageFollow(ConnexionMySQL connexion, Utilisateur utilisateur) {
        PageFollow pageFollow = new PageFollow(connexion, utilisateur,this);
        return pageFollow.executer();
    }

    public void setFollow(){
        setCenter(pageFollow(connexion, utilisateur));
    }

    public void setToFollow(){
        setCenter(pageToFollow(connexion, utilisateur));
    }

    public void setHistoriqueMessage(){
        setCenter(pageHistoriqueMessage(connexion, utilisateur));
    }

    public GridPane pageToFollow(ConnexionMySQL connexion, Utilisateur utilisateur){
        PageToFollow center = new PageToFollow(connexion, utilisateur,this);
        return center.executer();
    }

    public GridPane pageHistoriqueMessage(ConnexionMySQL connexion, Utilisateur utilisateur){
        HistoriqueMessage center = new HistoriqueMessage(connexion,utilisateur,this);
        return center.executer();

    }

    private BorderPane chat(int userId) {
        BorderPane borderPane = new BorderPane();
    
        messageArea.setEditable(false);
    
        TextArea inputArea = new TextArea();
        inputArea.setPromptText("Type your message here...");
    
        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            String message = inputArea.getText().trim();
            if (!message.isEmpty()) {
                try {
                    sendMessageToDatabase(connexion, message, userId);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } 
                sendMessage(message);
                updateMessageArea("You: " + message);
                inputArea.clear();
            }
        });
    
        HBox inputBox = new HBox(inputArea, sendButton);
    
        VBox chatBox = new VBox(messageArea, inputBox);
    
        borderPane.setCenter(chatBox);
    
        return borderPane;
    }
    

}



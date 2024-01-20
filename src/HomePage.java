import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.geometry.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe HomePage représente la page d'accueil de l'application.
 */
public class HomePage extends BorderPane {
    private Utilisateur utilisateur;
    private AppliReseau appli;

    private VBox messageArea;
    private ChatClient chatClient;
    private ConnexionMySQL connexion;
    private int userId;
    private MessageBD messageBD;

    /**
     * Constructeur de la classe HomePage.
     *
     * @param connexion   La connexion à la base de données.
     * @param appli       L'application réseau.
     * @param utilisateur L'utilisateur connecté.
     * @param ip          L'adresse IP du serveur de chat.
     */
    public HomePage(ConnexionMySQL connexion, AppliReseau appli, Utilisateur utilisateur, String ip) {
        this.appli = appli;
        this.utilisateur = utilisateur;
        this.connexion = connexion;
        this.messageBD = new MessageBD(connexion);
        this.userId = utilisateur.getIdUtilisateur();
        this.connexion = connexion;
        this.appli = appli;
        messageArea = new VBox();
        chatClient = new ChatClient(ip, 5555);
        chatClient.setMessageCallback(this::updateMessageArea);
        this.initialize();
    }

    /**
     * Initialise la configuration de la page d'accueil.
     */
    private void initialize() {
        GridPane leftGrid = new GridPane();
        leftGrid.setPadding(new Insets(60));
        Button buttonFollowed = new Button("Accédez à mes follows");
        Button buttonMessage = new Button("Accédez à mes discussions");
        Button button3 = new Button("Cherchez des contacts");
        Button button4 = new Button("Afficher mon historique");
        leftGrid.add(buttonFollowed, 0, 0);
        leftGrid.add(buttonMessage, 0, 1);
        leftGrid.add(button3, 0, 2);
        leftGrid.add(button4, 0, 3);
        setLeft(leftGrid);
        setCenter(messageArea);

        // Définir les actions des boutons
        buttonFollowed.setOnAction(e -> {
            try {
                handleButtonAction("Button Followed");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        buttonMessage.setOnAction(e -> {
            try {
                handleButtonAction("Button Message");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        button3.setOnAction(e -> {
            try {
                handleButtonAction("Button Contact");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        button4.setOnAction(e -> {
            try {
                handleButtonAction("Button Historique de message");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * Gère l'action d'un bouton.
     *
     * @param buttonLabel Le libellé du bouton.
     * @throws SQLException Si une erreur SQL survient.
     */
    public void handleButtonAction(String buttonLabel) throws SQLException {
        GridPane newContent = new GridPane();
        newContent.setPadding(new Insets(10));
        if ("Button Followed".equals(buttonLabel)) {
            setCenter(pageFollow(this.connexion, utilisateur));
        } else if ("Button Contact".equals(buttonLabel)) {
            setCenter(pageToFollow(this.connexion, utilisateur));
        } else if ("Button Historique de message".equals(buttonLabel)) {
            setCenter(pageHistoriqueMessage(connexion, utilisateur));
        } else if ("Button Message".equals(buttonLabel)) {
            displayAllMessagesFromDatabase(connexion);
            setCenter(chat(userId));
        }
    }

    /**
     * Affiche la page des follows.
     *
     * @param connexion   La connexion à la base de données.
     * @param utilisateur L'utilisateur connecté.
     * @return Le contenu de la page des follows.
     * @throws SQLException Si une erreur SQL survient.
     */
    public GridPane pageFollow(ConnexionMySQL connexion, Utilisateur utilisateur) throws SQLException {
        PageFollow pageFollow = new PageFollow(connexion, utilisateur, this);
        return pageFollow.executer();
    }

    /**
     * Définit la page des follows comme contenu central.
     *
     * @throws SQLException Si une erreur SQL survient.
     */
    public void setFollow() throws SQLException {
        setCenter(pageFollow(connexion, utilisateur));
    }

    /**
     * Définit la page des contacts à suivre comme contenu central.
     *
     * @throws SQLException Si une erreur SQL survient.
     */
    public void setToFollow() throws SQLException {
        setCenter(pageToFollow(connexion, utilisateur));
    }

    /**
     * Définit la page de l'historique des messages comme contenu central.
     *
     * @throws SQLException Si une erreur SQL survient.
     */
    public void setHistoriqueMessage() throws SQLException {
        setCenter(pageHistoriqueMessage(connexion, utilisateur));
    }

    /**
     * Affiche la page des contacts à suivre.
     *
     * @param connexion   La connexion à la base de données.
     * @param utilisateur L'utilisateur connecté.
     * @return Le contenu de la page des contacts à suivre.
     * @throws SQLException Si une erreur SQL survient.
     */
    public GridPane pageToFollow(ConnexionMySQL connexion, Utilisateur utilisateur) throws SQLException {
        PageToFollow center = new PageToFollow(connexion, utilisateur, this);
        return center.executer();
    }

    /**
     * Affiche la page de l'historique des messages.
     *
     * @param connexion   La connexion à la base de données.
     * @param utilisateur L'utilisateur connecté.
     * @return Le contenu de la page de l'historique des messages.
     * @throws SQLException Si une erreur SQL survient.
     */
    public GridPane pageHistoriqueMessage(ConnexionMySQL connexion, Utilisateur utilisateur) throws SQLException {
        HistoriqueMessage center = new HistoriqueMessage(connexion, utilisateur, this);
        return center.executer();
    }

    // ... (Le reste de la classe reste inchangé)

    /**
     * Affiche tous les messages stockés dans la base de données.
     *
     * @param connexion La connexion à la base de données.
     * @throws SQLException Si une erreur SQL survient.
     */
    private void displayAllMessagesFromDatabase(ConnexionMySQL connexion) throws SQLException {
        connexion.connecter("servinfo-maria", "DBmenjikoff", "menjikoff", "menjikoff");

        try {
            String query = "SELECT messages.id_utilisateur, messages.contenu, messages.date_heure, utilisateurs.nom_utilisateur " +
                    "FROM messages " +
                    "JOIN utilisateurs ON messages.id_utilisateur = utilisateurs.id";

            try (Connection dbConnection = connexion.getMySQLConnection();
                 PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String nomUtilisateur = resultSet.getString("nom_utilisateur");
                    String contenu = resultSet.getString("contenu");
                    String dateHeure = resultSet.getString("date_heure");

                    String formattedMessage = String.format("%s (%s): %s", nomUtilisateur, dateHeure, contenu);

                    // Mise à jour de l'interface utilisateur sur le fil d'exécution de l'application JavaFX
                    Platform.runLater(() -> updateMessageArea(formattedMessage));
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
        VBox messageBox = new VBox();
        messageBox.setSpacing(10);
        messageBox.setMaxWidth(USE_PREF_SIZE); 
    
        Label messageLabel = new Label(message);
    
        HBox hbox = new HBox(messageLabel);
        hbox.setMaxWidth(USE_PREF_SIZE); 
        hbox.setPadding(new Insets(5));
        hbox.setStyle("-fx-border-color: black; -fx-border-width: 1;-fx-border-radius:5px;");
    
        messageBox.getChildren().add(hbox);
    
        Button likeButton = new Button("Like");
        messageBox.getChildren().add(likeButton);
    
        messageArea.getChildren().add(messageBox);
        messageArea.setPrefHeight(500);
    
        ScrollPane messageAreaScrollPane = new ScrollPane(messageArea);
        messageAreaScrollPane.setPrefHeight(500); 
    
        messageAreaScrollPane.setVvalue(1.0);
    }

    private BorderPane chat(int userId) {
        BorderPane borderPane = new BorderPane();

        VBox messageContainer = new VBox();
        messageContainer.setSpacing(10);
        messageArea = messageContainer; 

        ScrollPane messageAreaScrollPane = new ScrollPane(messageContainer);
        messageAreaScrollPane.setFitToHeight(true);
        messageAreaScrollPane.setFitToWidth(true);
        messageAreaScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

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
                inputArea.clear();
            }
        });

        HBox inputBox = new HBox(inputArea, sendButton);

        VBox chatBox = new VBox(messageAreaScrollPane, inputBox);
        borderPane.setCenter(chatBox);

        return borderPane;
    }
    

}



import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class HomePage extends BorderPane {
    private AppliReseau appli;
    private TextArea messageArea;
    private ChatClient chatClient; 

    public HomePage(ConnexionMySQL connexion, AppliReseau appli) {
        this.appli = appli;
        messageArea = new TextArea();
        chatClient = new ChatClient("localhost", 5555); // Adresse et port du serveur

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

    private void handleButtonAction(String buttonLabel) {
        // Replace the content in the center based on the clicked button
        GridPane newContent = new GridPane();
        newContent.setPadding(new Insets(10));
        newContent.add(new Button("New Content for " + buttonLabel), 0, 0);
        if (buttonLabel == "Button Message"){
            setCenter(chat());
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

    private BorderPane chat() {
        BorderPane borderPane = new BorderPane();

        // Text area for displaying messages
        TextArea messageArea = new TextArea();
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

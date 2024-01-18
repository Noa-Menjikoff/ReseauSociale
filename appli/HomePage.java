import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class HomePage extends BorderPane {
    private AppliReseau appli;

    public HomePage(ConnexionMySQL connexion, AppliReseau appli) {
        this.appli = appli;

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
        setCenter(centerGrid);

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

        setCenter(newContent);
    }
}

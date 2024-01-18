import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage {

    public static void showHomePage(String username) {
        Stage homeStage = new Stage();
        StackPane homeLayout = new StackPane();
        homeLayout.getChildren().add(new Text("Bienvenue, " + username + "!"));

        Scene homeScene = new Scene(homeLayout, 400, 300);
        homeStage.setScene(homeScene);
        homeStage.setTitle("Accueil");
        homeStage.show();
    }
}

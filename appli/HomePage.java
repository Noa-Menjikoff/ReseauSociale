import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class HomePage extends GridPane{
    private AppliReseau appli;

    public HomePage(ConnexionMySQL connexion, AppliReseau appli){
        this.appli = appli;


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(60));

        Button buttonFollowed = new Button("Accédez à ces follows");
        Button buttonMessage = new Button("Accédez à mes discussions");
        Button button3 = new Button("3");
        Button button4 = new Button("4");
        Button button5 = new Button("5");

        grid.add(buttonFollowed,0, 0);
        grid.add(buttonMessage,0,1);
        grid.add(button3,0, 2);
        grid.add(button4,0, 3);
        grid.add(button5,0, 4);
        
        ControleurFollow controleurFollow = new ControleurFollow(this.appli, this, connexion);
        ControleurMessage controleurMessage = new ControleurMessage(this.appli, this, connexion);
        Controleur3 controleur3 = new Controleur3(appli, this, connexion);
        Controleur4 controleur4 = new Controleur4(appli, this,connexion);
        Controleur5 controleur5 = new Controleur5(appli, this, connexion);

        buttonFollowed.setOnAction(controleurFollow);
        buttonMessage.setOnAction(controleurMessage);
        button3.setOnAction(controleur3);
        button4.setOnAction(controleur4);
        button5.setOnAction(controleur5);

        this.getChildren().addAll(grid);

    }
}

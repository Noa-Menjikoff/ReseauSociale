import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SocialNetworkApp extends Application {

    public TextField tf1;
    public PasswordField tf2;
    public ConnexionMySQL connexion;


    public static void main(String[] args) {
        launch(args);
    }

    

    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException {
        this.connexion = new ConnexionMySQL();
        primaryStage.setTitle("Réseau Social - Connexion/Inscription");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        Label usernameLabel = new Label("Nom d'utilisateur:");
        this.tf1 = new TextField();
        Label passwordLabel = new Label("Mot de passe:");
        this.tf2 = new PasswordField();

        Button loginButton = new Button("Se connecter");
        Button registerButton = new Button("S'inscrire");

        grid.add(usernameLabel, 0, 0);
        grid.add(this.tf1, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(this.tf2, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(registerButton, 1, 3);

        ControleurConnexion controleurConnexion = new ControleurConnexion(this, this.connexion);
        loginButton.setOnAction(controleurConnexion);

        // registerButton.setOnAction(e -> {
        //     String username = this.tf1.getText();
        //     String password = this.tf2.getText();
        //     System.out.println("Inscription de : " + username);
        // });


        Scene scene = new Scene(grid, 1500, 900);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public String getTextField(){
        return this.tf1.getText();
    }

    public String getPasswordField(){
        return this.tf2.getText();
    }

}

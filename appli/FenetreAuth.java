import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class FenetreAuth extends GridPane {
    public TextField tf1;
    public TextField tf3;
    public PasswordField tf2;
    public AppliReseau appli;

    

    
    public  FenetreAuth(ConnexionMySQL connexionMySQL, AppliReseau appli) {
        this.tf1 = new TextField();
        this.tf3 = new TextField();
        this.tf2 = new PasswordField();
        this.appli = appli;
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);
        Label usernameLabel = new Label("Nom d'utilisateur:");
        this.tf1 = new TextField();
        Label passwordLabel = new Label("Mot de passe:");
        this.tf2 = new PasswordField();
        Label ip = new Label("Adresse IP:");
        this.tf3 = new TextField();
        Button loginButton = new Button("Se connecter");
        Button registerButton = new Button("S'inscrire");
        grid.add(usernameLabel, 0, 0);
        grid.add(this.tf1, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(this.tf2, 1, 1);
        grid.add(ip, 0, 2);
        grid.add(this.tf3, 1, 2);
        grid.add(loginButton, 1, 3);
        grid.add(registerButton, 1, 4);
        ControleurConnexion controleurConnexion = new ControleurConnexion(this.appli,this,connexionMySQL);
        loginButton.setOnAction(controleurConnexion);
        ControleurInscription controleurInscription = new ControleurInscription(this.appli,this,connexionMySQL);
        registerButton.setOnAction(controleurInscription);
        this.getChildren().addAll(grid);
    }

    public String getTextField(){
        return this.tf1.getText();
    }

    public String getIp(){
        return this.tf3.getText();
    }

    public String getPasswordField(){
        return this.tf2.getText();
    }

}

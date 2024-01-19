import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class HomePage extends BorderPane {
    private Utilisateur utilisateur;
    private AppliReseau appli;
    private ConnexionMySQL connexion;

    public HomePage(ConnexionMySQL connexion, AppliReseau appli, Utilisateur utilisateur) {
        this.appli = appli;
        this.utilisateur = utilisateur;
        this.connexion = connexion;
        this.executer();
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
        setCenter(centerGrid);
        buttonFollowed.setOnAction(e -> handleButtonAction("Button Followed"));
        buttonMessage.setOnAction(e -> handleButtonAction("Button Message"));
        button3.setOnAction(e -> handleButtonAction("Button Contact"));
        button4.setOnAction(e -> handleButtonAction("Button Historique de message"));
        button5.setOnAction(e -> handleButtonAction("Button 5"));
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
}



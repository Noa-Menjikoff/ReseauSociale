import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class Adm extends BorderPane {
    private Utilisateur utilisateur;
    private AppliReseau appli;
    private ConnexionMySQL connexion;

    public Adm(ConnexionMySQL connexion, AppliReseau appli, Utilisateur utilisateur) {
        this.appli = appli;
        this.utilisateur = utilisateur;
        this.connexion = connexion;
        this.executer();
    }

    private void executer(){
        GridPane leftGrid = new GridPane();
    }

}



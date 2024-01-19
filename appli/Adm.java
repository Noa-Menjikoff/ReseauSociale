import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import java.sql.SQLException;
import javafx.geometry.Insets;

public class Adm extends BorderPane {
    private Utilisateur utilisateur;
    private AppliReseau appli;
    private TextArea messageArea;
    private ConnexionMySQL connexion;

    public Adm(ConnexionMySQL connexion, AppliReseau appli, Utilisateur utilisateur) {
        this.appli = appli;
        this.utilisateur = utilisateur;
        this.connexion = connexion;
        this.executer();
        messageArea = new TextArea();
    }

    private void executer(){
        GridPane leftGrid = new GridPane();
        leftGrid.setPadding(new Insets(60));
        Button buttonSuppUser = new Button("Supprimer un utilisateur");
        Button buttonSuppMess = new Button("Supprimer un message");
        leftGrid.add(buttonSuppUser, 0, 0);
        leftGrid.add(buttonSuppMess, 0, 1);
        setLeft(leftGrid);
        setCenter(messageArea);
        buttonSuppUser.setOnAction(e -> {
            try {
                handleButtonAction("Button Supprimer Utilisateur");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        buttonSuppMess.setOnAction(e -> {
            try {
                handleButtonAction("Button Supprimer Message");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }

    public void handleButtonAction(String buttonLabel) throws SQLException {
        GridPane newContent = new GridPane();
        newContent.setPadding(new Insets(10));
        if (buttonLabel=="Button Supprimer Message"){
            setCenter(pageSuppMessage(this.connexion, utilisateur));
        }
        else if (buttonLabel=="Button Supprimer Utilisateur"){
            setCenter(pageSuppUtilisateur(this.connexion, utilisateur));
        }
    }

    public GridPane pageSuppMessage(ConnexionMySQL connexion, Utilisateur utilisateur) throws SQLException {
        PageSuppMessage page = new PageSuppMessage(connexion, utilisateur,this);
        return page.executer();
    }

    public void setSuppMessage() throws SQLException{
        setCenter(pageSuppMessage(connexion, utilisateur));
    }

    public void setSuppUtilisateur() throws SQLException{
        setCenter(pageSuppUtilisateur(connexion, utilisateur));
    }

    public GridPane pageSuppUtilisateur(ConnexionMySQL connexion, Utilisateur utilisateur) throws SQLException{
        PageSuppUtilisateur center = new PageSuppUtilisateur(connexion, utilisateur,this);
        return center.executer();
    }
}



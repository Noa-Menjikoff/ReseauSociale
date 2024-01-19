import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.SQLException;

public class ControleurConnexion implements EventHandler<ActionEvent> {
    private AppliReseau appli;
    private FenetreAuth connexion;
    private ConnexionMySQL connex;

    public ControleurConnexion(AppliReseau appli,FenetreAuth connexion, ConnexionMySQL connex) {
        this.appli = appli;
        this.connexion = connexion;
        this.connex = connex;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            this.connex.connecter("servinfo-maria", "DBmenjikoff", "menjikoff", "menjikoff");
            UtilisateurBD ubd = new UtilisateurBD(this.connex);
            Utilisateur utilisateur = ubd.connecterUtilisateur(connexion.getTextField(),connexion.getPasswordField());
            int nj = ubd.getRowCount();
            if (nj==1 && connexion.getIp() != ""){
                if("adm".equals(connexion.getTextField()) && "adm".equals(connexion.getPasswordField())){
                    System.err.println("connection réussi"); 
                    this.appli.afficheFenetreAdm(utilisateur,connexion.getIp());
                }
                else{
                    System.err.println("connection réussi"); 
                    this.appli.afficheFenetreAcceuil(utilisateur,connexion.getIp());
                }

            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur lors de votre connexion");
                alert.setContentText("Le pseudo ou le mot de passe est incorrecte");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


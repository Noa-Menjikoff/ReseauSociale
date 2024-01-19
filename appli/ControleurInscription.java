import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.SQLException;

public class ControleurInscription implements EventHandler<ActionEvent> {
    private AppliReseau appli;
    private FenetreAuth inscription;
    private ConnexionMySQL connex;

    public ControleurInscription(AppliReseau appli, FenetreAuth inscription, ConnexionMySQL connex) {
        this.appli = appli;
        this.inscription = inscription;
        this.connex = connex;
    }

    @Override
    public void handle(ActionEvent event) {
        Utilisateur util = new Utilisateur(inscription.getTextField(), inscription.getPasswordField());
        
        try {
            this.connex.connecter("servinfo-maria", "DBmenjikoff", "menjikoff", "menjikoff");
            UtilisateurBD ubd = new UtilisateurBD(this.connex);
            Utilisateur utilisateur = ubd.insererUtilisateur(util);
            int nj = utilisateur.getIdUtilisateur();
            if (nj == ubd.maxNumUtil()){
                this.appli.afficheFenetreAcceuil(utilisateur);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de votre inscription");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}


import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Classe qui implémente l'interface EventHandler<ActionEvent> et gère les événements liés à l'inscription.
 */
public class ControleurInscription implements EventHandler<ActionEvent> {

    private AppliReseau appli;
    private FenetreAuth inscription;
    private ConnexionMySQL connex;

    /**
     * Constructeur de la classe ControleurInscription.
     *
     * @param appli      l'instance d'AppliVAE à utiliser pour les actions liées à l'application.
     * @param inscription l'instance de FenetreInscription à utiliser pour l'inscription.
     * @param connex     l'instance de ConnexionMySQL à utiliser pour la connexion à la base de données.
     */
    public ControleurInscription(AppliReseau appli, FenetreAuth inscription, ConnexionMySQL connex) {
        this.appli = appli;
        this.inscription = inscription;
        this.connex = connex;
    }

    /**
     * Méthode appelée lorsqu'un événement d'action est déclenché.
     *
     * @param event l'événement d'action déclenché.
     */
    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) (event.getSource());
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
            // Gérer l'exception SQLException ici
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de votre inscription");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            // Autres actions à effectuer en cas d'exception
        }
    }
}


import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Classe qui implémente l'interface EventHandler<ActionEvent> et gère les événements liés à la connexion.
 */
public class ControleurConnexion implements EventHandler<ActionEvent> {

    private AppliReseau appli;
    private FenetreAuth connexion;
    private ConnexionMySQL connex;

    /**
     * Constructeur de la classe ControleurConnexion.
     *
     * @param appli     l'instance de AppliVAE à utiliser pour les actions liées à la connexion.
     * @param connexion l'instance de FenetreConnexion à utiliser pour les actions liées à la connexion.
     * @param connex    l'instance de ConnexionMySQL à utiliser pour la connexion à la base de données.
     */
    public ControleurConnexion(AppliReseau appli,FenetreAuth connexion, ConnexionMySQL connex) {
        this.appli = appli;
        this.connexion = connexion;
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
        try {
            this.connex.connecter("servinfo-maria", "DBmenjikoff", "menjikoff", "menjikoff");
            UtilisateurBD ubd = new UtilisateurBD(this.connex);
            int nj = ubd.connecterUtilisateur(connexion.getTextField(),connexion.getPasswordField());

            if (nj==1){
                System.err.println("connection réussi");
                this.appli.afficheFenetreAcceuil();
            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur lors de votre connexion");
                alert.setContentText("Le pseudo ou le mot de passe est incorrecte");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Gérer l'exception SQLException ici

            e.printStackTrace();
            // Autres actions à effectuer en cas d'exception
        }
    }
}


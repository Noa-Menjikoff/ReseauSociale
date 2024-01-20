import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.SQLException;

/**
 * Le ControleurInscription est un gestionnaire d'événements pour les actions liées à l'inscription.
 */
public class ControleurInscription implements EventHandler<ActionEvent> {
    private AppliReseau appli;
    private FenetreAuth inscription;
    private ConnexionMySQL connex;

    /**
     * Constructeur de la classe ControleurInscription.
     *
     * @param appli       L'application principale.
     * @param inscription La fenêtre d'inscription.
     * @param connex      La connexion à la base de données.
     */
    public ControleurInscription(AppliReseau appli, FenetreAuth inscription, ConnexionMySQL connex) {
        this.appli = appli;
        this.inscription = inscription;
        this.connex = connex;
    }

    /**
     * Gère l'événement de clic sur le bouton d'inscription.
     *
     * @param event L'événement de clic.
     */
    @Override
    public void handle(ActionEvent event) {
        Utilisateur util = new Utilisateur(inscription.getTextField(), inscription.getPasswordField());

        try {
            // Tentative de connexion à la base de données
            this.connex.connecter("servinfo-maria", "DBmenjikoff", "menjikoff", "menjikoff");

            // Insertion d'un nouvel utilisateur dans la base de données
            UtilisateurBD ubd = new UtilisateurBD(this.connex);
            Utilisateur utilisateur = ubd.insererUtilisateur(util);
            int nj = utilisateur.getIdUtilisateur();

            // Vérification de la réussite de l'inscription
            if (nj == ubd.maxNumUtil() && !inscription.getIp().isEmpty()) {
                this.appli.afficheFenetreAcceuil(utilisateur, inscription.getIp());
            }
        } catch (SQLException e) {
            // Affichage d'une alerte en cas d'échec d'inscription
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de votre inscription");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.SQLException;

/**
 * Le ControleurConnexion est un gestionnaire d'événements pour les actions liées à la connexion.
 */
public class ControleurConnexion implements EventHandler<ActionEvent> {
    private AppliReseau appli;
    private FenetreAuth connexion;
    private ConnexionMySQL connex;

    /**
     * Constructeur de la classe ControleurConnexion.
     *
     * @param appli     L'application principale.
     * @param connexion La fenêtre d'authentification.
     * @param connex    La connexion à la base de données.
     */
    public ControleurConnexion(AppliReseau appli, FenetreAuth connexion, ConnexionMySQL connex) {
        this.appli = appli;
        this.connexion = connexion;
        this.connex = connex;
    }

    /**
     * Gère l'événement de clic sur le bouton de connexion.
     *
     * @param event L'événement de clic.
     */
    @Override
    public void handle(ActionEvent event) {
        try {
            // Tentative de connexion à la base de données
            this.connex.connecter("servinfo-maria", "DBmenjikoff", "menjikoff", "menjikoff");

            // Vérification des informations d'authentification
            UtilisateurBD ubd = new UtilisateurBD(this.connex);
            Utilisateur utilisateur = ubd.connecterUtilisateur(connexion.getTextField(), connexion.getPasswordField());
            int nj = ubd.getRowCount();

            // Vérification de la réussite de la connexion
            if (nj == 1 && !connexion.getIp().isEmpty()) {
                if ("adm".equals(connexion.getTextField()) && "adm".equals(connexion.getPasswordField())) {
                    System.err.println("Connexion réussie en tant qu'administrateur");
                    this.appli.afficheFenetreAdm(utilisateur, connexion.getIp());
                } else {
                    System.err.println("Connexion réussie en tant qu'utilisateur");
                    this.appli.afficheFenetreAcceuil(utilisateur, connexion.getIp());
                }
            } else {
                // Affichage d'une alerte en cas d'échec de connexion
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur lors de votre connexion");
                alert.setContentText("Le pseudo ou le mot de passe est incorrect");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

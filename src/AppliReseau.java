/**
 * La classe AppliReseau est une application JavaFX représentant une application réseau
 * avec des fonctionnalités d'authentification et des fenêtres pour les utilisateurs et
 * les administrateurs.
 *
 * Elle étend la classe Application de JavaFX et gère la création des différentes interfaces
 * graphiques de l'application.
 */
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AppliReseau extends Application {

    private Scene scene;
    private ConnexionMySQL laConnexion;

    /**
     * Méthode principale de l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch(AppliReseau.class, args);
    }

    /**
     * Méthode d'initialisation de l'application.
     *
     * @throws ClassNotFoundException En cas d'erreur de chargement de classe.
     */
    @Override
    public void init() throws ClassNotFoundException {
        this.laConnexion = new ConnexionMySQL();
    }

    /**
     * Méthode de démarrage de l'application.
     *
     * @param stage La fenêtre principale de l'application.
     * @throws Exception En cas d'erreur lors du démarrage de l'application.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Crée la fenêtre d'authentification
        Pane root = new FenetreAuth(this.laConnexion, this);

        // Configure la taille de la fenêtre en fonction de l'écran
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();

        // Crée la scène et affiche la fenêtre d'authentification
        this.scene = new Scene(root, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setTitle("Application avec authentification");
        stage.show();
    }

    /**
     * Affiche la fenêtre principale pour un utilisateur connecté.
     *
     * @param utilisateurConnecte L'utilisateur connecté.
     * @param ip L'adresse IP associée à l'utilisateur.
     */
    public void afficheFenetreAcceuil(Utilisateur utilisateurConnecte, String ip) {
        Pane root = new HomePage(this.laConnexion, this, utilisateurConnecte, ip);
        this.scene.setRoot(root);
    }

    /**
     * Affiche la fenêtre d'administration pour un utilisateur connecté en tant qu'administrateur.
     *
     * @param utilisateurConnecte L'utilisateur connecté en tant qu'administrateur.
     * @param ip L'adresse IP associée à l'utilisateur.
     */
    public void afficheFenetreAdm(Utilisateur utilisateurConnecte, String ip) {
        Pane root = new Adm(this.laConnexion, this, utilisateurConnecte);
        this.scene.setRoot(root);
    }
}

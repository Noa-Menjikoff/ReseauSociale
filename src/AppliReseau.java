import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AppliReseau extends Application{

    private Scene scene;
    private ConnexionMySQL laConnexion;

    public static void main(String[] args) {
        launch(AppliReseau.class, args);
    }
    
    @Override
    public void init() throws ClassNotFoundException {
        this.laConnexion = new ConnexionMySQL();
        
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new FenetreAuth(this.laConnexion,this);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();
        this.scene = new Scene(root, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setTitle("Appli avec deux fenêtres");
        stage.show();
    }




    public void afficheFenetreAcceuil(Utilisateur utilisateurConnecte,String ip) {
        Pane root = new HomePage(this.laConnexion, this, utilisateurConnecte,ip);
        this.scene.setRoot(root);
    }
    public void afficheFenetreAdm(Utilisateur utilisateurConnecte,String ip) {
        Pane root = new Adm(this.laConnexion, this, utilisateurConnecte);
        this.scene.setRoot(root);
    }


}
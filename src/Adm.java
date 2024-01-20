/**
 * La classe Adm représente l'interface administrateur pour la gestion des utilisateurs
 * et des messages dans une application réseau utilisant JavaFX.
 *
 * Elle étend BorderPane pour organiser sa mise en page et inclut des fonctionnalités
 * permettant de supprimer des utilisateurs et des messages avec les éléments d'interface
 * correspondants.
 */
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

    /**
     * Constructeur de la classe Adm.
     *
     * @param connexion   La connexion à la base de données MySQL.
     * @param appli       L'application réseau.
     * @param utilisateur L'utilisateur associé à l'administrateur.
     */
    public Adm(ConnexionMySQL connexion, AppliReseau appli, Utilisateur utilisateur) {
        this.appli = appli;
        this.utilisateur = utilisateur;
        this.connexion = connexion;
        this.initialiserInterface();
        messageArea = new TextArea();
    }

    /**
     * Initialise les composants de l'interface utilisateur.
     */
    private void initialiserInterface() {
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
                gererActionBouton("Button Supprimer Utilisateur");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        buttonSuppMess.setOnAction(e -> {
            try {
                gererActionBouton("Button Supprimer Message");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * Gère l'action lorsqu'un bouton est pressé.
     *
     * @param labelBouton Le libellé du bouton pressé.
     * @throws SQLException En cas d'exception SQL.
     */
    public void gererActionBouton(String labelBouton) throws SQLException {
        if ("Button Supprimer Message".equals(labelBouton)) {
            setCenter(pageSuppMessage(this.connexion, utilisateur));
        } else if ("Button Supprimer Utilisateur".equals(labelBouton)) {
            setCenter(pageSuppUtilisateur(this.connexion, utilisateur));
        }
    }

    /**
     * Crée et renvoie la page pour la suppression des messages.
     *
     * @param connexion   La connexion à la base de données MySQL.
     * @param utilisateur L'utilisateur associé à l'administrateur.
     * @throws SQLException En cas d'exception SQL.
     */
    public GridPane pageSuppMessage(ConnexionMySQL connexion, Utilisateur utilisateur) throws SQLException {
        PageSuppMessage page = new PageSuppMessage(connexion, utilisateur, this);
        return page.executer();
    }

    /**
     * Définit le centre de l'interface pour la suppression des messages.
     *
     * @throws SQLException En cas d'exception SQL.
     */
    public void setSuppMessage() throws SQLException {
        setCenter(pageSuppMessage(connexion, utilisateur));
    }

    /**
     * Définit le centre de l'interface pour la suppression des utilisateurs.
     *
     * @throws SQLException En cas d'exception SQL.
     */
    public void setSuppUtilisateur() throws SQLException {
        setCenter(pageSuppUtilisateur(connexion, utilisateur));
    }

    /**
     * Crée et renvoie la page pour la suppression des utilisateurs.
     *
     * @param connexion   La connexion à la base de données MySQL.
     * @param utilisateur L'utilisateur associé à l'administrateur.
     * @throws SQLException En cas d'exception SQL.
     */
    public GridPane pageSuppUtilisateur(ConnexionMySQL connexion, Utilisateur utilisateur) throws SQLException {
        PageSuppUtilisateur centre = new PageSuppUtilisateur(connexion, utilisateur, this);
        return centre.executer();
    }
}

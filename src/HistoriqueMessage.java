import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe HistoriqueMessage représente l'historique des messages d'un utilisateur.
 */
public class HistoriqueMessage {
    private ConnexionMySQL connexion;
    private Utilisateur utilisateur;
    private HomePage page;

    /**
     * Constructeur de la classe HistoriqueMessage.
     *
     * @param connexion  La connexion à la base de données.
     * @param utilisateur L'utilisateur pour lequel récupérer l'historique des messages.
     * @param page       La page associée.
     */
    public HistoriqueMessage(ConnexionMySQL connexion, Utilisateur utilisateur, HomePage page) {
        this.connexion = connexion;
        this.page = page;
        this.utilisateur = utilisateur;
    }

    /**
     * Exécute la récupération et l'affichage de l'historique des messages.
     *
     * @return Une grille contenant l'historique des messages.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public GridPane executer() throws SQLException {
        connexion.connecter("servinfo-maria", "DBmenjikoff", "menjikoff", "menjikoff");

        GridPane center = new GridPane();
        center.setPadding(new Insets(10));
        VBox vBox = new VBox();
        vBox.setSpacing(10);

        String query = "SELECT id_message, contenu FROM messages WHERE id_utilisateur = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setInt(1, utilisateur.getIdUtilisateur());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idMessage = resultSet.getInt("id_message");
                String contenuMessage = resultSet.getString("contenu");

                Label labelMessage = new Label(contenuMessage);
                Button buttonSupprimer = new Button("Supprimer");
                buttonSupprimer.setOnAction(e -> supprimerMessage(idMessage));

                HBox hBox = new HBox(labelMessage, buttonSupprimer);
                hBox.setSpacing(10);
                vBox.getChildren().add(hBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        center.add(vBox, 0, 0);
        return center;
    }

    /**
     * Supprime un message de la base de données et met à jour l'historique des messages.
     *
     * @param idMessage L'ID du message à supprimer.
     */
    private void supprimerMessage(int idMessage) {
        String query = "DELETE FROM messages WHERE id_message = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setInt(1, idMessage);
            preparedStatement.executeUpdate();
            System.out.println("Message supprimé avec l'ID : " + idMessage);
            page.setHistoriqueMessage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PageSuppMessage {
    private ConnexionMySQL connexion;
    private Utilisateur utilisateur;
    private Adm page;

    public PageSuppMessage(ConnexionMySQL connexion, Utilisateur utilisateur, Adm page) {
        this.connexion = connexion;
        this.utilisateur = utilisateur;
        this.page = page;
    }

    public GridPane executer() {
        GridPane center = new GridPane();
        center.setPadding(new Insets(10));

        VBox vBox = new VBox();
        vBox.setSpacing(10);

        // Récupérer tous les messages de tous les utilisateurs
        String query = "SELECT id_message, id_utilisateur, contenu FROM messages";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idMessage = resultSet.getInt("id_message");
                int idUtilisateur = resultSet.getInt("id_utilisateur");
                String contenuMessage = resultSet.getString("contenu");

                // Créer un label avec le contenu du message
                Label labelMessage = new Label(contenuMessage);

                // Créer un bouton de suppression pour chaque message
                Button buttonSupprimerMessage = new Button("Supprimer");
                buttonSupprimerMessage.setOnAction(e -> supprimerMessage(idMessage, idUtilisateur));

                // Créer une HBox pour le label et le bouton
                HBox hboxMessage = new HBox(labelMessage, buttonSupprimerMessage);
                hboxMessage.setSpacing(10);

                // Ajouter la HBox à la VBox
                vBox.getChildren().add(hboxMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ajouter la VBox au centre de la grille
        center.add(vBox, 0, 0);

        return center;
    }

    // Fonction pour supprimer un message
    private void supprimerMessage(int idMessage, int idUtilisateur) {
        String querySuppressionMessage = "DELETE FROM messages WHERE id_message = ?";

        try (PreparedStatement pstmtMessage = connexion.prepareStatement(querySuppressionMessage)) {
            pstmtMessage.setInt(1, idMessage);
            pstmtMessage.executeUpdate();
            System.out.println("Message supprimé avec l'ID : " + idMessage);

            // Rafraîchir l'affichage après la suppression du message
            page.setSuppMessage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

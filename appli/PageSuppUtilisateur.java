import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PageSuppUtilisateur {
    private ConnexionMySQL connexion;
    private Utilisateur utilisateur;
    private Adm page;

    public PageSuppUtilisateur(ConnexionMySQL connexion, Utilisateur utilisateur, Adm page) {
        this.connexion = connexion;
        this.utilisateur = utilisateur;
        this.page = page;
    }

    public GridPane executer() {
        GridPane center = new GridPane();
        center.setPadding(new Insets(10));

        VBox vBox = new VBox();
        vBox.setSpacing(10);

        // Récupérer tous les utilisateurs de la base de données
        String query = "SELECT id, nom_utilisateur FROM utilisateurs";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idUtilisateur = resultSet.getInt("id");
                String nomUtilisateur = resultSet.getString("nom_utilisateur");

                // Créer un label avec le nom de l'utilisateur
                Label labelUtilisateur = new Label(nomUtilisateur);

                // Créer un bouton de suppression pour chaque utilisateur
                Button buttonSupprimerUtilisateur = new Button("Supprimer");
                buttonSupprimerUtilisateur.setOnAction(e -> supprimerUtilisateur(idUtilisateur));

                // Créer une HBox pour le label et le bouton
                HBox hboxUtilisateur = new HBox(labelUtilisateur, buttonSupprimerUtilisateur);
                hboxUtilisateur.setSpacing(10);

                // Ajouter la HBox à la VBox
                vBox.getChildren().add(hboxUtilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ajouter la VBox au centre de la grille
        center.add(vBox, 0, 0);

        return center;
    }

    // Fonction pour supprimer un utilisateur
    private void supprimerUtilisateur(int idUtilisateur) {
        String querySuppressionMessages = "DELETE FROM messages WHERE id_utilisateur = ?";
        String querySuppressionAbonnements = "DELETE FROM abonnements WHERE id_abonne = ? OR id_suivi = ?";
        String querySuppressionUtilisateur = "DELETE FROM utilisateurs WHERE id = ?";
    
        try (PreparedStatement pstmtMessages = connexion.prepareStatement(querySuppressionMessages);
             PreparedStatement pstmtAbonnements = connexion.prepareStatement(querySuppressionAbonnements);
             PreparedStatement pstmtUtilisateur = connexion.prepareStatement(querySuppressionUtilisateur)) {
    
            // Supprimer tous les messages de l'utilisateur
            pstmtMessages.setInt(1, idUtilisateur);
            pstmtMessages.executeUpdate();
    
            // Supprimer tous les abonnements de l'utilisateur et où il est suivi
            pstmtAbonnements.setInt(1, idUtilisateur);
            pstmtAbonnements.setInt(2, idUtilisateur);
            pstmtAbonnements.executeUpdate();
    
            // Supprimer l'utilisateur lui-même
            pstmtUtilisateur.setInt(1, idUtilisateur);
            pstmtUtilisateur.executeUpdate();
    
            System.out.println("Utilisateur supprimé avec l'ID : " + idUtilisateur);
    
            // Rafraîchir l'affichage après la suppression de l'utilisateur
            page.setSuppUtilisateur();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}

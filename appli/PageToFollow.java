import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PageToFollow {
    private ConnexionMySQL connexion;
    private Utilisateur utilisateur;

    public PageToFollow(ConnexionMySQL connexion, Utilisateur utilisateur) {
        this.connexion = connexion;
        this.utilisateur = utilisateur;
    }

    public GridPane executer() {
        GridPane center = new GridPane();
        center.setPadding(new Insets(10));
    
        // VBox pour empiler les éléments verticalement
        VBox vBox = new VBox();
        vBox.setSpacing(10);  // Espacement entre les éléments
    
        // Récupérer les utilisateurs autres que moi depuis la base de données
        String query = "SELECT id, nom_utilisateur FROM utilisateurs WHERE id <> ?";
        List<Utilisateur> utilisateurs = new ArrayList<>();
    
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setInt(1, utilisateur.getIdUtilisateur());
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                int idUtilisateur = resultSet.getInt("id");
                String nomUtilisateur = resultSet.getString("nom_utilisateur");
                Utilisateur autreUtilisateur = new Utilisateur(idUtilisateur, nomUtilisateur);
                utilisateurs.add(autreUtilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // Créer un label pour chaque utilisateur dans la liste
        for (Utilisateur autreUtilisateur : utilisateurs) {
            Label label = new Label(autreUtilisateur.getNomUtilisateur());
            vBox.getChildren().add(label);
        }
    
        // VBox pour empiler les éléments verticalement
        VBox vBox2 = new VBox();
        vBox2.setSpacing(10);  // Espacement entre les éléments
    
        // Récupérer les utilisateurs suivis par l'utilisateur actuel
        String query2 = "SELECT id_suivi FROM abonnements WHERE id_abonne = ?";
        List<Integer> listeAbonneIds = new ArrayList<>();
    
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query2)) {
            preparedStatement.setInt(1, utilisateur.getIdUtilisateur());
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                int idSuivi = resultSet.getInt("id_suivi");
                listeAbonneIds.add(idSuivi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // Ajouter "O" ou "N" pour chaque utilisateur dans la liste en fonction de l'abonnement
        for (Utilisateur autreUtilisateur : utilisateurs) {
            String abonnement = listeAbonneIds.contains(autreUtilisateur.getIdUtilisateur()) ? "O" : "N";
            Label label = new Label(" - "+abonnement);
            vBox2.getChildren().add(label);
        }
    
        // Ajouter la VBox au centre de la grille
        center.add(vBox, 0, 0);
        center.add(vBox2, 1, 0);
    
        return center;
    }
    
    
}

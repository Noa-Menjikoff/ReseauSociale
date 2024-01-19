import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class PageFollow {
    private ConnexionMySQL connexion;
    private Utilisateur utilisateur;
    private HomePage page;
    
    public PageFollow(ConnexionMySQL connexion, Utilisateur utilisateur,HomePage page) {
        this.page = page;
        this.connexion = connexion;
        this.utilisateur = utilisateur;
    }

    public GridPane executer(){
        GridPane center = new GridPane();
        center.setPadding(new Insets(10));
        
        // VBox pour empiler les éléments verticalement
        VBox vBox = new VBox();
        vBox.setSpacing(10);  // Espacement entre les éléments
        
        // Récupérer les utilisateurs suivis depuis la base de données
        String query = "SELECT u.id, u.nom_utilisateur " +
                        "FROM utilisateurs u " +
                        "JOIN abonnements a ON u.id = a.id_suivi " +
                        "WHERE a.id_abonne = ?";
        
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setInt(1, utilisateur.getIdUtilisateur());
            ResultSet resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                int idUtilisateur = resultSet.getInt("id");
                String nomUtilisateur = resultSet.getString("nom_utilisateur");
            
                // Créer un label avec le nom de l'utilisateur
                Label label = new Label(nomUtilisateur);
                
                // Créer un StackPane pour le label avec un fond gris et des bords rouges
                StackPane stackPane = new StackPane(label);
                stackPane.setStyle("-fx-background-color: LIGHTGRAY; -fx-border-color: RED; -fx-border-width: 1;");
                
                // Créer un bouton de désabonnement
                Button buttonDesabonner = new Button("Se désabonner");
                buttonDesabonner.setOnAction(e -> desabonnerUtilisateur(idUtilisateur));
                
                // Ajouter le label et le bouton à un HBox
                HBox hBox = new HBox(stackPane, buttonDesabonner);
                hBox.setSpacing(10);
                
                // Ajouter le HBox à la VBox
                vBox.getChildren().add(hBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // Ajouter la VBox au centre de la grille
        center.add(vBox, 0, 0);
    
        return center;
    }

    // Fonction pour se désabonner d'un utilisateur
    private void desabonnerUtilisateur(int idSuivi) {
        // Logique pour se désabonner de l'utilisateur avec l'id donné
        String query = "DELETE FROM abonnements WHERE id_abonne = ? AND id_suivi = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setInt(1, utilisateur.getIdUtilisateur());
            preparedStatement.setInt(2, idSuivi);
            preparedStatement.executeUpdate();
            System.out.println("Se désabonner de l'utilisateur avec l'ID : " + idSuivi);
            this.executer();
            page.setFollow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

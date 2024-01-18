import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

public class HomePage extends BorderPane {
    private Utilisateur utilisateur;
    private AppliReseau appli;
    private ConnexionMySQL connexion;

    public HomePage(ConnexionMySQL connexion, AppliReseau appli, Utilisateur utilisateur) {
        this.appli = appli;
        this.utilisateur = utilisateur;
        this.connexion = connexion;

        // Left side with buttons
        GridPane leftGrid = new GridPane();
        leftGrid.setPadding(new Insets(60));

        Button buttonFollowed = new Button("Accédez à ces follows");
        Button buttonMessage = new Button("Accédez à mes discussions");
        Button button3 = new Button("3");
        Button button4 = new Button("4");
        Button button5 = new Button("5");

        leftGrid.add(buttonFollowed, 0, 0);
        leftGrid.add(buttonMessage, 0, 1);
        leftGrid.add(button3, 0, 2);
        leftGrid.add(button4, 0, 3);
        leftGrid.add(button5, 0, 4);

        setLeft(leftGrid);

        // Center for content (initially empty)
        GridPane centerGrid = new GridPane();
        setCenter(centerGrid);

        // Event handling
        buttonFollowed.setOnAction(e -> handleButtonAction("Button Followed"));
        buttonMessage.setOnAction(e -> handleButtonAction("Button Message"));
        button3.setOnAction(e -> handleButtonAction("Button 3"));
        button4.setOnAction(e -> handleButtonAction("Button 4"));
        button5.setOnAction(e -> handleButtonAction("Button 5"));
    }

    private void handleButtonAction(String buttonLabel) {
        // Replace the content in the center based on the clicked button
        GridPane newContent = new GridPane();
        newContent.setPadding(new Insets(10));
        if (buttonLabel=="Button Followed"){
            setCenter(pageFollow(this.connexion, utilisateur));
        }
    }

    public GridPane pageFollow(ConnexionMySQL connexion, Utilisateur utilisateur) {
        GridPane center = new GridPane();
        center.setPadding(new Insets(10));
        
        // VBox pour empiler les éléments verticalement
        VBox vBox = new VBox();
        vBox.setSpacing(10);  // Espacement entre les éléments
        
        // Récupérer les utilisateurs suivis depuis la base de données
        String query = "SELECT u.nom_utilisateur " +
                        "FROM utilisateurs u " +
                        "JOIN abonnements a ON u.id = a.id_suivi " +
                        "WHERE a.id_abonne = ?";
        
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setInt(1, utilisateur.getIdUtilisateur());
            ResultSet resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                String nomUtilisateur = resultSet.getString("nom_utilisateur");
            
                // Créer un label avec le nom de l'utilisateur
                Label label = new Label(nomUtilisateur);
                
                // Créer un StackPane pour le label avec un fond gris et des bords rouges
                StackPane stackPane = new StackPane(label);
                stackPane.setStyle("-fx-background-color: LIGHTGRAY; -fx-border-color: RED; -fx-border-width: 1;");
            
                // Ajouter le StackPane à la VBox
                vBox.getChildren().add(stackPane);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // Ajouter la VBox au centre de la grille
        center.add(vBox, 0, 0);
    
        return center;
    }

    

}



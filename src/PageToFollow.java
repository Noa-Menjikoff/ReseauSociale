import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PageToFollow {
    private ConnexionMySQL connexion;
    private Utilisateur utilisateur;
    private HomePage page;
    public PageToFollow(ConnexionMySQL connexion, Utilisateur utilisateur,HomePage page) {
        this.connexion = connexion;
        this.page = page;
        this.utilisateur = utilisateur;
    }

    public GridPane executer() throws SQLException {
        connexion.connecter("servinfo-maria", "DBmenjikoff", "menjikoff", "menjikoff");
        GridPane center = new GridPane();
        center.setPadding(new Insets(10));
        VBox vBox = new VBox();
        vBox.setSpacing(10);
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
        for (Utilisateur autreUtilisateur : utilisateurs) {
            if (!estAbonne(autreUtilisateur.getIdUtilisateur())) {
                HBox hBox = new HBox();
                hBox.setSpacing(10);
                Label label = new Label(autreUtilisateur.getNomUtilisateur());
                Button buttonAbonner = new Button("S'abonner");
                buttonAbonner.setOnAction(e -> sAbonner(autreUtilisateur.getIdUtilisateur()));
                hBox.getChildren().addAll(label, buttonAbonner);
                vBox.getChildren().add(hBox);
            }
        }
        center.add(vBox, 0, 0);

        return center;
    }

    private boolean estAbonne(int idSuivi) {
        String query = "SELECT COUNT(*) FROM abonnements WHERE id_abonne = ? AND id_suivi = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setInt(1, utilisateur.getIdUtilisateur());
            preparedStatement.setInt(2, idSuivi);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                return rowCount > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void sAbonner(int idSuivi) {
        String query = "INSERT INTO abonnements (id_abonne, id_suivi) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setInt(1, utilisateur.getIdUtilisateur());
            preparedStatement.setInt(2, idSuivi);
            preparedStatement.executeUpdate();
            System.out.println("S'abonner à l'utilisateur avec l'ID : " + idSuivi);
            page.setToFollow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    
    
}
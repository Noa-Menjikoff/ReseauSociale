/**
 * La classe `PageFollow` représente une page dans une application JavaFX qui affiche une liste d'utilisateurs
 * que l'utilisateur actuel suit. Elle permet à l'utilisateur de ne plus suivre d'autres utilisateurs.
 *
 * La classe interagit avec une base de données MySQL en utilisant la classe `ConnexionMySQL` pour interroger
 * des informations sur les utilisateurs et leurs abonnements.
 *
 */
public class PageFollow {
    // Variables d'instance
    private ConnexionMySQL connexion;
    private Utilisateur utilisateur;
    private HomePage page;

    /**
     * Construit un nouvel objet `PageFollow`.
     *
     * @param connexion  La connexion à la base de données MySQL.
     * @param utilisateur  L'utilisateur actuel.
     * @param page  La page d'accueil de l'application.
     */
    public PageFollow(ConnexionMySQL connexion, Utilisateur utilisateur, HomePage page) {
        this.page = page;
        this.connexion = connexion;
        this.utilisateur = utilisateur;
    }

    /**
     * Exécute la fonctionnalité de la page, récupérant et affichant la liste des utilisateurs
     * que l'utilisateur actuel suit.
     *
     * @return Un `GridPane` contenant les éléments de l'interface utilisateur pour la page.
     * @throws SQLException Si une erreur de base de données se produit.
     */
    public GridPane executer() throws SQLException {
        // Connexion à la base de données
        connexion.connecter("servinfo-maria", "DBmenjikoff", "menjikoff", "menjikoff");

        // Configuration de l'interface utilisateur
        GridPane center = new GridPane();
        center.setPadding(new Insets(10));
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        String query = "SELECT u.id, u.nom_utilisateur FROM utilisateurs u JOIN abonnements a ON u.id = a.id_suivi WHERE a.id_abonne = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setInt(1, utilisateur.getIdUtilisateur());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idUtilisateur = resultSet.getInt("id");
                String nomUtilisateur = resultSet.getString("nom_utilisateur");
                Label label = new Label(nomUtilisateur);
                StackPane stackPane = new StackPane(label);
                stackPane.setStyle("-fx-background-color: LIGHTGRAY; -fx-border-color: RED; -fx-border-width: 1;");
                Button buttonDesabonner = new Button("Se désabonner");
                buttonDesabonner.setOnAction(e -> desabonnerUtilisateur(idUtilisateur));
                HBox hBox = new HBox(stackPane, buttonDesabonner);
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
     * Désabonne l'utilisateur spécifié.
     *
     * @param idSuivi L'identifiant de l'utilisateur à désabonner.
     */
    private void desabonnerUtilisateur(int idSuivi) {
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

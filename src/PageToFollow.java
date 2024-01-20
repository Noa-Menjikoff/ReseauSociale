/**
 * La classe `PageToFollow` représente une page dans une application JavaFX qui affiche la liste des utilisateurs
 * disponibles à suivre (c'est-à-dire les utilisateurs qui ne sont pas encore abonnés par l'utilisateur actuel).
 * Elle permet à l'utilisateur de s'abonner à d'autres utilisateurs.
 *
 * La classe interagit avec une base de données MySQL en utilisant la classe `ConnexionMySQL` pour récupérer
 * la liste des utilisateurs disponibles et gérer les abonnements.
 *
 */
public class PageToFollow {
    // Variables d'instance
    private ConnexionMySQL connexion;
    private Utilisateur utilisateur;
    private HomePage page;

    /**
     * Construit un nouvel objet `PageToFollow`.
     *
     * @param connexion  La connexion à la base de données MySQL.
     * @param utilisateur  L'utilisateur actuel.
     * @param page  La page d'accueil de l'application.
     */
    public PageToFollow(ConnexionMySQL connexion, Utilisateur utilisateur, HomePage page) {
        this.connexion = connexion;
        this.page = page;
        this.utilisateur = utilisateur;
    }

    /**
     * Exécute la fonctionnalité de la page, récupérant et affichant la liste des utilisateurs disponibles
     * à suivre avec des boutons d'abonnement associés.
     *
     * @return Un `GridPane` contenant les éléments de l'interface utilisateur pour la page.
     * @throws SQLException Si une erreur de base de données se produit.
     */
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

    /**
     * Vérifie si l'utilisateur actuel est déjà abonné à un utilisateur spécifié.
     *
     * @param idSuivi L'identifiant de l'utilisateur à vérifier.
     * @return `true` si l'utilisateur actuel est abonné, sinon `false`.
     */
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

    /**
     * Permet à l'utilisateur actuel de s'abonner à un autre utilisateur.
     *
     * @param idSuivi L'identifiant de l'utilisateur à suivre.
     */
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

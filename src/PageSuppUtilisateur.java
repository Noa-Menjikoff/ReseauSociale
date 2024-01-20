/**
 * La classe `PageSuppUtilisateur` représente une page dans une application JavaFX qui affiche tous les utilisateurs
 * de la base de données et permet à l'administrateur de supprimer des utilisateurs, ainsi que tous les messages
 * associés et les abonnements liés.
 *
 * La classe interagit avec une base de données MySQL en utilisant la classe `ConnexionMySQL` pour supprimer
 * les utilisateurs, leurs messages et les abonnements associés.
 *
 */
public class PageSuppUtilisateur {
    // Variables d'instance
    private ConnexionMySQL connexion;
    private Utilisateur utilisateur;
    private Adm page;

    /**
     * Construit un nouvel objet `PageSuppUtilisateur`.
     *
     * @param connexion  La connexion à la base de données MySQL.
     * @param utilisateur  L'utilisateur actuel.
     * @param page  La page d'administration de l'application.
     */
    public PageSuppUtilisateur(ConnexionMySQL connexion, Utilisateur utilisateur, Adm page) {
        this.connexion = connexion;
        this.utilisateur = utilisateur;
        this.page = page;
    }

    /**
     * Exécute la fonctionnalité de la page, récupérant et affichant tous les utilisateurs
     * avec des boutons de suppression associés pour l'administrateur.
     *
     * @return Un `GridPane` contenant les éléments de l'interface utilisateur pour la page.
     */
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

    /**
     * Supprime l'utilisateur spécifié par son identifiant, ainsi que tous ses messages
     * et les abonnements associés.
     *
     * @param idUtilisateur L'identifiant de l'utilisateur à supprimer.
     */
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

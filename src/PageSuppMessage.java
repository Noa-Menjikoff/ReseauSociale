/**
 * La classe `PageSuppMessage` représente une page dans une application JavaFX qui affiche tous les messages
 * de tous les utilisateurs et permet à l'administrateur de supprimer des messages.
 *
 * La classe interagit avec une base de données MySQL en utilisant la classe `ConnexionMySQL` pour récupérer
 * et supprimer les messages.
 *
 */
public class PageSuppMessage {
    // Variables d'instance
    private ConnexionMySQL connexion;
    private Utilisateur utilisateur;
    private Adm page;

    /**
     * Construit un nouvel objet `PageSuppMessage`.
     *
     * @param connexion  La connexion à la base de données MySQL.
     * @param utilisateur  L'utilisateur actuel.
     * @param page  La page d'administration de l'application.
     */
    public PageSuppMessage(ConnexionMySQL connexion, Utilisateur utilisateur, Adm page) {
        this.connexion = connexion;
        this.utilisateur = utilisateur;
        this.page = page;
    }

    /**
     * Exécute la fonctionnalité de la page, récupérant et affichant tous les messages
     * de tous les utilisateurs avec des boutons de suppression associés.
     *
     * @return Un `GridPane` contenant les éléments de l'interface utilisateur pour la page.
     */
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

    /**
     * Supprime le message spécifié par son identifiant.
     *
     * @param idMessage L'identifiant du message à supprimer.
     * @param idUtilisateur L'identifiant de l'utilisateur associé au message.
     */
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

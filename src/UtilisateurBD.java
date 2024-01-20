import java.sql.*;

/**
 * La classe `UtilisateurBD` fournit des méthodes pour effectuer des opérations sur la table des utilisateurs
 * dans une base de données MySQL. Elle gère l'insertion d'un nouvel utilisateur, la récupération du nombre
 * maximal d'identifiants d'utilisateurs, et la connexion d'un utilisateur existant.
 *
 */
public class UtilisateurBD {
    // Instance de la connexion à la base de données
    ConnexionMySQL laConnexion;
    // Déclaration d'un objet Statement pour exécuter les requêtes SQL
    Statement st;
    // Nombre de lignes affectées lors des opérations
    int rowCount = 0;

    /**
     * Constructeur de la classe `UtilisateurBD`.
     *
     * @param laConnexion L'objet `ConnexionMySQL` utilisé pour la connexion à la base de données.
     */
    UtilisateurBD(ConnexionMySQL laConnexion){
        this.laConnexion=laConnexion;
    }

    /**
     * Obtient le numéro maximum d'identifiant d'utilisateur dans la table des utilisateurs.
     *
     * @return Le numéro maximum d'identifiant d'utilisateur.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public int maxNumUtil() throws SQLException{
        st = laConnexion.createStatement();
        ResultSet rs = st.executeQuery("SELECT MAX(id) FROM utilisateurs");
        rs.next();
        int res = rs.getInt(1);
        rs.close();
        return res;
    }

    /**
     * Insère un nouvel utilisateur dans la table des utilisateurs.
     *
     * @param utilisateur L'objet `Utilisateur` à insérer.
     * @return L'objet `Utilisateur` inséré.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public Utilisateur insererUtilisateur(Utilisateur utilisateur) throws SQLException {
        int num = maxNumUtil() + 1;
        String insertQuery = "INSERT INTO utilisateurs (id, nom_utilisateur, mot_de_passe) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = laConnexion.prepareStatement(insertQuery)) {
            pstmt.setInt(1, num);
            pstmt.setString(2, utilisateur.getNomUtilisateur());
            pstmt.setString(3, utilisateur.getMotDePasse());
            pstmt.executeUpdate();
        }
        return utilisateur;
    }

    /**
     * Obtient le nombre de lignes affectées lors des opérations.
     *
     * @return Le nombre de lignes affectées.
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Connecte un utilisateur en vérifiant les informations de connexion dans la base de données.
     *
     * @param pseudoUt Le pseudo de l'utilisateur à connecter.
     * @param mdp Le mot de passe de l'utilisateur à connecter.
     * @return L'objet `Utilisateur` connecté.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    Utilisateur connecterUtilisateur(String pseudoUt, String mdp) throws SQLException {
        String query = "SELECT id, nom_utilisateur, count(mot_de_passe) as cmdp FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?";
        Utilisateur utilisateur = new Utilisateur(0, "FALSE");
        PreparedStatement statement = laConnexion.prepareStatement(query);
        statement.setString(1, pseudoUt);
        statement.setString(2, mdp);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nomUtilisateur = resultSet.getString("nom_utilisateur");
            utilisateur = new Utilisateur(id, nomUtilisateur);
            this.rowCount = resultSet.getInt("cmdp");
        }
        resultSet.close();
        statement.close();
        return utilisateur;
    }
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * La classe ConnexionMySQL représente une connexion à une base de données MySQL/MariaDB.
 */
public class ConnexionMySQL {

    private Connection mysql = null;
    private boolean connecte = false;

    /**
     * Constructeur de la classe ConnexionMySQL.
     *
     * @throws ClassNotFoundException Si la classe du pilote JDBC n'est pas trouvée.
     */
    public ConnexionMySQL() throws ClassNotFoundException {
        this.mysql = null;
        this.connecte = false;
    }

    /**
     * Établit une connexion à la base de données MySQL/MariaDB.
     *
     * @param nomServeur L'adresse du serveur de base de données.
     * @param nomBase    Le nom de la base de données.
     * @param nomLogin   Le nom d'utilisateur pour la connexion.
     * @param motDePasse Le mot de passe pour la connexion.
     * @throws SQLException En cas d'erreur lors de la connexion à la base de données.
     */
    public void connecter(String nomServeur, String nomBase, String nomLogin, String motDePasse) throws SQLException {
        this.mysql = null;
        this.connecte = false;
        this.mysql = DriverManager.getConnection("jdbc:mariadb://" + nomServeur + ":3306/" + nomBase, nomLogin,
                motDePasse);
        this.connecte = this.mysql != null;
    }

    /**
     * Ferme la connexion à la base de données.
     *
     * @throws SQLException En cas d'erreur lors de la fermeture de la connexion.
     */
    public void close() throws SQLException {
        this.mysql.close();
        this.connecte = false;
    }

    /**
     * Vérifie si la connexion à la base de données est établie.
     *
     * @return True si connecté, sinon False.
     */
    public boolean isConnecte() {
        return this.connecte;
    }

    /**
     * Crée un objet Statement pour exécuter des requêtes SQL simples.
     *
     * @return Un objet Statement.
     * @throws SQLException En cas d'erreur lors de la création du Statement.
     */
    public Statement createStatement() throws SQLException {
        return this.mysql.createStatement();
    }

    /**
     * Crée un objet PreparedStatement pour exécuter des requêtes SQL paramétrées.
     *
     * @param requete La requête SQL paramétrée.
     * @return Un objet PreparedStatement.
     * @throws SQLException En cas d'erreur lors de la création du PreparedStatement.
     */
    public PreparedStatement prepareStatement(String requete) throws SQLException {
        return this.mysql.prepareStatement(requete);
    }

    /**
     * Obtient la connexion MySQL associée à cette instance.
     *
     * @return La connexion MySQL.
     */
    public Connection getMySQLConnection() {
        return this.mysql;
    }
}

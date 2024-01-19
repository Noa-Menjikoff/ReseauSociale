
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionMySQL {

    private Connection mysql = null;
    private boolean connecte = false;

    public ConnexionMySQL() throws ClassNotFoundException {
        this.mysql = null;
        this.connecte = false;
    }

    public void connecter(String nomServeur, String nomBase, String nomLogin, String motDePasse) throws SQLException {
        this.mysql = null;
        this.connecte = false;
        this.mysql = DriverManager.getConnection("jdbc:mariadb://" + nomServeur + ":3306/" + nomBase, nomLogin,
                motDePasse);
        this.connecte = this.mysql != null;
    }

    public void close() throws SQLException {
        this.mysql.close();
        this.connecte = false;
    }

    public boolean isConnecte() {
        return this.connecte;
    }

    public Statement createStatement() throws SQLException {
        return this.mysql.createStatement();
    }

    public PreparedStatement prepareStatement(String requete) throws SQLException {
        return this.mysql.prepareStatement(requete);
    }

    // Nouvelle méthode pour obtenir la connexion MySQL
    public Connection getMySQLConnection() {
        return this.mysql;
    }
}
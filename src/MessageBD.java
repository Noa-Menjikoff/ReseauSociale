import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MessageBD {
    private ConnexionMySQL laConnexion;

    public MessageBD(ConnexionMySQL laConnexion) {
        this.laConnexion = laConnexion;
    }

    public int maxId() throws SQLException {
        try (Statement st = laConnexion.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT MAX(id_message) FROM messages");
            rs.next();
            return rs.getInt(1);
        }
    }

    public int insererUtilisateur(int userId, String contenu) throws SQLException {
        int num = maxId() + 1; // Génère le prochain numéro de l'utilisateur

        // Create the SQL statement to insert a new user
        String insertQuery = "INSERT INTO messages (id_message, id_utilisateur, contenu, date_heure) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = laConnexion.prepareStatement(insertQuery)) {
            pstmt.setInt(1, num);
            pstmt.setInt(2, userId);
            pstmt.setString(3, contenu);
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

            // Execute the insert query
            pstmt.executeUpdate();
        }

        return num;
    }
}

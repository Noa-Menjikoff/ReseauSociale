import java.sql.*;
import java.time.LocalDateTime;

public class MessageBD {
	ConnexionMySQL laConnexion;
	Statement st;

	MessageBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	int maxId() throws SQLException{
		st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("SELECT MAX(id_message) FROM messages");
		rs.next();
		int res = rs.getInt(1);
		rs.close();
		return res;
	}

	int insererUtilisateur(int userId,String contenu) throws SQLException {
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


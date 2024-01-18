import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class UtilisateurBD {
	ConnexionMySQL laConnexion;
	Statement st;

	UtilisateurBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	int maxNumUtil() throws SQLException{
		st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("SELECT MAX(id) FROM utilisateurs");
		rs.next();
		int res = rs.getInt(1);
		rs.close();
		return res;
	}


	int insererUtilisateur(Utilisateur utilisateur) throws SQLException {
		int num = maxNumUtil() + 1; // Génère le prochain numéro de l'utilisateur
	
		// Create the SQL statement to insert a new user
		String insertQuery = "INSERT INTO utilisateurs (id, nom_utilisateur, mot_de_passe) VALUES (?, ?, ?)";
		
		try (PreparedStatement pstmt = laConnexion.prepareStatement(insertQuery)) {
			pstmt.setInt(1, num);
			pstmt.setString(2, utilisateur.getNomUtilisateur());
			pstmt.setString(3, utilisateur.getMotDePasse());
	
			// Execute the insert query
			pstmt.executeUpdate();
		}
	
		return num;
	}

	

	int connecterUtilisateur(String pseudoUt, String mdp) throws SQLException {
        String query = "SELECT COUNT(*) FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?";
        int rowCount = 0;

        PreparedStatement statement = laConnexion.prepareStatement(query);
        statement.setString(1, pseudoUt);
        statement.setString(2, mdp);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            rowCount = resultSet.getInt(1);
        }
		resultSet.close();
        statement.close();
        return rowCount;
    }	
	
}


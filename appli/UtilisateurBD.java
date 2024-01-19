import java.sql.*;

public class UtilisateurBD {
	ConnexionMySQL laConnexion;
	Statement st;
	int rowCount = 0;

	UtilisateurBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	public int maxNumUtil() throws SQLException{
		st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("SELECT MAX(id) FROM utilisateurs");
		rs.next();
		int res = rs.getInt(1);
		rs.close();
		return res;
	}

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

	public int getRowCount() {
		return rowCount;
	}

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


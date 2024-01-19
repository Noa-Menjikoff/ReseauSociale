
public class Utilisateur {
    private String pseudo;
    private String motdepasse;
    private int idUtilisateur;

    public Utilisateur(String pseudo, String motdepasse) {
        this.pseudo = pseudo;
        this.motdepasse = motdepasse;
    }

    public Utilisateur(int idUtilisateur, String pseudo){
        this.idUtilisateur = idUtilisateur;
        this.pseudo = pseudo;

    }

    public int getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNomUtilisateur() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotDePasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }


}

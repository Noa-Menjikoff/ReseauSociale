/**
 * La classe `Utilisateur` représente un utilisateur dans l'application.
 * Chaque utilisateur a un identifiant unique, un pseudo et un mot de passe.
 *
 */
public class Utilisateur {
    // Variables d'instance
    private String pseudo;
    private String motdepasse;
    private int idUtilisateur;

    /**
     * Constructeur pour créer un nouvel utilisateur avec un pseudo et un mot de passe.
     *
     * @param pseudo Le pseudo de l'utilisateur.
     * @param motdepasse Le mot de passe de l'utilisateur.
     */
    public Utilisateur(String pseudo, String motdepasse) {
        this.pseudo = pseudo;
        this.motdepasse = motdepasse;
    }

    /**
     * Constructeur pour créer un nouvel utilisateur avec un identifiant, un pseudo.
     *
     * @param idUtilisateur L'identifiant unique de l'utilisateur.
     * @param pseudo Le pseudo de l'utilisateur.
     */
    public Utilisateur(int idUtilisateur, String pseudo){
        this.idUtilisateur = idUtilisateur;
        this.pseudo = pseudo;
    }

    /**
     * Obtient l'identifiant unique de l'utilisateur.
     *
     * @return L'identifiant de l'utilisateur.
     */
    public int getIdUtilisateur() {
        return this.idUtilisateur;
    }

    /**
     * Définit l'identifiant unique de l'utilisateur.
     *
     * @param idUtilisateur Le nouvel identifiant de l'utilisateur.
     */
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    /**
     * Obtient le pseudo de l'utilisateur.
     *
     * @return Le pseudo de l'utilisateur.
     */
    public String getNomUtilisateur() {
        return pseudo;
    }

    /**
     * Définit le pseudo de l'utilisateur.
     *
     * @param pseudo Le nouveau pseudo de l'utilisateur.
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Obtient le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
    public String getMotDePasse() {
        return motdepasse;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     *
     * @param motdepasse Le nouveau mot de passe de l'utilisateur.
     */
    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }
}

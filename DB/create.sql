DROP TABLE IF EXISTS abonnements;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS utilisateurs;

-- Table pour les utilisateurs
CREATE TABLE utilisateurs (
    id INT PRIMARY KEY,
    nom_utilisateur VARCHAR(255) NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    CONSTRAINT unique_nom_utilisateur UNIQUE (nom_utilisateur)
);

-- Table pour les messages
CREATE TABLE messages (
    id_message INT PRIMARY KEY,
    id_utilisateur INT,
    contenu TEXT NOT NULL,
    date_heure DATETIME NOT NULL,
    likes INT DEFAULT 0,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateurs(id)
);

-- Table pour gérer les abonnements
CREATE TABLE abonnements (
    id_abonne INT,
    id_suivi INT,
    PRIMARY KEY (id_abonne, id_suivi),
    FOREIGN KEY (id_abonne) REFERENCES utilisateurs(id),
    FOREIGN KEY (id_suivi) REFERENCES utilisateurs(id)
);

CREATE TABLE like(
    id_utilisateur INT,
    id_message INT,
    PRIMARY KEY (id_utilisateur, id_message),
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateurs(id),
    FOREIGN KEY (id_message) REFERENCES messages(id_message)
);

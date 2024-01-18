
-- Utilisateur 1
INSERT INTO utilisateurs (id, nom_utilisateur, mot_de_passe) VALUES (1, 'utilisateur1', 'pass1');

-- Utilisateur 2
INSERT INTO utilisateurs (id, nom_utilisateur, mot_de_passe) VALUES (2, 'utilisateur2', 'pass2');

-- Utilisateur 3
INSERT INTO utilisateurs (id, nom_utilisateur, mot_de_passe) VALUES (3, 'utilisateur3', 'pass3');

-- Utilisateur 4
INSERT INTO utilisateurs (id, nom_utilisateur, mot_de_passe) VALUES (4, 'utilisateur4', 'pass4');

-- Utilisateur 5
INSERT INTO utilisateurs (id, nom_utilisateur, mot_de_passe) VALUES (5, 'utilisateur5', 'pass5');

-- Utilisateur 6
INSERT INTO utilisateurs (id, nom_utilisateur, mot_de_passe) VALUES (6, 'utilisateur6', 'pass6');

-- Message 1
INSERT INTO messages (id_message, id_utilisateur, contenu, date_heure) VALUES (1, 1, 'Contenu message 1', '2024-01-18 12:00:00');

-- Message 2
INSERT INTO messages (id_message, id_utilisateur, contenu, date_heure) VALUES (2, 2, 'Contenu message 2', '2024-01-18 12:30:00');

-- Message 3
INSERT INTO messages (id_message, id_utilisateur, contenu, date_heure) VALUES (3, 3, 'Contenu message 3', '2024-01-18 13:00:00');

-- Message 4
INSERT INTO messages (id_message, id_utilisateur, contenu, date_heure) VALUES (4, 4, 'Contenu message 4', '2024-01-18 13:30:00');

-- Message 5
INSERT INTO messages (id_message, id_utilisateur, contenu, date_heure) VALUES (5, 5, 'Contenu message 5', '2024-01-18 14:00:00');

-- Message 6
INSERT INTO messages (id_message, id_utilisateur, contenu, date_heure) VALUES (6, 6, 'Contenu message 6', '2024-01-18 14:30:00');


-- Abonnement 1
INSERT INTO abonnements (id_abonne, id_suivi) VALUES (1, 2);

-- Abonnement 2
INSERT INTO abonnements (id_abonne, id_suivi) VALUES (2, 3);

-- Abonnement 3
INSERT INTO abonnements (id_abonne, id_suivi) VALUES (3, 4);

-- Abonnement 4
INSERT INTO abonnements (id_abonne, id_suivi) VALUES (4, 5);

-- Abonnement 5
INSERT INTO abonnements (id_abonne, id_suivi) VALUES (5, 6);

-- Abonnement 6
INSERT INTO abonnements (id_abonne, id_suivi) VALUES (6, 1);

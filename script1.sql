

-- Insertion de départements
INSERT INTO departement (nom) VALUES ('Ile-de-France');
INSERT INTO departement (nom) VALUES ('Auvergne-Rhône-Alpes');
INSERT INTO departement (nom) VALUES ('Provence-Alpes-Côte d\'Azur');
INSERT INTO departement (nom) VALUES ('Occitanie');
INSERT INTO departement (nom) VALUES ('Grand Est');
-- Insertion de villes
INSERT INTO ville (nom, nb_habitants, departement_id) VALUES ('Paris', 2148000, 1),('Boulogne-Billancourt', 121334, 1),
('Saint-Denis', 113116, 1),
('Argenteuil', 110468, 1),
( 'Montreuil', 109914, 1),
('Nanterre', 96021, 1),
('Versailles', 85716, 1),
('Courbevoie', 82473, 1),
('Colombes', 85875, 1),
('Asnières-sur-Seine', 86199, 1);  -- Ile-de-France
INSERT INTO ville (nom, nb_habitants, departement_id) VALUES ('Lyon', 513275, 2);    -- Auvergne-Rhône-Alpes
INSERT INTO ville (nom, nb_habitants, departement_id) VALUES ('Marseille', 861635, 3); -- Provence-Alpes-Côte d'Azur
INSERT INTO ville (nom, nb_habitants, departement_id) VALUES ('Toulouse', 479709, 4); -- Occitanie
INSERT INTO ville (nom, nb_habitants, departement_id) VALUES ('Nice', 342637, 3);     -- Provence-Alpes-Côte d'Azur
INSERT INTO ville (nom, nb_habitants, departement_id) VALUES ('Nantes', 318808, 2);   -- Auvergne-Rhône-Alpes
INSERT INTO ville (nom, nb_habitants, departement_id) VALUES ('Strasbourg', 287228, 5); -- Grand Est
-- Script pour corriger la colonne statut dans la table projets
-- Exécuter ce script directement dans votre base de données MySQL

ALTER TABLE projets MODIFY COLUMN statut VARCHAR(50) NOT NULL;

-- Vérifier que la modification a été appliquée
DESCRIBE projets;








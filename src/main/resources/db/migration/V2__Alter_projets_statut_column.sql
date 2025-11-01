-- Migration pour augmenter la taille de la colonne statut dans la table projets
ALTER TABLE projets MODIFY COLUMN statut VARCHAR(50) NOT NULL;














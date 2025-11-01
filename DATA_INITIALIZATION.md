# Données Initiales - 20 Arrondissements de Paris

Ce projet contient les données initiales pour les 20 arrondissements de Paris avec leurs informations réelles.

## 📊 Données Incluses

### 🏛️ Municipalités (20 arrondissements)
- **Nom** : Mairie du Xème arrondissement
- **Région** : Île-de-France
- **Code postal** : 75001 à 75020
- **Budget annuel** : 12M€ à 60M€ (selon la taille de l'arrondissement)
- **Adresse** : Adresse réelle de chaque mairie d'arrondissement
- **Téléphone** : Numéro de téléphone réel
- **Email** : mairieXX@paris.fr
- **Site web** : https://mairieXX.paris.fr

### 👥 Agents Municipaux (2-3 par arrondissement)
- **Username** : agentXX01, agentXX02, agentXX03
- **Mot de passe** : password123
- **Postes** : Chef de service, Agent municipal, Spécialiste urbanisme
- **Statut** : ACTIF
- **Date d'embauche** : Aléatoire dans les 3 dernières années

### 👤 Citoyens (80 citoyens répartis)
- **Username** : prenom.nom
- **Mot de passe** : password123
- **Âge** : 18-68 ans
- **Commune** : Paris 1er à Paris 20ème
- **Statut** : ACTIF
- **Réclamations** : 0-4 par citoyen
- **Projets participés** : 0-9 par citoyen

## 🚀 Initialisation Automatique

Les données sont chargées automatiquement au démarrage de l'application Spring Boot grâce aux classes :
- `DataInitializer` : Charge les municipalités
- `AgentDataInitializer` : Charge les agents
- `CitoyenDataInitializer` : Charge les citoyens

## 🔍 Vérification des Données

### Via l'API
```bash
# Statistiques générales
GET /api/test/stats

# Liste des municipalités
GET /api/test/municipalites

# Liste des agents
GET /api/test/agents

# Liste des citoyens
GET /api/test/citoyens
```

### Via l'Interface Web
1. Connectez-vous en tant qu'admin
2. Allez dans la section "Municipalités"
3. Vous devriez voir les 20 arrondissements de Paris

## 📋 Informations Détaillées par Arrondissement

| Arrondissement | Code Postal | Budget (M€) | Adresse Principale |
|----------------|-------------|-------------|-------------------|
| 1er | 75001 | 15 | 4 Place du Louvre |
| 2ème | 75002 | 12 | 8 Rue de la Banque |
| 3ème | 75003 | 18 | 2 Rue Eugène Spuller |
| 4ème | 75004 | 22 | 2 Place Baudoyer |
| 5ème | 75005 | 25 | 21 Place du Panthéon |
| 6ème | 75006 | 20 | 78 Rue Bonaparte |
| 7ème | 75007 | 30 | 116 Rue de Grenelle |
| 8ème | 75008 | 35 | 3 Rue de Lisbonne |
| 9ème | 75009 | 28 | 6 Rue Drouot |
| 10ème | 75010 | 32 | 72 Rue du Faubourg-Saint-Martin |
| 11ème | 75011 | 40 | 12 Place Léon-Blum |
| 12ème | 75012 | 45 | 130 Avenue Daumesnil |
| 13ème | 75013 | 50 | 1 Place d'Italie |
| 14ème | 75014 | 38 | 2 Place Ferdinand-Brunot |
| 15ème | 75015 | 55 | 31 Rue Péclet |
| 16ème | 75016 | 60 | 71 Avenue Henri-Martin |
| 17ème | 75017 | 42 | 16 Rue des Batignolles |
| 18ème | 75018 | 48 | 1 Place Jules-Joffrin |
| 19ème | 75019 | 52 | 5 Place Armand-Carrel |
| 20ème | 75020 | 46 | 6 Place Gambetta |

## 🔧 Configuration

### Base de Données
Les données sont insérées dans les tables :
- `municipalites`
- `agents_municipaux`
- `citoyens`

### Sécurité
- Tous les mots de passe par défaut sont `password123`
- Les utilisateurs admin peuvent modifier ces données via l'interface

## 📝 Notes Importantes

1. **Données Réelles** : Les adresses et téléphones sont les vrais de chaque mairie d'arrondissement
2. **Budgets Réalistes** : Les budgets sont proportionnels à la taille de chaque arrondissement
3. **Répartition** : Les citoyens et agents sont répartis de manière réaliste
4. **Statuts** : Tous les éléments sont en statut ACTIF par défaut

## 🎯 Utilisation

Ces données permettent de :
- Tester l'interface admin complète
- Voir les fonctionnalités de gestion des municipalités
- Tester les modals de détail et d'édition
- Vérifier les statistiques et analytics
- Simuler un environnement de production réaliste



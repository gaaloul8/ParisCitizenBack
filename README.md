# Backend - Plateforme Municipale Paris

Backend Spring Boot pour la plateforme de gestion municipale de la ville de Paris.

## 🚀 Technologies

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** avec JWT
- **Spring Data JPA**
- **MySQL** (via XAMPP)
- **Maven**
- **Lombok**

## 📋 Prérequis

1. **Java JDK 17** ou supérieur
2. **Maven 3.6+**
3. **MySQL** (XAMPP) démarré
4. **Git**

## ⚙️ Configuration

### 1. Base de Données MySQL

Assurez-vous que MySQL est démarré via XAMPP. La base de données sera créée automatiquement au premier démarrage.

Configuration par défaut dans `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/plateforme_paris?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
```

### 2. Variables d'environnement (optionnel)

Vous pouvez modifier les configurations dans `src/main/resources/application.properties`:

- Port du serveur : `server.port=8081`
- JWT Secret : `jwt.secret=...`
- JWT Expiration : `jwt.expiration=86400000` (24h)
- CORS : `cors.allowed-origins=http://localhost:4200`

## 🏃 Démarrage

### Avec Maven

```bash
# Se placer dans le dossier backend
cd C:\Users\User\Desktop\pfe\backend

# Compiler le projet
mvn clean install

# Démarrer l'application
mvn spring-boot:run
```

### Avec Java directement

```bash
# Compiler
mvn clean package

# Exécuter le JAR
java -jar target/plateforme-paris-1.0.0.jar
```

L'API sera accessible sur : **http://localhost:8081**

## 📚 Documentation API

### Base URL
```
http://localhost:8081/api/v1
```

### Authentification

#### POST /auth/login
Connexion utilisateur
```json
{
  "email": "admin@example.com",
  "password": "password",
  "role": "admin"
}
```

#### POST /auth/register
Inscription citoyen
```json
{
  "nom": "Dupont",
  "prenom": "Jean",
  "email": "jean.dupont@example.com",
  "password": "password123",
  "commune": "15ème",
  "age": 30
}
```

### Endpoints Principaux

| Endpoint | Méthode | Auth | Description |
|----------|---------|------|-------------|
| `/auth/login` | POST | Non | Connexion |
| `/auth/register` | POST | Non | Inscription |
| `/municipalites` | GET | Oui | Liste municipalités |
| `/agents` | GET | Oui | Liste agents |
| `/citoyens` | GET | Admin/Agent | Liste citoyens |
| `/projets` | GET | Public | Liste projets |
| `/reclamations` | GET | Oui | Liste réclamations |
| `/etablissements` | GET | Public | Liste établissements |

### Headers Requis

Pour les endpoints protégés:
```
Authorization: Bearer <votre_token_jwt>
Content-Type: application/json
```

## 🗂️ Structure du Projet

```
src/main/java/com/municipalite/paris/
├── config/              # Configurations (Security, CORS, etc.)
├── controller/          # Controllers REST
├── dto/                 # Data Transfer Objects
│   ├── auth/           # DTOs d'authentification
│   └── response/       # DTOs de réponse
├── entity/             # Entités JPA
├── exception/          # Gestion des exceptions
├── repository/         # Repositories JPA
├── security/           # JWT et sécurité
└── service/            # Services métier
    └── impl/          # Implémentations
```

## 🔒 Sécurité

- **JWT** pour l'authentification
- **BCrypt** pour le hashage des mots de passe
- **CORS** configuré pour le frontend Angular
- **Validation** des données avec Bean Validation

### Rôles

- **ADMIN** : Accès complet
- **AGENT** : Gestion des réclamations et projets
- **CITOYEN** : Création de réclamations, participation aux projets

## 📊 Base de Données

### Tables Principales

1. **admins** - Administrateurs de la plateforme
2. **municipalites** - Arrondissements de Paris
3. **agents_municipaux** - Agents travaillant pour les municipalités
4. **citoyens** - Utilisateurs citoyens
5. **projets** - Projets sociaux et communautaires
6. **reclamations** - Signalements et réclamations
7. **feedbacks** - Évaluations et feedbacks
8. **etablissements** - Établissements publics

Les relations entre les tables sont gérées automatiquement par JPA.

## 🧪 Tests

```bash
# Exécuter les tests
mvn test

# Exécuter les tests avec couverture
mvn test jacoco:report
```

## 🐛 Debugging

Activer les logs détaillés dans `application.properties`:

```properties
logging.level.com.municipalite=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

## 📝 Endpoints Complets

Consultez le fichier `API_ENDPOINTS_GUIDE.txt` dans le dossier frontend pour la liste complète des endpoints et leur documentation.

## 🔧 Maintenance

### Réinitialiser la base de données

1. Arrêter l'application
2. Dans phpMyAdmin (XAMPP), supprimer la base `plateforme_paris`
3. Redémarrer l'application (la base sera recréée)

### Changer le mode de génération des tables

Dans `application.properties`:
```properties
# create - supprime et recrée les tables
# update - met à jour les tables existantes (par défaut)
# validate - valide le schéma sans modifications
spring.jpa.hibernate.ddl-auto=update
```

## 🤝 Contribution

Ce projet est développé dans le cadre d'un PFE (Projet de Fin d'Études).

## 📄 Licence

Projet académique - Tous droits réservés

---

**Auteur**: Votre Nom  
**Date**: Octobre 2025  
**Version**: 1.0.0



# 🚀 Guide d'Installation - Backend Plateforme Municipale Paris

Ce guide vous accompagne pas à pas pour installer et démarrer le backend Spring Boot.

## ✅ Prérequis

### 1. Java JDK 17

Télécharger et installer Java 17 :
- **Windows**: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- Ou utilisez OpenJDK : https://adoptium.net/

Vérifier l'installation :
```bash
java -version
```
Vous devriez voir : `java version "17.x.x"`

### 2. Maven

Maven est inclus dans le projet (wrapper Maven). Pas besoin de l'installer séparément.

### 3. MySQL via XAMPP

1. Télécharger XAMPP : https://www.apachefriends.org/download.html
2. Installer XAMPP
3. Démarrer le **MySQL** depuis le panneau de contrôle XAMPP
4. Port par défaut : `3306`

## 📦 Installation

### Étape 1 : Cloner/Télécharger le Projet

Le backend est déjà dans le dossier :
```
C:\Users\User\Desktop\pfe\backend\
```

### Étape 2 : Vérifier MySQL

1. Ouvrir le **XAMPP Control Panel**
2. Cliquer sur **Start** pour MySQL
3. Le bouton devrait devenir vert
4. Optionnel : Accéder à phpMyAdmin → http://localhost/phpmyadmin/

### Étape 3 : Configuration (Optionnel)

Le fichier `src/main/resources/application.properties` contient déjà les bonnes configurations.

Si votre MySQL utilise un **mot de passe** différent de vide :
```properties
spring.datasource.password=votre_mot_de_passe
```

## 🏃 Démarrage

### Option 1 : Avec Maven Wrapper (Recommandé)

Ouvrir un terminal dans le dossier backend :

**Windows PowerShell :**
```powershell
cd C:\Users\User\Desktop\pfe\backend
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```

**Windows CMD :**
```cmd
cd C:\Users\User\Desktop\pfe\backend
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

### Option 2 : Avec Maven installé

```bash
cd C:\Users\User\Desktop\pfe\backend
mvn clean install
mvn spring-boot:run
```

### Option 3 : Avec IntelliJ IDEA ou Eclipse

1. Ouvrir le projet dans l'IDE
2. Attendre que Maven télécharge les dépendances
3. Localiser `PlateformeParisApplication.java`
4. Clic droit → **Run**

## ✔️ Vérification

### L'application a démarré avec succès si vous voyez :

```
========================================
  Plateforme Municipale Paris
  API démarrée sur http://localhost:8081
========================================
```

### Tester l'API

Ouvrir un navigateur et accéder à :
```
http://localhost:8081/api/v1/etablissements
```

Ou utiliser Postman/cURL :
```bash
curl http://localhost:8081/api/v1/etablissements
```

## 📊 Base de Données

La base de données `plateforme_paris` sera **créée automatiquement** au premier démarrage.

Pour la visualiser :
1. Aller sur http://localhost/phpmyadmin/
2. La base `plateforme_paris` devrait apparaître
3. Cliquer dessus pour voir les tables

## 🔧 Résolution de Problèmes

### Erreur : "Port 8081 already in use"

Le port est déjà utilisé. Solutions :

1. **Changer le port** dans `application.properties` :
   ```properties
   server.port=8082
   ```

2. **Arrêter le processus** utilisant le port 8081 :
   ```bash
   # Windows
   netstat -ano | findstr :8081
   taskkill /PID <PID> /F
   ```

### Erreur : "Could not connect to MySQL"

1. Vérifier que MySQL est démarré dans XAMPP
2. Vérifier le port (par défaut 3306)
3. Vérifier le mot de passe dans `application.properties`

### Erreur : "JAVA_HOME not set"

Définir la variable d'environnement :
```bash
# Windows
setx JAVA_HOME "C:\Program Files\Java\jdk-17"
```

### Erreur de compilation Maven

Nettoyer et recompiler :
```bash
mvnw clean install -U
```

## 🗃️ Créer des Données de Test

### Méthode 1 : Via l'API

Utiliser les endpoints de création (POST) avec Postman ou cURL.

### Méthode 2 : Via phpMyAdmin

1. Aller sur http://localhost/phpmyadmin/
2. Sélectionner la base `plateforme_paris`
3. Onglet **SQL**
4. Insérer des données SQL

Exemple :
```sql
INSERT INTO admins (username, email, password, nom, prenom, role, statut, date_creation)
VALUES ('admin', 'admin@paris.fr', '$2a$10$...', 'Admin', 'Super', 'SUPER_ADMIN', 'ACTIF', NOW());
```

⚠️ **Note** : Les mots de passe doivent être hashés avec BCrypt.

## 🎯 Prochaines Étapes

1. ✅ Backend démarré
2. 🔜 Démarrer le frontend Angular
3. 🔜 Tester la connexion complète

## 📞 Support

En cas de problème persistant :
1. Vérifier les logs dans le terminal
2. Consulter le fichier `README.md`
3. Vérifier les configurations dans `application.properties`

---

**Date de création** : Octobre 2025  
**Version** : 1.0.0



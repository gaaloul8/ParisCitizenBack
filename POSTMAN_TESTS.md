# 🧪 Tests Postman - Plateforme Municipale Paris

Guide complet pour tester toutes les entités avec Postman.

## 📋 Configuration Initiale

### Variables d'environnement Postman

Créer ces variables dans Postman :
- `base_url` = `http://localhost:8081/api/v1`
- `token` = (sera rempli après login)

---

## 1️⃣ AUTHENTIFICATION

### 🔐 Login Admin

**Méthode** : `POST`  
**URL** : `http://localhost:8081/api/v1/auth/login`  
**Headers** :
```
Content-Type: application/json
```

**Body (JSON)** :
```json
{
  "email": "admin@paris.fr",
  "password": "admin",
  "role": "admin"
}
```

**Réponse attendue** :
```json
{
  "success": true,
  "message": "Connexion réussie",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "user": {
      "id": 1,
      "nom": "Admin",
      "prenom": "Super",
      "email": "admin@paris.fr",
      "role": "admin"
    }
  }
}
```

⚠️ **Important** : Copier le `token` de la réponse pour l'utiliser dans les requêtes suivantes !

---

### 🔐 Login Agent

**Méthode** : `POST`  
**URL** : `http://localhost:8081/api/v1/auth/login`  
**Headers** :
```
Content-Type: application/json
```

**Body (JSON)** :
```json
{
  "email": "agent@paris.fr",
  "password": "agent",
  "role": "agent"
}
```

---

### 🔐 Login Citoyen

**Méthode** : `POST`  
**URL** : `http://localhost:8081/api/v1/auth/login`  
**Headers** :
```
Content-Type: application/json
```

**Body (JSON)** :
```json
{
  "email": "citoyen@example.com",
  "password": "citoyen",
  "role": "citoyen"
}
```

---

### 📝 Register (Inscription Citoyen)

**Méthode** : `POST`  
**URL** : `http://localhost:8081/api/v1/auth/register`  
**Headers** :
```
Content-Type: application/json
```

**Body (JSON)** :
```json
{
  "nom": "Durand",
  "prenom": "Sophie",
  "email": "sophie.durand@example.com",
  "password": "password123",
  "commune": "15ème arrondissement",
  "age": 28,
  "telephone": "0612345678"
}
```

---

## 2️⃣ MUNICIPALITÉS

### 📋 GET - Liste des Municipalités

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/municipalites?page=0&limit=10`  
**Headers** :
```
Authorization: Bearer <votre_token>
```

**Paramètres optionnels** :
- `?page=0&limit=10` - Pagination
- `?statut=ACTIVE` - Filtrer par statut
- `?region=Île-de-France` - Filtrer par région

---

### ➕ POST - Créer une Municipalité (ADMIN uniquement)

**Méthode** : `POST`  
**URL** : `http://localhost:8081/api/v1/municipalites`  
**Headers** :
```
Authorization: Bearer <token_admin>
Content-Type: application/json
```

**Body (JSON)** :
```json
{
  "nom": "Mairie du 20ème arrondissement",
  "region": "Île-de-France",
  "codePostal": "75020",
  "budgetAnnuel": 9800000,
  "tauxSatisfaction": 85,
  "dateCreation": "2020-03-10",
  "statut": "ACTIVE",
  "adresse": "6 Place Gambetta, 75020 Paris",
  "telephone": "01 43 15 20 20",
  "email": "contact@mairie20.paris.fr",
  "siteWeb": "www.mairie20.paris.fr"
}
```

---

### 🔍 GET - Une Municipalité par ID

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/municipalites/1`  
**Headers** :
```
Authorization: Bearer <votre_token>
```

---

## 3️⃣ AGENTS MUNICIPAUX

### 📋 GET - Liste des Agents

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/agents?page=0&limit=10`  
**Headers** :
```
Authorization: Bearer <votre_token>
```

**Paramètres optionnels** :
- `?municipaliteId=1` - Agents d'une municipalité
- `?statut=ACTIF` - Filtrer par statut

---

### ➕ POST - Créer un Agent (ADMIN uniquement)

**Méthode** : `POST`  
**URL** : `http://localhost:8081/api/v1/agents`  
**Headers** :
```
Authorization: Bearer <token_admin>
Content-Type: application/json
```

**Body (JSON)** :
```json
{
  "username": "pierre.moreau",
  "email": "pierre.moreau@paris.fr",
  "password": "password123",
  "nom": "Moreau",
  "prenom": "Pierre",
  "telephone": "0143669920",
  "municipalite": {
    "id": 1
  },
  "poste": "Coordinateur social",
  "dateEmbauche": "2023-09-01",
  "statut": "ACTIF"
}
```

---

### 🔍 GET - Un Agent par ID

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/agents/1`  
**Headers** :
```
Authorization: Bearer <votre_token>
```

---

## 4️⃣ CITOYENS

### 📋 GET - Liste des Citoyens (ADMIN/AGENT)

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/citoyens?page=0&limit=20`  
**Headers** :
```
Authorization: Bearer <token_admin_ou_agent>
```

**Paramètres optionnels** :
- `?municipaliteId=1` - Citoyens d'une municipalité
- `?statut=ACTIF` - Filtrer par statut

---

### 🔍 GET - Un Citoyen par ID

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/citoyens/1`  
**Headers** :
```
Authorization: Bearer <votre_token>
```

---

### ✏️ PUT - Modifier un Citoyen

**Méthode** : `PUT`  
**URL** : `http://localhost:8081/api/v1/citoyens/1`  
**Headers** :
```
Authorization: Bearer <token_citoyen>
Content-Type: application/json
```

**Body (JSON)** :
```json
{
  "nom": "Dupont",
  "prenom": "Marie",
  "email": "marie.dupont@example.com",
  "telephone": "0612345678",
  "age": 35,
  "adresse": "25 Rue de la Convention, 75015 Paris",
  "commune": "15ème arrondissement"
}
```

---

## 5️⃣ PROJETS

### 📋 GET - Liste des Projets (PUBLIC)

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/projets?page=0&limit=10`  
**Headers** : (optionnel si public)
```
Content-Type: application/json
```

**Paramètres optionnels** :
- `?statut=ACTIF` - Projets actifs
- `?municipaliteId=1` - Projets d'une municipalité

---

### ➕ POST - Créer un Projet (ADMIN/AGENT)

**Méthode** : `POST`  
**URL** : `http://localhost:8081/api/v1/projets`  
**Headers** :
```
Authorization: Bearer <token_admin_ou_agent>
Content-Type: application/json
```

**Body (JSON)** :
```json
{
  "titre": "Jardins partagés du 15ème",
  "description": "Création d'espaces verts communautaires pour favoriser le lien social et l'écologie urbaine",
  "dateDebut": "2024-03-01",
  "dateFin": "2024-12-31",
  "statut": "ACTIF",
  "budget": 25000,
  "responsable": {
    "id": 1
  },
  "municipalite": {
    "id": 1
  },
  "arrondissement": "15ème",
  "nombreParticipants": 0,
  "localisation": "Parc Georges Brassens"
}
```

---

### 🔍 GET - Un Projet par ID

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/projets/1`  

---

### 👥 POST - Ajouter un Participant à un Projet

**Méthode** : `POST`  
**URL** : `http://localhost:8081/api/v1/projets/1/participants/1`  
**Headers** :
```
Authorization: Bearer <votre_token>
```

(1er chiffre = ID projet, 2ème chiffre = ID citoyen)

---

## 6️⃣ RÉCLAMATIONS

### 📋 GET - Liste des Réclamations

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/reclamations?page=0&limit=20`  
**Headers** :
```
Authorization: Bearer <votre_token>
```

**Paramètres optionnels** :
- `?statut=NOUVELLE` - Par statut
- `?citoyenId=1` - Réclamations d'un citoyen
- `?municipaliteId=1` - Réclamations d'une municipalité

---

### ➕ POST - Créer une Réclamation

**Méthode** : `POST`  
**URL** : `http://localhost:8081/api/v1/reclamations`  
**Headers** :
```
Authorization: Bearer <token_citoyen>
Content-Type: application/json
```

**Body (JSON)** :
```json
{
  "sujet": "Éclairage défaillant",
  "description": "L'éclairage public de la rue de la Convention ne fonctionne plus depuis 3 jours, créant un problème de sécurité",
  "type": "ECLAIRAGE",
  "priorite": "HAUTE",
  "citoyen": {
    "id": 1
  },
  "municipalite": {
    "id": 1
  },
  "localisation": "Rue de la Convention, 75015 Paris",
  "arrondissement": "15ème",
  "statut": "NOUVELLE"
}
```

---

### 🔍 GET - Une Réclamation par ID

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/reclamations/1`  
**Headers** :
```
Authorization: Bearer <votre_token>
```

---

### ✅ PATCH - Changer le Statut d'une Réclamation (ADMIN/AGENT)

**Méthode** : `PATCH`  
**URL** : `http://localhost:8081/api/v1/reclamations/1/statut?statut=EN_COURS&commentaires=Intervention programmée`  
**Headers** :
```
Authorization: Bearer <token_agent>
```

Statuts disponibles : `NOUVELLE`, `EN_ATTENTE`, `EN_COURS`, `TRAITEE`, `REJETEE`

---

### 👤 PATCH - Assigner un Agent à une Réclamation

**Méthode** : `PATCH`  
**URL** : `http://localhost:8081/api/v1/reclamations/1/assigner?agentId=1`  
**Headers** :
```
Authorization: Bearer <token_agent>
```

---

## 7️⃣ ÉTABLISSEMENTS

### 📋 GET - Liste des Établissements (PUBLIC)

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/etablissements?page=0&limit=20`  

**Paramètres optionnels** :
- `?type=ECOLE` - Par type (ECOLE, HOPITAL, MAIRIE, ASSOCIATION, CULTUREL, SPORTIF, SOCIAL)
- `?arrondissement=15ème` - Par arrondissement

---

### ➕ POST - Créer un Établissement (ADMIN/AGENT)

**Méthode** : `POST`  
**URL** : `http://localhost:8081/api/v1/etablissements`  
**Headers** :
```
Authorization: Bearer <token_admin_ou_agent>
Content-Type: application/json
```

**Body (JSON)** :
```json
{
  "nom": "École élémentaire Voltaire",
  "type": "ECOLE",
  "adresse": "25 Rue Voltaire, 75015 Paris",
  "telephone": "0145321567",
  "email": "contact@ecole-voltaire15.fr",
  "horaires": "Lun-Ven 8h30-16h30",
  "description": "École élémentaire publique avec cantine et garderie",
  "arrondissement": "15ème",
  "municipalite": {
    "id": 1
  },
  "siteWeb": "www.ecole-voltaire15.fr",
  "statut": "OUVERT"
}
```

---

### 🔍 GET - Un Établissement par ID

**Méthode** : `GET`  
**URL** : `http://localhost:8081/api/v1/etablissements/1`  

---

## 📝 RÉSUMÉ DES VALEURS ENUM

### Statuts

**Municipalité** : `ACTIVE`, `INACTIVE`, `MAINTENANCE`  
**Agent/Citoyen** : `ACTIF`, `INACTIF`, `SUSPENDU`  
**Projet** : `BROUILLON`, `ACTIF`, `EN_COURS`, `PLANIFIE`, `TERMINE`, `SUSPENDU`  
**Réclamation** : `NOUVELLE`, `EN_ATTENTE`, `EN_COURS`, `TRAITEE`, `REJETEE`  
**Établissement** : `OUVERT`, `FERME`, `TEMPORAIREMENT_FERME`

### Types

**Type Réclamation** : `VOIRIE`, `ECLAIRAGE`, `DECHETS`, `ESPACES_VERTS`, `TRANSPORT`, `AUTRE`  
**Priorité Réclamation** : `BASSE`, `MOYENNE`, `HAUTE`, `URGENTE`  
**Type Établissement** : `ECOLE`, `HOPITAL`, `MAIRIE`, `ASSOCIATION`, `CULTUREL`, `SPORTIF`, `SOCIAL`

---

## 🎯 ORDRE DE TEST RECOMMANDÉ

1. ✅ **POST** `/auth/login` → Récupérer le token
2. ✅ **GET** `/municipalites` → Vérifier les municipalités créées
3. ✅ **GET** `/agents` → Vérifier les agents
4. ✅ **GET** `/citoyens` → Vérifier les citoyens
5. ✅ **POST** `/projets` → Créer un nouveau projet
6. ✅ **POST** `/reclamations` → Créer une réclamation
7. ✅ **GET** `/etablissements` → Lister les établissements
8. ✅ **POST** `/auth/register` → Créer un nouveau citoyen

---

## 🔧 Configuration Postman

### Variables d'environnement

Créer un environnement "Paris Backend" avec :

| Variable | Valeur |
|----------|--------|
| `base_url` | `http://localhost:8081/api/v1` |
| `token` | (vide au début, rempli après login) |

### Utilisation des variables

Dans les requêtes :
- URL : `{{base_url}}/municipalites`
- Header : `Authorization: Bearer {{token}}`

### Script Post-Response (pour Login)

Dans l'onglet "Tests" de la requête Login, ajouter :
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.environment.set("token", jsonData.data.token);
}
```

Cela enregistrera automatiquement le token après le login !

---

## 🚨 Codes de Réponse HTTP

- **200 OK** : Succès (GET, PUT, PATCH)
- **201 Created** : Ressource créée (POST)
- **400 Bad Request** : Données invalides
- **401 Unauthorized** : Token manquant/invalide
- **403 Forbidden** : Permissions insuffisantes
- **404 Not Found** : Ressource non trouvée
- **500 Internal Server Error** : Erreur serveur

---

**Bon test ! 🧪✨**



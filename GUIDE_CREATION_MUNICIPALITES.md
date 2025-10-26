# Guide pour Créer de Nouvelles Municipalités

## 🎯 Méthodes Disponibles

### 1. 📱 Interface Web (Le Plus Simple)
1. Connectez-vous en tant qu'admin (admin@paris.fr / admin)
2. Allez dans "Municipalités" 
3. Cliquez sur "➕ Ajouter une municipalité"
4. Remplissez le formulaire :
   - Nom de la municipalité
   - Région
   - Code postal
   - Budget annuel
   - Adresse
   - Téléphone
   - Email
   - Site web

### 2. 🌐 API REST (Pour Développeurs)
```bash
POST http://localhost:8083/api/municipalites
Content-Type: application/json

{
  "nom": "Mairie de Neuilly-sur-Seine",
  "region": "Île-de-France",
  "codePostal": "92200",
  "budgetAnnuel": 25000000.00,
  "adresse": "96 Avenue du Général de Gaulle, 92200 Neuilly-sur-Seine",
  "telephone": "01 40 88 88 88",
  "email": "contact@neuillysurseine.fr",
  "siteWeb": "https://www.neuillysurseine.fr"
}
```

### 3. 💾 Base de Données Directe
```sql
INSERT INTO municipalites (
  nom, region, code_postal, budget_annuel, 
  adresse, telephone, email, site_web, 
  date_creation, statut
) VALUES (
  'Mairie de Boulogne-Billancourt',
  'Île-de-France', 
  '92100',
  30000000.00,
  '26 Avenue André-Morizet, 92100 Boulogne-Billancourt',
  '01 41 41 41 41',
  'contact@boulognebillancourt.com',
  'https://www.boulognebillancourt.com',
  CURDATE(),
  'ACTIVE'
);
```

### 4. 🔧 Code Java (Pour Développeurs)
```java
// Dans un service ou contrôleur
Municipalite nouvelleMunicipalite = new Municipalite();
nouvelleMunicipalite.setNom("Mairie de Levallois-Perret");
nouvelleMunicipalite.setRegion("Île-de-France");
nouvelleMunicipalite.setCodePostal("92300");
nouvelleMunicipalite.setBudgetAnnuel(new BigDecimal("28000000.00"));
nouvelleMunicipalite.setAdresse("1 Place de la République, 92300 Levallois-Perret");
nouvelleMunicipalite.setTelephone("01 47 15 71 00");
nouvelleMunicipalite.setEmail("contact@levallois.fr");
nouvelleMunicipalite.setSiteWeb("https://www.levallois.fr");
nouvelleMunicipalite.setDateCreation(LocalDate.now());
nouvelleMunicipalite.setStatut(Municipalite.StatutMunicipalite.ACTIVE);

municipaliteRepository.save(nouvelleMunicipalite);
```

## 📋 Exemples de Municipalités à Créer

### Communes de la Petite Couronne
- **Neuilly-sur-Seine** (92200) - Budget: 25M€
- **Boulogne-Billancourt** (92100) - Budget: 30M€  
- **Levallois-Perret** (92300) - Budget: 28M€
- **Issy-les-Moulineaux** (92130) - Budget: 22M€
- **Clichy** (92110) - Budget: 18M€
- **Asnières-sur-Seine** (92600) - Budget: 20M€

### Communes de la Grande Couronne
- **Versailles** (78000) - Budget: 45M€
- **Nanterre** (92000) - Budget: 35M€
- **Créteil** (94000) - Budget: 40M€
- **Bobigny** (93000) - Budget: 32M€

## 🚀 Recommandation

**Utilisez l'interface web** - c'est la méthode la plus simple et sécurisée :
1. ✅ Validation automatique des données
2. ✅ Gestion des erreurs intégrée
3. ✅ Interface utilisateur intuitive
4. ✅ Pas besoin de connaissances techniques

## 🔍 Vérification

Après création, vous pouvez vérifier que la municipalité a été ajoutée :
1. Rafraîchir la page des municipalités
2. Chercher la nouvelle municipalité dans la liste
3. Cliquer sur "Voir" pour vérifier les détails

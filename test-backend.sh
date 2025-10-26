#!/bin/bash

echo "=== Test de démarrage du backend ==="

# Compiler le projet
echo "1. Compilation du projet..."
mvn compile

if [ $? -eq 0 ]; then
    echo "✅ Compilation réussie"
else
    echo "❌ Erreur de compilation"
    exit 1
fi

# Démarrer le backend en arrière-plan
echo "2. Démarrage du backend..."
mvn spring-boot:run &
BACKEND_PID=$!

# Attendre que le backend démarre
echo "3. Attente du démarrage (30 secondes)..."
sleep 30

# Tester si le backend répond
echo "4. Test de connectivité..."
curl -s -o /dev/null -w "%{http_code}" http://localhost:8083/api/municipalites

if [ $? -eq 0 ]; then
    echo "✅ Backend démarré avec succès"
else
    echo "❌ Backend ne répond pas"
fi

# Arrêter le backend
echo "5. Arrêt du backend..."
kill $BACKEND_PID

echo "=== Test terminé ==="

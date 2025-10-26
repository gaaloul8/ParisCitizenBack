# Script PowerShell pour corriger la colonne statut dans la base de données
# Assurez-vous que MySQL est en cours d'exécution et que vous avez les bonnes informations de connexion

Write-Host "Correction de la colonne statut dans la table projets..." -ForegroundColor Green

# Remplacez ces valeurs par vos informations de connexion MySQL
$mysqlHost = "localhost"
$mysqlPort = "3306"
$mysqlUser = "root"
$mysqlPassword = "password"
$mysqlDatabase = "plateforme_paris"

# Commande SQL à exécuter
$sqlCommand = "ALTER TABLE projets MODIFY COLUMN statut VARCHAR(50) NOT NULL;"

try {
    # Exécuter la commande MySQL
    $result = mysql -h $mysqlHost -P $mysqlPort -u $mysqlUser -p$mysqlPassword $mysqlDatabase -e $sqlCommand
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Colonne statut modifiée avec succès!" -ForegroundColor Green
        Write-Host "La colonne statut peut maintenant accepter des valeurs jusqu'à 50 caractères." -ForegroundColor Yellow
    } else {
        Write-Host "❌ Erreur lors de la modification de la colonne statut." -ForegroundColor Red
        Write-Host "Vérifiez vos informations de connexion MySQL." -ForegroundColor Yellow
    }
} catch {
    Write-Host "❌ Erreur lors de l'exécution du script: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Assurez-vous que MySQL est installé et accessible depuis PowerShell." -ForegroundColor Yellow
}

Write-Host "`nPour vérifier que la modification a été appliquée, exécutez:" -ForegroundColor Cyan
Write-Host "mysql -h $mysqlHost -P $mysqlPort -u $mysqlUser -p$mysqlPassword $mysqlDatabase -e 'DESCRIBE projets;'" -ForegroundColor White












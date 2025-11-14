# 🌍 Application Kotlin – Drapeaux du monde

Ce projet est une application Android réalisée en Kotlin avec Jetpack Compose.  
Elle affiche les pays du monde grâce à l'API CountryFlags et permet de consulter le drapeau de chaque pays.  
J’ai aussi ajouté un système de favoris pour sauvegarder certains pays en local.

L’application contient **3 écrans principaux** :

---

## 🏠 1. Écran d’accueil (Home)

- Affiche la liste complète des pays.
- Barre de recherche pour filtrer par nom ou par code.
- Possibilité d’accéder à un pays au hasard.
- Bouton ⭐ en haut à droite qui mène à l’écran des favoris.
- Lorsque l’on clique sur un pays, on arrive sur l’écran détaillé.

---

## 🚩 2. Écran détail d’un pays

Cet écran affiche :

- le drapeau du pays,
- le code du pays,
- plusieurs tailles possibles,
- deux styles possibles : *flat* ou *shiny*.

On peut aussi :

- revenir en arrière grâce à la flèche ←,
- ajouter ou retirer le pays des favoris grâce au bouton ⭐.

---

## ⭐ 3. Écran des favoris

- Liste des pays que l’utilisateur a ajoutés en favoris.
- Les favoris sont enregistrés grâce à **Room** (base de données locale).
- Possibilité de revenir à l’accueil via la flèche ←.

---

## ⚙️ Technologies utilisées

- **Kotlin**
- **Jetpack Compose** pour l’interface
- **Navigation Compose** pour les 3 écrans
- **Ktor Client** pour récupérer les pays via une API
- **FlagsAPI** pour afficher les drapeaux
- **Room** pour sauvegarder les favoris
- **ViewModel + Flow** pour la gestion de l’état

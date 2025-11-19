**Application Kotlin – Pays et drapeaux du monde**
Cette application Android réalisée en Kotlin et Jetpack Compose permet d’afficher les pays du monde, leurs drapeaux et quelques informations complémentaires.
L’utilisateur peut également enregistrer des pays en favoris grâce à une base de données locale avec Room.

1. Écran d’accueil
   L’écran d’accueil propose :
   la liste complète des pays,
   une barre de recherche (par nom ou par code),
   un bouton permettant d’afficher un pays au hasard,
   un accès rapide à la page des favoris.
   En sélectionnant un pays, on accède à son écran de détails.

2. Écran détail d’un pays
   Cet écran contient :
   le drapeau du pays,
   plusieurs tailles possibles,
   deux styles : flat et shiny,
   un bouton pour ajouter ou retirer des favoris,
   un bouton pour accéder aux informations du pays issues de l’API Wikipédia,
   un bouton de retour vers l’accueil.

3. Écran informations (API Wikipédia)
   Cet écran affiche :
   le titre Wikipédia,
   un résumé descriptif du pays,
   un lien vers la page complète, lorsqu’il est disponible.
   Si l’API ne renvoie pas d’informations exploitables, un message adapté est affiché.

4. Écran des favoris
   L’écran des favoris présente :
   la liste des pays ajoutés en favoris,
   les données sauvegardées localement via Room,

5. Technologies utilisées
   Kotlin
   Jetpack Compose
   Navigation Compose
   Ktor Client (APIs FlagsAPI et Wikipédia)
   FlagsAPI (images des drapeaux)
   Wikipédia REST API
   Room (base locale pour les favoris)
   ViewModel et StateFlow
   Coil (chargement des images)
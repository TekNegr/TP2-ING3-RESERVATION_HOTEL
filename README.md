# TP Booking Hotel — Java POO (TP2/TP3)

## Présentation
Ce projet académique modélise un système de réservation d’hébergements inspiré de Booking, dans le cadre d’un fil rouge POO (TP2 → TP3).

Objectifs pédagogiques principaux :
- passer de la modélisation UML (cas d’usage + classes) à l’implémentation Java,
- appliquer les notions de POO (encapsulation, héritage, polymorphisme, associations),
- implémenter des règles métier réalistes (disponibilité, confirmation, annulation, notation).

---

## État actuel du projet
La version active est basée sur :
- une entrée unique `src/App.java`,
- un package métier `src/Assets/` contenant l’ensemble des classes fonctionnelles.

### Architecture
- **Entrée** : `App`
- **Contrôleur CLI** : `Assets.CLI`
- **Stockage en mémoire** : `Assets.Database`
- **Domaine** :
  - `Personne` (abstraite)
  - `Client`, `NouveauClient`, `AncienClient`
  - `Housing`, `Amenity`, `Rating`
  - `Reservation`, `ReservationStatus`
  - `HousingType`

---

## Fonctionnalités implémentées
### Comptes
- inscription `register_new` / `register_old`
- connexion / déconnexion
- suppression de compte avec annulation des réservations futures liées

### Hébergements
- ajout, modification, suppression
- consultation (liste + détail par id/nom/adresse)
- gestion des amenities (ajout, édition, suppression)

### Réservations
- création (`PENDING`)
- confirmation par propriétaire (`CONFIRMED`)
- modification / annulation selon règles d’accès
- consultation (liste + détail + filtres par logement)

### Notation
- ajout d’une note (`rate_house`) après réservation confirmée
- affichage du détail des notes dans les détails de logement

---

## Lancer le projet
## Option 1 — VS Code
- Ouvrir le dossier `TP2-ING3-RESERVATION_HOTEL`
- Lancer la classe `App` (Run Java)

## Option 2 — Terminal (Java 21+)
Depuis le dossier du projet :

```bash
javac -d bin src/App.java src/Assets/*.java
java -cp bin App
```

---

## Commandes CLI principales
- `help`
- `register_new <first>|<last>|<email>|<password>`
- `register_old <first>|<last>|<email>|<password>`
- `login <email> <password>`
- `add_housing <name>|<address>|<type>|<maxCapacity>|<price>|<description>`
- `add_amenity <housingId>|<amenityName>|<description>`
- `reserve <housingId> <startYYYY-MM-DD> <endYYYY-MM-DD> <guests>`
- `confirm_reservation <reservationId>`
- `rate_house <housingId> <score(1-5)> <comment>`
- `list_housings`, `list_reservations`, `list_users`
- `delete_account`
- `exit`

---

## Documents du rendu
- Rapport : `../RAPPORT.md`
- Synthèse UML / cas d’usage : `CLASSES.md`

---

## Remarque pédagogique
Le stockage est volontairement en mémoire (`Database`) pour rester focalisé sur les notions POO et les règles métier du TP.

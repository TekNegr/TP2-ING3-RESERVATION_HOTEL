# Todo

## TP4

### TP4 Compliance Todo

This checklist is based on `TP4-Java-Classe abstraite Interface Polymorphisme.pdf` and your current codebase state.

## Goal
Make the current project fully compliant with TP4 requirements while keeping existing TP2/TP3 features.

## 1) Exercice 1 - Abstract class + inherited class (Status : done)

### 1.1 Add at least one abstract method in the abstract class *(done)*
- File: `src/Assets/Personne.java`
- Change:
  - Add one abstract method, for example: 
    - `public abstract void afficher();`
- Why:
  - TP4 explicitly requires at least one abstract method in the abstract class.

### 1.2 Implement the abstract method in inherited classes *(done)*
- Files:
  - `src/Assets/Client.java`
  - `src/Assets/NouveauClient.java` (only if needed for specialization)
  - `src/Assets/AncienClient.java` (only if needed for specialization)
- Change:
  - Implement `afficher()` at least in `Client` (or make `Client` abstract and implement in subclasses).
  - Print key attributes from `Personne` and `Client`.

### 1.3 Add required constructors in the chosen inherited class *(done)*
- Recommended class for TP4 demo: `Client` (or create a dedicated class for TP4 demo)
- File: `src/Assets/Client.java`
- Required constructors:
  1. Default constructor (no args)
  2. Constructor with parameters
  3. Copy constructor: takes `Client other`
- Important:
  - Use `super(...)` for inherited fields.
  - In copy constructor, copy all relevant attributes into `this`.

### 1.4 Ensure private attributes + getters in inherited class *(done)*
- File: `src/Assets/Client.java`
- Check:
  - At least 2 private attributes (already true: `registrationDate`, `loyaltyPoints`).
  - Getters exist for private fields (already mostly true).

## 2) Exercice 2 - Override toString()

### 2.1 Override toString in abstract class and inherited class *(done)*
- Files:
  - `src/Assets/Personne.java`
  - `src/Assets/Client.java`
- Change:
  - Add `@Override public String toString()` in both classes.
  - In `Client.toString()`, include `super.toString()` + client-specific fields.

## 3) Exercice 3 - Main class instantiation and display flow

### 3.1 Add a TP4 demo flow in main (or separate test class) *(done)*
- Preferred file:
  - `src/App.java`
- Alternative:
  - create `src/TestExercice1.java` and keep `App` unchanged.
- Required flow:
  1. Instantiate object with default constructor.
  2. Read at least one value from keyboard with validation loop.
  3. Instantiate object with parameterized constructor.
  4. Instantiate object with copy constructor.
  5. For all 3 objects:
    - call `afficher()`
    - print `toString()`

### 3.2 Keep your existing CLI behavior intact *(done)*
- If using `App.java` for TP4 demo, either:
  - run demo then start CLI, or
  - create a separate launcher class for TP4 to avoid interfering with booking CLI.

## 4) Exercice 4 - Interface + collection + Comparable + sorting

### 4.1 Create an interface for managing a dynamic list
- New file suggestion: `src/Assets/PersonneCollection.java`
- Content:
  - `ArrayList<Personne>` storage contract
  - `void ajouter(Personne p);`
  - optional: `List<Personne> getAll();`

### 4.2 Create a concrete class implementing that interface
- New file suggestion: `src/Assets/PersonneCollectionImpl.java`
- Content:
  - internal `ArrayList<Personne>`
  - implement `ajouter(...)`

### 4.3 Make abstract class implement Comparable
- File: `src/Assets/Personne.java`
- Change:
  - `public abstract class Personne implements Comparable<Personne>`
  - Implement `compareTo(Personne other)` based on one criterion.
- Suggested criterion:
  - `createdAt` (available now), or
  - add a numeric criterion if you want stronger sorting semantics.

### 4.4 Add sorting usage in a test/demo flow
- File: `src/App.java` or `src/TestExercice1.java`
- Change:
  - Store multiple objects in the collection.
  - Use `Collections.sort(...)`.
  - Print before/after order.

### 4.5 Add instanceof/casting example (requested in TP4 hints)
- File: `src/App.java` or `src/TestExercice1.java`
- Change:
  - Iterate over collection and show behavior for `NouveauClient` vs `AncienClient`.

## 5) Exercice 5 - Optional filters *(done)*

### 5.1 Optional improvements for full "go further" coverage
- Files:
  - `src/Assets/Database.java`
  - `src/Assets/CLI.java`
- Add combined filters such as:
  - by location + type
  - by date availability
  - by max price

## 6) Small logic bug to fix (not TP4 requirement, but recommended)

### 6.1 Reservation edit permission check is too strict
- File: `src/Assets/CLI.java`
- Current line to review: around `if (reservation.getClient() != currentClient || reservation.getHousing().getOwner() != currentClient)`
- Problem:
  - This requires the same user to be both client and owner, which is usually unintended.
- Likely expected behavior:
  - client can edit own reservation (or owner can, depending on your rule), not both simultaneously.

## 7) Acceptance checklist before submission

- [x] `Personne` has at least one abstract method.
- [x] Inherited class implements the abstract method.
- [x] Inherited class has default + parameterized + copy constructors.
- [x] `toString()` overridden in abstract + inherited class.
- [x] Main/test class demonstrates 3 constructor instantiations.
- [x] Keyboard input with validation loop exists in demo.
- [x] `afficher()` and `toString()` called for all 3 objects.
- [x] Interface + `ArrayList` implementation exists.
- [x] `Comparable` implemented and `compareTo` defined.
- [x] `Collections.sort(...)` demonstrated.

## 8) Build and run commands

From project root:

```powershell
$sources = @('src\App.java') + (Get-ChildItem 'src\Assets\*.java' | ForEach-Object { $_.FullName })
javac -d bin $sources
java -cp bin App
```

If you create a separate test launcher, run:

```powershell
java -cp bin TestExercice1
```

## TP5

### TP5 Compliance Todo

This checklist is based on `TP5-Java-Exceptions - Fichier - Sérialisation.pdf` and your current codebase state.

### Goal
Make the project compliant with TP5 by adding exception propagation, text-file persistence, and serialization/deserialization.

### 1) Exercice 1 - Exceptions in Booking app

#### 1.1 Identify and formalize throwable cases in domain/service methods
- Files to update:
  - `src/Assets/Reservation.java`
  - `src/Assets/Housing.java`
  - `src/Assets/Database.java`
  - `src/Assets/Client.java` (if validation added there)
- Change:
  - Replace silent invalid states with explicit exceptions.
  - Prefer typed exceptions (`IllegalArgumentException`, `IllegalStateException`, `DateTimeParseException`) instead of broad `Exception`.

#### 1.2 Propagate exceptions from methods (`throws`) where relevant
- Files to update:
  - same as above + `src/Assets/CLI.java`
- Change:
  - Let domain/database methods throw exceptions.
  - Catch exceptions at CLI boundary and print user-friendly messages.

#### 1.3 Reduce `catch (Exception e)` in CLI
- File:
  - `src/Assets/CLI.java`
- Change:
  - Replace generic catch blocks with targeted catches per command.
  - Keep one fallback catch only if needed.

#### 1.4 (Optional but recommended) Add custom exception classes
- New files suggestion:
  - `src/Assets/exceptions/ValidationException.java`
  - `src/Assets/exceptions/NotFoundException.java`
  - `src/Assets/exceptions/BookingConflictException.java`

### 2) Exercice 2 - Text file (.txt) for housings

#### 2.1 Create a text persistence service
- New file suggestion:
  - `src/Assets/HousingTextRepository.java`
- Methods to implement:
  - `saveToTextFile(List<Housing> housings, String path)`
  - `loadFromTextFile(String path): List<Housing>`

#### 2.2 Respect TP5 line format with `;` separators
- Required structure per line:
  - housing name
  - address
  - photo link(s)
  - client comments (can be joined with `-`)
  - options/amenities description
- Note:
  - Your current `Housing` model does not include a photo-link field. Add one or document a mapping strategy.

#### 2.3 Add display method for loaded data
- File:
  - `src/Assets/HousingTextRepository.java` or `src/Assets/CLI.java`
- Change:
  - Add method that prints loaded housings clearly.

#### 2.4 Add CLI commands for text import/export
- File:
  - `src/Assets/CLI.java`
- Suggested commands:
  - `export_housings_txt <path>`
  - `import_housings_txt <path>`

### 3) Exercice 3 - Serialization/Deserialization (.ser)

#### 3.1 Make involved classes serializable
- Files to update:
  - `src/Assets/Housing.java`
  - `src/Assets/Amenity.java`
  - `src/Assets/Rating.java`
  - `src/Assets/Client.java` and parent chain if referenced in serialized graph
  - enums are already serializable by default
- Change:
  - `implements Serializable`
  - add `private static final long serialVersionUID = 1L;`

#### 3.2 Create binary repository for `.ser`
- New file suggestion:
  - `src/Assets/HousingBinaryRepository.java`
- Methods to implement:
  - `saveToSer(List<Housing> housings, String path)`
  - `loadFromSer(String path): List<Housing>`

#### 3.3 Add CLI commands for serialization flow
- File:
  - `src/Assets/CLI.java`
- Suggested commands:
  - `export_housings_ser <path>`
  - `import_housings_ser <path>`

### 4) Main/demo requirement for TP5

#### 4.1 Add explicit try/catch demo in main (or dedicated test launcher)
- File:
  - `src/App.java` or new `src/TestTP5.java`
- Required flow:
  - Call methods that may throw exceptions.
  - Catch each exception type separately and print clear error messages.
  - Demonstrate text save/load and binary save/load at least once.

### 5) TP5 acceptance checklist

- [ ] Domain/service methods throw meaningful typed exceptions.
- [ ] CLI catches targeted exception types and prints clear messages.
- [ ] Housings can be exported to `.txt` in TP5 format.
- [ ] Housings can be loaded back from `.txt` and displayed.
- [ ] Serializable classes are correctly marked.
- [ ] Housings can be exported to `.ser` and loaded back.
- [ ] Main/test class demonstrates exception catching + file + serialization flows.

### 6) Build and run commands

From project root:

```powershell
$sources = @('src\App.java') + (Get-ChildItem 'src\Assets\*.java' | ForEach-Object { $_.FullName })
javac -d bin $sources
java -cp bin App
```

If you create a dedicated TP5 launcher, run:

```powershell
java -cp bin TestTP5
```

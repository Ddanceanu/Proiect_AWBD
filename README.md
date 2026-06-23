# Aplicatie web pentru gestionarea unei biblioteci
## Proiect AWBD

## Introducere
Prezenta aplicatie reprezinta un sistem informatic web destinat administrarii proceselor esentiale din cadrul unei biblioteci. Solutia este dezvoltata pe platforma Spring Boot si urmareste implementarea unui flux complet de gestiune pentru principalele entitati de domeniu.

Obiectivele proiectului sunt:
- administrarea entitatilor bibliotecii (carti, autori, categorii, cititori, edituri, imprumuturi, roluri, utilizatori);
- asigurarea unui mecanism de autentificare si autorizare bazat pe roluri;
- validarea datelor la nivel de server;
- separarea clara a configuratiilor de rulare pentru development si testare.

## Diagrama ER

Diagrama ER a fost realizata utilizand platforma dbdiagram.io.

![Diagrama ER](docs/diagrama_ER.png)

## Contributii si extinderi implementate

### 1. Componenta de securitate
Au fost implementate urmatoarele elemente:
- pagina de autentificare personalizata si mecanism explicit de logout;
- autentificare pe baza utilizatorilor persistati in baza de date prin serviciul CustomUserDetailsService;
- autorizare diferentiata pe roluri (ADMIN, USER);
- criptarea parolelor prin BCryptPasswordEncoder.

### 2. Functionalitati CRUD complete
Au fost adaugate controllere, servicii, repository-uri si view-uri pentru urmatoarele module:
- Autor
- Carte
- Categorie
- Cititor
- Editura
- Imprumut
- Rol
- Utilizator

### 3. Interfata utilizator si navigare
Au fost realizate urmatoarele imbunatatiri la nivel de interfata:
- template-uri Thymeleaf de listare si formular pentru fiecare modul;
- fragment comun de navigare (navbar) integrat in paginile de listare;
- pagina dedicata de autentificare;
- pagini de eroare personalizate pentru codurile 403, 404 si 500.

### 4. Initializare date
La pornirea aplicatiei este realizata initializarea automata a datelor minimale:
- inserarea datelor de baza in cazul in care acestea lipsesc;
- crearea unui utilizator administrator implicit pentru testarea locala.

### 5. Testare automata
A fost extinsa suita de teste pentru urmatoarele zone:
- incarcarea contextului aplicatiei;
- validarea regulilor de securitate (acces login si redirectionare pentru utilizator neautentificat);
- verificarea logicii de business la nivel de service pentru utilizatori si imprumuturi.

## Arhitectura aplicatiei
Arhitectura urmareste modelul stratificat specific aplicatiilor enterprise:
- entity: modelul de date si relatiile JPA;
- repository: accesul la date prin Spring Data JPA;
- service: implementarea logicii de business;
- controller: gestionarea fluxurilor HTTP si MVC;
- templates: componenta de prezentare bazata pe Thymeleaf.

## Tehnologii utilizate
- Java 17
- Spring Boot 4.0.6
- Spring Data JPA
- Spring Security
- Thymeleaf
- Maven Wrapper
- PostgreSQL (profil dev)
- H2 in-memory (profil test)

## Ghid de rulare locala

### 1. Rulare in profil development
Se recomanda verificarea prealabila a urmatoarelor conditii:
- serverul PostgreSQL este activ;
- parametrii de conectare din fisierul application-dev.yml sunt configurati corect.

Comanda pentru Windows:
```bash
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

Comanda pentru Linux/macOS:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Adresa implicita de acces:
- http://localhost:8080

Utilizator implicit (daca este creat de componenta de initializare):
- username: admin
- parola: admin123

### 2. Rulare teste automate
Comanda pentru Windows:
```bash
.\mvnw.cmd clean test -Dspring.profiles.active=test
```

Comanda pentru Linux/macOS:
```bash
./mvnw clean test -Dspring.profiles.active=test
```

Nota:
- profilul test utilizeaza baza de date H2 in memorie, eliminand dependenta de PostgreSQL pentru executia testelor.

## Status implementare
- functionalitatile de baza sunt implementate si operationale;
- componenta de securitate este configurata si functionala;
- operatiile CRUD principale sunt disponibile pentru toate entitatile relevante;
- exista o baza de teste automate pentru scenariile critice.

## Concluzie
In forma curenta, proiectul ofera un nucleu functional solid pentru gestionarea unei biblioteci, respectand principiile arhitecturii stratificate, cerintele de securitate de baza si standardele minime de testare automata. Structura implementata permite extinderea ulterioara a functionalitatilor fara modificari majore de design.

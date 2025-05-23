
# 📚 **Student Management System – SMS**

Ky është komponenti backend i **Student Management System (SMS)** – një aplikacion i zhvilluar me **Spring Boot**, i cili përfaqëson shtyllën logjike të sistemit tonë të menaxhimit të studentëve. Backend-i trajton logjikën e biznesit, menaxhimin e të dhënave, sigurinë, si dhe ofron API REST për konsumimin nga frontend-i.

---

## ⚙️ Teknologjitë e përdorura

* **Java 17(21)**
* **Spring Boot**
* **Spring Security (JWT)**
* **Spring Data JPA**
* **MySQL**
* **Flyway (Migrime databaze)**
* **Maven / Gradle**
* **Swagger UI**

---

## 🧠 Arkitektura dhe Strukturimi

Struktura e projektit ndjek praktikat më të mira të zhvillimit me Spring Boot:

* `controller/` – Përmban 21 REST Controller të organizuar sipas entiteteve kryesore.
* `dto/` – Përmban klasat DTO për transferimin dhe validimin e të dhënave ndërmjet shtresave.
* `model/` – Përmban entitetet JPA që përfaqësojnë strukturën e tabelave në databazë.
* `repository/` – Përmban interface-t që komunikojnë me databazën përmes Spring Data JPA.
* `service/` – Përmban logjikën e biznesit që ndërmjetëson controller-at dhe repository-t.
* `security/` – Përfshin konfigurimin e JWT, filtrat e sigurisë dhe menaxhimin e përdoruesve.
* `resources/db/migration/` – Përmban skriptet SQL për Flyway për menaxhimin e versionimit të databazës.

---

## 🔐 Siguria dhe Autentikimi

Sistemi përdor **JWT (JSON Web Tokens)** për autentikim dhe autorizim:

* Përdoruesit regjistrohen dhe autentikohen përmes endpoint-eve përkatëse.
* Pas login-it, gjenerohet një JWT që përdoret për thirrjet ndaj API-ve të mbrojtura.
* Mbështeten role të ndryshme (Admin, Student, Profesor), me kontroll të aksesit sipas rolit.

---

## 🚀 Funksionalitete të mbuluara

Sistemi përfshin **21 controller-a** funksionalë që mundësojnë:

* Menaxhimin e studentëve, profesorëve dhe kurseve
* Menaxhimin e fakulteteve, departamenteve dhe programeve studimore
* Regjistrimin e kurseve nga studentët
* Krijimin dhe menaxhimin e orarit të ligjëratave
* Administrimin e provimeve dhe notave
* Menaxhimin e aplikimeve për bursa dhe verifikimin e tyre nga administrata
* Menaxhimin e roleve dhe përdoruesve të sistemit
* Validim të plotë të të dhënave në nivel të DTO-ve
* Trajtim të gabimeve dhe autorizim të sigurt në të gjitha endpoint-et

---

## 🧪 Testim & Dokumentim

Për testim dhe eksplorim të API-ve gjatë zhvillimit është integruar **Swagger UI**, i cili ofron:

* Dokumentim automatik të të gjitha endpoint-eve REST
* Mundësi për të testuar interaktivisht API-të direkt nga ndërfaqja grafike
* Lehtësi në komunikimin mes zhvilluesve të backend-it dhe frontend-it

---

## 🔧 Si ta nisni projektin lokalisht

1. **Kloni projektin** nga repozitori përkatës.
2. Hapeni në një IDE si **IntelliJ IDEA** ose **VS Code**.
3. Sigurohuni që MySQL është i instaluar dhe aktiv.
4. Konfiguroni kredencialet në `application.properties` ose `application.yml`.
5. Ekzekutoni klasën kryesore `BackEndApplication.java` si një Spring Boot Application.
6. Flyway do të aplikojë automatikisht migrimet SQL në databazë nga `resources/db/migration`.

---

## 🗃️ Migrimet e Databazës

Migrimi i databazës realizohet në mënyrë automatike përmes **Flyway**, duke siguruar që të gjitha versionet e strukturës së databazës janë të sinkronizuara me kodin. Çdo ndryshim ruhet si një skedar SQL i versionuar në `resources/db/migration`.

---

## ✅ Statusi i Projektit

Backend-i është plotësisht funksional dhe i testuar, i gatshëm për përdorim me frontend-in përmes API-ve REST. Mbështet skenarë të ndryshëm të përdorimit, përfshirë:

* Regjistrimin e përdoruesve
* Menaxhimin e entiteteve të sistemit (studentë, profesorë, kurse, nota, etj.)
* Autentikim të sigurt me JWT
* Akses të kontrolluar sipas rolit
* Testim dhe dokumentim të API-ve me Swagger

---

## 📩 Kontributet

Ky projekt është rezultat i punës së përbashkët të ekipit tonë dhe çdo përmirësim apo ide e re është gjithmonë e mirëpritur. 
### Anëtarët e Grupit:

* Adrian Mehaj
* Elton Pajaziti
* Leutrim Hajdini
* Isma Klinaku
* Zana Shabani

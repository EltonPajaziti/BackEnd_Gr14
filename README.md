# **Student Management System**

Kjo është pjesa backend e Sistemit për Menaxhimin e Studentëve (SMS), e ndërtuar me Spring Boot. Ajo përfaqëson shtyllën logjike të aplikacionit tonë, duke trajtuar gjithë logjikën e biznesit, ruajtjen e të dhënave dhe sigurinë.

## Teknologjitë që kemi përdorur:

-Java 17
-Spring Boot
-Spring Security 
-Spring Data JPA
-MySQL
-Flyway 
-Maven / Gradle
-Swagger 


Backend-i përfshin gjithë funksionalitetin që i nevojitet një sistemi të menaxhimit të studentëve. Kemi krijuar një sërë controller-ash REST, të ndarë sipas entiteteve kryesore të sistemit, si: studentët, profesorët, kurset, programet, fakultetet, aplikimet për bursa dhe shumë të tjera.

Kemi ndërtuar një sistem të plotë autentikimi dhe autorizimi duke përdorur JWT tokens. Pas login-it, përdoruesit marrin një token i cili ruhet në frontend dhe përdoret për të bërë thirrje të autorizuara ndaj API-ve.

Struktura e projektit është e organizuar sipas praktikave më të mira të Spring Boot:

    Në controller kemi të gjithë endpoint-et që frontend-i konsumon.

    Në dto kemi krijuar klasa të veçanta për të dërguar dhe pranuar të dhëna në mënyrë të sigurt dhe të kontrolluar.

    Në model kemi entitetet që përfaqësojnë strukturën e databazës sonë.

    Në repository kemi interface-t që komunikojnë me databazën nëpërmjet JPA.

    Në service është logjika kryesore e biznesit, që përpunon kërkesat mes controller-it dhe repository-t.

    Në security është implementuar JWT, si dhe klasat për përdoruesit e personalizuar të sistemit.

    Për migrime databaze kemi përdorur Flyway, duke ruajtur skedarët SQL në resources/db/migration.

## Funksionalitetet kryesore të implementuara

Kemi ndërtuar më shumë se 15 kontrolle të ndryshme që përfshijnë:

-Regjistrimin dhe menaxhimin e kurseve
-Menaxhimin e studentëve dhe profesorëve
-Orarin e ligjëratave
-Administrimin e provimeve dhe notave
-Menaxhimin e departamenteve dhe fakulteteve
-Aplikimet për bursa dhe konfirmimin e tyre nga admini
-Përdoruesit dhe rolet e tyre
-Çdo funksionalitet është testuar dhe trajton me kujdes rastet e gabimeve, si kërkesat e pavlefshme, autorizimet dhe validimet në nivel të DTO.

Për testimin e API-ve, kemi integruar Swagger UI, i cili është i aksesueshëm gjatë zhvillimit të aplikacionit. Kjo na ka ndihmuar jashtëzakonisht shumë për të testuar endpoint-et që kemi ndërtuar dhe për të dokumentuar saktë mënyrën e përdorimit të secilit prej tyre. Kjo ndërfaqe grafike e thjeshton shumë komunikimin mes zhvilluesve dhe përdoruesve të API-së, sidomos gjatë fazës së integrimit me frontend-in.

Për të nisur backend-in në mënyrë lokale, së pari duhet të klonohet projekti nga repozitori përkatës. Më pas, projekti mund të hapet në një IDE si IntelliJ IDEA ose VS Code. Është e rëndësishme të sigurohemi që databaza MySQL është aktive dhe funksionale. Pasi të jetë bërë kjo, duhet të konfigurohen kredencialet e databazës në skedarin application.properties ose application.yml, varësisht nga mënyra e konfigurimit që përdor projekti. Aplikacioni më pas mund të nisë duke ekzekutuar klasën BackEndApplication.java si një Spring Boot App.

Menaxhimi i versioneve të databazës bëhet përmes Flyway, i cili automatikisht ekzekuton skedarët SQL që ndodhen në dosjen resources/db/migration, duke krijuar të gjitha tabelat dhe ndryshimet e nevojshme në databazë pa ndërhyrje manuale.

## Backend-i është në një gjendje plotësisht funksionale dhe është testuar me sukses. Ai është lidhur me frontend-in përmes API-ve REST dhe mbështet të gjithë skenarët kryesorë të përdorimit që kemi parashikuar në projekt, duke përfshirë menaxhimin e përdoruesve, kurseve, regjistrimeve, notave, bursave dhe më shumë.


### Front-end package restore
- cd singsing\src\main\resources\static
- npm install

### Fordítás 
`mvn package`

### Futtatás
`java -jar target/singsing-0.0.1-SNAPSHOT.jar`

### Elérés
Az alkalmazásba belépni a következő [linken](http://localhost:8080/login) lehet.
- Felhasználónév: admin
- Jelszó: admin

### Adatbázis
Az adatbázis módosítását el lehet végezni átmenetileg a következő [konzolon](http://localhost:8080/h2-console). A belépéshez tartozó adatokat az `src/main/resources/application.properties` tartalmazza.
Az adatbázis permanens módosításához az `src/main/resources/data.sql` fájt kell megmódosítani.

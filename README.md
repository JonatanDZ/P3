# P3 – Quick Start & Swagger Docs



## Swagger / OpenAPI (Boot 3.x)
- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- Her ses dokumentation (altså beskrivelsen og general information) omkring endpoints i back-enden. 

## Install dependencies
```bash
npm install
```

## Prerequisites
- Flyway benyttes til migrering af databasen. Hvis man vil loade mock dataen, skal MYSQL være opsat på ens computer.
- Dernæst skal man oprette en .env fil med følgende indhold. Den skal ligge i samme sti som "README.md" og "package.json":
  DB_URL=jdbc:mysql://localhost:3306/P3?createDatabaseIfNotExist=true
  TEST_DB_URL=jdbc:mysql://localhost:3306/P3_test?createDatabaseIfNotExist=true
  DB_PORT=3306
  DB_USERNAME=root
  DB_PASSWORD=DINKODEHER!!!
- Det er MEGET VIGTIGT at ændre username og password til ens egne. 

## Run the app
```bash
mvn spring-boot:run
```
The server starts at **http://localhost:8080**.

## Use cases for interaktion med programmet:
- Disse use cases fremhæver centrale aspekter ved koden og programmets design:

- Hvad bliver man mødt af ved det initielle load?
- Siden inddeler automatisk links efter deres tildelte department, stage og jurisdiction. Ved at trykke på de forskellige knapper, ser man listerne af tools ændre sig på nær all tools; denne viser alle tools på tværs af filtre. 
- Man kan søge efter tools, hvor der bruges fuzzy search vha. Wagner-Fischer algoritmen.
- Tags fremgår som "piller" på et tool. Farverne er bestemt efter antal ord og bogstaver i ordet.
- Man kan favorite et tool og unfavorite ved at trykke på stjernen. OBS. man skal kigge på de tre farver og jurisdiction af det tool man favoriserer, da der er 3*2 favorit lister i alt. 
  - Eks: *favoriter "Happy Tiger Player Portal (Development)". Denne viser sig kun i favorites, givet at man har trykket på "Dev", flaget (så den viser det britiske flag). 
- Man kan teste pending funktionaliteten, da der som standard er et pending tool i den nuværende brugers department (som de ikke selv har oprettet). 

- Add tool
- Tryk på det grønne "+" ikon. Her mødes man af en valgmulighed; Is the tool personal?
- Hvis ja: Toolet bliver tilgængeligt for den nuværende bruger, og skal ikke godkendes. Toolet kan kun ses i dine favoritter.
- Hvis nej: Toolet bliver tilgængeligt for alle på siden. Den skal derfor først godkendes. Dette kan kun ske fra andre brugere (det forklares senere hvordan man gør dette).
- Dernæst skal man beslutte om toolet er dynamisk. Dette vil sige at et tool repræsenterer et link i ens udviklingsmiljø. Man udfylder den første og sidste del af linket, hvor de separeres med ens initialer. Eks: pedo.greathippydev.co.uk.
- Vælg department. Toolet vil derfor høre til de checkede departments. Toolet vil kun blive vist i all tools, medmindre at man krydser den pågældende department af i sidebaren til venstre. Det anbefales at krydse alle af, da hvis den skal godkendes skal den nuværende bruger tilhøre den afkrydsede department. 
- Dernæst vælges stages og jurisdictions der fungerer ligesom ovenstående koncept (departments). Ved gennemgang af siden anbefales det at krydse alt af ved stages og jurisdictions.
- Tilføj tags efter behov. Tools fremsøges vha. fuzzy search; altså man kan både søge efter tags, navn på url, navn på tool etc. Der tages også højde for stavefejl til en vis grad.
- Add tool. 

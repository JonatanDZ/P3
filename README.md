# P3 – Quick Start & Swagger Docs

---

## Swagger
- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- Her ses dokumentation (altså beskrivelsen og general information) omkring endpoints i back-enden.

---

## Install dependencies
```bash
npm install
```

---

## Prerequisites
- MySQL skal være opsat på ens computer, før programmet kører via localhost. 
- Flyway benyttes til migrering af databasen, dette sker automatisk. 
- Dernæst skal man oprette en .env fil med følgende indhold. Den skal ligge i samme sti som "README.md" og "package.json":


```env
DB_URL=jdbc:mysql://localhost:3306/P3?createDatabaseIfNotExist=true
TEST_DB_URL=jdbc:mysql://localhost:3306/P3_test?createDatabaseIfNotExist=true
DB_PORT=3306
DB_USERNAME=root
DB_PASSWORD=DINKODEHER!!!
```

- Det er MEGET VIGTIGT at ændre username og password til ens egne.

---

## Run the app
```bash
mvn spring-boot:run
```
The server starts at **http://localhost:8080**.

---

### 1) Første load på hjemmesiden.

- Ved første load bliver du mødt af en oversigt over tools.
- Siden inddeler automatisk tools efter deres tildelte:
    - **Department**
    - **Stage**
    - **Jurisdiction**
- Ved at trykke på department, stage eller jurisdiction knapperne ser du listerne ændre sig.
- **All tools** viser alle tools på tværs af tildelte departments, stages og jurisdictions.

### 2) Søgning (fuzzy search)

- Du kan søge efter tools i søgebaren med **fuzzy search** hvor vi benytter **Wagner–Fischer** algoritmen.
- Du kan søge på fx:
    - tool-navn
    - URL
    - tags
- Der tages højde for stavefejl til en vis grad.

### 3) Tags

- Tags vises som “piller” på et tool.
- Farverne bestemmes ud fra antal ord og bogstaver i ordet.

### 4) Favoritter

- Du kan favorite/unfavorite ved at trykke på stjernen.
- ⚠️**OBS:** Kig på **stage** og **jurisdiction** for det tool du favoriserer, da der er **3 × 2 favoritlister** i alt.

**Eks:**
- Favorisér *"Happy Tiger Player Portal (Development)"*.
- Det vises kun i *Favorites* når:
    - du har valgt **Dev**
    - og valgt **UK-flaget** (jurisdiction)

### 5) Pending funktionalitet

- Du kan teste pending-funktionaliteten, da der som standard ligger et **pending tool** i den nuværende brugers department (som brugeren ikke selv har oprettet).

---

## Add tool

1. Tryk på det grønne **“+”** ikon.
2. Du bliver spurgt: **Is the tool personal?**

### Hvis *ja* (personal)
- Toolet bliver tilgængeligt for den nuværende bruger og skal **ikke** godkendes.
- Toolet kan kun ses i dine **favoritter**.

### Hvis *nej* (company)
- Toolet bliver tilgængeligt for alle på siden, men skal først **godkendes**.
- Godkendelse kan kun ske fra **andre brugere** (se afsnittet *Pending tool*).

### Dynamisk tool (valgfrit)
- Et dynamisk tool repræsenterer et link i dit udviklingsmiljø.
- Du udfylder den første og sidste del af linket, separeret af dine initialer.
- Eksempel: `pedo.<INITIALER>.greathippydev.co.uk`

### Vælg Department / Stage / Jurisdiction
- Vælg **department** (toolet hører til de afkrydsede departments).
- Toolet vises kun i **All tools**, medmindre du også markerer den pågældende department i venstre sidebar.
- Det anbefales at krydse alle af (departments/stages/jurisdictions) ved gennemgang.

### Tags
- Tilføj tags efter behov.
- Tools kan fremsøges via fuzzy search på tags/navn/url osv.

### Opret
- Tryk **Add tool**.

---

## Pending tool (godkendelse)

For at se et tool der lige er oprettet i pending tool menuen (**Bell menu**), skal du “logge ind” som en anden bruger.

### Skift bruger
1. Åbn filen:

`P3/src/main/resources/static/js/getCurrentEmployee.js`

2. Find metoden, hvor du kan ændre argumentet til initialerne på den bruger du vil “logge ind” som.

> Pending tools er filtreret pr. department.
> Hvis du vil se (og dermed kunne godkende) et company tool du lige har uploadet, skal du være logget ind som en anden bruger fra **samme department** som toolet blev uploadet til.

### Brugere pr. department
- `JADE`: DevOps
- `BAFR`: Frontend
- `MAGA`: Games
- `JAPL`: Players
- `JAPR`: Promotions
- `MAHR`: HR
- `LALE`: Legal


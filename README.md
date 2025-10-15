# P3 – Quick Start & Swagger Docs

## Run the app
```bash
mvn spring-boot:run
```
The server starts at **http://localhost:8080**.

## Swagger / OpenAPI (Boot 3.x)
- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- Ovenstående tool tilgås Swagger. Her ses dokumentation (altså beskrivelsen og general information) omkring endpoints i back-end. 

## Minimal annotations (optional)
You don’t need annotations for basic docs. Add them only for clarity:
- `@Tag(name = "Links")` – group endpoints
- `@Operation(summary = "Retrieve all tools")` – short description

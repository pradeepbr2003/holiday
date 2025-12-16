# Accenture Bangalore Holidays API (2025)

This small Spring Boot service exposes Accenture Bangalore office holidays for the year 2025.

Important: the application in this project is configured to run on port 6767 (see
`src/main/resources/application.properties`).

Quick links

- Swagger UI (API documentation and examples): http://localhost:6767/swagger-ui/index.html

API endpoints

- GET /holiday
    - Returns all holidays for the year (both Mandatory and Floating by default).
    - Optional query parameters:
        - month (integer 1-12) — filter results to a specific month. Example: `?month=12` for December.
        - htype (string) — filter by holiday type. Accepted values: `Mandatory`, `Floating`.
    - Examples:
        - All holidays for the year: http://localhost:6767/holiday
        - Holidays for December: http://localhost:6767/holiday?month=12
        - Mandatory holidays for the year: http://localhost:6767/holiday?htype=Mandatory
        - Floating holidays for February: http://localhost:6767/holiday?htype=Floating&month=2

Example requests (PowerShell / Windows)

- Using PowerShell's Invoke-RestMethod (pretty prints JSON):

  Invoke-RestMethod -Uri "http://localhost:6767/holiday" -Method Get

- Using curl (PowerShell or any shell):

  curl "http://localhost:6767/holiday?month=12"

Build and run (Windows)

- Build with the included Maven wrapper (Windows):

  .\mvnw.cmd -DskipTests package

- Run with the wrapper (dev):

  .\mvnw.cmd spring-boot:run

- Or run the packaged jar:

  java -jar target\holiday-0.0.1-SNAPSHOT.jar

Notes

- The application loads a static holiday list from resources and writes/read logs to `bangalore_holidays.log` (see
  `src/main/resources`).
- If you change the server port, update the Swagger and example URLs accordingly.
- Error responses and validation are handled by controller advice; consult the Swagger UI for full request/response
  schemas.

Contact / Next steps

- For questions or improvements (additional years, dynamic data source, authentication), open an issue or edit the
  repository README.

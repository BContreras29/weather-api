# Weather Sensor API

A RESTful Spring Boot API for recording and retrieving temperature readings from weather sensors across different cities. Built as a practice assessment to learn Spring Boot architecture, JPA repositories, and REST API design patterns.

> This is my first Spring Boot API — intentionally kept simple and heavily commented to document the learning process.

## Tech Stack

- Java 25
- Spring Boot 4
- Spring Data JPA
- H2 In-Memory Database

## How to Run

No database setup needed — uses H2 in-memory database. Data resets on restart.

1. Clone the repo
2. Run `mvn spring-boot:run`
3. API available at `http://localhost:8080/weather`
4. H2 console (optional) at `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:bookdb`
   - No password needed

## Endpoints

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| POST | /weather | Record a new weather reading | 201 Created |
| GET | /weather | Get all readings | 200 OK |
| GET | /weather?cityName=X | Filter by city (case-insensitive) | 200 OK |
| GET | /weather?region=X | Filter by region (case-insensitive) | 200 OK |
| GET | /weather?cityName=X&region=Y | Filter by both | 200 OK |
| GET | /weather/{id} | Get reading by ID | 200 OK / 404 Not Found |
| PUT | /weather/{id} | Not allowed | 405 Method Not Allowed |
| PATCH | /weather/{id} | Not allowed | 405 Method Not Allowed |
| DELETE | /weather/{id} | Not allowed | 405 Method Not Allowed |

## Why 405 for PUT/PATCH/DELETE?

Weather sensor readings are historical data — once a temperature reading is recorded, it should not be modified or deleted. Mutating past sensor readings would compromise data integrity and make historical analysis unreliable. The 405 responses are intentional, not incomplete implementation.

## Request Body

```json
{
  "cityName": "London",
  "region": "Europe",
  "latitude": 51.5074,
  "longitude": -0.1278,
  "temperature": 15.5,
  "timestamp": 1672531200000
}
```

## Example Response

```json
{
  "id": 1,
  "cityName": "London",
  "region": "Europe",
  "latitude": 51.5074,
  "longitude": -0.1278,
  "temperature": 15.5,
  "timestamp": 1672531200000
}
```

## Project Structure

```
src/main/java/com/example/demo/
├── controller/
│   └── WeatherController.java    — REST endpoints, HTTP status codes
├── service/
│   └── WeatherService.java       — business logic, Optional handling
├── repository/
│   └── WeatherRepo.java          — JPA repository, custom query methods
├── entity/
│   └── Weather.java              — JPA entity mapped to H2 table
└── dto/
    └── WeatherDTO.java           — Java record, API request contract
```

## Key Concepts Practiced

**Four-layer architecture** — Controller → Service → Repository → Entity with clean separation of concerns between layers.

**DTO pattern (Java record)** — `WeatherDTO` as an immutable Java record separates the API contract from the JPA entity, preventing clients from directly setting internal fields like `id`.

**Constructor injection** — dependencies injected via constructor (`private final WeatherService service`) rather than `@Autowired` field injection, making dependencies explicit.

**Optional handling** — service returns `Optional<Weather>` so the controller cleanly returns 404 when a record is not found without throwing exceptions.

**Case-insensitive filtering** — `findByCityNameIgnoreCase` and `findByRegionIgnoreCase` are Spring Data JPA derived query methods — no custom SQL needed, JPA generates the query from the method name.

**Explicit 405 handling** — prohibited methods are explicitly mapped and return 405 rather than being left unmapped, which would return 404 and mislead the client about why the operation failed.

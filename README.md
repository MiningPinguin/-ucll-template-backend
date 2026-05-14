# Backend

## Setup

### `.env`

```yaml
CORS_ALLOWED_ORIGINS=http://localhost:8080
JWT_SECRET_KEY=<base-64-key>
PORT=3000
```

```cmd
mvn spring-boot:run
```

## Tests

### Unit

Fast isolated tests without Spring context.

#### Characteristics

- Mockito only
- No database
- No HTTP
- No `@SpringBootTest`

#### Targets

- Business logic
- Validation
- Utility methods
- Mappers
- Calculations
- Edge cases

---

### Integration

Tests verifying multiple components working together within the Spring application.

#### Characteristics

- Spring context loaded
- Database interaction
- Repository/service integration
- REST endpoint testing
- Often uses Testcontainers

#### Targets

- Controllers
- Repositories
- Service integration
- Persistence
- External API integration

---

### E2E

End-to-end tests validating complete application flows.

#### Characteristics

- Runs close to production setup
- Real HTTP requests
- Full application flow testing

#### Targets

- User workflows
- Authentication flows
- Critical business scenarios

---

## Libraries

- [Lombok](https://projectlombok.org/features/?utm_source=chatgpt.com)

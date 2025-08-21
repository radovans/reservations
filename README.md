# Viable One Application

A Spring Boot application for Viable One.

## Database Setup

### Start PostgreSQL Database

```bash
# Start the PostgreSQL database using Docker Compose
docker-compose up -d
```

### Stop Database

```bash
# Stop and remove volumes
docker-compose down -v
```

### Recreate Database

```bash
# Stop the database and remove volumes, then start it again
docker-compose down -v && docker-compose up -d
```

### Run Flyway Migrations

```bash
# Run Flyway migrations to set up the database schema
mvn clean flyway:migrate
```

### Reset Database

```bash
# Reset the database by dropping and recreating it
docker-compose down -v && docker-compose up -d && mvn clean flyway:migrate
```

## Testing

### Unit Tests

Run tests with `mvn clean test`

### Code Quality Checks

- Run checkstyle with `mvn checkstyle:checkstyle` - report will be generated in `/target/reports/checkstyle.html`
    ```bash
    open target/reports/checkstyle.html
    ```
- Run Spotbugs with `mvn spotbugs:spotbugs` - report will be generated in `/target/site/spotbugs.html`
    ```bash
    open target/site/spotbugs.html
    ```

### API Testing with api.http

The project includes an `api.http` file for testing the REST API endpoints.

## Future Improvements

If I had more time, I would add the following enhancements to make the application more robust and production-ready:

- Authentication & Authorization
- Add role-based access control (admin, user, etc.)
- Api versioning
- Caching for frequently accessed data
- Add Indexes to database tables for performance
- Logging and Monitoring

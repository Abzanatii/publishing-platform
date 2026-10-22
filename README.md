# Publishing Platform

A RESTful backend for a blogging and publishing platform, built with Spring Boot. Authors can write posts, save them as drafts, organize them with categories and tags, and publish them for anyone to read.

> **Note:** This is a learning project, built to practice Spring Boot, Spring Security with JWT, and JPA.
## Features

- JWT-based authentication (stateless)
- Create, read, update, and delete posts
- Draft and published post states
- Categories and tags, with filtering of posts by either
- Automatic reading-time estimate for each post
- Request validation and consistent JSON error responses

## Tech Stack

- Java 25
- Spring Boot 4.1.1 (Web MVC, Data JPA, Validation, Security)
- PostgreSQL
- JJWT for token generation and validation
- MapStruct and Lombok
- Maven

## Getting Started

### Prerequisites

- JDK 25
- PostgreSQL running locally (or reachable from your machine)

### 1. Clone the repository

```bash
git clone https://github.com/Abzanatii/publishing-platform.git
cd publishing-platform
```

### 2. Create the database

```bash
createdb -U postgres pub_db
```

Tables are created automatically on startup (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Configure the application

Defaults live in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pub_db
spring.datasource.username=postgres
spring.datasource.password=postgres
jwt.secret=your-256bit-secret-key-here-make-it-at-least-32-bytes-long
```

Override them with environment variables rather than editing committed files:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/pub_db
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=your-password
export JWT_SECRET=a-long-random-secret-of-at-least-32-bytes
```

### 4. Run

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080`.

> **Development user:** on startup the app creates a test account, `user@test.com` / `password`, if it doesn't already exist. Remove or change this before deploying anywhere public.

## API Reference

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `POST` | `/auth` | No | Log in and receive a JWT |
| `GET` | `/posts` | No | List published posts. Optional query params: `categoryId`, `tagId` |
| `GET` | `/posts/{id}` | No | Get a single post |
| `GET` | `/posts/drafts` | Yes | List the current user's drafts |
| `POST` | `/posts` | Yes | Create a post |
| `PUT` | `/posts/{id}` | Yes | Update a post |
| `DELETE` | `/posts/{id}` | Yes | Delete a post |
| `GET` | `/categories` | No | List categories with post counts |
| `POST` | `/categories` | Yes | Create a category |
| `DELETE` | `/categories/{id}` | Yes | Delete a category (must have no posts) |
| `GET` | `/tags` | No | List tags with post counts |
| `POST` | `/tags` | No | Create one or more tags |
| `DELETE` | `/tags/{id}` | No | Delete a tag (must have no posts) |

Authenticated requests need an `Authorization: Bearer <token>` header. Tokens expire after 24 hours.

### Example usage

**Log in**

```bash
curl -X POST http://localhost:8080/auth \
  -H "Content-Type: application/json" \
  -d '{"email": "user@test.com", "password": "password"}'
```

```json
{ "token": "eyJhbGciOi...", "expiresIn": 86400 }
```

**Create a category**

```bash
curl -X POST http://localhost:8080/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "Technology"}'
```

**Create a post**

```bash
curl -X POST http://localhost:8080/posts \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Hello, world",
    "content": "This is my first post on the platform.",
    "categoryId": "<category-uuid>",
    "tagIds": [],
    "status": "PUBLISHED"
  }'
```

`status` is either `DRAFT` or `PUBLISHED`.

## Project Structure

```
src/main/java/com/abz/pub/
├── config/        # Security configuration
├── controllers/   # REST controllers and global error handling
├── domain/        # Entities, DTOs, and request/status types
├── mappers/       # MapStruct mappers
├── repos/         # Spring Data JPA repositories
├── security/      # JWT filter and user details
└── services/      # Service interfaces and implementations
```

## Running Tests

```bash
./mvnw test
```

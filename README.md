# reLink Project

## Introduction
**reLink** is a URL shortening application designed to make sharing and managing links simpler and more efficient. The application provides RESTful APIs for creating short links from long URLs and retrieving the original URLs using their short hash.

The project is built using **Kotlin** and leverages the **Spring Boot Framework** for its backend architecture.

---

## Features
1. **Create Short Links**  
   Transform long URLs into short, hashed, and easily shareable links.
2. **Retrieve Original URL**  
   Retrieve the original destination URL when provided with the short hash.
3. **Fast and Scalable**  
   Built using Spring Boot with support for PostgreSQL, making it suitable for scaling requirements.
4. **Database Migration Support**  
   Database schema management is handled using Flyway.

---

## Docker Usage
For ease of deployment, **reLink** can be run using Docker. The project includes a `docker-compose` setup to run the PostgreSQL database required for the application.

### Prerequisites
1. Ensure the **Docker** and **Docker Compose** are installed on your system.

### Steps to Run
1. Navigate to the `docker` directory where the `docker-compose.yml` file is located:
```shell script
cd docker
```


2. Start the PostgreSQL container using Docker Compose:
```shell script
docker-compose up -d
```

This will spin up a PostgreSQL container, which the application will connect to.

3. Once PostgreSQL is running, you can start the **reLink** application:
```shell script
./mvnw spring-boot:run
```


4. The application will be available at `http://localhost:8080`.

### Stopping Containers
To stop the PostgreSQL container, execute:
```shell script
docker-compose down
```


---

## API Endpoints
### 1. Create a Short Link
**Endpoint**:  
`POST /relink/api/short-links`

**Request Body**:
```json
{
  "url": "https://example.com"
}
```


**Response**:
```json
{
  "hash": "<short-hash>",
  "url": "https://example.com"
}
```


- **Response Code**: 201 Created

### 2. Retrieve Original URL
**Endpoint**:  
`GET /relink/api/short-links/{hash}`

**Path Variable**:
- `hash`: The unique hash identifier of the shortened URL

**Response**:
```json
{
  "hash": "<short-hash>",
  "url": "https://example.com"
}
```


- **Response Code**: 200 OK

---

## Technologies and Frameworks Used
1. **Kotlin** as the primary programming language.
2. **Spring Boot** for RESTful API development.
3. **PostgreSQL** for database persistence.
4. **Flyway** for database schema migration.
5. **Docker** for simplified containerized deployment.
6. **Testcontainers** for containerized integration testing.
7. **Jackson Kotlin Module** for JSON serialization/deserialization.

---

## Project Setup

### Prerequisites
- **JDK 21**
- **Maven** (for dependency and build management)
- **PostgreSQL** database instance or Docker for containerized database.

### Steps to Run
1. Clone the project repository:
```shell script
git clone <repository-url>
```


2. Navigate to the project directory:
```shell script
cd reLink
```


3. Use Docker (as described above) for PostgreSQL, or configure a PostgreSQL database instance.

4. Update your PostgreSQL configuration (e.g., credentials, database name) in `application.properties`.

5. Run the project using Maven:
```shell script
./mvnw spring-boot:run
```


6. The application will start at `http://localhost:8080`.

---

## Contribution Guidelines
1. Fork the repository.
2. Create a feature branch:
```shell script
git checkout -b feature/your-feature-name
```


3. Commit your changes and push to your fork:
```shell script
git commit -m "Description of your changes"
   git push origin feature/your-feature-name
```


4. Create a pull request for review.

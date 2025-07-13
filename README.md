# 🎬 Film Review Microservices App

A distributed system built using Spring Boot, Docker, Redis, MariaDB, Prometheus, and Grafana.  
It allows users to manage films, add reviews, calculate average ratings, and observe system metrics.

---

## 🧩 Architecture Overview

This project uses a microservices architecture with the following components:

| Service         | Responsibility                             |
|-----------------|---------------------------------------------|
| `film-service`  | Manages film data and calculates ratings    |
| `review-service`| Handles user reviews for films              |
| `api-gateway`   | Entry point for all client requests         |
| `redis`         | In-memory cache used for rating results     |
| `mariadb`       | Relational database (used by both services) |
| `prometheus`    | Collects metrics from services              |
| `grafana`       | Visualizes metrics in dashboards            |

All services communicate over HTTP inside a shared Docker network.

---

## 🚀 Features

- CRUD operations for films and reviews
- Calculation of average rating per film
- Redis caching for performance optimization
- Cache invalidation when reviews are added/removed
- API Gateway as the single public-facing interface
- System monitoring with Prometheus & Grafana
- Dockerized deployment with `docker-compose`

---

## ⚙️ Technologies

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- Spring Cache + Redis
- MariaDB
- Prometheus + Micrometer
- Grafana
- Docker & Docker Compose

---

## 📦 How to Run the App

### 1. Clone the repo
```bash
git clone https://github.com/ivanak7899/film-review-app.git
cd film-review-app
```

### 2. Start the application
```bash
docker compose up --build
```
This will spin up:

- All services (films, reviews, gateway)
- Redis and MariaDB
- Prometheus at http://localhost:9090
- Grafana at http://localhost:3000 (default credentials: admin / admin)

You can run the tests from the project root with:
```bash
mvn test -f film-service/pom.xml
mvn test -f review-service/pom.xml
```

## 📡 REST API Endpoints

All public requests go through the API Gateway (`http://localhost:8080`).

### Film Service
| Method | Endpoint                  | Description                      |
|--------|---------------------------|----------------------------------|
| `GET`  | `/films`                  | List all films                   |
| `GET`  | `/films/{id}`             | Get a film by ID                 |
| `POST` | `/films`                  | Add a new film                   |
| `DELETE`| `/films/{id}`            | Delete a film                    |
| `GET`  | `/films/{id}/rating`      | Get average rating for a film    |

### Review Service
| Method | Endpoint                     | Description                      |
|--------|------------------------------|----------------------------------|
| `GET`  | `/reviews?filmId={id}`       | List reviews for a film          |
| `POST` | `/reviews`                   | Add a review                     |
| `DELETE`| `/reviews/{id}`             | Delete a review                  |

---

## 🧠 Caching & Invalidation

- Average rating requests are cached in Redis.
- When a review is added or deleted, `review-service` notifies `film-service` via internal REST call (`/internal/cache/film/{id}`) to evict the cache.

---

## 🧪 Example cURL Commands

```bash
# Add a film
curl -X POST http://localhost:8080/films \
-H "Content-Type: application/json" \
-d '{"title":"Pulp Fiction", "director":"Quentin Tarantino", "genre":"Crime", "year":1994}'

# Add a review
curl -X POST http://localhost:8080/reviews \
-H "Content-Type: application/json" \
-d '{"filmId":1, "reviewerName":"Alice", "text":"Amazing!", "rating":9}'


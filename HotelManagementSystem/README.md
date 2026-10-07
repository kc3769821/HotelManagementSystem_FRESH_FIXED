# Hotel Management System

Spring Boot 3.5.6 + Java 21 + MySQL 8 + Spring Data JPA/Hibernate + REST API + HTML/CSS/JavaScript.

## 1. Fastest setup (Docker MySQL)
1. Install/start Docker Desktop.
2. From this project folder run `run-mysql.bat`, or `docker compose up -d mysql`.
3. Open the project in IntelliJ IDEA.
4. Use JDK 21 and reload Maven.
5. Run `com.karan.hotel.HotelManagementApplication`.
6. Open http://localhost:8080

Docker creates:
- database: `hotel_management`
- user: `hotel_app`
- password: `hotel123`

## 2. Existing local MySQL
If MySQL is already installed, run `setup-mysql.sql` as an administrator/root user. Then run the Spring Boot application.

Default application credentials are:
- DB URL: jdbc:mysql://localhost:3306/hotel_management
- DB user: hotel_app
- DB password: hotel123

If your MySQL uses another port or credentials, set environment variables in IntelliJ Run Configuration:
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.

## 3. Main REST APIs
- GET `/api/rooms`
- GET `/api/rooms/available?checkIn=YYYY-MM-DD&checkOut=YYYY-MM-DD`
- GET `/api/bookings`
- POST `/api/bookings`
- DELETE `/api/bookings/{id}`
- GET `/api/dashboard`

## 4. Project flow
Browser -> REST API -> Controller -> Service -> Repository -> JPA/Hibernate -> MySQL.

Room images are bundled locally under `src/main/resources/static/images`, so the website does not depend on an image CDN.

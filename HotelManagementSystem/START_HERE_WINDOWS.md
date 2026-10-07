# START HERE — Windows / IntelliJ

## 1) MySQL setup (recommended)
Open MySQL Workbench and connect as your MySQL administrator/root user.
Run the complete `setup-mysql.sql` file.

It creates/resets:
- Database: `hotel_management`
- User: `hotel_app`
- Password: `hotel123`

## 2) Verify the DB user before IntelliJ
In MySQL Workbench run:
```sql
SELECT User, Host, plugin FROM mysql.user WHERE User='hotel_app';
```
Then:
```sql
USE hotel_management;
SHOW TABLES;
```
After the first successful Spring Boot startup, tables `rooms`, `bookings`, and `users` will be created automatically by JPA.

## 3) IntelliJ
Open the folder containing `pom.xml`.
Set Project SDK to Java 21.
Reload Maven.
Run:
`src/main/java/com/karan/hotel/HotelManagementApplication.java`

Open:
`http://localhost:8080`

## 4) If MySQL password is different
Do NOT guess. Either run `setup-mysql.sql` as root/admin, or set environment variables in the Run Configuration:
- `DB_USERNAME=your_mysql_user`
- `DB_PASSWORD=your_mysql_password`
- `DB_URL=jdbc:mysql://localhost:3306/hotel_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata`

## 5) REST smoke tests
- GET `/api/rooms`
- GET `/api/dashboard`
- POST `/api/auth/signup`
- POST `/api/auth/login`
- GET `/api/auth/me`
- POST `/api/bookings` (requires login session)
- GET `/api/bookings`
- DELETE `/api/bookings/{id}`
- POST `/api/auth/logout`

## 6) Authentication
Passwords are stored as BCrypt hashes. The raw password is never returned by the API.

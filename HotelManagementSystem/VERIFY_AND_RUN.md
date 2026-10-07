# Verify and Run Checklist

## Option A - Existing MySQL (recommended for IntelliJ)

1. Make sure MySQL 8.x is running.
2. Open MySQL Workbench.
3. Open `setup-mysql.sql`.
4. Run it using a MySQL administrator/root account.
   - It creates `hotel_management`.
   - It creates/resets `hotel_app`.
   - It explicitly resets the password to `hotel123`.
5. In IntelliJ, reload Maven.
6. Run `HotelManagementApplication` with JDK 21.
7. Open `http://localhost:8080`.

## Option B - Docker

Run:

```bash
docker compose up -d mysql
```

Then run the Spring Boot application from IntelliJ.

## Expected startup log

You should see:

```text
Tomcat initialized with port 8080
Started HotelManagementApplication
```

There should be no `Access denied for user 'hotel_app'` message.

## REST smoke tests

### Rooms

`GET http://localhost:8080/api/rooms`

Expected: JSON array containing seeded rooms such as Deluxe Room, Executive Room and Suite Room.

### Dashboard

`GET http://localhost:8080/api/dashboard`

Expected: JSON containing `totalRooms`, `availableRooms`, `confirmedBookings`, and `revenue`.

### Availability

`GET http://localhost:8080/api/rooms/available?checkIn=2026-11-10&checkOut=2026-11-12`

Expected: available room JSON array.

### Booking

`POST http://localhost:8080/api/bookings`

JSON:

```json
{
  "roomId": "1",
  "guestName": "Karan Chauhan",
  "email": "karan@example.com",
  "phone": "9999999999",
  "checkIn": "2026-11-10",
  "checkOut": "2026-11-12",
  "guests": "2"
}
```

Expected: booking JSON with a generated ID, room, dates, total amount and `CONFIRMED` status.

### Browser smoke test

1. Home page loads.
2. Room cards show local images.
3. View details opens the room image/details.
4. Booking form submits successfully.
5. Recent bookings updates.
6. Dashboard revenue/bookings updates.
7. Cancelling a booking changes its status.
8. Searching dates calls the availability REST endpoint.

# Authentication Flow

The application now includes user authentication using Spring session + BCrypt password hashing.

## Endpoints
- POST `/api/auth/signup` - create a user account
- POST `/api/auth/login` - authenticate and create an HTTP session
- GET `/api/auth/me` - get current logged-in user
- POST `/api/auth/logout` - invalidate session

## Booking security
- A booking requires a logged-in user.
- Bookings are stored against the logged-in user.
- `/api/bookings` returns only the current user's bookings.
- A user can cancel only their own booking.

## Demo
1. Open `http://localhost:8080`
2. Click **Sign Up**.
3. Create an account.
4. Click **Book now** on any room.
5. Submit booking.
6. Open **My Bookings** to see the booking.
7. Logout and login again to demonstrate session persistence.

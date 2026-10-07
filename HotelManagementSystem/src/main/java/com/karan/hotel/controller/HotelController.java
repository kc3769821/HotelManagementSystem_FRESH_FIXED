package com.karan.hotel.controller;

import com.karan.hotel.entity.Booking;
import com.karan.hotel.entity.Room;
import com.karan.hotel.entity.User;
import com.karan.hotel.repository.BookingRepository;
import com.karan.hotel.repository.RoomRepository;
import com.karan.hotel.repository.UserRepository;
import com.karan.hotel.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class HotelController {
    private final RoomRepository rooms;
    private final BookingRepository bookings;
    private final BookingService service;
    private final UserRepository users;

    public HotelController(RoomRepository rooms, BookingRepository bookings, BookingService service, UserRepository users) {
        this.rooms = rooms;
        this.bookings = bookings;
        this.service = service;
        this.users = users;
    }

    @GetMapping("/rooms")
    public List<Room> rooms() {
        return rooms.findAll();
    }

    @GetMapping("/rooms/available")
    public List<Room> available(@RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut) {
        return service.available(checkIn, checkOut);
    }

    @GetMapping("/bookings")
    public List<Booking> bookings(HttpSession session) {
        Object uid = session.getAttribute("USER_ID");
        if (uid == null) return List.of();
        return bookings.findByUserIdOrderByCreatedAtDesc((Long) uid);
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> create(@RequestBody Map<String, String> p, HttpSession session) {
        try {
            Object uid = session.getAttribute("USER_ID");
            if (uid == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Please login before booking."));
            }

            User user = users.findById((Long) uid)
                    .orElseThrow(() -> new IllegalStateException("Logged-in user not found."));

            Booking booking = service.create(
                    user,
                    Long.valueOf(p.get("roomId")),
                    p.get("guestName"),
                    p.get("email"),
                    p.get("phone"),
                    LocalDate.parse(p.get("checkIn")),
                    LocalDate.parse(p.get("checkOut")),
                    Integer.valueOf(p.get("guests"))
            );

            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage() == null ? "Unable to create booking." : e.getMessage()
            ));
        }
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id, HttpSession session) {
        Object uid = session.getAttribute("USER_ID");
        if (uid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Please login."));
        }

        return bookings.findByIdAndUserId(id, (Long) uid)
                .map(booking -> ResponseEntity.ok().body((Object) service.cancel(booking.getId())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Booking not found.")));
    }

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        return service.dashboard();
    }
}

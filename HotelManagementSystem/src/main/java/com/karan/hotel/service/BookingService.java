package com.karan.hotel.service;

import com.karan.hotel.entity.Booking;
import com.karan.hotel.entity.Room;
import com.karan.hotel.entity.User;
import com.karan.hotel.repository.BookingRepository;
import com.karan.hotel.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {
    private final BookingRepository bookings;
    private final RoomRepository rooms;

    public BookingService(BookingRepository bookings, RoomRepository rooms) {
        this.bookings = bookings;
        this.rooms = rooms;
    }

    public List<Room> available(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in.");
        }
        return rooms.findAll().stream()
                .filter(r -> r.getStatus() == Room.Status.AVAILABLE)
                .filter(r -> bookings.findOverlaps(r.getId(), checkIn, checkOut).isEmpty())
                .toList();
    }

    public Booking create(User user, Long roomId, String name, String email, String phone,
                          LocalDate checkIn, LocalDate checkOut, Integer guests) {
        if (user == null) throw new IllegalArgumentException("User is required.");
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in.");
        }

        Room room = rooms.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found."));

        if (room.getStatus() != Room.Status.AVAILABLE) {
            throw new IllegalStateException("Room is not available.");
        }
        if (guests == null || guests < 1 || guests > room.getCapacity()) {
            throw new IllegalArgumentException("Guest count exceeds room capacity.");
        }
        if (!bookings.findOverlaps(roomId, checkIn, checkOut).isEmpty()) {
            throw new IllegalStateException("Room is already booked for selected dates.");
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        BigDecimal total = room.getPrice().multiply(BigDecimal.valueOf(nights));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setGuestName(name);
        booking.setEmail(email);
        booking.setPhone(phone);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setGuests(guests);
        booking.setTotalAmount(total);
        booking.setStatus(Booking.Status.CONFIRMED);

        return bookings.save(booking);
    }

    public Booking cancel(Long id) {
        Booking booking = bookings.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found."));
        booking.setStatus(Booking.Status.CANCELLED);
        return bookings.save(booking);
    }

    public Map<String, Object> dashboard() {
        long total = rooms.count();
        long available = rooms.findAll().stream()
                .filter(r -> r.getStatus() == Room.Status.AVAILABLE)
                .count();
        long confirmed = bookings.countByStatus(Booking.Status.CONFIRMED);
        BigDecimal revenue = bookings.findAll().stream()
                .filter(b -> b.getStatus() == Booking.Status.CONFIRMED)
                .map(Booking::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of(
                "totalRooms", total,
                "availableRooms", available,
                "confirmedBookings", confirmed,
                "revenue", revenue
        );
    }
}

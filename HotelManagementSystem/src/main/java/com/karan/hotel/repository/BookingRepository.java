package com.karan.hotel.repository;
import com.karan.hotel.entity.Booking;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.*;
public interface BookingRepository extends JpaRepository<Booking,Long>{
 @Query("select b from Booking b where b.room.id=:roomId and b.status='CONFIRMED' and b.checkIn < :checkOut and b.checkOut > :checkIn") List<Booking> findOverlaps(@Param("roomId") Long roomId,@Param("checkIn") LocalDate checkIn,@Param("checkOut") LocalDate checkOut);
 List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);
 Optional<Booking> findByIdAndUserId(Long id,Long userId);
 long countByStatus(Booking.Status status);
}

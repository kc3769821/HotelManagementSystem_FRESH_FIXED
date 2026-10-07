package com.karan.hotel.repository;
import com.karan.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoomRepository extends JpaRepository<Room,Long> {}

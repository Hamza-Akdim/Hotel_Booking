package org.example.hotelbook.repositories;

import org.example.hotelbook.entities.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {
    @Query("SELECT DISTINCT b FROM BookedRoom b WHERE b.room.id = :x")
    List<BookedRoom> findBookingsByRoomId(@Param("x") Long id);
}

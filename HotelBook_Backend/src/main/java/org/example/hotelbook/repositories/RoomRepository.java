package org.example.hotelbook.repositories;

import org.example.hotelbook.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}

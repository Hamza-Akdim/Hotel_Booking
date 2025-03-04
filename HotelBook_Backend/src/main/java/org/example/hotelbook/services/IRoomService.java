package org.example.hotelbook.services;

import org.example.hotelbook.entities.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface IRoomService {
    Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws IOException, SQLException;
    List<Room> getAllRooms();
    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;

}

package org.example.hotelbook.services;

import org.example.hotelbook.entities.Room;
import org.example.hotelbook.exceptions.ResourceNotFoundException;
import org.example.hotelbook.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author akdim
 */

@Service
public class RoomService implements IRoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws IOException, SQLException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!file.isEmpty()){
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }

        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
        Optional<Room> theRoom = roomRepository.findById(roomId);  //Optional serves that if no Room exists, the Optional will be empty (instead of returning null).
        if(theRoom.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Room not found!");
        }
        Blob photoBlob = theRoom.get().getPhoto();  // get(): is used to retrieve the actual Room object wrapped inside the Optional
        if(photoBlob != null){
            return photoBlob.getBytes(1, (int) photoBlob.length());  //getBytes(position, length) :  Retrieves binary data from the Blob as a byte array, starting at a specific position until we finish the specific length.

        }
        return null;
    }

}

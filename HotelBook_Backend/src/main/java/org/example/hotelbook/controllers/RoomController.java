package org.example.hotelbook.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.example.hotelbook.entities.BookedRoom;
import org.example.hotelbook.entities.Room;
import org.example.hotelbook.exceptions.PhotoRetrievalException;
import org.example.hotelbook.repositories.RoomRepository;
import org.example.hotelbook.responses.BookingResponse;
import org.example.hotelbook.responses.RoomResponse;
import org.example.hotelbook.services.BookingService;
import org.example.hotelbook.services.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author akdim
 */

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {
    @Autowired
    private IRoomService roomService;
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    BookingService bookingService;

    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {

        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/room-types")
    public List<String> getRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @GetMapping("/all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : rooms) {
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoBytes!= null && photoBytes.length>0) {
                String base64Photo = Base64.encodeBase64String(photoBytes);  // Base64 encoding transforms binary data into a 64-character text format (A-Z, a-z, 0-9, +, /, and = for padding). This ensures safe transmission of binary data over text-based protocols like HTTP or JSON.
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
            return ResponseEntity.ok(roomResponses);
        }

        return null;
    }


    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = bookingService.getAllBookingByRoomId(room.getId());

        List<BookingResponse> bookingInfo = bookings
                        .stream()
                        .map(booking -> new BookingResponse(
                                booking.getBookingId(),
                                booking.getCheckInDate(),
                                booking.getCheckOutDate(),
                                booking.getBookingConfirmationCode())
                        ).toList();

        //This photo bytes will be set again, just for the issue of the constructor which require to be set
        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalException("Error retrieving photo");
            }
        }
        return new RoomResponse(room.getId(),
                room.getRoomType(), room.getRoomPrice(),
                room.isBooked(), photoBytes, bookingInfo);
    }

}

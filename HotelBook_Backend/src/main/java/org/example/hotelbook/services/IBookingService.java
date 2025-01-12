package org.example.hotelbook.services;

import org.example.hotelbook.entities.BookedRoom;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookingByRoomId(Long roomId);
}

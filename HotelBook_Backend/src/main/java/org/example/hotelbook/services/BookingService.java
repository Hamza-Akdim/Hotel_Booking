package org.example.hotelbook.services;

import org.example.hotelbook.entities.BookedRoom;
import org.example.hotelbook.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author akdim
 */
@Service
public class BookingService implements IBookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<BookedRoom> getAllBookingByRoomId(Long roomId) {
        return bookingRepository.findBookingsByRoomId(roomId);
    }
}

package az.turing.service;

import az.turing.model.BookingDTO;

import java.util.List;

public interface BookingService {
    BookingDTO saveBooking(BookingDTO bookingDTO);
    void cancelBooking(long bookingId);
    BookingDTO getBookingById(long id);
    List<BookingDTO> getBookingsByPassengerName(String name, String surname);
}
package az.turing.service;

import az.turing.model.BookingDTO;
import az.turing.entity.Booking;
import az.turing.entity.Flight;
import az.turing.entity.Passenger;
import az.turing.mapper.BookingMapper;
import az.turing.repository.BookingRepository;
import az.turing.repository.FlightRepository;
import az.turing.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingDTO saveBooking(BookingDTO bookingDTO) {
        Booking booking;

        if (bookingDTO.getId() != null && bookingRepository.existsById(bookingDTO.getId())) {
            booking = bookingRepository.findById(bookingDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingDTO.getId()));
        } else {
            booking = new Booking();
        }

        Flight flight = flightRepository.findById(bookingDTO.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + bookingDTO.getFlightId()));
        booking.setFlight(flight);

        List<Passenger> passengers = bookingDTO.getPassengers().stream()
                .map(p -> {
                    List<Passenger> existing = passengerRepository.findByNameAndSurname(p.getName(), p.getSurname());
                    if (!existing.isEmpty()) {
                        return existing.get(0);
                    } else {
                        Passenger newPassenger = new Passenger();
                        newPassenger.setName(p.getName());
                        newPassenger.setSurname(p.getSurname());
                        return passengerRepository.save(newPassenger);
                    }
                })
                .collect(Collectors.toList());

        booking.setPassengers(passengers);

        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toDTO(saved);
    }

    @Override
    public void cancelBooking(long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public BookingDTO getBookingById(long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toDTO)
                .orElse(null);
    }

    @Override
    public List<BookingDTO> getBookingsByPassengerName(String name, String surname) {
        return bookingRepository.findByPassengerNameAndSurname(name, surname).stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }
}
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingDTO saveBooking(BookingDTO bookingDTO) {
        log.info("Starting to save booking: {}", bookingDTO);
        Booking booking;

        if (bookingDTO.getId() != null && bookingRepository.existsById(bookingDTO.getId())) {
            booking = bookingRepository.findById(bookingDTO.getId())
                    .orElseThrow(() -> {
                        log.error("Booking not found with ID: {}", bookingDTO.getId());
                        return new RuntimeException("Booking not found with ID: " + bookingDTO.getId());
                    });
            log.info("Updating existing booking with ID: {}", bookingDTO.getId());
        } else {
            booking = new Booking();
            log.info("Creating new booking.");
        }

        Flight flight = flightRepository.findById(bookingDTO.getFlightId())
                .orElseThrow(() -> {
                    log.error("Flight not found with ID: {}", bookingDTO.getFlightId());
                    return new RuntimeException("Flight not found with ID: " + bookingDTO.getFlightId());
                });
        booking.setFlight(flight);
        log.info("Flight with ID {} attached to booking.", bookingDTO.getFlightId());

        List<Passenger> passengers = bookingDTO.getPassengers().stream()
                .map(p -> {
                    if (p.getId() != null) {
                        return passengerRepository.findById(p.getId())
                                .orElseThrow(() -> {
                                    log.error("Passenger not found with ID: {}", p.getId());
                                    return new RuntimeException("Passenger not found with ID: " + p.getId());
                                });
                    } else {
                        List<Passenger> existing = passengerRepository.findByNameAndSurname(p.getName(), p.getSurname());
                        if (!existing.isEmpty()) {
                            log.info("Using existing passenger: {} {}", p.getName(), p.getSurname());
                            return existing.get(0);
                        } else {
                            Passenger newPassenger = new Passenger();
                            newPassenger.setName(p.getName());
                            newPassenger.setSurname(p.getSurname());
                            Passenger saved = passengerRepository.save(newPassenger);
                            log.info("Created new passenger with ID: {}", saved.getId());
                            return saved;
                        }
                    }
                })
                .collect(Collectors.toList());

        booking.setPassengers(passengers);

        Booking saved = bookingRepository.save(booking);
        log.info("Booking saved successfully with ID: {}", saved.getId());

        return bookingMapper.toDTO(saved);
    }

    @Override
    public void cancelBooking(long bookingId) {
        log.info("Cancelling booking with ID: {}", bookingId);
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public BookingDTO getBookingById(long id) {
        log.info("Retrieving booking with ID: {}", id);
        return bookingRepository.findById(id)
                .map(bookingMapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Booking not found with ID: {}", id);
                    return new RuntimeException("Flight not found with id: " + id);
                });
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        log.info("Retrieving all bookings.");
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        log.info("Updating booking with ID: {}", id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Booking not found with ID: {}", id);
                    return new RuntimeException("Booking not found with id: " + id);
                });

        if (bookingDTO.getFlightId() != null) {
            Flight flight = flightRepository.findById(bookingDTO.getFlightId())
                    .orElseThrow(() -> {
                        log.error("Flight not found with ID: {}", bookingDTO.getFlightId());
                        return new RuntimeException("Flight not found with id: " + bookingDTO.getFlightId());
                    });
            booking.setFlight(flight);
            log.info("Updated flight for booking.");
        }

        if (bookingDTO.getPassengers() != null && !bookingDTO.getPassengers().isEmpty()) {
            List<Passenger> passengers = bookingDTO.getPassengers().stream()
                    .map(p -> {
                        if (p.getId() != null) {
                            return passengerRepository.findById(p.getId())
                                    .orElseThrow(() -> {
                                        log.error("Passenger not found with ID: {}", p.getId());
                                        return new RuntimeException("Passenger not found with ID: " + p.getId());
                                    });
                        } else {
                            List<Passenger> existing = passengerRepository.findByNameAndSurname(p.getName(), p.getSurname());
                            if (!existing.isEmpty()) {
                                return existing.get(0);
                            } else {
                                Passenger newPassenger = new Passenger();
                                newPassenger.setName(p.getName());
                                newPassenger.setSurname(p.getSurname());
                                return passengerRepository.save(newPassenger);
                            }
                        }
                    })
                    .collect(Collectors.toList());

            booking.setPassengers(passengers);
            log.info("Updated passengers for booking.");
        }

        Booking updated = bookingRepository.save(booking);
        log.info("Booking updated successfully: {}", updated.getId());
        return bookingMapper.toDTO(updated);
    }

    @Override
    public List<BookingDTO> getBookingsByPassengerName(String name, String surname) {
        log.info("Retrieving bookings for passenger: {} {}", name, surname);
        return bookingRepository.findByPassengerNameAndSurname(name, surname).stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }
}
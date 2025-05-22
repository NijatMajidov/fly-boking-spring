package az.turing.service;

import az.turing.entity.Passenger;
import az.turing.mapper.PassengerMapper;
import az.turing.model.PassengerDTO;
import az.turing.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerDTO createPassenger(PassengerDTO passengerDTO) {
        log.info("Creating passenger: {}", passengerDTO);
        Passenger passenger = passengerMapper.toEntity(passengerDTO);
        Passenger savedPassenger = passengerRepository.save(passenger);
        log.info("Passenger created with ID: {}", savedPassenger.getId());
        return passengerMapper.toDTO(savedPassenger);
    }

    @Override
    public PassengerDTO getPassengerById(long id) {
        log.info("Retrieving passenger with ID: {}", id);
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Passenger not found with ID: {}", id);
                    return new RuntimeException("Passenger not found with the id: " + id);
                });
        return passengerMapper.toDTO(passenger);
    }

    @Override
    public List<PassengerDTO> getPassengersByBookingId(long bookingId) {
        log.info("Retrieving passengers for booking ID: {}", bookingId);
        List<Passenger> passengers = passengerRepository.findAllByBooking_Id(bookingId);
        return passengers.stream()
                .map(passengerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePassenger(long id) {
        log.info("Deleting passenger with ID: {}", id);
        if (!passengerRepository.existsById(id)) {
            log.error("Passenger not found with ID: {}", id);
            throw new RuntimeException("Passenger not found with the id: " + id);
        }
        passengerRepository.deleteById(id);
        log.info("Passenger deleted with ID: {}", id);
    }

    @Override
    public PassengerDTO updatePassenger(long id, PassengerDTO passengerDTO) {
        log.info("Updating passenger with ID: {}", id);
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Passenger not found with ID: {}", id);
                    return new RuntimeException("Passenger not found with the id: " + id);
                });
        if (passengerDTO.getName() != null) {
            passenger.setName(passengerDTO.getName());
        }
        if (passengerDTO.getSurname() != null) {
            passenger.setSurname(passengerDTO.getSurname());
        }
        Passenger updatedPassenger = passengerRepository.save(passenger);
        log.info("Passenger updated successfully: {}", updatedPassenger.getId());
        return passengerMapper.toDTO(updatedPassenger);
    }

    @Override
    public PassengerDTO getPassengersByFlightId(long flightId) {
        log.info("Retrieving passengers for flight ID: {}", flightId);
        List<Passenger> passengers = passengerRepository.findAllByBookings_Flight_Id(flightId);
        return (PassengerDTO) passengers.stream()
                .map(passengerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PassengerDTO> getPassengersByNameAndSurname(String name, String surname) {
        log.info("Retrieving passengers by name: {} {}", name, surname);
        List<Passenger> passengers = passengerRepository.findByNameAndSurname(name, surname);
        return passengers.stream()
                .map(passengerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
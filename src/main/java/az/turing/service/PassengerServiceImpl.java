package az.turing.service;

import az.turing.entity.Passenger;
import az.turing.mapper.PassengerMapper;
import az.turing.model.PassengerDTO;
import az.turing.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerDTO createPassenger(PassengerDTO passengerDTO) {
        Passenger passenger = passengerMapper.toEntity(passengerDTO);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toDTO(savedPassenger);
    }

    @Override
    public PassengerDTO getPassengerById(long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found with the id: " + id));
        return passengerMapper.toDTO(passenger);
    }

    @Override
    public List<PassengerDTO> getPassengersByBookingId(long bookingId) {
        List<Passenger> passengers = passengerRepository.findAllByBooking_Id(bookingId);
        return passengers.stream()
                .map(passengerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePassenger(long id) {
        if (!passengerRepository.existsById(id)) {
            throw new RuntimeException("Passenger not found with the id: " + id);
        }
        passengerRepository.deleteById(id);
    }

    @Override
    public PassengerDTO updatePassenger(long id, PassengerDTO passengerDTO) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found with the id: " + id));
        passenger.setName(passengerDTO.getName());
        passenger.setSurname(passengerDTO.getSurname());
        Passenger updatedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toDTO(updatedPassenger);
    }

    @Override
    public PassengerDTO getPassengersByFlightId(long flightId) {
        List<Passenger> passengers = passengerRepository.findAllByBookings_Flight_Id(flightId);
        return (PassengerDTO) passengers.stream()
                .map(passengerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PassengerDTO> getPassengersByNameAndSurname(String name, String surname) {
        List<Passenger> passengers = passengerRepository.findByNameAndSurname(name, surname);
        return passengers.stream()
                .map(passengerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
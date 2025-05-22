package az.turing.service;

import az.turing.model.PassengerDTO;

import java.util.List;


public interface PassengerService {

    PassengerDTO createPassenger(PassengerDTO passengerDTO);

    PassengerDTO getPassengerById(long id);

    List<PassengerDTO> getPassengersByBookingId(long bookingId);

    void deletePassenger(long id);

    PassengerDTO updatePassenger(long id, PassengerDTO passengerDTO);


    PassengerDTO getPassengersByFlightId(long flightId);

    List<PassengerDTO> getPassengersByNameAndSurname(String name, String surname);
}
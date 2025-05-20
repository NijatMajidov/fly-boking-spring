package az.turing.service;

import az.turing.model.FlightDTO;

import java.util.List;

public interface FlightService {
    FlightDTO createFlight(FlightDTO flightDTO);
    FlightDTO getFlightById(Long id);
    List<FlightDTO> getAllFlights();
    FlightDTO updateFlight(Long id, FlightDTO flightDTO);
    void deleteFlight(Long id);
}

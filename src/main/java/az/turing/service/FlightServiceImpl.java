package az.turing.service;

import az.turing.model.FlightDTO;
import az.turing.entity.Flight;
import az.turing.mapper.FlightMapper;
import az.turing.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService{
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightDTO createFlight(FlightDTO flightDTO) {
        Flight flight = flightMapper.toEntity(flightDTO);
        Flight savedFlight = flightRepository.save(flight);
        return flightMapper.toDTO(savedFlight);
    }

    @Override
    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        return flightMapper.toDTO(flight);
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FlightDTO updateFlight(Long id, FlightDTO flightDTO) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));

        if (flightDTO.getStartingPoint() != null) {
            flight.setStartingPoint(flightDTO.getStartingPoint());
        }

        if (flightDTO.getEndingPoint() != null) {
            flight.setEndingPoint(flightDTO.getEndingPoint());
        }

        if (flightDTO.getDateTime() != null) {
            flight.setDateTime(flightDTO.getDateTime());
        }

        if (flightDTO.getTotalSeats() != null) {
            flight.setTotalSeats(flightDTO.getTotalSeats());
        }

        Flight updatedFlight = flightRepository.save(flight);
        return flightMapper.toDTO(updatedFlight);
    }

    @Override
    public void deleteFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        flightRepository.delete(flight);
    }
}
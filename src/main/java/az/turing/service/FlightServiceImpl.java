package az.turing.service;

import az.turing.entity.Cities;
import az.turing.model.FlightDTO;
import az.turing.entity.Flight;
import az.turing.mapper.FlightMapper;
import az.turing.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightDTO createFlight(FlightDTO flightDTO) {
        log.info("Creating flight: {}", flightDTO);

        Cities startingPoint = Cities.fromString(flightDTO.getStartingPoint());
        Cities endingPoint = Cities.fromString(flightDTO.getEndingPoint());

        if (startingPoint == null) {
            throw new RuntimeException("Starting point city not found: " + flightDTO.getStartingPoint());
        }
        if (endingPoint == null) {
            throw new RuntimeException("Ending point city not found: " + flightDTO.getEndingPoint());
        }

        Flight flight = Flight.builder()
                .startingPoint(startingPoint)
                .endingPoint(endingPoint)
                .dateTime(flightDTO.getDateTime())
                .totalSeats(flightDTO.getTotalSeats())
                .build();

        Flight savedFlight = flightRepository.save(flight);
        log.info("Flight created successfully with ID: {}", savedFlight.getId());
        return flightMapper.toDTO(savedFlight);
    }

    @Override
    public FlightDTO getFlightById(Long id) {
        log.info("Retrieving flight with ID: {}", id);
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Flight not found with ID: {}", id);
                    return new RuntimeException("Flight not found with id: " + id);
                });
        return flightMapper.toDTO(flight);
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        log.info("Retrieving all flights.");
        return flightRepository.findAll().stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FlightDTO updateFlight(Long id, FlightDTO flightDTO) {
        log.info("Updating flight with ID: {}", id);
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Flight not found with ID: {}", id);
                    return new RuntimeException("Flight not found with id: " + id);
                });

        if (flightDTO.getStartingPoint() != null) {
            Cities startingPoint = Cities.fromString(flightDTO.getStartingPoint());
            if (startingPoint == null) {
                log.error("Invalid starting point city: {}", flightDTO.getStartingPoint());
                throw new RuntimeException("Invalid starting point city: " + flightDTO.getStartingPoint());
            }
            flight.setStartingPoint(startingPoint);
        }

        if (flightDTO.getEndingPoint() != null) {
            Cities endingPoint = Cities.fromString(flightDTO.getEndingPoint());
            if (endingPoint == null) {
                log.error("Invalid ending point city: {}", flightDTO.getEndingPoint());
                throw new RuntimeException("Invalid ending point city: " + flightDTO.getEndingPoint());
            }
            flight.setEndingPoint(endingPoint);
        }

        if (flightDTO.getDateTime() != null) {
            flight.setDateTime(flightDTO.getDateTime());
        }

        if (flightDTO.getTotalSeats() != null) {
            flight.setTotalSeats(flightDTO.getTotalSeats());
        }

        Flight updatedFlight = flightRepository.save(flight);
        log.info("Flight updated successfully: {}", updatedFlight.getId());
        return flightMapper.toDTO(updatedFlight);
    }

    @Override
    public void deleteFlight(Long id) {
        log.info("Deleting flight with ID: {}", id);
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Flight not found with ID: {}", id);
                    return new RuntimeException("Flight not found with id: " + id);
                });
        flightRepository.delete(flight);
        log.info("Flight deleted with ID: {}", id);
    }
}
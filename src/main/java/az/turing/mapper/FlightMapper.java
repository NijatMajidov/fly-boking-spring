package az.turing.mapper;

import az.turing.entity.Flight;
import az.turing.dto.FlightDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    FlightDTO toDTO(Flight flight);
    Flight toEntity(FlightDTO flightDTO);
}
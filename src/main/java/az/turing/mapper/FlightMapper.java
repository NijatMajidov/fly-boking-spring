package az.turing.mapper;

import az.turing.entity.Flight;
import az.turing.model.FlightDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper extends EntityMapper<Flight, FlightDTO> {}
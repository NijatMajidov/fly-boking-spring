package az.turing.mapper;

import az.turing.entity.Passenger;
import az.turing.model.PassengerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassengerMapper extends EntityMapper<Passenger, PassengerDTO> {
}
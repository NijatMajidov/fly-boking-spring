package az.turing.mapper;

import az.turing.entity.Booking;
import az.turing.model.BookingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper extends EntityMapper<Booking, BookingDTO> {
}
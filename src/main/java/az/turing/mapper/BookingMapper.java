package az.turing.mapper;


import az.turing.entity.Booking;
import az.turing.repository.BookingDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDTO toDTO(Booking booking);
    Booking toEntity(BookingDTO bookingDTO);
}
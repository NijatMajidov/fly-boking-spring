package az.turing.repository;

import az.turing.entity.Cities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private long id;
    private Cities startingPoint;
    private Cities endingPoint;
    private LocalDateTime dateTime;
    private int totalSeats;
}
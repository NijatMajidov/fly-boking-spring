package az.turing.model;

import az.turing.entity.Cities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private Long id;
    private String startingPoint;
    private String endingPoint;
    private LocalDateTime dateTime;
    private Integer totalSeats;
}
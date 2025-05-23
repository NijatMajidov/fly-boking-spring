package az.turing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@ToString
@Entity
@Builder
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "City is required")
    @Enumerated(EnumType.STRING)
    private Cities startingPoint;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "City is required")
    private Cities endingPoint;

    private LocalDateTime dateTime;

    private Integer totalSeats;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();
}
package az.turing.repository;

import az.turing.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b JOIN b.passengers p WHERE p.name = :name AND p.surname = :surname")
    List<Booking> findByPassengerNameAndSurname(@Param("name") String name, @Param("surname") String surname);
}

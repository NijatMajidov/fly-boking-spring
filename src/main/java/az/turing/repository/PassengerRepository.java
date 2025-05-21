package az.turing.repository;

import az.turing.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByNameAndSurname(String name, String surname);

    @Query("SELECT p FROM Passenger p JOIN p.bookings b WHERE b.id = :bookingId")
    List<Passenger> findAllByBooking_Id(@Param("bookingId") Long bookingId);


    List<Passenger> findAllByBookings_Flight_Id(long flightId);
}

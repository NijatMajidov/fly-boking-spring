package az.turing.controller;


import az.turing.model.PassengerDTO;
import az.turing.service.PassengerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;
    @PostMapping
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passengerDTO) {
        PassengerDTO createdPassenger=passengerService.createPassenger(passengerDTO);
        return ResponseEntity.ok(createdPassenger);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable Long id) {
        PassengerDTO passenger = passengerService.getPassengerById(id);
        return ResponseEntity.ok(passenger);
    }

    @GetMapping("/booking/{bookingId}/passengers")
    public ResponseEntity<List<PassengerDTO>> getPassengersByBookingId(@PathVariable Long bookingId) {
        List<PassengerDTO> passengers=passengerService.getPassengersByBookingId(bookingId);
        return ResponseEntity.ok(passengers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(@PathVariable Long id, @RequestBody PassengerDTO passengerDTO) {
        PassengerDTO updatedPassenger=passengerService.updatePassenger(id, passengerDTO);
        return ResponseEntity.ok(updatedPassenger);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/name/{name}/surname/{surname}")
    public ResponseEntity<List<PassengerDTO>> getPassengersByNameAndSurname(
            @PathVariable String name,
            @PathVariable String surname) {
        List<PassengerDTO> passengers = passengerService.getPassengersByNameAndSurname(name, surname);
        return ResponseEntity.ok(passengers);
    }


}

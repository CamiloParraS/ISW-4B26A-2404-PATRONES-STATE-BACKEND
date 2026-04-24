package com.ridehailing.api;

import com.ridehailing.api.dto.ActionRequestDTO;
import com.ridehailing.api.dto.ErrorDTO;
import com.ridehailing.api.dto.RideResponseDTO;
import com.ridehailing.service.RideNotFoundException;
import com.ridehailing.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping
    public ResponseEntity<?> createRide() {
        String rideId = rideService.createRide();
        String stateName = rideService.getState(rideId);
        RideResponseDTO response =
                new RideResponseDTO(rideId, stateName, "Ride created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRideState(@PathVariable String id) {
        String stateName = rideService.getState(id);
        RideResponseDTO response = new RideResponseDTO(id, stateName, "Current state retrieved.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/actions")
    public ResponseEntity<?> performAction(@PathVariable String id,
            @RequestBody ActionRequestDTO request) {
        String stateName = rideService.performAction(id, request.getAction());
        RideResponseDTO response = new RideResponseDTO(id, stateName,
                "Action '" + request.getAction() + "' performed.");
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(RideNotFoundException.class)
    public ResponseEntity<?> handleRideNotFound(RideNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(ex.getMessage()));
    }
}

package com.ridehailing.api;

import com.ridehailing.api.dto.ActionRequestDTO;
import com.ridehailing.api.dto.RideResponseDTO;
import com.ridehailing.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RideResponseDTO> createRide() {
        String rideId = rideService.createRide();
        String stateName = rideService.getState(rideId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RideResponseDTO(rideId, stateName, "Ride created successfully."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideResponseDTO> getRideState(@PathVariable String id) {
        String stateName = rideService.getState(id);
        return ResponseEntity.ok(new RideResponseDTO(id, stateName, "Current state retrieved."));
    }

    @PostMapping("/{id}/actions")
    public ResponseEntity<RideResponseDTO> performAction(@PathVariable String id,
            @Valid @RequestBody ActionRequestDTO request) {
        String stateName = rideService.performAction(id, request.getAction());
        return ResponseEntity.ok(new RideResponseDTO(id, stateName,
                "Action '" + request.getAction() + "' performed."));
    }
}

package com.ridehailing.api.dto;

public class RideResponseDTO {

    private final String id;
    private final String stateName;
    private final String message;

    public RideResponseDTO(String id, String stateName, String message) {
        this.id = id;
        this.stateName = stateName;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getStateName() {
        return stateName;
    }

    public String getMessage() {
        return message;
    }
}

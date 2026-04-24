package com.ridehailing.api.dto;

import jakarta.validation.constraints.NotBlank;

public class ActionRequestDTO {

    @NotBlank(message = "Action must not be blank.")
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

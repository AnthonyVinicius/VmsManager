package com.claro.vmsmanager.dtos;

import com.claro.vmsmanager.entities.Status;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusRequestDTO {

    @NotNull
    private Status status;

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}

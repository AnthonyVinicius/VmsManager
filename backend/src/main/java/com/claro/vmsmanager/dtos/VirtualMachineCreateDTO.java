package com.claro.vmsmanager.dtos;

import com.claro.vmsmanager.entities.Status;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VirtualMachineCreateDTO {

    @NotBlank
    @Size(min = 5)
    private String nome;

    @NotNull
    @Min(1)
    private Integer cpu;

    @NotNull
    @Min(1)
    private Integer memoria;

    @NotNull
    @Min(1)
    private Integer disco;

    private Status status;
}

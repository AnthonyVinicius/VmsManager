package com.claro.vmsmanager.dtos;

import com.claro.vmsmanager.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class VirtualMachineResponseDTO {

    private Long id;
    private String nome;
    private Integer cpu;
    private Integer memoria;
    private Integer disco;
    private Status status;
    private LocalDateTime dataCriacao;
    private Long userId;
}

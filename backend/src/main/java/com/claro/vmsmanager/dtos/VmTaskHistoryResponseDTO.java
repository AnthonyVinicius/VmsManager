package com.claro.vmsmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VmTaskHistoryResponseDTO {
    private String usuario;
    private LocalDateTime dataHora;
    private String nomeMaquina;
    private String acao;
    private String detalhes;
}

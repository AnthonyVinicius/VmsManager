package com.claro.vmsmanager.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 5, message = "Nome deve ter no mínimo 5 caracteres")
    private String nome;
}

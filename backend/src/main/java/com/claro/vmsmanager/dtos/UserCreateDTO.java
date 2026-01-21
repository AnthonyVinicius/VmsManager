package com.claro.vmsmanager.dtos;

import com.claro.vmsmanager.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDTO {

    @NotBlank @Size(min = 5)
    private String nome;

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 6)
    private String senha;

    private Role role;
}

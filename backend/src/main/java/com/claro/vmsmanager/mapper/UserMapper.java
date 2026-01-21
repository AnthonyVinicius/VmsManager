package com.claro.vmsmanager.mapper;

import com.claro.vmsmanager.dtos.UserResponseDTO;
import com.claro.vmsmanager.entities.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail()
        );
    }
}

package com.claro.vmsmanager.services;

import com.claro.vmsmanager.dtos.UserCreateDTO;
import com.claro.vmsmanager.dtos.UserResponseDTO;
import com.claro.vmsmanager.dtos.UserUpdateDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAll();
    UserResponseDTO getById(Long id);
    UserResponseDTO create(UserCreateDTO dto);
    UserResponseDTO update(Long id, UserUpdateDTO dto);
    void delete(Long id);
}

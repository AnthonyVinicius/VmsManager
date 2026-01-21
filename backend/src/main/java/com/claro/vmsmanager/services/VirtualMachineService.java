package com.claro.vmsmanager.services;

import com.claro.vmsmanager.dtos.*;
import java.util.List;

public interface VirtualMachineService {
    List<VirtualMachineResponseDTO> getAll();
    List<VirtualMachineResponseDTO> getAllByUser(Long userId);
    VirtualMachineResponseDTO getById(Long id);
    VirtualMachineResponseDTO create(VirtualMachineCreateDTO dto, Long userId);
    VirtualMachineResponseDTO update(Long id, VirtualMachineUpdateDTO dto);
    VirtualMachineResponseDTO updateStatus(Long id, UpdateStatusRequestDTO dto);
    void delete(Long id);
}

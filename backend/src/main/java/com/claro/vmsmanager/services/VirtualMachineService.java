package com.claro.vmsmanager.services;

import com.claro.vmsmanager.dtos.*;
import java.util.List;

public interface VirtualMachineService {

    List<VirtualMachineResponseDTO> getAll();
    VirtualMachineResponseDTO getById(Long id);
    VirtualMachineResponseDTO create(VirtualMachineCreateDTO dto);
    VirtualMachineResponseDTO update(Long id, VirtualMachineUpdateDTO dto);
    VirtualMachineResponseDTO updateStatus(Long id, UpdateStatusRequestDTO dto);
    void delete(Long id);

    List<VmTaskHistoryResponseDTO> getHistory();
    List<VmTaskHistoryResponseDTO> getHistoryByVm(Long vmId);
}

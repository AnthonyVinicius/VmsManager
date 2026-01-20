package com.claro.vmsmanager.mapper;

import com.claro.vmsmanager.dtos.VirtualMachineCreateDTO;
import com.claro.vmsmanager.dtos.VirtualMachineResponseDTO;
import com.claro.vmsmanager.entities.Status;
import com.claro.vmsmanager.entities.VirtualMachine;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VirtualMachineMapper {

    public VirtualMachineResponseDTO toDTO(VirtualMachine vm) {
        return new VirtualMachineResponseDTO(
                vm.getId(),
                vm.getNome(),
                vm.getCpu(),
                vm.getMemoria(),
                vm.getDisco(),
                vm.getStatus(),
                vm.getDataCriacao()
        );
    }

    public VirtualMachine toEntity(VirtualMachineCreateDTO dto) {
        VirtualMachine vm = new VirtualMachine();
        vm.setNome(dto.getNome());
        vm.setCpu(dto.getCpu());
        vm.setMemoria(dto.getMemoria());
        vm.setDisco(dto.getDisco());
        vm.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.STOP);
        return vm;
    }
}

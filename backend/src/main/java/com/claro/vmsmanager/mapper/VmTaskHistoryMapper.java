package com.claro.vmsmanager.mapper;

import com.claro.vmsmanager.dtos.VmTaskHistoryResponseDTO;
import com.claro.vmsmanager.entities.VmTaskHistory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VmTaskHistoryMapper {

    public VmTaskHistoryResponseDTO toDTO(VmTaskHistory h) {
        return new VmTaskHistoryResponseDTO(
                h.getUser().getEmail(),
                h.getCreatedAt(),
                h.getVmName(),
                h.getAction(),
                h.getDetails()
        );
    }
}

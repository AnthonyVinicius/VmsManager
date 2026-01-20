package com.claro.vmsmanager.services;

import com.claro.vmsmanager.dtos.*;
import com.claro.vmsmanager.entities.VirtualMachine;
import com.claro.vmsmanager.exceptions.ResourceNotFoundException;
import com.claro.vmsmanager.mapper.VirtualMachineMapper;
import com.claro.vmsmanager.repositories.VirtualMachineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VirtualMachineServiceImpl implements VirtualMachineService {

    private final VirtualMachineRepository repository;

    public VirtualMachineServiceImpl(VirtualMachineRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VirtualMachineResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(VirtualMachineMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VirtualMachineResponseDTO getById(Long id) {
        VirtualMachine vm = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id));
        return VirtualMachineMapper.toDTO(vm);
    }

    @Override
    @Transactional
    public VirtualMachineResponseDTO create(VirtualMachineCreateDTO dto) {
        VirtualMachine vm = VirtualMachineMapper.toEntity(dto);
        VirtualMachine saved = repository.save(vm);
        return VirtualMachineMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public VirtualMachineResponseDTO update(Long id, VirtualMachineUpdateDTO dto) {
        VirtualMachine existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id));

        existing.setNome(dto.getNome());
        existing.setCpu(dto.getCpu());
        existing.setMemoria(dto.getMemoria());
        existing.setDisco(dto.getDisco());

        VirtualMachine saved = repository.save(existing);
        return VirtualMachineMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public VirtualMachineResponseDTO updateStatus(Long id, UpdateStatusRequestDTO dto) {
        VirtualMachine existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id));

        existing.setStatus(dto.getStatus());

        VirtualMachine saved = repository.save(existing);
        return VirtualMachineMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        VirtualMachine existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id));
        repository.delete(existing);
    }
}

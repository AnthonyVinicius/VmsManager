package com.claro.vmsmanager.services;

import com.claro.vmsmanager.dtos.UpdateStatusRequestDTO;
import com.claro.vmsmanager.dtos.VirtualMachineCreateDTO;
import com.claro.vmsmanager.dtos.VirtualMachineResponseDTO;
import com.claro.vmsmanager.dtos.VirtualMachineUpdateDTO;
import com.claro.vmsmanager.entities.User;
import com.claro.vmsmanager.entities.VirtualMachine;
import com.claro.vmsmanager.exceptions.BusinessException;
import com.claro.vmsmanager.exceptions.ResourceNotFoundException;
import com.claro.vmsmanager.mapper.VirtualMachineMapper;
import com.claro.vmsmanager.repositories.UserRepository;
import com.claro.vmsmanager.repositories.VirtualMachineRepository;
import com.claro.vmsmanager.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VirtualMachineServiceImpl implements VirtualMachineService {

    private static final int VM_LIMIT = 5;

    private final VirtualMachineRepository repository;
    private final UserRepository userRepository;

    public VirtualMachineServiceImpl(VirtualMachineRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VirtualMachineResponseDTO> getAll() {

        if (SecurityUtils.isAdmin()) {
            return repository.findAll()
                    .stream()
                    .map(VirtualMachineMapper::toDTO)
                    .toList();
        }

        Long userId = SecurityUtils.getLoggedUserId();
        return repository.findByUser_Id(userId)
                .stream()
                .map(VirtualMachineMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VirtualMachineResponseDTO getById(Long id) {

        if (!SecurityUtils.isAdmin()) {
            Long userId = SecurityUtils.getLoggedUserId();
            if (!repository.existsByIdAndUser_Id(id, userId)) {
                throw new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id);
            }
        }

        VirtualMachine vm = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id));

        return VirtualMachineMapper.toDTO(vm);
    }

    @Override
    @Transactional
    public VirtualMachineResponseDTO create(VirtualMachineCreateDTO dto) {
        Long userId = SecurityUtils.getLoggedUserId();

        if (!SecurityUtils.isAdmin()) {
            long total = repository.countByUser_Id(userId);
            if (total >= VM_LIMIT) {
                throw new BusinessException("Limite atingido: você já possui 5 VMs cadastradas.");
            }
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: id=" + userId));

        VirtualMachine vm = VirtualMachineMapper.toEntity(dto);
        vm.setUser(user);

        VirtualMachine saved = repository.save(vm);
        return VirtualMachineMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public VirtualMachineResponseDTO update(Long id, VirtualMachineUpdateDTO dto) {

        if (!SecurityUtils.isAdmin()) {
            Long userId = SecurityUtils.getLoggedUserId();
            if (!repository.existsByIdAndUser_Id(id, userId)) {
                throw new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id);
            }
        }

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

        if (!SecurityUtils.isAdmin()) {
            Long userId = SecurityUtils.getLoggedUserId();
            if (!repository.existsByIdAndUser_Id(id, userId)) {
                throw new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id);
            }
        }

        VirtualMachine existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id));

        existing.setStatus(dto.getStatus());

        VirtualMachine saved = repository.save(existing);
        return VirtualMachineMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        if (!SecurityUtils.isAdmin()) {
            Long userId = SecurityUtils.getLoggedUserId();
            if (!repository.existsByIdAndUser_Id(id, userId)) {
                throw new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id);
            }
        }

        VirtualMachine existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id));

        repository.delete(existing);
    }
}

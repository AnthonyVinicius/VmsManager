package com.claro.vmsmanager.services;

import com.claro.vmsmanager.dtos.*;
import com.claro.vmsmanager.entities.User;
import com.claro.vmsmanager.entities.VirtualMachine;
import com.claro.vmsmanager.entities.VmTaskHistory;
import com.claro.vmsmanager.exceptions.BusinessException;
import com.claro.vmsmanager.exceptions.ResourceNotFoundException;
import com.claro.vmsmanager.mapper.VirtualMachineMapper;
import com.claro.vmsmanager.mapper.VmTaskHistoryMapper;
import com.claro.vmsmanager.repositories.UserRepository;
import com.claro.vmsmanager.repositories.VirtualMachineRepository;
import com.claro.vmsmanager.repositories.VmTaskHistoryRepository;
import com.claro.vmsmanager.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VirtualMachineServiceImpl implements VirtualMachineService {

    private static final int VM_LIMIT = 5;

    private final VirtualMachineRepository repository;
    private final UserRepository userRepository;
    private final VmTaskHistoryRepository historyRepository;

    public VirtualMachineServiceImpl(VirtualMachineRepository repository, UserRepository userRepository, VmTaskHistoryRepository historyRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VirtualMachineResponseDTO> getAll() {
        if (SecurityUtils.isAdmin()) {
            return repository.findAll().stream().map(VirtualMachineMapper::toDTO).toList();
        }

        Long userId = SecurityUtils.getLoggedUserId();
        return repository.findByUser_Id(userId).stream().map(VirtualMachineMapper::toDTO).toList();
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

        return repository.findById(id)
                .map(VirtualMachineMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id));
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
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        VirtualMachine vm = VirtualMachineMapper.toEntity(dto);
        vm.setUser(user);

        VirtualMachine saved = repository.save(vm);

        logHistory(user, saved, "CREATE", "VM criada");

        return VirtualMachineMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public VirtualMachineResponseDTO update(Long id, VirtualMachineUpdateDTO dto) {

        Long userId = SecurityUtils.getLoggedUserId();

        if (!SecurityUtils.isAdmin() && !repository.existsByIdAndUser_Id(id, userId)) {
            throw new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        VirtualMachine vm = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Máquina virtual não encontrada"));

        String before = String.format("cpu:%s mem:%s disk:%s", vm.getCpu(), vm.getMemoria(), vm.getDisco());

        vm.setNome(dto.getNome());
        vm.setCpu(dto.getCpu());
        vm.setMemoria(dto.getMemoria());
        vm.setDisco(dto.getDisco());

        VirtualMachine saved = repository.save(vm);

        String after = String.format("cpu:%s mem:%s disk:%s", saved.getCpu(), saved.getMemoria(), saved.getDisco());

        logHistory(user, saved, "UPDATE", before + " -> " + after);

        return VirtualMachineMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public VirtualMachineResponseDTO updateStatus(Long id, UpdateStatusRequestDTO dto) {

        Long userId = SecurityUtils.getLoggedUserId();

        if (!SecurityUtils.isAdmin() && !repository.existsByIdAndUser_Id(id, userId)) {
            throw new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        VirtualMachine vm = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Máquina virtual não encontrada"));

        String old = vm.getStatus().toString();

        vm.setStatus(dto.getStatus());

        VirtualMachine saved = repository.save(vm);

        logHistory(user, saved, "UPDATE_STATUS", old + " -> " + dto.getStatus());

        return VirtualMachineMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Long userId = SecurityUtils.getLoggedUserId();

        if (!SecurityUtils.isAdmin() && !repository.existsByIdAndUser_Id(id, userId)) {
            throw new ResourceNotFoundException("Máquina virtual não encontrada: id=" + id);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        VirtualMachine vm = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Máquina virtual não encontrada"));

        logHistory(user, vm, "DELETE", "VM deletada");

        repository.delete(vm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VmTaskHistoryResponseDTO> getHistory() {
        if (SecurityUtils.isAdmin()) {
            return historyRepository.findAllByOrderByCreatedAtDesc()
                    .stream().map(VmTaskHistoryMapper::toDTO).toList();
        }

        Long userId = SecurityUtils.getLoggedUserId();
        return historyRepository.findByUser_IdOrderByCreatedAtDesc(userId)
                .stream().map(VmTaskHistoryMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VmTaskHistoryResponseDTO> getHistoryByVm(Long vmId) {
        if (SecurityUtils.isAdmin()) {
            return historyRepository.findByVirtualMachine_IdOrderByCreatedAtDesc(vmId)
                    .stream().map(VmTaskHistoryMapper::toDTO).toList();
        }

        Long userId = SecurityUtils.getLoggedUserId();

        if (!repository.existsByIdAndUser_Id(vmId, userId)) {
            throw new ResourceNotFoundException("Máquina virtual não encontrada");
        }

        return historyRepository.findByVirtualMachine_IdAndUser_IdOrderByCreatedAtDesc(vmId, userId)
                .stream().map(VmTaskHistoryMapper::toDTO).toList();
    }

    private void logHistory(User user, VirtualMachine vm, String action, String details) {
        VmTaskHistory h = new VmTaskHistory();
        h.setUser(user);
        h.setVirtualMachine(vm);
        h.setVmName(vm.getNome());
        h.setAction(action);
        h.setDetails(details);
        historyRepository.save(h);
    }
}

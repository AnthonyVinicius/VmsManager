package com.claro.vmsmanager.repositories;

import com.claro.vmsmanager.entities.VmTaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VmTaskHistoryRepository extends JpaRepository<VmTaskHistory, Long> {
    List<VmTaskHistory> findByUser_IdOrderByCreatedAtDesc(Long userId);
    List<VmTaskHistory> findAllByOrderByCreatedAtDesc();
    List<VmTaskHistory> findByVirtualMachine_IdAndUser_IdOrderByCreatedAtDesc(Long vmId, Long userId);
    List<VmTaskHistory> findByVirtualMachine_IdOrderByCreatedAtDesc(Long vmId);
}

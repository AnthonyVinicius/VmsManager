package com.claro.vmsmanager.repositories;

import com.claro.vmsmanager.entities.VirtualMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long> {
    List<VirtualMachine> findByUser_Id(Long userId);
    boolean existsByIdAndUser_Id(Long id, Long userId);
    long countByUser_Id(Long userId);
}

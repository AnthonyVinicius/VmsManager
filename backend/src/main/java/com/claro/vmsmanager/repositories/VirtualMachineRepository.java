package com.claro.vmsmanager.repositories;

import com.claro.vmsmanager.entities.VirtualMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long> {
    List<VirtualMachine> findByuserId(Long userId);
}

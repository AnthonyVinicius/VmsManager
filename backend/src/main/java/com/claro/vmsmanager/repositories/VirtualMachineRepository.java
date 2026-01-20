package com.claro.vmsmanager.repositories;

import com.claro.vmsmanager.entities.VirtualMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long> {
}

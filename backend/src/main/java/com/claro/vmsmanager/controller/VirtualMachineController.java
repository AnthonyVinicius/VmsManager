package com.claro.vmsmanager.controller;

import com.claro.vmsmanager.dtos.*;
import com.claro.vmsmanager.security.JwtService;
import com.claro.vmsmanager.services.VirtualMachineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vm")
public class VirtualMachineController {

    private final VirtualMachineService service;
    private final JwtService jwtService;

    public VirtualMachineController(VirtualMachineService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<VirtualMachineResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VirtualMachineResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<VirtualMachineResponseDTO> create(
            @Valid @RequestBody VirtualMachineCreateDTO dto,
            HttpServletRequest request
    ) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = auth.substring(7);
        Long userId = jwtService.extractUserId(token);

        VirtualMachineResponseDTO saved = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VirtualMachineResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody VirtualMachineUpdateDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<VirtualMachineResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequestDTO req
    ) {
        return ResponseEntity.ok(service.updateStatus(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

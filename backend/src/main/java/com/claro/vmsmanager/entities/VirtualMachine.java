package com.claro.vmsmanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "virtual_machines")
public class VirtualMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 5, message = "Nome deve ter no mínimo 5 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "CPU é obrigatória")
    @Min(value = 1, message = "CPU deve ser maior que zero")
    @Column(nullable = false)
    private Integer cpu;

    @NotNull(message = "Memória é obrigatória")
    @Min(value = 2, message = "Memória deve ser maior que zero")
    @Column(nullable = false)
    private Integer memoria;

    @NotNull(message = "Disco é obrigatório")
    @Min(value = 50, message = "Disco deve ser maior que zero")
    @Column(nullable = false)
    private Integer disco;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "virtualMachine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VmTaskHistory> history = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        if (this.status == null) this.status = Status.STOP;
    }
}


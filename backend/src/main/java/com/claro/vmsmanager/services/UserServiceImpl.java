package com.claro.vmsmanager.services;

import com.claro.vmsmanager.dtos.UserCreateDTO;
import com.claro.vmsmanager.dtos.UserResponseDTO;
import com.claro.vmsmanager.dtos.UserUpdateDTO;
import com.claro.vmsmanager.entities.User;
import com.claro.vmsmanager.exceptions.EmailAlreadyExistsException;
import com.claro.vmsmanager.exceptions.ResourceNotFoundException;
import com.claro.vmsmanager.mapper.UserMapper;
import com.claro.vmsmanager.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: id=" + id));
        return UserMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO create(UserCreateDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email já cadastrado: " + dto.getEmail());
        }

        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(passwordEncoder.encode(dto.getSenha()));

        User saved = repository.save(user);
        return UserMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: id=" + id));

        existing.setNome(dto.getNome());

        User saved = repository.save(existing);
        return UserMapper.toDTO(saved);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: id=" + id));
        repository.delete(existing);
    }
}

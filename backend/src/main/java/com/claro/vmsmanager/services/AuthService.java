package com.claro.vmsmanager.services;

import com.claro.vmsmanager.dtos.LoginRequestDTO;
import com.claro.vmsmanager.dtos.LoginResponseDTO;
import com.claro.vmsmanager.entities.User;
import com.claro.vmsmanager.repositories.UserRepository;
import com.claro.vmsmanager.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(dto.getSenha(), user.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtService.generateToken(user.getId(), user.getNome());

        return new LoginResponseDTO(token);
    }
}

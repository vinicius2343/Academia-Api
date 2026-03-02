package com.academia.apiacademia.service;

import com.academia.apiacademia.dto.input.CreateAdminRequest;
import com.academia.apiacademia.dto.input.UpdateAdminRequest;
import com.academia.apiacademia.dto.output.AdminResponse;
import com.academia.apiacademia.entity.Role;
import com.academia.apiacademia.entity.User;
import com.academia.apiacademia.exception.DuplicateResourceException;
import com.academia.apiacademia.exception.ResourceNotFoundException;
import com.academia.apiacademia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar administradores
 * Trabalha com User que tem ROLE_ADMIN
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registra um novo admin
     * Cria User com ROLE_ADMIN
     */
    public AdminResponse registrarAdmin(CreateAdminRequest request) {
        log.info("Registrando novo admin com email: {}", request.getEmail());

        // Validar email único
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Email já cadastrado: {}", request.getEmail());
            throw new DuplicateResourceException("Usuário", "email", request.getEmail());
        }

        // Criar usuário com role ROLE_ADMIN
        User novoAdmin = User.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(Role.ROLE_ADMIN)
                .ativo(true)
                .build();

        User adminSalvo = userRepository.save(novoAdmin);
        log.info("Admin criado com sucesso. ID: {}", adminSalvo.getId());

        return toAdminResponse(adminSalvo);
    }

    /**
     * Busca admins por nome (contém)
     */
    @Transactional(readOnly = true)
    public List<AdminResponse> buscarPorNome(String nome) {
        log.debug("Buscando admins por nome contendo: {}", nome);

        List<User> admins = userRepository.findByRoleAndNomeContainingIgnoreCase(Role.ROLE_ADMIN, nome);

        return admins.stream()
                .map(this::toAdminResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza um admin
     */
    public AdminResponse atualizar(Long id, UpdateAdminRequest request) {
        log.info("Atualizando admin ID: {}", id);

        User admin = buscarPorIdEntidade(id);

        // Verificar se é realmente um admin
        if (!admin.getRole().equals(Role.ROLE_ADMIN)) {
            log.warn("Usuário {} não é um admin", id);
            throw new ResourceNotFoundException("Admin", "id", id);
        }

        // Atualizar campos se fornecidos
        if (request.getNome() != null && !request.getNome().isBlank()) {
            admin.setNome(request.getNome());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!admin.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                log.warn("Novo email já existe: {}", request.getEmail());
                throw new DuplicateResourceException("Usuário", "email", request.getEmail());
            }
            admin.setEmail(request.getEmail());
        }

        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            admin.setSenha(passwordEncoder.encode(request.getSenha()));
        }

        User adminAtualizado = userRepository.save(admin);
        log.info("Admin atualizado com sucesso. ID: {}", adminAtualizado.getId());

        return toAdminResponse(adminAtualizado);
    }

    /**
     * Deleta um admin
     */
    public void deletar(Long id) {
        log.info("Deletando admin ID: {}", id);

        User admin = buscarPorIdEntidade(id);

        // Verificar se é realmente um admin
        if (!admin.getRole().equals(Role.ROLE_ADMIN)) {
            log.warn("Usuário {} não é um admin", id);
            throw new ResourceNotFoundException("Admin", "id", id);
        }

        userRepository.deleteById(id);
        log.info("Admin deletado com sucesso. ID: {}", id);
    }

    /**
     * Método auxiliar: busca entidade User por ID
     */
    @Transactional(readOnly = true)
    private User buscarPorIdEntidade(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado com ID: {}", id);
                    return new ResourceNotFoundException("Usuário", "id", id);
                });
    }

    /**
     * Converte User em AdminResponse
     */
    private AdminResponse toAdminResponse(User user) {
        return AdminResponse.builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .ativo(user.getAtivo())
                .role(user.getRole().name())
                .criadoEm(user.getCriadoEm())
                .atualizadoEm(user.getAtualizadoEm())
                .build();
    }
}


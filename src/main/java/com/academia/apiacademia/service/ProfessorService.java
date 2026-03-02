package com.academia.apiacademia.service;

import com.academia.apiacademia.dto.input.CreateProfessorRequest;
import com.academia.apiacademia.dto.input.UpdateProfessorRequest;
import com.academia.apiacademia.dto.output.ProfessorResponse;
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
 * Serviço para gerenciar professores
 * Trabalha com User que tem ROLE_PROFESSOR
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProfessorService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registra um novo professor
     * Cria User com ROLE_PROFESSOR
     */
    public ProfessorResponse registrarProfessor(CreateProfessorRequest request) {
        log.info("Registrando novo professor com email: {}, CPF: {} e CREF: {}",
                request.getEmail(), request.getCpf(), request.getCref());

        // Validar email único
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Email já cadastrado: {}", request.getEmail());
            throw new DuplicateResourceException("Usuário", "email", request.getEmail());
        }

        // Validar CPF único
        if (userRepository.existsByCpf(request.getCpf())) {
            log.warn("CPF já cadastrado: {}", request.getCpf());
            throw new DuplicateResourceException("Professor", "cpf", request.getCpf());
        }

        // Validar CREF único
        if (userRepository.existsByCref(request.getCref())) {
            log.warn("CREF já cadastrado: {}", request.getCref());
            throw new DuplicateResourceException("Professor", "cref", request.getCref());
        }

        // Criar usuário com role ROLE_PROFESSOR
        User novoProfessor = User.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .cpf(request.getCpf())
                .cref(request.getCref())
                .telefone(request.getTelefone())
                .role(Role.ROLE_PROFESSOR)
                .ativo(true)
                .build();

        User professorSalvo = userRepository.save(novoProfessor);
        log.info("Professor criado com sucesso. ID: {}", professorSalvo.getId());

        return toProfessorResponse(professorSalvo);
    }

    /**
     * Busca professores por nome (contém, case insensitive)
     */
    @Transactional(readOnly = true)
    public List<ProfessorResponse> buscarPorNome(String nome) {
        log.debug("Buscando professores por nome contendo: {}", nome);

        List<User> professores = userRepository.findByRoleAndNomeContainingIgnoreCase(Role.ROLE_PROFESSOR, nome);

        return professores.stream()
                .map(this::toProfessorResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca professor por CREF
     */
    @Transactional(readOnly = true)
    public ProfessorResponse buscarPorCref(String cref) {
        log.debug("Buscando professor por CREF: {}", cref);

        User professor = userRepository.findByCref(cref)
                .orElseThrow(() -> {
                    log.warn("Professor não encontrado com CREF: {}", cref);
                    return new ResourceNotFoundException("Professor", "cref", cref);
                });

        // Verificar se é realmente um professor
        if (!professor.getRole().equals(Role.ROLE_PROFESSOR)) {
            throw new ResourceNotFoundException("Professor", "cref", cref);
        }

        return toProfessorResponse(professor);
    }

    /**
     * Busca professor por CPF
     */
    @Transactional(readOnly = true)
    public ProfessorResponse buscarPorCpf(String cpf) {
        log.debug("Buscando professor por CPF: {}", cpf);

        User professor = userRepository.findByCpf(cpf)
                .orElseThrow(() -> {
                    log.warn("Professor não encontrado com CPF: {}", cpf);
                    return new ResourceNotFoundException("Professor", "cpf", cpf);
                });

        // Verificar se é realmente um professor
        if (!professor.getRole().equals(Role.ROLE_PROFESSOR)) {
            throw new ResourceNotFoundException("Professor", "cpf", cpf);
        }

        return toProfessorResponse(professor);
    }

    /**
     * Atualiza dados do professor
     */
    public ProfessorResponse atualizar(Long id, UpdateProfessorRequest request) {
        log.info("Atualizando professor ID: {}", id);

        User professor = buscarPorIdEntidade(id);

        // Verificar se é realmente um professor
        if (!professor.getRole().equals(Role.ROLE_PROFESSOR)) {
            throw new ResourceNotFoundException("Professor", "id", id);
        }

        // Atualizar campos se fornecidos
        if (request.getNome() != null && !request.getNome().isBlank()) {
            professor.setNome(request.getNome());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!professor.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                log.warn("Novo email já existe: {}", request.getEmail());
                throw new DuplicateResourceException("Usuário", "email", request.getEmail());
            }
            professor.setEmail(request.getEmail());
        }

        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            professor.setSenha(passwordEncoder.encode(request.getSenha()));
        }

        if (request.getTelefone() != null) {
            professor.setTelefone(request.getTelefone());
        }

        User professorAtualizado = userRepository.save(professor);
        log.info("Professor atualizado com sucesso. ID: {}", professorAtualizado.getId());

        return toProfessorResponse(professorAtualizado);
    }

    /**
     * Deleta um professor
     */
    public void deletar(Long id) {
        log.info("Deletando professor ID: {}", id);

        User professor = buscarPorIdEntidade(id);

        // Verificar se é realmente um professor
        if (!professor.getRole().equals(Role.ROLE_PROFESSOR)) {
            throw new ResourceNotFoundException("Professor", "id", id);
        }

        userRepository.deleteById(id);
        log.info("Professor deletado com sucesso. ID: {}", id);
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
     * Converte User em ProfessorResponse
     */
    private ProfessorResponse toProfessorResponse(User user) {
        return ProfessorResponse.builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .cpf(user.getCpf())
                .cref(user.getCref())
                .telefone(user.getTelefone())
                .ativo(user.getAtivo())
                .role(user.getRole().name())
                .criadoEm(user.getCriadoEm())
                .atualizadoEm(user.getAtualizadoEm())
                .build();
    }
}


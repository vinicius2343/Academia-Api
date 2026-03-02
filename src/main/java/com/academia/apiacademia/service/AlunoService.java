package com.academia.apiacademia.service;

import com.academia.apiacademia.dto.input.CreateAlunoRequest;
import com.academia.apiacademia.dto.input.UpdateAlunoRequest;
import com.academia.apiacademia.dto.output.AlunoResponse;
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
 * Serviço para gerenciar alunos
 * Trabalha com User que tem ROLE_ALUNO
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AlunoService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registra um novo aluno
     * Cria User com ROLE_ALUNO
     */
    public AlunoResponse registrarAluno(CreateAlunoRequest request) {
        log.info("Registrando novo aluno com email: {} e CPF: {}", request.getEmail(), request.getCpf());

        // Validar email único
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Email já cadastrado: {}", request.getEmail());
            throw new DuplicateResourceException("Usuário", "email", request.getEmail());
        }

        // Validar CPF único
        if (userRepository.existsByCpf(request.getCpf())) {
            log.warn("CPF já cadastrado: {}", request.getCpf());
            throw new DuplicateResourceException("Aluno", "cpf", request.getCpf());
        }

        // Criar usuário com role ROLE_ALUNO
        User novoAluno = User.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .cpf(request.getCpf())
                .telefone(request.getTelefone())
                .role(Role.ROLE_ALUNO)
                .ativo(true)
                .build();

        User alunoSalvo = userRepository.save(novoAluno);
        log.info("Aluno criado com sucesso. ID: {}", alunoSalvo.getId());

        return toAlunoResponse(alunoSalvo);
    }

    /**
     * Busca alunos por nome (contém, case insensitive)
     */
    @Transactional(readOnly = true)
    public List<AlunoResponse> buscarPorNome(String nome) {
        log.debug("Buscando alunos por nome contendo: {}", nome);

        List<User> alunos = userRepository.findByRoleAndNomeContainingIgnoreCase(Role.ROLE_ALUNO, nome);

        return alunos.stream()
                .map(this::toAlunoResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca aluno por CPF
     */
    @Transactional(readOnly = true)
    public AlunoResponse buscarPorCpf(String cpf) {
        log.debug("Buscando aluno por CPF: {}", cpf);

        User aluno = userRepository.findByCpf(cpf)
                .orElseThrow(() -> {
                    log.warn("Aluno não encontrado com CPF: {}", cpf);
                    return new ResourceNotFoundException("Aluno", "cpf", cpf);
                });

        // Verificar se é realmente um aluno
        if (!aluno.getRole().equals(Role.ROLE_ALUNO)) {
            throw new ResourceNotFoundException("Aluno", "cpf", cpf);
        }

        return toAlunoResponse(aluno);
    }

    /**
     * Atualiza um aluno existente
     */
    public AlunoResponse atualizar(Long id, UpdateAlunoRequest request) {
        log.info("Atualizando aluno ID: {}", id);

        User aluno = buscarPorIdEntidade(id);

        // Verificar se é realmente um aluno
        if (!aluno.getRole().equals(Role.ROLE_ALUNO)) {
            throw new ResourceNotFoundException("Aluno", "id", id);
        }

        // Atualizar campos se fornecidos
        if (request.getNome() != null && !request.getNome().isBlank()) {
            aluno.setNome(request.getNome());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!aluno.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                log.warn("Novo email já existe: {}", request.getEmail());
                throw new DuplicateResourceException("Usuário", "email", request.getEmail());
            }
            aluno.setEmail(request.getEmail());
        }

        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            aluno.setSenha(passwordEncoder.encode(request.getSenha()));
        }

        if (request.getTelefone() != null) {
            aluno.setTelefone(request.getTelefone());
        }

        User alunoAtualizado = userRepository.save(aluno);
        log.info("Aluno atualizado com sucesso. ID: {}", alunoAtualizado.getId());

        return toAlunoResponse(alunoAtualizado);
    }

    /**
     * Deleta um aluno
     */
    public void deletar(Long id) {
        log.info("Deletando aluno ID: {}", id);

        User aluno = buscarPorIdEntidade(id);

        // Verificar se é realmente um aluno
        if (!aluno.getRole().equals(Role.ROLE_ALUNO)) {
            throw new ResourceNotFoundException("Aluno", "id", id);
        }

        userRepository.deleteById(id);
        log.info("Aluno deletado com sucesso. ID: {}", id);
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
     * Converte User em AlunoResponse
     */
    private AlunoResponse toAlunoResponse(User user) {
        return AlunoResponse.builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .cpf(user.getCpf())
                .telefone(user.getTelefone())
                .ativo(user.getAtivo())
                .role(user.getRole().name())
                .criadoEm(user.getCriadoEm())
                .atualizadoEm(user.getAtualizadoEm())
                .build();
    }
}


package com.academia.apiacademia.config;

import com.academia.apiacademia.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração para inicializar dados padrão do sistema
 * Executa ao iniciar a aplicação
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class   DataInitializationConfig {

    private final RoleRepository roleRepository;

    /**
     * Bean que popula as roles padrão no banco de dados
     * Executa uma única vez na inicialização da aplicação
     */
    @Bean
    public CommandLineRunner initializeRoles() {
        return args -> {
            log.info("Inicializando roles padrão do sistema");

            // Criar ROLE_ADMIN se não existir
            if (roleRepository.findByTipo(Role.RoleType.ROLE_ADMIN).isEmpty()) {
                Role roleAdmin = Role.builder()
                        .nome("ROLE_ADMIN")
                        .tipo(Role.RoleType.ROLE_ADMIN)
                        .build();
                roleRepository.save(roleAdmin);
                log.info("Role ROLE_ADMIN criada");
            }

            // Criar ROLE_PROFESSOR se não existir
            if (roleRepository.findByTipo(Role.RoleType.ROLE_PROFESSOR).isEmpty()) {
                Role roleProfessor = Role.builder()
                        .nome("ROLE_PROFESSOR")
                        .tipo(Role.RoleType.ROLE_PROFESSOR)
                        .build();
                roleRepository.save(roleProfessor);
                log.info("Role ROLE_PROFESSOR criada");
            }

            // Criar ROLE_ALUNO se não existir
            if (roleRepository.findByTipo(Role.RoleType.ROLE_ALUNO).isEmpty()) {
                Role roleAluno = Role.builder()
                        .nome("ROLE_ALUNO")
                        .tipo(Role.RoleType.ROLE_ALUNO)
                        .build();
                roleRepository.save(roleAluno);
                log.info("Role ROLE_ALUNO criada");
            }

            log.info("Roles padrão inicializadas com sucesso");
        };
    }
}


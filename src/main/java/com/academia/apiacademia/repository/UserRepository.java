package com.academia.apiacademia.repository;

import com.academia.apiacademia.entity.Role;
import com.academia.apiacademia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar usuários
 * Serve para Admin, Aluno e Professor (todos são User com roles diferentes)
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca usuário por email
     * @param email o email do usuário
     * @return Optional contendo o usuário ou vazio se não encontrado
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se email já existe no banco
     * @param email o email a verificar
     * @return true se email existe, false caso contrário
     */
    boolean existsByEmail(String email);

    /**
     * Busca usuários por nome (contém, case insensitive)
     * @param nome o nome ou parte do nome
     * @return lista de usuários encontrados
     */
    List<User> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca usuários por role e nome (contém, case insensitive)
     * Usado para buscar apenas Admins, Alunos ou Professores por nome
     * @param role a role do usuário (ROLE_ADMIN, ROLE_ALUNO, ROLE_PROFESSOR)
     * @param nome o nome ou parte do nome
     * @return lista de usuários encontrados
     */
    List<User> findByRoleAndNomeContainingIgnoreCase(Role role, String nome);

    /**
     * Busca usuário por CPF
     * @param cpf o CPF do usuário
     * @return Optional contendo o usuário ou vazio
     */
    Optional<User> findByCpf(String cpf);

    /**
     * Verifica se CPF já existe
     * @param cpf o CPF a verificar
     * @return true se CPF existe, false caso contrário
     */
    boolean existsByCpf(String cpf);

    /**
     * Busca usuário por CREF (apenas professores têm CREF)
     * @param cref o CREF do professor
     * @return Optional contendo o usuário ou vazio
     */
    Optional<User> findByCref(String cref);

    /**
     * Verifica se CREF já existe
     * @param cref o CREF a verificar
     * @return true se CREF existe, false caso contrário
     */
    boolean existsByCref(String cref);

    /**
     * Busca usuários por role
     * Útil para listar todos os admins, alunos ou professores
     * @param role a role do usuário
     * @return lista de usuários com essa role
     */
    List<User> findByRole(Role role);
}


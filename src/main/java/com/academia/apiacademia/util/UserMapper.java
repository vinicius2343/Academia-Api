package com.academia.apiacademia.util;

import com.academia.apiacademia.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utilidade para conversão entre entidades e DTOs
 * Concentra a lógica de mapeamento em um único lugar
 * Facilita manutenção e evolução
 */
@Component
public class UserMapper {

    /**
     * Converte entidade User para UserResponse DTO
     * @param user a entidade User
     * @return UserResponse com dados da entidade
     */
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .ativo(user.getAtivo())
                .roles(user.getRoles().stream()
                        .map(role -> role.getTipo().name())
                        .collect(Collectors.toSet()))
                .criadoEm(user.getCriadoEm())
                .atualizadoEm(user.getAtualizadoEm())
                .build();
    }

    /**
     * Converte conjunto de Users para conjunto de UserResponse DTOs
     * @param users conjunto de entidades User
     * @return Set de UserResponse
     */
    public Set<UserResponse> toUserResponseSet(Set<User> users) {
        return users.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toSet());
    }
}


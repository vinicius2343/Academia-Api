package com.academia.apiacademia.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para resposta de admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResponse {

    private Long id;
    private String nome;
    private String email;
    private Boolean ativo;
    private String role;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
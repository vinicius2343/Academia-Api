package com.academia.apiacademia.dto.output;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProfessorResponse {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private String cref;
    private Boolean ativo;
    private String role;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}

package com.academia.apiacademia.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisições de atualização de aluno
 * Todos os campos são opcionais
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAlunoRequest {

    @Size(min = 3, max = 150, message = "Nome deve ter entre 3 e 150 caracteres")
    private String nome;

    @Email(message = "Email deve ser válido")
    @Size(max = 150, message = "Email não pode exceder 150 caracteres")
    private String email;

    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
    private String senha;

    private String cpf;

    private String telefone;
}


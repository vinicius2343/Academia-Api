package com.academia.apiacademia.controller;

import com.academia.apiacademia.dto.input.CreateAlunoRequest;
import com.academia.apiacademia.dto.input.UpdateAlunoRequest;
import com.academia.apiacademia.dto.output.AlunoResponse;
import com.academia.apiacademia.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para alunos
 * Expõe endpoints para cadastro e gerenciamento de alunos
 */
@RestController
@RequestMapping("/v1/alunos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Alunos", description = "Endpoints para gestão de alunos")
public class AlunoController {

    private final AlunoService alunoService;

    /**
     * POST /v1/alunos
     * Registra um novo aluno (cria User + Aluno)
     * @param request dados completos do aluno
     * @return ResponseEntity com AlunoResponse e status 201 (Created)
     */
    @PostMapping
    @Operation(summary = "Criar novo aluno", description = "Registra um aluno com seus dados de usuário")
    public ResponseEntity<AlunoResponse> criar(@Valid @RequestBody CreateAlunoRequest request) {
        log.info("POST /v1/alunos - Criando novo aluno com email: {}", request.getEmail());

        AlunoResponse response = alunoService.registrarAluno(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /v1/alunos/buscar?nome=x
     * Busca alunos por nome (contém)
     * @param nome o nome ou parte do nome para buscar
     * @return ResponseEntity com lista de AlunoResponse e status 200 (OK)
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar alunos por nome", description = "Retorna alunos cujo nome contém o texto informado")
    public ResponseEntity<List<AlunoResponse>> buscarPorNome(@RequestParam String nome) {
        log.info("GET /v1/alunos/buscar?nome={} - Buscando alunos", nome);

        List<AlunoResponse> response = alunoService.buscarPorNome(nome);

        return ResponseEntity.ok(response);
    }

    /**
     * GET /v1/alunos/cpf/{cpf}
     * Busca aluno por CPF
     * @param cpf o CPF do aluno
     * @return ResponseEntity com AlunoResponse e status 200 (OK)
     */
    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar aluno por CPF", description = "Retorna um aluno pelo CPF")
    public ResponseEntity<AlunoResponse> buscarPorCpf(@PathVariable String cpf) {
        log.info("GET /v1/alunos/cpf/{} - Buscando aluno por CPF", cpf);

        AlunoResponse response = alunoService.buscarPorCpf(cpf);

        return ResponseEntity.ok(response);
    }

    /**
     * PUT /v1/alunos/{id}
     * Atualiza dados do aluno
     * @param id o ID do aluno
     * @param request novos dados do aluno
     * @return ResponseEntity com AlunoResponse e status 200 (OK)
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar aluno", description = "Atualiza os dados de um aluno existente")
    public ResponseEntity<AlunoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody UpdateAlunoRequest request) {
        log.info("PUT /v1/alunos/{} - Atualizando aluno", id);

        AlunoResponse response = alunoService.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /v1/alunos/{id}
     * Deleta um aluno
     * @param id o ID do aluno
     * @return ResponseEntity com status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar aluno", description = "Remove um aluno do sistema")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("DELETE /v1/alunos/{} - Deletando aluno", id);

        alunoService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}


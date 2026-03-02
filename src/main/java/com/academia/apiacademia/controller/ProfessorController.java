package com.academia.apiacademia.controller;

import com.academia.apiacademia.dto.input.CreateProfessorRequest;
import com.academia.apiacademia.dto.input.UpdateProfessorRequest;
import com.academia.apiacademia.dto.output.AlunoResponse;
import com.academia.apiacademia.dto.output.ProfessorResponse;
import com.academia.apiacademia.service.ProfessorService;
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
 * Controller REST para professores
 * Expõe endpoints para cadastro e gerenciamento de professores
 */
@RestController
@RequestMapping("/v1/professores")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Professores", description = "Endpoints para gestão de professores")
public class ProfessorController {

    private final ProfessorService professorService;

    /**
     * POST /v1/professores
     * Registra um novo professor (cria User + Professor)
     * @param request dados completos do professor
     * @return ResponseEntity com ProfessorResponse e status 201 (Created)
     */
    @PostMapping
    @Operation(summary = "Criar novo professor", description = "Registra um professor com seus dados de usuário")
    public ResponseEntity<ProfessorResponse> criar(@Valid @RequestBody CreateProfessorRequest request) {
        log.info("POST /v1/professores - Criando novo professor com email: {}", request.getEmail());

        ProfessorResponse response = professorService.registrarProfessor(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /v1/professores/buscar?nome=x
     * Busca professores por nome (contém)
     * @param nome o nome ou parte do nome para buscar
     * @return ResponseEntity com lista de ProfessorResponse e status 200 (OK)
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar professores por nome", description = "Retorna professores cujo nome contém o texto informado")
    public ResponseEntity<List<ProfessorResponse>> buscarPorNome(@RequestParam String nome) {
        log.info("GET /v1/professores/buscar?nome={} - Buscando professores", nome);

        List<ProfessorResponse> response = professorService.buscarPorNome(nome);

        return ResponseEntity.ok(response);
    }

    /**
     * GET /v1/professores/cref/{cref}
     * Busca professor por CREF
     * @param cref o CREF do professor
     * @return ResponseEntity com ProfessorResponse e status 200 (OK)
     */
    @GetMapping("/cref/{cref}")
    @Operation(summary = "Buscar professor por CREF", description = "Retorna um professor pelo CREF")
    public ResponseEntity<ProfessorResponse> buscarPorCref(@PathVariable String cref) {
        log.info("GET /v1/professores/cref/{} - Buscando professor por CREF", cref);

        ProfessorResponse response = professorService.buscarPorCref(cref);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar professor por CPF", description = "Retorna um professor pelo CPF")
    public ResponseEntity<ProfessorResponse> buscarPorCpf(@PathVariable String cpf) {
        log.info("GET /v1/professores/cpf/{} - Buscando professor por CPF", cpf);

        ProfessorResponse response = professorService.buscarPorCpf(cpf);

        return ResponseEntity.ok(response);
    }
    /**
     * PUT /v1/professores/{id}
     * Atualiza dados do professor
     * @param id o ID do professor
     * @param request novos dados do professor
     * @return ResponseEntity com ProfessorResponse e status 200 (OK)
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar professor", description = "Atualiza os dados de um professor existente")
    public ResponseEntity<ProfessorResponse> atualizar(@PathVariable Long id, @Valid @RequestBody UpdateProfessorRequest request) {
        log.info("PUT /v1/professores/{} - Atualizando professor", id);

        ProfessorResponse response = professorService.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /v1/professores/{id}
     * Deleta um professor
     * @param id o ID do professor
     * @return ResponseEntity com status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar professor", description = "Remove um professor do sistema")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("DELETE /v1/professores/{} - Deletando professor", id);

        professorService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}


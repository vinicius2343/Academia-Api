package com.academia.apiacademia.controller;

import com.academia.apiacademia.dto.input.CreateAdminRequest;
import com.academia.apiacademia.dto.input.UpdateAdminRequest;
import com.academia.apiacademia.dto.output.AdminResponse;
import com.academia.apiacademia.service.AdminService;
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
 * Controller REST para admins
 * Expõe endpoints para cadastro e gerenciamento de administradores
 */
@RestController
@RequestMapping("/v1/admins")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Admins", description = "Endpoints para gestão de administradores")
public class AdminController {

    private final AdminService adminService;

    /**
     * POST /v1/admins
     * Registra um novo admin
     * @param request dados completos do admin
     * @return ResponseEntity com UserResponse e status 201 (Created)
     */
    @PostMapping
    @Operation(summary = "Criar novo admin", description = "Registra um administrador no sistema")
    public ResponseEntity<AdminResponse> criar(@Valid @RequestBody CreateAdminRequest request) {
        log.info("POST /v1/admins - Criando novo admin com email: {}", request.getEmail());

        AdminResponse response = adminService.registrarAdmin(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /v1/admins/buscar?nome=x
     * Busca admins por nome (contém)
     * @param nome o nome ou parte do nome para buscar
     * @return ResponseEntity com lista de AdminResponse e status 200 (OK)
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar admins por nome", description = "Retorna admins cujo nome contém o texto informado")
    public ResponseEntity<List<AdminResponse>> buscarPorNome(@RequestParam String nome) {
        log.info("GET /v1/admins/buscar?nome={} - Buscando admins", nome);

        List<AdminResponse> response = adminService.buscarPorNome(nome);

        return ResponseEntity.ok(response);
    }

    /**
     * PUT /v1/admins/{id}
     * Atualiza dados do admin
     * @param id o ID do admin
     * @param request novos dados do admin
     * @return ResponseEntity com AdminResponse e status 200 (OK)
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar admin", description = "Atualiza os dados de um administrador existente")
    public ResponseEntity<AdminResponse> atualizar(@PathVariable Long id, @Valid @RequestBody UpdateAdminRequest request) {
        log.info("PUT /v1/admins/{} - Atualizando admin", id);

        AdminResponse response = adminService.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /v1/admins/{id}
     * Deleta um admin
     * @param id o ID do admin
     * @return ResponseEntity com status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar admin", description = "Remove um administrador do sistema")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("DELETE /v1/admins/{} - Deletando admin", id);

        adminService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}


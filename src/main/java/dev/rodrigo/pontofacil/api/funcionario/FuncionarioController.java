package dev.rodrigo.pontofacil.api.funcionario;

import dev.rodrigo.pontofacil.domain.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService service;

    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> listar(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(service.listar(usuario));
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponse> criar(@Valid @RequestBody FuncionarioRequest request,
                                                     @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.status(201).body(service.criar(request, usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> atualizar(@PathVariable Long id,
                                                         @Valid @RequestBody FuncionarioRequest request,
                                                         @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(service.atualizar(id, request, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id,
                                        @AuthenticationPrincipal Usuario usuario) {
        service.deletar(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
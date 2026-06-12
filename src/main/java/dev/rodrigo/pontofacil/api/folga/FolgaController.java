package dev.rodrigo.pontofacil.api.folga;

import dev.rodrigo.pontofacil.domain.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/folgas")
@RequiredArgsConstructor
public class FolgaController {

    private final FolgaService service;

    @GetMapping
    public ResponseEntity<List<FolgaResponse>> listar(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(service.listar(usuario));
    }

    @PostMapping
    public ResponseEntity<FolgaResponse> criar(@Valid @RequestBody FolgaRequest request,
                                               @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.status(201).body(service.criar(request, usuario));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FolgaResponse> atualizarStatus(@PathVariable Long id,
                                                         @RequestBody Map<String, String> body,
                                                         @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(service.atualizarStatus(id, body.get("status"), usuario));
    }
}

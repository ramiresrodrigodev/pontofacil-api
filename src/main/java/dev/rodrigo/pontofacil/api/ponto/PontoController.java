package dev.rodrigo.pontofacil.api.ponto;

import dev.rodrigo.pontofacil.domain.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pontos")
@RequiredArgsConstructor
public class PontoController {

    private final PontoService service;

    @GetMapping
    public ResponseEntity<List<PontoResponse>> listar(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(service.listar(usuario));
    }

    @PostMapping("/marcar")
    public ResponseEntity<PontoResponse> marcar(@Valid @RequestBody MarcarPontoRequest request,
                                                @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(service.marcar(request, usuario));
    }

    @PostMapping("/manual")
    public ResponseEntity<PontoResponse> criarManual(@Valid @RequestBody PontoManualRequest request,
                                                     @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.status(201).body(service.criarManual(request, usuario));
    }
}

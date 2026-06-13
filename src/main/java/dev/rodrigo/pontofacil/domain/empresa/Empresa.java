package dev.rodrigo.pontofacil.domain.empresa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    // ── Cerca virtual (geofence) para registro de ponto ──
    // Opcional: nulo = desativado. Quando definido, o ponto só pode ser
    // registrado dentro do raio (em metros) a partir deste ponto central.
    private Double latitude;

    private Double longitude;

    @Column(name = "raio_metros")
    private Integer raioMetros;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /** true se a empresa tem cerca virtual configurada. */
    public boolean temGeofence() {
        return latitude != null && longitude != null && raioMetros != null;
    }
}
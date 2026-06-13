package dev.rodrigo.pontofacil.domain.ponto;

import dev.rodrigo.pontofacil.domain.funcionario.Funcionario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "ponto")
public class Ponto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(nullable = false)
    private LocalDate data;

    private LocalTime entrada;

    @Column(name = "al_saida")
    private LocalTime alSaida;

    @Column(name = "al_retorno")
    private LocalTime alRetorno;

    private LocalTime saida;

    @Column(length = 20, nullable = false)
    private String status = "INCOMPLETO";

    private String observacao;

    // Localização capturada na batida (geofence)
    private Double latitude;

    private Double longitude;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
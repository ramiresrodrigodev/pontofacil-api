package dev.rodrigo.pontofacil.domain.folga;

import dev.rodrigo.pontofacil.domain.funcionario.Funcionario;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "folga")
public class Folga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(length = 30, nullable = false)
    private String tipo;

    @Column(nullable = false)
    private LocalDate inicio;

    private LocalDate fim;

    @Column(length = 20, nullable = false)
    private String status = "PENDENTE";

    private String observacao;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
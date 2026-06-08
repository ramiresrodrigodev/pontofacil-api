package dev.rodrigo.pontofacil.domain.funcionario;

import dev.rodrigo.pontofacil.domain.empresa.Empresa;
import dev.rodrigo.pontofacil.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100)
    private String cargo;

    @Column(length = 100)
    private String departamento;

    @Column(precision = 10, scale = 2)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Contrato contrato;

    @Column(length = 20)
    private String escala;

    @Column(length = 10, nullable = false)
    private String status = "ATIVO";

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
package dev.rodrigo.pontofacil.shared;

import dev.rodrigo.pontofacil.domain.empresa.Empresa;
import dev.rodrigo.pontofacil.domain.empresa.EmpresaRepository;
import dev.rodrigo.pontofacil.domain.folga.Folga;
import dev.rodrigo.pontofacil.domain.folga.FolgaRepository;
import dev.rodrigo.pontofacil.domain.funcionario.Contrato;
import dev.rodrigo.pontofacil.domain.funcionario.Funcionario;
import dev.rodrigo.pontofacil.domain.funcionario.FuncionarioRepository;
import dev.rodrigo.pontofacil.domain.ponto.Ponto;
import dev.rodrigo.pontofacil.domain.ponto.PontoRepository;
import dev.rodrigo.pontofacil.domain.usuario.Perfil;
import dev.rodrigo.pontofacil.domain.usuario.Usuario;
import dev.rodrigo.pontofacil.domain.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
public class DataSeeder implements CommandLineRunner {

    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final PontoRepository pontoRepository;
    private final FolgaRepository folgaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (empresaRepository.count() > 0) {
            log.info("Seed ignorado: banco já contém dados.");
            return;
        }

        Empresa empresa = new Empresa();
        empresa.setNome("Minha Empresa LTDA");
        empresa.setCnpj("12.345.678/0001-90");
        empresa = empresaRepository.save(empresa);

        Usuario gestor = new Usuario();
        gestor.setEmpresa(empresa);
        gestor.setNome("Gestor Demo");
        gestor.setEmail("gestor@pontofacil.com");
        gestor.setSenha(passwordEncoder.encode("123456"));
        gestor.setPerfil(Perfil.GESTOR);
        usuarioRepository.save(gestor);

        Funcionario ana = funcionario(empresa, "Ana Lima", "Desenvolvedora", "TI", "8500", Contrato.CLT, "ATIVO",
                "ana@empresa.com", "11 99999-0001", "2022-03-01", "5x2");
        funcionario(empresa, "Bruno Costa", "Designer", "Marketing", "6200", Contrato.CLT, "ATIVO",
                "bruno@empresa.com", "11 99999-0002", "2021-07-15", "5x2");
        Funcionario carla = funcionario(empresa, "Carla Souza", "Analista Financeiro", "Financeiro", "7800", Contrato.CLT, "ATIVO",
                "carla@empresa.com", "11 99999-0003", "2023-01-10", "6x1");
        Funcionario daniel = funcionario(empresa, "Daniel Rocha", "Estágio em TI", "TI", "1800", Contrato.ESTAGIO, "ATIVO",
                "daniel@empresa.com", "11 99999-0004", "2024-02-01", "5x2");
        funcionario(empresa, "Emília Nunes", "Consultora PJ", "Comercial", "12000", Contrato.PJ, "INATIVO",
                "emilia@empresa.com", "11 99999-0005", "2020-05-20", "12x36");

        // Pontos de exemplo no mês atual
        LocalDate base = LocalDate.now().withDayOfMonth(1);
        ponto(ana, base.plusDays(1), "08:02", "12:05", "13:10", "17:30");
        ponto(ana, base.plusDays(2), "08:15", "12:00", "13:05", "17:45");
        ponto(carla, base.plusDays(1), "07:55", "12:00", "13:00", "17:00");
        ponto(daniel, base.plusDays(2), "09:00", "12:30", "13:30", "15:00");

        // Folgas de exemplo
        folga(ana, "Férias", base.plusMonths(1), base.plusMonths(1).plusDays(14), "APROVADO", "Férias anuais");
        folga(carla, "Folga semanal", base.plusDays(6), base.plusDays(6), "APROVADO", "");
        folga(daniel, "Banco de horas", base.plusDays(4), base.plusDays(4), "PENDENTE", "Compensação horas extras");

        log.info("Seed concluído. Login: gestor@pontofacil.com / 123456");
    }

    private Funcionario funcionario(Empresa empresa, String nome, String cargo, String depto, String salario,
                                    Contrato contrato, String status, String email, String telefone,
                                    String admissao, String escala) {
        Funcionario f = new Funcionario();
        f.setEmpresa(empresa);
        f.setNome(nome);
        f.setCargo(cargo);
        f.setDepartamento(depto);
        f.setSalario(new BigDecimal(salario));
        f.setContrato(contrato);
        f.setStatus(status);
        f.setEmail(email);
        f.setTelefone(telefone);
        f.setDataAdmissao(LocalDate.parse(admissao));
        f.setEscala(escala);
        return funcionarioRepository.save(f);
    }

    private void ponto(Funcionario f, LocalDate data, String entrada, String alSaida, String alRetorno, String saida) {
        Ponto p = new Ponto();
        p.setFuncionario(f);
        p.setData(data);
        p.setEntrada(LocalTime.parse(entrada));
        p.setAlSaida(LocalTime.parse(alSaida));
        p.setAlRetorno(LocalTime.parse(alRetorno));
        p.setSaida(LocalTime.parse(saida));
        p.setStatus("COMPLETO");
        pontoRepository.save(p);
    }

    private void folga(Funcionario f, String tipo, LocalDate inicio, LocalDate fim, String status, String obs) {
        Folga folga = new Folga();
        folga.setFuncionario(f);
        folga.setTipo(tipo);
        folga.setInicio(inicio);
        folga.setFim(fim);
        folga.setStatus(status);
        folga.setObservacao(obs);
        folgaRepository.save(folga);
    }
}

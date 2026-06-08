CREATE TABLE empresa (
                         id         BIGSERIAL PRIMARY KEY,
                         nome       VARCHAR(100) NOT NULL,
                         cnpj       VARCHAR(18)  NOT NULL UNIQUE,
                         created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE usuario (
                         id         BIGSERIAL PRIMARY KEY,
                         empresa_id BIGINT       NOT NULL REFERENCES empresa(id),
                         nome       VARCHAR(100) NOT NULL,
                         email      VARCHAR(100) NOT NULL UNIQUE,
                         senha      VARCHAR(255) NOT NULL,
                         perfil     VARCHAR(20)  NOT NULL CHECK (perfil IN ('GESTOR','FUNCIONARIO')),
                         created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE funcionario (
                             id             BIGSERIAL PRIMARY KEY,
                             empresa_id     BIGINT      NOT NULL REFERENCES empresa(id),
                             usuario_id     BIGINT      REFERENCES usuario(id),
                             nome           VARCHAR(100) NOT NULL,
                             cargo          VARCHAR(100),
                             departamento   VARCHAR(100),
                             salario        NUMERIC(10,2),
                             contrato       VARCHAR(20) CHECK (contrato IN ('CLT','PJ','ESTAGIO')),
                             escala         VARCHAR(20),
                             status         VARCHAR(10) NOT NULL DEFAULT 'ATIVO',
                             data_admissao  DATE,
                             email          VARCHAR(100),
                             telefone       VARCHAR(20),
                             created_at     TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE TABLE ponto (
                       id            BIGSERIAL PRIMARY KEY,
                       funcionario_id BIGINT    NOT NULL REFERENCES funcionario(id),
                       data          DATE       NOT NULL,
                       entrada       TIME,
                       al_saida      TIME,
                       al_retorno    TIME,
                       saida         TIME,
                       status        VARCHAR(20) NOT NULL DEFAULT 'INCOMPLETO',
                       observacao    TEXT,
                       created_at    TIMESTAMP  NOT NULL DEFAULT NOW()
);

CREATE TABLE folga (
                       id             BIGSERIAL PRIMARY KEY,
                       funcionario_id BIGINT     NOT NULL REFERENCES funcionario(id),
                       tipo           VARCHAR(30) NOT NULL,
                       inicio         DATE        NOT NULL,
                       fim            DATE,
                       status         VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
                       observacao     TEXT,
                       created_at     TIMESTAMP   NOT NULL DEFAULT NOW()
);
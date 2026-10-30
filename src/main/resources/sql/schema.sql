CREATE TABLE IF NOT EXISTS usuarios (
    id uuid NOT NULL  DEFAULT uuid_generate_v4(),
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha_hash VARCHAR(64) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS funcionarios (
    id uuid NOT NULL  DEFAULT uuid_generate_v4(),
    nome VARCHAR(150) NOT NULL,
    data_admissao DATE NOT NULL,
    salario NUMERIC(12, 2) NOT NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

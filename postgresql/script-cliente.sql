CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    cpf VARCHAR(11),
    data_nasc DATE,
    email VARCHAR(255),
    ativo BOOLEAN
);

CREATE TABLE IF NOT EXISTS enderecos (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(255),
    logradouro VARCHAR(255),
    numero VARCHAR(255),
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    cep VARCHAR(8),
    cidade VARCHAR(255),
    estado VARCHAR(255),
    pais VARCHAR(255),
    ativo BOOLEAN,
    id_cliente INT REFERENCES clientes(id)
);

CREATE TABLE IF NOT EXISTS telefones (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(255),
    ddd INTEGER,
    numero VARCHAR(255),
    ativo BOOLEAN,
    id_cliente INT REFERENCES clientes(id)
);
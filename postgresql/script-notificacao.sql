CREATE TABLE IF NOT EXISTS restricoes (
    id SERIAL PRIMARY KEY,
    data_restricao DATE,
    cpf_cliente VARCHAR(255),
    nome_cliente VARCHAR(255),
    id_debito BIGINT,
    email VARCHAR(255),
    descricao VARCHAR(255),
    emissor VARCHAR(255),
    valor_debito NUMERIC(10, 2)
);

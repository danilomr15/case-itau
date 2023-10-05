CREATE TABLE IF NOT EXISTS debitos (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(255),
    cpf_cliente VARCHAR(255),
    email_cliente VARCHAR(255),
    nome_cliente VARCHAR(255),
    valor_debito NUMERIC(10, 2),
    situacao VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS cobrancas (
    id SERIAL PRIMARY KEY,
    data_cobranca TIMESTAMP,
    mensagem TEXT,
    email VARCHAR(255),
    id_debito BIGINT,
    FOREIGN KEY (id_debito) REFERENCES debitos(id)
);

CREATE TABLE IF NOT EXISTS pagamentos (
    id SERIAL PRIMARY KEY,
    valor_pagto NUMERIC(10, 2),
    liquidacao BOOLEAN,
    data_pagamento TIMESTAMP,
    id_debito BIGINT,
    FOREIGN KEY (id_debito) REFERENCES debitos(id)
);

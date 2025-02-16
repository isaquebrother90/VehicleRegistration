CREATE TABLE veiculo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    veiculo VARCHAR(255) NOT NULL,
    marca VARCHAR(255) NOT NULL,
    ano INT NOT NULL,
    descricao VARCHAR(255),
    vendido BOOLEAN NOT NULL,
    created TIMESTAMP,
    updated TIMESTAMP
);
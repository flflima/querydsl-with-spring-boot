CREATE TABLE endereco(
    id serial NOT NULL PRIMARY KEY,
    logradouro VARCHAR(50),
    bairro VARCHAR(25),
    cidade VARCHAR(25),
    estado VARCHAR(15)
);

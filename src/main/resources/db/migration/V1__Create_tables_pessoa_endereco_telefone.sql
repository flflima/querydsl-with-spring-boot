CREATE TABLE pessoa(
    id serial NOT NULL PRIMARY KEY,
    nome varchar(100) NOT NULL
);

CREATE TABLE endereco(
    id serial NOT NULL PRIMARY KEY,
    logradouro VARCHAR(50),
    bairro VARCHAR(25),
    cidade VARCHAR(25),
    estado VARCHAR(15)
);

CREATE TABLE telefone(
    id serial NOT NULL PRIMARY KEY,
    ddd VARCHAR(2),
    numero VARCHAR(10)
);

ALTER TABLE endereco ADD COLUMN pessoa_id serial;

ALTER TABLE endereco
ADD CONSTRAINT endereco_pessoa_fk FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE telefone ADD COLUMN pessoa_id serial;

ALTER TABLE telefone
ADD CONSTRAINT telefone_pessoa_fk FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

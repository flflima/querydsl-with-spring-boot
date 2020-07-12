INSERT INTO pessoa (id, nome) VALUES (1, 'Maria');
INSERT INTO pessoa (id, nome) VALUES (2, 'Joao');
INSERT INTO pessoa (id, nome) VALUES (3, 'Pedro');
INSERT INTO pessoa (id, nome) VALUES (4, 'Ana');

INSERT INTO endereco (logradouro, bairro, cidade, estado, pessoa_id) VALUES ('Rua 01', 'Bairro 01', 'Cidade 01',
'Estado 01', 1);
INSERT INTO endereco (logradouro, bairro, cidade, estado, pessoa_id) VALUES ('Rua 02', 'Bairro 02', 'Cidade 02',
'Estado 02', 2);
INSERT INTO endereco (logradouro, bairro, cidade, estado, pessoa_id) VALUES ('Rua 03', 'Bairro 03', 'Cidade 03',
'Estado 03', 3);
INSERT INTO endereco (logradouro, bairro, cidade, estado, pessoa_id) VALUES ('Rua 04', 'Bairro 04', 'Cidade 04',
'Estado 04', 4);

INSERT INTO telefone (ddd, numero, pessoa_id) VALUES ('11', '99999999', 1);
INSERT INTO telefone (ddd, numero, pessoa_id) VALUES ('12', '99999999', 2);
INSERT INTO telefone (ddd, numero, pessoa_id) VALUES ('13', '99999999', 3);
INSERT INTO telefone (ddd, numero, pessoa_id) VALUES ('14', '99999999', 4);

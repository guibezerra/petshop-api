DROP TABLE IF EXISTS atendimento;

DROP TABLE IF EXISTS pet;

DROP TABLE IF EXISTS raca;

DROP TABLE IF EXISTS contato;

DROP TABLE IF EXISTS endereco;

DROP TABLE IF EXISTS cliente;

DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    id_usuario BIGSERIAL NOT NULL,
    cpf varchar(14) NOT NULL,
    nome varchar(50) NOT NULL,
    perfil varchar(10) NOT NULL,
    senha varchar(65) NOT NULL,

    PRIMARY KEY (id_usuario)
);

CREATE TABLE cliente(
    id_cliente BIGSERIAL NOT NULL,
    id_usuario BIGSERIAL NOT NULL,
    data_de_cadastro date NOT NULL,

    PRIMARY KEY (id_cliente),
    FOREIGN KEY (id_usuario) REFERENCES usuario
);

CREATE TABLE endereco(
     id_endereco BIGSERIAL NOT NULL,
     id_cliente BIGSERIAL NOT NULL,
     logradouro varchar(60) NOT NULL,
     cidade varchar(30)NOT NULL,
     bairro varchar(30) NOT NULL,
     complemento varchar(40),
     tag varchar(20),

     PRIMARY KEY (id_endereco),
     FOREIGN KEY (id_cliente) REFERENCES cliente
);

CREATE TABLE contato(
    id_contato BIGSERIAL NOT NULL,
    id_cliente BIGSERIAL NOT NULL,
    tag varchar(11) NOT NULL,
    tipo varchar(9)	NOT NULL,
    valor varchar(40) NOT NULL,

    PRIMARY KEY (id_contato),
    FOREIGN KEY (id_cliente) REFERENCES cliente
);

CREATE TABLE raca(
 id_raca BIGSERIAL NOT NULL,
 descricao varchar(30) NOT NULL,

 PRIMARY KEY (id_raca)
);

CREATE TABLE pet(
id_pet BIGSERIAL NOT NULL,
id_cliente BIGSERIAL NOT NULL,
id_raca BIGSERIAL NOT NULL,
data_de_nascimento date,
nome varchar(50) NOT NULL,

PRIMARY KEY (id_pet),
FOREIGN KEY (id_cliente) REFERENCES cliente,
FOREIGN KEY (id_raca) REFERENCES raca
);

CREATE TABLE atendimento(
id_atendimento BIGSERIAL NOT NULL,
id_pet BIGSERIAL NOT NULL,
descricao_do_atendimento varchar(60) NOT NULL,
valor FLOAT NOT NULL,
data_do_atendimento date NOT NULL,

PRIMARY KEY (id_atendimento),
FOREIGN KEY (id_pet) REFERENCES pet
);


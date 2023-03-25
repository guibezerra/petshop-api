
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
                         cpf varchar(14)NOT NULL,
                         nome varchar(50) NOT NULL,
                         perfil varchar(7),
                         senha varchar(8) NOT NULL,
                         PRIMARY KEY (cpf)
);

DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente(
                        id_cliente BIGINT NOT NULL,
                        nome varchar(50) NOT NULL,
                        cpf varchar(14)NOT NULL,
                        data_de_cadastro date NOT NULL,

                        PRIMARY KEY (id_cliente),
                        FOREIGN KEY (cpf) REFERENCES usuario
);

DROP TABLE IF EXISTS endereco;

CREATE TABLE endereco(
                         id_endereco BIGINT NOT NULL,
                         id_cliente BIGINT NOT NULL,
                         logradouro varchar(60) NOT NULL,
                         cidade varchar(30)NOT NULL,
                         bairro varchar(30) NOT NULL,
                         complemento varchar(40) NOT NULL,
                         tag varchar(20),

                         PRIMARY KEY (id_endereco),
                         FOREIGN KEY (id_cliente) REFERENCES cliente
);

DROP TABLE IF EXISTS contato;

CREATE TABLE contato(
                        id_contato BIGINT NOT NULL,
                        id_cliente BIGINT NOT NULL,
                        tag varchar(11) NOT NULL,
                        tipo varchar(9)	NOT NULL,
                        valor varchar(40) NOT NULL,

                        PRIMARY KEY (id_contato),
                        FOREIGN KEY (id_cliente) REFERENCES cliente
);

DROP TABLE IF EXISTS raca;

CREATE TABLE raca(
                     id_raca BIGINT NOT NULL,
                     descricao varchar(30) NOT NULL,

                     PRIMARY KEY (id_raca)
);

DROP TABLE IF EXISTS pet;

CREATE TABLE pet(
                    id_pet BIGINT NOT NULL,
                    id_cliente BIGINT NOT NULL,
                    id_raca BIGINT NOT NULL,
                    data_de_nascimento date,
                    nome varchar(50) NOT NULL,

                    PRIMARY KEY (id_pet),
                    FOREIGN KEY (id_cliente) REFERENCES cliente,
                    FOREIGN KEY (id_raca) REFERENCES raca
);

DROP TABLE IF EXISTS atendimento;

CREATE TABLE atendimento(
                            id_atendimento BIGINT NOT NULL,
                            id_pet BIGINT NOT NULL,
                            descricao_do_atendimento varchar(60) NOT NULL,
                            valor FLOAT,
                            data_do_atendimento date NOT NULL,

                            PRIMARY KEY (id_atendimento),
                            FOREIGN KEY (id_pet) REFERENCES pet
);


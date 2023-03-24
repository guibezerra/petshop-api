
DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
`id_usuario` bigint NOT NULL AUTO_INCREMENT,
`cpf` varchar(14)NOT NULL,
`nome` varchar(50) NOT NULL,
`senha` varchar(8) NOT NULL,
PRIMARY KEY (`id_usuario`),
);
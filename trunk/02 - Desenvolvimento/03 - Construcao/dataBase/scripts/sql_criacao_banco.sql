/*===============================================Cria Banco de dados===========================================================*/

DROP DATABASE IF EXISTS bd_control_cheque;
CREATE DATABASE bd_control_cheque;

/*===============================================Cria Tabelas===================================================================*/

/*======TABELA BANCO==============*/
DROP TABLE IF EXISTS `bd_control_cheque`.`tb_banco`;
CREATE TABLE  `bd_control_cheque`.`tb_banco` (
  `idBanco` varchar(36) NOT NULL DEFAULT '',
  `codigoBanco` varchar(3) NOT NULL DEFAULT '',
  `descricaoBanco` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`idBanco`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*===============TABELA CLIENTE===================================*/
DROP TABLE IF EXISTS `bd_control_cheque`.`tb_cliente`;
CREATE TABLE  `bd_control_cheque`.`tb_cliente` (
  `idCliente` varchar(36) NOT NULL DEFAULT '',
  `codigoCliente` varchar(10) NOT NULL DEFAULT '',
  `nomeCliente` varchar(100) NOT NULL DEFAULT '',
  `telefoneCliente` varchar(20) NOT NULL DEFAULT '',
  `ruaCliente` varchar(100) DEFAULT NULL,
  `bairroCliente` varchar(45) DEFAULT NULL,
  `cidadeCliente` varchar(45) DEFAULT NULL,
  `complementoCliente` varchar(10) DEFAULT NULL,
  `numeroCliente` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*===============TABELA USUARIO==================================*/
DROP TABLE IF EXISTS `bd_control_cheque`.`tb_usuario`;
CREATE TABLE  `bd_control_cheque`.`tb_usuario` (
  `idUsuario` varchar(36) NOT NULL,
  `loginUsuario` varchar(100) DEFAULT NULL,
  `senhaUsuario` varchar(10) DEFAULT NULL,
  `nivelAcessoUsuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `idUsuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*========TABELA CHEQUE======================*/
DROP TABLE IF EXISTS `bd_control_cheque`.`tb_cheque`;
CREATE TABLE  `bd_control_cheque`.`tb_cheque` (
  `idBanco` varchar(36) NOT NULL DEFAULT '',
  `idCheque` varchar(36) NOT NULL,
  `contaBanco` varchar(15) DEFAULT NULL,
  `agenciaBanco` varchar(20) DEFAULT NULL,
  `numeroCheque` varchar(15) DEFAULT NULL,
  `titularCheque` varchar(200) DEFAULT NULL,
  `cpfTitular` varchar(15) DEFAULT NULL,
  `dataBomPara` date DEFAULT NULL,
  `telefoneTitular` varchar(50) DEFAULT NULL,
  `dataEmissao` date DEFAULT NULL,
  `obsCheque` text,
  `situacaoCheque` int(11) NOT NULL,
  `valorCheque` float DEFAULT NULL,
  `recebiDe` varchar(200) DEFAULT NULL,
  `passeiPara` varchar(200) DEFAULT NULL,
  `statusCheque` int(11) NOT NULL,
  `idUsuario` varchar(36) DEFAULT NULL,
  `codigoCheque` varchar(45) NOT NULL DEFAULT '',
  PRIMARY KEY (`idCheque`),
  UNIQUE KEY `idCheque` (`idCheque`),
  KEY `idBanco` (`idBanco`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `FK_tb_cheque_tb_banco` FOREIGN KEY (`idBanco`) REFERENCES `tb_banco` (`idBanco`),
  CONSTRAINT `FK_tb_cheque_tb_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `tb_usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
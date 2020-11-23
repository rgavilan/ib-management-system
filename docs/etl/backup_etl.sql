-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.3.27-MariaDB-1:10.3.27+maria~focal - mariadb.org binary distribution
-- SO del servidor:              debian-linux-gnu
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para etl
CREATE DATABASE IF NOT EXISTS `etl` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `etl`;

-- Volcando estructura para tabla etl.Control
CREATE TABLE IF NOT EXISTS `Control` (
  `End_Flag` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`End_Flag`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.Control: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `Control` DISABLE KEYS */;
/*!40000 ALTER TABLE `Control` ENABLE KEYS */;

-- Volcando estructura para tabla etl.ControlCarga
CREATE TABLE IF NOT EXISTS `ControlCarga` (
  `idCarga` int(11) NOT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  `fechaModificacion` datetime DEFAULT NULL,
  `IdEjecucion` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCarga`) USING BTREE,
  KEY `CONTROL_TABLAS_FK` (`IdEjecucion`),
  CONSTRAINT `CONTROL_TABLAS_FK` FOREIGN KEY (`IdEjecucion`) REFERENCES `ControlEjecucion` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.ControlCarga: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `ControlCarga` DISABLE KEYS */;
/*!40000 ALTER TABLE `ControlCarga` ENABLE KEYS */;

-- Volcando estructura para tabla etl.ControlEjecucion
CREATE TABLE IF NOT EXISTS `ControlEjecucion` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `FechaInicio` datetime DEFAULT NULL,
  `FechaFin` datetime DEFAULT NULL,
  `IdEstado` int(11) NOT NULL,
  `IdVersion` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `RUN_PROCESS_FK` (`IdEstado`),
  KEY `CONTROL_PROCESO_FK` (`IdVersion`),
  CONSTRAINT `CONTROL_PROCESO_FK` FOREIGN KEY (`IdVersion`) REFERENCES `ControlVersion` (`Id`),
  CONSTRAINT `RUN_PROCESS_FK` FOREIGN KEY (`IdEstado`) REFERENCES `ControlEjecucionEstado` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.ControlEjecucion: ~51 rows (aproximadamente)
/*!40000 ALTER TABLE `ControlEjecucion` DISABLE KEYS */;
INSERT INTO `ControlEjecucion` (`Id`, `FechaInicio`, `FechaFin`, `IdEstado`, `IdVersion`) VALUES
	(87, '2020-10-30 14:07:20', NULL, 1, 3),
	(88, '2020-10-30 14:15:11', '2020-10-30 14:17:25', 2, 3),
	(89, '2020-10-30 14:34:51', '2020-10-30 14:38:09', 2, 3),
	(90, '2020-10-30 14:45:21', '2020-10-30 14:46:47', 2, 3),
	(91, '2020-10-30 14:49:55', '2020-10-30 14:51:15', 2, 3),
	(92, '2020-10-30 14:55:55', '2020-10-30 14:57:10', 2, 3),
	(93, '2020-11-10 08:55:50', '2020-11-10 08:56:57', 2, 3),
	(94, '2020-11-10 08:58:21', '2020-11-10 08:59:27', 2, 3),
	(95, '2020-11-10 09:08:15', '2020-11-10 09:09:52', 2, 3),
	(96, '2020-11-10 16:34:28', '2020-11-10 16:36:07', 2, 3),
	(97, '2020-11-11 17:25:57', '2020-11-11 17:27:32', 2, 3),
	(98, '2020-11-12 13:42:45', '2020-11-12 13:44:39', 2, 3),
	(99, '2020-11-12 17:35:35', '2020-11-12 17:37:17', 2, 3),
	(100, '2020-11-12 17:44:12', '2020-11-12 17:45:52', 2, 3),
	(101, '2020-11-13 13:58:50', '2020-11-13 13:59:08', 2, 3),
	(102, '2020-11-13 14:32:10', '2020-11-13 14:32:28', 2, 3),
	(103, '2020-11-13 14:42:32', '2020-11-13 14:42:50', 2, 3),
	(104, '2020-11-18 08:56:03', '2020-11-18 08:56:21', 2, 3),
	(105, '2020-11-18 08:57:34', '2020-11-18 08:57:51', 2, 3),
	(106, '2020-11-18 08:58:32', '2020-11-18 08:58:50', 2, 3),
	(107, '2020-11-18 08:59:50', '2020-11-18 09:00:08', 2, 3),
	(108, '2020-11-18 09:03:37', '2020-11-18 09:03:55', 2, 3),
	(109, '2020-11-18 11:10:44', '2020-11-18 11:11:01', 2, 3),
	(110, '2020-11-18 12:36:01', '2020-11-18 12:36:20', 2, 3),
	(111, '2020-11-19 15:27:44', '2020-11-19 15:28:06', 3, 3),
	(112, '2020-11-19 15:30:29', '2020-11-19 15:30:53', 2, 3),
	(113, '2020-11-19 16:48:40', NULL, 1, 3),
	(114, '2020-11-19 16:49:49', '2020-11-19 16:50:03', 2, 3),
	(115, '2020-11-19 16:53:24', '2020-11-19 16:53:38', 2, 3),
	(116, '2020-11-19 16:57:55', '2020-11-19 16:58:09', 2, 3),
	(117, '2020-11-19 16:59:58', '2020-11-19 17:00:11', 2, 3),
	(118, '2020-11-19 17:12:15', '2020-11-19 17:12:28', 2, 3),
	(119, '2020-11-19 17:13:12', '2020-11-19 17:13:25', 2, 3),
	(120, '2020-11-19 17:15:13', '2020-11-19 17:15:26', 2, 3),
	(121, '2020-11-20 13:51:26', '2020-11-20 13:51:45', 2, 3),
	(122, '2020-11-20 14:28:53', '2020-11-20 14:29:21', 2, 3),
	(123, '2020-11-20 14:30:05', '2020-11-20 14:30:32', 2, 3),
	(124, '2020-11-20 14:30:54', '2020-11-20 14:31:21', 2, 3),
	(125, '2020-11-23 10:07:28', '2020-11-23 10:07:36', 3, 3),
	(126, '2020-11-23 10:08:52', '2020-11-23 10:08:59', 3, 3),
	(127, '2020-11-23 10:09:45', '2020-11-23 10:09:52', 3, 3),
	(128, '2020-11-23 10:12:19', '2020-11-23 10:12:26', 3, 3),
	(129, '2020-11-23 10:18:20', '2020-11-23 10:18:27', 3, 3),
	(130, '2020-11-23 10:19:11', '2020-11-23 10:19:18', 3, 3),
	(131, '2020-11-23 10:20:25', '2020-11-23 10:20:32', 3, 3),
	(132, '2020-11-23 10:23:43', '2020-11-23 10:23:50', 3, 3),
	(133, '2020-11-23 10:24:04', '2020-11-23 10:24:11', 3, 3),
	(134, '2020-11-23 10:26:27', '2020-11-23 10:26:34', 3, 3),
	(135, '2020-11-23 10:31:36', '2020-11-23 10:32:04', 2, 3),
	(136, '2020-11-23 10:35:39', '2020-11-23 10:36:06', 2, 3),
	(137, '2020-11-23 10:37:11', '2020-11-23 10:37:38', 2, 3);
/*!40000 ALTER TABLE `ControlEjecucion` ENABLE KEYS */;

-- Volcando estructura para tabla etl.ControlEjecucionEstado
CREATE TABLE IF NOT EXISTS `ControlEjecucionEstado` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.ControlEjecucionEstado: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `ControlEjecucionEstado` DISABLE KEYS */;
INSERT INTO `ControlEjecucionEstado` (`Id`, `Nombre`) VALUES
	(1, 'Running'),
	(2, 'Successfully completed'),
	(3, 'Failed');
/*!40000 ALTER TABLE `ControlEjecucionEstado` ENABLE KEYS */;

-- Volcando estructura para tabla etl.ControlEnvio
CREATE TABLE IF NOT EXISTS `ControlEnvio` (
  `Entity` varchar(50) DEFAULT NULL,
  `Status` varchar(50) DEFAULT NULL,
  `Read` bigint(20) DEFAULT NULL,
  `Written` bigint(20) DEFAULT NULL,
  `Updated` bigint(20) DEFAULT NULL,
  `Input` bigint(20) DEFAULT NULL,
  `Output` bigint(20) DEFAULT NULL,
  `Rejected` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.ControlEnvio: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `ControlEnvio` DISABLE KEYS */;
/*!40000 ALTER TABLE `ControlEnvio` ENABLE KEYS */;

-- Volcando estructura para tabla etl.ControlErrores
CREATE TABLE IF NOT EXISTS `ControlErrores` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Fecha` datetime DEFAULT NULL,
  `IdEjecucion` int(11) NOT NULL,
  `IdTipoError` int(11) NOT NULL,
  `Mensaje` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `CONTROL_ERRORS_FK` (`IdEjecucion`),
  KEY `CONTROL_ERRORS_FK_1` (`IdTipoError`),
  CONSTRAINT `CONTROL_ERRORS_FK` FOREIGN KEY (`IdEjecucion`) REFERENCES `ControlEjecucion` (`Id`),
  CONSTRAINT `CONTROL_ERRORS_FK_1` FOREIGN KEY (`IdTipoError`) REFERENCES `ControlTipoError` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.ControlErrores: ~7 rows (aproximadamente)
/*!40000 ALTER TABLE `ControlErrores` DISABLE KEYS */;
INSERT INTO `ControlErrores` (`Id`, `Fecha`, `IdEjecucion`, `IdTipoError`, `Mensaje`) VALUES
	(1, '2020-11-20 13:51:31', 121, 5, 'Han habido errores en la carga de los Proyectos'),
	(2, '2020-11-20 14:28:59', 122, 5, 'Han habido errores en la carga de los Proyectos'),
	(3, '2020-11-20 14:30:10', 123, 5, 'Han habido errores en la carga de los Proyectos'),
	(4, '2020-11-20 14:31:00', 124, 5, 'Han habido errores en la carga de los Proyectos'),
	(5, '2020-11-23 10:31:41', 135, 5, 'Han habido errores en la carga de los Proyectos'),
	(6, '2020-11-23 10:35:45', 136, 5, 'Han habido errores en la carga de los Proyectos'),
	(7, '2020-11-23 10:37:16', 137, 5, 'Han habido errores en la carga de los Proyectos');
/*!40000 ALTER TABLE `ControlErrores` ENABLE KEYS */;

-- Volcando estructura para tabla etl.ControlTipoError
CREATE TABLE IF NOT EXISTS `ControlTipoError` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.ControlTipoError: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `ControlTipoError` DISABLE KEYS */;
INSERT INTO `ControlTipoError` (`Id`, `Descripcion`) VALUES
	(1, 'No se ha podido encontrar el fichero de parámetros "config.properties".'),
	(2, 'No se ha podido establecer conexión con la base de datos origen.'),
	(3, 'No se ha podido establecer conexión con la base de datos destino.'),
	(4, 'No se ha podido establecer conexión con el servicio de la cola de mensajes de kafka.'),
	(5, 'Ha habido algún error en la inserción/actualización/eliminación de algún registro en una de las tablas destino. Por favor, revisar la tabla de ControlErrores para solucionar el problema en la próxima ejecución.');
/*!40000 ALTER TABLE `ControlTipoError` ENABLE KEYS */;

-- Volcando estructura para tabla etl.ControlVersion
CREATE TABLE IF NOT EXISTS `ControlVersion` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Fecha` datetime DEFAULT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Descripcion` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.ControlVersion: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `ControlVersion` DISABLE KEYS */;
INSERT INTO `ControlVersion` (`Id`, `Fecha`, `Nombre`, `Descripcion`) VALUES
	(3, '2020-10-30 14:07:19', '1.0', 'Proyecto ETL Version 1.0. Aplicacion ETL: Pentaho Data Integration. Version de la aplicacion:9.0');
/*!40000 ALTER TABLE `ControlVersion` ENABLE KEYS */;

-- Volcando estructura para tabla etl.EmpresaExplotacionPatente
CREATE TABLE IF NOT EXISTS `EmpresaExplotacionPatente` (
  `id` int(11) DEFAULT NULL,
  `idPatente` int(11) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `observaciones` varchar(50) DEFAULT NULL,
  `fechaInicioPeriodo` datetime DEFAULT NULL,
  `fechaFinPeriodo` datetime DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  `fechaModificacion` datetime DEFAULT NULL,
  `operation` varchar(50) DEFAULT NULL,
  `idEjecucion` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.EmpresaExplotacionPatente: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `EmpresaExplotacionPatente` DISABLE KEYS */;
INSERT INTO `EmpresaExplotacionPatente` (`id`, `idPatente`, `numero`, `observaciones`, `fechaInicioPeriodo`, `fechaFinPeriodo`, `fechaCreacion`, `fechaModificacion`, `operation`, `idEjecucion`) VALUES
	(143235, 39, 1, 'CONTRATO GINVES 3003', '2001-01-01 00:00:00', '2004-01-01 00:00:00', '2020-11-19 17:00:05', '2020-11-23 10:37:21', 'INSERT', 137),
	(147522, 39, 3, 'CONTRATO GINVES 4065', '2005-03-08 00:00:00', '2008-03-08 00:00:00', '2020-11-19 17:00:05', '2020-11-23 10:37:21', 'INSERT', 137),
	(150430, 12, 1, 'CONTRATO GINVES 3941', '2004-04-05 00:00:00', '2015-01-23 00:00:00', '2020-11-19 17:00:05', '2020-11-23 10:37:21', 'INSERT', 137),
	(152054, 39, 4, 'CONTRATO GINVES 6631', '2011-09-12 00:00:00', '2012-09-12 00:00:00', '2020-11-19 17:00:05', '2020-11-23 10:37:21', 'INSERT', 137),
	(153698, 1, 1, 'CONTRATO GINVES 3744', '2003-01-24 00:00:00', '2013-01-23 00:00:00', '2020-11-19 17:00:05', '2020-11-23 10:37:21', 'INSERT', 137);
/*!40000 ALTER TABLE `EmpresaExplotacionPatente` ENABLE KEYS */;

-- Volcando estructura para tabla etl.FacturaProyecto
CREATE TABLE IF NOT EXISTS `FacturaProyecto` (
  `id` varchar(50) DEFAULT NULL,
  `operation` varchar(50) DEFAULT NULL,
  `idProyecto` int(11) DEFAULT NULL,
  `numeroFacturaPrevista` int(11) DEFAULT NULL,
  `FechaCreacion` datetime DEFAULT NULL,
  `FechaModificacion` datetime DEFAULT NULL,
  `IdEjecucion` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.FacturaProyecto: ~30 rows (aproximadamente)
/*!40000 ALTER TABLE `FacturaProyecto` DISABLE KEYS */;
INSERT INTO `FacturaProyecto` (`id`, `operation`, `idProyecto`, `numeroFacturaPrevista`, `FechaCreacion`, `FechaModificacion`, `IdEjecucion`) VALUES
	('FEM-J-2012-129/2', 'INSERT', 15057, 2, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-164/2', 'INSERT', 15086, 2, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-169/2', 'INSERT', 15087, 2, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-173/1', 'INSERT', 15100, 1, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-212/3', 'INSERT', 15063, 3, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-239/4', 'INSERT', 15063, 4, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-260/3', 'INSERT', 15087, 3, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-306/2', 'INSERT', 15115, 1, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-326/2', 'INSERT', 15058, 2, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-388/5', 'INSERT', 15063, 5, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-418/3', 'INSERT', 15057, 3, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-42/1', 'INSERT', 15057, 1, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-45/1', 'INSERT', 15058, 1, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-453/6', 'INSERT', 15063, 6, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-466/7', 'INSERT', 15063, 7, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-483/3', 'INSERT', 15086, 3, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-49/1', 'INSERT', 15063, 1, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-518/2', 'INSERT', 15120, 2, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-57/1', 'INSERT', 15087, 1, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-58/1', 'INSERT', 15086, 1, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-67/1', 'INSERT', 15059, 1, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-74/1', 'INSERT', 15117, 1, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-78/1', 'INSERT', 15115, 1, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-79/1', 'INSERT', 15120, 1, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2012-97/2', 'INSERT', 15063, 2, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2013-225/3', 'INSERT', 15058, 3, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2013-226/4', 'INSERT', 15086, 4, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2013-278/3', 'INSERT', 15115, 2, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2013-587/4', 'INSERT', 15058, 4, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137),
	('FEM-J-2014-490/5', 'INSERT', 15058, 5, '2020-11-23 10:37:17', '2020-11-23 10:37:17', 137);
/*!40000 ALTER TABLE `FacturaProyecto` ENABLE KEYS */;

-- Volcando estructura para tabla etl.GrupoInvestigacion
CREATE TABLE IF NOT EXISTS `GrupoInvestigacion` (
  `id` varchar(15) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `idUniversidad` int(11) NOT NULL,
  `idEjecucion` int(11) DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  `fechaModificacion` datetime DEFAULT NULL,
  `operation` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.GrupoInvestigacion: ~33 rows (aproximadamente)
/*!40000 ALTER TABLE `GrupoInvestigacion` DISABLE KEYS */;
INSERT INTO `GrupoInvestigacion` (`id`, `nombre`, `idUniversidad`, `idEjecucion`, `fechaCreacion`, `fechaModificacion`, `operation`) VALUES
	('E002-01', 'HISTOLOGIA Y ANATOMIA PATOLOGICA VETERINARIA', 1, 137, '2020-11-18 12:36:05', '2020-11-23 10:37:23', 'INSERT'),
	('E002-02', 'ANATOMIA Y EMBRIOLOGIA VETERINARIAS', 1, 137, '2020-11-18 12:36:05', '2020-11-23 10:37:23', 'INSERT'),
	('E002-05', 'INMUNOHISTOPATOLOGÍA VETERINARIA', 1, 110, '2020-11-18 12:36:05', '2020-11-18 12:36:05', 'INSERT'),
	('E005-01', 'PLANTAS BIOFACTORÍA: PRODUCCIÓN DE COMPUESTOS BIOACTIVOS Y PROTEINAS RELACIONADAS CON LA DEFENSA VEGETAL', 1, 110, '2020-11-18 12:36:05', '2020-11-18 12:36:05', 'INSERT'),
	('E005-02', 'BIOTECNOLOGIA VEGETAL Y FITOQUIMICA', 1, 110, '2020-11-18 12:36:05', '2020-11-18 12:36:05', 'INSERT'),
	('E005-06', 'FITOHORMONAS Y DESARROLLO VEGETAL', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E005-07', 'BIOLOGIA, ECOLOGIA Y EVOLUCION DE BRIÓFITOS Y ESPERMATÓFITOS', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E017-08', 'EJERCICIO FÍSICO Y SALUD (EFISAL)', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E020-04', 'LINGUISTICA APLICADA (INTERLINGUISTICA L1-L1)', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E023-01', 'GRUPO DE TRADUCCIÓN, LEXICOLOGÍA Y ESCRITURAS', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E024-03', 'MATERIA CONDENSADA', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E028-01', 'ARQUITECTURA Y ARTE CIVIL BARROCOS', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E041-02', 'ARQUEOLOGIA', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E043-04', 'PSICOLOGIA SOCIAL', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E046-03', 'QUIMICA ORGANOMETALICA', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E047-01', 'QUIMICA DE CARBOHIDRATOS, POLÍMEROS Y ADITIVOS INDUSTRIALES-QCPAI', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E050-01', 'POLITICAS COMPARADAS DE LA EDUCACION', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E050-03', 'EDUCACION, HISTORIA Y SOCIEDAD', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E051-01', 'SISTEMAS PRODUCTIVOS, COOPERACIÓN Y DESARROLLO', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E051-04', 'SISTEMA FINANCIERO Y ECONOMIA MONETARIA', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E069-07', 'PERSONALIDAD Y SALUD: UNA PERSPECTIVA INTERCULTURAL Y DE GÉNERO [PERSONALITY AND HEALTH: A INTERCULTURAL AND GENDER P.]', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E070-01', 'DERECHO INTERNACIONAL', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E079-01', 'CARACTERIZACION ORDENACION Y CLASIFICACION DE DISTRIBUCIONES', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E080-05', 'ODONTOPEDIATRÍA, ODONTOLOG. PREVENTIVA, ODONTOLOG. CONSERVADORA', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E091-03', 'NÓESIS', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E091-07', 'FILOSOFÍA Y MUNDO CONTEMPORÁNEO', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E094-04', 'REGULACION A LARGO PLAZO DE LA FUNCION RENAL Y PRESION ARTERIAL', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E096-03', 'TECNOLOGÍAS DE MODELADO, PROCESAMIENTO Y GESTIÓN DEL CONOCIMIENTO (TECNOMOD)', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E097-04', 'COMPUTACIÓN MÓVIL Y VISIÓN ARTIFICIAL', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E0A5-03', 'CAMBIOS AMBIENTALES,TRANSFORMACION DEL PAISAJE Y ORDENACION DEL TERRITORIO', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('E0A5-07', 'INVESTIGACIÓN Y MODELIZACIÓN DE PROCESOS HIDROLÓGICOS Y AMBIENTALES EN MEDIOS SEMIÁRIDOS (IMPRHAS)', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('GERM15-E036-03', 'GEOMETRÍA DIFERENCIAL Y CONVEXA', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT'),
	('GERM15-E0A2-02', 'FILOGENIA Y EVOLUCIÓN ANIMAL', 1, 137, '2020-11-19 15:27:52', '2020-11-23 10:37:23', 'INSERT');
/*!40000 ALTER TABLE `GrupoInvestigacion` ENABLE KEYS */;

-- Volcando estructura para tabla etl.InventorPatente
CREATE TABLE IF NOT EXISTS `InventorPatente` (
  `id` int(11) DEFAULT NULL,
  `idPatente` int(11) DEFAULT NULL,
  `cedNombre` varchar(80) DEFAULT NULL,
  `depNombre` varchar(80) DEFAULT NULL,
  `inventorPrincipal` varchar(50) DEFAULT NULL,
  `idEjecucion` int(11) DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  `fechaModificacion` datetime DEFAULT NULL,
  `Operation` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.InventorPatente: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `InventorPatente` DISABLE KEYS */;
INSERT INTO `InventorPatente` (`id`, `idPatente`, `cedNombre`, `depNombre`, `inventorPrincipal`, `idEjecucion`, `fechaCreacion`, `fechaModificacion`, `Operation`) VALUES
	(1062, 12, 'FACULTAD DE MEDICINA', 'DERMATOLOGÍA, ESTOMATOLOGÍA, RADIOLOGÍA Y MEDICINA FÍSICA', 'S', 137, '2020-11-19 16:57:59', '2020-11-23 10:37:19', 'INSERT'),
	(2075, 13, 'FACULTAD DE INFORMÁTICA', 'INGENIERÍA DE LA INFORMACIÓN Y LAS COMUNICACIONES', 'S', 137, '2020-11-19 16:57:59', '2020-11-23 10:37:19', 'INSERT'),
	(5180, 13, NULL, NULL, 'N', 137, '2020-11-19 16:57:59', '2020-11-23 10:37:19', 'INSERT'),
	(39931, 13, NULL, NULL, 'N', 137, '2020-11-19 16:57:59', '2020-11-23 10:37:19', 'INSERT'),
	(136957, 1, NULL, NULL, 'N', 137, '2020-11-19 16:57:59', '2020-11-23 10:37:19', 'INSERT');
/*!40000 ALTER TABLE `InventorPatente` ENABLE KEYS */;

-- Volcando estructura para tabla etl.Patente
CREATE TABLE IF NOT EXISTS `Patente` (
  `id` int(11) DEFAULT NULL,
  `idEjecucion` int(11) DEFAULT NULL,
  `operation` varchar(50) DEFAULT NULL,
  `fechaConcesionPatente` datetime DEFAULT NULL,
  `FechaExpiracionPatente` datetime DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  `fechaModificacion` datetime DEFAULT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.Patente: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `Patente` DISABLE KEYS */;
INSERT INTO `Patente` (`id`, `idEjecucion`, `operation`, `fechaConcesionPatente`, `FechaExpiracionPatente`, `fechaCreacion`, `fechaModificacion`, `titulo`, `tipo`) VALUES
	(1, 137, 'INSERT', NULL, NULL, '2020-11-19 16:57:58', '2020-11-23 10:37:18', 'PROGRAMA DE ORDENADOR PARA LA APLICACION DE LA ISO 9000 EN CENTROS DE HEMODONACIÓN', 'D'),
	(9, 137, 'INSERT', NULL, NULL, '2020-11-19 16:57:58', '2020-11-23 10:37:18', 'P-MMAN PREVENTIVE MAINTENANCE MANAGEMENT PROGRAM V1.0', 'D'),
	(12, 137, 'INSERT', '1997-05-15 00:00:00', NULL, '2020-11-19 16:57:58', '2020-11-23 10:37:18', 'DISPOSITIVO PARA REALIZAR RADIOGRAFIAS PANORAMICAS DE MUÑECA', 'P'),
	(13, 137, 'INSERT', NULL, NULL, '2020-11-19 16:57:58', '2020-11-23 10:37:18', 'CIRCADIANWARE', 'D'),
	(39, 137, 'INSERT', NULL, NULL, '2020-11-19 16:57:58', '2020-11-23 10:37:18', 'DISPOSITIVO Y MÉTODO PARA INTRODUCIR YO RECOGER FLUIDOS EN EL INTERIOR DEL ÚTERO DE UN ANIMAL', 'P');
/*!40000 ALTER TABLE `Patente` ENABLE KEYS */;

-- Volcando estructura para tabla etl.Proyecto
CREATE TABLE IF NOT EXISTS `Proyecto` (
  `id` int(11) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `tipo` varchar(15) DEFAULT NULL,
  `cerrado` date DEFAULT NULL,
  `idGrupoInvestigacion` varchar(15) DEFAULT NULL,
  `idEjecucion` int(11) DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  `fechaModificacion` datetime DEFAULT NULL,
  `operation` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.Proyecto: ~30 rows (aproximadamente)
/*!40000 ALTER TABLE `Proyecto` DISABLE KEYS */;
INSERT INTO `Proyecto` (`id`, `nombre`, `descripcion`, `tipo`, `cerrado`, `idGrupoInvestigacion`, `idEjecucion`, `fechaCreacion`, `fechaModificacion`, `operation`) VALUES
	(15067, 'SEMINARIO DE ESTUDIOS DE LAS MUJERES Y GÉNERO: CONVESATORIOS SOBRE MUJERES Y GÉNERO-CONVERSAÇOES SOBRE MULHERES E GÊNERO', 'SEMINARIO DE ESTUDIOS DE LAS MUJERES Y GÉNERO: CONVESATORIOS SOBRE MUJERES Y GÉNERO-CONVERSAÇOES SOBRE MULHERES E GÊNERO', 'AYUDA', '2012-05-29', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15069, 'CURSO DE VERANO DE LA UMU: LA IGUALDAD COMO EJE DE UN NUEVO MODELO ECONÓMICO Y SOCIAL SOSTENIBLE', 'CURSO DE VERANO DE LA UMU: LA IGUALDAD COMO EJE DE UN NUEVO MODELO ECONÓMICO Y SOCIAL SOSTENIBLE', 'AYUDA', '2012-07-31', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15068, 'WORKSHOP  EN POLÍTICAS PÚBLICAS Y GÉNERO', 'WORKSHOP  EN POLÍTICAS PÚBLICAS Y GÉNERO', 'AYUDA', '2012-12-14', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15062, 'ESTRATEGIA MULTIMARCADOR COMO HERRAMIENTA PRONOSTICA EN LA FIBRILACIÓN AURICULAR NO VALVULAR BAJO TRATAMIENTO ANTICOAGULANTE ORAL', 'ESTRATEGIA MULTIMARCADOR COMO HERRAMIENTA PRONOSTICA EN LA FIBRILACIÓN AURICULAR NO VALVULAR BAJO TRATAMIENTO ANTICOAGULANTE ORAL', 'AYUDA', '2014-12-31', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15053, 'PROYECTO TÚ DECIDES. LA UNIVERSIDAD DE MURCIA INFORMA Y ORIENTA.', 'PROYECTO TÚ DECIDES. LA UNIVERSIDAD DE MURCIA INFORMA Y ORIENTA.', 'AYUDA', '2012-12-31', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15060, 'DIGITUM ( DEPOSITO DIGITAL INSTITUCIONAL EN ACCESO ABIERTO DE LA UNIVERSIDAD DE MURCIA): BIBLIOTECA DIGITAL FLORIDABLANCA, IMÁGENES LIBRORUM Y TESIS DOCTORALES', 'DIGITUM ( DEPOSITO DIGITAL INSTITUCIONAL EN ACCESO ABIERTO DE LA UNIVERSIDAD DE MURCIA): BIBLIOTECA DIGITAL FLORIDABLANCA, IMÁGENES LIBRORUM Y TESIS D', 'AYUDA', '2012-09-23', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15061, 'SATIETY INNOVATION', 'SATIETY INNOVATION-SATIN', 'AYUDA', '2016-12-31', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15066, 'PROJECT ON CRISIS COMMUNICATION IN THE AREA OF RISK MANAGEMENT', 'PROJECT ON CRISIS COMMUNICATION IN THE AREA OF RISK MANAGEMENT-CRICORM', 'AYUDA', '2015-05-31', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15057, 'FUNCIONES PROPIAS DEL CO-COMISARIADO DE LA EXPOSICIÓN 100 AÑOS EN FEMENINO', 'CONTRATO ARTÍCULO 83', 'CON', '2012-08-30', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15058, 'APOYO A LA REALIZACIÓN DE TRABAJOS RELACIONADOS CON LA ORGANIZACIÓN DE EMPRESAS', 'CONTRATO ARTÍCULO 83', 'CON', '2013-04-25', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15059, 'MODELO PLASTINADO PARA EL ENTRENAMIENTO EN ENTEROSCOPIA', 'CONTRATO ARTÍCULO 83', 'CON', '2013-01-25', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15063, 'CURSOS EN DIAGNÓSTICO ECOGRÁFICO AVANZADO EN PEQUEÑOS ANIMALES', 'CONTRATO ARTÍCULO 83', 'CON', '2012-10-01', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15086, 'PROYECTO DE INVESTIGACIÓN Y DESARROLLO EN VALORIZACIÓN DE RESIDUOS VEGETALES COMO INDUCTOR DE RESISTENCIA A PATÓGENOS', 'CONTRATO ARTÍCULO 83', 'CON', '2013-10-11', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15070, 'FP00-354', 'FP00-354', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15072, 'FP00-355', 'FP00-355', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15073, 'FP00-356', 'FP00-356', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15074, 'FP00-357', 'FP00-357', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15075, 'FP00-358', 'FP00-358', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15076, 'FP00-359', 'FP00-359', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15077, 'FP00-360', 'FP00-360', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15078, 'FP00-361', 'FP00-361', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15079, 'FP00-362', 'FP00-362', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15080, 'FP00-363', 'FP00-363', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15081, 'FP00-364', 'FP00-364', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15082, 'FP00-365', 'FP00-365', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15083, 'FP00-366', 'FP00-366', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15084, 'FP00-367', 'FP00-367', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15085, 'FP00-368', 'FP00-368', 'FP00', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15056, 'MOVILIDAD SENIOR - CARMEN MATAS PARRA', 'MOVILIDAD SENIOR - CARMEN MATAS PARRA', 'RRHH', '2012-03-31', NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT'),
	(15054, 'IV  TERAPÉUTICA HOMEOPÁTICA.', 'IV  TERAPÉUTICA HOMEOPÁTICA.', 'SPE2', NULL, NULL, 137, '2020-11-18 12:50:07', '2020-11-23 10:37:14', 'INSERT');
/*!40000 ALTER TABLE `Proyecto` ENABLE KEYS */;

-- Volcando estructura para tabla etl.Universidad
CREATE TABLE IF NOT EXISTS `Universidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `idEjecucion` int(11) DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  `fechaModificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla etl.Universidad: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `Universidad` DISABLE KEYS */;
INSERT INTO `Universidad` (`id`, `nombre`, `idEjecucion`, `fechaCreacion`, `fechaModificacion`) VALUES
	(1, 'Universidad de Murcia', 137, '2020-10-30 14:08:10', '2020-11-23 10:37:22'),
	(2, 'Universidad de la Vida', 137, '2020-11-05 13:01:16', '2020-11-23 10:37:22'),
	(3, 'Universidad de Oviedo', 137, '2020-11-05 13:01:16', '2020-11-23 10:37:22');
/*!40000 ALTER TABLE `Universidad` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

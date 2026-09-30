/*
SQLyog Community
MySQL - 9.4.0 : Database - ibcsecurity
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `comunisecurity` */

CREATE TABLE `comunisecurity` (
  `ALIAS` varchar(20) NOT NULL,
  `DESCRIZIONE` varchar(100) DEFAULT NULL COMMENT 'Indica la descrizione per l''ente installato. Es. Comune di Gubbio.',
  `IDCOMUNE` varchar(6) DEFAULT NULL COMMENT 'E'' l''idcomune che identifica parte della chiave delle tabelle di SIGePro, non necessariamente e'' un codice belfiore (o catastale).',
  `ATTIVO` decimal(1,0) DEFAULT NULL COMMENT '0=non attivo, 1=attivo',
  `DBUSER` varchar(25) DEFAULT NULL COMMENT 'E'' utente per l''accesso alla base dati.',
  `DBPWD` varchar(50) DEFAULT NULL COMMENT 'E'' la password per l''accesso alla base dati. La password e'' criptata.',
  `OWNER` varchar(50) DEFAULT NULL COMMENT 'E'' l''OWNER delle tabelle del database.',
  `DBAGGIORNATO` decimal(1,0) DEFAULT NULL COMMENT '0=non aggiornato, 1=aggiornato',
  `IDCOMUNE_LOC` varchar(6) DEFAULT NULL,
  `URL_VERIFICA_UTENTE` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`ALIAS`),
  UNIQUE KEY `COMUNISECURITY_PK` (`ALIAS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `comunisecurity_app` */

CREATE TABLE `comunisecurity_app` (
  `APPLICATION` varchar(25) NOT NULL,
  `PASSWORD` varchar(50) DEFAULT NULL COMMENT 'La password e'' criptata con algoritmo MD5',
  `FK_ALIAS` varchar(20) DEFAULT NULL COMMENT 'Se null allora l''utente applicativo puo'' accedere a qualsiasi alias della tabella COMUNISECURITY altrimenti puo'' fare loginAPP solo per l''alias specificato. ',
  `ADMIN` decimal(1,0) DEFAULT NULL COMMENT '1 = accede alle interfacce di gestione di SigeproSecurity, 0 = non accede',
  PRIMARY KEY (`APPLICATION`),
  UNIQUE KEY `COMUNISECURITY_APP_PK` (`APPLICATION`),
  KEY `FK_COMUNIAPP_COMUNIS` (`FK_ALIAS`),
  CONSTRAINT `FK_COMUNIAPP_COMUNIS` FOREIGN KEY (`FK_ALIAS`) REFERENCES `comunisecurity` (`ALIAS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `comunisecurity_connection` */

CREATE TABLE `comunisecurity_connection` (
  `FK_ALIAS` varchar(20) NOT NULL,
  `AMBIENTE` varchar(20) NOT NULL,
  `CONNECTIONSTRING` varchar(1000) DEFAULT NULL COMMENT 'La stringa di connessione alla base dati',
  `PROVIDER` varchar(20) DEFAULT NULL COMMENT 'Il provider utilizzato',
  `OVERRIDE_CONF` decimal(1,0) DEFAULT '0',
  `DBUSER` varchar(25) DEFAULT NULL,
  `DBPWD` varchar(50) DEFAULT NULL,
  `OWNER` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`FK_ALIAS`,`AMBIENTE`),
  UNIQUE KEY `COMUNISECURITY_CONNECTION_PK` (`FK_ALIAS`,`AMBIENTE`),
  CONSTRAINT `FK_COMUNICONNECTION_COMUNIS` FOREIGN KEY (`FK_ALIAS`) REFERENCES `comunisecurity` (`ALIAS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `comunisecurity_param` */

CREATE TABLE `comunisecurity_param` (
  `PARAM` varchar(35) NOT NULL,
  `VALUE` varchar(200) DEFAULT NULL,
  `NOTE` varchar(500) DEFAULT NULL COMMENT 'Sono delle note sul significato del parametro.',
  PRIMARY KEY (`PARAM`),
  UNIQUE KEY `COMUNISECURITY_PARAM_PK` (`PARAM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `comunisecurity_sess_metadati` */

CREATE TABLE `comunisecurity_sess_metadati` (
  `token` varchar(50) NOT NULL,
  `chiave` varchar(125) NOT NULL,
  `valore` longtext,
  PRIMARY KEY (`token`,`chiave`),
  CONSTRAINT `FK_TOKEN_SESS_METADATI` FOREIGN KEY (`token`) REFERENCES `comunisecurity_session` (`TOKEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `comunisecurity_session` */

CREATE TABLE `comunisecurity_session` (
  `TOKEN` varchar(50) NOT NULL,
  `CONTESTO` varchar(4) NOT NULL,
  `CLIENT_IP` varchar(100) DEFAULT NULL COMMENT 'E'' l''indirizzo IP del client dal quale e'' stata effettuata la chiamata',
  `ALIAS` varchar(20) DEFAULT NULL COMMENT 'E'' l''alias per cui e'' stato richiesto il token',
  `IDCOMUNE` varchar(20) DEFAULT NULL COMMENT 'E'' l''identificativo della colonna idcomune in sigepro',
  `FIRSTREQUEST` date DEFAULT NULL COMMENT 'E'' la data ora di accensione del token',
  `LASTREQUEST` varchar(250) DEFAULT NULL COMMENT 'E'' la data ora dell''ultima richiesta di verifica del token',
  `USERID` varchar(250) DEFAULT NULL COMMENT 'E'' l''utente/applicazione che ha richiesto  il token',
  `VALID` decimal(1,0) NOT NULL,
  `TOKEN_PARTNER_APP` longtext,
  `AUTH_LEVEL` decimal(1,0) DEFAULT NULL COMMENT '0=ANONIMO,1=NON_IDENTIFICATO,2=IDENTIFICATO,3=FORTE',
  `IDCOMUNE_LOC` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`TOKEN`),
  UNIQUE KEY `TOKENINFO_PK` (`TOKEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `comunisecurity_tpartnerapp` */

CREATE TABLE `comunisecurity_tpartnerapp` (
  `ID` decimal(10,0) NOT NULL,
  `TOKEN` varchar(50) NOT NULL,
  `CODICECOMUNE` varchar(10) DEFAULT NULL,
  `SOFTWARE` varchar(10) DEFAULT NULL,
  `TOKENPARTNERAPP` longtext,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PK_COMUNISECURITY_TPARTNERAPP` (`ID`),
  KEY `IDX_CSECURITYPARTNERAPP_001` (`TOKEN`),
  CONSTRAINT `FK_CSECTPARTNERAPP_SESSION` FOREIGN KEY (`TOKEN`) REFERENCES `comunisecurity_session` (`TOKEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `id_table` */

CREATE TABLE `id_table` (
  `ID` varchar(30) NOT NULL,
  `NEXT_ID` decimal(10,0) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_TABLE_PK` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `versione` */

CREATE TABLE `versione` (
  `VERSIONE` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

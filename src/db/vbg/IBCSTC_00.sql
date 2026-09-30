/*
SQLyog Community
MySQL - 9.4.0 : Database - ibcstc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `attivita` */

CREATE TABLE `attivita` (
  `ID` decimal(8,0) NOT NULL,
  `FKIDPRATICHE` decimal(8,0) DEFAULT NULL,
  `IDATTIVITA` varchar(160) DEFAULT NULL,
  `DATAATTIVITA` date DEFAULT NULL,
  `NUMPROTGEN` varchar(50) DEFAULT NULL,
  `DATAPROTGEN` date DEFAULT NULL,
  `TIPOATTIVITA` varchar(150) DEFAULT NULL,
  `DATASISTEMA` date DEFAULT NULL,
  `IDPROCEDIMENTO` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ATTIVITA_PK` (`ID`),
  KEY `ATTIVITA_PRATICHE_1` (`FKIDPRATICHE`),
  CONSTRAINT `ATTIVITA_PRATICHE_1` FOREIGN KEY (`FKIDPRATICHE`) REFERENCES `pratiche` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `configurazione` */

CREATE TABLE `configurazione` (
  `IDNODO` decimal(8,0) NOT NULL,
  `WSURL` varchar(150) DEFAULT NULL,
  `USERID` varchar(30) DEFAULT NULL,
  `PASSWORD` varchar(30) DEFAULT NULL,
  `IDDIREZIONE` varchar(10) DEFAULT NULL,
  `DIREZIONE` varchar(50) DEFAULT NULL,
  `DESCRIZIONE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`IDNODO`),
  UNIQUE KEY `CONFIGURAZIONE_PK` (`IDNODO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `id_table` */

CREATE TABLE `id_table` (
  `ID` varchar(30) NOT NULL,
  `NEXT_ID` decimal(9,0) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_TABLE_PK` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `messaggiattivita` */

CREATE TABLE `messaggiattivita` (
  `ID` decimal(8,0) NOT NULL,
  `FKIDRICHIESTA` decimal(8,0) DEFAULT NULL,
  `FKIDRISPOSTA` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MESSAGGIATTIVITA_PK` (`ID`),
  KEY `MESSAGGIATTIVITA_ATTIVITA_FK1` (`FKIDRICHIESTA`),
  KEY `MESSAGGIATTIVITA_ATTIVITA_FK2` (`FKIDRISPOSTA`),
  CONSTRAINT `MESSAGGIATTIVITA_ATTIVITA_FK1` FOREIGN KEY (`FKIDRICHIESTA`) REFERENCES `attivita` (`ID`),
  CONSTRAINT `MESSAGGIATTIVITA_ATTIVITA_FK2` FOREIGN KEY (`FKIDRISPOSTA`) REFERENCES `attivita` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `messaggipratiche` */

CREATE TABLE `messaggipratiche` (
  `ID` decimal(8,0) NOT NULL,
  `FKIDRICHIESTA` decimal(8,0) DEFAULT NULL,
  `FKIDRISPOSTA` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MESSAGGIPRATICHE_PK` (`ID`),
  KEY `MESSAGGIPRATICHE_PRATICHE_FK1` (`FKIDRICHIESTA`),
  KEY `MESSAGGIPRATICHE_PRATICHE_FK2` (`FKIDRISPOSTA`),
  CONSTRAINT `MESSAGGIPRATICHE_PRATICHE_FK1` FOREIGN KEY (`FKIDRICHIESTA`) REFERENCES `pratiche` (`ID`),
  CONSTRAINT `MESSAGGIPRATICHE_PRATICHE_FK2` FOREIGN KEY (`FKIDRISPOSTA`) REFERENCES `pratiche` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `pratiche` */

CREATE TABLE `pratiche` (
  `ID` decimal(8,0) NOT NULL,
  `IDENTE` varchar(20) DEFAULT NULL,
  `IDSPORTELLO` varchar(30) DEFAULT NULL,
  `IDPRATICA` varchar(150) DEFAULT NULL,
  `NUMPRATICA` varchar(150) DEFAULT NULL,
  `DATAPRATICA` date DEFAULT NULL,
  `NUMPROTGEN` varchar(50) DEFAULT NULL,
  `DATAPROTGEN` date DEFAULT NULL,
  `FKIDNODO` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PRATICHE_PK` (`ID`),
  KEY `PRATICHE_INDEX1` (`IDENTE`,`IDSPORTELLO`,`IDPRATICA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sicurezza` */

CREATE TABLE `sicurezza` (
  `ID` decimal(8,0) NOT NULL,
  `TOKEN` varchar(32) NOT NULL,
  `SCADENZA` date DEFAULT NULL,
  `FKIDNODO` decimal(8,0) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SICUREZZA_PK` (`ID`),
  KEY `FK_SICUREZZA_CONFIGURAZIONE` (`FKIDNODO`),
  CONSTRAINT `FK_SICUREZZA_CONFIGURAZIONE` FOREIGN KEY (`FKIDNODO`) REFERENCES `configurazione` (`IDNODO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `versione` */

CREATE TABLE `versione` (
  `VERSIONE` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `vw_messaggiattivita` */

DROP TABLE IF EXISTS `vw_messaggiattivita`;

/*!50001 CREATE TABLE  `vw_messaggiattivita`(
 `ID` decimal(8,0) ,
 `MITT_IDNODO` decimal(8,0) ,
 `MITT_IDENTE` varchar(20) ,
 `MITT_IDSPORTELLO` varchar(30) ,
 `MITT_IDPRATICA` varchar(150) ,
 `MITT_NUMPRATICA` varchar(150) ,
 `MITT_ATT_ID` varchar(160) ,
 `MITT_ATT_TIPO` varchar(150) ,
 `MITT_ATT_IDPROC` varchar(30) ,
 `MITT_ATT_DATA` date ,
 `DEST_IDNODO` decimal(8,0) ,
 `DEST_IDENTE` varchar(20) ,
 `DEST_IDSPORTELLO` varchar(30) ,
 `DEST_IDPRATICA` varchar(150) ,
 `DEST_NUMPRATICA` varchar(150) ,
 `DEST_ATT_ID` varchar(160) ,
 `DEST_ATT_TIPO` varchar(150) ,
 `DEST_ATT_IDPROC` varchar(30) ,
 `DEST_ATT_DATA` date 
)*/;

/*Table structure for table `vw_messaggipratiche` */

DROP TABLE IF EXISTS `vw_messaggipratiche`;

/*!50001 CREATE TABLE  `vw_messaggipratiche`(
 `ID` decimal(8,0) ,
 `MITT_IDNODO` decimal(8,0) ,
 `MITT_IDENTE` varchar(20) ,
 `MITT_IDSPORTELLO` varchar(30) ,
 `MITT_IDPRATICA` varchar(150) ,
 `MITT_NUMPRATICA` varchar(150) ,
 `MITT_DATAPRATICA` date ,
 `DEST_IDNODO` decimal(8,0) ,
 `DEST_IDENTE` varchar(20) ,
 `DEST_IDSPORTELLO` varchar(30) ,
 `DEST_IDPRATICA` varchar(150) ,
 `DEST_NUMPRATICA` varchar(150) ,
 `DEST_DATAPRATICA` date 
)*/;

/*View structure for view vw_messaggiattivita */

/*!50001 DROP TABLE IF EXISTS `vw_messaggiattivita` */;
/*!50001 CREATE ALGORITHM=TEMPTABLE SQL SECURITY DEFINER VIEW `vw_messaggiattivita` AS select `messaggiattivita`.`ID` AS `ID`,`richieste`.`FKIDNODO` AS `MITT_IDNODO`,`richieste`.`IDENTE` AS `MITT_IDENTE`,`richieste`.`IDSPORTELLO` AS `MITT_IDSPORTELLO`,`richieste`.`IDPRATICA` AS `MITT_IDPRATICA`,`richieste`.`NUMPRATICA` AS `MITT_NUMPRATICA`,`att_richieste`.`IDATTIVITA` AS `MITT_ATT_ID`,`att_richieste`.`TIPOATTIVITA` AS `MITT_ATT_TIPO`,`att_richieste`.`IDPROCEDIMENTO` AS `MITT_ATT_IDPROC`,`att_richieste`.`DATAATTIVITA` AS `MITT_ATT_DATA`,`risposte`.`FKIDNODO` AS `DEST_IDNODO`,`risposte`.`IDENTE` AS `DEST_IDENTE`,`risposte`.`IDSPORTELLO` AS `DEST_IDSPORTELLO`,`risposte`.`IDPRATICA` AS `DEST_IDPRATICA`,`risposte`.`NUMPRATICA` AS `DEST_NUMPRATICA`,`att_risposte`.`IDATTIVITA` AS `DEST_ATT_ID`,`att_risposte`.`TIPOATTIVITA` AS `DEST_ATT_TIPO`,`att_risposte`.`IDPROCEDIMENTO` AS `DEST_ATT_IDPROC`,`att_risposte`.`DATAATTIVITA` AS `DEST_ATT_DATA` from ((((`messaggiattivita` join `attivita` `att_richieste` on((`att_richieste`.`ID` = `messaggiattivita`.`FKIDRICHIESTA`))) join `pratiche` `richieste` on((`richieste`.`ID` = `att_richieste`.`FKIDPRATICHE`))) join `attivita` `att_risposte` on((`att_risposte`.`ID` = `messaggiattivita`.`FKIDRISPOSTA`))) join `pratiche` `risposte` on((`risposte`.`ID` = `att_risposte`.`FKIDPRATICHE`))) order by `messaggiattivita`.`ID` */;

/*View structure for view vw_messaggipratiche */

/*!50001 DROP TABLE IF EXISTS `vw_messaggipratiche` */;
/*!50001 CREATE ALGORITHM=TEMPTABLE SQL SECURITY DEFINER VIEW `vw_messaggipratiche` AS select `messaggipratiche`.`ID` AS `ID`,`richieste`.`FKIDNODO` AS `MITT_IDNODO`,`richieste`.`IDENTE` AS `MITT_IDENTE`,`richieste`.`IDSPORTELLO` AS `MITT_IDSPORTELLO`,`richieste`.`IDPRATICA` AS `MITT_IDPRATICA`,`richieste`.`NUMPRATICA` AS `MITT_NUMPRATICA`,`richieste`.`DATAPRATICA` AS `MITT_DATAPRATICA`,`risposte`.`FKIDNODO` AS `DEST_IDNODO`,`risposte`.`IDENTE` AS `DEST_IDENTE`,`risposte`.`IDSPORTELLO` AS `DEST_IDSPORTELLO`,`risposte`.`IDPRATICA` AS `DEST_IDPRATICA`,`risposte`.`NUMPRATICA` AS `DEST_NUMPRATICA`,`risposte`.`DATAPRATICA` AS `DEST_DATAPRATICA` from ((`messaggipratiche` join `pratiche` `richieste` on((`richieste`.`ID` = `messaggipratiche`.`FKIDRICHIESTA`))) join `pratiche` `risposte` on((`risposte`.`ID` = `messaggipratiche`.`FKIDRISPOSTA`))) order by `messaggipratiche`.`ID` */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

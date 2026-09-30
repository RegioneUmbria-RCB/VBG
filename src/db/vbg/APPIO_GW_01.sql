/*
SQLyog Community
MySQL - 9.4.0 : Database - appio_gw
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Data for the table `stato_messaggio` */

insert  into `stato_messaggio`(`ID`,`NOME`) values 
(1,'PRESO_IN_CARICO'),
(2,'CONSEGNATO'),
(3,'GET_200'),
(4,'ERRORE'),
(5,'SENDER_NOT_ALLOWED'),
(6,'RITENTA_INVIO');

/*Data for the table `tipo_connettore` */

insert  into `tipo_connettore`(`ID`,`NOME`) values 
(2,'IOITALIA'),
(1,'UD');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

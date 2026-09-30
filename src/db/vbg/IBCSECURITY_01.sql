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


insert into `comunisecurity_param` (`param`, `value`, `note`) values('APP_AR_JAVA',NULL,'Nome dell\'applicazione area riservata java. Es. http://backoffice.sigepro.it/areariservata è la composizione dei parametri BASE_URL + APP_AR_JAVA BASE_URL=\"http://backoffice.sigepro.it/\" APP_AR_JAVA=\"areariservata\"');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('APP_ASP',NULL,'Nome dell\'applicazione asp. Es. http://backoffice.sigepro.it/backoffice è la composizione dei parametri BASE_URL + APP_ASP BASE_URL=\"http://backoffice.sigepro.it/\" APP_ASP=\"backoffice\" Es: BACKOFFICE');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('APP_ASPNET',NULL,'Nome dell\'applicazione asp.net. Es. http://backoffice.sigepro.it/aspnet è la composizione dei parametri BASE_URL + APP_ASPNET BASE_URL=\"http://backoffice.sigepro.it/\" APP_JAVA=\"aspnet\" Es: aspnet');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('APP_JAVA',NULL,'Nome dell\'applicazione java. Es. http://backoffice.sigepro.it/jback è la composizione dei parametri BASE_URL + APP_JAVA BASE_URL=\"http://backoffice.sigepro.it/\" APP_JAVA=\"jback\" Es: sigepro2');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('AUDIT_SERVICE_URL',NULL,'Url del servizio di auditing');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('AUTHENTICATION_GATEWAY_FO_URL',NULL,'Url del servizio di autenticazione \"debole\" per il front-end, viene configurato con \"AuthenticationGateway/login\" per l\'autenticazione con utente e password.');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('AUTHENTICATION_GATEWAY_FO_URLCIE',NULL,'Url del servizio di autenticazione \"forte\" per il front-end, viene configurato con \"AuthenticationGateway/logincie\" per un\'autenticazione con smart card, con \"AuthenticationGateway/loginefed\" per l\'autenticazione forte con FED Umbria e con \"AuthenticationGateway/loginefedera\" per l\'autenticazione forte con FEDERA Emilia Romagna.');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('AUTHENTICATION_GATEWAY_URL',NULL,'Url del servizio di autenticazione per il back-end, viene configurato con \"AuthenticationGateway/login\" se l\'autenticazione avviene con utente e password o se l\'AuthenticationGateway è configurato per LDAP e con \"AuthenticationGateway/loginefed\" per l\'autenticazione forte con FED Umbria.');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('BACKEND_URL_VISURA',NULL,'Indica la URL da invocare per fare una visura di una pratica del gestionale');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('BASE_URL',NULL,'E\' l\'url (dominio) dal quale si \"accede\" alla piattaforma SIGePro back office. L\'url deve essere utilizzabile dai client che si collegano alla piattaforma SIGePro. Può essere nullo, se non indicato le applicazioni asp.net e java dovranno recuperare tale informazione dalla richiesta http del browser e scorporarne il nome dell\'applicazione \"rappresentata\" APP_JAVA o APP_ASPNET. Es: http://devel3.init.gruppoinit.it');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('CHECK_TOKEN_TIMEOUT',NULL,'Time out per eseguire la check token (espresso in minuti). 0=esegui sempre, X=esegui ogni X minuti');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('MAIL.LOGINNAME',NULL,'Invio Mail. Utente di default (se non specificato nella tabella mail_config)');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('MAIL.MAILSERVER',NULL,'Invio Mail. Server da utilizzare per l\'invio delle mail (se non specificato nella tabella mail_config)');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('MAIL.PASSWORD',NULL,'Invio Mail. Password di default (se non specificato nella tabella mail_config)');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('MAIL.SENDER',NULL,'Invio Mail. Mittente dei messaggi di posta elettronica (se non specificato in mail_config)');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('MAIL.SMTP_PORT',NULL,'Invio Mail. Porta da utilizzare per l\'invio delle maill (se non specificato nella tabella mail_config)');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('MAIL.USE_AUTHENTICATION',NULL,'Invio Mail. 1 = la mail viene inviata tramite autenticazione, 0= la mail viene inviata senza autenticazione (se non specificato nella tabella mail_config)');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('MAIL.USE_SSL',NULL,'Invio Mail. 1 = La mail viene inviata utilizzando ssl, 0= la mail viene inviata senza ssl (se non specificato nella tabella mail_config)');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('NODOPAGAMENTI_URL_VISURA',NULL,'Indica la URL da invocare per fare una visura di una posizione debitoria al nodo di pagamenti');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('PENTAHO_EXP_PATH',NULL,'Indica il percorso in cui vengono salvati i risultati dell’esportazione e/o del job richiamati dalla procedura');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('PENTAHO_URL_CARTE',NULL,'Indica l’url in cui è in ascolto il servizio CARTE per richiamare le trasformazioni e/o i job creati tramite Kettle.');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('PRODUCT_NAME',NULL,'Nome del prodotto');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('PRODUCT_STYLE',NULL,'Nome dello stile');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('RABBIT_EXCHANGE_NAME',NULL,'Nome dell\'exchange verso cui inviare i messaggi');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('RABBIT_HOSTNAME',NULL,'Url (o nome) del server su cui è ospitato il servizio di RabbitMQ');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('RABBIT_MESSAGGI_BACKEND_SERVICE',NULL,'Url della componente BACKEND-RABBIT-MQ che gestisce la notifica dei messaggi di backend a RABBIT. Questa URL viene usata attualmente dal backend per notificare i messaggi da inviare a RABBIT.');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('RABBIT_MESSAGGI_PAGAMENTI_SERVICE',NULL,'Url della componente NODOPAGAMENTI-RABBIT-MQ che gestisce la notifica dei messaggi di DEL NODOPAGAMENTI a RABBIT. Questa URL viene usata attualmente dal nodo dei pagamenti per notificare i messaggi da inviare a RABBIT.');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('RABBIT_PASSWORD',NULL,'Password per collegarsi al servizio di RabbitMQ');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('RABBIT_PORT',NULL,'Porta su cui è esposto il servizio di RabbitMQ');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('RABBIT_USERNAME',NULL,'Utente per collegarsi al servizio di RabbitMQ');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('TOKEN_TIMEOUT',NULL,'Time out del token di sessione. Immettere un valore numerico intero che esprime i minuti es.: 20 (20 minuti)');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('URL_SERVIZI_NODO_PAGAMENTI',NULL,'La Url dei servizi SOAP del nodo dei pagamenti es: http://servername:8080/nodo-pagamenti/services/pagamentiSOAP?wsdl');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_APIBACKEND',NULL,'E\' l\'url DI BASE del web service delle API di backend. Le chiamate di questo applicativo devono essere effettuate server to server in quanto non saranno esposte tramite webserver perché non prevedono autenticazione');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_ASPNET',NULL,'E\' l\'url di base dal quale partire per invocare i web service asp.net. L\'url deve essere accedibile solo dalle singole applicazioni asp.net e java ed eventualmente http://10.10.45.2/aspnet per invocare i web service bisogna accodare all\'url il percorso relativo del metodo da richiamare: \"WebServicesWSSIGeProSmtpMail SmtpMailSender.asmx\" che sarà hard coded.dal front-end.');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_ASPNET_CORE',NULL,'Url (interno) dei servizi asp.net che alimentano l\'area riservata/domanda on line. Se non impostato verrà utilizzato il valore del parametro WSHOSTURL_ASPNET (es. http://vbg.servizicorear)');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_CARTOGRAFICO',NULL,'Url di base del servizio di integrazione cartografico (es. http://vbg.sic:8080/sic/rest-api');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_EXPORT',NULL,'E\' l\'url che contiene il metodo web service utilizzato per le esportazioni. L\'url deve essere accedibile solo dalle singole applicazioni asp.net e java. Es: http://10.10.45.2/wssigeproexport/Service1.asmx');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_FILECONVERTER',NULL,'E\' l\'url dell\'host che espone il servizio di conversione file. L\'url deve essere accedibile solo dalle singole applicazioni asp.net, java e dal front-end. Es: http://sigeprodemo:8080/fileconverter/services/fileconverter.wsdl');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_FIRMA',NULL,'E\' l\'url del web service dell\'applicativo per la generazione della firma digitale');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_FIRMADIGITALE',NULL,'E\' l\'url dell\'host che espone il servizio di controllo di firma digitale. L\'url deve essere accedibile solo dalle singole applicazioni asp.net e java. Es: http://212.104.15.15/firmadigitaleweb/services/FirmaDigitaleWSA');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_FIRMADIGITALE_REST',NULL,'E\' l\'url REST dell\'host che espone il servizio di controllo di firma digitale');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_GENERATORE_RIEPILOGHI',NULL,'Url del servizio che si occupa di rigenerare i riepiloghi della domanda on line (default: http://vbg.generatore-riepiloghi:8682)');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_JAVA',NULL,'E\' l\'url di base dal quale partire per invocare i web service java. L\'url deve essere accedibile solo dalle singole applicazioni asp.net e java ed eventualmente dal front-end. Es: http://10.10.45.2/sigepro2');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_MAILSERVICE',NULL,'E\' l\'url del web service per l\'invio mail. L\'url deve essere accedibile solo dalle singole applicazioni asp.net, java e dal front-end. Es: http://localhost:8080/MailService/services/MailService');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_NLAPEC',NULL,'E\' l\'url dell\'applicativo che gestisce i servizi di lettura di una casella email. L\'applicativo espone sia servizi STC che servizi per leggere le mail di una casella PEC configurata nel backend ma questo parametro serve per accedere solamente ai servizi di lettura / processamento messaggi.Es. di configurazione: http://localhost:8080/nla-pec/services/nlaGestioneMail.wsdl');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_ONCEONLY_MODELLI',NULL,'E\' l\'url dell\'applicativo che gestisce i servizi once only per i modelli dinamici');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_PDFUTILS',NULL,'E\' l\'url dell\'applicativo che gestisce i servizi di precompilazione / lettura dati da PDF compilabili. L\'applicativo deve essere raggiungibile anche dalla macchina di frontoffice quindi verificare che dalla macchina del frontoffice questa configurazione sia raggiungibile.Es. di configurazione: http://nomemacchina:8080/pdfutils/services/pdfutils?wsdl');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_RENDER',NULL,'E\' l\'url dell\'host che espone il servizio graficazione delle procedure. L\'url deve essere accedibile solo dalle singole applicazioni asp.net, java e dal front-end. Es: http://localhost:9090/axis/services/sigeprorendererSOAP');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_RICALCOLOAREE',NULL,'Url root per ricalcolo aree');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_SERVIZI_REST_SECURITY',NULL,'Url di base dei servizi REST di ibcsecurity (default: http://vbg.security:8080/ibcsecurity/services/rest)');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WSHOSTURL_SIT',NULL,'Url di base del servizio di integrazione con i SIT (es. http://localhost:12345/WsSit.svc l\'hostname) variano da installazione a installazione');
insert into `comunisecurity_param` (`param`, `value`, `note`) values('WS_URL_PROTOCOLLO',NULL,'E\' l\'url dell\'applicativo che gestisce i servizi di protocollo. Es. di configurazione: http://10.10.45.2:80/aspnet/WebServices/WsSIGePro/ProtocollazioneService.svc?wsdl');



/*Data for the table `versione` */

insert  into `versione`(`VERSIONE`) values ('1.16');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

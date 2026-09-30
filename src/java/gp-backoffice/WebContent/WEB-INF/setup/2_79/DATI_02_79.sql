INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('WSANAGRAFE_MAGGIOLI', 'Se attivato permette di accedere al sistema anagrafico di Maggioli, tale sistema permette di recuperare sia persone fisiche che giuridiche.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_MAGGIOLI', 'URL', 'Indirizzo del ws anagrafe. Dato obbligatorio.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_MAGGIOLI', 'CODICEAMMINISTRAZIONE', 'Parametro richiesto in fase di richiesta, coincide con il parametro in querystring CID presente nel wsPrtocoolloDM del sistema di protocollo JIRIDE.');

INSERT INTO CLMENU_JAVA(ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, SOFTWAREESCLUSI, LINK_STANDARD, TIPO_FUNZIONALITA, MENULINK_V2) VALUES 
(1046, 'Accesso agli atti', 'istanzeaccessoattilog/createSearch.htm?software=SOFTWARE', '414', '*', 'JAVA', 'PR,FI', 'istanzeaccessoattilog/createSearch.htm?software=SOFTWARE', 'S', '414');


insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('AUTH_CODICE_ENTE','Parametro codice_ente usato per i servizi soap/rest per il connettore EasyPA','EASYPA');
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('AUTH_CODICE_ISTITUTO','Parametro codice_istituto usato per i servizi soap/rest per il connettore EasyPA','EASYPA');
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('AUTH_GRANT_TYPE','Parametro grant_type usato per i servizi soap/rest per il connettore EasyPA','EASYPA');
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('AUTH_ID_DOMINIO','Parametro id_dominio usato per i servizi soap/rest per il connettore EasyPA','EASYPA');
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('AUTH_ID_ENTE','Parametro id_ente usato per i servizi soap/rest per il connettore EasyPA','EASYPA');
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('CENTRO_DI_COSTO','Codice del centro di costo utilizzato per la nomenclatura dei files del flusso NEXI','NEXIGE');
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('DOCUMENTI_SERVICE','URL del servizio del BO per la generazione di documenti','NEXIGE');
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('DOCUMENTI_SU_FILESYSTEM','Specificando un percorso di una cartella su filesystem il nodo pagamenti salverà i documenti nella cartella specificata. Se la cartella non esiste verrà creata.',NULL);
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('SECURITY_ALIAS','Id comune alias per interrogare securiry',NULL);
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('SECURITY_PWD','Password per la connessione a security',NULL);
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('SECURITY_URL','URL del servizio di security',NULL);
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('SECURITY_USER','Utente per la connessione a security',NULL);
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) values('TIPO_DOCUMENTO_DEBITO','Tipologia di documento di debito da emettere per il connettore NEXI: per i valori fare riferimento all''enum java NexiGenovaConnector.TipologiaDocumentoDebito','NEXIGE');


INSERT INTO
pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES('SSL_TRUST_STORE_LOCATION','Indicare il percorso al trust store per l''autenticazione dei client soape rest con certificato SSL',NULL);

INSERT INTO
pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES('SSL_TRUST_STORE_PASSWORD','Indicare la password del trust store per l''autenticazione dei client soape rest con certificato SSL',NULL);

INSERT INTO
pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES('SSL_KEY_STORE_LOCATION','Indicare il percorso del key store che contiene il certificato client per l''autenticazione dei client soape rest con certificato SSL',NULL);

INSERT INTO
pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES('SSL_KEY_STORE_PASSWORD','Indicare la password del key store che contiene il certificato client per l''autenticazione dei client soape rest con certificato SSL',NULL);

INSERT INTO
pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES('SSL_KEY_STORE_CERT_ALIAS','Indicare l''alias del certificato da estrare per dal key store del certificato client per l''autenticazione dei client soape rest con certificato SSL',NULL);


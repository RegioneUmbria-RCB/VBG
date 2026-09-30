INSERT INTO verticalizzazionibase (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE)VALUES('FILESYSTEM_API_ALFRESCO', 'Se attivata, tutti i documenti dell''applicativo verranno salvati su Sistema di gestione documentale esterno che espone interfacce API REST <b>ALFRESCO</b> e non più su database. La funzionalità non può essere attivata contemporaneamente a quella su FILESYSTEM O FILESYSTEM_CMIS', 0);
INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE)VALUES('FILESYSTEM_API_ALFRESCO', 'ALFRESCO_API_PWD', 'Rappresenta la password dell''utente abilitato ad operare sulle interfacce del servizio di pubblicazione ALFRESCO');
INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE)VALUES('FILESYSTEM_API_ALFRESCO', 'ALFRESCO_API_URL', 'Rappresenta il percorso servizio di pubblicazione ALFRESCO es: http://localhost:8090/.');
INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE)VALUES('FILESYSTEM_API_ALFRESCO', 'ALFRESCO_API_USR', 'Rappresenta l''identificativo utente abilitato ad operare sulle interfacce del servizio di pubblicazione ALFRESCO');
INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE)VALUES('FILESYSTEM_API_ALFRESCO', 'CONNECT_TIMEOUT', 'Rappresenta in millisecondi il tempo concesso per la connessione al server di Alfresco');
INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE)VALUES('FILESYSTEM_API_ALFRESCO', 'READ_TIMEOUT', 'Rappresenta in millisecondi il tempo concesso per la lettura dei dati dal server Alfresco');
INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE)VALUES('FILESYSTEM_API_ALFRESCO', 'ALFRESCO_DOCUMENT_ROOT_FOLDER', 'Rappresenta la directory a partire dalla quale inserire i file mediante le interfacce del servizio di pubblicazione ALFRESCO');


INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE)VALUES('FILESYSTEM_API_ALFRESCO', 'ALFRESCO_ROOT_RELATIVE_PATH', 'Rappresenta Il percorso di base della directory root. Es. User%20Homes/vbg-entei/E256. Il percorso viene configurato ed e'' specifico per di ogni installazione. Serve per recuperare le info e creare le cartelle di destinazione del nuovo file a partire dal percorso calcolato dalla procedura es: 0000/00/01');
INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE)VALUES('FILESYSTEM_API_ALFRESCO', 'ALFRESCO_DOCUMENT_CONTENT_NAME', 'Rappresenta il document model dei file caricati. Di default e se non specificato cm:content, se installato il document model vbg allora è possibile specificare quello es: vbg:docPratica');

UPDATE clmenu_java SET menulink='0AF', MENULINK_V2='0A0F' WHERE ID=750;
UPDATE clmenu_java SET menulink='0AF1', MENULINK_V2='0AF1' WHERE ID=751;
UPDATE clmenu_java SET menulink='0AF2', MENULINK_V2='0AF2' WHERE ID=752;
UPDATE clmenu_java SET menulink='0AF0', MENULINK_V2='0AF0' WHERE ID=780;
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('FVG_SUAP_IN_RETE','ELABORA_DOCUMENTI_XML_ATTIVITA','Parametro per la configurazione dell''analisi dei file xml della pratica nel caso di notifica attività. Se rispettano la struttura xsd di fvgDataSet.xsd (presente nel backoffice) verranno trasformati in un oggetto SchedaType inserito durante la notifica dell''attivita'' (esempio integrazioni). Può assumere i valori S: analisi attiva, N: analisi non attiva');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','RUOLO','Codice che identifica il ruolo nel sistema esterno');


UPDATE VERTICALIZZAZIONIPARAMETRI SET VALORE='UFFICI' WHERE MODULO='STC' AND PARAMETRO='DIS_MOD_DATA_NOTIFICA' AND VALORE='1';
UPDATE VERTICALIZZAZIONIPARAMETRI SET VALORE= NULL WHERE MODULO='STC' AND PARAMETRO='DIS_MOD_DATA_NOTIFICA' AND VALORE='0';
UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = 'Permette di impostare la data e ora di inoltro dei movimenti notificati da stc in base alla data di ricezione della pec o di ricezione da parte degli uffici o della protocollazione (i valori ammessi sono PEC,UFFICI,PROTOCOLLAZIONE e possono esserne configurati più valori separati da virgola).' WHERE MODULO='STC' AND PARAMETRO='DIS_MOD_DATA_NOTIFICA';


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AUTENTICAZIONE_LDAP','USE_LDAP_SECURE_PROTOCOL','Usato per interrogare il sistema usando il protocollo LDAPS (LDAP SECURE) piuttosto che LDAP normale ');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','URL_FIRMATARI','Servizio che ritorna l''elenco dei firmatari da proporre in fase di rilascio atto');


INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('SCHED_TRAC_PAGOPA_STRATEGIA','Parametri per la lettura dei tracciati Standard PAGOPA: La strategia usata per leggere i file di tracciato scegliere tra i valori SFTP, FTP',NULL);
INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('SCHED_TRAC_PAGOPA_FTP_USER','Parametri per la lettura dei tracciati Standard PAGOPA: Utente per il collegamento FTP/SFTP',NULL);
INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('SCHED_TRAC_PAGOPA_FTP_PASSWORD','Parametri per la lettura dei tracciati Standard PAGOPA: Password per il collegamento FTP/SFTP',NULL);
INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('SCHED_TRAC_PAGOPA_FTP_SERVER','Parametri per la lettura dei tracciati Standard PAGOPA: Indirizzo del server per il collegamento FTP/SFTP',NULL);
INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('SCHED_TRAC_PAGOPA_FTP_PORT','Parametri per la lettura dei tracciati Standard PAGOPA: Porta del servizio FTP/SFTP',NULL);
INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('SCHED_TRAC_PAGOPA_FTP_FOLDER','Parametri per la lettura dei tracciati Standard PAGOPA: La folder di accesso del servizio FTP/SFTP',NULL);
INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('SCHED_TRAC_PAGOPA_QUARTZEXP','Parametri per la lettura dei tracciati Standard PAGOPA: L''espressione QUARTZ per indicare la tempistica di schedulazione',NULL);


INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('PPAY_USA_SERV_REST_ATTIVA_SESS','Nel caso del connettore Piemonte PAY REST determina se usare il servizio di attiva sessione del connettore non REST. Valori possibili (true o false) Il valore predefinito è true e indica che viene usato il servizio rest, mentre false indica che viene usato il servizio classico', NULL);

UPDATE COMMISSIONIEDILIZIE_T SET FLAG_SINCRONA='1' WHERE FLAG_SINCRONA IS NULL;
UPDATE COMMEDILIZIE_TIPOLOGIE SET FLAG_UPLOAD_DOC_PARERE ='0' WHERE FLAG_UPLOAD_DOC_PARERE IS NULL;

insert into software (CODICE,DESCRIZIONE,MODULOOPZIONALE, DESCRIZIONELUNGA,ACCESSORAPIDO,ORDINE) values ('ET','Scrivania Enti Terzi',1,'Scrivania Enti Terzi',0,99);

INSERT INTO VERTICALIZZAZIONIBASE(MODULO, DESCRIZIONE, FLAG_GESTCOMUNE) VALUES ('PROTOCOLLO_HALLEY2', 'E'' il protocollo gestito direttamente dalla ditta Halley Informatica senza intermediari.', 1);
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_HALLEY2', 'URL', '(Obbligatorio) End point del web service per invocare i servizi, ad esempio per il protocollo halley l''ambiente di test è presente su https://testwebservice.halleyas.com/testw/PI0/PIWSMAIN.HBL?wsdl');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_HALLEY2', 'CASELLAEMAIL', 'Indicare la casella di posta dell''ente configurata all''interno della procedura Protocollo informatico');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_HALLEY2', 'USERNAME', '(Obbligatorio) Username di autenticazione al web service del protocollo Halley. Deve essere fornito dal fornitore del protocollo');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_HALLEY2', 'PASSWORD', '(Obbligatorio) Password di autenticazione al web service del protocollo Halley. Deve essere fornito dal fornitore del protocollo');

UPDATE anagrafe SET fo_utentetester=0;

INSERT INTO pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES('SSL_TRUST_STORE_LOCATION','Indicare il percorso al trust store per l''autenticazione dei client soape rest con certificato SSL',NULL);

INSERT INTO pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES('SSL_TRUST_STORE_PASSWORD','Indicare la password del trust store per l''autenticazione dei client soape rest con certificato SSL',NULL);

INSERT INTO pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES('SSL_KEY_STORE_LOCATION','Indicare il percorso del key store che contiene il certificato client per l''autenticazione dei client soape rest con certificato SSL',NULL);

INSERT INTO pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES('SSL_KEY_STORE_PASSWORD','Indicare la password del key store che contiene il certificato client per l''autenticazione dei client soape rest con certificato SSL',NULL);

INSERT INTO pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES('SSL_KEY_STORE_CERT_ALIAS','Indicare l''alias del certificato da estrare per dal key store del certificato client per l''autenticazione dei client soape rest con certificato SSL',NULL);


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('COMPORTAMENTI_MERCATI', 'DATA_POS_DEB_CONCESSIONARI', 'Se valorizzato, a partire da quella data verranno inserire le posizioni debitorie anche per i concessionari quando verranno registrati presenti nelle giornate interessate');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('COMPORTAMENTI_MERCATI', 'NASCONDI_BOTTONE_CONC_PRES', 'Se impostato a S verrà nascosto il bottone che permette di segnare presenti tutti i concessionari. Se impostato a N o non impostato, tale bottone verrà mostrato');

INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('URL_CALLBACK_CAMBIO_STATO','Url del servizio che si mette in ascolto dei cambiamenti di stato delle posizioni debitorie',NULL);

UPDATE CONFIGURAZIONEUTENTE SET VALORE='div_bottoni_istanza_sotto' WHERE NOMEPARAMETRO='ISTANZE_POSIZIONE_BOTTONI' AND VALORE='drop_bottom'; UPDATE CONFIGURAZIONEUTENTE SET VALORE='div_bottoni_istanza_sopra' WHERE NOMEPARAMETRO='ISTANZE_POSIZIONE_BOTTONI' AND VALORE='drop_up';
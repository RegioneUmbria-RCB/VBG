
INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('FVG_PAY_USA_AUTH_PAG_IMMED','Nel pagamento immediato sovrascrive il parametro autenticazione (Il valore predefinito se non impostato è true). Accetta Valori true/false.', NULL);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO', 'AOO', 'Identificativo della AOO presso il software del protocollo');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO', 'USA_NUM_ANNO_LEGGI', 'Impostare a 1 se la lettura del protocollo deve essere fatta solamente tramite numero e anno. 0 o non valorizzato: se presente l''id del protocollo la lettura avverrà per id, altrimenti per numero e anno');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO', 'VISUALIZZA_RICEVUTE_PEC', 'Impostare a 1 se si vuole visualizzare l''esito delle ricevute inviate via PEC dal sistema di protocollo, tra l''elenco degli allegati in fase di lettura di un protocollo.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO', 'OPERATORE', 'Identificativo dell''operatore presso il software del protocollo');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO', 'RUOLO', 'Identificativo del ruolo operatore presso il software del protocollo');

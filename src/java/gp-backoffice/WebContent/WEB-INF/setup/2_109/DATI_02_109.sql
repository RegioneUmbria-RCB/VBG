UPDATE tipicausalioneri SET mappaturanodopag  = codicecausalepeople where mappaturanodopag is null;

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('WS_ATTI', 'Abilita l''integrazione tra il backoffice e i servizi esterni dell''ente per la gestione degli atti dirigenziali (determine,ordinanze,...)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','URL','URL dei servizi web del sistema esterno che gestisce gli atti (determina,ordinanza,...)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','OGGETTO_TESTOTIPO','Contiene il riferimento al testo tipo che verrà utilizzato per recuperare l''oggetto della determina,ordinanza,.....');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','CODICE_TRATTAMENTO','Codice del tipo di iter che viene passato al sistema esterno per identificare l''iter che l''atto seguirà nel gestionale esterno');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','CODICE_PROPONENTE','Codice che identifica l''ufficio proponente nel sistema esterno');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','CODICE_DIRIGENTE','Codice che identifica il dirigente nel sistema esterno; solitamente coincide con il firmatario');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','CLASSIFICA','Codice della classifica');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','UTENTE','Utente applicativo utilizzato per la connessione ai servizi');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','TIPO_CONNETTORE','Connettore da utilizzare per il collegamento al servizio di gestione atti. ( Attualmente è implementato solo SICRAWEB');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','TIPO_CONNETTORE','Connettore da utilizzare per il collegamento al servizio di gestione atti. ( Attualmente è implementato solo SICRAWEB');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WS_ATTI','FASCICOLA_ATTO','Indica al sistema se l''atto deve essere fascicolato una volta completo. N (default): l''atto non viene fascicolato, S:l''atto viene fascicolato richiamando il metodo di fascicolazione del protocollo attivo');

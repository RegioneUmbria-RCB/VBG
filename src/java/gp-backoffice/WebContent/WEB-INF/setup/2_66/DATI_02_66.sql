INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'ALLEGA_XMLDOMANDASTC', 'Se impostato a 1 consentirà di allegare il file XML relativo alla domanda STC per le pratiche in arrivo. Ovviamente vale solo per le istanze in arrivo e dove è presente tale file.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('COMPORTAMENTI_MERCATI', 'BLOCCA_ACCESSO_MERC_NEL_FUTURO', 'Può assumere i valori S o N (NULL). S: Non sarà possibile accedere alla gestione delle presenze di una giornata di mercato successiva alla data odierna. N: continua ad avere il di default che, permette di accedere a tutte le giornate');

UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = 'Indica come deve essere gestito l''indirizzo pec delle anagrafiche; se non valorizzato, o valorizzato a 0 questo parametro, 
allora la logica sarà la stessa di prima, ossia le anagrafiche relative a Richiedente e Titolare Legale avranno come indirizzo PEC 
il Domicilio Elettronico della pratica, se non presente sarà valorizzato l''indirizzo PEC dell''anagrafica stessa. 
Se valorizzato a 1 allora verranno recuperati solo gli indirizzi PEC delle anagrafiche, se valorizzato a 2 allora la logica sarà la stessa 
indicata con il valore 1, con la differenza che, se non presenti, la logica tornerà ad essere la stessa con il valore 0; 
se valorizzato a 3 invece, la logica sarà la stessa del valore 2, con la differenza che la logica varrà solo per il titolare legale, se valorizzato a 4
le pec dell''azienda e del richiedente saranno quelle delle anagrafiche, solo il tecnico avrà il domicilio elettronico come PEC, a meno che l''azienda non 
abbia la pec e non sia presente il tecnico.
Qualsiasi altra anagrafica infatti, se vuoto l''indirizzo PEC, lo stesso rimarrà vuoto.' WHERE MODULO = 'PROTOCOLLO_ATTIVO' AND PARAMETRO = 'GESTIONE_PEC';

UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = 'Indica con quale dato deve essere valorizzato il mittente / destinatario per le protocollazioni automatiche, 
se una protocollazione è in arrivo allora il soggetto interessato sarà il mittente, se la protocollazione automatica 
sarà in partenza allora il soggetto sarà il destinatario. Può assumere i seguenti valori: non valorizzato o se valorizzato a 0 
il sistema indicherà come mittente / destinatario il Richiedente e l''Azienda, se valorizzato a 1 verrà proposto solo il Richiedente, 
se valorizzato a 2 il sistema indicherà come Mittenti solo l''Azienda, se, in questo caso l''Azienda non è presente indicherà solamente il Richiedente,
se valorizzato a 3 il sistema indicherà l''azienda e il tecnico, se l''azienda non è presente indicherà il richiedente.' 
WHERE MODULO = 'PROTOCOLLO_ATTIVO' AND PARAMETRO = 'TIPO_MITTDEST_AUTO';
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JIRIDE', 'TIPORECAPITO_EMAIL', 'Indica la tipologia di recapito relativa alla Mail / Pec. Questo tipo di dato deve essere fornito dal gestore di protocollo perchè indica, appunto la tipologia che deve assumere il recapito, va indicato solo se, in accordo con l''ente, è stata aggiunta una tipologia nuova ( di default è impostata a EMAIL).');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_IRIDE', 'TIPORECAPITO_EMAIL', 'Indica la tipologia di recapito relativa alla Mail / Pec. Questo tipo di dato deve essere fornito dal gestore di protocollo perchè indica, appunto la tipologia che deve assumere il recapito, va indicato solo se, in accordo con l''ente, è stata aggiunta una tipologia nuova ( di default è impostata a EMAIL).');
INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'PARAMETRI_SISTEMA',
    'PEC_CLIENT_CALL_TIMEOUT',
   'Timeout della chiamata client NlaGestioneMailWSClient. Di default impostato a 1200000 ms'
);

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE) VALUES ('PAGAMENTI_ENTRANEXT', 'Sistema di Pagamenti dennmoinato EntraNext (o LinkNext) sviluppato da Next Step Solution.', '0');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_ENTRANEXT', 'URL_WS', 'End point di riferimento relativamente al web service del sistema di pagamenti');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_ENTRANEXT', 'IDENTIFICATIVO_CONNETTORE', 'Identificativo del connettore assegnato, deve essere comunicato dal cliente, serve per valorizzare l''oggetto IntestazioneFO che deve essere passato come argomento su tutte le chiamate.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_ENTRANEXT', 'CODICE_FISCALE_ENTE', 'Codice fiscale Ente sul quale lavorare, serve per valorizzare l''oggetto IntestazioneFO che deve essere passato come argomento su tutte le chiamate.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_ENTRANEXT', 'VERSIONE', 'Parametro di riferimento che serve per effettuare la login al web service, deve essere comunicato dal cliente');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_ENTRANEXT', 'IDENTIFICATIVO', 'Identificativo per l’accesso, parametro che serve per effettuare la login al web service, deve essere comunicato dal cliente.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_ENTRANEXT', 'USERNAME', 'Username per l''accesso al web service, deve essere comunicato dal cliente.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_ENTRANEXT', 'PASSWORD_MD5', 'Password di accesso al web service criptata in MD5 (deve essere valorizzato l''MD5 in questo parametro), deve essere comunicata dal cliente.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_ENTRANEXT', 'CODICE_TIPO_PAGAMENTO', 'Id del tipo pagamento da utilizzare per i pagamenti online');


INSERT INTO COMUNI(CODICECOMUNE,COMUNE,SIGLAPROVINCIA,REGIONE,CODICEISTAT,CODICEISTATREGIONE,PROVINCIA,CF,CAP) VALUES('M316','MAPPANO','TO','PIEMONTE','001316','01','TORINO','M316','10079');


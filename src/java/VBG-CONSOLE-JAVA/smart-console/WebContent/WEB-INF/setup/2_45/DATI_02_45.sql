INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) 
VALUES ('LIVORNO_SERVIZI_CITTADINO', 'URL_WS_MODULISTICA_DRUPAL', 'Url del web service che espone i dati delle schede drupal. Es. http://localhost/DrupalService/www/DrupalService.asmx');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione)  VALUES ('STC', 'USA_NUMISTANZA_ALTRO_SISTEMA', 'Se attivato ed il valore = S allora durante l''inserimento della pratica verra'' usato il numero istanza del sistema mittente. Ad esempio viene usato con per la ricezione pratica da SIEDER.');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione)  VALUES ('SIEDER', 'INSERISCI_SEMPRE_PRATICHE', 'Se attivato ed il valore = S allora qualsiasi pratica SIEDER genera una pratica di Backoffice e non un movimento');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('DBINFORMATICA', 'Se attivato rende disponibili le funzionalita'' specifiche dei servizi DBINFORMATICA');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE VALUES ('DBINFORMATICA','WS_URL_DBENERGIA_QC','URL DEL WS per i servizi DBE_GetDati_POD di DBINFORMATICA');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE VALUES ('DBINFORMATICA','CODICE_IMPIANTO_DEFAULT','Serve per prepopolare le chiamate ws GET_DATI_PO con il codice impianto di default');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_DOCER', 'N_TENTATIVI_RETRY', 'Indicare in questo parametro il valore, obbligatoriamente numerico, del numero di retry alla chiamata a protocollaById che il sistema deve fare in caso di errore di protocollazione del web service. Di default non saranno impostate retry, quindi è come l''equivalente del valore 0');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('SIEDER','CODICECAUSALEONEREDEFAULT','Il parametro indica il codice di causale di default da associare agli oneri provenienti dal sistema SIEDER' );

INSERT INTO  COMUNI (CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CAP, CF, CODICEISTAT, CODICEISTATREGIONE) VALUES ('M377', 'SAN MARCELLO PITEGLIO', 'PT', 'PISTOIA', 'TOSCANA', '00000', 'M377', '047024', '09');

INSERT INTO  COMUNI (CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CAP, CF, CODICEISTAT, CODICEISTATREGIONE) VALUES ('M376', 'ABETONE CUTIGLIANO', 'PT', 'PISTOIA', 'TOSCANA', '00000', 'M376', '047023', '09');


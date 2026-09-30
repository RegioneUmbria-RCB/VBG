INSERT INTO VERTICALIZZAZIONIBASE(MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) 
    VALUES 
    ('API_SERVICE'
    ,'La verticalizzazione contiene i parametri per gestire consumare i servizi delle API applicative esposte dall''applicativo api-backend'
    ,0);
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE  (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('API_SERVICE','API_SERVICE_URL','Rappresenta l''URI per raggiungere le API');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_CIVILIA', 'TIPO_RICHIESTA', 'In questo parametro va indicato che tipo di richiesta deve essere eseguita, se in GET o in POST, i parametri accettati sono, appunto GET e POST (scritto in maiuscolo). Se sarà indicato qualsiasi altro valore o se non sarà inserito alcun valore, o se il parametro non sarà presente, la richiesta sarà fatta in POST.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO', 'CODICEUFFICIO', 'Indicare la sigla dell''ufficio che identifica, presso il fornitore, l''ufficio che protocolla');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_LDP', 'URL_PRESENTAZIONE_INTEGRAZIONE', 'Url per l''aggiornamento dei dati nel SIT LDP utilizzato dai movimenti che vengono eseguiti dall''area riservata');

INSERT INTO dyn2_basecontesti (id, contesto) VALUES('ME', 'Mercati');
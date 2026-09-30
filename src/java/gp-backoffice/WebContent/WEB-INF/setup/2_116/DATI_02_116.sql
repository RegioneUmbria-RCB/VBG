insert into verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) values('AREA_RISERVATA', 'FLG_USA_MESSAGGI_RABBITMQ', 'Abilita o disabilita l''utilizzo dei messaggi su RabbitMQ (1=abilitato, 0=disabilitato, default: 0). Per funzionare correttamente i parametri di rabbit devono essere impostati nel security');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'PASSWORD', 'Password facente parte delle credenziali di autenticazione basic, utilizzare solamente nel caso in cui sia presente la richiesta di autenticazione');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO','APPLICA_LAYER','Parametro che permette di applicare la stampigliatura ad un documento protocollato. Può assumere valori : S = attivo, N (NULL)= non attivo.');
INSERT INTO verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'DOL_URL_LAYOUT_CONFIG_SERVICE', 'Url del servizio da interrogare per ottenere il layout di header/footer nella domanda on line (es. https://comune-jesi.inera.it/o/utilty-api/page-utility)');
INSERT INTO verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'DOL_URL_HOMEPAGE_COMUNE', 'Url dell''home page del comune, verrà utilizzato nell''header della pagina (es. https://comune-jesi.inera.it)');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'ABILITA_TEMPLATE_DOMANDA', 'Il parametro permette di presentare una nuova domanda a partire da una già presentata.</br>Valori:</br> 1 - Viene mostrato, nella sezione LE MIE PRATICHE, per ogni istanza il link "usa come modello" che permette di presentare una nuova domanda a partire da quella</br> 0 o non impostato - La sezione LE MIE PRATICHE non mostra la colonna aggiuntiva con il link');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('QRCODE','TEMPLATE_AUTOMATICO','Parametro che indica se applicare il QRCODE ad un documento PDF per visualizzare le informazioni della pratica. Può assumere valori : S = attivo, N (NULL)= non attivo.'); 
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('QRCODE','TEMPLATE_HEIGHT','Dimensione in altezza del qrcode da generare. Default 150');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('QRCODE','TEMPLATE_PAGE_NUM','Numero della pagina dove posizionare il qrcode. Default 1');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('QRCODE','TEMPLATE_POS_X','Coordinata delle ascisse per posizionare il QRCODE nel PDF.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('QRCODE','TEMPLATE_POS_Y','Coordinata delle ordinate per posizionare il QRCODE nel PDF.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('QRCODE','TEMPLATE_URL_DOWNLOAD','E'' possibile configurare questo parametro per servire il file in maniera non autenticata ovvero visualizzando il QRCODE ');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('QRCODE','TEMPLATE_WIDTH','Dimensione in larghezza del qrcode da generare. Default 150');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('QRCODE','TEMPLATE_CHIAVE_MAC','Chiave da applicare per crittofare la url. Può essere una stringa qualsiasi.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('QRCODE','TEMPLATE_RIF_LETTERA_TIPO','Codice della lettera tipo da cui prendere il template per creare il documento da visualizzare');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE)  VALUES ('RABBITMQ','La regola permette di attivare i comportamenti per la messaggistica RABBIT MQ. La regola va attivata a livello generale e non per singolo software/comune.', 0);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('PROTOCOLLO_ATTIVO','RICHIEDI_AMM_AZIONI_PROTOCOLLO','Valori 1 o 0; Valorizzare a 1 se, nella funzionalità "Azioni protocollo" deve essere obbligatoriamente indicata l''amministrazione da utilizzare per la lettura delle info del protocollo; altrimenti valorizzare a 0 o non impostare questo parametro');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('RABBITMQ','DATA_INIZIO_RICERCHE_NOTIFICHE','Parametro che serve per definire la data di inizio ricerca dei vari eventi da notificare a Rabbit');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('APPLICA_LAYER_PROT_PDF','COD_MAIL_TIPO_MOV','Codice mail tipo da cui prendere l''oggetto per creare la stringa stampata nell''annotazione del movimento');

UPDATE verticalizzazioniparametribase SET DESCRIZIONE='Codice mail tipo da cui prendere l''oggetto per creare la stringa stampata nell''annotazione' WHERE MODULO='APPLICA_LAYER_PROT_PDF' AND PARAMETRO='COD_MAIL_TIPO';

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_AURIGA','VERSIONE','Valori ammessi: V1 e V2. Se non indicato si considera V1. Se nei servizi esposti dal protocollo Auriga non è più presente il tag DataOraSped, impostare con V2 questo parametro.');

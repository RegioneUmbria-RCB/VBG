INSERT INTO clmenu_java ( ID , DESCRIZIONE , PAGINA , MENULINK , SOFTWARE , JSP , VERTICALIZZAZIONE , SOFTWAREESCLUSI , LINK_STANDARD , TIPO_FUNZIONALITA , LAYOUTTESTI , MENULINK_V2 ) VALUES (((SELECT MAX(A.ID) FROM CLMENU_JAVA A )+ 1), 'Comunicazioni massive', 'mercati/listComunicazioniM.htm?software=SOFTWARE', '10H1', '*', 'JAVA', NULL, NULL, 'mercati/listComunicazioniM.htm?software=SOFTWARE', 'E', NULL, '10H1');
insert into verticalizzazioniparametribase ( MODULO , PARAMETRO , DESCRIZIONE ) values ('PROTOCOLLO_HALLEY2', 'USERNAMEAURI', '(Obbligatorio) Username di autenticazione al web service Auri');
insert into verticalizzazioniparametribase ( MODULO , PARAMETRO , DESCRIZIONE ) values ('PROTOCOLLO_HALLEY2', 'PASSWORDAURI', '(Obbligatorio) Password di autenticazione al web service Auri');
insert into verticalizzazioniparametribase ( MODULO , PARAMETRO , DESCRIZIONE ) values ('PROTOCOLLO_HALLEY2', 'URLESTRAIPROTO', '(Obbligatorio) Url per raggiungere il web service Auri EstraiProtocollo');
insert into verticalizzazioniparametribase ( MODULO , PARAMETRO , DESCRIZIONE ) values ('PROTOCOLLO_HALLEY2', 'URLCONSULTADOCUMENTO', '(Obbligatorio) Url per raggiungere il web service Auri ConsultaDocumento');

UPDATE verticalizzazioniparametribase SET DESCRIZIONE = 'Indica il limite in bytes dei file caricabili per ogni domanda. Il valore di default è 10485760 bytes (10 Mb).' WHERE modulo = 'AREA_RISERVATA' AND parametro = 'DIMENSIONE_MASSIMA_ALLEGATI';

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'DOL_MODALITA_INVIO_NOTIFICHE', 'Modalità con cui vengono inviate le notifiche al termine della presentazione domanda. Può assumere i seguenti valori: mail,io,nessuna (default=mail). VALIDO SOLO PER DOL');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'DOL_MODALITA_INVIO_NOTIFICHE', 'Modalità con cui vengono inviate le notifiche al termine della presentazione domanda. Può assumere i seguenti valori: mail,io,nessuna (default=mail). VALIDO SOLO PER DOL');

UPDATE BLACKLIST_MOTIVI SET CONTESTO = 'presenze' WHERE CONTESTO IS NULL;

INSERT INTO layouttestibase (codicetesto,testo,SOFTWARE) VALUES  ('BLACKLIST_PRESENZE','B.L Presenze','TT');
INSERT INTO layouttestibase (codicetesto,testo,SOFTWARE) VALUES  ('BLACKLIST_BOLLETTAZIONE','B.L Utenze','TT');


INSERT INTO verticalizzazioniparametribase ( MODULO , PARAMETRO , DESCRIZIONE ) VALUES ('NODO_PAGAMENTI', 'BLACKLIST_TIME_BOLLETTAZIONE', 'Rappresenta dopo quanto tempo le posizioni debitorie non pagate sulle bollettazioni devono finire in black list. Indicare una durata nel formato ISO come documentato in https://www.w3.org/TR/xmlschema-2/#duration. Esempi: per aggiungere 30 ore specificare P0Y0M0DT30H0M0S.');

INSERT INTO  verticalizzazioniparametribase  ( MODULO , PARAMETRO , DESCRIZIONE ) VALUES ('NODO_PAGAMENTI', 'BLACKLIST_TIME_PRESENZE', 'Rappresenta dopo quanto tempo le posizioni debitorie non pagate sulle presenze devono finire in black list. Indicare una durata nel formato ISO come documentato in https://www.w3.org/TR/xmlschema-2/#duration. Esempi: per aggiungere 30 ore specificare P0Y0M0DT30H0M0S.');

UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = '(DISMESSO) Al posto di questo parametro sarà usato il nuovo parametro BLACKLIST_TIME_PRESENZE' WHERE MODULO = 'NODO_PAGAMENTI' AND PARAMETRO = 'BLACKLIST_TIME_CHECK_PAGAM';

UPDATE VERTICALIZZAZIONIPARAMETRI SET parametro = 'BLACKLIST_TIME_PRESENZE' WHERE MODULO = 'NODO_PAGAMENTI' AND PARAMETRO = 'BLACKLIST_TIME_CHECK_PAGAM';

INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('COMPORTAMENTI_BOLLETTAZIONE', 'Permette di impostare i parametri per gestire alcuni comportamenti della funzionalità bollettazione', 0);

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COMPORTAMENTI_BOLLETTAZIONE', 'MOSTRA_FUNZIONE_RETTIFICA', 'Mostra o nasconde la funzionalità di rettifica delle voci, Valori ammessi S/N, se non specificato S');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COMPORTAMENTI_BOLLETTAZIONE', 'MOSTRA_FUNZIONE_AGGIUNGI_RIGA', 'Mostra o nasconde la funzionalità di aggiunta di una nuova voce di importo della bollettazione per l''anagrafica, Valori ammessi S/N, se non specificato S');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COMPORTAMENTI_MERCATI', 'APP_VIGILI_NASCONDI_INS_SPUNT', 'ACCETTA S o N DEFAULT N');

insert into COMUNI (CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CAP, CF, CODICEISTAT, CODICEISTATREGIONE, CODICESTATOESTERO) values('M406','BORGOCARBONARA','MN','MANTOVA','LOMBARDIA',NULL,'M406','20073','3',NULL);

INSERT INTO verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'INTEGR_MSG_TERMINE_INVIO', 'Messaggio da mostrare al termine dell''invio di un movimento dall''area riservata');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('CARTOGRAFICO_ATTIVO','CAMPO_ALTRI_DATI','Inserire il nome del campo dinamico su cui verranno riportati i dati recuperati tramite l''integrazione. I dati vengono riportati in formato JSON per essere poi lavorati tramite formule');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('CARTOGRAFICO_ATTIVO','MODELLO_ALTRI_DATI','Inserire il codice testuale della scheda dinamica su cui verranno riportati i dati recuperati tramite l''integrazione');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS','GESTISCI_SOTTOFASCICOLO','Se impostato a 1 abilita la sottofascicolazione');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS','DESCRIZIONE_SOTTOFASCICOLO','OBBLIGATORIO se GESTISCI_SOTTOFASCICOLO = 1');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COMPORTAMENTI_BOLLETTAZIONE', 'MOSTRA_FUNZIONE_COM_MASSIVE', 'Mostra o nasconde la funzionalità di COMUNICAZIONI MASSIVE. Valori ammessi S/N, se non specificato S');

UPDATE TASKPARAMETRIBASE SET DESCRIZIONE = 'Se l''installazione gestisce più comuni va valorizzato con il codice del comune per il quale effettuare la schedulazione. In caso contrario va lasciato vuoto.' WHERE TASK = 'SORTEGGI' AND PARAMETRO = 'CODICECOMUNE';
INSERT INTO TASKPARAMETRIBASE(TASK,PARAMETRO,DESCRIZIONE,ORDINE) VALUES ('SORTEGGI','CATEGORIA','Se si vuole legare il sorteggio ad una particolare categoria, inserire il codice della categoria','22');
UPDATE CLMENU_JAVA SET PAGINA = 'taskscheduler/list.htm?software=SOFTWARE', LINK_STANDARD = 'taskscheduler/list.htm?software=SOFTWARE', JSP='JAVA', TIPO_FUNZIONALITA = 'S' WHERE ID = 879;


insert into fo_rating_main (idComune, id, tipo, testo) select idcomune, '1','page','Quanto sono chiare le informazioni su questa pagina?' from configurazione where software='TT';
insert into fo_rating_main (idComune, id, tipo, testo) select idcomune, '2','service','Quanto è stato facile usare questo servizio?' from configurazione where software='TT';
insert into fo_rating_sub_question (idComune, id, idRatingMain,isPositive,testo) select idcomune, '1','1','1','Quali sono stati gli aspetti che hai preferito?' from configurazione where software='TT';
insert into fo_rating_sub_question (idComune, id, idRatingMain,isPositive,testo) select idcomune, '2','1','0','Dove hai incontrato le maggiori difficoltà?' from configurazione where software='TT';
insert into fo_rating_sub_question (idComune, id, idRatingMain,isPositive,testo) select idcomune, '3','2','1','Quali sono stati gli aspetti che hai preferito?' from configurazione where software='TT';
insert into fo_rating_sub_question (idComune, id, idRatingMain,isPositive,testo) select idcomune, '4','2','0','Dove hai incontrato le maggiori difficoltà?' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '1' ,'1','Le indicazioni erano chiare', '1' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '2' ,'1','Le indicazioni erano complete', '2' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '3' ,'1','Capivo sempre che stavo procedendo correttamente', '3' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '4' ,'1','Non ho avuto problemi tecnici', '4' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '5' ,'1','Altro', '5' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '6' ,'2','A volte le indicazioni non erano chiare', '1' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '7' ,'2','A volte le indicazioni non erano complete', '2' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '8' ,'2','A volte non capivo se stavo procedendo correttamente', '3' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '9' ,'2','Ho avuto problemi tecnici', '4' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '10' ,'2','Altro', '5' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '11' ,'3','Le indicazioni erano chiare', '1' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '12' ,'3','Le indicazioni erano complete', '2' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '13' ,'3','Capivo sempre che stavo procedendo correttamente', '3' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '14' ,'3','Non ho avuto problemi tecnici', '4' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '15' ,'3','Altro', '5' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '16' ,'4','A volte le indicazioni non erano chiare', '1' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '17' ,'4','A volte le indicazioni non erano complete', '2' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '18' ,'4','A volte non capivo se stavo procedendo correttamente', '3' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '19' ,'4','Ho avuto problemi tecnici', '4' from configurazione where software='TT';
insert into fo_rating_choice (idComune,id, idRatingSubQuestion, testo, ordine) select idcomune, '20' ,'4','Altro', '5' from configurazione where software='TT';

INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('AREA_RISERVATA_SSU', 'Parametri per attivare la modalità SSU dell''area riservata', 0);

 

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA_SSU', 'BASE_URL_API_CATALOGO_SERVIZI', 'Url di base su cui sono esposte le API del catalogo dei servizi');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA_SSU', 'ID_NODO_DESTINATARIO', 'Id del nodo STC che riceve le domande SSU');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA_SSU', 'ID_ENTE_DESTINATARIO', 'Id del''ente STC che riceve le domande SSU');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA_SSU', 'ID_SPORTELLO_DESTINATARIO', 'Id sportello STC che riceve le domande SSU');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COMPORTAMENTI_BOLLETTAZIONE', 'INVIO_POSIZIONI_DELAY', 'Durante l''invio delle posizioni debitorie imposta un delay di n millisecondi tra la creazione di una posizione ed un''altra. Se non specificato l''invio avviene in modalit senza delay');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS','VALORIZZA_SOGGETTO_FASCICOLO','Indica se valorizzare o meno la proprietà Soggetto del folder di Acaris. Se vuoto o valorizzato a 1: riporta la lista dei soggetti del protocollo separata da un trattino; se valorizzato a 0 non popola la rispettiva proprietà del protocollo Acaris');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS','ANNOTA_ALLEGATO_PRINCIPALE','Indica se valorizzare o meno le annotazioni sull''allegato principale. Se vuoto o valorizzato a 1: verranno valorizzate le annotazioni; se valorizzato a 0 le annotazioni non verranno valorizzate');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS','ANNOTA_ALLEGATO_SECONDARIO','Indica se valorizzare o meno le annotazioni sugli allegati secondari. Se vuoto o valorizzato a 1: verranno valorizzate le annotazioni; se valorizzato a 0 le annotazioni non verranno valorizzate');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('CARTOGRAFICO_ATTIVO','POSIZIONE_LATITUDINE','Indica la posizione (0 o 1) della coordinata che rappresenta la latitudine nella struttura JSON di ritorno. Se vengono invertite le coordinate nel dettaglio della localizzazione è possibile variare questo valore. Se non impostato vale 0'); 
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('CARTOGRAFICO_ATTIVO','POSIZIONE_LONGITUDINE','Indica la posizione (0 o 1) della coordinata che rappresenta la longitudine nella struttura JSON di ritorno. Se vengono invertite le coordinate nel dettaglio della localizzazione è possibile variare questo valore. Se non impostato vale 1');
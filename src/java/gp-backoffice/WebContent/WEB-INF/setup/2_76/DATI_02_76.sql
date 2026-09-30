UPDATE VERTICALIZZAZIONIPARAMETRIBASE 
SET descrizione='(OBSOLETO) specifica l''algoritmo usato per l''archiviazione. ARCHIVIAZIONE_MULTI_ISTANZE: un pacchetto N istanze, ARCHIVIAZIONE_PER_OGGETTO: un pacchetto un oggetto'
WHERE modulo='ARCHIVIAZIONE_DOC_LEGALDOC' and parametro='ALGORITMO_ARCHIVIAZIONE';

UPDATE VERTICALIZZAZIONIPARAMETRIBASE 
SET descrizione='(OBSOLETO) percorso della cartella locale di LegalDocs dove archiviare i file' 
WHERE modulo='ARCHIVIAZIONE_DOC_LEGALDOC' and parametro='DATA_FOLDER_PATH';

UPDATE VERTICALIZZAZIONIPARAMETRIBASE 
SET descrizione='(OBSOLETO) Lista di stringhe (separate da ;) da ricercare nel nome del file per individuare il documento principale' 
WHERE modulo='ARCHIVIAZIONE_DOC_LEGALDOC' and parametro='DOC_PRINCIPALE_SEARCH_STRING';

UPDATE VERTICALIZZAZIONIPARAMETRIBASE 
SET descrizione='(OBSOLETO) Valori : OFFLINE, WS. OFFLINE: la procedura crea i file necessari per mandare il documento in conservazione sulla cartella specificata in verticalizzazione, WS: la procedura invoca un ws per l'' dei documenti in conservazione, utilizzando l''indirizzo configurato in verticalizzazione' 
WHERE modulo='ARCHIVIAZIONE_DOC_LEGALDOC' and parametro='TIPO_SERVIZIO';

UPDATE VERTICALIZZAZIONIPARAMETRIBASE 
SET descrizione='(OBSOLETO) parametro obbligatorio che identifica il tipo di richiesta a LegalDoc. Valore di default: conserve'
WHERE modulo='ARCHIVIAZIONE_DOC_LEGALDOC' and parametro='OPERATION';

UPDATE VERTICALIZZAZIONIPARAMETRIBASE 
SET descrizione='(OBSOLETO) attributo facoltativo all''interno del file di avvio ad uso del Cliente per descrivere la tipologia di file che costituisce il job; il contenuto di questo campo è libero e, se presente, viene riportato nei messaggi PEC di segnalazione' 
WHERE modulo='ARCHIVIAZIONE_DOC_LEGALDOC' and parametro='NOME';

UPDATE VERTICALIZZAZIONIPARAMETRIBASE 
SET descrizione='(OBSOLETO) attributo facoltativo all''interno del file di avvio che può essere usato per identificare l''utente finale, di norma rappresentato da un cliente del cliente intermediario (es. Banca1, Banca2...); il contenuto di questo campo è libero e, se presente, viene riportato nei messaggi PEC di segnalazione'
WHERE modulo='ARCHIVIAZIONE_DOC_LEGALDOC' and parametro='END_USER_ID';

UPDATE VERTICALIZZAZIONIPARAMETRIBASE 
SET descrizione='(OBSOLETO) numero massimo di record della tabella ISTANZE da recuperare dalla query che individua le istanze da archiviare'
WHERE modulo='ARCHIVIAZIONE_DOC_LEGALDOC' and parametro='MAX_NUM_ISTANZE_PER_QUERY';
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC','LEGALDOC_INDEX_LABEL','Valore da inserire in nell''xml index nel tag <legaldocIndex> nell''opzione ''label''');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC','LEGALDOC_INDEX_DOCUMENT_CLASS','Valore da inserire in nell''xml index nel tag <legaldocIndex> nell''opzione ''documentClass''');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC','LEGALDOC_URL_REST','Url rest servizio legal doc per conservazione documentale');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC','LEGALDOC_TRIGGER_PATH','Valore da inserire nel file di trigger nel tag <path>');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC','TIPO_SERVIZIO','Valori : OFFLINE, WS. OFFLINE: la procedura crea i file necessari per mandare il documento in conservazione sulla cartella specificata in verticalizzazione, WS: la procedura invoca un ws per l'' dei documenti in conservazione, utilizzando l''indirizzo configurato in verticalizzazione');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC','USER_NAME_REST_SERVICE','user name per invoare servizio rest');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC','PSW_NAME_REST_SERVICE','password  per invoare servizio rest');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC','SOLO_FIRMATI','Pò assumere i valori 0(NULL),1. 1: andranno in conservazione solo i file firmati, 0: tutti i file presenti tra le estensioni ammesse.
Il parametro può sovrascrivere parzialmente il comportamento FILE_EXTENSIONS; se tra le estesioni ammesse è presnete  .doc non verrà mandato in conservazione in quanto file non firmato se il valore SOLO_FIRMATI = 1.
Anche se SOLO_FIRMATI = 1 il parametro FILE_EXTENSIONS dovrà essere popoalto con le possibili estensioni dei file firmati. Es. pdf, p7m,tsd ..');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('NODO_PAGAMENTI', 'ID_MODALITA_PAGAMENTO', 'Valorizzare con il codice identificativo dellà modalità di pagamento che indica il pagamento avvenuto On-Line. Le modalità di pagamento sono gestite dalla voce di menù Archivi -> Archivi di base -> Tabelle -> Modalità di pagamento');

insert into CONTENTTYPES (ct_mimetype,ct_extension) values ('x-application/pkcs7-mime',';p7m;');

update CONTENTTYPES set ct_extension=';p7c;' where ct_mimetype='application/pkcs7-mime';


Insert into VERTICALIZZAZIONIBASE (MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) values ('NODO_PAGAMENTI','Attiva la gestione dei pagamenti mediante il nodo pagamenti','0');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('NODO_PAGAMENTI','AR_COD_FISC_ENTE_CREDITORE','Usato da area riservata: Codice fiscale dell''ente creditore');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('NODO_PAGAMENTI','AR_URL_RITORNO','Usato da area riservata: Url di ritorno quando per comunicare l''esito di un pagamento terminato (es. ~/Reserved/InserimentoIstanza/pagamenti/VerificaStatoPagamentiNodoPagamenti.aspx)');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('NODO_PAGAMENTI','BLACKLIST_TIME_CHECK_PAGAM','Rappresenta dopo quanto tempo le posizioni debitorie non pagate devono finire in black list. Indicare una durata nel formato ISO come documentato in https://www.w3.org/TR/xmlschema-2/#duration. Esempi: per aggiungere 30 ore specificare P0Y0M0DT30H0M0S.');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('NODO_PAGAMENTI','ID_MODALITA_PAGAMENTO','Valorizzare con il codice identificativo dellà modalità di pagamento che indica il pagamento avvenuto On-Line. Le modalità di pagamento sono gestite dalla voce di menù Archivi -> Archivi di base -> Tabelle -> Modalità di pagamento');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('NODO_PAGAMENTI','URL_WS','indirizzo del wsdl del webservice  del nodo dei pagamenti es: http://devel9:8084/nodo-pagamenti/services/pagamentiSOAP?wsdl');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JIRIDE', 'VISUALIZZA_RICEVUTE_PEC', 'Impostare a 1 se si vuole visualizzare l''esito delle ricevute inviate via PEC dal sistema di protocollo, tra l''elenco degli allegati in fase di lettura di un protocollo.');


UPDATE mercati SET flag_contabilita=0 WHERE flag_contabilita IS NULL;
UPDATE mercati_d SET DISABILITATO=0 WHERE disabilitato IS NULL;


INSERT INTO CLMENU_JAVA(ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, SOFTWAREESCLUSI, LINK_STANDARD, TIPO_FUNZIONALITA, MENULINK_V2) VALUES 
(1043, 'Bollettazione', 'bollettazione/list.htm?software=SOFTWARE', '783', '*', 'JAVA', 'PR,FI', 'bollettazione/list.htm?software=SOFTWARE', 'S', '783');

INSERT INTO CLMENU_JAVA (ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, SOFTWAREESCLUSI, LINK_STANDARD, TIPO_FUNZIONALITA, MENULINK_V2) VALUES 
(1044, 'Bollettazione', 'bollcfgtipo/list.htm?software=SOFTWARE', '0AA', '*', 'JAVA', 'PR,FI', 'bollcfgtipo/list.htm?software=SOFTWARE', 'S', '0AA');
UPDATE CLMENU_JAVA SET PAGINA = 'bollgestione/list.htm?software=SOFTWARE', LINK_STANDARD = 'bollgestione/list.htm?software=SOFTWARE' WHERE ID = 1043;

UPDATE CLMENU_JAVA SET SOFTWARE = '*', SOFTWAREESCLUSI = 'PR,FI,AB' WHERE ID = 1039;

INSERT INTO TIPI_SCADENZA (ID, DESCRIZIONE) VALUES ('8', 'Fine mese successivo');

update verticalizzazioniparametribase set descrizione = 'E'' possibile configurare questo parametro per servire il file in maniera non autenticata ovvero visualizzando il QRCODE oppure in maniera autenticata (solamente per gli operatori di backoffice).
<br/>Per la modalita'' NON AUTENTICATA in questa URL va messo l''indirizzo dell''area riservata per il download del documento mediante GUID. Esempio http://<server_pubblico>/areariservata2/public_json/download/
<br/>Per la modalita'' AUTENTICATA in questa URL va messo l''indirizzo dell''applicativo di download-app per il download del documento mediante GUID e autenticazione di backoffice.
Esempio http://<server_pubblico>/download-app/downloadallegatimov/downloadDaQrcode.htm'
where modulo = 'QRCODE' and parametro='ALLEGATOPDF_URL_DOWNLOAD';

INSERT INTO verticalizzazioniparametribase(modulo, parametro, descrizione) 
    VALUES 
('CONSOLE', 'SERVIZI_CONSOLE_REMOTA', 
'Se i servizi di allineamento si riferiscono ad una console che non sta in una stessa installazione (console remota) allora va specificato questo parametro. Valori ammessi S e N (N è il valore predefinito');


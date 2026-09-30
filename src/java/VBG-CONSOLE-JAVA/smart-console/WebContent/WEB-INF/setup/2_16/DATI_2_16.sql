INSERT INTO verticalizzazioniparametribase(modulo,parametro,descrizione) VALUES('PROTOCOLLO_IRIDE','URL_PEC', 'Indicare in questo parametro l''endpoint da utilizzare per usare il servizio di invio posta elettronica certificata di Iride, sarà proprio questo servizio che si occuperà di inviare la PEC');
INSERT INTO verticalizzazioniparametribase(modulo,parametro,descrizione) VALUES('PROTOCOLLO_IRIDE','MEZZO_PEC','Indicare il mezzo da utilizzare se si vuole inviare la PEC richiamando il web service PosteWeb di Iride. Se il protocollo è in partenza e sono presenti dei destinatari esterni (che non abbiano scrivanie su Iride) è necessario indicare il mezzo per cui, al destinatario, venga inviata anche una PEC tramite il servizio PostePec di Iride indicato nel parametro URL_PEC, se sarà utilizzato quindi il mezzo indicato in questo parametro il sistema si occuperà di chiamare quindi il servizio di invio pec di iride'); 

INSERT INTO CLMENU_JAVA (ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, LAYOUTTESTI, SOFTWAREESCLUSI) VALUES (992, 'Campi visura', 'fovisuracampi/create.htm?software=SOFTWARE', '852', '*', 'JAVA', '0', 'PR,FI,AB');
INSERT INTO CLMENU_JAVA (ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, LAYOUTTESTI, SOFTWAREESCLUSI) VALUES (993, 'Campi visura', 'fovisuracampi/create.htm?software=TT', '824', 'TT', 'JAVA', '0', 'PR,FI,AB'  );

INSERT INTO STP_MODALITA_APERTURA (ID,DESCRIZIONE,TIPO_SCHEDA) VALUES ('DOMANDA','Domanda','SET2');

UPDATE ALBEROPROC_ENDO SET FLAG_PUBBLICA=1 WHERE FLAG_PUBBLICA IS NULL;
UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE='(OBSOLETO : sostituito dai parametri URL_RICERCA_PF e URL_RICERCA_PG). URL completo del componente Anagrafe Seacher, se presente andrà a sovrascrivere quello di default che punta al compomente dotNet. Es. http://<ip-servert>:<port>/webapp/services/name_services?wsdl (http://devel9:8080/nla-pdd-ri/services/anagrafe?wsdl).' where MODULO='WSANAGRAFE' and PARAMETRO='URL';

UPDATE VERTICALIZZAZIONIPARAMETRI SET VALORE=REPLACE(VALORE,'AreaRiservata','areariservata') WHERE MODULO='AREA_RISERVATA' AND PARAMETRO='URL_APPLICAZIONE_FACCT';

UPDATE AMMINISTRAZIONI SET CODICE_CART = CODICEANCITEL WHERE (CODICEANCITEL IS NOT NULL OR CODICEANCITEL!='') AND (CODICE_CART IS NULL OR CODICE_CART='');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_IRIDE', 'MITTENTE_PEC', 'Inserire in questo parametro il valore relativo al mittente che invia la mail pec in fase di protocollazione in partenza, questo parametro è legato all''altro parametro URL_PEC in quanto, se non presente quest''ultimo, MITTENTE_PEC non viene utilizzato'); 

INSERT INTO FO_CONFIGURAZIONEBASE (CODICE, ETICHETTA, CHIAVE, FK_CONTESTO) VALUES (74, 'Archivio pratiche', 'TIPIARCHIVIOISTANZE.ARCHIVIO', 'AIP-FIL');
INSERT INTO FO_CONFIGURAZIONEBASE (CODICE, ETICHETTA, CHIAVE, FK_CONTESTO) VALUES (75, 'Archivio pratiche', 'TIPIARCHIVIOISTANZE.ARCHIVIO', 'AIP-LIS');
INSERT INTO FO_CONFIGURAZIONEBASE (CODICE, ETICHETTA, CHIAVE, FK_CONTESTO) VALUES (76, 'Posizione in archivio', 'ISTANZE.POSIZIONEARCHIVIO', 'AIP-LIS');
INSERT INTO FO_CONFIGURAZIONEBASE (CODICE, ETICHETTA, CHIAVE, FK_CONTESTO) VALUES (77, 'Posizione in archivio', 'ISTANZE.POSIZIONEARCHIVIO', 'AIP-FIL');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) values ('PROTOCOLLO_IRIDE','MOSTRA_METTI_ALLA_FIRMA','Attivando il pulsante c''è la possibilità di comunicare al software INTRANOS (Comune di Ravenna) che un documento deve essere firmato. Ogni successivo cambiamento di stato compiuto in INTRANOS + IRIDE viene comunicato al Backoffice tramite WebService (Es: "Il documento è stato firmato", "Il documento è stato protocollato", "Il documento è stato inviato"). I cambiamenti di stato vengono scritti nel backoffice nella sezione eventi del movimento e, nel caso di protocollazione, viene riportato in automatico il numero di protocollo nel movimento. Accetta valori S o N');

UPDATE MERCATI_D SET DISABILITATO = 0 WHERE DISABILITATO IS NULL;
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('STC','MAPPATURE_SCHEDE','Durante la creazione di un''istanza proveniente da STC, se impostato a 0 e il campo dinamico oggetto della mappatura non è presente tra le schede già collegate all''istanza allora vengono collegate all''istanza tutte le schede che lo contengono. Se impostato a 1 ed il campo dinamico oggetto della mappatura non è presente nelle schede dell''istanza non viene agganciata nessuna scheda all''istanza (e di fatto il valore del dato si perde). Le schede dinamiche che vengono agganciate all''istanza durante l’inserimento sono quelle configurate per l''albero, gli endo e la procedura.');

INSERT INTO SOFTWARE (CODICE, DESCRIZIONE, MODULOOPZIONALE, DESCRIZIONELUNGA, ACCESSORAPIDO, ORDINE) VALUES ('GC', 'Genio Civile - Sismica', '1', 'Genio Civile - Sismica', '0', '51');

INSERT INTO TIPI_SCADENZA (ID, DESCRIZIONE) VALUES (7,'Inizio mese');

INSERT INTO MAPOGGETTI (NOMETABELLA,NOMECAMPO) values ('DOCUMENTI_CONTABILITA','CODICEOGGETTO');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'MOSTRA_DATI_PROTOCOLLO_NULLI', 'Il parametro permette di decidere se in fase di lettura protocollo devono essere visualizzati i campi ''Dati protocollo'' che restituiscono un valore vuoto. Il parametro può assumere i valori 0 ed 1. 0 non mostrare valori vuoti,1 mostra i valori vuoti.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'TIPOSMISTAMENTODEFAULT', 'Indica quale tipo di smistamento utilizzare nel caso in cui arrivino pratiche da online e la voce da selezionare di default nella maschera di protocollazione sia istanza che movimento alla voce TIPO SMISTAMENTO. Va inserito un codice presente nella tabella PROTOCOLLO_SMISTAMENTI');

INSERT INTO verticalizzazionibase(modulo, descrizione) VALUES('PROTOCOLLO_SIGEDO','Se attivato consente di eseguire la protocollazione con l''applicativo SIGEDO, questo tipo di protocollo utilizza in parte gli Standard DocArea, percui molti parametri saranno gli stessi di quel tipo di protocollo, in più però sono state aggiunte alcune funzionalità specifiche per il Comune di Firenze, tra cui il leggi protocollo');

INSERT INTO verticalizzazioniparametribase(modulo, parametro, descrizione ) VALUES ('PROTOCOLLO_SIGEDO', 'URL_STRUTTURASOA', 'Indicare l''url del web service utilizzato per recuperare i valori relativi alle unità protocollanti (uffici). La funzionalità di questo web service sarà utilizzata solamente in fase di lettura del protocollo per fare in modo compaia la descrizione dell''ufficio assegnatario sulla lista dei mittenti / destinatari. Da notare che questo servizio restituisce tutta la struttura degli uffici non potendo utilizzare filtri, sarà quindi cura del componente comparare il codice restituito dal servizio di lettura con quelli restituiti da questo web service');

INSERT INTO verticalizzazioniparametribase(modulo, parametro, descrizione ) VALUES ('PROTOCOLLO_SIGEDO', 'CANALE_STRUTTURASOA','Indica l''ambito per il quale andare a cercare le unità protocollanti (uffici), i valori da impostare sono: TEST per l''ambiente di test e PRODUZIONE per l''ambiente di produzione, questo parametro va di pari passo con il parametro URL_STRUTTURASOA. NB. Se non impostato verrà restituita un''eccezione');

INSERT INTO verticalizzazioniparametribase(modulo, parametro, descrizione ) VALUES ('PROTOCOLLO_SIGEDO', 'INVIA_CF','1 = invia, 0 o altro = non invia. Indica se in fase di protocollazione deve essere inviato il codice fiscale come attributo chiave riguardante il mittente / destinatario (valore 1) oppure no (altro valore o null). Se viene inviato il web service di protocollazione verifica se l''anagrafica è già presente, in caso negativo inserisce i dati nel proprio archivio altrimenti no. Nel caso in cui non venga inviato i dati saranno trattati come testo non andando a fare alcuna operazione sugli archivi di Sigedo. NB. Nel caso in cui il valore sia uguale a 1 sarà fatto il controllo se i mittenti / destinatari, indicati per la protocollazione, abbiano il codice fiscale o la partita iva, in caso negativo il sistema restituirà un''eccezione prima della chiamata al web service di protocollo.');

INSERT INTO verticalizzazioniparametribase(modulo, parametro, descrizione) VALUES('PROTOCOLLO_SIGEDO', 'URL_WS_ALLEGATI', 'Inserire in questo parametro l''url che indica l''endpoint del web service di recupero dati degli allegati, da questo web service è possibile visualizzare l''oggetto che viene passato direttamente dentro il web service, se non viene valorizzato questo parametro sarà possibile leggere il protocollo, ma, nel momento in cui si cerca di visualizzare gli allegati verrà restituito un errore');

INSERT INTO verticalizzazioniparametribase(modulo, parametro, descrizione) VALUES('PROTOCOLLO_SIGEDO', 'USERNAME_WS_ALLEGATI', 'Inserire in questo paramtro lo username necessario a collegarsi al web service di recupero allegati descritto nel parametro URL_WS_ALLEGATI');

INSERT INTO verticalizzazioniparametribase(modulo, parametro, descrizione) VALUES('PROTOCOLLO_SIGEDO', 'PASSWORD_WS_ALLEGATI', 'Inserire in questo paramtro la password necessaria a collegarsi al web service di recupero allegati descritto nel parametro URL_WS_ALLEGATI');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('AREA_RISERVATA', 'DIMENSIONE_MASSIMA_ALLEGATI','Indica il limite in bytes dei file caricabili per ogni domanda. Il valore di default è 52428800 bytes (50 Mb).');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES('AREA_RISERVATA','WARNING_DIMENSIONE_MASSIMA_ALL','Il messaggio per avvisare l''utente che ha superato la dimensione massima dei file caricabili per una domanda. Il messaggio di default e'' ''<b>Attenzione! E'' stato raggiunto il limite di 50 Mb di allegati e la domanda non può essere inviata. <br />Si prega di verificare la dimensione dei singoli file ottimizzandone la risoluzione.</b>''');

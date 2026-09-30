Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('FIRMA_REMOTA_ARUBA','DETACHED','Utilizzato per la generazione della busta CAdES senza documento originale. Può assumere i valori 0(NULL), 1. 0: Busta CAdES contenente documento originale; 1: Busta CAdES non contenente documento originale');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('FIRMA_REMOTA_ARUBA','RETURN_DER','Utilizzato per la generazione della busta CAdES in formato BER. Può assumere i valori 0(NULL), 1. 0: Busta CAdES non in formato BER; 1: Busta CAdES nin formato BER');

Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('TIPO_INSTALLAZIONE','OVERRIDE_MENU_STANDARD','La lista degli identificativi dei menu, separati da virgola per i quali prendere il link ENTERPRISE al posto di quello STANDARD');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('APPLICA_LAYER_PROT_PDF', 'IS_BORDO_ANNOTAZIONE_ATTIVO', 'Può assumere i valori 0(NULL),1. 0: Bordo annotazione non presente; 1: Bordo annotazione presente');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('PROTOCOLLO_ATTIVO','TIMEOUT_CHIAMATA_WS_INTERNA','Timeout della chiamata tra il backend e l''interfaccia dei servizi di protocollazione .NET. Se non specificato vale 600 secondi (10 minuti)');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('PROTOCOLLO_DATAGRAPH', 'E'' il sistema di protocollo creato dalla ditta Datagraph. E'' un''estensione del protocollo STADANRD DOCAREA, nel quale segue tutte le sue regole ma le estende con delle proprie (ad esempio il leggi protocollo). Tutti i parametri relativi alle funzionalità Stadandard DocArea devono essere esposte sulla regola PROTOCOLLO_DOCAREA, eventuali regole relative alle funzionalità aggiuntive proprietarie solo di Datagraph vanno aggiunte qui.');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('SCRIVANIA_ENTI_TERZI', 'Attivazione della "Scrivania Virtuale" dove sarà possibile, agli operatori dell’ente terzo, interagire mediante la piattaforma web visualizzando le pratiche di propria competenza per i servizi alle imprese/cittadini attive presso il portale al fine di poter fare consultazioni e/o esprimere pareri di loro competenza.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_FOLIUM', 'ELENCO_EST_CONTENUTO', 'Indicare l''elenco delle estensioni che sono accettate nel file principale, le estensioni devono essere indicate con il punto e separate da punto e virgola, quindi, ad esempio: .pdf;.doc;.docx;....da notare che, in caso di protocollo in arrivo, non sarà valorizzato il documento principale (detto contenuto), ma tutti i files saranno inseriti tra gli allegati. In partenza invece, la protocollazione sarà bloccata tramite un messaggio, la protocollazione quindi, non andrà a buon fine. Questo controllo si è reso necessario in quanto, il documento principale, deve essere necessariamente un file che possa essere convertito in pdf.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'ABILITA_INDIRIZZI_EMAIL', 'Valore 1 o 0. Se 1 sarà visualizzata, nell''interfaccia di protocollazione, una textarea dove poter inserire l''indirizzo pec dell''anagrafica in questione. Tale textarea sarà visualizzata presente su ogni anagrafica / amministrazione che sarà inserita in tale maschera, sarà quindi presente per le protocollazioni in arrivo e partenza. Darà la possibilità di inviare uno o più indirizzi e-mail relativi comunque all''anagrafica. Come dato andrà ad aggiungersi al mezzo e alla modalità di trasmissione.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_SIDUMBRIA', 'DISABILITA_CONTROLLO_P7M', 'Valore 1 o altro. Se valorizzato a 1 disabilita il controllo sull''esistenza dei files p7m durante la protocollazione automatica (su quella manuale non esiste alcun controllo in tal senso), e invia tutti i file presenti, invece che solo quelli con estensione p7m. Se invece non viene valorizzato o viene valorizzato diverso da 1, saranno inviati solo i file con estensione p7m, se non presenti verrà sollevato un errore (solo su protocollazione automatica da online).');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('ALLINEAMENTO_ANAGRAFE_FORLIV', 'Se 1 indica che è attivo l''allineamento dell''anagrafe dell''Unione della Romagna Forlivese');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('ALLINEAMENTO_ANAGRAFE_FORLIV','OWNER','Owner della vista da interrogare');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('ALLINEAMENTO_ANAGRAFE_FORLIV','VIEW','Nome della vista da interrogare');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('ALLINEAMENTO_ANAGRAFE_FORLIV','PROVIDER','Provider della vista da interrogare');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('ALLINEAMENTO_ANAGRAFE_FORLIV','CONNECTIONSTRING','Stringa di connessione della vista da interrogare');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('SCRIVANIA_ENTI_TERZI', 'SOFTWARE_ATTIVAZIONE', 'Modulo software nel quale viene gestita la scrivania enti terzi');       


INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('COMPORTAMENTI_MERCATI','Permette di attivare alcune impostazioni per le manifestazioni/fiere ');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('COMPORTAMENTI_MERCATI','GESTISCI_PROPRIETARIO','Permette di indicare se visualizzare / nascondere la colonna proprietario nella gestione delle presenze. valori S o N (predefinito S)');

INSERT
INTO VERTICALIZZAZIONIPARAMETRIBASE
  (
    MODULO,
    PARAMETRO,
    DESCRIZIONE
  )
  VALUES
  (
    'QRCODE',
    'ALLEGATOPDF_URL_DOWNLOAD',
    'In questa URL va messo l''indirizzo dell''area riservata per il download del documento mediante GUID. Esempio http://<server_pubblico>/areariservata2/public_json/download/'
  );
  
INSERT
INTO VERTICALIZZAZIONIPARAMETRIBASE
  (
    MODULO,
    PARAMETRO,
    DESCRIZIONE
  )
  VALUES
  (
    'QRCODE',
    'ALLEGATOPDF_WIDTH',
    'Dimensione in larghezza del qrcode da generare. default 150'
  );


  
INSERT
INTO VERTICALIZZAZIONIPARAMETRIBASE
  (
    MODULO,
    PARAMETRO,
    DESCRIZIONE
  )
  VALUES
  (
    'QRCODE',
    'ALLEGATOPDF_HEIGHT',
    'Dimensione in altezza del qrcode da generare. default 150'
  );
  
  
  INSERT
INTO VERTICALIZZAZIONIPARAMETRIBASE
  (
    MODULO,
    PARAMETRO,
    DESCRIZIONE
  )
  VALUES
  (
    'QRCODE',
    'ALLEGATOPDF_POS_X',
    'Coordinata delle ascisse per posizionare il QRCODE nel PDF.'
  );
    INSERT
INTO VERTICALIZZAZIONIPARAMETRIBASE
  (
    MODULO,
    PARAMETRO,
    DESCRIZIONE
  )
  VALUES
  (
    'QRCODE',
    'ALLEGATOPDF_POS_Y',
    'Coordinata delle ordinate per posizionare il QRCODE nel PDF.'
  );
  
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE
  (
    MODULO,
    PARAMETRO,
    DESCRIZIONE
  )
  VALUES
  (
    'QRCODE',
    'ALLEGATOPDF_PAGE_NUM',
    'Numero della pagina dove posizionare il qrcode. default 1'
  );

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE
  (
    MODULO,
    PARAMETRO,
    DESCRIZIONE
  )
  VALUES
  (
    'QRCODE',
    'ALLEGATOPDF_AUTOMATICO',
    'Indica se applicare di default a tutti i PDF il QRCODE durante la conversione da altro formato. Valori possibili (S o N - Default N)'
  );
  

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) 
    VALUES ('COMPORTAMENTI_ISTANZE', 'DIS_CANC_MOV_PROTOCOLLATI', 
    'Inibisce la possibilita'' di cancellare movimenti o allegati di movimenti che sono stati protocollati. Se attivo allora anche il numero/data protocollo saranno posti in sola lettura');

    UPDATE ALBEROPROC SET FLAGESCLUDISORTEGGIO = 0 WHERE FLAGESCLUDISORTEGGIO IS NULL;
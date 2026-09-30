insert into LAYOUTPAGINEBASE(LP_OGGETTO,DESCRIZIONE) VALUES ('fldIstanzeDomicilioElettronico','Mostra o nasconde, nella scheda di una ISTANZA il campo DOMICILIO_ELETTRONICO');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('SIT_LDP', 'URL_GENERAZIONE_PDF_DOMANDA', 'Url da richiamare per ottenere il pdf dei dati compilati nel frontend di ldp. In produzione è http://siena.ldpgis.it/presentazione_pratiche_edilizie_online/pratiche/report_pratica_pdf_connector.php?identificativo_temporaneo={idDomandaEsteso}');

INSERT INTO SOFTWARE(CODICE, DESCRIZIONE, MODULOOPZIONALE, DESCRIZIONELUNGA, ACCESSORAPIDO, ORDINE) VALUES ('UR','Urbanistica',1,'Urbanistica',0,66);
UPDATE SOFTWARE SET ORDINE = 65 WHERE CODICE = 'SN';
UPDATE SOFTWARE SET ORDINE = 67 WHERE CODICE = 'P4';
UPDATE SOFTWARE SET ORDINE = 68 WHERE CODICE = 'S1';
UPDATE SOFTWARE SET ORDINE = 98 WHERE CODICE = 'T1';

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_DOCER', 'DISABILITA_METADATI', 'Se impostato a 1 il sistema di protocollazione non riceverà i metadati non obbligatori. Questa modifica si rende necessaria in quanto la logica della valorizzazione dei metadati al tipo documento è completamente gestita da codice, e può essere utile nel caso in cui ci sia un disallineamento con DocEr per quanto riguarda appunto i metadati.');

INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('A1','Concentrazione');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('AS','Apertura per subingresso');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('C','Chiusura volontaria');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('C1','Revoca-Decadenza');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('C2','Chiusura per concentrazione');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('CS','Chiusura per subingresso');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('T','Variazione trimestrale');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('A','Nuova attività');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('0','NESSUNA VARIAZIONE');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('E','Correzione');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VD','Variazione di denominazione');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VU','Varizione di ubicazione');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VS','Variazione di superficie');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDU','Variazione di denominazione e ubicazione');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDS','Variazione di denominazione e superficie');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VUS','Variazione di ubicazione e superficie');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDUS','Variazione di denominazione, ubicazione e superficie');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VM','Variazione di settore merceologico');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDM','Variazione di denominazione e settore merceologico');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VSM','Variazione di superficie e settore merceologico');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VUM','Variazione di ubicazione e settore merceologico');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDUM','Variazione di denominazione, ubicazione e settore merceologico');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDSM','Variazione di denominazione, superficie e settore merceologico');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VUSM','Variazione di ubicazione, superficie e settore merceologico');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDUSM','Variazione di denominazione, ubicazione, superficie e settore merceologico');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VT','Variazione di stato');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDT','Variazione di denominazione e stato');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VST','Variazione di superficie e stato');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VUT','Variazione di ubicazione e stato');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VMT','Variazione di settore merceologico e stato');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDUT','Variazione di denominazione, ubicazione e stato');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDST','Variazione di denominazione, superficie e stato');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDMT','Variazione di denominazione, settore merceologico e stato');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VUST','Variazione di ubicazione, superficie e stato');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VUMT','Variazione di ubicazione, settore merceologico e stato');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VSMT','Variazione di superficie, settore merceologico e stato');
INSERT INTO FVG_TIPIVARIAZIONI(CODICE,DESCRIZIONE) VALUES ('VDUSMT','Variazione di denominazione, ubicazione, superficie, settore merceologico e stato');


INSERT INTO LAYOUTPAGINEBASE (LP_OGGETTO, DESCRIZIONE) VALUES ('sezIstanzeAltreinformazioni', 'Mostra o nasconde nella pagina dell''istanza la sezione con il bottone ''ALTRE INFORMAZIONI''');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'NASCONDI_NOTE_MOVIMENTO', 'Se impostato a 1 nasconde le note del movimento di origine nelle integrazioni documentali (usato in alcuni comuni in cui nelle note vengono riportati commenti ad uso interno)');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'INTEGR_NO_INSERIMENTO_NOTE', 'Se impostato a 1 quando un utente effettua una integrazione non potrà aggiungere note al movimento (in alcuni comuni vogliono che l''integrazione sia composta solo da documenti firmati)');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'INTEGR_NO_NOMI_ALLEGATI', 'Se impostato a 1 quando un utente aggiunge un file ad una integrazione non ne potrà modificare la descrizione che deve necessariamente coincidere con il nome file');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_IRIDE', 'VERSIONE', 'Questo parametro sta ad indicare la versione del web service installata e che sarà utilizzata, i valori ammessi sono solamente IRIDE e J_IRIDE, nel caso in cui non sia specificato, oppure specificato erroneamente un valore diverso da quello indicato il sistema si comporterà come se questo parametro fosse valorizzato con IRIDE. Questo parametro va inoltre valorizzato, al momento, solo se utilizzato il web service di Invio PEC (wsPosteWeb) di Maggioli, in quanto, nella vecchia versione (IRIDE) veniva utilizzato il metodo inviaMailInterop mentre quella nuova utilizza il metodo inviaMail. In quest''ultimo caso sull''xml vanno specificati anche il parametro AOO e gli indirizzi destinatari.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_IRIDE', 'AOO', 'Valorizzare questo parametro solamente nel caso in il parametro VERSIONE sia valorizzato a J_IRIDE, in caso contrario sarà comunque ignorato, serve solamente per l''invio delle PEC in quanto il metodo utilizzato da J-IRIDE inviaMail (a differenza di IRIDE che utilizza inviaMailInterop) lo richiede in modo necessario.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_DOCER', 'INVIA_METADATI', 'Questo parametro indica se devono essere inviati o meno i metadati non obbligatori relativi alla tipologia documentaria. Se impostato a 1 allora saranno inviati i metadati altrimenti no. Questo parametro si è reso necessario in quanto la gestione dei metadati avviene in modo fisso, ossia direttamente da codice, questo sta a significare che VBG valorizza sempre gli stessi metadati indipendentemente dalla tipologia selezionata. Se, in DocEr, anche un solo metadato compilato, relativo alla tipologia documentaria, non è presente, lo stesso DocEr solleva un''eccezione, mandando in errore tutta la procedura, motivo per il quale, con questo parametro si può decidere se compilare o meno i metadati non obbligatori.');
INSERT INTO CLMENU_JAVA (ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, LAYOUTTESTI, SOFTWAREESCLUSI,LINK_STANDARD,TIPO_FUNZIONALITA) VALUES ('1026', 'Tipo Modulistica', 'tipimodelli/list.htm?software=TT', '00BS', 'TT', 'JAVA', '0', 'AB','tipimodelli/list.htm?software=TT','S');

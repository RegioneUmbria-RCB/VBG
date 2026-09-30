Insert into SOFTWARE (CODICE,DESCRIZIONE,MODULOOPZIONALE,DESCRIZIONELUNGA,ACCESSORAPIDO,ORDINE) values ('ST','Sport',1,'Sport',0,48);

Insert Into Software (Codice,Descrizione,Moduloopzionale,Descrizionelunga,Accessorapido,Ordine) Values ('CW','Sportello Edilizia',1,'Sportello Unico Edilizia',0,50);

Update Software Set Ordine=49 Where Codice='AP';

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE VALUES ('STC','NLA_IDNODO_PEC_MUTA','Id del nodo NLA PEC MUTA registrato su STC (utilizzato per individuare le chiamate provenienti dal nodo NLA PEC MUTA)');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('NLA-RICEZIONE-PEC', 'Se abilitato, il nodo nla-pec processa le PEC presenti nelle caselle di posta certificata dei rispettivi software (questo modulo va attivato per i singoli software e non per il software TT)');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('NLA-RICEZIONE-PEC', 'PEC_COMUNICA_CAMERACOM', 'I valori ammessi sono 0, 1 o 2. Se posto a 0 non processa i messaggi PEC provenienti dalla Camera di Commercio, se posto a 1 processa le PEC formattate secondo lo standard nazionale della Camera di Commercio, se posto a 2 processa le PEC formattate secondo lo standard della Lombardia ');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('NLA-RICEZIONE-PEC', 'PEC_AREARISERVATA', 'I valori ammessi sono S o N. Se posto a S processa i messaggi di PEC provenienti dall''Area Riservata, se posto a N non vengono processati questa tipologia di messaggi');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('NLA-RICEZIONE-PEC', 'PEC_CITTADINO', 'I valori ammessi sono S o N. Se posto a S processa i messaggi di PEC inviati direttamente dai Cittadini/Imprese (formattati in base al DPR160), se posto a N non vengono processati questa tipologia di messaggi');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('NLA-RICEZIONE-PEC', 'PEC_ENTITERZI', 'I valori ammessi sono S o N. Se posto a S processa i messaggi provenienti dagli enti terzi (formattati in base al DPR160) ; se posto a N non vengono processati questa tipologia di messaggi');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('NLA-RICEZIONE-PEC', 'PEC_RICEVUTE_CONTROLLO', 'I valori ammessi sono S o N. Se posto a S vengono elaborati tutti i messaggi di notifica presenti nella casella PEC ; se posto a N le notifiche non vengono gestite');


insert into verticalizzazionibase(modulo, descrizione) values('PROTOCOLLO_DOCPRO','Ha le stesse identiche caratteristiche del protocollo DOCAREA, infatti è certificato DOCAREA, ma per errore l''azienda fornitrice (SCAP Sistemi srl) ha chiamato il metodo di Login in maniera non corretta (LoginUser invece che Login) costringendo quindi la creazione di questa verticalizzazione.');

insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_DOCPRO','OPERATORE','(Obbligatorio) E'' l''operatore da utilizzare per protocollare con DOCPRO, deve essere fornito dall''amministratore del protocollo DOCPRO.'); insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_DOCPRO','URL','(Obbligatorio) E'' l''URL per invocare il protocollo. Non devono essere specificati i metodi.'); insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_DOCPRO','PASSWORD','(Obbligatorio) E'' la password per protocollare in DOCPRO, deve essere fornita dall''amministratore del protocollo DOCPRO.'); insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_DOCPRO','CODICEENTE','(Obbligatorio) E'' il codice ente per il quale protocollare, deve essere fornito dall''amministratore del protocollo DOCPRO.'); insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_DOCPRO','INVIA_ALL_MOV_AVVIO','(Facoltativo) Se valorizzato a 1 va a cercare in automatico, solamente durante la protocollazione da istanza, gli allegati caricati nel movimento di avvio dell''istanza stessa, se esistono questi vengono inviati al protocollo DOCPRO. Se valorizzato con valore diverso da 1 o non valorizzato la funzionalità appena descritta non sarà svolta.');

insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_DOCPRO','INVIA_SEGNATURA','(Facoltativo) Se valorizzato a 1 consente, in fase di protocollazione istanza o movimento da backoffice, di inviare il file segnatura.xml che sarebbe il file che viene inviato al web service per eseguire la protocollazione; se valorizzato a 0 o non valorizzato non esegue nessuna funzionalità.'); insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_DOCPRO','CODICE_AOO','(Facoltativo) E'' il codice dell''Area Organizzativa Omogenea, va fornito dagli amministratori del protocollo. In precedenza veniva passato un parametro fisso con valore = ''AOO'' è stato modificato in quanto in alcuni casi (ad esempio Piacenza e Asp Roma) veniva richiesto un valore diverso. Sulle specifiche DOCPRO viene descritto come: 
viene inizializzato con un valore che identifica l''ambito dell''applicazione.
Ad esempio questo codice potrebbe essere utilizzato, per individuare i messaggi provenienti dal portale.
Ovviamente si puo’ scegliere come nel caso a) di usare una codifica del tipo P_X dove X e’ il nome dell’applicazione chiamante.
Questo valore viene inserito nel file segnatura.xml dentro 
Intestazione-->Identificatore-->CodiceAOO
 e dentro
Intestazione -->Classifica -->CodiceAOO
Se non valorizzato il parametro prenderà il valore di AOO');
 
insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_DOCPRO','TIPO_DOCUMENTO_ALLEGATO','(Facoltativo) Specificare il valore del tipo documento allegato ossia quei documenti allegati al protocollo escluso quello principale.
Se non valorizzato prenderà il valore = ''Allegato'' che era il valore fisso che veniva passato in precedenza.
Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento successivamente a quello principale.');
 
insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_DOCPRO','TIPO_DOCUMENTO_PRINCIPALE','(Facoltativo) Specificare il valore del tipo documento principale. ossia quel documento principale o di richiesta del protocollo.
Se non valorizzato prenderà il valore = ''Principale'' che era il valore fisso che veniva passato in precedenza.
E'' stato parametrizzato perchè a Piacenza viene richiesto un valore specifico che è ''TRAS''.
Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento');
 
insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_DOCPRO','APPLICATIVO_PROTOCOLLO','(Facoltativo) Indica il nome dell''applicativo del protocollo, questa voce sarà inserita dentro il file segnatura.xml dentro l''attributo -nome- di <ApplicativoProtocollo/>. NB. Se lasciato vuoto o non attivato prenderà il valore inserito dentro il parametro CODICEENTE'); insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_DOCPRO','UO','(Facoltativo) Indica l''ufficio di smistamento del protocollo DOCPRO, questa voce sarà inserita dentro il file segnatura.xml dentro il nodo <ApplicativoProtocollo> valorizzando un nuovo parametro con nome -uo-. E'' facoltativo ma il protocollo GS4 di ADS (Piacenza) lo richiede necessariamente.');

INSERT INTO contenttypes(ct_mimetype, ct_extension) VALUES('image/jp2',';jp2;'); 

UPDATE CLMENU_java SET verticalizzazione=NULL, descrizione='Configurazione mappature Front-end' WHERE ID=987;

Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (259,'RIC_EMAIL','Email del richiedente dell''istanza','Verra'' sostituito con l''indirizzo email del richiedente della pratica');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (260,'RIC_EMAILPEC','PEC del richiedente dell''istanza','Verra'' sostituito con l''indirizzo PEC del richiedente della pratica');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (261,'CO_EMAIL','Email dell''impresa dell''istanza','Verra'' sostituito con l''indirizzo email dell''impresa indicata come ragione sociale');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (262,'CO_EMAILPEC','PEC dell''impresa dell''istanza','Verra'' sostituito con l''indirizzo PEC dell''impresa indicata come ragione sociale');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (263,'TEC_EMAIL','Email del tecnico dell''istanza','Verra'' sostituito con l''indirizzo email dell''anagrafica individuata come tecnico/professionista della pratica');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (264,'TEC_EMAILPEC','PEC del tecnico dell''istanza','Verra'' sostituito con l''indirizzo PEC dell''anagrafica individuata come tecnico/professionista della pratica');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (265,'SOGCOL_EMAIL(CodiceTipoSoggetto)','Email del soggetto collegato','Verra'' sostituito con l''indirizzo email dell''anagrafica dei soggetti collegati identificati dalla tipologia riferita a (CodiceTipoSoggetto)');
Insert into segnaposto (id,tag,descrizione,help) values (266,'SOGCOL_EMAILPEC(CodiceTipoSoggetto)','PEC del soggetto collegato','Verra'' sostituito con l''indirizzo PEC dell''anagrafica dei soggetti collegati identificati dalla tipologia riferita a (CodiceTipoSoggetto)');

INSERT INTO MAPOGGETTI(NOMETABELLA,NOMECAMPO) VALUES ('TIPIPROCEDURE','CODICEOGGETTO_CERTINVIO');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('PROTOCOLLO_AIDA', 'Se 1 indica che il protocollo AIDA è attivo.'); INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_AIDA', 'PASSWORD', '(Obbligatorio) Specifica la password, riferita al parametro UTENTE, con cui effettuare la protocollazione.'); INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_AIDA', 'URL', '(Obbligatorio) E'' l''URL per invocare il web service del protocollo.'); INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_AIDA', 'POSSESSO', '(Obbligatorio) Specifica quale valore deve assumere il tag POSSESSO riferito all''assegnatario, di default deve essere impostato a 0.'); INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_AIDA', 'TIPO_ALLEGATI', '(Obbligatorio) Specifica quale valore deve assumere il tag TIPO durante la valorizzazione dell''xml riferito all''inserimento di un allegato (metodo SETDOCNPROT), di default deve assumere il valore 2.'); INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_AIDA', 'UTENTE', '(Obbligatorio) Specifica l''utente con cui effettuare la protocollazione.');
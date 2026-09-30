insert into contenttypes(ct_mimetype,CT_EXTENSION) values('application/x-rar-compressed',';rar;rev;r00;r01;');
insert into verticalizzazioniparametribase (modulo,parametro,descrizione) values ('CART','TEMPIFICAZIONE','Rappresenta l''id della tempificazione di default da associare agli endo procedimenti creati dal dizionario');
update clmenu_java set pagina = 'cart/view.htm?software=SOFTWARE' where id=986;
update helpbase set contenttype = '/cart/view.htm' where software='TT' and contenttype='/stp/pannellocontrollo.htm';
insert into categorieeventibase (id, descrizione) values ('STC', 'Comunicazioni tramite Stc');
insert into categorieeventibase (id, descrizione) values ('AVVERTIMENTI', 'Avvertimenti generati da funzionalità di business');
insert into CATEGORIEEVENTIBASE (id,DESCRIZIONE) values ('STC-INS-ATT','STC: Inserimento Attività') ;
insert into CATEGORIEEVENTIBASE (id,DESCRIZIONE) values ('STC-INS-PRA','STC: Inserimento Pratica') ;
delete from stili where st_id=2;
delete from stili where st_id=4;
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('CODICE_ISTANZA','Codice Istanza');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('DATA_ISTANZA','Data Istanza');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('NUMERO_PROTOCOLLO','Numero protocollo');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('DATA_PROTOCOLLO','Data protocollo');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('OGGETTO','Oggetto dell''istanza');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('CIVICO','Civico');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('NUMERO_AUTORIZZAZIONE','Numero autorizzazione');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('INDIRIZZO','Indirizzo');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('STATO_ISTANZA','Stato istanza');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('DATI_CATASTALI','Dati catastali');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('RICHIEDENTE','Richiedente');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('INTERVENTO','Intervento');
insert into FO_VISURA_CAMPI_BASE (ID,CAMPO) values ('RESPONSABILE_PROCEDIMENTO','Responsabile del procedimento');

insert into FO_VISURA_CONTESTI_BASE (ID,CONTESTO) values ('ARCHIVIO_FILTRI','Campi utilizzabili come filtri nell''arichivio istanze');
insert into FO_VISURA_CONTESTI_BASE (ID,CONTESTO) values ('ARCHIVIO_LISTA','Campi visualizzati nella lista dell''arichivio istanze');
insert into FO_VISURA_CONTESTI_BASE (ID,CONTESTO) values ('VISURA_FILTRI','Campi utilizzabili come filtri nella sezione ''le mie pratiche''');
insert into FO_VISURA_CONTESTI_BASE (ID,CONTESTO) values ('VISURA_LISTA','Campi visualizzati nella lista della sezione ''le mie pratiche''');

insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','DATI_CATASTALI');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','INDIRIZZO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','RICHIEDENTE');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','RESPONSABILE_PROCEDIMENTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','CODICE_ISTANZA');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','CIVICO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','DATA_PROTOCOLLO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','NUMERO_PROTOCOLLO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','DATA_ISTANZA');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','STATO_ISTANZA');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','NUMERO_AUTORIZZAZIONE');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','INTERVENTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_FILTRI','OGGETTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','DATA_PROTOCOLLO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','DATA_ISTANZA');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','CODICE_ISTANZA');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','CIVICO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','STATO_ISTANZA');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','RICHIEDENTE');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','RESPONSABILE_PROCEDIMENTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','OGGETTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','NUMERO_PROTOCOLLO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','NUMERO_AUTORIZZAZIONE');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','INTERVENTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','INDIRIZZO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('ARCHIVIO_LISTA','DATI_CATASTALI');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','INTERVENTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','OGGETTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','RESPONSABILE_PROCEDIMENTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','DATI_CATASTALI');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','DATA_PROTOCOLLO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','INDIRIZZO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','CIVICO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','CODICE_ISTANZA');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','NUMERO_PROTOCOLLO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','RICHIEDENTE');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','NUMERO_AUTORIZZAZIONE');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','STATO_ISTANZA');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_FILTRI','DATA_ISTANZA');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','OGGETTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','DATA_PROTOCOLLO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','RICHIEDENTE');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','DATI_CATASTALI');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','NUMERO_PROTOCOLLO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','INDIRIZZO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','INTERVENTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','CODICE_ISTANZA');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','NUMERO_AUTORIZZAZIONE');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','RESPONSABILE_PROCEDIMENTO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','STATO_ISTANZA');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','CIVICO');
insert into FO_VISURA_CONTESTI_CAMPI_BASE (FKIDCONTESTO,FKIDCAMPO) values ('VISURA_LISTA','DATA_ISTANZA');
delete from configurazioneutente where nomeparametro='StileBO' and valore in ('giallo.css','temporaneo.css');
UPDATE CLMENU_JAVA SET PAGINA = 'configurazioneutente/view.htm?software=TT', JSP = 'JAVA' WHERE ID=652;
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_DOCAREA','CODICE_AOO','(Facoltativo) E'' il codice dell''Area Organizzativa Omogenea, va fornito dagli amministratori del protocollo. 
In precedenza veniva passato un parametro fisso con valore = ''AOO'' è stato modificato in quanto 
in alcuni casi (ad esempio Piacenza e Asp Roma) veniva richiesto un valore diverso. Sulle specifiche DOCAREA viene descritto come: 
viene inizializzato con un valore che identifica l''ambito dell''applicazione.
Ad esempio questo codice potrebbe essere utilizzato,
per individuare i messaggi provenienti dal portale.
Ovviamente si puo’ scegliere come nel caso a) di usare una codifica del tipo P_X dove X e’ il nome dell’applicazione chiamante
.
Questo valore viene inserito nel file segnatura.xml dentro 
Intestazione-->Identificatore-->CodiceAOO
 e dentro 
Intestazione -->Classifica -->CodiceAOO
Se non valorizzato il parametro prenderà il valore di AOO');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_DOCAREA','TIPO_DOCUMENTO_ALLEGATO','(Facoltativo) Specificare il valore del tipo documento allegato ossia quei documenti allegati al protocollo escluso quello principale.
Se non valorizzato prenderà il valore = ''Allegato'' che era il valore fisso che veniva passato in precedenza. 
Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento successivamente a quello principale.');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_DOCAREA','TIPO_DOCUMENTO_PRINCIPALE','(Facoltativo) Specificare il valore del tipo documento principale. ossia quel documento principale o di richiesta del protocollo.
Se non valorizzato prenderà il valore = ''Principale'' che era il valore fisso che veniva passato in precedenza. 
E'' stato parametrizzato perchè a Piacenza viene richiesto un valore specifico che è ''TRAS''.
Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento');
Insert into VERTICALIZZAZIONIBASE (MODULO,DESCRIZIONE) values ('PROTOCOLLO_PALEO','Se 1 indica che il protocollo PALEO è attivo.');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_PALEO','CODICE_AMMINISTRAZIONE','(Obbligatorio) E'' il codice amministrazione da utilizzare per protocollare con PALEO, deve essere fornito dall''amministratore del protocollo PALEO.');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_PALEO','COGNOME_OPERATORE','(Obbligatorio) Indicare il cognome, la ragione sociale o la denominazione in genere del corrispondente.');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_PALEO','NOME_OPERATORE','(Opzionale) Indicare il nome o una denominazione aggiuntiva del corrispondente.');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_PALEO','PASSWORD','(Obbligatorio) E'' la password per protocollare in PALEO, deve essere fornita dall''amministratore del protocollo PALEO.');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_PALEO','REGISTRO','(Obbligatorio) Indica il registro su cui effettuare la protocollazione; indicare un registro valido per la UO del richiedente.');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_PALEO','URL','(Obbligatorio) E'' l''URl per invocare il protocollo. Non devono essere specificati i metodi.');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_PALEO','USERNAME','(Obbligatorio) Username da utilizzare per protocollare con PALEO, deve essere fornito dall''amministratore del protocollo PALEO.');
update istanze set azione='=' where azione is null;
delete from verticalizzazioniparametri where modulo='CART' AND PARAMETRO='DESTINATARIO';
delete from verticalizzazioniparametri where modulo='CART' AND PARAMETRO='INTEGRATION_MANAGER_URL';
delete from verticalizzazioniparametri where modulo='CART' AND PARAMETRO='MITTENTE';
delete from verticalizzazioniparametri where modulo='CART' AND PARAMETRO='PASSWORD';
delete from verticalizzazioniparametri where modulo='CART' AND PARAMETRO='PDD_LOCATION';
delete from verticalizzazioniparametri where modulo='CART' AND PARAMETRO='SERVIZIO';
delete from verticalizzazioniparametri where modulo='CART' AND PARAMETRO='SERVIZIOAPPLICATIVO';
delete from verticalizzazioniparametri where modulo='CART' AND PARAMETRO='TIPODESTINATARIO';
delete from verticalizzazioniparametri where modulo='CART' AND PARAMETRO='TIPOMITTENTE';
delete from verticalizzazioniparametri where modulo='CART' AND PARAMETRO='TIPO_SERVIZIO';
delete from verticalizzazioniparametri where modulo='CART' AND PARAMETRO='USER_NAME';

delete from verticalizzazioniparametribase where modulo='CART' AND PARAMETRO='DESTINATARIO';
delete from verticalizzazioniparametribase where modulo='CART' AND PARAMETRO='INTEGRATION_MANAGER_URL';
delete from verticalizzazioniparametribase where modulo='CART' AND PARAMETRO='MITTENTE';
delete from verticalizzazioniparametribase where modulo='CART' AND PARAMETRO='PASSWORD';
delete from verticalizzazioniparametribase where modulo='CART' AND PARAMETRO='PDD_LOCATION';
delete from verticalizzazioniparametribase where modulo='CART' AND PARAMETRO='SERVIZIO';
delete from verticalizzazioniparametribase where modulo='CART' AND PARAMETRO='SERVIZIOAPPLICATIVO';
delete from verticalizzazioniparametribase where modulo='CART' AND PARAMETRO='TIPODESTINATARIO';
delete from verticalizzazioniparametribase where modulo='CART' AND PARAMETRO='TIPOMITTENTE';
delete from verticalizzazioniparametribase where modulo='CART' AND PARAMETRO='TIPO_SERVIZIO';
delete from verticalizzazioniparametribase where modulo='CART' AND PARAMETRO='USER_NAME';
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'ATECO_PRIMARIA_ID_CAMPO', 'Id del campo dinamico in cui riportare il codice dell''attività ateco che è stata selezionata in fase di individuazione dell''intervento');
INSERT into VERTICALIZZAZIONIBASE (MODULO,DESCRIZIONE) values ('FILESYSTEM_PROTOCOLLO','Attenzione!!! Questa funzionalità va attivata solo da personale esperto di In.I.T.. Se attivata, tutti i documenti del Protocollo Informatico verranno salvati su disco e non più su database. Dopo aver attivato la verticalizzazione è necessario eseguire l''applicativo ALLINEAOGGETTIPROTOCOLLO.exe che serve per spostare tutti gli oggetti da DB a filesystem o viceversa.');
INSERT into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('FILESYSTEM_PROTOCOLLO','DIRECTORY_LOCALE','Rappresenta il percorso alla directory del server dove vengono salvati/letti i file riguardanti il protocollo.');
INSERT INTO VERTICALIZZAZIONIBASE ( MODULO, DESCRIZIONE ) VALUES ( 'FILESYSTEM_CMIS', 'Attenzione!!! Questa funzionalità va attivata solo da personale esperto di In.I.T.. Se attivata, tutti i documenti dell''applicativo verranno salvati su Sistema di gestione documentale esterno che espone interfacce CMIS (Content Management Interoperability Services) es. <b>ALFRESCO</b> e non più su database. Dopo aver attivato questa funzionalità bisogna trasferire su disco tutti gli oggetti attualmente presenti su DB, utilizzando l''utility  o dalla voce di menù "utilità/Spostamento oggetti su systema CMIS". L''attivazione è valida solo per il software TT. Se attivata, tutti i documenti dell''applicativo verranno salvati su Sistema che espone interfacce CMIS Esterno all''applicativo., La funzionalità non può essere attivata contemporaneamente a quella su FILESYSTEM' );
INSERT INTO verticalizzazioniparametribase ( modulo, parametro, descrizione ) VALUES ( 'FILESYSTEM_CMIS', 'CMIS_ATOM_URL', 'Rappresenta il percorso servizio di pubblicazione CMIS es: http://&lt;server&gt;:&lt;port&gt;/alfresco/s/cmis.' );
INSERT INTO verticalizzazioniparametribase ( modulo, parametro, descrizione ) VALUES ( 'FILESYSTEM_CMIS', 'CMIS_USERNAME', 'Rappresenta l''identificativo utente abilitato ad operare sulle interfacce del servizio di pubblicazione CMIS' );
INSERT INTO verticalizzazioniparametribase ( modulo, parametro, descrizione ) VALUES ( 'FILESYSTEM_CMIS', 'CMIS_PASSWORD', 'Rappresenta la password dell'' utente abilitato ad operare sulle interfacce del servizio di pubblicazione CMIS' );
INSERT INTO verticalizzazioniparametribase ( modulo, parametro, descrizione ) VALUES ( 'FILESYSTEM_CMIS', 'CMIS_DOCUMENT_ROOT_FOLDER', 'Rappresenta la directory a partire dalla quale inserire i file mediante le interfacce del servizio di pubblicazione CMIS' );

INSERT INTO verticalizzazioniparametribase ( modulo, parametro, descrizione ) VALUES ( 'CART', 'CART_PROXY_ENABLED', 'Se abilitata la gestione del proxy per le chiamate verso integration manager. Ammette valori true o false(se non specificato allora false). Se true allora è necessario configurare CART_PROXY_HOST, CART_PROXY_PORT e se il proxy è protetto da autenticazione anche PROXY_USER_NAME e PROXY_PASSWORD' );
INSERT INTO verticalizzazioniparametribase ( modulo, parametro, descrizione ) VALUES ( 'CART', 'CART_PROXY_HOST', 'L''indirizzo del proxy per le chiamate verso integration manager. es. 10.10.45.160' );
INSERT INTO verticalizzazioniparametribase ( modulo, parametro, descrizione ) VALUES ( 'CART', 'CART_PROXY_PORT', 'La porta del proxy per le chiamate verso integration manager. es. 8080' );
INSERT INTO verticalizzazioniparametribase ( modulo, parametro, descrizione ) VALUES ( 'CART', 'PROXY_USER_NAME', 'L''utente del proxy per le chiamate verso integration manager. es. tomcat' );
INSERT INTO verticalizzazioniparametribase ( modulo, parametro, descrizione ) VALUES ( 'CART', 'PROXY_PASSWORD', 'La password dell''utente del proxy per le chiamate verso integration manager. es. tomcatpwd' );

INSERT INTO SOFTWARE( ACCESSORAPIDO, CODICE, DESCRIZIONE, DESCRIZIONELUNGA, MODULOOPZIONALE, ORDINE ) VALUES ( 0, 'FQ', 'Fiere Quaresimali','Fiere Quaresimali',1,47);

UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = 'E'' il parametro che permette di stabilire se nella protocollazione automatica da FO occorre gestire gli allegati o meno, di default gli allegati sono gestiti. Es. 1=Allegati non gestiti, 0=Allegati gestiti' WHERE modulo = 'PROTOCOLLO_ATTIVO' and parametro = 'NOALLEGATIFO';
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'NOMEFILE_ORIGINE', 'Determina la descrizione d''origine a cui assegnare il nome file degli allegati durante la protocollazione. Se non inserito o = 0 il nome file sarà assegnato con la descrizione inserita nell''albero dei procedimenti, se = 1 la descrizione coinciderà con il nome del file originale.');
INSERT INTO VERTICALIZZAZIONIBASE ( MODULO, DESCRIZIONE ) VALUES ( 'TIPO_INSTALLAZIONE', 'Attenzione!!! Questa funzionalità va attivata solo da personale esperto di In.I.T.. Se attivata allora deve essere specificato attraverso il parametro TIPO se l''installazione è di tipo ENTERPRISE o STANDARD. Di default l''installazione è considerata ENTERPRISE' ); 
INSERT INTO verticalizzazioniparametribase ( modulo, parametro, descrizione ) VALUES ( 'TIPO_INSTALLAZIONE', 'TIPO', 'Identifica se l''installazione è di tipo STANDARD o ENTERPRISE. Di default se non specificato l''installazione è considerata ENTERPRISE' );

update clmenu_java set link_standard=pagina where pagina is not null;
update clmenu_java set pagina='Archivi/DatiDinamici/Dyn2Modelli.aspx?Software=SOFTWARE',jsp='NET' where id=158;
update clmenu_java set pagina='Archivi/DatiDinamici/Dyn2Modelli.aspx?Software=TT',jsp='NET' where id=447;
update clmenu_java set pagina='Archivi/DatiDinamici/Dyn2Campi.aspx?Software=SOFTWARE',jsp='NET' where id=159;
update clmenu_java set pagina='Archivi/DatiDinamici/Dyn2Campi.aspx?Software=TT',jsp='NET' where id=449;

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE ( MODULO, PARAMETRO, DESCRIZIONE ) VALUES ( 'FILESYSTEM', 'READONLY', 'Accetta valori 0 o 1. Nel caso che sia settato il valore 1 allora solamente i file che hanno percorso valorizzato vengono letti da filesystem ed i nuovi file vengono scritti nella colonna BLOB della tabella oggetti' );
UPDATE MOVIMENTI SET CODICEAMMINISTRAZIONE_STC=CODICEAMMINISTRAZIONE WHERE INVIATO_CON_STC=1;

UPDATE PROTOCOLLO_CONFIGURAZIONE SET CODTESTOISTANZE=NULL WHERE CODTESTOISTANZE=0;
UPDATE PROTOCOLLO_CONFIGURAZIONE SET CODTESTOMOVIMENTI=NULL WHERE CODTESTOMOVIMENTI=0;

UPDATE CLMENU_JAVA SET PAGINA = 'configurazione/createConfigurazioneMailAndTestiTpo.htm?software=SOFTWARE', JSP = 'JAVA', LINK_STANDARD = 'configurazione/createConfigurazioneMailAndTestiTpo.htm?software=SOFTWARE' WHERE ID=126;

insert into VERTICALIZZAZIONIPARAMETRIBASE (modulo,parametro,descrizione) values('PROTOCOLLO_DOCAREA', 'APPLICATIVO_PROTOCOLLO', 'Indica il nome dell''applicativo del protocollo, questa voce sarà inserita dentro il file segnatura.xml dentro l''attributo -nome- di <ApplicativoProtocollo/>. NB. Se lasciato vuoto o non attivato prenderà il valore inserito dentro il parametro CODICEENTE');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_DOCAREA', 'UO', '(Facoltativo) Indica l''ufficio di smistamento del protocollo DOCAREA, questa voce sarà inserita dentro il file segnatura.xml dentro il nodo <ApplicativoProtocollo> valorizzando un nuovo parametro con nome -uo-. E'' facoltativo ma il protocollo GS4 di ADS (Piacenza) lo richiede necessariamente.');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'Se 1 indica che il protocollo JPROTOCOLLO è attivo.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'USERNAME', 'Username utilizzato per invocare le funzionalità di protocollazione di JProtocollo, deve essere fornito dall''admin del Protocollo JProtocollo.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'URL', '(Obbligatorio) E'' l''URL per invocare il protocollo. Non devono essere specificati i metodi.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PALEO', 'UO_OPERATORE', '(Obbligatorio) Indica l''unità operativa dell''operatore abilitato alla protocollazione (parametri NOME_OPERATORE e COGNOME_OPERATORE), deve essere fornito dall''amministratore del protocollo PALEO.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PALEO', 'RUOLO_OPERATORE', '(Obbligatorio) Indica il ruolo dell''operatore abilitato alla protocollazione (parametri NOME_OPERATORE e COGNOME_OPERATORE), deve essere fornito dall''amministratore del protocollo PALEO.');
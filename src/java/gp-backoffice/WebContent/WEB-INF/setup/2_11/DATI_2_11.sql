INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'MITTENTE_LIBERO', 'Indica quale tipologia di mittente deve essere usata durante la protocollazione, se = 1 allora sarà utilizzato il valore presente dentro il parametro MITTENTE che quindi dovrà essere obbligatoriamente valorizzato, altrimenti se = 0 o non utilizzato saranno utilizzati i valori dei parametri CODICEENTE/AOO/UO');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'MITTENTE', 'Se il parametro MITTENTE_LIBERO è valorizzato a 1 allora sarà utilizzato il valore presente in questo parametro come SOGGETTO MITTENTE del protocollo.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'CODICEENTE', 'E'' il codice ente per il quale protocollare, deve essere fornito dall''amministratore del protocollo.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'AOO', 'Codice dell''AOO (Area Organizzativa omogenea) deve essere comunicata dall''amministratore del protocollo.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'UO', 'Codice dell''UO (Unità Operativa) deve essere comunicata dall''amministratore del protocollo.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'TRAMITEDEFAULT', 'E'' il codice del tramite con cui avviene la protocollazione, deve essere fornito dall''amministratore del protocollo.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'MITTENTEINTERNO', 'Indica il codice del mittente interno con cui avviene la protocollazione. Deve essere fornito dall''amministratore del protocollo.');
INSERT INTO CLMENU_JAVA (ID,DESCRIZIONE,PAGINA,MENULINK,SOFTWARE,JSP,LAYOUTTESTI,VERTICALIZZAZIONE,SOFTWAREESCLUSI,LINK_STANDARD) VALUES(994,'Natura Endo','naturaendo/list.htm?software=TT','00BQ','TT','JAVA',0,NULL,'AB','naturaendo/list.htm?Software=TT');

Insert into MASTERKEY (tablename,columnname) values ('ALBEROPROC_ONERI','AO_ID');
Insert into MASTERKEY (tablename,columnname) values ('TIPICAUSALIONERI','CO_ID');
Insert into MASTERKEY (tablename,columnname) values ('TIPIENDO','CODICE');
Insert into MASTERKEY (tablename,columnname) values ('TIPIFAMIGLIEENDO','CODICE');
Insert into MASTERKEY (tablename,columnname) values ('AMMINISTRAZIONI','CODICEAMMINISTRAZIONE');
Insert into MASTERKEY (tablename,columnname) values ('INVENTARIOPROCEDIMENTI','CODICEINVENTARIO');
Insert into MASTERKEY (tablename,columnname) values ('LETTERETIPO','CODICELETTERA');
Insert into MASTERKEY (tablename,columnname) values ('NATURAENDO','CODICENATURA');
Insert into MASTERKEY (tablename,columnname) values ('NORMATIVE','CODICENORMATIVA');
Insert into MASTERKEY (tablename,columnname) values ('OGGETTI','CODICEOGGETTO');
Insert into MASTERKEY (tablename,columnname) values ('TIPIPROCEDURE','CODICEPROCEDURA');
Insert into MASTERKEY (tablename,columnname) values ('TEMPIFICAZIONI','CODICETEMPIFICAZIONE');
Insert into MASTERKEY (tablename,columnname) values ('TIPISOGGETTO','CODICETIPOSOGGETTO');
Insert into MASTERKEY (tablename,columnname) values ('TIPICONTROMOVIMENTO','CONTATORE');
Insert into MASTERKEY (tablename,columnname) values ('ALBEROPROC_DOCUMENTICAT','ID');
Insert into MASTERKEY (tablename,columnname) values ('INVENTARIOPROCEDIMENTIINCOMP','ID');
Insert into MASTERKEY (tablename,columnname) values ('INVENTARIOPROC_LEGGI','ID');
Insert into MASTERKEY (tablename,columnname) values ('INVENTARIOPROCEDIMENTIPEOPLE','ID');
Insert into MASTERKEY (tablename,columnname) values ('ALLEGATI','ID');
Insert into MASTERKEY (tablename,columnname) values ('TESTIESTESI','ID');
Insert into MASTERKEY (tablename,columnname) values ('DOCUMENTI','ID');
Insert into MASTERKEY (tablename,columnname) values ('INVENTARIOPROCEDIMENTIONERI','ID');
Insert into MASTERKEY (tablename,columnname) values ('DYN2_CAMPI','ID');
Insert into MASTERKEY (tablename,columnname) values ('DYN2_MODELLID','ID');
Insert into MASTERKEY (tablename,columnname) values ('DYN2_MODELLIDTESTI','ID');
Insert into MASTERKEY (tablename,columnname) values ('DYN2_MODELLIT','ID');
Insert into MASTERKEY (tablename,columnname) values ('INVENTARIOPROC_TIPITITOLO','ID');
Insert into MASTERKEY (tablename,columnname) values ('ALBEROPROCPEOPLEOPER','ID');
Insert into MASTERKEY (tablename,columnname) values ('ALBEROPROCPEOPLEHREF','ID');
Insert into MASTERKEY (tablename,columnname) values ('ALBEROPROC_ARENDO','ID');
Insert into MASTERKEY (tablename,columnname) values ('LEGGI','LE_ID');
Insert into MASTERKEY (tablename,columnname) values ('LEGGITIPI','LT_ID');
Insert into MASTERKEY (tablename,columnname) values ('TIPIMODALITAPAGAMENTO','MP_ID');
Insert into MASTERKEY (tablename,columnname) values ('RAGGRUPPAMENTOCAUSALIONERI','RCO_ID');
Insert into MASTERKEY (tablename,columnname) values ('ALBEROPROC','SC_ID');
Insert into MASTERKEY (tablename,columnname) values ('ALBEROPROC_LEGGI','SL_ID');
Insert into MASTERKEY (tablename,columnname) values ('ALBEROPROC_DOCUMENTI','SM_ID');

UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE='Se S significa che SIGePro è integrato con un protocollo generale unico nel comune e quindi negli xml di comunicazione tra NLA e STC i tag NUMEROPROTOCOLLOGENERALE e DATAPROTOCOLLOGENERALE verranno compilati e scritti. Se N o non presente significa che SIGePro non è integrato con nessun protocollo o ha un protocollo di direzione. In questo caso i riferimenti a numero e data protocollo vengono compilati e scritti solamente nel caso che gli sportelli mittente/destinatario abbiano configurato in STC lo stesso identificativo di direzione.' WHERE MODULO='STC' AND PARAMETRO='PROTOCOLLOGENERALE';
UPDATE CLMENU_JAVA SET PAGINA='Protocollo/PROT_Fascicoli.asp?software=PR',LINK_STANDARD='Protocollo/Prot_Fascicoli.asp?software=PR' WHERE ID=433;
UPDATE CLMENU_JAVA SET PAGINA='Protocollo/PROT_Classificazione.asp?software=PR',LINK_STANDARD='Protocollo/PROT_Classificazione.asp?software=PR' WHERE ID=434;
DELETE FROM CLPERMMENU WHERE FKIDMENU=114;
DELETE FROM CLMENU_JAVA WHERE ID=114;
DELETE FROM CLPERMMENU WHERE FKIDMENU=108;
DELETE FROM CLMENU_JAVA WHERE ID=108;

Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (1,'DATAODIERNA','Data di oggi','Data di oggi');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (2,'DATAINSERITADALLUTENTE','Data inserita dall''utente','Data inserita dall''utente. Se vuota viene inserita la data di oggi.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (3,'INDIRIZZOWEB','Indirizzo web dell''amministrazione Spportello Unico','Indirizzo web dell''amministrazione Spportello Unico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (4,'OPERATORE','Responsabile da configurazione','Responsabile recuperato dalla configurazione del modulo');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (5,'CFG_SP_FAX','Fax da configurazione','Fax recuperato dai dati di configurazione del modulo. 
Se non presente viene recuperato dalla configurazione di base.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (6,'CFG_SP_TELEFONO','Telefono da configurazione','Telefono recuperato dai dati di configurazione del modulo. 
Se non presente viene recuperato dalla configurazione di base.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (7,'CFG_SP_EMAIL','Email da configurazione','Email recuperata dai dati di configurazione del modulo.
Se non presente viene recuperata dalla configurazione di base.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (8,'CFG_SP_ORARIO','Orario da configurazione','Orario recuperato dai dati di configurazione del modulo.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (9,'CFG_SP_DESCRAGG','Descrizione da configurazione','Descrizione recuperata dai dati di configurazione del modulo.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (10,'RICHIEDENTE','Nominativo completo del richiedente','Nominativo completo del richiedente. Nome + cognome.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (11,'RICHIEDENTECOGNOME','Cognome del richiedente','Cognome del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (12,'RICHIEDENTENOME','Nome del richiedente','Nome del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (13,'FORMAGIURIDICA','Forma giuridica del richiedente','Forma giuridica del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (14,'TITOLO','Titolo del richiedente','Titolo del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (15,'INDIRIZZO','Indirizzo del richiedente','Indirizzo del richiedente.
Viene preso l''indirizzo di corrispondenza se presente, altrimenti quello di residenza.
L''indirizzo comprende solo la via e il numero civico.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (16,'CITTA','Città del richiedente','Città del richiedente.
Viene preso il nome del comune di corrispondenza, se è assente viene utilizzato il comune di residenza.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (17,'CAP','CAP del richiedente','CAP del richiedente.
Viene preso il CAP dell''indirizzo di corrispondenza se è presente, altrimenti viene preso quello dell''indirizzo di residenza.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (18,'PROVINCIA','Provincia del richiedente','Sigla della provincia di corrispondenza del richiedente. Se non è presente viene presa la provincia di residenza.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (19,'LOCALITA','Località del richiedente','Località di corrisppondenza del richiedente. Se è assente viene presa la località di residenza.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (20,'INDIRIZZORESIDENZA','Indirizzo di residenza del richiedente','Indirizzo di residenza del richiedente. Solo via e numero civico.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (21,'CITTARESIDENZA','Città di residenza del richiedente','Città di residenza del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (22,'CAPRESIDENZA','CAP di residenza del richiedente','CAP di residenza del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (23,'PROVINCIARESIDENZA','Provincia di residenza del richiedente','Sigla della provincia di residenza del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (24,'LOCALITARESIDENZA','Località di residenza del richiedente','Località di residenza del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (25,'FAX','Fax del richiedentec','Fax del richiedentec');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (26,'TELEFONO','Telefono del richiedente','Telefono del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (27,'DATANASCITA','Data di nascita del richiedente','Data di nascita del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (28,'CODICEFISCALE','Codice fiscale del richiedente','Codice fiscale del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (29,'COMUNE','Comune di residenza del richiedente','Comune di residenza del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (30,'PARTITAIVA','Partita IVA del richiedente','Partita IVA del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (31,'DATANOMINATIVO','Data nominativo del richiedente','Data nominativo del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (32,'DATACOSTITUZIONE','Data nominativo del richiedente','Data nominativo del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (33,'CCIAANR','N. registrazione CCIAA del richiedente','Numero di registrazione alla camera di commercio del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (34,'CCIAADATA','Data registrazione CCIAA del richiedente','Data di registrazione alla camera di commercio del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (35,'CCIAACOMUNE','Comune registrazione CCIAA del richiedente','Comune di registrazione alla camera di commercio del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (36,'REGTRIBNR','N. registrazione tribunale del richiedente','Numero di registrazione al tribunale del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (37,'REGTRIBDATA','Data registrazione tribunale del richiedente','Data di registrazione al tribunale del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (38,'REGTRIBCOMUNE','Comune registrazione tribunale del richiedente','Comune di registrazione al tribunale del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (39,'LUOGODINASCITA','Luogo di nascita del richiedente','Luogo di nascita del richiedente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (40,'GENERALITALR','Legale rappresentante - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (41,'LUOGODINASCITALR','Luogo di nascita legale rappresentante - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (42,'DATANASCITALR','Data di nascita legale rappresentante - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (43,'RESIDENZAINDIRIZZOLR','Indirizzo residenza legale rappresentante - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (44,'RESIDENZACITTALR','Città residenza legale rappresentante - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (45,'RESIDENZACAPLR','CAP residenza legale rappresentante - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (46,'RESIDENZAPROVINCIALR','Provincia residenza legale rappresentante - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (47,'TECNICO','Nominativo del tecnico','Nominativo completo del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (48,'TEC_RICHIEDENTE','Nominativo del tecnico','Nominativo completo del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (49,'EMAILTECNICO','Email del tecnico','Indirizzo email del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (50,'TEC_INDIRIZZO','Indirizzo del tecnico','Indirizzo del tecnico (solo via e numero civico)');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (51,'TEC_CITTA','Città del tecnico','Città del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (52,'TEC_CAP','CAP del tecnico','CAP del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (53,'TEC_PROVINCIA','Provincia del tecnico','Sigla della provincia del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (54,'TEC_CODICEFISCALE','Codice fiscale del tecnico','Codice fiscale del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (55,'TEC_TITOLO','Titolo del tecnico','Titolo del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (56,'TEC_COMUNE','Comune del tecnico','Comune di residenza del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (57,'TEC_FAX','Fax del tecnico','Fax del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (58,'TEC_TELEFONO','Telefono del tecnico','Telefono del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (59,'TEC_PARTIVA','Partita IVA del tecnico','Partita IVA del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (60,'TEC_CODICE','Codice del tecnico','Codice anagrafica del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (61,'TEC_PASSWORD','Password del tecnico - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (62,'TEC_REGDITTE','N. registrazione CCIAA del tecnico','Numero di registrazione alla camera di commercio del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (63,'TEC_DATAREGDITTE','Data registrazione CCIAA del tecnico','Data di registrazione alla camera di commercio del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (64,'TEC_REGTRIB','N. registrazione tribunale del tecnico','Numero di registrazione al tribunale del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (65,'TEC_DATAREGTRIB','Data registrazione tribunale del tecnico','Data di registrazione al tribunale del tecnico');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (66,'NRCIVICO','Civico dell''istanza - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (67,'CODICEISTANZA','Codice istanza','Codice istanza');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (68,'DATAPRESENTAZIONEISTANZA','Data presentazione istanza','Data di presentazione dell''istanza');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (69,'NRPROTOCOLLOISTANZA','N. protocollo istanza','Numero di protocollo dell''istanza');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (70,'DATAPROTOCOLLO','Data protocollo istanza','Data di protocollo dell''istanza');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (71,'TIPOINTERVENTO','Descrizione procedimento','Descrizione del procedimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (72,'TIPOINTERVENTONOTE','Note procedimento','Note del procedimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (73,'POSIZARCHIVIO','Posizione in archivio','Posizione in archivio');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (74,'LOTTO','Codice lotto','Codice lotto');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (75,'DESCRIZIONEDEILAVORI','Descrizione dei lavori','Descrizione dei lavori');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (76,'DESCRIZIONEPROGETTO','Descrizione del progetto','Descrizione del progetto');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (77,'SUPERFICIE','Superficie','Superficie');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (78,'PASSWORD','Password','Password per l''accesso alla pratica dal font-end');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (79,'VARIANTEPRG','Variante piano regolatore','E'' una variante al piano regolatore?');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (80,'VALUTAZIMPATTOAMBIENTALE','Valutazione impatto ambientale','Valutazione impatto ambientale');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (81,'FORMATO','Formato istanza','Formato istanza');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (82,'TIPOIMPIANTO','Impianto','Impianto');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (83,'CODICEARCHIVIO','Codice tipo archivio','Codice del tipo di archivio dell''istanza');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (84,'ARCHIVIOPRATICHE','Tipo archivio','Tipo archivio dell''istanza');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (85,'TIPOLOGIAISTANZA','Tipologia istanza','Tipologia dell''istanza');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (86,'TIPOPROCEDURA','Tipo procedura','Tipo procedura');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (87,'DATASCADENZAISTANZA','Data scadenza','Data di scadenza dell''istanza');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (88,'NUMEROISTANZA_COLL','Numero istanza collegata - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (89,'_AUTORIZREGISTRO','Registro autorizzazioni - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (90,'QUALITADI','Tipo soggetto','Tipo soggetto');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (91,'INQUALITADI','Tipo soggetto','Tipo soggetto');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (92,'IST_COMUNE','Comune istanza','Comune dell''istanza');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (93,'CO_RICHIEDENTE','Denominazione azienda','Ragione sociale dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (94,'CO_INDIRIZZO','Indirizzo azienda','Indirizzo dell''azienda. Solo via enumero civico.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (95,'CO_CITTA','Città azienda','Città dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (96,'CO_CAP','CAP azienda','CAP dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (97,'CO_COMUNE','Comune azienda','Comune dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (98,'CO_PROVINCIA','Provincia azienda','Provincia dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (99,'CO_FAX','Fax azienda','Fax dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (100,'CO_TELEFONO','Telefono azienda','Telefono dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (101,'CO_DATANASCITA','Data nascita azienda','Data di nascita dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (102,'CO_CODICEFISCALE','Codice fiscale azienda','Codice fiscale dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (103,'CO_PARTITAIVA','Partita IVA azienda','Partita IVA dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (104,'CO_DATACOSTITUZIONE','Dtata costituzione azienda','Dtata di costituzione dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (105,'CO_CCIAANR','N. registrazione CCIAA dell''azienda','Numero di registrazione alla camera di commercio dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (106,'CO_CCIAADATA','Data registrazione CCIAA dell''azienda','Data di registrazione alla camera di commercio dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (107,'CO_CCIAACOMUNE','Comune registrazione CCIAA dell''azienda','Comune di registrazione alla camera di commercio dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (108,'CO_REGTRIBNR','N. registrazione tribunale azienda','Numero di registrazione al tribunale dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (109,'CO_REGTRIBDATA','Data registrazione tribunale azienda','Data di registrazione al tribunale dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (110,'CO_REGTRIBCOMUNE','Comune registrazione tribunale azienda','Comune di registrazione al tribunale dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (111,'CO_LUOGODINASCITA','Luogo di nascita dell''azienda','Luogo di nascita dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (112,'CO_TITOLO','Titolo azienda','Titolo azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (113,'CO_FORMAGIURIDICA','Forma giuridica azienda','Forma giuridica dell''azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (114,'OPERATORESPORTELLO','Responsabile','Nominativo del responsabile');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (115,'TELEFONOPERATORE','Telefono responsabile','Telefono del responsabile');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (116,'MAILOPERATORE','Email responsabile','Emai dell responsabile');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (117,'RESPONSPROCEDIMENTO','Responsabile del procedimento','Nominativo del responsabile del procedimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (118,'TELEFONORESPONSABILE','Telefono responsabile procedimento','Telefono del responsabile del procedimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (119,'MAILRESPONSABILE','Email responsabile procedimento','Email del responsabile del procedimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (120,'ISTRUTTORE','Istruttore','Nominativo dell''istruttore della pratica');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (121,'ISTRUTTOREEMAIL','Email istruttore','Email dell''istruttore della pratica');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (122,'ISTRUTTORETEL','Telefono istruttore','Telefono dell''istruttore della pratica');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (123,'DENOMINAZIONEATTIVITA','Attività','Denominazione attività');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (124,'SETTOREISTAT','Codice ISTAT del settore attività - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (125,'ATTIVITAISTAT','Codice ISTAT dell''attività - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (126,'RICQUAAZI','Richiedente, qualifica e azienda','Nominativo del richiedente, qualifica tipo soggetto e nominativo azienda');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (127,'INTERVENTODAALBERO','Intervento','Intervento dell''istanza; se non è impostato viene ricostruito risalendo l''albero dei procedimenti.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (128,'AREAINDUSTRIALE','Area','Area industriale');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (129,'LOCALIZZAZIONE','Localizzazione (CAP + via)','Localizzazione primaria dell''istanza. Vengono riportati il CAP e la via.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (130,'LOCALIZZAZIONE_INDIRIZZO','Localizzazione (via)','Localizzazione primaria dell''istanza. Viene riportata solo la via.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (131,'LOCALIZZAZIONE_LOCALITA','Localizzazione (località)','Localizzazione primaria dell''istanza. Viene riportata solo la località.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (132,'LOCALIZZAZIONE_CAP','Localizzazione (CAP)','Localizzazione primaria dell''istanza. Viene riportato solo il CAP.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (133,'LOCALIZZAZIONE_CIVICO','Localizzazione (civico)','Localizzazione primaria dell''istanza. Viene riportato solo il cvivico.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (134,'LOCALIZZAZIONE_CIRCOSCRIZIONE','Localizzazione (circoscrizione)','Localizzazione primaria dell''istanza. Viene riportata solo la circoscrizione.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (135,'LOCALIZZAZIONE_COLORE','Localizzazione (colore)','Localizzazione primaria dell''istanza. Viene riportato solo il colore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (136,'FOGLIO','Foglio','Mappale primario dell''istanza: foglio');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (137,'PARTICELLA','Particella','Mappale primario dell''istanza: particella');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (138,'SUB','Subalterno','Mappale primario dell''istanza: subalterno');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (139,'CDSDATA1CONV','Data prima convocazione CDS','Data prima convocazione della Conferenza dei Servizi');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (140,'CDSORA1CONV','Ora prima convocazione CDS','Ora prima convocazione della Conferenza dei Servizi');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (141,'DATA1CONVESTESA','Data prima convocazione CDS (estesa)','Data prima convocazione della Conferenza dei Servizi in forma estesa');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (142,'DATAORA1CONVOCAZ','Data e ora prima convocazione CDS','Data e ora prima convocazione della Conferenza dei Servizi');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (143,'CDSDATA2CONV','Data seconda convocazione CDS','Data della seconda convocazione della Conferenza dei Servizi');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (144,'CDSORA2CONV','Ora seconda convocazione CDS','Ora della seconda convocazione della Conferenza dei Servizi');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (145,'DATA2CONVESTESA','Data seconda convocazione CDS (estesa)','Data della seconda convocazione della Conferenza dei Servizi in forma estesa');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (146,'DATAORA2CONVOCAZ','Data e ora seconda convocazione CDS','Data e ora della seconda convocazione della Conferenza dei Servizi');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (147,'OGGETTO','Oggetto CDS','Ordine del giorno della Conferenza dei Servizi');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (148,'NOTE','Note CDS','Note sulla Conferenza dei Servizi');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (149,'PRESENZARICHIEDENTE','Presenza richiedente CDS','Richiesta la presenza del richiedente al Consiglio dei Servizi?');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (150,'VARIANTEAPRG','Variante a P.R.G. CDS','Variante al piano regolatore al Consiglio dei Servizi?');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (151,'MOVNUMEROPROTOCOLLO','N. protocollo del movimento','Numero di protocollo del movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (152,'MOV_NRPROT','N. protocollo del movimento','Numero di protocollo del movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (153,'MOVCODICETIPOMOVIMENTO','Codice tipo movimento','Codice tipo movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (154,'MOVDESCRIZIONEMOVIMENTO','Movimento','Descrizione del movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (155,'MOV_DATA','Data movimento','Data movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (156,'MOVDATAMOVIMENTO','Data movimento','Data movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (157,'MOV_CODICE','Codice movimento','Codice movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (158,'MOV_ENDO','Endoprocedimento','Endoprocedimento collegato al movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (159,'MOV_ESITO','Esito','Esito del movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (160,'MOV_PARERE','Parere','Parere del movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (161,'MOV_NOTE','Note movimento','Note del movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (162,'MOV_PUBBLICARE','Pubblica il movimento','Pubblicare il movimento?');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (163,'MOVCLASSIFICA','Classifica di protocollazione - DISMESSO','DISMESSO');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (164,'MOV_AMMINISTRAZIONE','Amministrazione del movimento','Amministrazione del movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (165,'MOVAMMINISTRAZIONE','Amministrazione del movimento','Amministrazione del movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (166,'MOV_UFFICIO','Ufficio referente del movimento','Ufficio referente del movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (167,'MOVUFFICIO','Ufficio referente del movimento','Ufficio referente del movimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (168,'MOVAMMINISTRAZIONEINDIRIZZO','Indirizzo ammistrazione del movimento','Indirizzo dell''ammistrazione del movimento. Indirizzo completo con via, CAP, città e provincia');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (169,'MOVUFFICIOINDIRIZZO','Indirizzo ufficio refenente del movimento','Indirizzo dell''ufficio referente del movimento. Indirizzo completo con via, CAP, città e provincia.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (170,'INTERVENTODAALBEROPRIMAVOCE','Intervento da albero (prima voce)','Descrizione della prima voce dell''albero dei procedimenti');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (171,'CAS_INTESTAZIONE1','Intestazione del comune (prima parte)','Prima parte dell''intestazione del comune. Il dato è recuperato in base al modulo attualmente in uso, se il dato non è presente viene recuperato dal modulo di base.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (172,'CAS_INTESTAZIONE2','Intestazione del comune (seconda parte)','Seconda parte dell''intestazione del comune. Il dato è recuperato in base al modulo attualmente in uso, se il dato non è presente viene recuperato dal modulo di base.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (173,'CAS_INTESTAZIONE3','Intestazione del comune (terza parte)','Terza parte dell''intestazione del comune. Il dato è recuperato in base al modulo attualmente in uso, se il dato non è presente viene recuperato dal modulo di base.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (174,'CAS_PIEPAGINA1','Piè di pagina del comune (prima parte)','Prima parte del piè di pagina del comune. Il dato è recuperato in base al modulo attualmente in uso, se il dato non è presente viene recuperato dal modulo di base.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (175,'CAS_PIEPAGINA2','Piè di pagina del comune (seconda parte)','Seconda parte del piè di pagina del comune. Il dato è recuperato in base al modulo attualmente in uso, se il dato non è presente viene recuperato dal modulo di base.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (176,'CAS_STEMMA','Stemma del comune','Stemma del comune. Il dato è recuperato in base al modulo attualmente in uso, se il dato non è presente viene recuperato dal modulo di base.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (177,'AUTORIZNUMERO','Numero autorizzazione','Numero autorizzazione. Se sono presenti più di una autorizzazione viene presa la più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (178,'AUTORIZDATA','Data autorizzazione','Data autorizzazione. Se sono presenti più di una autorizzazione viene presa la più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (179,'AUTORIZRESPONSABILE','Responsabile autorizzazione','Responsabile dell''autorizzazione. Se sono presenti più di una autorizzazione viene presa la più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (180,'AUTORIZDATARESPONSABILE','Data visione responsabile autorizzazione','Data di visione da parte del responsabile dell''autorizzazione. Se sono presenti più di una autorizzazione viene presa la più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (181,'AUTORIZREGISTRO','Registro autorizzazione','Registro dell''autorizzazione. Se sono presenti più di una autorizzazione viene presa la più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (182,'LISTAENDOATTIVATI','Lista endoprocedimenti attivati','Lista endoprocedimenti attivati. Vengono visualizzati: amministrazione, ufficio e procedimento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (183,'LISTAMOVIMENTI','Lista movimenti eseguiti','Lista movimenti attivati ordinati per data decrescente. Vengono mostrati: data movimento, tipo movimento, endoprocedimento, amministrazione, numero e data protocollo, esito e pubblica.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (184,'COINTESTATARI','Lista cointestatari','Lista dei cointestatari. Viene mostrato il nominativo completo.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (185,'COINTESTATARIESTESO','Lista cointestatari estesa','Lista dei cointestatari. Viene mostrato il nominativo completo, i dati di residenza e i dati di nascita.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (186,'COINTESTATARIESTESO_CFPI','Lista cointestatari estesa con CF/PI','Lista dei cointestatari. Viene mostrato il nominativo completo, i dati di residenza, i dati di nascita, codice fiscale e partita IVA.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (187,'ALLEGATIRICHIESTI','Lista allegati richiesti','Lista degli allegati dell''istanza.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (188,'ALLEGATIPRESENTATI','Lista allegati presentati','Lista degli allegati dell''istanza che sono stati presentati.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (189,'PARTICELLEUNICO','Mappali raggruppati','Particelle raggruppate per tipo catasto. Tutto in un''unica riga.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (190,'LISTAMAPPALI','Lista mappali','Lista dei mappali dell''istanza. Di ciascuno viene visualizzato: catasto, foglio, particella e sub');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (191,'MAPPALIISTANZA','Lista mappali','Lista dei mappali dell''istanza. Di ciascuno viene visualizzato: catasto, foglio, particella e sub');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (192,'ONEQUA','Lista oneri da controllo qualitativo','Lista degli oneri legati all''istanza. Di ciscun''onere viene visualizzata la descrizione dell''endoprocedimento, l''importo pagato e quello dovuto.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (193,'ENDOAUTORIZZATIVI','Lista endoprocedimenti autorizzativi','Lista degli endoprocedimenti autorizzativi. Di ciscuno viengono mostrati codice e descrizione.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (194,'ENDONONAUTORIZZATIVI','Lista endoprocedimenti non autorizzativi','Lista degli endoprocedimenti non autorizzativi. Di ciscuno viengono mostrati codice e descrizione.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (195,'ENDOACQUISITI','Lista endoprocedimenti acquisiti','Lista degli endoprocedimenti acquisitii. Di ciscuno viengono mostrati codice e descrizione.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (196,'ENDONONACQUISITI','Lista endoprocedimenti non acquisiti','Lista degli endoprocedimenti non acquisitii. Di ciscuno viengono mostrati codice e descrizione.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (197,'ENDOAUTOCERTIFICABILI','Lista endoprocedimenti autocertificabili','Lista degli endoprocedimenti autocertificabili. Di ciscuno viengono mostrati codice e descrizione.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (198,'ENDONONAUTOCERTIFICABILI','Lista endoprocedimenti non autocertificabili','Lista degli endoprocedimenti non autocertificabili. Di ciscuno viengono mostrati codice e descrizione.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (199,'LISTAENDOATTIVATICONPARERE','Lista endoprocedimenti attivati con parere','Lista degli endoprocedimenti attivati che hanno avuto un parere di ritorno. Di ciscuno viengono mostrati: amministrazione, descrizione procedimento e parere di ritorno.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (200,'LISTAENDOATTIVATIPROTOCOLLI','Lista endoprocedimenti attivati con protocollo','Lista degli endoprocedimenti attivati che hanno avuto un parere di ritorno. Di ciscuno viengono mostrati: amministrazione e, se presenti, numero e data di protocollo.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (201,'DOCUMENTIRICHIESTI','Lista documenti richiesti','Lista dei documenti richiesti');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (202,'DOCUMENTIMANCANTI','Lista documenti mancanti','Lista dei documenti richiesti ma non presentati');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (203,'DOCUMENTIPRESENTATI','Lista documenti presentati','Lista dei documenti richiesti che sono stati presentati');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (204,'LISTADOCRICHIESTIPRESENTI','Lista documenti presentati','Lista dei documenti richiesti che sono stati presentati');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (205,'LISTADOCRICHIESTINONPRESENTI','Lista documenti mancanti','Lista dei documenti richiesti ma non presentati');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (206,'LISTADOCNONRICHIESTIPRESENTI','Lista documenti non richiesti e presenti','Lista dei documenti non richiesti e presenti');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (207,'LISTADOCNONRICHIESTINONPRESENTI','Lista documenti non richiesti e non presenti','Lista dei documenti non richiesti e non presenti');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (208,'LISTADOCRICHIESTIPRESENTICONDATA','Lista documenti presentati con data','Lista dei documenti richiesti che sono stati presentati. Viene mostrata anche la data del documento.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (209,'LISTAAMMINVITATE','Lista amministrazioni invitate CDS','Lista delle amministrazioni invitate alla Conferenza dei Servizi. Visualizza solo i nomi delle amministrazioni.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (210,'CDSAMMINDIRIZZO','Lista amministrazioni invitate CDS (con indirizzo)','Lista delle amministrazioni invitate alla Conferenza dei Servizi. Visualizza i nomi delle amministrazioni e gli indirizzi completi.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (211,'LISTASOGGETTIINVITATI','Lista soggetti invitati CDS','Lista dei soggetti invitati alla Conferenza dei Servizi. Visualizza i nominativi completi.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (212,'CDSSOGGINDIRIZZO','Lista soggetti invitati CDS (con indirizzo)','Lista dei soggetti invitati alla Conferenza dei Servizi. Visualizza i nominativi completi e gli indirizzi completi.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (213,'LISTAATTIVERBALI','Lista atti verbali CDS','Lista degli atti della Conferenza dei Servizi. riporta anche gli esiti e le date e le ore delle prossime convocazioni.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (214,'ELENCODESTINATARI','Eleco destinatari','Elenco dei nominativi e degli indirizzi completi dei destinatari. I destinatari messi nell''elenco dipendono dall''input dell''utente che può scegliere se includere le amministrazioni coinvolte nei procedimenti dell''istanza o, se vuole, ne può specificare una a sua scelta; l''utente può anche specificare se includere anche se includere il richiedente, il tecnico e gli altri soggetti collegati all''istanza.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (215,'LISTAAMMINISTRAZCOINVOLTE','Lista amministrazioni coinvolte','Lista dei nominativi e degli indirizzi completi delle amministrazioni coinvolte. Per ciascun procedimento collegato all''istanza viene messo nella lista il referente dell''amministrazione se presente, se non è presente il referente viene messo nell''elenco il nominativo e l''indirizzo dell''amministrazione stessa.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (216,'LISTADETTAGLIOINFORMAZIONE','Lista dettaglio attività (aggregate per attività)','Lista delle somme delle dimensioni delle attività associate all''istanza aggregate per codice ISTAT dell''attività. 
Vengono mostrati:la definizione ISTAT dell''attività, l''unità di misura e la somma della dimensione di tutte le attività aventi quel codice ISTAT.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (217,'LISTATIPIINFORMAZIONE','Lista dettaglio attività (aggregate per settore)','Somma delle dimensioni delle attività associate all''istanza aggregate per settore attività. Vengono mostrati: il settore, l''unità di misura e la somma della dimensione di tutte le attività appartenenti a quel settore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (218,'DYN-$$$','Campo dinamico','Viene recuperato il valore del campo dinamico avente codice $$$. Sostituire $$$ con il codice del campo dianmico che si desidera visualizzare nel documento');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (219,'CHECKINTERVENTO$$$','Intervento selezionato','Sostituire $$$ con il codice intervento. Se il codice intervento inserito coincide con quello dell''intervento associato all''istanza, viene visualizzata una checkbox selezionata, altrimenti viene visualizzata una checkbox non selezionata.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (220,'COINTESTATARINOMINATIVO(COD_TIPO_SOGG,TIPO_RUOLO)','Cointestatari per tipo soggetto','Lista dei cointestatari filtrati per tipo soggetto e ruolo nell''istanza. Vengono visualizzati i nominativi completi e il titolo se è presente tutti su un''unica riga.
Sostituire COD_TIPPO_SOGG con un codice valido di una tipologa di soggetti. 
Sostituire TIPO_RUOLO con zero, uno o più valori scelti fra R, T e A se oltre ai cointestatari selezionati per tipo si vuole aggiungere all''elenco anche uno o più dei seguenti soggetti: T = tecnico, R = richiedente e A = azienda.
Per esempio se si vogliono nella lista tutti i cointestatari appartenenti al tipo soggetto con codice 30 e in più si vogliono anche il richiedente e il tecnico occorrerà utilizzare un segnaposto del tipo: COINTESTATARINOMINATIVO(30,R,T).
Se invece si vogliono solo i cointestatari appartenenti al tipo soggetto con codice 30 si dovrà utilizzare COINTESTATARINOMINATIVO(30).');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (221,'COINTESTATARIACAPO(COD_TIPO_SOGG,TIPO_RUOLO)','Cointestatari a capo per tipo soggetto (nominativo e indirizzo)','Lista dei cointestatari filtrati per tipo soggetto e ruolo nell''istanza. Vengono visualizzati su righe diverse i nominativi completi, il titolo se è presente e l''indirizzo.
Sostituire COD_TIPPO_SOGG con un codice valido di una tipologa di soggetti. 
Sostituire TIPO_RUOLO con zero, uno o più valori scelti fra R, T e A se oltre ai cointestatari selezionati per tipo si vuole aggiungere all''elenco anche uno o più dei seguenti soggetti: T = tecnico, R = richiedente e A = azienda.
Per esempio se si vogliono nella lista tutti i cointestatari appartenenti al tipo soggetto con codice 30 e in più si vogliono anche il richiedente e il tecnico occorrerà utilizzare un segnaposto del tipo: COINTESTATARIACAPO(30,R,T).
Se invece si vogliono solo i cointestatari appartenenti al tipo soggetto con codice 30 si dovrà utilizzare COINTESTATARIACAPO(30).');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (222,'COINTESTATARIACAPOBREVE(COD_TIPO_SOGG,TIPO_RUOLO)','Cointestatari a capo per tipo soggetto (completo breve)','Lista dei cointestatari filtrati per tipo soggetto e ruolo nell''istanza. Vengono visualizzati su righe diverse i nominativi completi, il titolo se è presente, il tipo soggetto, il codice fiscale e la partita IVA.
Sostituire COD_TIPPO_SOGG con un codice valido di una tipologa di soggetti. 
Sostituire TIPO_RUOLO con zero, uno o più valori scelti fra R, T e A se oltre ai cointestatari selezionati per tipo si vuole aggiungere all''elenco anche uno o più dei seguenti soggetti: T = tecnico, R = richiedente e A = azienda.
Per esempio se si vogliono nella lista tutti i cointestatari appartenenti al tipo soggetto con codice 30 e in più si vogliono anche il richiedente e il tecnico occorrerà utilizzare un segnaposto del tipo: COINTESTATARIACAPOBREVE(30,R,T).
Se invece si vogliono solo i cointestatari appartenenti al tipo soggetto con codice 30 si dovrà utilizzare COINTESTATARIACAPOBREVE(30).');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (223,'COINTESTATARIACAPOESTESO(COD_TIPO_SOGG,TIPO_RUOLO)','Cointestatari a capo per tipo soggetto (completo esteso)','Lista dei cointestatari filtrati per tipo soggetto e ruolo nell''istanza. Vengono visualizzati su righe diverse i nominativi completi, il titolo se è presente, il tipo soggetto, l''indirizzo, il codice fiscale e la partita IVA.
Sostituire COD_TIPPO_SOGG con un codice valido di una tipologa di soggetti. 
Sostituire TIPO_RUOLO con zero, uno o più valori scelti fra R, T e A se oltre ai cointestatari selezionati per tipo si vuole aggiungere all''elenco anche uno o più dei seguenti soggetti: T = tecnico, R = richiedente e A = azienda.
Per esempio se si vogliono nella lista tutti i cointestatari appartenenti al tipo soggetto con codice 30 e in più si vogliono anche il richiedente e il tecnico occorrerà utilizzare un segnaposto del tipo: COINTESTATARIACAPOESTESO(30,R,T).
Se invece si vogliono solo i cointestatari appartenenti al tipo soggetto con codice 30 si dovrà utilizzare COINTESTATARIACAPOESTESO(30).');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (224,'SOGCOL_NOME(COD_TIPO_SOGG)','Nome soggetto collegato per tipo','Viene recuperato il nome del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (225,'SOGCOL_COGNOME(COD_TIPO_SOGG)','Cognome soggetto collegato per tipo','Viene recuperato il cognome del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (226,'SOGCOL_CODICEFISCALE(COD_TIPO_SOGG)','C.F. soggetto collegato per tipo','Viene recuperato il codice fiscale del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (227,'SOGCOL_PARTITAIVA(COD_TIPO_SOGG)','P.I. soggetto collegato per tipo','Viene recuperata la partita IVA del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (228,'SOGCOL_DATANASCITA(COD_TIPO_SOGG)','Data nascita soggetto collegato per tipo','Viene recuperata la data di nascita del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (229,'SOGCOL_LUOGONASCITA(COD_TIPO_SOGG)','Luogo nascita soggetto collegato per tipo','Viene recuperato il luogo di nascita del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (230,'SOGCOL_PROVNASCITA(COD_TIPO_SOGG)','Provincia nascita soggetto collegato per tipo','Viene recuperata la provincia di nascita del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (231,'SOGCOL_RESINDIRIZZO(COD_TIPO_SOGG)','Indirizzo residenza soggetto collegato per tipo','Viene recuperato l''indirizzo di residenza del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (232,'SOGCOL_RESCAP(COD_TIPO_SOGG)','CAP residenza soggetto collegato per tipo','Viene recuperato il CAP di residenza del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (233,'SOGCOL_RESCITTA(COD_TIPO_SOGG)','Città residenza soggetto collegato per tipo','Viene recuperata la città di residenza del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (234,'SOGCOL_RESCOMUNE(COD_TIPO_SOGG)','Comune residenza soggetto collegato per tipo','Viene recuperato il comune di residenza del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (235,'SOGCOL_RESPROVINCIA(COD_TIPO_SOGG)','Provincia residenza soggetto collegato per tipo','Viene recuperata la provincia di residenza del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (236,'SOGCOL_SESSO(COD_TIPO_SOGG)','Sesso soggetto collegato per tipo','Viene recuperato il sesso del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (237,'SOGCOL_CITTADINANZA(COD_TIPO_SOGG)','Cittadinanza soggetto collegato per tipo','Viene recuperata la cittadinanza del soggetto collegato appartenente alla tipologia di soggetti che ha codice uguale a COD_TIPO_SOGG.
Sostituire COD_TIPO_SOGG con un codice tipo soggetto valido.
Se ci sono più di un soggetto collegato all''istanza del tipo specificato viene recuperato solo il più recente ossia quello con codice invitato maggiore.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (238,'MOV_CODICE(COD_TIPO_MOV)','Codice movimento per tipo','Viene recuperato il codice del movimento che appartiene al tipo movimento che ha codice COD_TIPO_MOV.
Sostituire COD_TIPO_MOV con un codice tipo movimento valido.
Se esistono più di un movimento del tipo richiesto viene recuperato solo il dato del movimento più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (239,'MOV_ENDO(COD_TIPO_MOV)','Endoprocedimento del movimento per tipo movimento','Viene recuperata la descrizione dell''endoprocedimento del movimento che appartiene al tipo movimento che ha codice COD_TIPO_MOV.
Sostituire COD_TIPO_MOV con un codice tipo movimento valido.
Se esistono più di un movimento del tipo richiesto viene recuperato solo il dato del movimento più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (240,'MOV_AMMINISTRAZIONE(COD_TIPO_MOV)','Amministrazione del movimento per tipo movimento','Viene recuperata amministrazione del movimento che appartiene al tipo movimento che ha codice COD_TIPO_MOV.
Sostituire COD_TIPO_MOV con un codice tipo movimento valido.
Se esistono più di un movimento del tipo richiesto viene recuperato solo il dato del movimento più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (241,'MOV_UFFICIO(COD_TIPO_MOV)','Ufficio referente del movimento per tipo movimento','Viene recuperato l''ufficio referente del movimento che appartiene al tipo movimento che ha codice COD_TIPO_MOV.
Sostituire COD_TIPO_MOV con un codice tipo movimento valido.
Se esistono più di un movimento del tipo richiesto viene recuperato solo il dato del movimento più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (242,'MOV_DATA(COD_TIPO_MOV)','Data movimento per tipo','Viene recuperata la data del movimento che appartiene al tipo movimento che ha codice COD_TIPO_MOV.
Sostituire COD_TIPO_MOV con un codice tipo movimento valido.
Se esistono più di un movimento del tipo richiesto viene recuperato solo il dato del movimento più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (243,'MOV_NRPROT(COD_TIPO_MOV)','N. protocollo del movimento per tipo movimento','Viene recuperato il numero di protocollo del movimento che appartiene al tipo movimento che ha codice COD_TIPO_MOV.
Sostituire COD_TIPO_MOV con un codice tipo movimento valido.
Se esistono più di un movimento del tipo richiesto viene recuperato solo il dato del movimento più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (244,'MOV_DATAPROT(COD_TIPO_MOV)','Data protocollo del movimento per tipo movimento','Viene recuperata la data di protocollo del movimento che appartiene al tipo movimento che ha codice COD_TIPO_MOV.
Sostituire COD_TIPO_MOV con un codice tipo movimento valido.
Se esistono più di un movimento del tipo richiesto viene recuperato solo il dato del movimento più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (245,'MOV_ESITO(COD_TIPO_MOV)','Esito movimento per tipo','Viene recuperato l''esito del movimento che appartiene al tipo movimento che ha codice COD_TIPO_MOV.
Sostituire COD_TIPO_MOV con un codice tipo movimento valido.
Se esistono più di un movimento del tipo richiesto viene recuperato solo il dato del movimento più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (246,'MOV_PARERE(COD_TIPO_MOV)','Parere movimento per tipo','Viene recuperato il parere del movimento che appartiene al tipo movimento che ha codice COD_TIPO_MOV.
Sostituire COD_TIPO_MOV con un codice tipo movimento valido.
Se esistono più di un movimento del tipo richiesto viene recuperato solo il dato del movimento più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (247,'MOV_NOTE(COD_TIPO_MOV)','Note movimento per tipo','Vengono recuperate le note del movimento che appartiene al tipo movimento che ha codice COD_TIPO_MOV.
Sostituire COD_TIPO_MOV con un codice tipo movimento valido.
Se esistono più di un movimento del tipo richiesto viene recuperato solo il dato del movimento più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (248,'MOV_PUBBLICARE(COD_TIPO_MOV)','Pubblicare movimento per tipo','Viene recuperato il flag che indica se pubblicare o no il movimento che appartiene al tipo movimento che ha codice COD_TIPO_MOV.
Sostituire COD_TIPO_MOV con un codice tipo movimento valido.
Se esistono più di un movimento del tipo richiesto viene recuperato solo il dato del movimento più recente.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (249,'DETTAGLIOINFORMAZIONE_DESCRIZIONE(COD_ISTAT)','Descrizione Attività per codice ISTAT','Descrizione completa dell''attività avente codice ISTAT uguale a COD_ISTAT.
Sostituire COD_ISTAT con il codice attività ISTAT valido.
Il dato viene restituito anche se non ci sono attività di quel tipo associate all''istanza.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (250,'DETTAGLIOINFORMAZIONE_MQ(COD_ISTAT)','Somma delle dimensioni delle attività per tipo attività','Viene riportata la somma delle attività associate all''istanza che hanno codice ISTAT uguale a COD_ISTAT.
Sostituire COD_ISTAT con un codice attività ISTAT valido.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (251,'LISTADETTAGLIOINFORMAZIONE(COD_SETT)','Lista dettaglio attività per settore (aggregate per attività)','Somma delle dimensioni delle attività associate all''istanza aggregate per codice ISTAT dell''attività e filtrate per codice settore.
Sostituire COD_SETT con un codice settore ISTAT valido. Verranno calcolate solo le attività che appartengono al settore richiesto.
Vengono mostrati: il codice ISTAT dell''attività, l''unità di misura e la somma della dimensione di tutte le attività aventi quel codice ISTAT.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (252,'LISTADETTAGLIOINFORMAZIONENORAGGRUPPATA(COD_SETT)','Lista dettaglio attività per settore','Lista dei dettagli delle attività associate all''istanza filtrate per codice settore.
Sostituire COD_SETT con un codice settore ISTAT valido. Verranno recuperate solo le attività che appartengono al settore richiesto.
Vengono mostrati: la definizione ISTAT dell''attività, l''unità di misura, la dimensione e le note di tutte le attività aventi quel codice settore ISTAT.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (253,'LISTADETTAGLIOINFORMAZIONESOLONOTE(COD_SETT)','Note attività per settore','Lista delle note delle attività associate all''istanza filtrate per codice settore.
Sostituire COD_SETT con un codice settore ISTAT valido. Verranno recuperate solo le attività che appartengono al settore richiesto.
Vengono mostrate solo le note di ciascuna attività.');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (254,'AUTORIZNUMERO(COD_TIPO_REG)','Numero autorizzazione per tipo registro','Numero dell''autorizzazione appartenente al tipo registro che ha codice COD_TIPO_REG.
Sostituire COD_TIPO_REG con un codice tipo registro valido.
Se ci sono più di una autorizzazione appartenenti al tipo di registro richiesto viene recuperata solo la più recente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (255,'AUTORIZDATA(COD_TIPO_REG)','Data autorizzazione per tipo registro','Data dell''autorizzazione appartenente al tipo registro che ha codice COD_TIPO_REG.
Sostituire COD_TIPO_REG con un codice tipo registro valido.
Se ci sono più di una autorizzazione appartenenti al tipo di registro richiesto viene recuperata solo la più recente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (256,'AUTORIZRESPONSABILE(COD_TIPO_REG)','Responsabile autorizzazione per tipo registro','Responsabile dell''autorizzazione appartenente al tipo registro che ha codice COD_TIPO_REG.
Sostituire COD_TIPO_REG con un codice tipo registro valido.
Se ci sono più di una autorizzazione appartenenti al tipo di registro richiesto viene recuperata solo la più recente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (257,'AUTORIZDATARESPONSABILE(COD_TIPO_REG)','Data visione responsabile autorizzazione per tipo registro','Data visione da parte del esponsabile per l''autorizzazione appartenente al tipo registro che ha codice COD_TIPO_REG.
Sostituire COD_TIPO_REG con un codice tipo registro valido.
Se ci sono più di una autorizzazione appartenenti al tipo di registro richiesto viene recuperata solo la più recente');
Insert into SEGNAPOSTO (ID,TAG,DESCRIZIONE,HELP) values (258,'AUTORIZREGISTRO(COD_TIPO_REG)','Descrizione registro autorizzazione per tipo registro','Descrizione della tipologia di registro dell''autorizzazione appartenente al tipo registro che ha codice COD_TIPO_REG.
Sostituire COD_TIPO_REG con un codice tipo registro valido.
Se ci sono più di una autorizzazione appartenenti al tipo di registro richiesto viene recuperata solo la più recente');
update MESSAGGICFGBASE set corpo=null where contesto='AR_INVIO';
update messaggicfgbase SET oggetto='Presentazione di una nuova istanza on-line' WHERE contesto='AR_INVIO';
Insert into Software (CODICE,DESCRIZIONE,MODULOOPZIONALE,DESCRIZIONELUNGA,ACCESSORAPIDO,ORDINE) values ('AP','Sportello Unico',1,'S.U.A.P. - Sportello Unico Attività Produttive',1,48);
update clmenu_java set link_standard='documentmerge/inputPage.htm?software=SOFTWARE' where id=87;
INSERT INTO verticalizzazionibase(modulo,descrizione) VALUES('WSANAGRAFE_PARIX','Se attivato permette di recuperare un''anagrafe persona giuridica dai servizi web di PARIX, la funzionalità viene attivata nelle anagrafiche richiedenti e tecnici sia IN inserimento che per controlli successivi e durante la presentazione della domanda On-line. Questo modulo va abilitato contestualmente ad un altro modulo WSANAGRAFE_XXX (modulo "principale") dove XXX è CESENA, PIACENZA... a condizione che il modulo "principale" sia sviluppato tenendo conto della compatibilità con l''integrazione PARIX. Le indicazioni se un modulo "principale" è compatibile con PARIX saranno scritte nella descrizione del modulo "principale".');
INSERT INTO verticalizzazioniparametribase(modulo,parametro,descrizione) VALUES('WSANAGRAFE_PARIX','XSD','Percorso degli xsd utili per la validazione dei risultati restituiti dai ws di Parix');                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
INSERT INTO verticalizzazioniparametribase(modulo,parametro,descrizione) VALUES('WSANAGRAFE_PARIX','USER','E'' l''utente per accedere ai servizi PARIX, deve essere fornito dal cliente.');                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
INSERT INTO verticalizzazioniparametribase(modulo,parametro,descrizione) VALUES('WSANAGRAFE_PARIX','URL','Url del web service da invocare per ricavare i dati anagrafici');                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
INSERT INTO verticalizzazioniparametribase(modulo,parametro,descrizione) VALUES('WSANAGRAFE_PARIX','SWITCHCONTROL','Parametro che stabilisce se effettuare la ricerca sul servizio nazionale o meno ( "diretto": accesso diretto alò servizio nazionale, "no" non passa mai dal servizio nazionale, ""/" ":passa dal servizio locale ed eventualmente da quello nazionale');                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
INSERT INTO verticalizzazioniparametribase(modulo,parametro,descrizione) VALUES('WSANAGRAFE_PARIX','PASSWORD','Password dell''utente abilitato alla ricerca');
insert into clmenu_java (id,descrizione,pagina,menulink,software,jsp,layouttesti,verticalizzazione,softwareesclusi,link_standard) values (995,'Pannello controllo CART','cart/view.htm?software=SOFTWARE','0A4','AP','JAVA',0,'CART',null,'cart/view.htm?software=SOFTWARE');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('WSANAGRAFE_TERNI', 'Se il parametro SEARCH_COMPONENT della verticalizzazione WSANAGRAFE è impostato su TERNI questa verticalizzazione contiene i parametri di configurazione per invocare il web service di CIVILIA');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_TERNI', 'WS_URL', '(obbligatorio) Url del web service esposto da CIVILIA, di solito è http://civiliaweb.core.it/suap_web/services/WsAnagrafe?wsdl');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_TERNI', 'USERNAME', '(obbligatorio) Username per effettuare il login al web service, di default è Administrator');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_TERNI', 'PASSWORD', '(obbligatorio) Password per effettuare il login al web service, di default è password7');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_TERNI', 'ENTE', '(obbligatorio) Ente utilizzato per effettuare il login al web service, di default è ente0');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_TERNI', 'USA_ANAGRAFE_SIGEPRO', 'Se impostato a 1 nel caso in cui il web service non trovasse l''anagrafica cercata verrà effettuata una ricerca anche tra le anagrafiche di Sigepro');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE', 'ASSEMBLY_LOAD_PATH', 'Percorso completo da cui caricare gli assembly contenenti le classi searcher. Se lasciato vuoto o non impostato verrà utilizzata la cartella ~/Bin dell''applicazione. Normalmente viene utilizzato solo nell''ambiente di sviluppo interno.');
UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = 'Determina la descrizione d''origine a cui assegnare il nome file degli allegati durante la protocollazione. Se non inserito o = 0 il nome file sarà assegnato con la descrizione inserita nell''albero dei procedimenti, se = 1 la descrizione coinciderà con il nome del file originale, se = 2 il nome file prenderà la sintassi IDCOMUNE + _ + CODICEOGGETTO + Estensione.' WHERE modulo = 'PROTOCOLLO_ATTIVO' and parametro = 'NOMEFILE_ORIGINE';
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'ENCODING', 'Indicare la stringa con un valore di encoding valido con cui sarà passato il file xml al web service di protocollazione, se non presente non sarà passato alcun encoding con il problema che nei casi in cui venga creato un file segnatura.xml e lo stesso venga passato al sistema di protocollazione sotto formato stringa, si verifichino dei problemi con alcuni tipi di carattere come per esempio quelli accentati. Ad esempio GeProt ha la necessità di avere un encoding utf-8 per accettare caratteri accentati.');
UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = 'Può assumere 3 valori: <br>0 – o non presente – Il nome file da mandare al protocollo è quello indicato nel back (es. ALBEROPROC_DOCUMENTI, DOCUMENTI_ISTANZA…). Potrebbe contenere accenti e lettere accentate e caratteri non convenzionali per un nome file (es. :,?,*).<br>1 – Il nome file da mandare al protocollo è quello che  stato caricato in upload dall’operatore. Potrebbe contenere accenti e lettere accentate.<br>2 – Il nome file da mandare al protocollo viene generato nella forma IDCOMUNE-CODICEOGGETTO.ESTENZIONE (Es. D612-1821.pdf). (Scelta consigliata).' WHERE modulo = 'PROTOCOLLO_ATTIVO' and parametro = 'NOMEFILE_ORIGINE';

UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=886;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=893;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=894;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=895;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=896;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=897;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=898;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=899;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=900;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=901;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=902;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=903;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=904;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=905;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=906;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=961;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=907;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=908;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=909;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=910;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=911;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=912;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=913;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=945;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=914;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=915;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=916;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=881;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=882;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=883;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=937;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=884;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=750;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=780;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=751;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=752;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=888;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=889;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=890;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=891;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=950;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=952;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=953;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=971;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=946;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=947;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=920;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=995;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=986;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=432;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=758;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=433;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=434;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=435;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=436;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=466;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=519;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=520;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=991;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=984;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=985;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=972;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=738;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=438;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=439;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=440;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=450;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=777;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=778;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=52;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=56;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=57;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=138;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=188;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=954;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=955;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=956;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=959;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=960;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=964;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=966;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=970;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=969;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=785;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=948;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=60;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=206;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=148;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=149;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=150;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=151;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=152;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=153;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=154;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=874;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=23;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=759;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=746;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=747;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=748;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=749;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=982;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=983;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=48;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=958;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=975;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=976;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=753;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=754;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=755;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=744;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=962;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=944;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=963;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=957;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=227;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=187;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=495;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=775;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=872;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=531;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=532;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=877;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=879;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=978;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=979;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=276;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=38;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=941;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=942;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=965;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=967;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=739;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=24;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=25;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=663;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=69;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=441;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=31;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=740;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=875;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=465;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=459;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=511;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA='E' WHERE ID=139;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA = 'E' WHERE ID = 29;

update clmenu_java set tipo_funzionalita='S' where NOT tipo_funzionalita='E';
delete from masterkey where Tablename='OGGETTI';

update CLMENU_JAVA set link_standard='report/createReportBase.htm?software=TT' where id=171;
update CLMENU_JAVA set link_standard='report/createStatisticaPerModulo.htm?software=SOFTWARE' where id=58;
update CLMENU_JAVA set link_standard='report/createReportPerModulo.htm?software=SOFTWARE' where id=28;

Update Istanze_Tempistica Set Stato='I' Where Stato Like '%interrotta%';
Update Istanze_Tempistica Set Stato='S' Where Stato Like '%sospesa%';
Update Istanze_Tempistica Set Stato=Null Where Stato Not In ('I','S');

UPDATE TIPIMOV_STC_MAPPING SET FLAG_CREAINVIA_ALLEGATI=0 WHERE FLAG_CREAINVIA_ALLEGATI IS NULL;
UPDATE TIPIMOV_STC_MAPPING SET FLAG_ALLEGA_DOCUMENTI_ISTANZA=0 WHERE FLAG_ALLEGA_DOCUMENTI_ISTANZA IS NULL;
UPDATE TIPIMOV_STC_MAPPING SET FLAG_PROTOCOLLA=0 WHERE FLAG_PROTOCOLLA IS NULL;
UPDATE TIPIMOV_STC_MAPPING SET FLAG_NOTIFICA_AUTOMATICA=0 WHERE FLAG_NOTIFICA_AUTOMATICA IS NULL;
UPDATE TIPIMOV_STC_MAPPING SET NONINVIAREPROCEDIMENTI=0 WHERE NONINVIAREPROCEDIMENTI IS NULL;
insert into MASTERKEY (Tablename,columnName) values ('NATURAENDO','BINARIODIPENDENZE');
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA = 'E' WHERE ID=928;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA = 'E' WHERE ID=927;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA = 'E' WHERE ID=949;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA = 'E' WHERE ID = 76;
UPDATE CLMENU_JAVA SET TIPO_FUNZIONALITA = 'E' WHERE ID = 745;

update CLMENU_JAVA set link_standard='cctabellaclassiedificio/list.htm?software=SOFTWARE' where id=900;
update CLMENU_JAVA set link_standard='ccvaliditacoefficienti/list.htm?software=SOFTWARE' where id=907;
update CLMENU_JAVA set tipo_funzionalita='S' where id=905;
update CLMENU_JAVA set tipo_funzionalita='S' where id=894;
update CLMENU_JAVA set tipo_funzionalita='S' where id=893;
update CLMENU_JAVA set tipo_funzionalita='S' where id=907;
update CLMENU_JAVA set tipo_funzionalita='S' where id=899;
update CLMENU_JAVA set tipo_funzionalita='S' where id=900;
update CLMENU_JAVA set link_standard='cctipointervento/list.htm?software=SOFTWARE' where id=898;
update CLMENU_JAVA set tipo_funzionalita='S' where id=898;
update CLMENU_JAVA set link_standard='cccausaliriduzionit/list.htm?software=SOFTWARE' where id=961;
update CLMENU_JAVA set tipo_funzionalita='S' where id=961;


UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'ACQUALAGNA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'APECCHIO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'AUDITORE';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'BARCHI';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'BELFORTE ALL''ISAURO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'BORGO PACE';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'CAGLI';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'CANTIANO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'CARPEGNA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'CARTOCETO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'COLBORDOLO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'FANO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'FERMIGNANO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'FOSSOMBRONE';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'FRATTE ROSA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'FRONTINO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'FRONTONE';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'GABICCE MARE';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'GRADARA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'ISOLA DEL PIANO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'LUNANO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MACERATA FELTRIA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MERCATELLO SUL METAURO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MERCATINO CONCA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MOMBAROCCIO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MONDAVIO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MONDOLFO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MONTE CERIGNONE';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MONTE GRIMANO TERME';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MONTE PORZIO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MONTECALVO IN FOGLIA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MONTECICCARDO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MONTECOPIOLO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MONTEFELCINO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MONTELABBATE';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'MONTEMAGGIORE AL METAURO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'ORCIANO DI PESARO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'PEGLIO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'PERGOLA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'PESARO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'PETRIANO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'PIAGGE';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'PIANDIMELETO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'PIETRARUBBIA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'PIOBBICO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'SALTARA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'SAN COSTANZO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'SAN GIORGIO DI PESARO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'SAN LORENZO IN CAMPO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'SANT''ANGELO IN LIZZOLA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'SANT''ANGELO IN VADO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'SANT''IPPOLITO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'SASSOCORVARO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'SASSOFELTRIO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'SERRA SANT''ABBONDIO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'SERRUNGARINA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'TAVOLETO';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'TAVULLIA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'URBANIA';

UPDATE COMUNI SET SIGLAPROVINCIA='PU' WHERE SIGLAPROVINCIA = 'PS' AND COMUNE = 'URBINO';


update FO_VISURA_CONTESTI_BASE set CONTESTO='Archivio istanze presentate - Campi da visualizzare nella lista' where id='ARCHIVIO_LISTA';
update FO_VISURA_CONTESTI_BASE set CONTESTO='Le mie pratiche - Campi da visualizzare come filtri' where id='VISURA_FILTRI';
update FO_VISURA_CONTESTI_BASE set CONTESTO='Le mie pratiche - Campi da visualizzare nella lista' where id='VISURA_LISTA';
update FO_VISURA_CONTESTI_BASE set CONTESTO='Archivio istanze presentate - Campi da visualizzare come filtri' where ID='ARCHIVIO_FILTRI';

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'MODIFICA_CLASSIFICA', 'Consente, in fase di protocollazione istanza o movimento da backoffice, di abilitare o meno la modifica della classifica. Se valorizzato a 1 allora abilita la modifica della classifica altrimenti se valorizzato a 0 o non valorizzato non consente la modifica della classifica.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'MODIFICA_CLASSIFICAFASC', 'Consente, in fase di protocollazione istanza o movimento da backoffice, di abilitare o meno la modifica della classifica della fascicolazione. Se valorizzato a 1 allora abilita la modifica della classifica della fascicolazione altrimenti se valorizzato a 0 o non valorizzato non consente la modifica della classifica della fascicolazione.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_DOCAREA', 'INVIA_SEGNATURA', '(Facoltativo) Se valorizzato a 1 consente, in fase di protocollazione istanza o movimento da backoffice, di inviare il file segnatura.xml che sarebbe il file che viene inviato al web service per eseguire la protocollazione; se valorizzato a 0 o non valorizzato non esegue nessuna funzionalità.');

UPDATE comuni SET CAP = '47521' where comune ='CESENA';
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_DOCAREA', 'INVIA_ALL_MOV_AVVIO', '(Facoltativo) Se valorizzato a 1 va a cercare in automatico, solamente durante la protocollazione da istanza, gli allegati caricati nel movimento di avvio dell''istanza stessa, se esistono questi vengono inviati al protocollo DOCAREA. Se valorizzato con valore diverso da 1 o non valorizzato la funzionalità appena descritta non sarà svolta.');

UPDATE ALBEROPROC_DYN2MODELLIT SET FLAG_FACOLTATIVA=0 WHERE FLAG_FACOLTATIVA IS NULL;
UPDATE INVENTARIOPROCDYN2MODELLIT SET FLAG_FACOLTATIVA=0 WHERE FLAG_FACOLTATIVA IS NULL;
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('ALLINEAMENTO_ANAGRAFE_ATTIVO', 'AGGIORNA_TUTTO_WEEK_END', 'Indica se nelle giornate di sabato e domenica deve essere fatto l''aggiornamento delle anagrafiche senza limiti temporali, indipendentemente quindi dal fatto che il parametro DATA_AGGIORNAMENTO sia valorizzato o meno. Valorizzare a 1 se si desidera attivare questo parametro, tutti gli altri valori non lo attiveranno.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('ALLINEAMENTO_ANAGRAFE_ATTIVO', 'MIN_CHECK_TOKEN', 'Indicare il numero di minuti dopo i quali l''aggiornamento deve staccare nuovamente il token, serve per non far scadere il token nei casi di allineamenti molto lenti. Se non valorizzato non sarà fatta alcuna check token');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE VALUES
  (
    'RFC239',
    'LISTA_NODI_NO_RICEVUTA',
    'La lista dei nodi (idsportello) in cui la notifica attivita'' non deve essere messa come in attesa conferma ricezione (es ASL che attualmente lavora in 183).'
  );
INSERT INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'SIT_LDP',
    'OSP_DYN2_MODELLIT',
    'NOME DELLA SCHEDA DINAMICA CHE CONTIENE I DATI CHE TORNANO DAL SERVIZIO OCCUPAZIONE SUOLO PUBBLICO'
  );
INSERT INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'SIT_LDP',
    'OSP_DYN2_INIZIO_DATA',
    'NOME DEL CAMPO DIMAMICO CHE CONTIENE LA DATA DI INIZIO OCCUPAZIONE SUOLO PUBBLICO'
  );
  INSERT INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'SIT_LDP',
    'OSP_DYN2_INIZIO_ORA',
    'NOME DEL CAMPO DIMAMICO CHE CONTIENE L''ORA DI INIZIO OCCUPAZIONE SUOLO PUBBLICO'
  );
INSERT INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'SIT_LDP',
    'OSP_DYN2_FINE_DATA',
    'NOME DEL CAMPO DIMAMICO CHE CONTIENE LA DATA DI FINE OCCUPAZIONE SUOLO PUBBLICO'
  );

INSERT INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'SIT_LDP',
    'OSP_DYN2_FINE_ORA',
    'NOME DEL CAMPO DIMAMICO CHE CONTIENE LA ORA DI FINE OCCUPAZIONE SUOLO PUBBLICO'
  );

UPDATE TIPIMODALITAPAGAMENTO SET FLAG_DISABILITATO = 0 WHERE FLAG_DISABILITATO IS NULL;


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('TIPO_INSTALLAZIONE', 'PAGINA_ONERI', 'Può assumere valore MICROSOFT o JAVA il default dipende dal tipo di installazione.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('TIPO_INSTALLAZIONE', 'PAGINA_STATISTICHE', 'Può assumere valore MICROSOFT o JAVA il default dipende dal tipo di installazione.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('TIPO_INSTALLAZIONE', 'PAGINA_STAMPE', 'Può assumere valore MICROSOFT o JAVA il default dipende dal tipo di installazione.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('TIPO_INSTALLAZIONE', 'PAGINA_SCHEDEDINAMICHE', 'Può assumere valore MICROSOFT o JAVA il default dipende dal tipo di installazione.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('TIPO_INSTALLAZIONE', 'STAMPE_DOCTIPO', 'Può assumere valore MICROSOFT o JAVA il default dipende dal tipo di installazione.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'MOD_TRASMISSIONE_PEC', 'Indicare il codice della modalità di trasmissione, per il quale deve essere inviata una pec successivamente ad una protocollazione in partenza. Ad esempio, se viene impostato questo parametro a PEC allora ogni volta che in un protocollo in partenza verrà selezionato PEC come modalità di trasmissione (invio) il sistema chiederà al web service di protocollo di invocare il metodo di invio PEC denominato inviaProtocollo, altrimenti questa funzionalità non sarà invocata, come nel caso in cui questo parametro non sarà valorizzato');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JPROTOCOLLO', 'INVIA_PEC', 'Se impostato a 1 consente di accedere alla funzionalità di invio pec del web service per un protocollo in partenza, altrimenti non sarà invocato tale metodo del web service di protocollazione.');



Insert into VERTICALIZZAZIONIBASE (MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) values ('QRCODE','Consente la gestione dei segnaposto qrcode', null);
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('QRCODE','URL_VISURA_ANONIMA','In questo parametro deve essere indicata l''url pubblica che sarà usata per accedere alla funzionalita'' di visura anonima - senza autenticazione');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('QRCODE','URL_VISURA_AUTENTICAZIONE','In questo parametro deve essere indicata l''url pubblica che sarà usata per accedere alla funzionalita'' di visura che prevede comunque l''autenticazione classica del sistema di frontend');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('QRCODE','URL_VISURA_PIN','In questo parametro deve essere indicata l''url pubblica che sarà usata per accedere alla funzionalita'' di visura che prevede comunque l''autenticazione tramite pin');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('PROTOCOLLO_PAL', 'Regola che se attivata gestisce l''integrazione con il sistema di protocollo CITYWARE della ditta PAL Informatica.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PAL', 'USERNAME', 'Codice dell''utente facente parte delle credenziali per poter accedere alle funzionalità esposte dal web service. Parametro obbligatorio che serve per poter ottenere il token generato dalla chiamata a creaToken del servizio REST, che successivamente sarà passato nell''intestazione delle altre chiamate a web service. Il parametro, nella chiamata a creaToken è denominato -codute-.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PAL', 'PASSWORD', 'Password dell''utente facente parte delle credenziali per poter accedere alle funzionalità esposte dal web service. Parametro obbligatorio che serve per poter ottenere il token generato dalla chiamata a creaToken del servizio REST, che successivamente sarà passato nell''intestazione delle altre chiamate a web service. Il parametro, nella chiamata a creaToken è denominato -password-. Il valore di questo parametro dovrà poi essere codificato in base64 e poi facendo un urlencode.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PAL', 'CODICE_ISTAT', 'Codice istat dell''ente facente parte delle credenziali per poter accedere alle funzionalità esposte dal web service. Parametro obbligatorio che serve per poter ottenere il token generato dalla chiamata a creaToken del servizio REST, che successivamente sarà passato nell''intestazione delle altre chiamate a web service. Il parametro, nella chiamata a creaToken è denominato -codente-, ma è a tutti gli effetti il codice istat dell''ente.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PAL', 'CODICE_AOO', 'Codice aoo dell''ente facente parte delle credenziali per poter accedere alle funzionalità esposte dal web service. Parametro obbligatorio che serve per poter ottenere il token generato dalla chiamata a creaToken del servizio REST, che successivamente sarà passato nell''intestazione delle altre chiamate a web service. Il parametro, nella chiamata a creaToken è denominato -codaoo-, ed è a tutti gli effetti il codice ipa ufficiale dell''ente (http://www.indicepa.gov.it/).');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PAL', 'URL_BASE', 'Indica la url di base del servizio REST del protocollo PAL, non va quindi indicato il metodo in questo parametro che sarà aggiunto direttamente da codice, ad esempio nell''ambiente di test il valore è http://cw2.gruppoapra.com/cw2/services/');

insert into layoutpaginebase(lp_oggetto, descrizione) values ('fldCodiceDomandaStc'           ,'nasconde il campo codice domanda stc ');
insert into layoutpaginebase(lp_oggetto, descrizione) values ('fldTipologiaIstanza'           ,'nasconde il campo tipologiaistanza');
insert into layoutpaginebase(lp_oggetto, descrizione) values ('sezioneAutConcessioni'         ,'nasconde la sezione autorizzazioni concessioni');
insert into layoutpaginebase(lp_oggetto, descrizione) values ('fldProcedura'                  ,'nasconde il campo procedura nella maschera di ricerca istanze');
insert into layoutpaginebase(lp_oggetto, descrizione) values ('fldIstanzeSettori'             ,'nasconde il campo tipo informazione nella maschera di ricerca istanze');
insert into layoutpaginebase(lp_oggetto, descrizione) values ('fldIstanzeAttivita'            ,'nasconde il campo dettaglio informazione nella maschera di ricerca istanze');
insert into layoutpaginebase(lp_oggetto, descrizione) values ('fldIstanzeDomicilioElettronico','nasconde il campo domicilio elettronico nella pagina delle istanze');
insert into layoutpaginebase(lp_oggetto, descrizione) values ('fldIstanzeRespProc'            ,'nasconde il campo responsabile procedimento nella pagina delle istanze');
insert into layoutpaginebase(lp_oggetto, descrizione) values ('fldIstanzeIstruttore'          ,'nasconde il campo istruttore nella pagina delle istanze');

Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('SIEDER','URL_STATO_PRATICA','In questo parametro deve essere indicata l''url per i servizi rest esposti dal nodo sieder per la verifica dello stato attuale della pratica in SIEDER');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('SIEDER','URL_STATI_AMMISSIBILI','In questo parametro deve essere indicata l''url per i servizi rest esposti dal nodo sieder per la verifica degli stati ammissibili per la pratica in SIEDER');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('QRCODE','URL_WS_RIEPILOGO_PRATICA','In questa URL va messa l''indirizzo per accedere al web service di rigenerazione riepilogo pratica es: http://devel3/frontoffice/AreaRiservata/webservices/istanze/visura/riepilogo-pratica.asmx?WSDL' );

Update softwareattivi set flag_subvisura=0;

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_MIP_RPCSUAP', 'CODICE_UTENTE', 'Valorizzare il Codice Utente dell''identificativo del servizio che richiede il pagamento.Valorizzato con la seguente formattazione:Codice Utente - CodiceEnte (*) - TipoUfficio (*) - CodUfficio (*) - Tipologia Servizio');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_MIP_RPCSUAP', 'CODICE_ENTE', 'Valorizzare, se richiesto, il Codice Ente dell''identificativo del servizio che richiede il pagamento.Valorizzato con la seguente formattazione:Codice Utente - CodiceEnte (*) - TipoUfficio (*) - CodUfficio (*) - Tipologia Servizio, questo dato non è obbligatorio.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_MIP_RPCSUAP', 'TIPO_UFFICIO', 'Valorizzare, se richiesto, il Tipo Ufficio dell''identificativo del servizio che richiede il pagamento.Valorizzato con la seguente formattazione:Codice Utente - CodiceEnte (*) - TipoUfficio (*) -CodUfficio (*) - Tipologia Servizio, questo dato non è obbligatorio.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_MIP_RPCSUAP', 'CODICE_UFFICIO', 'Valorizzare, se richiesto, il Codice Ufficio dell''identificativo del servizio che richiede il pagamento.Valorizzato con la seguente formattazione:Codice Utente - CodiceEnte (*) - TipoUfficio (*) -CodUfficio (*) - Tipologia Servizio, questo dato non è obbligatorio.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_MIP_RPCSUAP', 'TIPOLOGIA_SERVIZIO', 'Valorizzare,la Tipologia del Servizio relativa all''identificativo del servizio che richiede il pagamento.Valorizzato con la seguente formattazione:Codice Utente - CodiceEnte (*) - TipoUfficio (*) -CodUfficio (*) - Tipologia Servizio');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_MIP_RPCSUAP', 'CHIAVE_IV', 'Valorizzare la Chiave IV relativa all''inizializzazione del servizio, è una chiave segreta che deve essere comunicata dal gestore del servizio.');

INSERT INTO verticalizzazioniparametribase (modulo,parametro,descrizione) VALUES ('SIT_LDP','OSP_DYN2_DETT_AREE_OCCUPATE','NOME DEL CAMPO DIMAMICO CHE CONTIENE IL DETTAGLIO DELLE AREE PER LE QUALI VIENE RICHIESTA L''OCCUPAZIONE SUOLO PUBBLICO'  );

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PAGAMENTI_MIP_RPCSUAP', 'URL_NOTIFICA', 'Indicare la url che il servizio di pagamento deve raggiungere al momento di nmotificare l''avvenuto pagamento.');

INSERT into verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA','FL_SCHEDE_FIRMATE_IN_RIEPILOGO' ,'Gestisce l''inserimento o meno delle schede firmate nel riepilogo di domanda. Valori possibili: 0 = Includi solo le schede che non necessitano firma (default), 1 = Includi tutte le schede');

INSERT into LAYOUTPAGINEBASE (LP_OGGETTO,DESCRIZIONE) values ('fldIstRichProcuratore', 'Mostra o nasconde nella pagina dei richiedenti dell''istanza il campo procuratore');

INSERT into LAYOUTPAGINEBASE (LP_OGGETTO,DESCRIZIONE) values ('fldIstanzeQualitaDi', 'Mostra o nasconde nella pagina dei richiedenti dell''istanza il campoIn qualita'' di');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'DEST_UTENTI_CO_AUTOMATICI', 'Flag che se impostato ad -S- indica che gli utenti in CO vengono recuperati in automatico dall''ufficio (dove (n) deve essere sostituito col progressivo dell''ufficio cui l''informazione si riferisce); in CO vengono inseriti tutti gli utenti collegati all’ufficio con il ruolo assegnatario/ass.smistatore, mentre in CC tutti gli altri utenti collegati all’ufficio. Il flag può essere impostato anche con -S1-, in questo caso vengono recuperati soltanto gli utenti in CO (evitando il caricamento di quelli in CC).<br>Di default sarà impostato a S');

update clmenu_java set tipo_funzionalita='S'  where ID IN (1018,1019);
UPDATE clmenu_java SET SOFTWARE='*' where ID=1039;

INSERT INTO dyn2_basecontesti (id, contesto) VALUES('PO', 'Posteggi');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'ALIAS_BACKEND_ENTI_TERZI', 'Indicale l''Alias dell''installazione che indentifica l''ente o gli enti che usufruiscono dell''Area Riservata della console senza avere un vero backend VBG. In questo caso, l''Area riservata della console non proporrà la trasmissione della domanda al termine ma rimanderà ad una visura con la possibilitò di scaricare la documentazione in formato ZIP');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'CODICE_INTERVENTO_ESCLUSIVO', 'Se valorizzato con il codice (SC_ID) dell''albero degli interventi, permetterà di presentare domande solo per quell''intervento (o per i suoi interventi figlio se si tratta dell''ID di una cartella )');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('STC', 'DIS_MOD_DATA_NOTIFICA', 'Permette di disabilitare la modifica della data e ora di inoltro dei movimenti notificati da STC');

INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'STC',
    'NLA_IDNODO_ENTE_NON_LOCALE',
    'L''identificativo dedl nodo configurato come NON VBG o SUAPE per il quale viene attivata la funzionalità invia pratiche come ZIP'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('STC', 'AMM_DEST_INVIA_ZIP_PRATICA', 'Indica il codice amministrazione che sara'' identificata come destinataria dell''inoltro zip pratica. L''amministrazione dovra'' essere configurata con i parametri STC (idnodo,idente,idsportello). E'' il caso d''uso dell''invio pratiche da backoffice non VBG');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'SEGNA_PROT_AUT_KO', 'Può assumere valori : S = attivo, N (NULL)= non attivo. Attiva comportamento che permette di segnare sull''istanza una protocollazione fallita. Se attivo segna la tipologia di protocollo fallita(Es. Automatica ,Manuale,ect). Il comportamento permetterà di riprotocollare le pratiche tramite un job apposito: RitentaProtocollazioneIstanzaFalliteJob ');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'URL_GENERA_RICEVUTA_PRATICA', 'Link per rigenerare il certificato di invio');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'NOME_FILE_RICEVUTA', 'nome del file del riepilogo generato dall''area riservata. Valore di default certificato-di-invio.pdf');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'DESCRIZIONE_FILE_RICEVUTA','descrizione del file della ricevuta generata dall''area riservata');


INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('ANTENNE_LUCCA', 'Gestione della visualizzazione dei dati delle antenne di Lucca', 0);
INSERT INTO verticalizzazioniparametribase(modulo, parametro, descrizione) VALUES ('ANTENNE_LUCCA', 'NOME_CAMPO_ID_ANTENNA', 'Nome del campo dinamico che contiene l''identificativo antenna');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'URL_PAGINA_VISURA_ISTANZA', 'Va configurato insieme al parametro ALIAS_BACKEND_ENTI_TERZI ed è la pagina dell''area riservata in cui visualizzare la Visura per poter eventualmente scaricare la documentazione della pratica in formato ZIP');

update VERTICALIZZAZIONIPARAMETRIBASE  set descrizione ='Rappresenta l''URI per sovrascrivere l''indirizzo delle API di BACKEND es: http://10.10.45.64:8080/api-backend' where modulo='API_SERVICE' and parametro='API_SERVICE_URL';

INSERT INTO verticalizzazioniparametribase(modulo, parametro, descrizione) VALUES ('SUAPER', 'VECCHIA_LOGICA_SE_UN_ENDO', 'Nel caso che la pratica sia composta da un solo endo allora non si applica la logica dei gruppi di smistamento ma la vecchia logica basata su endo principale');

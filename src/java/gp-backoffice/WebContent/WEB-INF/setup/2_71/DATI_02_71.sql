INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) 
    VALUES (
    'CONSOLE'
    ,'SC_CODICE_PARTENZA'
    ,'IL CODICE (CAMPO SC_CODICE) DELL''INTERVENTO VOCE DI PARTENZA DALLA QUALE COPIARE LE INFORMAZIONI. ES: SOLO VOCE DELL''ALBERO AMBIENTE ''0105''');

UPDATE COMUNI SET SIGLAPROVINCIA = 'FM', PROVINCIA = 'FERMO', CAP = '63900', CODICEISTAT = '109006' WHERE CODICECOMUNE='D542';
INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('COSAP_PARMA', 'Modulo per la gestione della bollettazione per la COSAP di Parma', 0);
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COSAP_PARMA','WEB_SERVICE_URL', 'Url del web service da chiamare, per i test è https://webservicestest.comune.parma.it/Wsdbpagamenti/pagamenti.asmx?wsdl');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COSAP_PARMA','UTENTE_INSERIMENTO', 'Valore del parametro ''utenteInserimento'' da passare al web service, per i test è ''VBG''');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COSAP_PARMA','FONTE', 'Valore del parametro ''fonte'' da passare al web service, per i test è ''FLUSSI DI BACK OFFICE''');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COSAP_PARMA','ANNO', 'Valore del parametro ''anno'' da passare al web service, se lasciato vuoto o non valorizzato verrà usato l''anno corrente');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COSAP_PARMA','WS_USER', 'Valore del parametro ''user'' da passare al web service, per i test è ''test''');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COSAP_PARMA','WS_PASSWORD', 'Valore del parametro ''password'' da passare al web service, per i test è ''scrittura''');

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NODO_PAGAMENTI', 'BLACKLIST_TIME_CHECK_PAGAM', 'Rappresenta dopo quanto tempo le posizioni debitorie non pagate devono finire in black list. Indicare una durata nel formato ISO come documentato in https://www.w3.org/TR/xmlschema-2/#duration. Esempi: per aggiungere 30 ore specificare P0Y0M0DT30H0M0S.');   


INSERT INTO VERTICALIZZAZIONIBASE(MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) VALUES ('PROTOCOLLO_STORICO','Permette di attivare un protocollo per la sola operazione di LeggiProtocollo; utilizzato quando si cambia il protocollo ma non si migrano i protocolli. E'' possibile solamente effettuale la lettura del protocollo',1);
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO','TIPOPROTOCOLLO','E'' il tipo protocollo attivato (IRIDE,PINDARO,SIGEPRO,GEPROT... vedi l''enumerazione ');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO','DATAULTIMAPROTOCOLLAZIONE','Indicare la data nel formato GG/MM/YYYY che verrà utilizzata dal sistema per capire fino a quando il protocollo storico era in vigore e non storico');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO','CODICEREGISTRO','Indica il registro ( o la sigla del registro ) che identifica, presso il fornitore, il protocollo generale');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO','URLLEGGIPROTOCOLLO','Indica la URL per la lettura dei dati del protocollo');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO','URLLEGGIALLEGATI','Indica la URL per la lettura e l''eventuale scarico degli allegati del protocollo. Se vuoto significa che non è previsto il metodo lato protocollo');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_STORICO','UTENTE','Utente utilizzato per l''autenticazione se previsto');

CREATE INDEX PAY_POSIZIONIDEBITORIE_002 ON pay_posizioni_debitorie(IDCOMUNE,fk_soggetto_debitore);


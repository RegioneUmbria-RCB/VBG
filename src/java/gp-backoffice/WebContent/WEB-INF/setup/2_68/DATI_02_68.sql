INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'PROTOCOLLO_ATTIVO',
    'VIS_PANEL_RICERCA_FASCICOLO',
    'Se impostato a nella maschera di protocollazione viene visualizzata la funzionalita'' di ricerca fascicoli'
);
insert into mapoggetti(nometabella,nomecampo)values('EQUITALIATRACCIATO','CODICEOGGETTO');

INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'PROTOCOLLO_ATTIVO','PANEL_RIC_FASC_PREC_CLASSIF',
    'Se impostato a 1 nella maschera di ricerca fascicolo viene preimpostata la classifica e messa in sola lettura. La classifica impostata viene recuperata dalle configurazioni dell''albero degli interventi'
);
INSERT INTO ELENCHIPROFESSIONALIBASE(EP_ID, EP_DESCRIZIONE, EP_ATRIBCODICE, FLAG_REGIONALE) VALUES (20,'Collegio dei periti agrari',5,0);

INSERT INTO TIPICONTESTOESPORTAZIONE(CODICE, DESCRIZIONE) VALUES ('POS', 'Esportazione di posteggi');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'VALORIZZADATARIC_SPEDIZ', 'Valori: 0 (default) e 1. Se impostato a 1 valorizza la proprietà ValorizzaDataRicezioneSpedizione del protocollo di base per poterla poi utilizzare nelle implementazioni dei protocolli. Ad esempio con PROTOCOLLO_INSIEL ( versione 3 ) viene utilizzato per passare la data dell''istanza o la data del movimento o la data di ricezione PEC al protocollo tramite il parametro dataRicezioneSpedizione del WS del fornitore del protocollo');


INSERT INTO comuni(CODICECOMUNE,COMUNE,SIGLAPROVINCIA,PROVINCIA,REGIONE,CAP,CF,CODICEISTAT,CODICEISTATREGIONE,CODICESTATOESTERO) VALUES('M403','CORIGLIANO-ROSSANO','CS','COSENZA','CALABRIA','87064','M403','078157','18',NULL);
INSERT INTO comuni(CODICECOMUNE,COMUNE,SIGLAPROVINCIA,PROVINCIA,REGIONE,CAP,CF,CODICEISTAT,CODICEISTATREGIONE,CODICESTATOESTERO) VALUES('M385','CASALI DEL MANCO','CS','COSENZA','CALABRIA','87059','M385','078156','18',NULL);
INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'COMPORTAMENTI_MERCATI',
    'URL_APP_AMBULANTE_WEB',
    'L''url dell''APP di accesso web degli ambulanti / Spuntisti.'
);
INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'COMPORTAMENTI_MERCATI',
    'SERV_GRAD_ADD_NOME_GIORNO',
    'Nel servizio delle graduatorie quando viene passato il nome del mercato gli viene aggiunto anche il nome del giorno (es. TORINO--> CINCINNATO LUN. Valori ammessi S/N (predefinito N)'
);
INSERT INTO TIPICONTESTOESPORTAZIONE(CODICE, DESCRIZIONE) VALUES ('MPT', 'Esportazione di giornate di calendario');


INSERT INTO TIPICONTESTOESPORTAZIONE (CODICE, DESCRIZIONE) VALUES ('GEN', 'Esportazioni generiche');

INSERT INTO clmenu_java (
    id,
    descrizione,
    pagina,
    menulink,
    software,
    jsp,
    layouttesti,
    softwareesclusi,
    link_standard,
    tipo_funzionalita,
    menulink_v2
) VALUES (
    1040,
    'Reportistica',
    'report/createReportGenerici.htm?software=TT',
    '42',
    'TT',
    'JAVA',
    '0',
    'AB',
    'report/createReportGenerici.htm?software=TT',
    'S',
    '42'
);
INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE) VALUES ('RIC_ANAG_COLLEGATE', 'COMPONENTE PER LE RICERCHE DELLE ANAGRAFICHE COLLEGATE.', 0);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('RIC_ANAG_COLLEGATE','COMPONENTE','L''IDENTIFICATIVO DEL COMPONENTE ES RIC_ANAG_COLL_CSI');


INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE)  VALUES ('RIC_ANAG_COLL_CSI', 'COMPONENTE PER LE RICERCHE DELLE ANAGRAFICHE COLLEGATE SPECIFICO PER L''AMBIENTE CSI PIEMONTE', 0);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('RIC_ANAG_COLL_CSI','URL_WS','L''INDIRIZZO DEL WS DI RICERCA');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('RIC_ANAG_COLL_CSI','WS_TIMEOUT','TIMEOUT DI ATTESA DELLA RISPOSTA DEL SERVIZIO (DEFAULT 12000 - 12 SECONDI)');

INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('GOOGLE_MAPS', 'Parametri per le integrazioni con le mappe di google', 0);
INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('GOOGLE_MAPS', 'API_KEY', 'Api key da utilizzare per le chiamate, richiede un account di billing con accesso alle mappe abilitato');
INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('GOOGLE_MAPS', 'MAP_BOUNDS', 'Limiti da utilizzare per restringere l''area di ricerca delle vie, se presente va impostato nel formato google.LatLng (es. { ''ne'': { ''lat'': 43.23410747538151, ''lng'': 12.61002541992184 }, ''sw'': { ''lat'': 43.03367373857498, ''lng'': 12.108774199218715 } })');

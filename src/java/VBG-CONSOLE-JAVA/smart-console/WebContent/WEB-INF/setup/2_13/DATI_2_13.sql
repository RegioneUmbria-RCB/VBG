insert into clmenu_java (id,descrizione,pagina,menulink,software,jsp,layouttesti,verticalizzazione,softwareesclusi,link_standard,tipo_funzionalita) VALUES (996, 'Dati tecnici', 'configurazione/createAndViewDatitecnici.htm?software=TT', '812', 'TT', 'JAVA', 0, null, null,'configurazione/createAndViewDatitecnici.htm?software=TT', 'S');
INSERT INTO MAPOGGETTI (NOMETABELLA, NOMECAMPO) values ('ISTANZEPROCURE', 'CODICEOGGETTOPROCURA');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AIDA', 'AGG-STATO_DATA_SCHEDULAZIONE', 'Pattern del parametro gg/MM/yyyy. Indicare la data dalla quale il nodo NLA_AIDA inizierà a processare i movimenti che saranno inviati al metodo aggiornaStato presente nei WS esposti dal sistema AIDA. Questo valore verrà aggiornato con la data dell’ultimo invio dei dati ad AIDA. Se un movimento verrà modificato successivamente all’inserimento le modifiche non verranno inviate (tale parametro viene confrontato con MOVIMENTI.DATAINSERIMENTO).');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AIDA','AGG-STATO_INVIO_ALLEGATI','Invia o meno le informazioni relative agli allegati al metodo aggironaStato esposto dai WS di AIDA. Non invia l’allegato fisico. Può assumere due valori . 1 : invia la lista degli allegati, 0: non invia la lista degli allegati');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('CART', 'NUOVA_DOMANDA.ID_TIPOPROCEDURA', 'E'' utilizzato nell''elaborazione dei messaggi di presentazione domanda ricevuti dal front-office per specificare l''id del tipo di procedura che dovrà essere assegnato alle nuove istanze che vengono create.');

insert into clmenu_java (id,descrizione,pagina,menulink,software,jsp,layouttesti,verticalizzazione,softwareesclusi,link_standard,tipo_funzionalita) VALUES (1000,'Utilità', null, '0AY','*',null,0,null,NULL,NULL,'S');
insert into clmenu_java (id,descrizione,pagina,menulink,software,jsp,layouttesti,verticalizzazione,softwareesclusi,link_standard,tipo_funzionalita) values (1001,'Export SI-VBG', 'importexportsivbg/createExport.htm?software=SOFTWARE', '0AY0','*','JAVA',0,'IMPORT-EXPORT-SIVBG',null,'importexportsivbg/createExport.htm?software=SOFTWARE','S');
insert into clmenu_java (id,descrizione,pagina,menulink,software,jsp,layouttesti,verticalizzazione,softwareesclusi,link_standard,tipo_funzionalita) VALUES (1002,'Import SI-VBG', 'importexportsivbg/createImport.htm?software=SOFTWARE', '0AY1','*','JAVA',0,'IMPORT-EXPORT-SIVBG',NULL,'importexportsivbg/createImport.htm?software=SOFTWARE','S');

update clmenu_java set descrizione = 'Individuazione interventi' where id=886; update clmenu_java set descrizione = 'Albero degli interventi' where id=157;

Insert into SOFTWARE (CODICE,DESCRIZIONE,MODULOOPZIONALE,DESCRIZIONELUNGA,ACCESSORAPIDO,ORDINE) values ('PM','Polizia municipale',1,'Polizia municipale',0,51);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_IRIDE', 'CODICEAMMINISTRAZIONE', 'Indica il codice di amministrazione da passare al web service di Iride, deve essere fornito dall''amministratore del protocollo. Questo dato indica su quale amministrazione protocollare, infatti Iride può avere un''installazione MultiDB, in questo caso va valorizzato questo dato che, tra le altre cose può indicare se si sta utilizzando un ambiente di Test o di Produzione. Questo parametro va in simbiosi con PROTOCOLLO_IRIDE2, evoluzione di Iride che gestisce proprio le installazioni di Iride MultiDB.');
UPDATE verticalizzazioniparametribase SET Descrizione = 'Indica il codice di amministrazione da passare al web service di Iride, deve essere fornito dall''amministratore del protocollo. Questo dato indica su quale amministrazione protocollare, infatti Iride può avere un''installazione MultiDB, in questo caso va valorizzato questo dato che, tra le altre cose può indicare se si sta utilizzando un ambiente di Test o di Produzione.' WHERE modulo='PROTOCOLLO_IRIDE' AND parametro='CODICEAMMINISTRAZIONE';

UPDATE cittadinanza SET flg_paese_comunitario=0;

UPDATE cittadinanza SET flg_paese_comunitario = 1 WHERE cittadinanza IN (
'AUSTRIA',
'BELGIO',
'BULGARIA',
'CIPRO',
'DANIMARCA',
'ESTONIA',
'FINLANDIA',
'FRANCIA',
'GERMANIA',
'GRECIA',
'ITALIA',
'LETTONIA',
'LITUANIA',
'LUSSEMBURGO',
'MALTA',
'POLONIA',
'PORTOGALLO',
'ROMANIA',
'SLOVACCHIA',
'SLOVENIA',
'SPAGNA',
'SVEZIA',
'UNGHERIA',
'PAESI BASSI - OLANDA',
'EIRE (IRLANDA)',
'GRAN BRETAGNA E IRLANDA DEL NORD',       
'CECA REPUBBLICA'   
);


INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('IMPORT-EXPORT-SIVBG', 'Se abilitato, sarà possibile effettuare l''import/export degli interventi e dei procedimenti con il Repertorio della regione Umbria');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('IMPORT-EXPORT-SIVBG', 'URL_WS_REPERTORIO', 'Tale parametro deve contenere la URL del web service che la regione Umbria espone per effettuare le operazioni di import/export dei procedimenti (es:http://10.101.223.226:8090/RepertorioProc/services/RepertorioWS)');

update clmenu_java set pagina='tipicausalioneri/list.htm?software=SOFTWARE&flgTipicausaliinteressi=0',link_standard='tipicausalioneri/list.htm?software=SOFTWARE&flgTipicausaliinteressi=0' where id=457;
update clmenu_java set pagina='tipicausalioneri/list.htm?software=TT&flgTipicausaliinteressi=0',link_standard='tipicausalioneri/list.htm?software=TT&flgTipicausaliinteressi=0' where id=873;

insert into clmenu_java ( id,descrizione,pagina,menulink,software,jsp,layouttesti,verticalizzazione,softwareesclusi,link_standard,tipo_funzionalita) VALUES (1003,'Interessi di mora','tipicausalioneri/list.htm?software=SOFTWARE&flgTipicausaliinteressi=1', '0AZ24', '*','JAVA',0,NULL,'PR,FI,AB','tipicausalioneri/list.htm?software=SOFTWARE&flgTipicausaliinteressi=1','E');
insert into clmenu_java ( id,descrizione,pagina,menulink,software,jsp,layouttesti,verticalizzazione,softwareesclusi,link_standard,tipo_funzionalita) VALUES (1004,'Interessi di mora','tipicausalioneri/list.htm?software=TT&flgTipicausaliinteressi=1', '00BR', 'TT','JAVA',0,NULL,'PR,FI,AB','tipicausalioneri/list.htm?software=TT&flgTipicausaliinteressi=1','E');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE','ESCLUDI_RICERCA_PER_PF','Indica se sulla pagina di inserimento di una nuova anagrafica deve essere attiva la funzionalità di ricerca di informazioni relative al codice fiscale passato per le persone fisiche. I possibili valori sono: 0 disattiva e 1 attiva');
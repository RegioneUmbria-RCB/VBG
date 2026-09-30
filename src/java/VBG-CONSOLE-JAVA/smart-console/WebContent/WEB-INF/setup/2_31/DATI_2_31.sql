INSERT INTO CLMENU_JAVA (ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, LAYOUTTESTI, SOFTWAREESCLUSI,LINK_STANDARD,TIPO_FUNZIONALITA) VALUES ('1025', 'Configurazione dehors', 'dehorscfg/list.htm?software=SOFTWARE', '0AZN', '*', 'JAVA', '0', 'PR,FI,AB','dehorscfg/list.htm?software=SOFTWARE','E');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'METADATO_RIEPILOGODOMANDA', 'Indica il valore del metadato presente dentro la tabella OGGETTI_METADATI con chiave TIPO_DOCUMENTO relativo al file di riepilogo domanda, viene utilizzato dal componente che gestisce la protocollazione in modo tale da spostare come primo allegato (documento principale) il riepilogo della domanda. Si rende necessario parametrizzare questa informazione in quanto questo valore cambia in base al frontoffice utilizzato, ad esempio suaper utilizza il valore MDA-PDF mentre l''Area Riservata utilizza RiepilogoDomanda.');
INSERT INTO software (codice, descrizione, moduloopzionale,descrizionelunga,accessorapido,ordine) VALUES ('S1', 'Sociale', 1,'Modulo per la gestione dei procedimenti ''sociali''',0,69);
INSERT INTO software (codice, descrizione, moduloopzionale,descrizionelunga,accessorapido,ordine) VALUES ('P4', 'Passi carrabili', 1,'Modulo per la gestione dei passi carrabili',0,69);

insert into verticalizzazioniparametribase (modulo, PARAMETRO, descrizione) values(
'SIT_7DBTL',
'URL_ZOOM_DA_MAPPALE',
'Url da utilizzare per visualizzare un punto nella cartografia comunale a partire da un mappale');

insert into verticalizzazioniparametribase (modulo, PARAMETRO, descrizione) values(
'SIT_7DBTL',
'URL_ZOOM_DA_CIVICO',
'Url da utilizzare per visualizzare un punto nella cartografia comunale a partire da un civico');

insert into verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) values ('AREA_RISERVATA', 'DESCR_DELEGA_A_TRASMETTERE', 'Descrizione della delega a trasmettere nel riepilogo domanda, se lasciato vuoto sarà "Delega a trasmettere"');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_HALLEY', 'MULTI_MITT_DEST', 'Questo parametro indica se il componente che crea la segnatura 
da inviare come request al web service HALLEY debba inviare tutte le anagrafiche che arrivano dal backoffice 
(valore uguale a 1), oppure solo la prima (non presente, valore vuoto o qualsiasi altro valore diverso da 1).
Questo parametro si rende necessario in quanto le versioni più vecchie di halley seguivano le specifiche DocArea che non consentiva 
l''utilizzo di più di un mittente / destinatario.');

UPDATE CLMENU_JAVA SET MENULINK = '0AZ6' WHERE ID = 1007;

insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PAGAMENTI_MIP_RPCSUAP', 'CODICE_TIPO_PAGAMENTO', 'Id del tipo pagamento da utilizzare per i pagamenti online');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_SIGEDO', 'TIPOREGISTRO', 'Questo parametro è richiesto obbligatoriamente sul metodo aggiungiAllegati del web service di protocollazione e fa da chiave, insieme a numero e anno protocollo, come riferimento di un determinato protocollo al quale vengono aggiunti documenti.');

insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_ATTIVO', 'VIS_BOTTONE_ADD_DOCUMENTO','Gestisce la presenza della funzionalità aggiungi documento all''interno della funzionalità ''Leggi Protocollo''. Può assumere i valori 1 visualizza, 0 non visualizzare');
INSERT INTO MASTERKEY(TABLENAME, COLUMNNAME) VALUES ('ALBEROPROC_TIPISOGGETTO', 'ID');

insert into verticalizzazioniparametribase(modulo, parametro, descrizione) values('PROTOCOLLO_ATTIVO', 'IS_SMISTAMENTO_MULTIPLO','Gestisce la possibilità di inserire più destinatari in fase di protocollazione i flussi Arrivo e Interno. Può assumere i valori 1, permetti più di un destinatario; 0 o null non permette più di un destinatario');

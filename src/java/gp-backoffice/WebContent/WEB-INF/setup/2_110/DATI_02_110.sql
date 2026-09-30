UPDATE COMUNI SET SIGLAPROVINCIA='UD',PROVINCIA='UDINE', REGIONE='FRIULI VENEZIA GIULIA',CAP='33012',CODICEISTAT='030189',CODICEISTATREGIONE='06' WHERE CODICECOMUNE='I421';

UPDATE clmenu_java SET menulink='00F', MENULINK_V2='00F' WHERE ID=750;

UPDATE clmenu_java SET menulink='00F1', MENULINK_V2='00F1' WHERE ID=751;

UPDATE clmenu_java SET menulink='00F2', MENULINK_V2='00F2' WHERE ID=752;

UPDATE clmenu_java SET menulink='00F0', MENULINK_V2='00F0' WHERE ID=780;

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_INSIEL', 'DISATTIVA_CTRL_DOCS', 'Impostando il parametro a 1 indica al componente di protocollazione di Insiel di non procedere con il controllo sulla duplicazione dei documenti, e sarà quindi possibile inviare più documenti identici, quindi con lo stesso codice hash, cosa che di default non è possibile fare in quanto il protocollo blocca la protocollazione.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_GEPROT', 'GESTIONE_INDIRIZZO_TELEM_ANAG', 'Parametro che serve per mantenere la compatibilità con vecchie versioni, serve per indicare se il componente deve associare l''indirizzo e-mail dell''anagrafica sull''indirizzo telematico (in questo caso lasciare vuoto), oppure lasciare la gestione al parametro GESTIONE_PEC della regola PROTOCOLLO_ATTIVO (in questo caso valorizzare a 1.');

UPDATE Pay_Regcausali_Parametri SET CHIAVE='CODICE_TASSONOMIA' WHERE CHIAVE='TASSONOMIA_PAGAMENTO';

INSERT INTO CLMENU_JAVA(ID,DESCRIZIONE,PAGINA,MENULINK,SOFTWARE,JSP,LINK_STANDARD,TIPO_FUNZIONALITA,MENULINK_V2) VALUES (1053,'Configuratore calcoli','Utilita/ConfiguratoreCalcoli/ConfiguratoreCalcoli.aspx?Software=TT','74','TT','NET','Utilita/ConfiguratoreCalcoli/ConfiguratoreCalcoli.aspx?Software=TT','S','704');

INSERT INTO comuni (CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CAP, CF, CODICEISTAT, CODICEISTATREGIONE, CODICESTATOESTERO)
VALUES('Z161','TERRITORI PALESTINESI','EE','STATIESTERI',NULL,NULL,'Z161','161',NULL,'161');


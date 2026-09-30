UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = 'Serve per limitare al minimo gli errori dovuti al protocollo halley e riguardanti le anagrafiche presenti in modo non univoco 
sulle loro anagrafiche. Il web service di protocollo infatti confronta le anagrafiche che arrivano sulla request 
con quelle di protocollo per NOME e COGNOME, nel caso in cui fossero presenti più di una volta vengono restituiti 
gli errori 108 (Protocollo in Arrivo) e 112 (Protocollo in Partenza). 
Valorizzando a 1 questo parametro, solo nel caso in cui vengono restituiti i due errori appena descritti dal web service, 
il plug in di protocollo riproverà a protocollare aggiungendo al mittente / destinatario anche il codice fiscale / partita iva.' WHERE MODULO = 'PROTOCOLLO_HALLEY' and PARAMETRO = 'INVIA_CF';

INSERT INTO CATEGORIEEVENTIBASE (ID, DESCRIZIONE) VALUES ('ASS-POST-GRAD', 'Eventi relativi all''assegnazione di un posteggio');


UPDATE clmenu_java SET pagina='nlaservizi/list.htm?software=TT', software='TT', link_standard='nlaservizi/list.htm?software=TT' WHERE id=1007;
UPDATE clmenu_java SET link_standard='dyn2modellit/list.htm?software=SOFTWARE' WHERE id=158;
UPDATE clmenu_java SET link_standard='dyn2campi/list.htm?software=SOFTWARE' WHERE id=159;
UPDATE clmenu_java SET link_standard='dyn2campi/list.htm?software=TT' WHERE id=449;
UPDATE clmenu_java SET tipo_funzionalita = 'S' WHERE tipo_funzionalita IS NULL;

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PEOPLE', 'WS_TIMEOUT', 'Se configurato, permette di settare il timeout (in millisecondi) delle chiamate WS client effettuate dal nodo NLAPeople');

update configurazioneutente set valore = replace(valore,'inbox_list_mr_=1000', 'inbox_list_mr_=100') where valore like '%inbox_list_mr_=1000%' and nomeparametro='CONF_UTENTE_PECINBOX_JMESA';
UPDATE ALBEROPROC SET FLAG_PROG_ATT_OSSERV=0 WHERE FLAG_PROG_ATT_OSSERV IS NULL;
INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('OSSERVATORIO_REGIONALE','Regole per estrapolare i dati secondo gli standard degli osservatori regionali.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO, DESCRIZIONE) VALUES ('OSSERVATORIO_REGIONALE','SERVIZIO_ATTIVO','E'' il nome del servizio di implementazione. (attualmente OSSERVATORIO_FVG)');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('OSSERVATORIO_FVG','Regole di esportazione dati per l''osservatorio regionale del Friuli Venezia Giulia');

update CLMENU_JAVA set menulink = '00B03' where id=1021;
update CLMENU_JAVA set menulink = '0A7' where id=1005;
update CLMENU_JAVA set menulink = '0A9' where id=995;

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE,FLAG_GESTCOMUNE) VALUES ('SIEDER', 'Se abilitato consente di attivare e specificare le configurazioni per l''\integrazione con SIEDER (Sistema Integrato dell''Edilizia dell''Emilia-Romagna)',1);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIEDER', 'URL_WS_GESTIONALE', ' url del servizio WebService per il recupero delle pratiche e file MUDE.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIEDER', 'URL_WS_DOWNLOAD_ISTANZA', 'Url del servizio REST per il download del file pdf/a MUDE');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIEDER', 'URL_WS_DOWNLOAD_ALLEGATI', 'Url del servizio REST per il download degli allegati della pratica MUDE.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIEDER', 'NOME_CERTIFICATO_AUTH', 'Nome del certificato usato perl''autenticazione server2server');



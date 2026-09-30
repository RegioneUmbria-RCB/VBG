insert into mapoggetti values ('FO_ARCONFIGURAZIONE','CODICEOGGETTO_WORKFLOW');
insert into mapoggetti VALUES ('FO_ARCONFIGURAZIONE','CODICEOGGETTO_MENUXML');
insert into verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('FILESYSTEM','DIRECTORY_LOCALE','Rappresenta il percorso alla directory del server dove vengono salvati/letti i file.');
INSERT INTO CONTENTTYPES(CT_MIMETYPE, CT_EXTENSION) VALUES ('application/x-7z-compressed', ';7z;');
delete from VERTICALIZZAZIONIPARAMETRI WHERE PARAMETRO='COD_NATURA_SCIA' and MODULO='AREA_RISERVATA';
delete from VERTICALIZZAZIONIPARAMETRIBASE WHERE PARAMETRO='COD_NATURA_SCIA' and MODULO='AREA_RISERVATA';
insert into verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('STC','NLA_IDNODO_AIDA','Id del nodo NLA AIDA registrato su STC (utilizzato per individuare le chiamate provenienti da AIDA)');
update TIPIMOVIMENTO set FLAG_PUBBLICAALLEGATI=1;
update MOVIMENTIALLEGATI set FLAG_PUBBLICA=1;
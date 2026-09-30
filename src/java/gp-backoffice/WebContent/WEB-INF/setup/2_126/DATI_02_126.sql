INSERT INTO SOFTWARE(CODICE,DESCRIZIONE,MODULOOPZIONALE,DESCRIZIONELUNGA,ACCESSORAPIDO,ORDINE) VALUES ('LT','Locazioni turistiche',1,'Locazioni turistiche',0,42);

INSERT INTO contenttypes (CT_MIMETYPE,CT_EXTENSION) VALUES ('application/json',';json;');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS', 'CREA_FASCICOLO_DA_MOVIMENTO', 'Se impostato a 1 permette la creazione del fascicolo, se non presente, durante la protocollazione del movimento; di default è 0');

UPDATE CLMENU_JAVA SET PAGINA = 'configurazionecalcoli/list.htm?software=TT', JSP = 'JAVA', LINK_STANDARD = 'configurazionecalcoli/list.htm?software=TT' WHERE ID = 1053;
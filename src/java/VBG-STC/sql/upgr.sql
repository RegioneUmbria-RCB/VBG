--------------------------------------------------------
--  Modifiche del 02/03/2011
--------------------------------------------------------
alter table pratiche modify idente varchar2(10 char);
alter table pratiche modify idpratica varchar2(50 char);
alter table configurazione modify idente varchar2(10 char);

--------------------------------------------------------
--  Modifiche del 18/03/2011
--------------------------------------------------------
alter table pratiche drop constraint PRATICHE_CONFIGURAZIONE_FK1;
alter table configurazione drop constraint CONFIGURAZIONE_UK1;
alter table configurazione drop column idente;
alter table configurazione drop column idsportello;
alter table configurazione drop column ente;
alter table configurazione drop column sportello;
alter table configurazione add descrizione varchar2(100 char);
alter table PRATICHE add FKIDNODO number(8,0);
alter table pratiche add constraint PRATICHE_CONFIGURAZIONE_FK1 foreign key(fkidnodo) references configurazione(idnodo);
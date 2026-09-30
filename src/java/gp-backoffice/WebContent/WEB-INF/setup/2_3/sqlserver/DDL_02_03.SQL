SET IMPLICIT_TRANSACTIONS ON 
GO
alter table fo_arconfigurazione add msg_invio_pec varchar(4000 )
GO
ALTER TABLE DOMANDESTC add id_nodo varchar(10)
GO
alter table AMMINISTRAZIONI add STC_IDNODO varchar(10)
GO
alter table istanzehummingbird ALTER COLUMN id_pratica varchar(50) null
GO
alter table istanzehummingbird ALTER COLUMN docnum numeric(6,0) NULL
GO
alter table PERMISTANZE ALTER COLUMN TABELLA  numeric(6,0) null
GO
ALTER TABLE ISTANZE ADD CODICEPRATICATEL varchar(40) NULL
GO
ALTER TABLE CONFIGURAZIONE ADD CODICE_ACCREDITAMENTO varchar(30)
GO
SET IMPLICIT_TRANSACTIONS ON 
GO
alter table alberoproc ALTER COLUMN progressivoistanze varchar(25)
GO
alter table configurazione ALTER COLUMN progressivoistanze varchar(25)
GO
alter table inventarioprocedimentioneri add constraint fk_invent_proconer_tipi_caus foreign key (idcomune,fk_coid) references tipicausalioneri(idcomune,co_id)
GO
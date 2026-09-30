alter table alberoproc modify progressivoistanze varchar(25);
alter table configurazione modify progressivoistanze varchar(25);
alter table inventarioprocedimentioneri add constraint FK_INVENT_PROCONER_TIPI_CAUS foreign key (idcomune,fk_coid) references tipicausalioneri(idcomune,co_id);
delete from messaggiattivita;
delete from attivita;
delete from messaggipratiche;
delete from pratiche;
delete from sicurezza;

-- CANCELLARE I RECORD DELLE TABELLE SOTTOSTANTI SOLAMENTE SE 
-- SI VUOLE REINIZIALIZZARE TUTTO STC COMPRESE LE CONFIGURAZIONI
-- delete from configurazione;
-- delete from id_table;

-- Aggiorna (annulla) tutte le registrazioni fatte con STC
-- Va eseguito nel DB di sigepro
update istanze set creato_da_stc=0 where creato_da_stc=1;
update movimenti set inviato_con_stc=0 where inviato_con_stc=1;
update movimenti set creato_da_stc=0 where creato_da_stc=1;
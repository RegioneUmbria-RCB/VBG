ALTER TABLE TIPIMOV_STC_MAPPING ADD FLAG_ENDO_OBBLIGATORIO NUMERIC(1,0) DEFAULT 0 NOT NULL;

alter table movimentimail modify DESTINATARIO     varchar(2000);            
alter table movimentimail modify DESTINATARIOCC   varchar(2000);        
alter table movimentimail modify DESTINATARIOBCC   varchar(2000);


alter table MESSAGGI_MAIL modify DESTINATARIO     varchar(2000);            
alter table MESSAGGI_MAIL modify DESTINATARIOCC   varchar(2000);        
alter table MESSAGGI_MAIL modify DESTINATARIOBCC   varchar(2000);


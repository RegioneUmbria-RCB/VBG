ALTER TABLE TIPIMOV_STC_MAPPING ADD FLAG_ENDO_OBBLIGATORIO NUMBER(1,0)  DEFAULT 0 NOT NULL;

alter table movimentimail modify DESTINATARIO     varchar2(2000 char);            
alter table movimentimail modify DESTINATARIOCC   varchar2(2000 char);        
alter table movimentimail modify DESTINATARIOBCC   varchar2(2000 char);


alter table MESSAGGI_MAIL modify DESTINATARIO     varchar2(2000 char);            
alter table MESSAGGI_MAIL modify DESTINATARIOCC   varchar2(2000 char);        
alter table MESSAGGI_MAIL modify DESTINATARIOBCC   varchar2(2000 char);


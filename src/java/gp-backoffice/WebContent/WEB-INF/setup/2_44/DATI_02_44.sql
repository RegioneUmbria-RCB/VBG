
insert into CONTENTTYPES (CT_MIMETYPE,CT_EXTENSION) values ('image/svg+xml',';svg;');

insert into CONTENTTYPES (CT_MIMETYPE,CT_EXTENSION) values ('model/vnd.dwf',';dwf;');


Insert into VERTICALIZZAZIONIPARAMETRIBASE(modulo, parametro, descrizione) values('SIEDER', 'KEYSTORE_LOCATION','Il parametro indica dove è localizzato il file keystore per l''autenticazione ai servizi SIEDER');

Insert into VERTICALIZZAZIONIPARAMETRIBASE(modulo, parametro, descrizione) values('SIEDER', 'KEYSTORE_PASSWORD','Il parametro indica la password della chiave del file keystore per l''autenticazione ai servizi SIEDER');

Insert into VERTICALIZZAZIONIPARAMETRIBASE(modulo, parametro, descrizione) values('SIEDER', 'TRUSTSTORE_LOCATION','Il parametro indica  dove è localizzato il file TRUSTSTORE');

Insert into VERTICALIZZAZIONIPARAMETRIBASE(modulo, parametro, descrizione) values('SIEDER', 'TRUSTSTORE_PASSWORD','Il parametro indica la password della chiave del file TRUSTSTORE');

Insert into VERTICALIZZAZIONIPARAMETRIBASE(modulo, parametro, descrizione) values('MAIL_SERVICE', 'POSTA_USCITA_FOLDERNAME','Se valorizzato i messaggi di posta in uscita saranno salvati in questa folder, altrimenti non verranno salvati (comportamento di default), se attivato al protocolla di un movimento viene scaricato l’eml della mail inviata');



INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE VALUES ('STC','NLA_IDNODO_SIEDER','Id del nodo NLA SIEDER registrato su STC (utilizzato per individuare le chiamate provenienti dal nodo SIEDER)');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(modulo,parametro,descrizione) VALUES ('SIEDER','TIPOMOV_COMUNICAZIONE_GENERICA','Il parametro indica il nome del tipo movimento da usare in caso di <b>COMUNICAZIONE</b> generica dal sistema SIEDER'  );
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE  (  modulo, parametro, descrizione) VALUES (   'SIEDER',   'TIPOMOV_RICHIESTA_GENERICA', 'Il parametro indica il nome del tipo movimento da usare in caso di <b>RICHIESTA</b> generica dal sistema SIEDER' );

insert into verticalizzazionibase (modulo, descrizione) values ('LIVORNO_SERVIZI_CITTADINO', 'se attivato mostra alcune configurazioni specifiche dell''integrazione previste per lo sportello al cittadino (es: MODULISTICA DRUPAL)');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'DATA_SWITCH', 'Valorizzare questo parametro qualora fosse indispensabile utilizzare anche la fascicolazione. In particolare va valorizzato con la data di switch da un protocollo qualsiasi a URBI, e va valorizzato solo se il vecchio protocollo non ha possibilità di essere letto e non ha il dato relativo al fascicolo. Di pari passo va valorizzato anche il parametro ID_FASCICOLO_GENERICO.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'ID_FASCICOLO_GENERICO', 'Valorizzare questo dato qualora si voglia indicare un fascicolo generico per protocolli antecedenti ad una certa data valorizzata nel parametro DATA_SWITCH. Questo si rende necessario qualora sia presente una fase transitoria tra un protocollo e URBI e qualora la fascicolazione sia obbligatoria e quando non sia possibile andare a rileggere il fascicolo su un protocollo presente in un altro sistema di protocollazione.');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE)  VALUES ('WSANAGRAFE2','Gestisce le configurazioni della componente WSANAGRAFE2 per la ricerca dei dati anagrafici di persone fisiche e giuridiche', 0);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE2','LISTA_COMPONENTI','Lista dei servizi attivi. L''ordine di esecuzione va spoecificato nella relativa configurazione del singolo componente. Es. dei servizi ad oggi censiti: <ul><li>servizioAnagraficaDefaultService(Il WS anagrafe Interno VBG)</li><li>servizioAnagraficaNlaPddService (<b>WSANAGRAFE_NLAPDD</b>)</li><li>servizioAnagraficaCedafService(<b>WSANAGRAFE_CEDAF</b></li><li>servizioAnagraficaCSIServiceImpl(<b>WSANAGRAFE_CSI</b>)</li><li>servizioAnagraficaGenovaServiceImpl(<b>WSANAGRAFE_GENOVA</b>)</li></li><li>servizioAnagraficaMaggioliService(<b>WSANAGRAFE_MAGGIOLI</b>)</li></ul>');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE2','USA_PERSONA_FISICA','Il parametro indica se il servizio di anagrafe di default (WSANAGRAFE .NET) deve essere attivato per la ricerca di persone fisiche. Accetta i valori S o N');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE2','USA_PERSONA_GIURIDICA','Il parametro indica se il servizio di anagrafe di default (WSANAGRAFE .NET) deve essere attivato per la ricerca di persone giuridiche. Accetta i valori S o N.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE2','ORDINE_ESECUZIONE','Indica l''ordine di esecuzione rispetto alle altre componenti indicate nel parametro LISTA_COMPONENTI della verticalizzazione WSANAGRAFE2');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE2','ERROR_CASE','Il parametro indica se in caso di errore la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE2','RETURN_NULL_VALUE','Il parametro indica se in caso di risposta NULLA la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');




INSERT INTO VERTICALIZZAZIONIBASE(MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) VALUES ('WSANAGRAFE_NLAPDD','Se attivato permette di accedere al sistema anagrafico della componente NLA-PDD-RI, tale sistema permette di recuperare SOLO le persone giuridiche interrogando i servizi di impresa in un giorno.',0);
INSERT INTO VERTICALIZZAZIONIBASE(MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) VALUES ('WSANAGRAFE_CEDAF','Se attivato permette di accedere al sistema anagrafico di CEDAF, tale sistema permette di recuperare SOLO le persone fisiche.',0);
INSERT INTO VERTICALIZZAZIONIBASE(MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) VALUES ('WSANAGRAFE_CSI','Se attivato permette di accedere al sistema anagrafico di CSI (AEEP), tale sistema permette di recuperare SOLO le persone giuridiche.',0);
INSERT INTO VERTICALIZZAZIONIBASE(MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) VALUES ('WSANAGRAFE_GENOVA','Se attivato permette di accedere al sistema anagrafico di GENOVA, tale sistema permette di recuperare SOLO persone fisiche.',0);


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_NLAPDD','URL','Indirizzo del ws anagrafe.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CEDAF','URL','Indirizzo del ws anagrafe.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CSI','URL','Indirizzo del ws anagrafe.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_GENOVA','URL','Indirizzo del ws anagrafe.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_GENOVA','URL_TOKEN','Indirizzo del servizio di autenticazione e recupero del Token anagrafe.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_GENOVA','CHIAVE','Consumer Key servizio di autenticazione e recupero del Token per l''interrogazione del WS Anagrafe.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_GENOVA','SEGRETO','Secret Key del servizio di autenticazione e recupero del Token per l''interrogazione del WS Anagrafe.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_MAGGIOLI','USERNAME','Nome Utente per ottenere il token.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_MAGGIOLI','PASSWORD','Password per ottenere il token.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_MAGGIOLI','ALIAS','Alias per ottenere il token, di solito uguale all''username.');



INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_NLAPDD','ORDINE_ESECUZIONE','Indica l''ordine di esecuzione rispetto alle altre componenti indicate nel parametro LISTA_COMPONENTI della verticalizzazione WSANAGRAFE2');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CEDAF','ORDINE_ESECUZIONE','Indica l''ordine di esecuzione rispetto alle altre componenti indicate nel parametro LISTA_COMPONENTI della verticalizzazione WSANAGRAFE2');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CSI','ORDINE_ESECUZIONE','Indica l''ordine di esecuzione rispetto alle altre componenti indicate nel parametro LISTA_COMPONENTI della verticalizzazione WSANAGRAFE2');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_GENOVA','ORDINE_ESECUZIONE','Indica l''ordine di esecuzione rispetto alle altre componenti indicate nel parametro LISTA_COMPONENTI della verticalizzazione WSANAGRAFE2');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_MAGGIOLI','ORDINE_ESECUZIONE','Indica l''ordine di esecuzione rispetto alle altre componenti indicate nel parametro LISTA_COMPONENTI della verticalizzazione WSANAGRAFE2');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_NLAPDD','ERROR_CASE','Il parametro indica se in caso di errore la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CEDAF','ERROR_CASE','Il parametro indica se in caso di errore la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CSI','ERROR_CASE','Il parametro indica se in caso di errore la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_GENOVA','ERROR_CASE','Il parametro indica se in caso di errore la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_MAGGIOLI','ERROR_CASE','Il parametro indica se in caso di errore la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_NLAPDD','RETURN_NULL_VALUE','Il parametro indica se in caso di risposta NULLA la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CEDAF','RETURN_NULL_VALUE','Il parametro indica se in caso di risposta NULLA la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CSI','RETURN_NULL_VALUE','Il parametro indica se in caso di risposta NULLA la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_GENOVA','RETURN_NULL_VALUE','Il parametro indica se in caso di risposta NULLA la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_MAGGIOLI','RETURN_NULL_VALUE','Il parametro indica se in caso di risposta NULLA la ricerca deve fermarsi oppure proseguire attivando le altre componenti in ordine di esecuzione. Il parametro è gestito nella componente WSANAGRAFE2. Accetta i valori S o N (default N)');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_NLAPDD','USA_PERSONA_FISICA','Il parametro indica se il servizio deve essere attivato per la ricerca di persone fisiche. Accetta i valori S o N. Al momento usato solo per persone giuridiche.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CEDAF','USA_PERSONA_FISICA','Il parametro indica se il servizio deve essere attivato per la ricerca di persone fisiche. Accetta i valori S o N. Al momento usato solo per persone fisiche.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CSI','USA_PERSONA_FISICA','Il parametro indica se il servizio deve essere attivato per la ricerca di persone fisiche. Accetta i valori S o N. Al momento usato solo per persone giuridiche');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_GENOVA','USA_PERSONA_FISICA','Il parametro indica se il servizio deve essere attivato per la ricerca di persone fisiche. Accetta i valori S o N. Al momento usato solo per persone fisiche.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_MAGGIOLI','USA_PERSONA_FISICA','Il parametro indica se il servizio deve essere attivato per la ricerca di persone fisiche. Accetta i valori S o N.');



INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_NLAPDD','USA_PERSONA_GIURIDICA','Il parametro indica se il servizio deve essere attivato per la ricerca di persone giuridiche. Accetta i valori S o N. Al momento usato solo per persone giuridiche.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CEDAF','USA_PERSONA_GIURIDICA','Il parametro indica se il servizio deve essere attivato per la ricerca di persone giuridiche. Accetta i valori S o N. Al momento usato solo per persone fisiche.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_CSI','USA_PERSONA_GIURIDICA','Il parametro indica se il servizio deve essere attivato per la ricerca di persone giuridiche. Accetta i valori S o N. Al momento usato solo per persone giuridiche');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_GENOVA','USA_PERSONA_GIURIDICA','Il parametro indica se il servizio deve essere attivato per la ricerca di persone giuridiche. Accetta i valori S o N. Al momento usato solo per persone fisiche.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_MAGGIOLI','USA_PERSONA_GIURIDICA','Il parametro indica se il servizio deve essere attivato per la ricerca di persone giuridiche. Accetta i valori S o N.');



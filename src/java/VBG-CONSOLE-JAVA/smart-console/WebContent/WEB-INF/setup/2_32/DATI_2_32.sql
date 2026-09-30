INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PADOC', 'URL_LEGGI_PROTO', 'Url del servizio rest messo a disposizione dal fornitore di protocollo che serve per fare una lettura di protocollo. Al servizio devono essere indicati i parametri di autenticazione USERNAME e PASSWORD. Il servizio risponde in maniera sincrona a differenza del ws di protocollazione.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PADOC', 'USERNAME', 'Parametro login da indicare al web service di lettura protocollo.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PADOC', 'PASSWORD', 'Parametro password da indicare al web service di lettura protocollo.');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE) VALUES ('NLA-ATTI', 'Se attivo consente di gestire il collegamento con il nodo nla atti.', '0');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('NLA-ATTI', 'URL_NLA_ATTI_WS', 'E'' il link per chiamare il ws esposto da nla atti.');

insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('CART', 'URL_ACCETTATORE', 'Url dell''accettatore verso cui l''area riservata in configurazione STAR deve girare le richieste di nuova domanda e istanze in sospeso');

UPDATE INVENTARIOPROCEDIMENTI SET DISABILITATO=0 WHERE DISABILITATO IS NULL;

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'USERNAME_UTENTE_ANONIMO', 'username da utilizzare per l''accesso anonimo' );
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'PASSWORD_UTENTE_ANONIMO', 'password da utilizzare per l''accesso anonimo' );

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) values ('AREA_RISERVATA', 'CIVICI_NUMERICI', 'Se valorizzato a 1 verifica che i civici immessi in una domanda online siano numerici');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) values ('AREA_RISERVATA', 'ESPONENTI_NUMERICI', 'Se valorizzato a 1 verifica che gli esponenti immessi in una domanda online siano numerici');

INSERT INTO SOFTWARE(CODICE, DESCRIZIONE, MODULOOPZIONALE, DESCRIZIONELUNGA, ACCESSORAPIDO, ORDINE) VALUES ('SN', 'Sanzioni', '1', 'Sanzioni', '0', '64');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'CREA_COPIA_FILE', 'In fase di protocollazione, indica al sistema se deve controllare che due file abbiano lo stesso nome, in quel caso modifica il nome del secondo file inserendo il valore ''[indice]'' (per indice si intende il valore 1, 2, 3, 4.... in base alle copie), ad esempio prova.txt diventerebbe prova [1].txt. In questo modo si evitano dei problemi nel caso in cui qualche sistema di protocollazione non accetti nomi uguali (ad esempio GEPROT), tuttavia il controllo potrebbe leggermente rallentare il procedimento di protocollazione. VALORE = 1, fai la verifica e in caso di omonimia crea una copia rinominando il secondo file, ALTRI VALORI = non fa la verifica. ATTENZIONE: questo parametro potrebbe entrare in conflitto con il parametro NOMEFILE_MAXLENGTH in quanto, quando si crea una copia inevitabilmente il numero dei caratteri del nome file aumenta, anche se di poco, quindi potenzialmente si potrebbe superare la lunghezza indicata in quel parametro, in caso di errore tenere presente questa indicazione.');


INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('SIT_LDP', 'Configurazione dell''integrazione con il SIT LDP',0);
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('SIT_LDP','URL_SERVIZIO_CIVICI','Url del web service per la gestione dei civici (es. https://ws.ldpgis.it/siena/civici.php)');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('SIT_LDP','URL_SERVIZIO_CATASTO','Url del web service per la gestione dei riferimenti catastali (es. https://ws.ldpgis.it/siena/catasto.php)');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('SIT_LDP','URL_SERVIZIO_DOMANDE','Url del web service per la gestione dei dati della domanda online e per la notifica dell''id domanda(es. https://ws.ldpgis.it/siena/presentazione_pratiche_edilizie.php)');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('SIT_LDP','USERNAME','Username da utilizzare per l''autenticazione BASIC del web service');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('SIT_LDP','PASSWORD','Password da utilizzare per l''autenticazione BASIC del web service');

UPDATE verticalizzazioniparametribase SET DESCRIZIONE='Url dell''accettatore verso cui l''area riservata in configurazione STAR deve girare le richieste di nuova domanda e istanze in sospeso. Es. https://servizi2.suap.toscana.it/suap-accettatore/054015/' WHERE modulo='CART' AND PARAMETRO='URL_ACCETTATORE';


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('GEST_CANCELLAZIONI', 'PWD_MOVIMENTO', 'Se attiva la verticalizzazione, contiene la password per poter cancellare un movimento e i relativi dati collegati.');


INSERT INTO  VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_INSIEL', 'ATTIVA_MONF', 'Viene attivato se assume il valore 1, in tutti gli altri casi non viene attivato. Questo parametro indica se il componente deve seguire le regole imposte dal Comune di Monfalcone in ambito di protocollazione. Le regole riguardano la compilazione della denominazione anagrafica da inviare al protocollo insiel, e sono: <NOME> <COGNOME> <LOCALITA_RESIDENZA> (<LOCALITA_SEDE_RESIDENZA> se azienda), se la località coincide con una provincia allora va indicata la sigla, quindi <NOME> <COGNOME> <SIGLAPROVINCIA>, se la località coincide con MONFALCONE allora va indicato il valore CITTA'', quindi <NOME> <COGNOME> CITTA''.');

INSERT INTO  VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_LDP', 'NODI_SERVIZIO_DOMANDE', 'Permette di impostare i nodi da cui provengono le pratiche per cui deve essere gestita la notifica istanza');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PADOC', 'METODO_PROTO', 'Indicare quale metodo del web service deve invocare il componente di protocollazione. E'' il valore che viene assegnato al parametro verb presente sull''url del ws. Può prendere solo si seguenti valori (case sensitive): register --> indica la sola registrazione del protocollo; register_insert --> indica la registrazione e successivamente la pratica viene inoltrata nelle Pratiche Trattate del protocollo. Di default (quindi non assegnata) assumerà il valore register.');

INSERT INTO  VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_LDP', 'URL_RITORNO_PRATICA_GIS', 'Url per il servizio ritorno alla pratica GIS definitiva');

insert into VERTICALIZZAZIONIBASE(MODULO, DESCRIZIONE) values ('SIT_PISTOIA', 'Integrazione con il nuovo SIT del comune di Pistoia');
insert into VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) values ('SIT_PISTOIA', 'URL_CARTOGRAFIA_DA_CIVICO', 'Url per visualizzare la cartografia a partire da un civico (es. http://pistoia.ldpgis.it/ctc/pub/index.php?codvia=$CODICEVIA$&numero=$CIVICO$6&esponente=$ESPONENTE$)');
insert into VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) values ('SIT_PISTOIA', 'URL_CARTOGRAFIA_DA_MAPPALE', 'Url per visualizzare la cartografia a partire da foglio, particella e sub (es. http://pistoia.ldpgis.it/catasto/pub/index.php?sezione=$SEZIONE$&foglio=$FOGLIO$&particella=$PARTICELLA$)');


UPDATE TIPIENDO SET FLAG_PUBBLICA=1 where FLAG_PUBBLICA is null;
UPDATE INVENTARIOPROCEDIMENTI SET FLAG_PUBBLICA=1 where FLAG_PUBBLICA is null;
UPDATE TIPIFAMIGLIEENDO SET FLAG_PUBBLICA=1 where FLAG_PUBBLICA is null;


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_SIGEDO', 'USA_SMISTAMENTOACTION', 'Questo parametro indica se deve essere utilizzata la funzionalità di presa in carico ed eseguito messa a disposizione dal web service di protocollo con il metodo SmistamentoAction, se valorizzata a 1 la funzionalità verrà eseguita, con qualsiasi altro valore no.
Da tenere presente che la suddetta funzionalità verrà comunque eseguita solamente per i protocolli in arrivo e dove sia presente direttamente o indirettamente un''istanza (quindi la protocollazione delle pec non la invocherà).
');


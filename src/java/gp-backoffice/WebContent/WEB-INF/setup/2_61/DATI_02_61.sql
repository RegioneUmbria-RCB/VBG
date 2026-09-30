
UPDATE COMUNI SET SIGLAPROVINCIA = 'PI', PROVINCIA = 'PISA' WHERE CODICECOMUNE='M327';
INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE) VALUES ('WSANAGRAFE_FORLIV', 'Parametri di configurazione dell''oggetto responsabile per le ricerche anagrafiche dell''Unione della Romagna Forlivese', '0');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_FORLIV', 'CONNECTIONSTRING', 'Stringa di connessione al database');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_FORLIV', 'VIEW', 'Vista da interrogare');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_FORLIV', 'PROVIDER', 'Nome del provider da utilizzare nella personalLib per creare l''oggetto database');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_FORLIV', 'OWNER', 'Owner della vista da interrogare');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_IRIDE', 'DISABILITA_CARICO_P', 'Se valorizzato a 1 consente di non impostare il carico per le protocollazioni in partenza, come accade di default, quindi, se non valorizzato o valorizzato diverso da 1 la procedura sarà la medesima di default, quindi verrà impostato il carico con il dato del mittente.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_JIRIDE', 'DISABILITA_CARICO_P', 'Se valorizzato a 1 consente di non impostare il carico per le protocollazioni in partenza, come accade di default, quindi, se non valorizzato o valorizzato diverso da 1 la procedura sarà la medesima di default, quindi verrà impostato il carico con il dato del mittente.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_7DBTL', 'IGNORA_DATI_TOPONOMASTICA', 'Se valorizzato a 1 ignorerà i dati relativi alla toponomastica quando saranno richiesti i dati catastali.');
INSERT
INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'STC',
    'NLA_IDNODO_AR_CONSOLE',
    'Identificativo - registrato su STC - del nodo dell''area riservata che pesca i dati dalla banca dati console'
  ) ;
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_INSIEL', 'TIPO_AGGIORNAMENTO_ANAG', 'Questo parametro determina come deve essere aggiornata un''anagrafica quando si richiede un protocollo. Da notare che questo parametro viene utilizzato solamente per la versione 3 del PROTOCOLLO_INSIEL (valore PROTOCOLLO_INSIEL3 su parametro TIPOPROTOCOLLO della regola PROTOCOLLO_ATTIVO) e solo con il parametro TIPO_GESTIONE_PEC impostato a RICERCA_CODICE_FISCALE e solo se la ricerca dell''anagrafica per codice fiscale o partita iva viene soddisfatta, altrimenti inserisce una nuova anagrafica.
I valori che può assumere questo parametro sono: 
L''anagrafica trovata non viene aggiornata
0 (o non gestito) --> L''anagrafica trovata non viene mai aggiornata.
1 --> L''anagrafica trovata viene aggiornata sempre con il nuovo indirizzo pec che si va ad aggiungere a quelli già inseriti, con la differenza che il nuovo indirizzo diventa il principale; 
2 --> L''anagrafica trovata viene aggiornata solamente se sprovvista di indirizzo pec.');
UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = 'Questo parametro determina come deve essere aggiornata un''anagrafica quando si richiede un protocollo. Da notare che questo parametro viene utilizzato solamente per la versione 3 del PROTOCOLLO_INSIEL (valore PROTOCOLLO_INSIEL3 su parametro TIPOPROTOCOLLO della regola PROTOCOLLO_ATTIVO) e solo se la ricerca dell''anagrafica per codice fiscale o partita iva viene soddisfatta, altrimenti inserisce una nuova anagrafica.
I valori che può assumere questo parametro sono: 
L''anagrafica trovata non viene aggiornata
AGGIORNA_SEMPRE (o non gestito) (DEFAULT) --> L''anagrafica trovata viene aggiornata sempre con il nuovo indirizzo pec che si va ad aggiungere a quelli già inseriti, con la differenza che il nuovo indirizzo diventa il principale; 
AGGIORNA_SE_PEC_VUOTA --> L''anagrafica trovata non viene mai aggiornata.
L''anagrafica trovata viene aggiornata sempre con il nuovo indirizzo pec che si va ad aggiungere a quelli già inseriti, con la differenza che il nuovo indirizzo diventa il principale; 
NO_AGGIORNAMENTO --> L''anagrafica trovata viene aggiornata solamente se sprovvista di indirizzo pec.' WHERE MODULO = 'PROTOCOLLO_INSIEL' AND PARAMETRO = 'TIPO_AGGIORNAMENTO_ANAG';


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_INSIEL', 'INVIA_PEC', 'Valore 1 o 0, indica se deve essere richiesto anche l''invio di una pec per i protocolli in partenza. Valido solo per la versione 3 del PROTOCOLLO_INSIEL.');

update verticalizzazioniparametribase set DESCRIZIONE = 'Nome fuorviante, perchè indica come gestire le anagrafiche quando viene richiesto un protocollo, in realtà, serve più che altro per l''invio pec, in quanto non è presente un elemento dove poter indicare l''indirizzo, ma viene recuperato direttamente dall''anagrafe del protocollo, motivo per il quale è comunque il backoffice a gestire questo tipo di dato. 
Può essere utilizzato solo per la versione 3 del protocollo insiel, che è quella dove è possibile inviare una pec per un protocollo in partenza, e serve per specificare il format delle anagrafiche da inviare al web service. Può assumere i seguenti valori:
PEC: Aggiunge alla descrizione la pec, va a fare ricerche per descrizione, se non presenta ne inserisce una nuova.
CODICE_FISCALE: Aggiunge il codice fiscale alla descrizione, va a cercare per descrizione e se non presente ne inserisce una nuova, se l''anagrafica viene trovata ma la pec manca o non è presente quella passata, aggiorna l''anagrafica aggiungendo il nuovo indirizzo pec.
NOMINATIVO: Non aggiunge niente al nominativo, ricerca per descrizione e se non presente ne inserisce una nuova, se l''anagrafica viene trovata ma la pec manca o non è presente quella passata, aggiorna l''anagrafica aggiungendo il nuovo indirizzo pec.
MONFALCONE: scrive la denominazione con le seguenti regole definite dal comune di monfalcone <COGNOME> <NOME> <LOCALITA_RESIDENZA> (<LOCALITA_SEDE_RESIDENZA> se azienda), se la località coincide con una provincia allora va indicata la sigla, quindi <COGNOME> <NOME> <SIGLAPROVINCIA>, se la località coincide con MONFALCONE allora va indicato il valore CITTA'', quindi <COGNOME> <NOME> CITTA
RICERCA_CODICE_FISCALE: Ricerca per partita iva o codice fiscale, se trova l''anagrafica saranno aggiornati solo i parametri relativi alla PEC e comunque in base al parametro TIPO_AGGIORNAMENTO_ANAG
Di default, quindi se il parametro non verrà valorizzato, verrà impostato RICERCA_CODICE_FISCALE' where modulo = 'PROTOCOLLO_INSIEL' and parametro = 'TIPO_GESTIONE_PEC';

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('CONSOLE', 'ATTIVA LE FUNZIONALITA'' DI ALLINEAMENTO TRAMITE CONSOLE');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('CONSOLE','URL_APP_ALLINEAMENTO','INDIRIZZO DI BASE DELL''APPLICATIVO CHE ALLINEA I DATI');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('CONSOLE','ALIAS_DATI_CONSOLE','ALIAS DELLA CONSOLE REGIONALE CHE SERVE PER L''ALLINEAMENTO DEI DATI');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('CONSOLE','SOFTWARE_DATI_CONSOLE','SOFTWARE PRINCIPALE DELLA CONSOLE REGIONALE CHE SERVE PER L''ALLINEAMENTO DEI DATI');

INSERT INTO clmenu_java
    (id,descrizione,pagina,menulink,software,jsp,verticalizzazione,softwareesclusi,link_standard,tipo_funzionalita,layouttesti,menulink_v2) 
    VALUES ('1039','Pannello controllo CONSOLE','pannelloconsole/view.htm?software=SOFTWARE','0AB','SS',
    'JAVA','CONSOLE','AB','pannelloconsole/view.htm?software=SOFTWARE','S','0','0AB');

 insert into verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) values ('AREA_RISERVATA', 'URL_VISURA_ISTANZA_CONSOLE', 'Url da utilizzare per effettuare la visura al termine della presentazione istanza tramite la console. Funziona a patto che: 1-La security utilizzata sia la stessa della console, 2-La console .net riesce a staccare un token applicativo con la stessa username e password sul comune in cui si fa la visura, 3-L''alias utilizzato dal comune su cui si fa la visura sia lo stesso utilizzato come sportello destinatario nei parametri di stc');
 
 INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('EDIT_DOCS_APPLICATION', 'UTILITA PER L''APPLICAZIONE EDIT DOCS JWS');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('EDIT_DOCS_APPLICATION','URL_CODEBASE_FO','INDIRIZZO DEL CODE BASE USATO DA APPLICAZIONI DI FRONTEND');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('EDIT_DOCS_APPLICATION','URL_CODEBASE_BO','INDIRIZZO DEL CODE BASE USATO DA APPLICAZIONI DI BACKEND');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_APSYSTEMS', 'FORMATO_DATA', 'Indicare il formato della data da parsare nella lettura di un protocollo, se non indicato la stringa di default sarà "dd/MM/yyyy h.mm.ss"');
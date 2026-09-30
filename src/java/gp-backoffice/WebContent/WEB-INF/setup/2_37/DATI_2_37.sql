INSERT INTO  VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_DOCER', 'IGNORA_WARN_ANAG', 'Questo parametro sta ad indicare se si intende ignorare il warning della ricerca / inserimento / aggiornamento delle anagrafiche custom (valore uguale a 1) o meno (valore diverso da 1 o parametro non presente). Questo parametro si rende necessario in quanto la comunicazione con le anagrafiche custom è di bassissima importanza e spesso è causa di errori da parte di solr / docer, il sistema, in casi di errori, restituisce semplicemente un warning che viene poi visualizzato dall''utente generando preoccupazione anche se in realtà è di scarsissima importanza. Il warning anche se non visualizzato (valore uguale a 1) sarà comunque inserito nei log.');
INSERT
INTO VERTICALIZZAZIONIPARAMETRIBASE
  (
    MODULO,
    PARAMETRO,
    DESCRIZIONE
  )
  VALUES
  (
    'ALLEGATI_PEC',
    'DOWNLOAD_SENZA_PIN',
    'Se valorizzato i link generati nei documenti non riportano il PIN ed il documento sarà scaricabile direttamente dal link senza l''autenticazione. Accetta i valori S o N.'
  );
 
INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('PROTOCOLLO_ARCHIFLOW', 'E'' il protocollo in dotazione alla Regione Abruzzo, sviluppato per conto di Arit, ii web service sono stati sviluppati da EuroInformatica.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ARCHIFLOW', 'USERNAME', 'Parametro facente parte delle credeziali con cui accedere alle funzionalità dei web service relativi al Protocollo Informatico ARCHIFLOW, indicare in questo caso, lo username.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ARCHIFLOW', 'PASSWORD', 'Parametro facente parte delle credeziali con cui accedere alle funzionalità dei web service relativi al Protocollo Informatico ARCHIFLOW, indicare in questo caso, la password relativa all''utente (username) indicato nel parametro USERNAME.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ARCHIFLOW', 'CODICEENTE', 'Parametro facente parte delle credeziali con cui accedere alle funzionalità dei web service relativi al Protocollo Informatico ARCHIFLOW, indicare in questo caso il codice ente relativo all''utente (username) indicato nel parametro USERNAME.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ARCHIFLOW', 'URL', 'Parametro in cui va indicata la url relativa all''endpoint del web service di protocollazione.');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'E'' il protocollo sviluppato da PADIGITALE, il protocollo si chiama, appunto URBI, i servizi, che si chiamano APIREST, sviluppati anch''essi da PADIGITALE, sono di tipo REST, dove i parametri relativi a metodi e filtri vanno definiti in querystring, saranno comunque gestiti direttamente da codice. Attivarlo dove presente.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'URL', 'Indicare in questo parametro la url base dove è installato il web service, ad esempio, per l''ambiente di test indicare la url http://apirest.urbi.it/urbi/progs/main/xapirest.sto, i metodi saranno poi definiti da codice nel parametro querystring WTDK_REQ.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'USERNAME', 'I servizi necessito di autenticazione basic ad ogni chiamata ai metodi, motivo per il quale è necessario avere delle credenziali per accedere al web service stesso, indicare in questo parametro la username.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'PASSWORD', 'I servizi necessito di autenticazione basic ad ogni chiamata ai metodi, motivo per il quale è necessario avere delle credenziali per accedere al web service stesso, indicare in questo parametro la password relativa all''utente indicato nel parametro USERNAME.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE VALUES ('STC','LISTA_NODI_MITT_NO_ONERI_ENDO','Indicare l''elenco dei nodi mittenti per i quali non si vogliono inserire automaticamente nell''istanza gli oneri associati ad un endo . Es. 400,420,430');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_DOCER', 'COMPATIBILITA_FASC', 'Questo parametro si rende necessario in quanto la logica standard relativa alle impostazioni delle ACL successivamente alla creazione di un fascicolo impone che venga fatta la chiamata updateACLFascicolo del ws di Fascicolazione, mentre inizialmente l''impostazione delle ACL veniva fatto con il metodo setACLFascicolo del ws di gestioneDocumentale. Visto e considerato che molti fornitori di protocollo funzionano correttamente con la vecchia versione, quindi quella che invoca il metodo setACLFascicolo, si è deciso di distinguere l''utilizzo di uno piuttosto che di un altro metodo tramite questo parametro che, se impostato a 1 utilizzerà il nuovo sistema, quindi quello standard che usa il metodo updateACLFascicolo del ws di fascicolazione, altrimenti, con qualsiasi altro valore, sarà utilizzata la vecchia logica funzionante con i protocolli di Maggioli e ADS, ossia utilizzando il metodo setACLFascicolo del ws di gestione documentale.');

INSERT INTO COMUNI(CODICECOMUNE,COMUNE,SIGLAPROVINCIA,PROVINCIA,REGIONE,CAP,CF,CODICEISTAT,CODICEISTATREGIONE,CODICESTATOESTERO)VALUES('M381','TERRE DEL RENO','FE','FERRARA','EMILIA ROMAGNA','44047','M381','038028','08',NULL);

INSERT
INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'SIT_LDP',
    'URL_SERVIZIO_DOMANDE_NSPACE',
    'NAMESPACE DEL SERVIZIO LDP es: https://ws.ldpgis.it/'
  );
  INSERT
INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'SIT_LDP',
    'URL_SERVIZIO_DOMANDE_SNAME',
    'NOME DEL SERVIZIO DEL WS LDP es: PresentazionePraticheEdilizie'
  );
  
 INSERT INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'SIT_LDP',
    'URL_SERVIZIO_DOMANDE_PNAME',
    'NOME DELLA PORTA DEL WS LDP es: PresentazionePraticheEdilizieSoap'
  );

INSERT INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'SIT_LDP',
    'URL_SERVIZIO_DOMANDE_MNAME',
    'NOME DEL METODO DA INVOCARE NEL BODY ES: ComplexTypePraticaIdentificativi oppure pratica_identificativi'
  );
  
INSERT
INTO VERTICALIZZAZIONIPARAMETRIBASE VALUES
  (
    'STC',
    'ATTIVA_LETTURA_SCHEDE_DA_PDF',
    'Quando arriva una pratica da STC ogni PDF viene processato per verificare se è compilato e se ci sono i dat da verificare. Se il valore impostato a S allora viene attivata questa logica. Se N no. di default viene impostato a N.'
  );

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_IRIDE', 'USA_METODI_STRING', 'Se valorizzato a 1 permette di utilizzare i metodi del web service con suffisso string che stanno ad indicare che, gli stessi metodi, vogliono una request di tipo string e restituiscono una risposta sempre di tipo string. E'' importante soprattutto per poter utilizzare la funzionalità di utilizzo del mezzo generico, ossia l''invio di un mezzo di invio valido per tutto il protocollo e non solo per l''anagrafica inviata.');
  
Insert into VERTICALIZZAZIONIBASE (MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) values ('PROTOCOLLO_MICROSIS','Consente l''integrazione con il protocollo del fornitore Microsis.',null);
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_MICROSIS','PASSWORD','Indicare in questo parametro la password relativa all''utente specificato nel parametro USERNAME facente parte delle credenziali di accesso per creare un protocollo, più in generale per poter utilizzare i metodi esposti dal web service MICROSIS.');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_MICROSIS','USERNAME','Indicare in questo parametro lo username facente parte delle credenziali di accesso per creare un protocollo, più in generale per poter utilizzare i metodi esposti dal web service MICROSIS.');
Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('PROTOCOLLO_MICROSIS','URL','Indicare in questo parametro la url relativa all''endpoit del web service di protocollazione MICROSIS.');

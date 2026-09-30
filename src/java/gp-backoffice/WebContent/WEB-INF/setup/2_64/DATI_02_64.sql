INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) 
VALUES ('COMPORTAMENTI_ISTANZE', 'BLOCCA_DEL_MOVALL_OPE_MOV',
'Se Attivato allora solo il responsabile che ha eseguito il movimento puo'' cancellare gli allegati di quel movimento.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'URL_SERVLET_RESETCREDENZIALI', 'INDICARE L''URL DELLA SERVLET DI RESET CREDENZIALI ES http://devel3.init.gruppoinit.it/ibcauthenticationgateway/resetcredenziali');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE)  VALUES ('AREA_RISERVATA', 'REG_INVMAIL_MODELLO_RESET', 'INDICARE IL CODICE DELLA MAIL TIPO PER IL RESET DELLE CREDENZIALI');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE)  VALUES ('COMPORTAMENTI_ISTANZE','VIS_CAMB_STATO_IST_COLLEGATE','Se attivo visualizza nella sezione istanze collegate la possibilità di cambiare lo stato delle istanze collegate scegliendo il nuovo stato e quali istanze per le quali impostarlo. Valori ammessi 1 o 0 (predefinito 0) ');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'DISABILITA_INVIOPEC', 'Se impostato a 1 disabilita la funzionalità di invio PEC per le protocollazioni in partenza');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'DISABILITA_ESEGUITO', 'Se impostato a 1 disabilita la funzionalità di carico ed eseguito che viene utilizzata per le protocollazioni dei movimenti');
INSERT INTO COMUNI(CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CF, CODICEISTAT, CODICEISTATREGIONE) VALUES ('M363', 'VILLE D''ANAUNIA', 'TN', 'TRENTO', 'PROV. AUT. TRENTO', 'M363', '022249', '04');              
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_LDP', 'ABILITA_PASSICARRAI', 'Se impostato a 1 abilita la gestione e recupero dei passi carrai dal sistema SIT');

INSERT
INTO VERTICALIZZAZIONIPARAMETRIBASE
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'I_ATTIVITA',
    'INVERTI_RICHIEDENTE_STORICO',
    'Nella pagina di visualizzazione delle attivita'' viene attualmente visualizzato il richiedente storico e sul campo help il richiedente attuale. Se viene impostato il valore 1 allora il comportamento viene invenrtito.'
  );

Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('FIRMA_REMOTA_ARUBA','IS_GENERA_OTP','Può assumere in valori 0(NULL) o 1. 0(NULL): non attiva la funzionalità per generare OTP, 1: attiva la funzionalità per generare OTP. La generazione dell'' OTP virtuale deve essere attivato anche a livello di servizio sul provider');
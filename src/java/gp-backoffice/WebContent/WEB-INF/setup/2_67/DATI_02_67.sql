INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('WSANAGRAFE_PARMA', 'Parametri di configurazione dell''oggetto responsabile per le ricerche anagrafiche del comune di PARMA');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_PARMA', 'URLWS_BASE', 'Impostare la url facente riferimento all''endpoint del web service rest relativo ai dati dell''anagrafe di PARMA, deve essere impostata la url di base, dove è comune a tutti i metodi, ad esempio https://webservices2.comune.parma.it/apianagrafe/api/, per le richieste anagrafiche dovrà essere aggiunto, da codice: anagrafe/[codice_fiscale], per le variazioni: variazioni?from=[data_da_formato_yyyyMMdd]&to=[data_a_formato_yyyyMMdd]');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_PARMA', 'USERNAME', 'Username da inserire nell''header della richiesta come autenticazione basic al web service.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_PARMA', 'PASSWORD', 'Password da inserire nell''header della richiesta come autenticazione basic al web service.');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('PROTOCOLLO_ITCITY', 'Sistema di protocollazione del Comune di Parma sviluppato da ItCity, fa da tramite con il protocollo e-grammata.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ITCITY', 'URL', 'Endpoint del web service di It City');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ITCITY', 'USERNAME', 'Username relativo alle credenziali per autenticarsi al web service, da non confondere con gli utenti del protocollo');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ITCITY', 'PASSWORD', 'Indica il registro di protocollazione, se non valorizzato il web service lo imposterà, di default, a PG (Protocollo Generale), questo su tutte le operazioni che richiedono questo parametro.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ITCITY', 'SIGLA', 'Indica il registro di protocollazione, se non valorizzato il web service lo imposterà, di default, a PG (Protocollo Generale), questo su tutte le operazioni che richiedono questo parametro.');

update comuni set cap = '52041' where codicecomune='C774';

Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('FIRMA_REMOTA_ARUBA','PREPOPOLA_USER_NAME_CON_CF','Prepopola la user name con il CF dell''utente loggato. 1: attivo, 0(NULL): non attivo');
INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'PARAMETRI_SISTEMA',
    'ATTIVA_AUDIT_WEB',
   'Accetta valori S o N (default N). Se S attiva il log su file delle attivita'' web'
);


INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'PARAMETRI_SISTEMA',
    'ATTIVA_COMPORTAMENTI_SICUREZZA',
    'Accetta valori S o N (default N). Se S le funzionalita'' di controllo sicurezza sui parametri della request vengono attivate'
);

UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = 'Gestisce la possibilità di inserire più destinatari in fase di protocollazione per i flussi Arrivo e/o Interno (la partenza non è gestita in nessun modo da questo parametro). Può assumere i seguenti valori: 1 --> permette più di un destinatario per le protocollazioni in arrivo e interne; 2 --> permette più di un destinatario solo per le protocollazioni in ARRIVO; 3 --> permette più di un destinatario solo per le protocollazioni INTERNE; qualsiasi altro valore (compreso null) --> funzionalità STANDARD, non permette quindi l''inserimento di più destinatari nè per le protocollazioni in ARRIVO e nè per quelle INTERNE.' WHERE MODULO = 'PROTOCOLLO_ATTIVO' and PARAMETRO = 'IS_SMISTAMENTO_MULTIPLO';

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE) VALUES ('SIT_ITCITY', 'Configurazione dell''integrazione con il SIT_ITCITY', 0);
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_ITCITY','URL_SERVIZIO_CIVICI','Url del web service per la gestione dei civici (es. https://api.comune.parma.it/Stradario/api/civici)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_ITCITY','USERNAME','Username riferito all''autenticazione al web service');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_ITCITY','PASSWORD','Password da utilizzare per l''autenticazione BASIC del web service');
UPDATE COMUNI SET PROVINCIA='FERMO' , SIGLAPROVINCIA='FM', CODICEISTAT='109017' WHERE CODICECOMUNE='F520';

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('CART', 'SYNC_DIZIONARIO_USA_STP_CODICE', 'Valorizzato automaticamente dalla procedura di importazione del dizionario. Se vale N allora saranno usati i codici regionali di STP_ENDO_TIPO1 e ST_ENDO_TIPO2 per identiifcare i record da sincronizzare durante l''importazione, se vale S saranno usati i valori di CODICE_STP delle stesse tabelle che devono coincidere con gli id degli elementi XML restituiti dal servizio del dizionario. Se non è valorizzato il parametro o se ha valore diverso da S o N allora sarà lanciata una procedura che verifica se esistono le condizioni per basare l''import su CODICE_STP e valorizza di conseguenza il parametro della verticalizzazione.');


INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE) VALUES ('AUTORIZ_ACCESSI', 'Gestione delle autorizzazioni legate agli accessi dei varchi (es: autorita'' portuali)', 0);

INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'FVG_SUAP_IN_RETE',
    'PREFIX_MAPPING_PEOPLE',
   'Prefisso da utilizzare per il mapping dei procedimenti con la tabella INVENTARIOPROCEDIMENTIPEOPLE'
);


INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'FVG_SUAP_IN_RETE',
    'TEMPISTICHE',
   'Codice di una tempistica registrata sul backoffice'
);


INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'FVG_SUAP_IN_RETE',
    'COD_AMMINISTRAZIONE',
   'Codice di una amministrazione registrata sul backoffice. Verrà impostata all''inserimento dell''endoprocedimento'
);

INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'FVG_SUAP_IN_RETE',
    'DESC_TIPO_CLASSIFICAZIONE',
    'Indica il testo della descrizione della classificazione che ci permette di agganciare la porzione di xml da cui possiamo recuperare per un procedimento le sotto classificazione.'
);

INSERT INTO verticalizzazioniparametribase (modulo,parametro,descrizione) VALUES ('COMPORTAMENTI_MERCATI','AUTORIZ_UNIQUE_NUMERO_COMUNE','Se specificata viene inserito un constraint in inserimento autorizzazioni su numero e comune e non su numero,comune,data,registro. Può assumere i valori S o N (NULL default N). Se S viene impostato il nuovo vincolo');

insert into verticalizzazioniparametribase (modulo, parametro, descrizione) values('FVG_SOL','D2_CHK_DEF_ETICHETTA_DESTRA', 'Se impostato a 1 durante la creazione di un campo dinamico di tipo checkbox il flag etichetta a destra viene impostato a ''Si'' di default');

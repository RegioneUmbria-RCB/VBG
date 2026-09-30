UPDATE COMUNI SET PROVINCIA = 'REGGIO CALABRIA' WHERE CODICECOMUNE = 'B775';
INSERT INTO verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AREA_RISERVATA',
'ID_SCHEDA_ESTREMI_DOCUMENTO',
'Id della scheda dinamica (dyn2_modellit.id) contenente gli estremi del firmatario di cui verificare la compilazione prima di poter apporre una firma grafometrica oppure una firma con CID/PIN'
);

insert into verticalizzazionibase(MODULO, DESCRIZIONE,FLAG_GESTCOMUNE) values('OBS_EXPORT','Se attivata permette di esportare secondo la modalità classica della chiamata al Web Service .Net',0);

INSERT INTO verticalizzazioniparametribase (modulo,parametro,descrizione) VALUES ('TARES_BARI', 'URL_SERVIZIO_FIRMA_CID', 'Url del servizio di firma digitale tramite CID-PIN. Dovrebbe essere simile a http://devel9:8080/jsignpdf-web/services/signerWS?wsdl');
INSERT INTO TIPICONTESTOESPORTAZIONE (CODICE, DESCRIZIONE) VALUES ('ATS','Esportazione di snapshot');
INSERT INTO TIPICONTESTOESPORTAZIONE (CODICE, DESCRIZIONE) VALUES ('CON','Esportazione di concessioni');
INSERT INTO TIPICONTESTOESPORTAZIONE (CODICE, DESCRIZIONE) VALUES ('IST','Esportazione di istanze');
INSERT INTO TIPICONTESTOESPORTAZIONE (CODICE, DESCRIZIONE) VALUES ('ATT','Esportazione di attività');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_INSIEL', 'TIPO_GESTIONE_PEC', 'Questo parametro può essere utilizzato solo per la versione 3 del protocollo insiel, che è quella dove è possibile inviare una pec per un protocollo in partenza, e serve per specificare il format delle anagrafiche da inviare al web service. Può assumere i seguenti valori: PEC - Aggiunge alla descrizione la pec, va a fare ricerche per descrizione, se non presente ne inserisce una nuova, se presente ma la pec manca o non è presente quella passata, aggiorna l''anagrafica aggiungendo il nuovo indirizzo pec; CODICE_FISCALE: Aggiunge il codice fiscale alla descrizione, va a cercare per descrizione e se non presente ne inserisce una nuova, se presente ma la pec manca o non è presente quella passata, aggiorna l''anagrafica aggiungendo il nuovo indirizzo pec; NORMALE: Non aggiunge niente al nominativo, ricerca per descrizione e se non presente ne inserisce una nuova, se presente ma la pec manca o non è presente quella passata, aggiorna l''anagrafica aggiungendo il nuovo indirizzo pec. Se questo parametro non viene valorizzato o viene valorizzato con un valore diverso da PEC, CODICE_FISCALE, NORMALE, le funzionalità di gestione anagrafiche del web service, e di invio pec, saranno ignorate.');
INSERT INTO CLMENU_JAVA (ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, LAYOUTTESTI, SOFTWAREESCLUSI,LINK_STANDARD,TIPO_FUNZIONALITA) VALUES ('1023', 'Mail / testi tipo', 'mailtipo/list.htm?software=TT', '00D', 'TT', 'JAVA', '0', 'PR,FI,AB','mailtipo/list.htm?software=TT','S');
INSERT INTO CLMENU_JAVA (ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, LAYOUTTESTI, SOFTWAREESCLUSI,LINK_STANDARD,TIPO_FUNZIONALITA) VALUES ('1024', 'Configurazione Pentaho', 'pentahocfg/createOrView.htm?software=TT', '00E', 'TT', 'JAVA', '0', 'PR,FI,AB','pentahocfg/createOrView.htm?software=TT','E');

INSERT INTO software (codice, descrizione, moduloopzionale,descrizionelunga,accessorapido,ordine)
VALUES ('UM', 'Servizi Regionali', 1,'Digitalizzazione servizi Regione Umbria',0,64);
UPDATE verticalizzazioniparametribase SET descrizione = 'Indicare in questo parametro il codice del registro del protocollo insiel (in genere viene utilizzato il valore GEN che starebbe per PROTOCOLLO GENERALE), NB. questo parametro va di pari passo con il parametro Codice ufficio, che viene recuperato tramite la tabella PROTOCOLLO_UFFICIREGISTRI passando il valore impostato in questo parametro. L''associazione quindi è: UN REGISTRO --> UN UFFICIO (UN UFFICIO --> N REGISTRI)'
WHERE modulo    = 'PROTOCOLLO_INSIEL'
AND parametro   = 'CODICEREGISTRO';

UPDATE VERTICALIZZAZIONIPARAMETRIBASE 
SET descrizione='(OBSOLETO)Nome del dominio al quale accedere, se non specificato prende quello del server su cui è installata la libreria che autentica.' 
WHERE modulo='AUTENTICAZIONE_LDAP' and parametro='DOMAINNAME';

UPDATE VERTICALIZZAZIONIPARAMETRIBASE 
SET descrizione='(OBSOLETO)E''il nome del ruolo a cui appartengono gli utenti LDAP che hanno accesso a SIGEPRO. Può essere non specificato. Es. Users o SIGePro. ATTENZIONE è case sensitive.' 
WHERE modulo='AUTENTICAZIONE_LDAP' and parametro='GROUPNAME';


UPDATE VERTICALIZZAZIONIPARAMETRIBASE 
SET descrizione='(OBSOLETO)E'' il nome della macchina che fa da Domain Server nel quale va ricercato il dominio DOMAINNAME. Se non specificato lo recupera dal dominio della macchina server dove è installato il componente che autentica.Il formato è Es: ldap://parsifal/ dove parsifal è il nome del domain server o l''indirizzo ip.' 
WHERE modulo='AUTENTICAZIONE_LDAP' and parametro='LDAPPATH';

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP','LDAP_HOST',
'Hostname del server LDAP'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP_PORT',
'Porta del server LDAP'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP_CN',
'Nome dell''attributo CN da utilizzare per la login'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP_USER_DN',
'Path dove effettuare il bind dell''utente'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP_USER_ATTRS',
'Attributi che si vogliono recuperare dalla chiamata al metodo getUserAttributes(separati da ;)'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP_LOGIN_TYPE',
'Indica il tipo di login; può assumere i valori bind o search. bind: Effettua la login con l''utente specificato nel form; search: effettua la login con l''utente specificato nei parametri LDAP_USER e LDAP_PWD e poi una search dell''utente del form sulla lista utenti tornata ed effettua una seconda login con le credenziali del form'
);


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP_USER',
'Nome utente, da popolare in caso di LDAP_LOGIN_TYPE è impostato a ''search''.'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP_PSW',
'Password, da popolare in caso di LDAP_LOGIN_TYPE è impostato a ''search''.'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP_UID',
'Nome dell''attributo contenente la userid. Se la loginType = search ed il server ldap è activeDirectory allora LDAP_UID=sAMAccountName'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP.MEMBER_OF_ARRAY',
'Usato per specificare un filtro sui gruppi di appartenenza degli utenti. Es. si vogliono autenticare solo gli utinti dello sviluppo'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP.MEMBER_OF',
'Nome dell''attributo memberOf(ActiveDirectory e OpenLDAP si comportano differentemente)'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP_MEMBER_OF_ARRAY',
'Usato per specificare un filtro sui gruppi di appartenenza degli utenti. Es. si vogliono autenticare solo gli utinti dello sviluppo'
);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'AUTENTICAZIONE_LDAP',
'LDAP_MEMBER_OF',
'Nome dell''attributo memberOf(ActiveDirectory e OpenLDAP si comportano differentemente)'
);

UPDATE VERTICALIZZAZIONIBASE 
SET descrizione='Se attiva, l''accesso a SIGePro sarà gestito da LDAP. Accesso tramite utenti di dominio. Ogni utente per accedere deve essere presente nel dominio e in SIGePro, la passowrd di accesso utilizzata sarà quella di dominio.' 
WHERE modulo='AUTENTICAZIONE_LDAP';

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) Values (
'PROTOCOLLO_DOCER','USA_LDAP_AUTH',
'Il parametro indica se deve essere utilizzata l''autenticazione tramire LDAP o no. Può assumere i valori 0 o 1; 0 non usare autenticazione LDAP, 1 utilizza autenticazione LDAP. Nel caso venga lasciato vuoto avrà lo stesso comportamento del valore 0'
);

INSERT INTO  VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'TIPO_MITTENTE', 'Indica con quale dato deve essere valorizzato il mittente, se non valorizzato o se valorizzato a 0 il sistema indicherà come mittente il Richiedente, se valorizzato a 1 verrà proposta l''Azienda, se non presente sarà valorizzato il Richiedente, se valorizzato a 2 il sistema indicherà come mittenti sia il Richiedente che l''Azienda, se non presente l''Azienda indicherà solamente il Richiedente.');

UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET PARAMETRO = 'TIPO_MITTDEST_AUTO', DESCRIZIONE = 'Indica con quale dato deve essere valorizzato il mittente / destinatario per le protocollazioni automatiche, se una protocollazione è in arrivo allora il soggetto interessato sarà il mittente, se la protocollazione automatica sarà in partenza allora il soggetto sarà il destinatario. Può assumere i seguenti valori: non valorizzato o se valorizzato a 0 il sistema indicherà come mittente / destinatario il Richiedente e l''Azienda, se valorizzato a 1 verrà proposto solo il Richiedente,se valorizzato a 2 il sistema indicherà come Mittenti solo l''Azienda, se, in questo caso l''Azienda non è presente indicherà solamente il Richiedente.' WHERE MODULO = 'PROTOCOLLO_ATTIVO' AND PARAMETRO = 'TIPO_MITTENTE';

insert into VERTICALIZZAZIONIBASE(MODULO, DESCRIZIONE) values ('SIT_RAVENNA2', 'Integrazione con il nuovo SIT del comune di Ravenna');
insert into VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) values ('SIT_RAVENNA2', 'CONNECTION_STRING', 'Stringa di connessione al database');
insert into VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) values ('SIT_RAVENNA2', 'PREFISSO_TABELLE', 'Prefisso da anteporre al nome tabella per specificare l''utente a cui appartiene');
insert into VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) values ('SIT_RAVENNA2', 'URL_CARTOGRAFIA_DA_CIVICO', 'Url per visualizzare la cartografia a partire da un civico');
insert into VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) values ('SIT_RAVENNA2', 'URL_CARTOGRAFIA_DA_MAPPALE', 'Url per visualizzare la cartografia a partire da foglio, particella e sub');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_EGRAMMATA2', 'URL_LEGGIANAGRAFICHE', 'Url del web service e-grammata2 che consente di svolgere operazioni con le anagrafiche.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_DOCER', 'PAD_NUMPROTO_LENGTH', 'Questo parametro serve in fase di lettura protocollo nel caso in cui non sia specificato l''ID (ad esempio quando viene indicato manualmente numero e data protocollo su istanze / movimenti), in particolare indica il numero di caratteri (indicati nel parametro PAD_NUMPROTO_CHAR) da cui fare il padding a sinistra, ad esempio se viene indicato il numero 1234 ed il valore di questo parametro è indicato a 7 e quello del parametro PAD_NUMPROTO_CHAR a 0, il numero sarà trasformato, in fase di lettura, a 0001234.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_DOCER', 'PAD_NUMPROTO_CHAR', 'A stretto legame con il parametro PAD_NUMPROTO_LENGTH serve per indicare quale carattere utilizzare durante il padding dei caratteri del numero protocollo, di default è impostato a 0.');

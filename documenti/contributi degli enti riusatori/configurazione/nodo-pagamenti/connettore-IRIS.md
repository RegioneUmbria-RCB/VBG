#Connettore di pagamento IRIS - Manuale di configurazione
##Ambiente di staging-test
###Configurazione ente e servizio
| Parametro | Valore | Configurazione |	Note |
|-----------|--------|----------------|------|
| ID ente | qualsiasi | PAY_PROFILI_ENTI_CREDITORI.CF_CODICE_PROFILO | Il valore configurato in questo campo dovrà essere impostato nelle verticalizzazioni dei client che invocano il nodo |
| Codice ente | CLivorno | PAY_PROFILI_ENTI_CREDITORI.CF_CODICE_PROFILO_PSP | ID dell’ente in IRIS |
| CF ente | PI dell’ente (facoltativo) | AMMINISTRAZIONI.PARTITAIVA | L’amministrazione deve essere associata al profilo ente in PAY_PROFILI_ENTI_CREDITORI.CODICEAMMINISTRAZIONE|
| Intestazione creditore | Denominazione dell’ente (facoltativo) | AMMINISTRAZIONI.AMMINISTRAZIONE | | 	
| ID Applicazione | SIL_CLIVORNO_AMBU | PAY_PROFILI_ENTI_CREDITORI.ID_APP_PSP | ID dell’applicazione che usa i servizi IRIS |
| Application code | 310 | PAY_PROFILI_ENTI_CREDITORI.CODICE_SEGREGAZIONE | codice che codifica il tipo di tributo all’interno dello IUV. | Poiché altri IUV vengono generati da altri software per lo stesso ente ci consente di garantire l’univocità dei nostri IUV rispetto a quelli utilizzati dagli altri |
| ID gestionale | 16 | PAY_PROFILI_ENTI_CREDITORI.APPLICATION_CODE | Codifica dell’applicativo che genera lo IUV previsto dalle specifiche interne di IRIS per la generazione degli IUV |
| Codice tributo | TOSAP_AMBU |	PAY_REGISTRAZIONI_CAUSALI.CODICE_VERSAMENTO | Il record configurato in PAY_REGISTRAZIONI_CAUSALI deve essere associato a PAY_PROFILI_ENTI_CREDITORI nel campo FK_CAUSALE_REG_DEFAULT |
|CF_ENTE_QRCODE_PAGOPA|Il codice fiscale/partitaiva dell'ente che serve per generare la sezione Identificativo Ente/codice fiscale dell’Ente Creditore dell'algoritmo di generazione qrcode|
 
###Configurazione connettore e endpoint dei servizi SOAP
| Parametro | Valore | Configurazione | Note |
|-----------|--------|----------------|------|
| Codice connettore IRIS | PAY_CONNECTOR_CONFIG.CODICE | ID del connettore nel nodo pagamenti, si può impostare qualunque codice. Il codice deve essere valorizzato in FK nel campo PAY_PROFILI_ENTI_CREDITORI.CODICE_CONNETTORE |
| Classe connettore | it.gruppoinit.pal.gp.pay.connector.plugandpay.IRISPayConnector | PAY_CONNECTOR_CONFIG.JAVA_CLASS | Nome complete della classe Java che implementa il connettore (interfaccia IPaymentConnector) |
| URL WS caricamento | https://pdastage.tix.it/stage/pdd/PD/SPCCLivorno/SPCRTIRIS/SPCComunicazionePosizioniDebitorieOTF-v3/IdpAllineamentoPendenzeEnteOTF |	PAY_CONNECTOR_WS_ENDPOINT.ENDPOINT_URL | Un riferimento al record di PAY_CONNECTOR_WS_ENDPOINT deve essere impostato in PAY_CONNECTOR_CONFIG.FK_WS_CARICAMENTO e in PAY_CONNECTOR_CONFIG.FK_WS_ANNULLAMENTO |
| User WS caricamento |	SIL_CLIVORNO_AMBU | PAY_CONNECTOR_WS_ENDPOINT.UTENTE | Utente per la basic autentication del WS di caricamento |
| Password WS caricamento |	******** | PAY_CONNECTOR_WS_ENDPOINT.PASSWORD |	Password per la basic autentication del WS di caricamento |
| URL WS verifica stato pagamento | https://pdastage.tix.it/stage/pdd/PD/SPCCLivorno/SPCRTIRIS/SPCVerificaStatoPagamento-v3/IdpVerificaStatoPagamento | PAY_CONNECTOR_WS_ENDPOINT.ENDPOINT_URL | Un riferimento al record di PAY_CONNECTOR_WS_ENDPOINT deve essere impostato in PAY_CONNECTOR_CONFIG.FK_WS_VERIFICA |
| User WS verifica | SIL_CLIVORNO_AMBU | PAY_CONNECTOR_WS_ENDPOINT.UTENTE | Utente per la basic autentication del WS di verifica stato |
| Password WS verifica | ****** | PAY_CONNECTOR_WS_ENDPOINT.PASSWORD | Password per la basic autentication del WS di verifica stato |

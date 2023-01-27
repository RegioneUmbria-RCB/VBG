![](https://www.nexi.it/content/dam/nexi/img/logo/logo--dark-double.svg)


In questa documentazione verrà trattata la configurazione che è necessaria per attivare il connettore Nexi nel nodo pagamenti.
Per quanto riguarda l'installazione del nodo pagamenti in generale e la configurazione delle verticalizzazioni sul backoffice fare riferimento al documento 

[Configurazione del nodo dei pagamenti](./configurazione-nodo-pagamenti.md)


# Prerequisiti
  - backend ( VBG ) alla versione 2.77 o successiva
  - applicativo nodo-pagamenti versione 2.8.2
  - comunicazione tra l'applicativo nodo-pagamenti e vbg ( solitamtente tramite http sulla porta 8080 )
  - comunicazione tra l'applicativo nodo-pagamenti e ibcsecurity ( solitamente tramite http sulla porta 8080 )


# Servizi usati dal connettore 
Il connettore di NEXI implementa solo il servizio di caricamento delle posizioni debitorie e quello per la verifica dello stato delle posizioni caricate.
In entrambi i casi il servizio è realizzato come uno scambio di informazioni su file txt in una specifica directory condivisa.
Nel caso del caricamento delle posizioni il connettore dovrà scrivere dei flussi di dati nella cartella condivisa.
Nel caso della verifica stato il connettore dovrà andare a leggere il contenuto di specifici files predisposti da NEXI in specifiche sottocartelle del medesimo percorso condiviso.
Per questa ragione per il connettore NEXI è necessario configurare un unico servizio nella tabella PAY_CONNECTOR_WS_ENDPOINT che punta all'URL della cartella condivisa.
Trattandosi di un percorso di rete occorre fare attenzione a configurarlo nel formato URL secondo il protocollo file:///. 
E' supportato anche il protocollo ftp:// ma non è mai stato usato e quindi non è testato a sufficienza per l'uso in produzione senza ulteriori test. 
Il percorso per lo scambio dei flussi dovrà essere configurato in un nuovo record della tabellla pay_connector_ws_endpoint. I valori da inserire sono i seguenti.

#### PAY_CONNECTOR_WS_ENDPOINT 
| Colonna | Valore | Note |
| ------ | ------ | ------ |
| **CODICE_CONNETTORE** | NEXIGE | Codice del connettore nel nodo pagamenti, si può impostare qualunque codice. Il codice deve essere valorizzato in FK nel campo PAY_PROFILI_ENTI_CREDITORI.CODICE_CONNETTORE   |
| **ID** |  | Numero progressivo |
| ENDPOINT_URL | file://///vm-back20/NEXI/TestFlussi/TestCommercio | URL del percorso condiviso per lo scambio files |
| UTENTE |  | lasciare vuoto |
| PASSWORD | | lasciare vuoto |
| TIMEOUT | 15000 | Serve a configurare il timeout di attesa nell'invocazione del servizio configurato nel campo ENDPOINT_URL |
| DESCRIZIONE | cartella condivisa per lo scambio dei flussi | Descrizione aggiuntiva che spiega a cosa si riferisce questo endpoint |
| QUARTZ_SCHEDULE | 0 4/10 * * * ? | Espressione chron che fa partire le chiamate ogni 10 minuti a partire dal minuto 4, ossia a e 4, a e 14, a e 24 eccetera |
| FLAG_SOLO_SCHEDULATO | 1 | Indica se la chiamata all'endpoint avviene in maniera solo schedulata (1) oppure anche sincrona (0) |
| MAX_CHIAMATE |  | lasciare vuoto |
| FLAG_SPEGNI_SCHEDULER | 0 | utile per disattivare gli scheduler mantenendo memorizzata l'espressione che lo configura in quartz_schedule |

# Configurazione del connettore
Il connettore NEXI per Genova è deployato già col nodo pagamenti e per poter essere utilizzato deve essere configurato inserendo un record nella tabella PAY_CONNECTOR_CONFIG

#### PAY_CONNECTOR_CONFIG
| Colonna | Valore | Note |
| ------ | ------ | ------------ |
| **CODICE** |  | Identificativo del connettore che deve essere poi associato al profilo dell'ente creditore in PAY_PROFILI_ENTI_CREDITORI.CODICE_CONNETTORE
| DESCRIZIONE | Connettore NEXI | Descrizione del connettore |
| PAY_CONNECTOR_JAVA_CLASS | it.gruppoinit.pal.gp.pay.connector.nexi.genova.NexiGenovaConnector | Classe java che identifica il connettore |
| URL_PORTALE_PAGAMENTI | | Url per accedere al portale dei pagamenti |
| FK_WS_CARICAMENTO | | FK al servizio di scambio files configurato al paragrafo precedente |
| FK_WS_VERIFICA | | FK al servizio di scambio files configurato al paragrafo precedente |

gli altri campi devono essere lasciati vuoti

# Configurazione dell'amministrazione
Deve essere inserito un record in AMMINISTRAZIONI per popolare gli attributi dell'anagrafica del comune di Genova.
I campi nell'elenco sottostante se presenti vengono riportati nei dati (facoltativi) descritttivi dell'ente creditore durante la trasmissione delle posizioni debitorie al sistema di pagamento NEXI
| Colonna | Descrizione |
| ------ | ------ |
| **IDCOMUNE** | Identificativo dell'installazione  |
| **CODICEAMMINISTRAZIONE** | Numero progressivo  |
| AMMINISTRAZIONE | Denominazione dell'ente |
| INDIRIZZO | Indirizzo  |
| CITTA | Località  |
| CAP | CAP  |
| PROVINCIA | Provincia  |
| TELEFONO1 | Numero di telefono  |
| EMAIL | Email  |

#Configurazione della causale di registrazione di default
Tutti i debiti che vengono caricati nel nodo pagamenti devono essere associati ad una causale di registrazione per cui è specificato il CODICE_VERSAMENTO.
Nel caso di Genova tutte le bollette che vengono caricate attraverso il nodo pagamenti saranno associate alla stessa causale di registrazione a cui è assegnato il codice versamento 'MERCMERCIVARIEAVV'.
Per associare automaticamente tutte le posizioni caricate per genova a questo codice versamento è necessario inserire un record nella tabella PAY_REGISTRAZIONI_CAUSALI. Come illustrato nella tabella sotto.

#### pay_registrazioni_causali
| Colonna | Valore | Note |
| ------  | ------ | ------ |
| **ID** |  | Numero progressivo |
| **IDCOMUNE** |  | Identificativo dell'ente |
| SOFTWARE |  | Identificativo del modulo dell'ente (non utilizzato) |
| DESCRIZIONE |  | Descrizione della causale |
| ORDINE |  | (non utilizzato) | 
| FLAG_RIDUZIONE |  | (non utilizzato) |
| FLAG_NOINCASSI | | (non utilizzato) |
| CODICE_VERSAMENTO | MERCMERCIVARIEAVV | Codice che identifica la tipologia di onere nel sistema dei pagamenti NEXI |

#Configurazione del profilo dell'ente
Tutte le chiamate che il nodo pagamenti riceve devono contenere un parametro **cfEnteCreditore** che deve fare riferimento all'identificativo del profilo di un ente censito nel nodo pagamenti.
Per attivate il connettore NEXI per il comune di Genova è quindi necessario inserire il corrispindente profilo ente nella tabella PAY_PROFILI_ENTI_CREDITORI.
Il record inserito associerà l'amministrazione inserita nella tabella AMMINISTRAZIONI al connettore NEXIGE precedentemente configurato in PAY_CONNECTOR_CONFIG.
Il CF_CODICE_PROFILO usato per genova è 'GE'. 
Tale valore deve essere configurato nella apposita verticalizzazione del BackOffice per assicurarsi che il nodo pagamenti sia invocato con il parametro corretto.
#### pay_profili_enti_creditori

| Colonna | Valore | Note |
| ------ | ------ | ------ |
| **IDCOMUNE** |  | Identificativo dell'ente |
| **ID** |  | Numero progressivo |
| CODICEAMMINISTRAZIONE |  | Fk verso la tabella AMMINISTRAZIONI che identifica i dati dell'ente |
| CF_CODICE_PROFILO | GE | E' il codice che il backoffice trasmette per identificare il sistema dei pagamenti da utilizzare |
| CODICE_CONNETTORE | NEXIGE | Identificativo del connettore NEXI già configurato |
| FK_CUSALE_REG_DEFAULT |  | FK verso pay_registrazioni_causali che definisce la causale di registrazione del debito da usare nel caso in cui vengano caricate posizioni per cui non è specificata nessuna causale di versamento. Impostare il riferimento alla PK della causale configurata in precedenza |
| ID_APP_PSP |  | Non utilizzato |
| CF_CODICE_PROFILO_PSP | D969 | ID dell'ente in NEXI |
|CF_ENTE_QRCODE_PAGOPA|Il codice fiscale/partitaiva dell'ente che serve per generare la sezione Identificativo Ente/codice fiscale dell'Ente Creditore dell'algoritmo di generazione qrcode|
Le colonne della tabella PAY_PROFILI_ENTI_CREDITORI che non sono mostrate non devono essere valorizzate.

# Configurazione dei parametri specifici del connettore NEXI
#### pay_connector_config_values
Il connettore NEXI necessita di una serie di parametri specifici per poter funzionare correttamente. 
I valori di questi parametri devono essere configurati nella tabella PAY_CONNECTOR_CONFIG_VALUES come mostrato nella tabella sottostante.
| IDCOMUNE | ID | CONFIG_PARAM | CODICE_CONNETTORE | VALORE |                                                                                          
|-------- | ------ | ----------------- | ----------------- | -------------------------------------------------------------------------------------------------------- |
| GE | 1 | DOCUMENTI_SERVICE | NEXIGE | http://192.168.153.182:8083/api-backend/services/rest-auth-token/bollettazione/lettera-accompagnamento |
| GE | 2 | SECURITY_URL      | NEXIGE | http://192.168.153.182:8080/ibcsecurity/services/sigeproSecurity.wsdl |
| GE | 3 | SECURITY_USER | NEXIGE | NODO_PAGAMENTI |                             
| GE | 4 | SECURITY_PWD | NEXIGE | c2a0e24031859d404ffb5b966457e9a4 |                                                                        
| GE | 5 | SECURITY_ALIAS | NEXIGE | D969 |                                                                        
| GE | 6 | CENTRO_DI_COSTO | NEXIGE | 7101 |

#### pay_connector_config_params
I parametri di configurazione mostrati nel precedente paragrafo per poter essere configurati devono essere definiti come parametri del connettore.
Questo significa che devono esistere nella tabella PAY_CONNECTOR_CONFIG_PARAMS dei record che associano i nomi dei precedenti parametri (CONFIG_PARAM) al connettore NEXI (CODICE_CONNETTORE).
Quindi nella tabella PAY_CONNECTOR_CONFIG_PARAMS devono esistere i record mostrati nella tabella che segue.
| CONFIG_PARAM            | DESCRIZIONE                                                                                                                                                        | CODICE_CONNETTORE   |
| ----------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------- |
| CENTRO_DI_COSTO         | Codice del centro di costo utilizzato per la nomenclatura dei files del flusso NEXI                                                                                | NEXIGE              |
| DOCUMENTI_SERVICE       | URL del servizio del BO per la generazione di documenti                                                                                                            | NEXIGE              |
| SECURITY_ALIAS          | Id comune alias per interrogare securiry                                                                                                                           | (NULL)              |
| SECURITY_PWD            | Password per la connessione a security                                                                                                                             | (NULL)              |
| SECURITY_URL            | URL del servizio di security                                                                                                                                       | (NULL)              |
| SECURITY_USER           | Utente per la connessione a security                                                                                                                               | (NULL)              |

# Configurazione del backend
Ora che il nodo-pagamenti e il connettore sono configurati, bisogna indicare al backend che è attivo un sistema per poter pagare in maniera integrata. Per fare ciò bisogna recarsi nella voce di menù del backend _**Configurazione**_ -> _**Tutti i backoffice**_ -> _**Configurazione regole**_ 

![](./immagini/backend_01.png )

attivare la verticalizzazione NODO_PAGAMENTI

![](./immagini/backend_02.png )

e configurare i seguenti parametri personalizzandoli a seconda dell'ente

![](./immagini/backend_03.png )

| Parametro | Valore |
| ------ | ------ |
| AR_COD_FISC_ENTE_CREDITORE | GE |
| URL_WS |  http://devel9:8085/nodo-pagamenti/services/pagamentiSOAP?wsdl |
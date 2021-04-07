# Configurazione connettore EntraNext


In questa documentazione verrà trattata la configurazione che è necessaria per attivare il connettore **EntraNext** nel nodo pagamenti.
Per quanto riguarda l'installazione del nodo pagamenti in generale e la configurazione delle verticalizzazioni sul backoffice fare riferimento al documento 

[Configurazione del nodo dei pagamenti](./configurazione-nodo-pagamenti.md)


# Prerequisiti
  - backend ( VBG ) alla versione 2.77 o successiva
  - applicativo nodo-pagamenti versione 2.8.2
  - comunicazione tra l'applicativo nodo-pagamenti e vbg ( solitamtente tramite http sulla porta 8080 )
  - comunicazione tra l'applicativo nodo-pagamenti e ibcsecurity ( solitamente tramite http sulla porta 8080 )


# Servizi usati dal connettore 
Il connettore di EntraNext implementa i servizi di:
- caricamento del debito
- annullamento del debito
- verifica dello stato di pagamento delle rate di un debito
- autenticazione
Il sistema di pagamenti di EntraNext espone tutti i servizi necessari al connettore come metodi diversi di uno stesso endpoint SOAP.
Perciò sarà sufficiente configurare un sono record nella tabella *pay_connector_ws_endpoint* e impostare il riferimento a questo record nei campi corrispondenti nella riga di *pay_connector_config* che configura il connettore. 
La configurazione da impostare per il connettore e il suo endpoint è illustrata nei due paragrafi seguenti.

#### PAY_CONNECTOR_WS_ENDPOINT 
| Colonna | Valore | Note |
| ------ | ------ | ------ |
| **CODICE_CONNETTORE** | EntraNext | Codice del connettore nel nodo pagamenti come definito in PAY_CONNECTOR_CONFIG.CODICE, si può impostare qualunque codice. Il codice deve essere valorizzato in FK nel campo PAY_PROFILI_ENTI_CREDITORI.CODICE_CONNETTORE   |
| **ID** |  | Numero progressivo |
| ENDPOINT_URL | https://linknext-t.comune.modena.it/LinkNext.asmx | URL dell'endpoint SOAP |
| UTENTE | VBG_Portale | lasciare vuoto |
| PASSWORD | ****** | impostare la vera password |
| TIMEOUT | 15000 | Serve a configurare il timeout di attesa nell'invocazione del servizio configurato nel campo ENDPOINT_URL |
| DESCRIZIONE | URL base dei servizi SOAP | Descrizione aggiuntiva che spiega a cosa si riferisce questo endpoint |
| QUARTZ_SCHEDULE |  | Configurare un espressione chron se si vuole che il nodo pagamenti ritenti periodicamente le chiamate che hanno avuto esito negativo a causa di errori dovuti alla temporanea indisponibilità del servizio |
| FLAG_SOLO_SCHEDULATO | 0 | Indica che le chiamate all'endpoint avvengono in maniera sincrona |
| MAX_CHIAMATE |  | può essesere impostato per impostare un limite ai tentativi di invocare i servizi che hanno fallito |
| FLAG_SPEGNI_SCHEDULER | 0 | utile per disattivare gli scheduler mantenendo memorizzata l'espressione che lo configura in quartz_schedule |

# Configurazione del connettore
Il connettore ExtraNet per Modena è deployato già col nodo pagamenti e per poter essere utilizzato deve essere configurato inserendo un record nella tabella PAY_CONNECTOR_CONFIG

#### PAY_CONNECTOR_CONFIG
| Colonna | Valore | Note |
| ------ | ------ | ------------ |
| **CODICE** |  | Identificativo del connettore che deve essere poi associato al profilo dell'ente creditore in PAY_PROFILI_ENTI_CREDITORI.CODICE_CONNETTORE |
| DESCRIZIONE | Connettore EntraNext | Descrizione del connettore usata nei messaggi di errore |
| PAY_CONNECTOR_JAVA_CLASS | it.gruppoinit.pal.gp.pay.connector.entranext.EntraNextConnector | Classe java che implementa il connettore |
| FK_WS_CARICAMENTO | | FK all'enpoint SOAP configurato al paragrafo precedente |
| FK_WS_ANNULLAMENTO | | FK all'enpoint SOAP configurato al paragrafo precedente |
| FK_WS_VERIFICA | | FK all'enpoint SOAP configurato al paragrafo precedente |

gli altri campi devono essere lasciati vuoti

# Configurazione dell'amministrazione
Deve essere inserito un record in AMMINISTRAZIONI per popolare gli attributi dell'anagrafica del comune di Genova.
I campi nell'elenco sottostante se presenti vengono riportati nei dati (facoltativi) descritttivi dell'ente creditore durante la trasmissione delle posizioni debitorie al sistema di pagamento NEXI
| Colonna | Descrizione |
| ------ | ------ |
| **IDCOMUNE** | Identificativo dell'installazione  |
| **CODICEAMMINISTRAZIONE** | Numero progressivo  |
| AMMINISTRAZIONE | Denominazione dell'ente |

# Configurazione delle causali di registrazione 
Tutti i debiti che vengono caricati nel nodo pagamenti devono essere associati ad una causale di registrazione per cui devono essere valorizzati i campi CODICE_VERSAMENTO e PARAMETRI.
Nel caso di Modena la causale di versamento è utilizzata per determinare la tipologia di documento (Avviso o Fattura) che il sistema EntraNext deve restituire al noso pagamenti insieme all'esito del caricamento di un debito.
Quindi dovranno essere configurati due record nella tabella *pay_registrazioni_causali* per poter specificare le due diverse tipologie di documento richiesto a seconda delle esigenze del client.

#### pay_registrazioni_causali causale per gli avvisi
| Colonna | Valore | Note |
| ------  | ------ | ------ |
| **ID** |  | Numero progressivo |
| **IDCOMUNE** |  |  |
| SOFTWARE |  | Identificativo del modulo dell'ente (non utilizzato) |
| DESCRIZIONE |  | Descrizione della causale (libero) |
| ORDINE |  | (non utilizzato) | 
| FLAG_RIDUZIONE |  | (non utilizzato) |
| FLAG_NOINCASSI | | (non utilizzato) |
| CODICE_VERSAMENTO | TOSAP | Codice che identifica la tipologia di onere nel backoffice (TIPICAUSALIONERI.CODICECAUSALEPEOPLE)  |
| PARAMETRI | Avviso | Valore passato a EntraNext per specificare il tipo documento Avviso |

#### pay_registrazioni_causali causale per le fatture
| Colonna | Valore | Note |
| ------  | ------ | ------ |
| **ID** |  | Numero progressivo |
| **IDCOMUNE** |  |  |
| SOFTWARE |  | Identificativo del modulo dell'ente (non utilizzato) |
| DESCRIZIONE |  | Descrizione della causale (libero) |
| ORDINE |  | (non utilizzato) | 
| FLAG_RIDUZIONE |  | (non utilizzato) |
| FLAG_NOINCASSI | | (non utilizzato) |
| CODICE_VERSAMENTO | FIERE | Codice che identifica la tipologia di onere nel backoffice (TIPICAUSALIONERI.CODICECAUSALEPEOPLE)  |
| PARAMETRI | Fattura | Valore passato a EntraNext per specificare il tipo documento fattura |

I valori specificati nel campo CODICE_VERSAMENTO delle due causali di registrazione devono corrispondere con i valori configurati nel backoffice nella tabella TIPICAUSALIONERI come descritto nella tabella sottostante:
#### TIPICAUSALIONERI causale per gli avvisi
| Colonna | Valore | Note |
| ------  | ------ | ------ |
| **ID** |  | Numero progressivo |
| **IDCOMUNE** |  |  |
| **SOFTWARE** | SS | Identificativo del modulo di VBG |
| CODICECAUSALEPEOPLE | TOSAP | deve corrispondere a pay_registrazioni_causali.CODICE_VERSAMENTO |
| FLAG_GENERA_FATTURA | 0 | generazione fattur anon richiesta |
| FLAG_GENERA_AVVISO | 1 | generazione avviso richiesta |

#### TIPICAUSALIONERI causale per le fatture 
| Colonna | Valore | Note |
| ------  | ------ | ------ |
| **ID** |  | Numero progressivo |
| **IDCOMUNE** |  |  |
| **SOFTWARE** | SS | Identificativo del modulo di VBG |
| CODICECAUSALEPEOPLE | FIERE | deve corrispondere a pay_registrazioni_causali.CODICE_VERSAMENTO |
| FLAG_GENERA_FATTURA | 1 | generazione fattur anon richiesta |
| FLAG_GENERA_AVVISO | 0 | generazione avviso richiesta |

# Configurazione del profilo dell'ente
Tutte le chiamate che il nodo pagamenti riceve devono contenere un parametro **cfEnteCreditore** che deve fare riferimento all'identificativo del profilo di un ente censito nel nodo pagamenti.
Per attivate il connettore EntraNext per il comune di Modena è quindi necessario inserire il corrispindente profilo ente nella tabella PAY_PROFILI_ENTI_CREDITORI.
Il record inserito associerà l'amministrazione inserita nella tabella AMMINISTRAZIONI al connettore EntraNext precedentemente configurato in PAY_CONNECTOR_CONFIG.
Il CF_CODICE_PROFILO usato per Modena è 'F257'. 
Tale valore deve essere configurato nella apposita verticalizzazione del BackOffice per assicurarsi che il nodo pagamenti sia invocato con il parametro corretto.

#### pay_profili_enti_creditori
| Colonna | Valore | Note |
| ------ | ------ | ------ |
| **IDCOMUNE** |  | Identificativo dell'ente |
| **ID** |  | Numero progressivo |
| CODICEAMMINISTRAZIONE |  | Fk verso la tabella AMMINISTRAZIONI che identifica i dati dell'ente |     
| CF_CODICE_PROFILO | F257 | E' il codice che il backoffice trasmette per identificare il sistema dei pagamenti da utilizzare |
| CODICE_CONNETTORE | EntraNext | Identificativo del connettore EntraNext già configurato |
| FK_CUSALE_REG_DEFAULT |  | FK verso pay_registrazioni_causali che definisce la causale di registrazione del debito da usare nel caso in cui non venga specificata in fase di caricamento. Di default si usa la causale TOSAP associata alla generazione dell'avviso di pagamento |
| ID_APP_PSP | EntraNext_CSV | Identificativo dell'applicazione nel sistema EntraNext |
| CF_CODICE_PROFILO_PSP | 00221940364 | Ientificativo dell’ente nel sistema EntraNext |
|CF_ENTE_QRCODE_PAGOPA|Il codice fiscale/partitaiva dell'ente che serve per generare la sezione Identificativo Ente/codice fiscale dell’Ente Creditore dell'algoritmo di generazione qrcode|
Le colonne della tabella PAY_PROFILI_ENTI_CREDITORI che non sono mostrate non devono essere valorizzate.

# Configurazione del backend
Ora che il nodo-pagamenti e il connettore sono configurati, bisogna indicare al backend che è attivo un sistema per poter pagare in maniera integrata. Per fare ciò bisogna recarsi nella voce di menù del backend _**Configurazione**_ -> _**Tutti i backoffice**_ -> _**Configurazione regole**_ 

![](./immagini/backend_01.png )

attivare la verticalizzazione NODO_PAGAMENTI

![](./immagini/backend_02.png )

e configurare i seguenti parametri personalizzandoli a seconda dell'ente

![](./immagini/backend_03.png )

| Parametro | Valore |
| ------ | ------ |
| AR_COD_FISC_ENTE_CREDITORE | F257 |
| URL_WS |  http://devel9:8085/nodo-pagamenti/services/pagamentiSOAP?wsdl |
|TIPOMOVIMENTO_DOC_AVVISO | TTGENAVV|
|TIPOMOVIMENTO_DOC_FATTURA | TTGENFATT|

Sono i movimenti da specificare nel caso si voglia riportare i documenti delle posizioni debitorie nella pratica di riferimento

## conti

ID|CODICECONTO|CODICESOTTOCONTO|DESCRIZIONE|NOTE|FK_CODICEAMMINISTRAZIONE|SOFTWARE|IDCOMUNE|IVA|ANNO_ACCERTAMENTO|NUMERO_ACCERTAMENTO|DATASCADENZA|NUMERO_SOTTO_ACCERTAMENTO
-|-|-|-|-|-|-|-|-|-|-|-|-
2||Servizi|ONERI AVVISI TOSAP|||CO|F257|0|2021||31-DIC-99|T525009

Sono importanti le colonne CODICESOTTOCONTO che **DEVE** corrispondere con una delle causali definite in 
```java
@XmlType(name = "CausaliImporti")
@XmlEnum
public enum CausaliImporti {

    @XmlEnumValue("Servizi")
    SERVIZI("Servizi"),
    @XmlEnumValue("Sanzioni")
    SANZIONI("Sanzioni"),
    @XmlEnumValue("Spese")
    SPESE("Spese"),
    @XmlEnumValue("Bollo")
    BOLLO("Bollo"),
    @XmlEnumValue("Interessi")
    INTERESSI("Interessi"),
    @XmlEnumValue("Arrotondamento")
    ARROTONDAMENTO("Arrotondamento"),
    @XmlEnumValue("DepositiCauzionali")
    DEPOSITI_CAUZIONALI("DepositiCauzionali"),
    @XmlEnumValue("RimborsoDepositiCauzionali")
    RIMBORSO_DEPOSITI_CAUZIONALI("RimborsoDepositiCauzionali"),
    @XmlEnumValue("RimborsoServizi")
    RIMBORSO_SERVIZI("RimborsoServizi"),
    @XmlEnumValue("SpeseTenutaConto")
    SPESE_TENUTA_CONTO("SpeseTenutaConto"),
    @XmlEnumValue("ImpostaRegistro")
    IMPOSTA_REGISTRO("ImpostaRegistro"),
    @XmlEnumValue("Commissioni")
    COMMISSIONI("Commissioni"),
    @XmlEnumValue("InteressiPassiviCCP")
    INTERESSI_PASSIVI_CCP("InteressiPassiviCCP"),
    @XmlEnumValue("SpeseDomiciliazione")
    SPESE_DOMICILIAZIONE("SpeseDomiciliazione"),
    @XmlEnumValue("CommissioniBolloSpeseTenutaConto")
    COMMISSIONI_BOLLO_SPESE_TENUTA_CONTO("CommissioniBolloSpeseTenutaConto"),
    @XmlEnumValue("CommissioniSpeseTenutaConto")
    COMMISSIONI_SPESE_TENUTA_CONTO("CommissioniSpeseTenutaConto"),
    @XmlEnumValue("Urgenza")
    URGENZA("Urgenza"),
    @XmlEnumValue("SanzioniInfedele")
    SANZIONI_INFEDELE("SanzioniInfedele"),
    @XmlEnumValue("SanzioniOmessa")
    SANZIONI_OMESSA("SanzioniOmessa"),
    @XmlEnumValue("SanzioniLiquidazione")
    SANZIONI_LIQUIDAZIONE("SanzioniLiquidazione"),
    @XmlEnumValue("Addizionali")
    ADDIZIONALI("Addizionali");
```
ed è importante la colonna numero_sotto_accertamento che è un valore che deve essere comunicato dall'ente e configurato per quel tipo di pendenza



## causali oneri

CO_ID|CO_DESCRIZIONE|CO_SERICHIEDEENDO|IDCOMUNE|SOFTWARE|FK_RCO_ID|CO_DISABILITATO|CO_ORDINAMENTO|CODICECAUSALEPEOPLE|PAGAMENTIREGULUS|FKIDCAUSALEBOLLO|FLG_TIPICAUSALIINTERESSI|FK_TIPICAUSALIINTERESSI|FLAG_GENERA_FATTURA|FLAG_GENERA_AVVISO
-|-|-|-|-|-|-|-|-|-|-|-|-|-|-
54|ONERI AVVISI TOSAP|0|F257|CO||0||TOSAP|0||0||0|1

È importante la colonna CODICECAUSALEPEOPLE che va a corrispondere  nel nodo pagamento la colonna PAY_REGISTRAZIONI_CAUSALI.CODICE_VERSAMENTO e serve per capire se generare un avviso o una fattura.


## job repository

Il job permette di agganciare il documento alla pratica

IDCOMUNEALIAS|JOB_NAME|DESCRIPTION|ACTIVE|TRIGGER_TYPE|START_DELAY|REPEAT_INTERVAL|CRON_EXPRESSION|JOB_CLASS_NAME|SOFTWARE
-|-|-|-|-|-|-|-|-|-  
'F257'|'VerificaDocumentiPosizioniDebitorieJob'|'VerificaDocumentiPosizioniDebitorieJob'|'0'|'CRON'|null|null|'0 0/10 * * * ? *'|'it.gruppoinit.pal.gp.core.features.oneri.jobs.VerificaDocumentiPosizioniDebitorieJob'|'TT'

## job repository params

ETICHETTA|DESCRIZIONE|VALORE|FK_JOB_REPOSITORY
-|-|-|-
'LISTA_ALIAS_DA_ELABORARE'|'La lista degli alias da elaborare separata da ; se non specificato prende la lista dalla security'|'F257'|'2'
'NUM_RECORD_DA_ELABORARE'|'Indicare il numero di record da processare per ente. Ogni esecuzione del job cerca di elaborare questo numero di record. Il conteggio viene incrementato quando una riga della tabella ISTANZEONERI_POS_DEB_BATCH viene segnata come completata'|'150'|'2'
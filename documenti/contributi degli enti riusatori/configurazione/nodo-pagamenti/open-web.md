# Open Web

In questa documentazione verrà trattata la configurazione che è necessaria per attivare il connettore OPEN WEB nel nodo pagamenti del fornitore Dedagroup.

Per quanto riguarda l'installazione del nodo pagamenti in generale e la configurazione delle verticalizzazioni sul backoffice fare riferimento al documento

[Configurazione del nodo dei pagamenti](./configurazione-nodo-pagamenti.md)

## Prerequisiti

- backend ( VBG ) alla versione 2.99 o successiva
- applicativo nodo-pagamenti versione 2.99
- comunicazione tra l'applicativo nodo-pagamenti e vbg ( solitamtente tramite http sulla porta 8080 )
- comunicazione tra l'applicativo nodo-pagamenti e ibcsecurity ( solitamente tramite http sulla porta 8080 )

## NODO PAGAMENTI

### Attivazione ente

Per attivare l'ente contattare il fornitore dei servizi (DEDAGROUP). È importante preventivamente capire con l'ente quali servizi o tipi di dovuto (causali attivare).

### PAY_CONNECTOR_CONFIG

CODICE|DESCRIZIONE|PAY_CONNECTOR_JAVA_CLASS|WS_URL|WS_USR|WS_PWD|WS_TIMEOUT|URL_PORTALE_PAGAMENTI|PWD_PORTALE_PAGAMENTI|IN_WS_TIMEOUT|IN_WS_PWD|IN_WS_USR|FK_WS_CARICAMENTO|FK_WS_ANNULLAMENTO|FK_WS_VERIFICA|FK_WS_ATTIVA_SESSIONE|FK_WS_AVVISO|FK_WS_NOTIFICA|FK_WS_SECURITY|APPLICATION_CODE|FK_WS_FATTURA|FK_WS_RICEVUTA|FK_WS_IUV
---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---
**codice_connettore**|OpenWeb Connettore dedagroup|it.gruppoinit.pal.gp.pay.connector.openweb.OpenWebConnector||||||||||2|2|2|2|2||1||||

### PAY_CONNECTOR_WS_ENDPOINT

CODICE_CONNETTORE|ID|ENDPOINT_URL|UTENTE|PASSWORD|TIMEOUT|DESCRIZIONE|QUARTZ_SCHEDULE|FLAG_SOLO_SCHEDULATO|MAX_CHIAMATE|FLAG_SPEGNI_SCHEDULER
---|---|---|---|---|---|---|---|---|---|---
**codice_connettore**|1|`https://server/auth_hub/oauth/token`|**USERNAME TOKEN**|**PASSWORD TOKEN**|20000|||0||0
**codice_connettore**|2|`http://server/portal/servizi/pagamenti/ws/10/`|||30000|||0||0

### PAY_PROFILI_ENTI_CREDITORI

IDCOMUNE|ID|CODICECOMUNE|CODICEAMMINISTRAZIONE|SOFTWARE|CBILL|CC_POSTALE|CF_CODICE_PROFILO|CODICE_CONNETTORE|FK_CUSALE_REG_DEFAULT|ID_APP_PSP|CF_CODICE_PROFILO_PSP|URL_ESITO_PAGAMENTO|URL_ANNULLAMENTO_PAGAMENTO|CODICE_SEGREGAZIONE|APPLICATION_CODE|CF_ENTE_QRCODE_PAGOPA
---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---
**idcomune**|1|||TT|||**codice_connettore**|**codice_connettore**|1|||`https://server_publico_cliente/nodo-pagamenti/esitoSessionePagamento/**codice_connettore**`||||

> **Atenzione!** la URL di esito pagamento deve essere raggiungibile dall'esterno.
Va configurata su NGINX o IIS per esporre precisamente fino all'url **esitoPagamento**.

### PAY_REGISTRAZIONI_CAUSALI

ID|IDCOMUNE|SOFTWARE|DESCRIZIONE|ORDINE|CODICE_VERSAMENTO|PARAMETRI
---|---|---|---|---|---|---
1|**idcomune**|TT|Open web diritti|1|**diritti**|
2|**idcomune**|TT|Open web edilizia_costo_costruzione|2|**edilizia_costo_costruzione**|

> **Attenzione!** Il codice versamento e le causali dipendono dalle voci di pagamento che il cliente intende attivare. La lista riportata è solo di esempio.

## BACKOFFICE

### AMMINISTRAZIONI

Non vanno configurate amministrazioni specifiche per il nodo.

### VERTICALIZZAZIONI

A livello di verticalizzazione vanno attivate quelle generiche del nodo dei pagamenti indicando il **CF_ENTE_CREDITORE** come configurato in pay_profili_enti_creditori.

### conti

È importante configurare i conti tenendo presente che devono essere inserite informazioni relative a:

- **anno competenza** nel campo **anno_accertamento** 
- **accertamento** nel campo **numero accertamento**
- **tipo_dovuto** nel campo **codice sotto conto**.

> **Attenzione!** Il **tipo_dovuto** dipende dalle voci di pagamento che il cliente intendoe attivare.

### causali oneri

Le causali di pagamento vanno concordate con l'ente ed per ognuna di essi va associato il conto definito sopra.

Queste informazioni servono per popolare i dati contabili dell'ente.

# FVG PAY

In questa documentazione verrà trattata la configurazione che è necessaria per attivare il connettore FVG PAY nel nodo pagamenti.
Per quanto riguarda l'installazione del nodo pagamenti in generale e la configurazione delle verticalizzazioni sul backoffice fare riferimento al documento

[Configurazione del nodo dei pagamenti](./configurazione-nodo-pagamenti.md)

## Prerequisiti

- backend ( VBG ) alla versione 2.96 o successiva
- applicativo nodo-pagamenti versione 2.96
- comunicazione tra l'applicativo nodo-pagamenti e vbg ( solitamtente tramite http sulla porta 8080 )
- comunicazione tra l'applicativo nodo-pagamenti e ibcsecurity ( solitamente tramite http sulla porta 8080 )

## NODO PAGAMENTI

### Attivazione ente

Per attivare l'ente o si mandano le informazioni a INSIEL o si invoca un servizio di sottoscrizione con POSTMAN all'url
<http://pagopa-coll-gateway.insiel.it/pagopa/[ID_BENEFICIARIO]]/[ID_SERVIZIO]]/subscriptions>

dove si comunicano gli url di ricezione esito, i modelli di pagamento ed il supporto RATE.

> ATTENZIONE! Al momento il supporto rate non è previsto nella implementazione del nostro connettore quindi le attivazioni andranno fatte specificando  **"rate_obbligatorie": false**

esempio di chiamata di sottoscrizione con **CURL**

```bash

curl --location --request POST 'http://<server_name>/pagopa/80002150938/209680002150938/subscriptions' \
--header 'Authorization: Basic <basic authentication>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "callbackUrlEsitoRevoca": "https://<nostro_server>/nodo-pagamenti/services/rest/fvgpay/notificaEsitiRevoca",
    "callbackUrlRegistrazione": "https://<nostro_server>/nodo-pagamenti/services/rest/fvgpay/notificaEsitiRegistrazione",
    "callbackUrlRicezioneRT": "https://<nostro_server>/nodo-pagamenti/services/rest/fvgpay/notificaEsitiPagamento",
    "commissione_carico_pa": {
        "importo": 0,
        "valuta": "EUR"
    },
    "modelli_pagamento": {
        "elenco": [
        "1",
        "3"
        ]
    },
    "supporto_rate": {    
        "max_rate": 10,
        "rate_obbligatorie": false    
    }
}
'
```

Contenuto **JSON**

```json

{
    "callbackUrlEsitoRevoca": "http://<SERVERNAME>/nodo-pagamenti/services/rest/fvgpay/notificaEsitiRevoca",
    "callbackUrlRegistrazione": "http://<SERVERNAME>/nodo-pagamenti/services/rest/fvgpay/notificaEsitiRegistrazione",
    "callbackUrlRicezioneRT": "http://<SERVERNAME>/nodo-pagamenti/services/rest/fvgpay/notificaEsitiPagamento",
    "commissione_carico_pa": {
        "importo": 0,
        "valuta": "EUR"
    },
    "modelli_pagamento": {
        "elenco": [
        "1",
        "3"
        ]
    },
    "supporto_rate": {    
        "max_rate": 10,
        "rate_obbligatorie": false    
    }
}

```

Questi indirizzi devono essere raggiungibili e quindi è necessario pubblicarli su NGINX o ISAPI.

È necessario configurare quali IP dovrebbero raggiungere queste porte applicative e quindi su NGINX va legato tutto il path

http://<SERVERNAME>/nodo-pagamenti/services/rest/fvgpay/* all'IP chiamante (farseli mandare da INSIEL).

### PAY_CONNECTOR_CONFIG

CODICE|DESCRIZIONE|PAY_CONNECTOR_JAVA_CLASS|WS_URL|WS_USR|WS_PWD|WS_TIMEOUT|URL_PORTALE_PAGAMENTI|PWD_PORTALE_PAGAMENTI|IN_WS_TIMEOUT|IN_WS_PWD|IN_WS_USR|FK_WS_CARICAMENTO|FK_WS_ANNULLAMENTO|FK_WS_VERIFICA|FK_WS_ATTIVA_SESSIONE|FK_WS_AVVISO|FK_WS_NOTIFICA|FK_WS_SECURITY|APPLICATION_CODE|FK_WS_FATTURA|FK_WS_RICEVUTA|FK_WS_IUV
---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---
FVG_G888|FVG PAY PORDENONE|it.gruppoinit.pal.gp.pay.connector.fvgpay.FvgPayConnector|||||<http://ponline.regione.fvg.it/FVGPaymentGateway/Login>|||||1|1|1|1|1||||||

### PAY_CONNECTOR_WS_ENDPOINT

CODICE_CONNETTORE|ID|ENDPOINT_URL|UTENTE|PASSWORD|TIMEOUT|DESCRIZIONE|QUARTZ_SCHEDULE|FLAG_SOLO_SCHEDULATO|MAX_CHIAMATE|FLAG_SPEGNI_SCHEDULER
---|---|---|---|---|---|---|---|---|---|---
FVG_G888|1|<http://pagopa-coll-gateway.insiel.it>|**USER_NAME**|**PASSWORD**|180000|Base Path dei Servizi rest||0||0

### PAY_PROFILI_ENTI_CREDITORI

> **Attenzione!!** Il campo **CF_CODICE_PROFILO_PSP** deve essere il cf impostato nella configurazione del sistema FVGPAY. Il dato ci viene rimandato nei servizi di notifica delle posizioni debitorie e ci serve per recuperare il profilo e dunque le posizioni. Significa che deve essere univoco nelle installazioni e non è possibile avere una configurazione mista per più servizi.

IDCOMUNE|ID|CODICECOMUNE|CODICEAMMINISTRAZIONE|SOFTWARE|CBILL|CC_POSTALE|CF_CODICE_PROFILO|CODICE_CONNETTORE|FK_CUSALE_REG_DEFAULT|ID_APP_PSP|CF_CODICE_PROFILO_PSP|URL_ESITO_PAGAMENTO|URL_ANNULLAMENTO_PAGAMENTO|CODICE_SEGREGAZIONE|APPLICATION_CODE|CF_ENTE_QRCODE_PAGOPA
---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---
**IDCOMUNE**|1|||TT|||80002150938|FVG_G888|1|209680002150938|80002150938|<https://devel3.init.gruppoinit.it/nodo-pagamenti/esitoSessionePagamento/80002150938?esito=1>|<https://devel3.init.gruppoinit.it/nodo-pagamenti/esitoSessionePagamento/80002150938?esito=0>|||80002150938

### PAY_CONNECTOR_CONFIG_VALUES

IDCOMUNE|ID|CONFIG_PARAM|CODICE_CONNETTORE|VALORE
---|---|---|---|---
**IDCOMUNE**|1|URL_CALLBACK_CAMBIO_STATO|FVG_G888|<http://10.10.45.64:8080/api-backend/services/rest-auth-token/nodo-pagamenti/posizione-debitoria/aggiorna-stato>
**IDCOMUNE**|2|SECURITY_ALIAS|FVG_G888|**ALIAS_ENTE**
**IDCOMUNE**|3|SECURITY_PWD|FVG_G888|**password_security**
**IDCOMUNE**|4|SECURITY_URL|FVG_G888|<http://devel9:8080/ibcsecurity/services/sigeproSecurity.wsdl>
**IDCOMUNE**|5|SECURITY_USER|FVG_G888|NODO_PAGAMENTI
**IDCOMUNE**|6|FVG_PAY_USA_AUTH_PAG_IMMED|FVG_G888|true

> il parametro **FVG_PAY_USA_AUTH_PAG_IMMED** può essere omesso di default è true e indica che l'utente deve essere autenticato. In collaudo abbiamo sempre usato **false**.

### PAY_REGISTRAZIONI_CAUSALI

Le casuali vanno registrate senza particolari riferimenti. Usare il campo **codice_versamento** per collegarle con il back.
> È importante settare invece il parametro della causale **TASSONOMIA_PAGAMENTO** come descritto nella sezione specifica.

### PAY_REGCAUSALI_PARAMETRI

IDCOMUNE|ID|FK_PAYREGCAUSALE_ID|CHIAVE|VALORE
---|---|---|---|---
[IDCOMUNE]|[PROGRESSIVO]|[FK_CAUSALE]|**TASSONOMIA_PAGAMENTO**|[**codice tassonomia assegnato da FVG PAY**]
[IDCOMUNE]|[PROGRESSIVO]|[FK_CAUSALE]|**AGGIUNGI_GIORNI_A_DATA_SCADENZA_AVVISO**|2

Il parametro *AGGIUNGI_GIORNI_A_DATA_SCADENZA_AVVISO* **NON** è obbligatorio.
Serve per aggiungere giorni alla scadenza del pagamento.
La scadenza dell'avviso e la scadenza del pagamento sono due date differenti spesso e di solito la La data scadenza avviso blocca il pagamento quindi deve essere maggiore o uguale alla data scadenza pagamento.

## BACKOFFICE

### AMMINISTRAZIONI

Non vanno configurate amministrazioni specifiche per il nodo.

### VERTICALIZZAZIONI

A livello di verticalizzazione vanno attivate quelle generiche del nodo dei pagamenti indicando il **CF_ENTE_CREDITORE** come configurato in pay_profili_enti_creditori.

### conti

È importante configurare i conti tenendo presente che devono essere inserite informazioni relative a **capitolo bilancio** nel campo **CODICE_SOTTO_CONTO** e **accertamento** nel campo **numero accertamento**.

### causali oneri

Le causali di pagamento vanno concordate con l'ente ed per ognuna di essi va associato il conto definito sopra.

Queste informazioni servono per popolare i dati contabili dell'ente.

```xml
<datiContabili xmlns="http://www.insiel.it/pagamentifvg/datiContabili">
    <codBilancio>
        <codCapitolo>2020/2019/000123/001</codCapitolo>
        <accertamento>
            <codAccertamento>2020/2019/000000123</codAccertamento>
            <importo>10.0</importo>
        </accertamento>
    </codBilancio>
    <codBilancio>
        <codCapitolo>2020/2019/000123/002</codCapitolo>
        <accertamento>
            <codAccertamento>2020/2019/002000123</codAccertamento>
            <importo>1.0</importo>
        </accertamento>
    </codBilancio>
</datiContabili>
```

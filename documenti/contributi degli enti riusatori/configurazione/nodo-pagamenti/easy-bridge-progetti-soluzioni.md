# Configurazione connettore EasyBridge (Progetti e Soluzioni)

In questa documentazione verrà trattata la configurazione che è necessaria per attivare il connettore **Easy Bridge** nel nodo pagamenti.
Per quanto riguarda l'installazione del nodo pagamenti in generale e la configurazione delle verticalizzazioni sul backoffice fare riferimento al documento

[Configurazione del nodo dei pagamenti](./configurazione-nodo-pagamenti.md)

## Prerequisiti

- backend ( VBG ) alla versione 2.111 o successiva
- applicativo nodo-pagamenti versione 2.111

## Servizi usati dal connettore

Il connettore implementa i servizi di:

- caricamento del debito
- annullamento del debito
- annullamento di un debito come già pagato offline
- verifica dello stato di pagamento
- attivazione del pagamento OTF
- download dell'avviso di pagamento

Il sistema di pagamenti di espone tutti i servizi necessari al connettore come metodi diversi di uno stesso endpoint SOAP.
Perciò sarà sufficiente configurare un solo record nella tabella *pay_connector_ws_endpoint* e impostare il riferimento a questo record nei campi corrispondenti nella riga di *pay_connector_config* che configura il connettore.
La configurazione da impostare per il connettore e il suo endpoint è illustrata nei due paragrafi seguenti.

Il connettore espone anche un servizio SOAP che deve essere invocato da EasyBridge per notificare al nodo-pagamenti l'avvenuto pagamento di una posizione debitoria e trasmettere la ricevuta telematica.
Questo servizio è esposto all'URL `https://<server_name>/nodo-pagamenti/services/easybridge/esitiPagamento?wsdl` e deve essere configurato in modo da essere accessibile al sitema di pagamento di EasyBridge.

## Configurazioni

### Configurazione sicurezza chiamate in ingresso ws-in-users.properties

Al momento dell'attivazione dei servizi va comunicato a EasyBridge le credenziali per poter invocare il WS `https://<server_name>/nodo-pagamenti/services/easybridge/esitiPagamento?wsdl` esposto dal nostro nodo.
IL servizio va in qualche modo reso disponibile per essere invocato dall'esterno quindi mediante configurazioni di rete o proxy con protezione IP chiamante ad esempio.

Le credenziali vanno salvate nel file **ws-in-users.properties** nella forma

``` properties
# INSERIRE USERNAME / PASSWORD PER AUTENTICAZIONE BASIC
# MEDIANTE INTERCEPTOR ==> it.gruppoinit.pal.gp.pay.ws.interceptors.BasicAuthAuthorizationInterceptor
user1=passw1
```

### PAY_CONNECTOR_WS_ENDPOINT

| Colonna | Valore | Note |
| ------ | ------ | ------ |
| **CODICE_CONNETTORE** | EASYB_E256 | Codice del connettore nel nodo pagamenti come definito in PAY_CONNECTOR_CONFIG.CODICE, si può impostare qualunque codice. Il codice deve essere valorizzato in FK nel campo PAY_PROFILI_ENTI_CREDITORI.CODICE_CONNETTORE   |
| **ID** |  | Numero progressivo |
| ENDPOINT_URL | `https://<SERVER_EASY_BRIDGE>/bridge/WEBS_EasyBridgeInterface.asmx` | URL dell'endpoint SOAP |
| UTENTE |  | configuare le credenziali fornite al momento dell'attivazione |
| PASSWORD |  | configuare le credenziali fornite al momento dell'attivazione |
| TIMEOUT | 240000 | Serve a configurare il timeout di attesa nell'invocazione del servizio configurato nel campo ENDPOINT_URL |
| DESCRIZIONE | endpoint unico per i servizi easybridge | Descrizione aggiuntiva che spiega a cosa si riferisce questo endpoint |

## Configurazione del connettore

Il connettore  è deployato già col nodo pagamenti e per poter essere utilizzato deve essere configurato inserendo un record nella tabella PAY_CONNECTOR_CONFIG

### PAY_CONNECTOR_CONFIG

| Colonna | Valore | Note |
| ------ | ------ | ------------ |
| **CODICE** |  | Identificativo del connettore che deve essere poi associato al profilo dell'ente creditore in PAY_PROFILI_ENTI_CREDITORI.CODICE_CONNETTORE |
| DESCRIZIONE | Connettore EasyBridge comune di ... | Descrizione del connettore usata nei messaggi di errore |
| PAY_CONNECTOR_JAVA_CLASS | it.gruppoinit.pal.gp.pay.connector.easybridge.EasyBridgeConnector | Classe java che implementa il connettore |
| URL_PORTALE_PAGAMENTI | `https://server_easy_bridge:50061/POL_CitizenShortcut/GEN_Default.aspx?idDominio=01556360152&GroupKey=27092021` | URL del portale dei pagamenti per i pagamenti online
| FK_WS_CARICAMENTO | | FK all'enpoint SOAP configurato al paragrafo precedente |
| FK_WS_ANNULLAMENTO | | FK all'enpoint SOAP configurato al paragrafo precedente |
| FK_WS_AVVISO | | FK all'enpoint SOAP configurato al paragrafo precedente |

gli altri campi devono essere lasciati vuoti

## Configurazione delle causali di registrazione

Tutti i debiti che vengono caricati nel nodo pagamenti devono essere associati ad una causale di registrazione.

## pay_registrazioni_causali

Le causali vanno definite insieme all'ente in quanto andranno abilitate / gestite nel nodo pagamenti / backoffice quelle proprie specifiche per ogni specifico servizio.

| Colonna | Valore | Note |
| ------  | ------ | ------ |
| **ID** |  | Numero progressivo |
| **IDCOMUNE** |  |  |
| SOFTWARE |  | Identificativo del modulo dell'ente (non utilizzato) |
| DESCRIZIONE |  | Descrizione della causale (libero) |
| ORDINE |  | (non utilizzato) |
| CODICE_VERSAMENTO |  | Codice che identifica il tipo di debito/servizio da attivare nel sistema pagamento EasyBridge |
| MAPPATURA_CLIENT |  | Il campo che identifica la mappatura da associare al backoffice |

## pay_regcausali_parametri

In questa tabella vanno inseriti i valori da impostare per le date di scadenza.

IDCOMUNE|ID|FK_PAYREGCAUSALE_ID|CHIAVE|VALORE
---|---|---|---|---
**IDCOMUNE**|**PROGRESSIVO**|*FK_CAUSALE*|DESCRIZIONE_CAUSALE_PSP|Indicare la descrizione della causale come se lo aspetta
**IDCOMUNE**|**PROGRESSIVO**|*FK_CAUSALE*|AGGIUNGI_GIORNI_A_DATA_SCADENZA_AVVISO|La data di scadenza che viene visualizzata nel bollettino avviso è quella della posizione debitoria. La data di scadenza del pagamento (diversa dalla data stampabile e oltre la quale non è più pagabile in PAGO PA anche se scaduta) è uguale alla data di scadenza della posizione debitoria. Questo parametro permette di allungare la data di scadenza del pagamento rispetto a quella stampabile. L'ente decide di fare pagare oltre la data di scadenza

## Configurazione del profilo dell'ente

Tutte le chiamate che il nodo pagamenti riceve devono contenere un parametro **cfEnteCreditore** che deve fare riferimento all'identificativo del profilo di un ente censito nel nodo pagamenti.
Per attivate il connettore per il comune di Besana in Brianza, ad esempio, è quindi necessario inserire il corrispondente profilo ente nella tabella PAY_PROFILI_ENTI_CREDITORI.
Il CF_CODICE_PROFILO usato per Firenze è 'PES_A818'.
Tale valore deve essere configurato nella apposita verticalizzazione del BackOffice per assicurarsi che il nodo pagamenti sia invocato con il parametro corretto.

## pay_profili_enti_creditori

| Colonna | Valore | Note |
| ------ | ------ | ------ |
| **IDCOMUNE** |  | Identificativo dell'ente |
| **ID** |  | Numero progressivo |
| CODICEAMMINISTRAZIONE |  | lasciare vuoto |
| CF_CODICE_PROFILO |  | E' il codice che il backoffice trasmette per identificare il sistema dei pagamenti da utilizzare |
| CODICE_CONNETTORE |  | Identificativo del connettore già configurato nella tabella pay_connector_config |
| FK_CUSALE_REG_DEFAULT |  | FK verso pay_registrazioni_causali che definisce la causale di registrazione del debito da usare nel caso in cui non venga specificata in fase di caricamento. |
| ID_APP_PSP |  | Identificativo della Bu nel sistema EasyBridge. Parametro (identificativoBU) |
| CF_CODICE_PROFILO_PSP | FIRENZE | Identificativo del Dominio EasyBridge. Parametro (identificativoDominio) |
| URL_ESITO_PAGAMENTO | `http://[host]:[port]/nodo-pagamenti/esitoSessionePagamento/pes_a818?esito=1` | il codice **pes_a818** deve essere coerente con quanto configurato in **CF_CODICE_PROFILO** |
| URL_ANNULLAMENTO_PAGAMENTO | `http://[host]:[port]/nodo-pagamenti/esitoSessionePagamento/pes_a818?esito=0` | il codice **pes_a818** deve essere coerente con quanto configurato in **CF_CODICE_PROFILO** |
| CF_ENTE_QRCODE_PAGOPA | |Il codice fiscale/partitaiva dell'ente che serve per generare la sezione Identificativo Ente/codice fiscale dell'Ente Creditore dell'algoritmo di generazione qrcode|

Le colonne della tabella PAY_PROFILI_ENTI_CREDITORI che non sono mostrate non devono essere valorizzate.

## Configurazione dei parametri specifici del connettore

### pay_connector_config_values

Il connettore prevede l'autenticazione tramite certificato SSL, per far questo devono essere configurati una serie di parametri per il connettore.
I valori di questi parametri devono essere configurati nella tabella PAY_CONNECTOR_CONFIG_VALUES come mostrato sotto.

IDCOMUNE|ID|CONFIG_PARAM|CODICE_CONNETTORE|VALORE
--|--|--|--|--
idcomune|progressivo|SCHED_TRAC_PAGOPA_STRATEGIA|codice_connettore|SFTP
idcomune|progressivo|SCHED_TRAC_PAGOPA_FTP_USER|codice_connettore|username accesso cartella sftp
idcomune|progressivo|SCHED_TRAC_PAGOPA_FTP_PASSWORD|codice_connettore|password utente sftp
idcomune|progressivo|SCHED_TRAC_PAGOPA_FTP_SERVER|codice_connettore|host sftp
idcomune|progressivo|SCHED_TRAC_PAGOPA_FTP_PORT|codice_connettore|port sftp
idcomune|progressivo|SCHED_TRAC_PAGOPA_FTP_FOLDER|codice_connettore|cartella root per la ricerca dei file di tracciato es: **/upload/**
idcomune|progressivo|SCHED_TRAC_PAGOPA_QUARTZEXP|codice_connettore| espressione quartz per gestire la schedulazione delle letture dei tracciati es. (**`0 0/5 * * *`**)
idcomune|progressivo|URL_CALLBACK_CAMBIO_STATO|codice_connettore|`http://<appservername:8080>/api-backend/services/rest-auth-token/nodo-pagamenti/posizione-debitoria/aggiorna-stato`
idcomune|progressivo|SECURITY_ALIAS|codice_connettore|ALIAS DELLA SECURITY
idcomune|progressivo|SECURITY_PWD|codice_connettore|password della security
idcomune|progressivo|SECURITY_URL|codice_connettore|`http://vbg.security:8080/ibcsecurity/services/sigeproSecurity.wsdl`
idcomune|progressivo|SECURITY_USER|codice_connettore|NODO_PAGAMENTI

### Cartella SFTP

Progetti e soluzioni prevede l'invio di traccviati di rendicontazione e si aspetta la predisposizione di una cartella sftp dove andare a salvare i tracciati.
Anche le impostazioni dei parametri **SCHED_%** (pay_connector_config_values) si riferiscono al fatto che deve essere configurata una cartella sftp per andare a leggere i tracciati che EasyBridge invia al Data Provider (in questo  caso il nostro servizio per ente). Le informazioni devono essere date ai referenti easybridge per poter procedere alla loro configurazione.


Esempio di creazione utente sftp.
La creazione della cartella **DEVE** contenere il **CF/PIVA** dell'ente.

Avendo in mente di creare una cartella per l'utente **cliente_x** con piva **01234567891**

```bash

[user@vmsftp ~]$ sudo mkdir -p /data/cliente_x/01234567891/upload
[user@vmsftp ~]$ sudo useradd -g sftpusers -d /01234567891/upload -s /sbin/nologin cliente_x
useradd: cannot create directory /01234567891/upload
[user@vmsftp ~]$ sudo passwd cliente_x
Changing password for user cliente_x.
New password:
Retype new password:
passwd: all authentication tokens updated successfully.
[user@vmsftp ~]$ sudo chown -R root:sftpusers /data/cliente_x
[user@vmsftp ~]$ sudo chown -R cliente_x:sftpusers /data/cliente_x/01234567891/upload
 
```

La configurazione deve essere differente per ogni attivazione di connettore/cliente.

## Configurazione del backend

Ora che il nodo-pagamenti e il connettore sono configurati, bisogna indicare al backend che è attivo un sistema per poter pagare in maniera integrata. Per fare ciò bisogna recarsi nella voce di menù del backend **Configurazione**-> **Tutti i backoffice** -> **Configurazione regole**

![Menù regole](./immagini/backend_01.png )

attivare la verticalizzazione NODO_PAGAMENTI

![Attivazione regola](./immagini/backend_02.png )

e configurare i seguenti parametri personalizzandoli a seconda dell'ente

![Configurazione parametri](./immagini/backend_03.png )

| Parametro | Valore |
| ------ | ------ |
| AR_COD_FISC_ENTE_CREDITORE | CF_CODICE_PROFILO configurato nel nodo dei pagamenti |
| URL_WS |  `http://servername:8085/nodo-pagamenti/services/pagamentiSOAP?wsdl` |
| ID_MODALITA_PAGAMENTO | indicare la modalità configurata nel back

Sono i movimenti da specificare nel caso si voglia riportare i documenti delle posizioni debitorie nella pratica di riferimento.

## Configurazione frontend NGINX

Il servizio deve essere pubblicato sul frontend con due location.

Una Location serve per il rientro dai pagamenti degli utenti durante il pagamento online.

```shell
location ~* /nodo-pagamenti/esitoSessionePagamento/ {
                proxy_http_version      1.1;
                proxy_set_header        Upgrade         $http_upgrade;
                proxy_set_header        Connection      keep-alive;
                proxy_set_header        Host            $host;
                proxy_set_header        X-Real-IP       $remote_addr;
                proxy_set_header        x-forwarded-for $proxy_add_x_forwarded_for;
                proxy_set_header        X-Forwarded-Proto https;
                proxy_cache_bypass      $http_upgrade;

                proxy_pass http://<server>:<port>;
}
```
L'altra serve per ricevere gli esiti pagamento da PES.

```shell
location ~* /nodo-pagamenti/services/easybridge/ {

                allow <server1>; 
                allow <server2>; 
                allow <server3>; # lista IP progetti e soluzioni

                deny all;

                proxy_http_version      1.1;
                proxy_set_header        Upgrade         $http_upgrade;
                proxy_set_header        Connection      keep-alive;
                proxy_set_header        Host            $host;
                proxy_set_header        X-Real-IP       $remote_addr;
                proxy_set_header        x-forwarded-for $proxy_add_x_forwarded_for;
                proxy_set_header        X-Forwarded-Proto https;
                proxy_cache_bypass      $http_upgrade;

                sub_filter_types        *;
                sub_filter_once off;
                sub_filter 'http://<server_name_cliente>' 'https://<server_name_cliente>';

                proxy_pass http://<server>:<port>;
}
```
# Spid Gateway Solution di Progetti e Soluzioni

Il connettore permette di integrarci come DP (Data Provider) nei confronti di SGS di **Progetti e Soluzioni** (d'ora innanzi **PeS**)

## Formalismi da attivare

Se l'ente intende avviare la procedura di adesione deve inoltrarci alcune informazioni che poi dovremmo mandare a **PeS**

Parametro|Obbligatorio|Note
---|---|---
Nome ente|SI|Comune di Demo
Dominio principale (entity ID)|SI|Scrivere solo il dominio (es.  comune.demo.it) senza protocollo o altri parametri accodati
Partita IVA|SI|es. 00841910999
IPA_CODE|SI|es. c_l123
Codice Catastale (Belfiore)|SI|Codice composto da una lettera e tre cifre usato nella parte finale del codice fiscale (es: Milano F205)
Località|SI|es. Demo
Sito istituzionale Ente|SI|es. http://www.comune.demo.it/ 
Referente dell'Ente  - Telefono|SI|Persona da contattare in caso di comunicazioni o segnalazioni in ambito SPID
Referente dell'Ente  - Nome|SI|Persona da contattare in caso di comunicazioni o segnalazioni in ambito SPID
Referente dell'Ente  - Cognome|SI|Persona da contattare in caso di comunicazioni o segnalazioni in ambito SPID
Referente dell'Ente  - e-mail|SI|Persona da contattare in caso di comunicazioni o segnalazioni in ambito SPID
Logo|NO|Allegare Logo dell'Ente che desidera esporre sulle pagine SGS. Formati accettati: jpg, jpeg, png, bmp, gif, svg e webp

Lato Configurazione vanno indicati a **PeS** le url che gli permetterà di configurare il nostro applicativo come Data Provider.

Parametro|Obbligatorio|Note
---|---|---
AMBIENTE CIE/SPID PRODUZIONE||
URL di avvio del servizio|SI| `https://URLSERVIZIO/areariservata/login/**ALIAS**/**SOFTWARE**`
URL di provenienza|NO|Indicare solo in caso in cui il protocollo e/o il dominio di terzo livello della pagina su cui viene esposto il pulsante non appartiene alla pagina vera e propria che esegue il redirect verso SGS/CGS
URL di rientro (login effettuato)|SI|`https://URLSERVIZIO/ibcauthenticationgateway/sgspes_login`
URL di rientro (login fallito)|SI|`https://URLSERVIZIO/ibcauthenticationgateway/sgspes_login`
URL di rientro (logout effettuato)|SI|`https://URLSERVIZIO/ibcauthenticationgateway/sgspes_logout`
URL di rientro (logout fallito)|SI|`https://URLSERVIZIO/ibcauthenticationgateway/sgspes_logout`
Urls di eccezione|NO|Vedere documento "Aggregatore SPID - Specifiche di interfacciamento" - "Temporary (TEST) scenario"
Dati Personali Richiesti SPID|SI| **spidCode, name, familyName, fiscalNumber**

## Configurazione authgate

Va configurato il file **login-sgs.properties**

``` properties
## SEZIONE PARAMETRI DI DEFAULT
default_pes_logout_url=https://URL_PROGETTI_ESOLUZIONI/do-logout/<spid_operationId>
default_pes_login_url=https:/URL_PROGETTI_ESOLUZIONI/login
default_referer_post=URL_PROGETTI_ESOLUZIONI

## SEZIONE PARAMETRI PERSONALIZZATI PER ALIAS/ENTE
# <ALIAS>.spid_api_key=
# <ALIAS>.spid_service_index=
# <ALIAS>.pes_login_url=
# <ALIAS>.pes_logout_url= 
#

```

Per ogni ente che si intende attivare vanno indicate le configurazioni che **PeS** ci invia dopo aver avviato i formalismi.

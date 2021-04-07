# ProcediMarche - Manuale di configurazione

## Prerequisiti
1. VBG 2.81 o superiore

## Note architetturali e sistemistiche per l’installazione
Dal punto di vista sistemistico l’installazione dei servizi di ProcediMarche non comporta l’aggiunta di nuove componenti all’architettura generale del sistema VBG.
I servizi JSON di ProcediMarche vengono invocati direttamente dalla web-application del backoffice su protocollo http o HTTPS. L’invocazione dei servizi è protetta da basic authentication.

## Configurazione backoffice

### Verticalizzazione PROCEDI_MARCHE
Per attivare nei moduli di VBG le funzionalità di integrazione con i servizi di ProcediMarche deve essere attivata la regola di configurazione PROCEDI_MARCHE per il software in cui si vuole accendere la funzionalità. Devono anche essere presenti e valorizzati i parametri PROCEDI_MARCHE_URL e PROCEDI_MARCHE_CF_ENTE.
La regola di configurazione può essere configurata in VBG accedendo alla voce di menù 
Configurazione --> Tutti i backoffice --> Configurazione Regole ed entrando nel dettaglio della regola PROCEDI_MARCHE per il modulo software di VBG in cui si vuole attivare la funzionalità. Il modulo TT attiverà la funzionalità su tutti i moduli.

![language](/configurazione/procedimarche/immagini/cfgpm1.png)

I dati visualizzati sono quelli effettivi per il Comune di Jesi. L’URL visualizzato è relativo all’ambiente di produzione di ProcediMarche.
Prima di mettere in produzione il servizio occorre assicurarsi che la tabella STP_ENDO_TIPO2  sia vuota o cancellare tutti i record dalla tabella per l’idcomune corrente.

|Nome Parametro|Valori|Descrizione|
| ------ | ------ | ------ |
| PROCEDI_MARCHE_URL | 	http://testwsprocedimenti.regione.marche.it/api (test) o http://wsprocedimenti.regione.marche.it/api (produzione) | URL dell’endpoint dei servizi di ProcediMarche |
| PROCEDI_MARCHE_CF_ENTE | 00135880425 (es. comune di Jesi) | Codice fiscale dell’ente così come censito in ProcediMarche  |
| PROCEDI_MARCHE_USR | jesi | Utente per autenticazione nella chiamata ai servizi tramite basic authentication. Se omesso non viene utilizzata l’autenticazione |
| PROCEDI_MARCHE_PWD | ******* | Password per autenticazione nella chiamata ai servizi tramite basic authentication. Se omesso non viene utilizzata l’autenticazione |

### Verticalizzazione AREA_RISERVATA
Nella scheda di dettaglio di ProcediMarche la funzionalità di compilazione automatica dell'URL per l'avvio su front office del procedimento è basata sul valore del parametro URL_AVVIO_PROCEDIMENTO della verticalizzazione AREA_RISERVATA. 
Il valore del parametro deve essere configurato utilizzando dei segnaposto che saranno sostituiti a runtime di volta in volta con gli effettivi valori.
Ad esempio: http://devel3.init.gruppoinit.it/frontoffice/areariservata/presenta-intervento-locale/**{alias}**/**{software}**/**{codiceintervento}**



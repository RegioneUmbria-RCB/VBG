# Changelog

## VNEXT

### Backoffice

- Implementazione connettore nodo pagamenti Unicredit
- Generazione avviso PagoPA da schede dinamiche secondo specifiche Comune di Parma
- Gestione calcolo delle aree in base al km della localizzazione
- Implementazione connettore nodo pagamenti GovPay
   [(documentazione)](./configurazione/nodo-pagamenti/govpay.md)
- Gestione affitto autorizzazioni/concessioni
   [(documentazione)](./configurazione/autorizzazioni-concessioni/README.md)

### Area riservata

# 2.83

### Area riservata
- Visualizzazione campo **Anno** e **Fabbricato** su ricerche delle *pratiche presentate*
- Nuova gestione degli url di accesso alla console per installazioni multicomune
   [(documentazione)](./configurazione/area-riservata/url-accesso-console/README.md)

### Backoffice
- Movimenti: invio notifica via mail/PEC ad amministrazione
   [(documentazione)](./configurazione/movimenti-notifiche-mailservice/README.md)
   
- Gestione calcolo delle aree in base al km della localizzazione   
- Integrazione con il sistema di registrazione delle ordinanze di occupazione suolo pubblico
   [(documentazione)](./configurazione/ordinanze/modena/README.md)

### Console
- Gestione dei link specifici per comune su nuova **domanda(console)** e su **Domande in sospeso(Console)**
### Nodo Pagamenti

- Implementazione dei servizi Sincroni nel connettore nodo pagamenti Piemonte Pay
   [(documentazione)](./configurazione/nodo-pagamenti/README.md)

# 2.82

### Backoffice

- (.net) PROTOCOLLO_SIGEDO, cambiato l'operatore che si occupa di inserire il protocollo quando la protocollazione è automatica
- (.net) correzioni problematiche ANAGRAFE MAGGIOLI

### Area riservata

- Fix su integrazione con nodo pagamenti
- Gestione metadati su caricamento oggetti

### Console.net

- Fix su integrazione con nodo pagamenti


# 2.81
### Backoffice
- Gestione della generazione debito Fattura comune di Modena
   [(documentazione)](./configurazione/calcolo-cosap/README.md)
- Controllo firma documento prima di protocollare
   [(documentazione)](./configurazione/protocollazione/protocollo_attivo.md)
- Conversione automatica dei documenti in fase di firma
    [(documentazione)](./configurazione/firma-digitale/conversione-automatica-pdf-firma.md)
- Autorizzazioni Accessi gestione delle proroghe - ripristino contatore accessi   
  Da ora è possibile ripristinare il contatore degli accessi quando viene autorizzata una proroga
- Implementazioni e verifiche Procedi Marche
   [(documentazione)](./configurazione/procedimarche/README.md)
- Forzare il documento principale firmato in fase di protocollazione ( parametro FLUSSI_VER_FIRMA_DOC_PRINC della verticalizzazione PROTOCOLLO_ATTIVO )
   [(documentazione)](./configurazione/protocollazione/protocollo_attivo.md)
- Storicizzazione degli oggetti
   [(documentazione)](./configurazione/funzionalita-trasversali/README.md)
- Posizioni debitorie da istanze oneri
    [(documentazione)](./configurazione/gestione-istanze/posizioni-debitorie-istanzeoneri/README.md)

### Area riservata
- Moduli FVG: modificata visualizzazione del PDF di riepilogo in modo da riflettere lo stato di visualizzazione delle varie schede 


# 2.80

### Backoffice

- Gestione mercati occasionali secondo specifiche CSI Piemonte
- Implementazione nuovo calcolo importi pagamento posteggi aree mercatali 2021
- Implementazione pagamento concessionari su occupazione giornaliera delle aree mercatali
- Calcolo importi COSAP su schede dinamiche secondo specifiche CSI Piemonte (distributori carburante, concessioni stradali, mezzi pubblicitari)
- Bollettazione: Integrazione con MIP per Invio/riconciliazione
- Gestione account e-mail multicomune per le installazioni con comuni associati
- Scelta dell'account mail per le comunicazioni automatiche dai movimenti
- Adeguamento del backoffice al nuovo Accettatore Unico di Regione Toscana
- Condivisione documentale in fase di protocollazione
- Possibilità di visualizzare le pratiche con errore in base ai comuni configurati per l'operatore
- Contenuti regionali Friuli: possibilità di allineare solamente gli endoprocedimenti  con i contenuti regionali
 
### Area riservata

- FIX implementazione integrazione con pagamenti NEXI Bassilichi versione 3.1
- Step di benvenuto: configurazione da backend dei comuni da mostrare nella lista dei comuni associati


# Versioni  precedenti alla 2.80

[Changelog delle versioni precedenti alla 2.79](./risorse/versioni-precedenti.md) 


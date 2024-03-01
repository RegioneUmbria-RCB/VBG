# Ricerca pratiche

*URL*: ~/Reserved/InserimentoIstanza/ricerca-pratiche/ricerca-pratiche.aspx

## Requisiti minimi

Richiede:

- Console versione 3.19
- Installazione comune locale versione 2.117

## Descrizione

Permette di ricercare una pratica presentata nel backend locale per numero pratica o riferimenti di protocollo.

I soggetti e le localizzazioni della pratica trovata verranno utilizzati per prepopolare i soggetti e le localizzazioni della pratica che si sta presentando.

## Configurazione di backend

Nella console di backend è necessario configurare il parametro **URL_RICERCA_PRATICA_CONSOLE** della verticalizzazione **SERVIZI_CONSOLE_AREARISERVATA**.
Normalmente il valore del parametro è ROOT_ASPNET_LOCALE/webservices/wsareariservata/wcfservices/ricercapratiche/wsricercapraticheservice.svc (es. <http://devel3/aspnet/webservices/wsareariservata/wcfservices/ricercapratiche/wsricercapraticheservice.svc>)

### Nota sui dati delle anagrafiche recuperate

Se l'utente connesso ha diritti di visura sulla pratica che sta ricercando (quindi se è il richiedente, il tecnico o uno dei soggetti con accesso completo ai dati della pratica) allora verranno restituiti tutti i dati di tutte le anagrafiche presenti nella pratica.

Se invece l'utente non ha tali diritti allora gli unici dati che verranno restituiti saranno:

- TIPOANAGRAFE
- NOME
- NOMINATIVO
- CODICEFISCALE
- PARTITAIVA
- DATANASCITA
- CODCOMNASCITA
- SESSO

Eventuali anagrafiche già presenti nella domanda non verranno modificate e i soggetti recuperati verranno aggiunti a quelli già presenti

> Le qualifiche delle varie anagrafiche vengono mostrate nel riepilogo dei dati della pratica ricercata ma non vengono riportate nei dati di una nuova pratica. Questo perché i tipi soggetto fanno riferimento ai tipi soggetto del backend locale che potrebbero essere diversi nella console

## Parametri

### SelezionePraticaCollegataObbligatoria

> Tipo: boolean
> Default: false

Determina se è obbligatorio effettuare la ricerca di una pratica presentata per la prosecuzione della presentazione.

Se impostato a true il bottone "Ignora ricerca pratica collegata" sarà visibile e l'utente potrà scegliere di non effettuare la ricerca di una pratica.

Vd. parametro **TestoBottoneIgnoraRicerca**

### ErroreNumeroProtocolloObbligatorio

> Tipo: string
> Default: "Il campo Numero protocollo è obbligatorio"

Errore da mostrare nel caso in cui si effettua una ricerca per estremi protocollo ed il campo Numero protocollo sia stato lasciato vuoto (bypassando il controllo lato client)

### ErroreDataProtocolloObbligatoria

> Tipo: string
> Default: "Il campo Data Protocollo è obbligatorio"

Errore da mostrare nel caso in cui si effettua una ricerca per estremi protocollo ed il campo Data Protocollo sia stato lasciato vuoto (bypassando il controllo lato client)

### TestoModificaCollegamento

> Tipo: string
> Default: "Rimuovi collegamento e cerca un'altra pratica"

Testo da mostrare nel bottone che permette di annullare una ricerca effettuata e di effettuarne una nuova. Il bottone è visibile quando si torna sullo step a seguito della ripresa di una domanda o di un click sul pulsante "Indietro"

### TestoMantieniCollegamento

> Tipo: string
> Default: "Mantieni collegamento e procedi"

Testo da mostrare nel bottone che permette di confermare la ricerca effettuata in precedenza e di procedere con la presentazione della domanda. Il bottone è visibile quando si torna sullo step a seguito della ripresa di una domanda o di un click sul pulsante "Indietro"

### TestoConfermaAnnullamentoCollegamento

> Tipo: string
> Default: ""

Testo mostrato nella dialog di conferma quando l'utente richiede l'annullamento di un collegamento tra due pratiche vd **TestoModificaCollegamento**

### ErroreNumeroPraticaObbligatorio

> Tipo: string
> Default: "Il campo Numero Pratica è obbligatorio"

Errore da mostrare nel caso in cui si effettua una ricerca per estremi protocollo ed il campo Numero Pratica sia stato lasciato vuoto (bypassando il controllo lato client)

### ErroreNessunaPraticaTrovata

> Tipo: string
> Default: "Non sono state trovate pratiche corrispondenti ai criteri di ricerca impostati."

Testo mostrato a seguito di una ricerca che non ha restituito risultati

### TestoBottoneCollegaPratica

> Tipo: string
> Default: "Collega a questa pratica"

Testo del bottone che permette di confermare il collegamento tra la pratica ricercata e la pratica che si sta compilando

### TestoBottoneIgnoraRicerca

> Tipo: string
> Default: "Collega a questa pratica"

Testo del bottone che permette di ignorare la ricerca pratica e di bypassare il collegamento tra pratiche. Il bottone è visibile solo se il parametro **SelezionePraticaCollegataObbligatoria** è impostato a *false*

### CopiaAnagrafiche

> Tipo: boolean
> Default: true

Permette di configurare se i dati delle anagrafiche della pratica ricercata devono essere copiati nella pratica corrente

### CopiaLocalizzazioni

> Tipo: boolean
> Default: true

Permette di configurare se i dati delle localizzazioni della pratica ricercata devono essere copiati nella pratica corrente

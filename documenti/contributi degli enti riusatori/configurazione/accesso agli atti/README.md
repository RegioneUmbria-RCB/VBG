# Accesso agli atti

La funzionalità permette di creare un fascicolo di istanze che può essere visionato sul frontoffice dalle anagrafiche che possono accedere al portale. Le anagrafiche che potranno accedere al fascicolo saranno configurate nel backoffice; il fascicolo sarà visionabile per un determinato intervallo di tempo, sempre configurabile nel backoffice.

## Attivare la funzionalità

La funzionalità può essere attivata per singola voce dell’albero dei procedimenti; nella sezione “Area riservata” è stato aggiunto un nuovo checkbox “Accesso Atti”

![accesso_atti_1](./immagini/accesso_atti_1.png)

Se selezionato all’interno dell’elaborazione di un’istanza comparirà un nuovo bottone “Configura accessi atti” che permetterà di creare un fascicolo visionabile sul frontoffice.

## Creazione e Configurazione fascicolo

Entrando in elaborazione di una istanza associata ad una voce dell’albero configurata per creare un fascicolo comparirà un nuovo bottone “Configura accesso atti”
![accesso_atti_2](./immagini/accesso_atti_2.png)

Cliccando sul bottone si aprirà una nuova scheda in cui è presente una tabella con un elenco dei fascicoli creati.
![accesso_atti_3](./immagini/accesso_atti_3.png)

Nella tabella saranno indicati la data di inizio e fine del procedimento, l’operatore che lo ha creato, la colonna “Pubblica” che indica se il fascicolo verrà reso pubblico o meno. L’icona nella colonna “Dettaglio” l’icona presente permetterà di entrare nella configurazione del fascicolo. Il bottone Nuovo permetterà di creare un nuovo fascicolo.

### Creazione fascicolo

Cliccando su nuovo avremo un form che ci permetterà di inserire le seguenti informazioni:

1. Data inizio
2. Data fine
3. Se pubblicare o no il fascicolo

![accesso_atti_4](./immagini/accesso_atti_4.png)

Cliccando su “inserisci” verrà creato un nuovo fascicolo, inizialmente vuoto che contiene due tabelle:

1. Lista delle istanze presenti nel fascicolo
2. Lista delle anagrafiche che possono accedere al fascicolo

![accesso_atti_5](./immagini/accesso_atti_5.png)

### Aggiungere un’anagrafica

Per aggiungere un’anagrafica che può accedere al fascicolo basta effettuare la ricerca nell’apposito campo (vedi immagine),

![accesso_atti_6](./immagini/accesso_atti_6.png)

alla scelta dell’anagrafica automaticamente questa verrà aggiunta alla tabella (evidenziata in verde). La [X] permetterà di eliminare questa associazione.

Nel caso si tenti di aggiungere un’anagrafica già presente, il sistema evidenzierà la situazione tramite un messaggio e l’anagrafica non sarà duplicata nella lista (vedi immagine)

![accesso_atti_7](./immagini/accesso_atti_7.png)

### Aggiungere un’istanza al fascicolo

Cliccando sul bottone “Aggiungi istanza” sarà possibile collegare al fascicolo altre istanze presenti nel sistema

![accesso_atti_8](./immagini/accesso_atti_8.png)

All’aggiunta di un’istanza potranno accadere due casi:

1. Le istanze scelte non hanno istanze collegate
2. Le istanze scelte hanno istanze collegate

Cliccando sul bottone “Aggiungi istanza” si accede alla classica pagina della ricerca istanze, effettuata la ricerca secondo i filtri impostati accederemo ad una pagina che riporterà una lista di istanze

![accesso_atti_9](./immagini/accesso_atti_9.png)

Attraverso il checkbox “selezione multipla” possiamo decidere quali istanze collegare al fascicolo; è possibile decidere per ogni istanza, se mostrare anche i documenti non validi/da verificare attraverso il checkbox “Visualizza anche i documenti da verificare/non validi”.

### Caso 1: Le istanze scelte non hanno istanze collegate

Se selezioniamo tutte istanze che non hanno istanze collegate cliccando sul bottone “Collega istanza” il sistema assocerà le istanze scelte al fascicolo e tornerà sulla pagina principale evidenziando la riuscita dell’operazione con il messaggio “Inserimento eseguito” e popolando la tabella.

![accesso_atti_10](./immagini/accesso_atti_10.png)

### Caso 2: Le istanze scelte hanno istanze collegate

Se tra le istanze selezionate è presente almeno una che ha istanze collegate cliccando sul bottone “Collega istanza” il sistema presenterà una pagina intermedia che permetterà all’operatore di decidere se associare al fascicolo anche le istanze collegate.

![accesso_atti_11](./immagini/accesso_atti_11.png)

All’operatore verranno mostrate tutte le istanze collegate per ogni singola istanza scelta, anche qui sarà possibile effettuare una scelta multipla e se nel fascicolo dovranno essere mostrati anche i documenti non validi/da verificare.
In questa fase non potranno essere modificate le impostazioni delle istanze selezionate al passo precedente. Cliccando sul bottone “Collega istanza” il sistema assocerà le istanze scelte al fascicolo e tornerà sulla pagina principale evidenziando la riuscita dell’operazione con il messaggio “Inserimento eseguito” e popolando la tabella.

> NB: se proviamo ad inserire nella lista una pratica già esistente il sistema la scarta, per modificare la proprietà sulla visualizzazione dei documenti dobbiamo intervenire sul flag presente nella tabella delle istanze legate al fascicolo

Sulla lista delle istanze legate al fascicolo sarà possibile:

1. Eliminare l’associazione dell’istanza al fascicolo tramite l’icona [X]
2. Modificare l’accesso ai documenti

![accesso_atti_12](./immagini/accesso_atti_12.png)

## Modifica fascicolo

Sarà possibile modificare il fascicolo entrando nel dettaglio e in particolare possiamo:

1. Modificare data inizio, data fine e pubblica tramite il bottone “Salva”
2. Aggiungere istanze al fascicolo (procedura descritta nel paragrafo “creazione fascicolo”)
3. Eliminare istanze al fascicolo (procedura descritta nel paragrafo “creazione fascicolo”)
4. Aggiungere anagrafiche al fascicolo (procedura descritta nel paragrafo “creazione fascicolo”)
5. Eliminare anagrafiche al fascicolo (procedura descritta nel paragrafo “creazione fascicolo”)

### Visualizza accessi al fascicolo

All’interno del dettaglio di un fascicolo sarà presente il bottone “Visualizza Accessi” che permetterà di visualizzare gli accessi fatti per ogni singola istanza dal frontoffice

![accesso_atti_13](./immagini/accesso_atti_13.png)

Gli accessi saranno riportati su una tabella

Dove verranno indicati:

1. Anagrafica che ha acceduto
2. Istanza a cui ha acceduto
3. Data/ora di accesso
Le informazioni saranno solo in lettura e non potranno essere modificate/cancellate

### Estrazione dati

Sarà possibile interrogare il sistema per due criteri principali:

- dato un nominativo, conoscere a quali Pratiche Edilizie ha avuto accesso (e quando) e con quale pratica di accesso agli atti è stata effettuata (numero/anno/VIS)
- dato un numero di Pratica Edilizia, da chi e quando è stata visionata e con quale pratica di accesso agli atti è stata effettuata (numero/anno/VIS).

Per poter accedere a questa funzionalità bisognerà seguire il percorso “Statistiche->Pratiche Edilizie->Accesso agli atti”, si aprirà allora una maschera di ricerca (vedi immagine)

![search_accesso_atti](./immagini/search_accesso_atti.png)

Per poter effettuare la ricerca sarà sufficiente selezionare almeno un'anagrafica che ha acceduto alla pratica oppure un numero istanza.
Inoltre è possibile filtrare anche per un dato intervallo temporale impostando le date nel campo Periodo.

E' possibile ordinare la lista sia per "Nominativo" che per "Numero istanza", e in ordine crescente o descrescente.

![list_accesso_atti](./immagini/list_accesso_atti.png)

La lista ottenuta si potrà esportare nei formati csv, xml o PDF selezionando le relative icone nella barra in alto alla tabella.

## Configurazione Area Riservata

### Menu.xml

Per attivare la funzionalità di accesso atti nell’area riservata occorre configurare il file menu.xml del relativo modulo software aggiungendo una nuova voce:

>        <MenuItem icona-bs="search">
>          <Titolo>Accesso agli atti</Titolo>
>          <Descrizione>Permette di accedere alle  richieste di accesso atti presentate mediante l'appositio servizio</Descrizione>
>          <Url>~/reserved/accesso-atti/accesso-atti-list.aspx</Url>
>        </MenuItem>

Questo attiverà un nuovo link nella home page dell’AR

![accesso_atti_14](./immagini/accesso_atti_14.png)

Selezionando tale voce l’utente verrà portato ad una nuova pagina che presenta la lista delle istanze a cui egli ha accesso:

[accesso_atti_15](./immagini/accesso_atti_15.png)

Alla selezione di una delle voci presenti nella lista l’utente verrà portato nella relativa pagina di dettaglio:

![accesso_atti_16](./immagini/accesso_atti_16.png)

## Personalizzazione della descrizione della pagina “Lista atti”

È possibile personalizzare il testo che compare nella pagina “Lista atti” in due modalità:

### Tramite gestione testi

Tramite la pagina di gestione dei testi selezionare la voce “AREA_RISERVATA.ctl00_ContentPlaceHolder1_descrizioneAccessoAtti”.

![accesso_atti_17](./immagini/accesso_atti_17.png)

### Tramite editing dall’interfaccia

Se l’utente che accede all’AR ha impostato il flag “utente tester” potrà editare il testo tramite l’interfaccia. Per fare ciò sarà sufficiente fare doppio click nella sezione che si intende modificare

![accesso_atti_18](./immagini/accesso_atti_18.png)

Questo causerà l’apertura di una finestra pop-up in cui sarà possibile editare il testo della sezione a proprio piacimento

![accesso_atti_19](./immagini/accesso_atti_19.png)
